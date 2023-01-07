package pl.edu.pw.elka.bdbt.filharmonia.concert;

import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "concerts_musicians",
            joinColumns = @JoinColumn(name="concert_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="musician_id", referencedColumnName = "id")
    )
    private Set<Musician> musicians = new HashSet<Musician>();

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

    public Set<Musician> getMusicians() {
        return musicians;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setPhilharmonic(Philharmonic philharmonic) {
        this.philharmonic = philharmonic;
    }
}
