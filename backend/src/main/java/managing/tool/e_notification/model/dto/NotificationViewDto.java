package managing.tool.e_notification.model.dto;

public class NotificationViewDto {
    private String issueNum;
    private String author;
    private String status;
    private String classification;
    private String dueDate;

    private String maintenance;

    public NotificationViewDto() {
    }

    public String getIssueNum() {
        return issueNum;
    }

    public NotificationViewDto setIssueNum(String issueNum) {
        this.issueNum = issueNum;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public NotificationViewDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NotificationViewDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getClassification() {
        return classification;
    }

    public NotificationViewDto setClassification(String classification) {
        this.classification = classification;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public NotificationViewDto setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public NotificationViewDto setMaintenance(String maintenance) {
        this.maintenance = maintenance;
        return this;
    }
}
