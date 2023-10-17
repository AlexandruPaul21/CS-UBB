package com.example.entityserver.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String plane;

    @Column
    private String destination;

    @Column
    private LocalDateTime estimatedDeparture;

    @Column
    private Boolean canceled;

    public Flight() {
    }

    public Flight(String plane, String destination, LocalDateTime estimatedDeparture, Boolean canceled) {
        this.plane = plane;
        this.destination = destination;
        this.estimatedDeparture = estimatedDeparture;
        this.canceled = canceled;
    }

    public Flight(Long id, String plane, String destination, LocalDateTime estimatedDeparture, Boolean canceled) {
        this.id = id;
        this.plane = plane;
        this.destination = destination;
        this.estimatedDeparture = estimatedDeparture;
        this.canceled = canceled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
