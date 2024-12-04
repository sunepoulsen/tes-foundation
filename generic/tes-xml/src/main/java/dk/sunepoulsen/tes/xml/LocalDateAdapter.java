package dk.sunepoulsen.tes.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * XML Adapter to marshall and unmarshall XML tags to and from
 * <code>java.time.LocalDate</code>
 * <p>
 *     This Adapter marshall a <code>LocalDate</code> to a ISO date
 *     formatter that formats or parses a date with the offset if
 *     available, such as '2011-12-03' or '2011-12-03+01:00'.
 * </p>
 * <b>Usage</b>
 * <p>
 * The adapter can be used with <code>jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter</code>
 * annotation like this:
 * </p>
 *
 * <pre>
 * <code>
 * &commat;XmlJavaTypeAdapter(value = LocalDateAdapter.class)
 * public void setDate(LocalDate date) {
 *     this.date = date;
 * }
 * </code>
 * </pre>
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    /**
     * Convert a value type to a <code>LocalDate</code>.
     *
     * @param v The value to be converted. Can be null.
     */
    @Override
    public LocalDate unmarshal(String v) {
        return LocalDate.parse(v);
    }

    /**
     * Convert a <code>LocalDate</code> to a value type.
     *
     * @param v The value to be converted. Can be null.
     */
    @Override
    public String marshal(LocalDate v) {
        return DateTimeFormatter.ISO_DATE.format(v);
    }
}
