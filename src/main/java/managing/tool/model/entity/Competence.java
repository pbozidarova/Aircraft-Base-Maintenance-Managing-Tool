package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.CompetenceEnum;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Competence extends BaseEntity {

    CompetenceEnum competenceEnum;


    public Competence() {

    }

    public Competence(CompetenceEnum competenceEnum) {
        this.competenceEnum = competenceEnum;
    }

    public CompetenceEnum getCompetenceEnum() {
        return competenceEnum;
    }

    public Competence setCompetenceEnum(CompetenceEnum competenceEnum) {
        this.competenceEnum = competenceEnum;
        return this;
    }
}
