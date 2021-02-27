package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;

public class TaskSeedDto {
    @Expose
    private String taskNum;
    @Expose
    private String code;
    @Expose
    private String description;

    public TaskSeedDto() {
    }

    public String getTaskNum() {
        return taskNum;
    }

    public TaskSeedDto setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TaskSeedDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskSeedDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
