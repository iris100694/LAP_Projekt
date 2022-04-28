package com.lap.roomplaningsystem.model;

public class Location {

    private int locationID;
    private String description;
    private String adress;
    private String postCode;
    private String city;

    public Location(int locationID, String description, String adress, String postCode, String city) {
        this.locationID = locationID;
        this.description = description;
        this.adress = adress;
        this.postCode = postCode;
        this.city = city;
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
