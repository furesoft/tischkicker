package tischkicker.messages;

import tischkicker.models.Turnier;

public class TurnierBeendetMessage extends Message {

    Turnier turnier;
    public TurnierBeendetMessage() {
        this.type = MessageType.TurnierBeendet;
    }

    public Turnier getTurnier() {
        return turnier;
    }

    public void setTurnier(Turnier turnier) {
        this.turnier = turnier;
    }

}
