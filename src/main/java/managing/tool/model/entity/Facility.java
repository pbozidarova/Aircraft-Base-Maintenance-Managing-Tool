package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.CompetenceEnum;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "facilities")
public class Facility extends BaseEntity {

    private String name;
    private String city;
    private String country;
    private int capacity;
    private User manager;
    private Set<Competence> competences;

    public Facility() {
    }

    public String getName() {
        return name;
    }

    public Facility setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Facility setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Facility setCountry(String country) {
        this.country = country;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public Facility setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @OneToOne
    public User getManager() {
        return manager;
    }

    public Facility setManager(User manager) {
        this.manager = manager;
        return this;
    }

    @ManyToMany
    public Set<Competence> getCompetences() {
        return competences;
    }

    public Facility setCompetences(Set<Competence> competences) {
        this.competences = competences;
        return this;
    }
}
