package dk.sunepoulsen.tes.testdata.generators;

import java.util.List;
import java.util.Random;

public class CharacterGenerator implements TestDataGenerator<Character> {

    public static final String ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZÆØÅ";
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL = " ;:,.'<>";

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
        return new CharacterGenerator(List.of(ALPHA, ALPHA.toLowerCase()));
    }

    public static CharacterGenerator createDigits() {
        return new CharacterGenerator(DIGITS);
    }

    public static CharacterGenerator createAll() {
        return new CharacterGenerator(List.of(ALPHA, ALPHA.toLowerCase(), DIGITS, SPECIAL));
    }

}
