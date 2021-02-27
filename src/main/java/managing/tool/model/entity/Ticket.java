package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.TicketClassification;
import managing.tool.model.entity.enumeration.TicketStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private String ticketNum;
    private String description;
    private User createdBy;
    private TicketStatus status;
    private TicketClassification classification;
    private LocalDate dateOfEntry;
    private LocalDate dueDate;

    private Maintenance maintenance;
    private Set<Task> tasks;

    public Ticket() {
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public Ticket setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
        return this;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Ticket setDescription(String description) {
        this.description = description;
        return this;
    }

    @OneToOne
    public User getCreatedBy() {
        return createdBy;
    }

    public Ticket setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public TicketStatus getStatus() {
        return status;
    }

    public Ticket setStatus(TicketStatus status) {
        this.status = status;
        return this;
    }

    @Enumerated(EnumType.STRING)
    public TicketClassification getClassification() {
        return classification;
    }

    public Ticket setClassification(TicketClassification classification) {
        this.classification = classification;
        return this;
    }

    public LocalDate getDateOfEntry() {
        return dateOfEntry;
    }

    public Ticket setDateOfEntry(LocalDate dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
        return this;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Ticket setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @OneToOne
    public Maintenance getMaintenance() {
        return maintenance;
    }

    public Ticket setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    public Set<Task> getTasks() {
        return tasks;
    }

    public Ticket setTasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
