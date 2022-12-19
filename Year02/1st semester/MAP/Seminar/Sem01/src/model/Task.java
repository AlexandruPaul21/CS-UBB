package model;

import java.util.Objects;

public abstract class Task {
    private String taskId;
    private String description;

    public Task(String taskId, String description) {
        this.taskId = taskId;
        this.description = description;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //decorator explicit
    @Override
    public String toString() {
        return getTaskId() + " " + getDescription();
    }

    public abstract void execute();

    public int hashCode() {
        return Objects.hash(getTaskId(),getDescription());
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Task task)) {
            return false;
        }

        return Objects.equals(task.getTaskId(),getTaskId()) &&
                Objects.equals(task.getDescription(),getDescription());
    }
}
