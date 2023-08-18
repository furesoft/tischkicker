package tischkicker.messages;

import tischkicker.models.Spiel;
import tischkicker.models.Team;

public class SpielBeendetMessage extends Message {
    Team gewinner;
    Spiel spiel;
    Spiel neuesSpiel;

    public SpielBeendetMessage() {
        this.type = MessageType.SpielBeendet;
    }

    public Team getGewinner() {
        return gewinner;
    }

    public void setGewinner(Team gewinner) {
        this.gewinner = gewinner;
    }

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    public Spiel getNeuesSpiel() {
        return neuesSpiel;
    }

    public void setNeuesSpiel(Spiel neuesSpiel) {
        this.neuesSpiel = neuesSpiel;
    }
}
