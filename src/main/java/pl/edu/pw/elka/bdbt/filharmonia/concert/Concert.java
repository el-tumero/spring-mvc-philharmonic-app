package pl.edu.pw.elka.bdbt.filharmonia.concert;

import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name = "concerts",
        uniqueConstraints = {
                @UniqueConstraint(name="concert_name_unique", columnNames = "name"),
        }
)
public class Concert {
    @Id
    @SequenceGenerator(
            name="concert_sequence",
            sequenceName = "concert_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "concert_sequence"
    )
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration;

    @ManyToOne
    @JoinColumn(name = "philharmonic_id", referencedColumnName = "id", nullable = false)
    private Philharmonic philharmonic;

    @OneToMany(mappedBy = "concert")
    private List<Ticket> tickets;

    public Concert() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getDateString(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public Philharmonic getPhilharmonic() {
        return philharmonic;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}
