package managing.tool.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Communication extends BaseEntity {

    private String description;
    private User author;

    public Communication() {
    }

    public String getDescription() {
        return description;
    }

    public Communication setDescription(String description) {
        this.description = description;
        return this;
    }

}
