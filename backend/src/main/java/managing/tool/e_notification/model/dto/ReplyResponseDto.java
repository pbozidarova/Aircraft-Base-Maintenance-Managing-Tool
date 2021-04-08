package managing.tool.e_notification.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ReplyResponseDto {

    private String description;
    private MultipartFile attachment;

    public String getDescription() {
        return description;
    }

    public ReplyResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getAttachment() {
        return attachment;
    }

    public ReplyResponseDto setAttachment(MultipartFile attachment) {
        this.attachment = attachment;
        return this;
    }
}
