package com.lap.roomplaningsystem.model;

public record Equipment (int equipmentID, String description){
    @Override
    public int equipmentID() {
        return equipmentID;
    }

    @Override
    public String description() {
        return description;
    }
}
