package dk.sunepoulsen.tes.springboot.rest.logic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Utility functions that is useful for patch operations.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PatchUtilities {
    public static <T> T patchValue( T originalValue, T updateValue ) {
        if( updateValue != null ) {
            return updateValue;
        }

        return originalValue;
    }
}
