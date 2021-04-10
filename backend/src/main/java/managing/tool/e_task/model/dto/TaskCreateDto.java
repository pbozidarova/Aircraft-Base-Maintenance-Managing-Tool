package managing.tool.e_task.model.dto;

import managing.tool.e_task.model.TaskEntity;

public class TaskCreateDto {
    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean isJobCardsPrepared;
    private boolean isQualityAssured;


    public TaskCreateDto() {
    }

    public String getTaskNum() {
        return taskNum;
    }

    public TaskCreateDto setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TaskCreateDto setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TaskCreateDto setDescription(String description) {
        this.description = description;
        return this;
    }


    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public TaskCreateDto setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    public boolean isJobCardsPrepared() {
        return isJobCardsPrepared;
    }

    public TaskCreateDto setJobCardsPrepared(boolean jobCardsPrepared) {
        isJobCardsPrepared = jobCardsPrepared;
        return this;
    }

    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public TaskCreateDto setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }


    public static TaskCreateDto asDTO(TaskEntity taskEntity) {
        return new TaskCreateDto().
                setTaskNum(taskEntity.getTaskNum());

    }


}
