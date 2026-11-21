package dk.sunepoulsen.tes.data.generators;

import java.security.SecureRandom;
import java.util.List;

/** Generates random characters from a supplied set of characters. */
public class CharacterGenerator implements DataGenerator<Character> {

    public static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALL_ALPHA = ALPHA + ALPHA.toLowerCase();
    public static final String DIGITS = "0123456789";
    public static final String ALL_ALPHA_DIGITS = ALL_ALPHA + DIGITS;
    public static final String SPECIAL = " ;:,.'<>";
    public static final String ANY_CHARECTERS = ALL_ALPHA_DIGITS + SPECIAL;
    public static final String URI_PATH_CHARECTERS = ANY_CHARECTERS
        .replace(" ", "")
        .replace(";", "");

    /** Source of random character indices. */
    private final SecureRandom random;
    /** Characters from which values are selected. */
    private final String characters;

    /**
     * Creates a generator from a string of characters.
     *
     * @param characters characters from which to select
     */
    public CharacterGenerator(String characters) {
        this(List.of(characters));
    }

    /**
     * Creates a generator from the concatenation of the supplied strings.
     *
     * @param strings strings containing characters from which to select
     */
    public CharacterGenerator(List<String> strings) {
        this.random = new SecureRandom();
        this.characters = String.join("", strings);
    }

    /**
     * Selects a random character.
     *
     * @return a character from the configured strings
     * @throws IllegalArgumentException if there are no characters to select
     */
    @Override
    public Character generate() {
        return this.characters.charAt(this.random.nextInt(this.characters.length()));
    }

    /**
     * Creates a generator of uppercase and lowercase alphabetic characters.
     *
     * @return the character generator
     */
    public static CharacterGenerator createAlpha() {
        return new CharacterGenerator(ALL_ALPHA);
    }

    /**
     * Creates a generator of decimal digits.
     *
     * @return the character generator
     */
    public static CharacterGenerator createDigits() {
        return new CharacterGenerator(DIGITS);
    }

    /**
     * Creates a generator of alphabetic characters, digits and configured special characters.
     *
     * @return the character generator
     */
    public static CharacterGenerator createAll() {
        return new CharacterGenerator(ANY_CHARECTERS);
    }

}
