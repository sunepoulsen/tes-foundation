package dk.sunepoulsen.tes.security.certificates

import spock.lang.Specification

class DistinguishedNameSpec extends Specification {

    void "Tests DEFAULT distinguished name"() {
        expect:
            DistinguishedName.DEFAULT.name() == 'CN=Sune Thomas Poulsen, OU=Private Person, O=Private Person, L=Copenhagen, ST=Region Hovedstaden, C=DK'
    }

}
