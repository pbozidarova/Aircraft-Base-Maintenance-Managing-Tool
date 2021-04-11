package managing.tool.e_task.model.dto;

import managing.tool.e_task.model.TaskEntity;

public class TaskRequestDto {
    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean isJobCardsPrepared;
    private boolean isQualityAssured;


    public TaskRequestDto() {
    }

    public String getTaskNum() {
        return taskNum;
    }

    public TaskRequestDto setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TaskRequestDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskRequestDto setDescription(String description) {
        this.description = description;
        return this;
    }


    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public TaskRequestDto setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    public boolean isJobCardsPrepared() {
        return isJobCardsPrepared;
    }

    public TaskRequestDto setJobCardsPrepared(boolean jobCardsPrepared) {
        isJobCardsPrepared = jobCardsPrepared;
        return this;
    }

    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public TaskRequestDto setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }


    public static TaskRequestDto asDTO(TaskEntity taskEntity) {
        return new TaskRequestDto().
                setTaskNum(taskEntity.getTaskNum());

    }


}
