package managing.tool.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "facilities")
public class FacilityEntity extends BaseEntity {

    private String name;
    private String city;
    private String country;
    private int capacity;
    private UserEntity manager;

    public FacilityEntity() {
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public FacilityEntity setName(String name) {
        this.name = name;
        return this;
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public FacilityEntity setCity(String city) {
        this.city = city;
        return this;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public FacilityEntity setCountry(String country) {
        this.country = country;
        return this;
    }

    @Column(name = "capacity", nullable = false)
    public int getCapacity() {
        return capacity;
    }

    public FacilityEntity setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    @OneToOne
    public UserEntity getManager() {
        return manager;
    }

    public FacilityEntity setManager(UserEntity manager) {
        this.manager = manager;
        return this;
    }

}
