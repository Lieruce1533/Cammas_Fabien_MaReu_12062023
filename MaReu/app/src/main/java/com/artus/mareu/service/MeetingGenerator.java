package com.artus.mareu.service;

import com.artus.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> PLANIFIED_MEETINGS = Arrays.asList(
            new Meeting(1, "Implémentation de pascal pour des Turbo modules",
                    "11 Juillet 2023", "10:00","Cupcake", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(2, "le pattern du pissenlit",
                    "11 Juillet 2023","10:00","Donut",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(3, "le concepte de front-Back",
                    "11 Juillet 2023", "10:00","Eclair", new ArrayList<>(Arrays.asList("fred@caramail.fr", "francois@lycos.fr"))),
            new Meeting(4, "Le pot au feu",
                    "12 Juillet 2023","10:00","Froyo",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(5, "le Data Binding",
                    "12 Juillet 2023", "10:00","Gingerbread", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(6, "the next IOS will be a fork of Android",
                    "13 Juillet 2023","10:00","Honeycomb",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr"))),
            new Meeting(7, "la culture Montesori l'avenir de l'agriculture",
                    "13 Juillet 2023", "10:00","Ice Cream Sandwich", new ArrayList<>(Arrays.asList("michel@caramail.fr", "francois@lycos.fr"))),
            new Meeting(8, "l'orange sanguine se moque t'elle du véganisme",
                    "14 Juillet 2023","10:00","Jelly Bean",new ArrayList<>(Arrays.asList("patrick@caramail.fr", "gontrand@lycos.fr")))
    );

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(PLANIFIED_MEETINGS);
    }
}
