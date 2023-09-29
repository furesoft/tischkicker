package tischkicker.messages;

import tischkicker.models.Team;

import java.util.ArrayList;

public class SiegerTreppchenMessage extends Message {
    public ArrayList<Team> teams;

    public SiegerTreppchenMessage() {
        this.type = MessageType.SiegerTreppchen;
    }
}

