package dk.sunepoulsen.tes.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Clock;
import java.time.ZonedDateTime;

@MappedSuperclass
@Getter
public abstract class TimestampEntity {

    @Column( name = ColumnNames.CREATE_TIME, nullable = false, updatable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private ZonedDateTime createDateTime;

    @Column( name = ColumnNames.UPDATE_TIME, nullable = false )
    @Temporal( TemporalType.TIMESTAMP )
    private ZonedDateTime updateDateTime;

    @PrePersist
    private void createTimestamp() {
        this.createDateTime = ZonedDateTime.now(Clock.systemUTC());
        this.updateDateTime = this.createDateTime;
    }

    @PreUpdate
    private void updateTimestamp() {
        this.updateDateTime = ZonedDateTime.now(Clock.systemUTC());
    }

}
