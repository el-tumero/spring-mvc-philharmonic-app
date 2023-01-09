package pl.edu.pw.elka.bdbt.filharmonia.ticket;


import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;

import javax.persistence.*;

@Entity
@Table(
        name = "tickets"
)
public class Ticket {
    @Id
    @SequenceGenerator(
            name="ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    @Column(updatable = false)
    private Long id;

    @Column(updatable = true)
    private String seatId;

    @Column(updatable = true)
    private int price;

    @ManyToOne
    @JoinColumn(name = "concert_id", referencedColumnName = "id", nullable = false)
    private Concert concert;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true, updatable = true)
    private User user;

    public Ticket() {}

    public Long getId() {
        return id;
    }

    public String getSeatId() {
        return seatId;
    }

    public int getPrice() {
        return price;
    }

    public Concert getConcert() {
        return concert;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setConcert(Concert concert) {
        this.concert = concert;
    }
}
