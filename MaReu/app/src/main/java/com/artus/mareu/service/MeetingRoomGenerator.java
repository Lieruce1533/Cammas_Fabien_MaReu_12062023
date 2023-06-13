package com.artus.mareu.service;

import com.artus.mareu.model.MeetingRoom;

import java.util.Arrays;
import java.util.List;

public abstract class MeetingRoomGenerator {

    public static List<MeetingRoom> DROIDNA_MEETING_ROOMS = Arrays.asList(
            new MeetingRoom("Cupcake", true),
            new MeetingRoom("Donut",true),
            new MeetingRoom("Eclair",true),
            new MeetingRoom("Froyo",true),
            new MeetingRoom("Gingerbread",true),
            new MeetingRoom("Honeycomb",true),
            new MeetingRoom("Ice Cream Sandwich",true),
            new MeetingRoom("Jelly Bean",true),
            new MeetingRoom("Kit Kat",true),
            new MeetingRoom("Lollipop",true)
    );

}
