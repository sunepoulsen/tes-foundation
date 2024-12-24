package dk.sunepoulsen.tes.security.certificates;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@EqualsAndHashCode
public class DistinguishedName {

    public static DistinguishedName DEFAULT = DistinguishedName.builder()
        .commonName("Sune Thomas Poulsen")
        .organizationName("Private Person")
        .company("Private Person")
        .city("Copenhagen")
        .state("Region Hovedstaden")
        .country("DK")
        .build();

    private String commonName;
    private String organizationName;
    private String company;
    private String city;
    private String state;
    private String country;

    public String name() {
        return String.format("CN=%s, OU=%s, O=%s, L=%s, ST=%s, C=%s", commonName, organizationName, company, city, state, country);
    }

}
