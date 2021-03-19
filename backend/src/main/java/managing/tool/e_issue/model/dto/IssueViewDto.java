package managing.tool.e_issue.model.dto;

import managing.tool.e_issue.model.CommunicationEntity;
import managing.tool.e_issue.model.IssueClassificationEnum;
import managing.tool.e_issue.model.IssueStatusEnum;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_user.model.UserEntity;

import java.time.LocalDateTime;
import java.util.Set;

public class IssueViewDto {
    private String issueNum;
    private String author;
    private String status;
    private String classification;
    private String dueDate;

    private String maintenance;

    public IssueViewDto() {
    }

    public String getIssueNum() {
        return issueNum;
    }

    public IssueViewDto setIssueNum(String issueNum) {
        this.issueNum = issueNum;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public IssueViewDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public IssueViewDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getClassification() {
        return classification;
    }

    public IssueViewDto setClassification(String classification) {
        this.classification = classification;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public IssueViewDto setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getMaintenance() {
        return maintenance;
    }

    public IssueViewDto setMaintenance(String maintenance) {
        this.maintenance = maintenance;
        return this;
    }
}
