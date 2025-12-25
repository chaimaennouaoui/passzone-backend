package com.passzone.passzone_backend.dto;

public class AdminCreateZoneReq {
    private String name;
    private String city;
    private int capacity;
    private String rules;

    public AdminCreateZoneReq() {}

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getRules() { return rules; }
    public void setRules(String rules) { this.rules = rules; }
}
