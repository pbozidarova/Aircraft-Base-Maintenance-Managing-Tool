package managing.tool.e_task.model.dto;

public class TaskViewDto {
    private String taskNum;
    private String code;
    private String description;

    public TaskViewDto() {
    }

    public String getTaskNum() {
        return taskNum;
    }

    public TaskViewDto setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TaskViewDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskViewDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
