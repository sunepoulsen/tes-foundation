package dk.sunepoulsen.tes.springboot.rest.logic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility functions that is useful for patch operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchUtilities {
    /**
     * Selects the update when non-null, otherwise the original value.
     *
     * @param <T> value type
     * @param originalValue existing value
     * @param updateValue replacement value, or {@code null} to retain the original
     * @return the selected value
     */
    public static <T> T patchValue( T originalValue, T updateValue ) {
        if( updateValue != null ) {
            return updateValue;
        }

        return originalValue;
    }
}
