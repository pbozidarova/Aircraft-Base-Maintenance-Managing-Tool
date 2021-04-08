package managing.tool.e_notification.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;

@Entity
@Table(name="replies")
public class ReplyEntity extends BaseEntity {

    private String description;
    private UserEntity author;
    private String attachment;
//    private NotificationEntity notification;

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

    @Column(name="attachment")
    public String getAttachment() {
        return attachment;
    }

    public ReplyEntity setAttachment(String attachment) {
        this.attachment = attachment;
        return this;
    }

    //    @ManyToOne
//    public NotificationEntity getNotification() {
//        return notification;
//    }
//
//    public ReplyEntity setNotification(NotificationEntity notification) {
//        this.notification = notification;
//        return this;
//    }
}
