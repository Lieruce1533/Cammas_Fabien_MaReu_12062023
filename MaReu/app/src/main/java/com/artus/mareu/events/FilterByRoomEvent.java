package com.artus.mareus.events;


/**
 *
 * Event fired when a user ask to filter with a meeting room
 */

public class FilterByRoomEvent {

    public String room;

    public FilterByRoomEvent(String room) { this.room = room;}
}
