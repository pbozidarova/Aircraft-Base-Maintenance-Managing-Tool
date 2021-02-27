package managing.tool.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table
public class Communication extends BaseEntity {

    private String description;
    private Set<User> author;
    private LocalDateTime dateOfEntry;

    public Communication() {
    }

    @Column(name = "description", nullable = false ,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Communication setDescription(String description) {
        this.description = description;
        return this;
    }

    @OneToMany
    @Column(name ="author", nullable = false)
    public Set<User> getAuthor() {
        return author;
    }

    public Communication setAuthor(Set<User> author) {
        this.author = author;
        return this;
    }

    @Column(name="date_of_entry", nullable = false)
    public LocalDateTime getDateOfEntry() {
        return dateOfEntry;
    }

    public Communication setDateOfEntry(LocalDateTime dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
        return this;
    }


}
