package managing.tool.e_notification.model.dto;

public class ReplyViewDto {

    private String description;
    private String author;

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
}
