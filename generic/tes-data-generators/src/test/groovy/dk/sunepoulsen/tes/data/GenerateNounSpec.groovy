package dk.sunepoulsen.tes.data

import com.opencsv.*
import spock.lang.Ignore
import spock.lang.Specification

import java.nio.file.FileSystem
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

/**
 * This test class does not test any functionality. In stead it is used to convert downloaded data sources
 * into files that can be used by this library.
 */
class GenerateNounSpec extends Specification {

    @Ignore
    void "Generate list of Nouns"() {
        given:
            String dataPath = 'data'
            String inputFilename = "${dataPath}/ddo_fullforms_2023-10-11.csv"
            String outputFilename = "${dataPath}/danish-nouns.csv"

            Reader reader = new FileReader(inputFilename)
            CSVReader csvReader = new CSVReaderBuilder(reader)
                .withCSVParser(new CSVParserBuilder()
                    .withSeparator('\t'.charAt(0))
                    .build())
                .build()

        when:
            FileSystem fs = FileSystems.default
            Path outputPath = fs.getPath(outputFilename)
            if (Files.exists(outputPath)) {
                Files.delete(outputPath)
            }

        and:
            Writer writer = new FileWriter(outputFilename)
            ICSVWriter csvWriter = new CSVWriterBuilder(writer)
                .withParser(new CSVParserBuilder()
                    .withSeparator(';'.charAt(0))
                    .build())
                .build()

        and:
            Iterator<String[]> iterator = csvReader.iterator()
            while (iterator.hasNext()) {
                String[] readLine = iterator.next()

                String form = readLine[0]
                String noun = readLine[1]
                String wordType = readLine[3]

                if (form == noun && wordType.contains('sb.')) {
                    csvWriter.writeNext(readLine[1])
                }

            }
            csvReader.close()
            csvWriter.close()

        then:
            Files.exists(outputPath)
    }
}
