package com.example.simulation3.domain.DTOs;

import com.example.simulation3.domain.Need;

public class NeedDTO {
    private Long id;
    private String title;
    private String description;
    private String deadline;
    private Long pNeed;
    private Long pSave;

    public NeedDTO(Need need) {
        id = need.getId();
        title = need.getTitle();
        description = need.getDescription();
        deadline = need.getDeadline().toString();
        pNeed = need.getpInNeed();
        pSave = need.getpSave();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Long getpNeed() {
        return pNeed;
    }

    public void setpNeed(Long pNeed) {
        this.pNeed = pNeed;
    }

    public Long getpSave() {
        return pSave;
    }

    public void setpSave(Long pSave) {
        this.pSave = pSave;
    }
}
