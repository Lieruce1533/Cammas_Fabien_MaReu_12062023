package com.artus.mareu.events;


import androidx.annotation.NonNull;

public class DeleteParticipantEvent {

    public String participant;

    public DeleteParticipantEvent(String participant) {this.participant = participant;}
}
