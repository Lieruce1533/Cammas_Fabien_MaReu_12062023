package com.artus.mareu.model;

public class MeetingRoom {

    private final String name;
    private Boolean isFree;

    public MeetingRoom(String name, Boolean isFree) {
        this.name = name;
        this.isFree = isFree;
    }

    public String getName() {
        return name;
    }

    public Boolean getFree() {
        return isFree;
    }

    public void setFree(Boolean free) {
        isFree = free;
    }
}
