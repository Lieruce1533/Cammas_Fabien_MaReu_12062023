package com.artus.mareu.events;

/**
 * Event fired when a user deletes a participant
 */
public class DeleteParticipantEvent {
    /**
     * participant to delete
     */
    public String participant;

    /**
     * constructor
     * @param participant
     */
    public DeleteParticipantEvent(String participant) {this.participant = participant;}
}
