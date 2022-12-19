package domain;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Friendship extends Entity<Long>{
    private Long id1;
    private Long id2;
    private LocalDate friendsFrom;

    public Friendship(Long id1, Long id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.friendsFrom = LocalDate.now();
    }

    public LocalDate getFriendsFrom() {
        return friendsFrom;
    }

    public void setFriendsFrom(LocalDate friendsFrom) {
        this.friendsFrom = friendsFrom;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public Long getId2() {
        return id2;
    }

    public void setId2(Long id2) {
        this.id2 = id2;
    }

    public String writeable() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return getId() + ";" + getId1() + ";" + getId2() + ";" + dtf.format(getFriendsFrom()) + "\n";
    }

    @Override
    public String toString() {
        return "Friendship{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", friendsFrom=" + friendsFrom +
                '}';
    }
}
