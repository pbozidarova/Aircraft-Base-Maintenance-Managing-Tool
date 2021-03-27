package managing.tool.e_notification.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;

@Entity
@Table(name="replies")
public class ReplyEntity extends BaseEntity {

    private String description;
    private UserEntity author;

    public ReplyEntity() {
    }

    @Column(name = "description", nullable = false ,columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public ReplyEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @ManyToOne
//    @Column(name ="author", nullable = false)
    public UserEntity getAuthor() {
        return author;
    }

    public ReplyEntity setAuthor(UserEntity author) {
        this.author = author;
        return this;
    }
}
