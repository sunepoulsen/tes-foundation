package dk.sunepoulsen.tes.security.keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

public class KeyPairFactory {

    public static final String DEFAULT_KEY_ALGORITHM = "RSA";
    public static final int DEFAULT_KEY_SIZE = 2048;

    public KeyPair createKeyPair() {
        return createKeyPair(DEFAULT_KEY_ALGORITHM, DEFAULT_KEY_SIZE);
    }

    public KeyPair createKeyPair(final String algorithm, final int keySize) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPairGenerator.initialize(keySize);

            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException(algorithm + " algorithm not available", ex);
        }
    }

}
