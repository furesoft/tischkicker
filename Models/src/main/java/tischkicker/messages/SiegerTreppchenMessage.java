package tischkicker.messages;

import tischkicker.models.Team;

public class SiegerTreppchenMessage extends Message {
    public Team[] teams;

    public SiegerTreppchenMessage() {
        this.type = MessageType.SiegerTreppchen;
    }
}

