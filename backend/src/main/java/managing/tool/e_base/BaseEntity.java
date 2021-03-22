package managing.tool.e_base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.Instant;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    private long id;
    private Instant createdOn;
    private Instant updatedOn;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public BaseEntity setId(long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public BaseEntity setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public BaseEntity setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}
