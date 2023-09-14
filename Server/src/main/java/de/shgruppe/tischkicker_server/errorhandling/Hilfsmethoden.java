package de.shgruppe.tischkicker_server.errorhandling;

import de.shgruppe.tischkicker_server.repositories.TeamRepository;
import de.shgruppe.tischkicker_server.repositories.TurnierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import tischkicker.models.Team;
import tischkicker.models.Turnier;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class Hilfsmethoden {


    public static <T> T optionalCheck(Optional<T> optional, int id) {
        if (optional.isPresent()) {
            return optional.get();
        }
        else {
            System.out.println(optional.getClass() + " mit ID " + id + " nicht vorhanden");
            return null;
        }
    }


    public  static String ermittleDatum(){

         String timestamp = ZonedDateTime.now(ZoneId.of("Europe/Berlin"))
                .format(DateTimeFormatter.ofPattern("MM.dd.yyy"));
        return  timestamp;
    }


}
