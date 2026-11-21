package dk.sunepoulsen.tes.security.certificates;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/** Components of a certificate distinguished name. */
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class DistinguishedName {

    public static final DistinguishedName DEFAULT = DistinguishedName.builder()
        .commonName("Sune Thomas Poulsen")
        .organizationName("Private Person")
        .company("Private Person")
        .city("Copenhagen")
        .state("Region Hovedstaden")
        .country("DK")
        .build();

    /** Common name (CN). */
    private String commonName;
    /** Organizational unit (OU). */
    private String organizationName;
    /** Organization (O). */
    private String company;
    /** Locality (L). */
    private String city;
    /** State or province (ST). */
    private String state;
    /** Country (C). */
    private String country;

    /**
     * Formats the components as a distinguished name.
     *
     * @return the formatted name
     */
    public String name() {
        return String.format("CN=%s, OU=%s, O=%s, L=%s, ST=%s, C=%s", commonName, organizationName, company, city, state, country);
    }

}
