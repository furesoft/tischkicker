package de.shgruppe.tischkicker_server;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Hilfsmethoden {

    public static <T> T optionalCheck(Optional<T> optional, int id) {
        if (optional.isPresent()) {
            return optional.get();
        }

        System.out.println(optional.getClass() + " mit ID " + id + " nicht vorhanden");

        return null;
    }

    public static String ermittleDatum() {

        String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
                                        .format(DateTimeFormatter.ofPattern("MM.dd.yyy"));

        return timestamp;
    }

}
