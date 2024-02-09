package org.example;

public class Incident {
    String idAgent;
    String idCall;
    Integer level;

    public Incident(String idAgent, String idCall, Integer level) {
        this.idAgent = idAgent;
        this.idCall = idCall;
        this.level = level;
    }
}
