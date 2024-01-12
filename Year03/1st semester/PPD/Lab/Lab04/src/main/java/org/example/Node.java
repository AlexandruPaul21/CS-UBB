package org.example;

public class Node {
    private String id;
    private Integer points;

    public Node(String id, Integer points) {
        this.id = id;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "ID: " + id + "; Points: " + points + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Node)) {
            return false;
        }

        return id.equals(((Node) obj).id) && points.equals(((Node) obj).points);

    }
}
