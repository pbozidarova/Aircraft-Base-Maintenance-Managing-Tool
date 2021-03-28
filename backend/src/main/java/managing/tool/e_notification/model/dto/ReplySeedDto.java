package managing.tool.e_notification.model.dto;

import com.google.gson.annotations.Expose;

public class ReplySeedDto {

    @Expose
    private String description;

    public ReplySeedDto() {
    }

    public String getDescription() {
        return description;
    }

    public ReplySeedDto setDescription(String description) {
        this.description = description;
        return this;
    }
}

