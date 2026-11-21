package dk.sunepoulsen.tes.deployment.core.data;

import lombok.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/** Filename and bytes of a file to deploy. */
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class DeployFileContent {
    /** Name of the file. */
    private final String filename;
    /** File contents as bytes. */
    private final byte[] content;

    /**
     * Opens a stream over the content bytes.
     * @return a new input stream
     */
    public InputStream getContentAsInputStream() {
        return new ByteArrayInputStream(content);
    }

    /**
     * Decodes the content as UTF-8.
     * @return decoded content
     */
    public String getContentAsString() {
        return getContentAsString(StandardCharsets.UTF_8);
    }

    /**
     * Decodes the content using the supplied charset.
     * @param charset charset used to decode the content
     * @return decoded content
     */
    public String getContentAsString(Charset  charset) {
        return new String(content, charset);
    }
}
