package managing.tool.e_notification.model.dto;

import org.springframework.web.multipart.MultipartFile;

public class ReplyResponseDto {

    private String description;
    private MultipartFile img;

    public String getDescription() {
        return description;
    }

    public ReplyResponseDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public MultipartFile getImg() {
        return img;
    }

    public ReplyResponseDto setImg(MultipartFile img) {
        this.img = img;
        return this;
    }
}
