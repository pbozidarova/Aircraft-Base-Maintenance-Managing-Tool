package managing.tool.e_task.model.dto;

import managing.tool.e_task.model.TaskEntity;

import java.util.List;

public class TaskViewDto {
    private String taskNum;
    private String code;
    private String description;
    private String preparedBy;
    private String status;

    private boolean isToolingAvailable;
    private boolean isJobCardsPrepared;
    private boolean isQualityAssured;


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

    public String getStatus() {
        return status;
    }

    public TaskViewDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public TaskViewDto setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    public boolean isJobCardsPrepared() {
        return isJobCardsPrepared;
    }

    public TaskViewDto setJobCardsPrepared(boolean jobCardsPrepared) {
        isJobCardsPrepared = jobCardsPrepared;
        return this;
    }

    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public TaskViewDto setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }

    public String getPreparedBy() {
        return preparedBy;
    }

    public TaskViewDto setPreparedBy(String preparedBy) {
        this.preparedBy = preparedBy;
        return this;
    }

    public static TaskViewDto asDTO(TaskEntity taskEntity) {
        return new TaskViewDto().
                setTaskNum(taskEntity.getTaskNum());

    }


}
