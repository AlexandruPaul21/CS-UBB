package com.example.simulation3.domain;

import java.time.LocalDateTime;

public class Need extends Entity<Long> {
    private String title;
    private String description;
    private LocalDateTime deadline;
    private Long pInNeed;
    private Long pSave;
    private String status;

    public Need(String title, String description, LocalDateTime deadline, Long pInNeed, Long pSave, String status) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.pInNeed = pInNeed;
        this.pSave = pSave;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getpInNeed() {
        return pInNeed;
    }

    public void setpInNeed(Long pInNeed) {
        this.pInNeed = pInNeed;
    }

    public Long getpSave() {
        return pSave;
    }

    public void setpSave(Long pSave) {
        this.pSave = pSave;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
