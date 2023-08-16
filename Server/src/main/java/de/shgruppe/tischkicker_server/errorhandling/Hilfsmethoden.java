package de.shgruppe.tischkicker_server.errorhandling;

import java.util.Optional;

public class Hilfsmethoden {
    public static <T> T optionalCheck (Optional<T> optional, int id)
    {
        if (optional.isPresent())
        {
            return optional.get();
        }
        else
        {
            System.out.println(optional.getClass()+" mit ID "+id+" nicht vorhanden");
            return null;
        }
    }
}
