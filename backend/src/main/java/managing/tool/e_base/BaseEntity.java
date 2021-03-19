package managing.tool.e_base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    private long id;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public BaseEntity setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public BaseEntity setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }
}
