package managing.tool.e_task.model.dto;

import java.util.List;

public class TaskViewDto {
    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean areJobCardsPrepared;
    private boolean isQualityAssured;

    private List<String> preparedBy;

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

    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public TaskViewDto setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    public boolean isAreJobCardsPrepared() {
        return areJobCardsPrepared;
    }

    public TaskViewDto setAreJobCardsPrepared(boolean areJobCardsPrepared) {
        this.areJobCardsPrepared = areJobCardsPrepared;
        return this;
    }

    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public TaskViewDto setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }

    public List<String> getPreparedBy() {
        return preparedBy;
    }

    public TaskViewDto setPreparedBy(List<String> preparedBy) {
        this.preparedBy = preparedBy;
        return this;
    }

}
