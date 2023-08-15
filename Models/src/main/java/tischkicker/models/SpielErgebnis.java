package tischkicker.models;

import tischkicker.messages.Message;
import tischkicker.messages.MessageType;

public class SpielErgebnis extends Message {
    public Spiel spiel;
    public int toreTeam1;
    public int toreTeam2;

    public Team[] teams;

    public SpielErgebnis() {
        this.type = MessageType.SpielErgebnis;
    }
}
