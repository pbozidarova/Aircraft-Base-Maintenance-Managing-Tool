package managing.tool.e_notification.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
public class CommunicationEntity extends BaseEntity {

    private String description;
    private Set<UserEntity> author;
    private LocalDateTime dateOfEntry;

    public CommunicationEntity() {
    }

    @Column(name = "description", nullable = false ,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public CommunicationEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @OneToMany
    @Column(name ="author", nullable = false)
    public Set<UserEntity> getAuthor() {
        return author;
    }

    public CommunicationEntity setAuthor(Set<UserEntity> author) {
        this.author = author;
        return this;
    }

    @Column(name="date_of_entry", nullable = false)
    public LocalDateTime getDateOfEntry() {
        return dateOfEntry;
    }

    public CommunicationEntity setDateOfEntry(LocalDateTime dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
        return this;
    }


}
