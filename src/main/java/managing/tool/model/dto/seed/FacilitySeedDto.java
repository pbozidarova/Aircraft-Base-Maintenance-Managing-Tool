package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;
import managing.tool.model.entity.User;

import java.util.Set;

public class FacilitySeedDto {
    @Expose
    private String name;
    @Expose
    private String city;
    @Expose
    private String country;
    @Expose
    private int capacity;
    @Expose
    private String manager;
    @Expose
    private String competences;

    public FacilitySeedDto() {
    }

    public String getName() {
        return name;
    }

    public FacilitySeedDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public FacilitySeedDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public FacilitySeedDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public FacilitySeedDto setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getManager() {
        return manager;
    }

    public FacilitySeedDto setManager(String manager) {
        this.manager = manager;
        return this;
    }

    public String getCompetences() {
        return competences;
    }

    public FacilitySeedDto setCompetences(String competences) {
        this.competences = competences;
        return this;
    }
}
