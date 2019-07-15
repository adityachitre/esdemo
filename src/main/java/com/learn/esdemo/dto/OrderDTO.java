package com.learn.esdemo.dto;

public class OrderDTO {

    private String id;
    private LocationDTO location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}
