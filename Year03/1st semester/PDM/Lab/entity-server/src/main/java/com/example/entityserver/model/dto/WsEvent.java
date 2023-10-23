package com.example.entityserver.model.dto;

public class WsEvent {
    private String event;

    public WsEvent(String event) {
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
