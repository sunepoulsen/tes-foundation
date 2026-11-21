package dk.sunepoulsen.tes.jpa.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Common database column names for timestamped entities. */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ColumnNames {

    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
