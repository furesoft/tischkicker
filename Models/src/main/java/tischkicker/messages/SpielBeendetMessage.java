package tischkicker.messages;

import tischkicker.models.Spiel;
import tischkicker.models.Team;

public class SpielBeendetMessage extends Message {
    public Team getGewinner() {
        return gewinner;
    }

    public void setGewinner(Team gewinner) {
        this.gewinner = gewinner;
    }

    Team gewinner;

    public Spiel getSpiel() {
        return spiel;
    }

    public void setSpiel(Spiel spiel) {
        this.spiel = spiel;
    }

    Spiel spiel;

    public SpielBeendetMessage() {
        this.type = MessageType.SpielBeendet;
    }
}
