package com.artus.mareu.service;

import com.artus.mareu.model.Meeting;

import org.threeten.bp.LocalDateTime;


import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.Set;

public abstract class MeetingGenerator {


    /**
     * A list of fake Meetings to populate our Application.
     */
    public static List<Meeting> PLANIFIED_MEETINGS = Arrays.asList(
            new Meeting(1, "Implémentation de pascal pour des Turbo modules",
                    LocalDateTime.of(2023, 7, 12, 10, 15),"Cupcake", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(2, "le pattern du pissenlit",
                   LocalDateTime.of(2023, 7,12, 11, 0),"Donut",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(3, "le concepte de front-Back",
                    LocalDateTime.of(2023, 7,12, 15,0),"Eclair", new ArrayList<>(Arrays.asList("fred@caramail.fr", "francois@lycos.fr"))),
            new Meeting(4, "Le pot au feu",
                    LocalDateTime.of(2023, 7,13, 9,0),"Froyo",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(5, "le Data Binding",
                    LocalDateTime.of(2023, 7,12, 11, 15),"Gingerbread", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(6, "the next IOS will be a fork of Android",
                    LocalDateTime.of(2023, 7,12, 11, 30),"Honeycomb",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(7, "la culture Montesori l'avenir de l'agriculture",
                    LocalDateTime.of(2023, 7,16, 11, 45), "Ice Cream Sandwich", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(8, "l'orange sanguine se moque t'elle du véganisme",
                    LocalDateTime.of(2023, 7,16, 10, 0),"Jelly Bean",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr")))
    );

    /**
     * The list of Meeting Rooms we dispose of.
     */
    public static List<String> DROIDNA_MEETING_ROOMS = Arrays.asList("Cupcake", "Donut", "Eclair", "Froyo", "Gingerbread", "Honeycomb",
            "Ice Cream Sandwich", "Jelly Bean", "Kit Kat", "Lollipop");

    static List<Meeting> generateMeetings() {return new ArrayList<>(PLANIFIED_MEETINGS);}

    static List<String> generateMeetingRooms() {return DROIDNA_MEETING_ROOMS;}
}
