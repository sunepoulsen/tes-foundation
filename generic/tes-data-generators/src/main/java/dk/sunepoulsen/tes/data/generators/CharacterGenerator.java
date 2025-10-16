package dk.sunepoulsen.tes.data.generators;

import java.util.List;
import java.util.Random;

public class CharacterGenerator implements DataGenerator<Character> {

    public static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALL_ALPHA = ALPHA + ALPHA.toLowerCase();
    public static final String DIGITS = "0123456789";
    public static final String ALL_ALPHA_DIGITS = ALL_ALPHA + DIGITS;
    public static final String SPECIAL = " ;:,.'<>";
    public static final String ANY_CHARECTERS = ALL_ALPHA_DIGITS + SPECIAL;

    private final Random random;
    private final String characters;

    public CharacterGenerator(String characters) {
        this(List.of(characters));
    }

    public CharacterGenerator(List<String> strings) {
        this.random = new Random();
        this.characters = String.join("", strings);
    }

    @Override
    public Character generate() {
        return this.characters.charAt(this.random.nextInt(this.characters.length()));
    }

    public static CharacterGenerator createAlpha() {
        return new CharacterGenerator(ALL_ALPHA);
    }

    public static CharacterGenerator createDigits() {
        return new CharacterGenerator(DIGITS);
    }

    public static CharacterGenerator createAll() {
        return new CharacterGenerator(ANY_CHARECTERS);
    }

}
