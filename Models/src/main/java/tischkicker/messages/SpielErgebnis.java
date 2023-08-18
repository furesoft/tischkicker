package tischkicker.messages;

import tischkicker.models.Spiel;
import tischkicker.models.Team;
import tischkicker.models.Tor;

public class SpielErgebnis extends Message {
    public Spiel spiel;
    public int toreTeam1;
    public int toreTeam2;

    public Team[] teams;

    public Tor.Seite seiteTeam1;
    public Tor.Seite seiteTeam2;

    public SpielErgebnis() {
        this.type = MessageType.SpielErgebnis;
    }
}

