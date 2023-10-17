package com.example.entityserver.model.dto;

import jakarta.annotation.Nullable;

import java.io.Serializable;
import java.time.LocalDateTime;

public class FlightDto implements Serializable {
    private Long id;
    private String plane;
    private String destination;
    private LocalDateTime estimatedDeparture;
    private Boolean canceled;

    public FlightDto(@Nullable Long id, String plane, String destination, LocalDateTime estimatedDeparture, Boolean canceled) {
        this.id = id;
        this.plane = plane;
        this.destination = destination;
        this.estimatedDeparture = estimatedDeparture;
        this.canceled = canceled;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getEstimatedDeparture() {
        return estimatedDeparture;
    }

    public void setEstimatedDeparture(LocalDateTime estimatedDeparture) {
        this.estimatedDeparture = estimatedDeparture;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }
}
