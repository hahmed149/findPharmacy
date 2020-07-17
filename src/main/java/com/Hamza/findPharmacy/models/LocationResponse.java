package com.Hamza.findPharmacy.models;

public class LocationResponse {

    private String name;
    private String address;
    private  Double distance;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Double getDistance() {
        return distance;
    }

    public LocationResponse(String name, String address, Double distance) {
        this.name = name;
        this.address = address;
        this.distance = distance;
    }
}
