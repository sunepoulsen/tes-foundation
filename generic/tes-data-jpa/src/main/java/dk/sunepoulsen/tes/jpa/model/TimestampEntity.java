package dk.sunepoulsen.tes.jpa.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.Clock;
import java.time.ZonedDateTime;

/** Base entity that records creation and update timestamps in UTC. */
@MappedSuperclass
@Getter
public abstract class TimestampEntity {

    /** Time at which the entity was first persisted. */
    @Column( name = ColumnNames.CREATE_TIME, nullable = false, updatable = false )
    private ZonedDateTime createDateTime;

    /** Time at which the entity was created or last updated. */
    @Column( name = ColumnNames.UPDATE_TIME, nullable = false )
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
