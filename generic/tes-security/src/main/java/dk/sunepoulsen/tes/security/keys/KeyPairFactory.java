package dk.sunepoulsen.tes.security.keys;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

/** Creates key pairs using JCA key pair generators. */
public class KeyPairFactory {

    public static final String DEFAULT_KEY_ALGORITHM = "RSA";
    public static final int DEFAULT_KEY_SIZE = 2048;

    /** @return a key pair using the default algorithm and key size */
    public KeyPair createKeyPair() {
        return createKeyPair(DEFAULT_KEY_ALGORITHM, DEFAULT_KEY_SIZE);
    }

    /**
     * Generates a key pair with the requested algorithm and size.
     * @param algorithm JCA key pair algorithm
     * @param keySize key size in bits
     * @return the generated key pair
     * @throws IllegalStateException if the algorithm is unavailable
     * @throws java.security.InvalidParameterException if the key size is invalid
     */
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
