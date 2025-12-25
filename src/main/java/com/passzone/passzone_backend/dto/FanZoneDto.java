package com.passzone.passzone_backend.dto;

public class FanZoneDto {
    private Long id;
    private String name;
    private String city;
    private int capacity;
    private String rules;

    public FanZoneDto() {}

    public FanZoneDto(Long id, String name, String city, int capacity, String rules) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.capacity = capacity;
        this.rules = rules;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
}
