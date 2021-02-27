package managing.tool.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "facilities")
public class Facility extends BaseEntity {

    private String name;
    private String city;
    private String country;
    private int capacity;
    private User manager;

    public Facility() {
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public Facility setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public Facility setCity(String city) {
        this.city = city;
        return this;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public Facility setCountry(String country) {
        this.country = country;
        return this;
    }

    @Column(name = "capacity", nullable = false)
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

}
