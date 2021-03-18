package managing.tool.e_facility.model.dto;

import managing.tool.e_user.model.UserEntity;

public class FacilityViewDto {
    private String name;
    private String city;
    private String country;
    private int capacity;
    private String manager;

    public FacilityViewDto() {
    }

    public String getName() {
        return name;
    }

    public FacilityViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getCity() {
        return city;
    }

    public FacilityViewDto setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public FacilityViewDto setCountry(String country) {
        this.country = country;
        return this;
    }

    public int getCapacity() {
        return capacity;
    }

    public FacilityViewDto setCapacity(int capacity) {
        this.capacity = capacity;
        return this;
    }

    public String getManager() {
        return manager;
    }

    public FacilityViewDto setManager(String manager) {
        this.manager = manager;
        return this;
    }
}
