package managing.tool.e_notification.model.dto;

public class ReplyViewDto {

    private String description;
    private String author;
    private String title;
    private String createdOn;

    public String getDescription() {
        return description;
    }

    public ReplyViewDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public ReplyViewDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ReplyViewDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public ReplyViewDto setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
        return this;
    }
}
