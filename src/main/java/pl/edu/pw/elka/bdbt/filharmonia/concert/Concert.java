package pl.edu.pw.elka.bdbt.filharmonia.concert;

import org.springframework.format.annotation.DateTimeFormat;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;
import pl.edu.pw.elka.bdbt.filharmonia.hall.Hall;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import javax.persistence.*;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(nullable = false, columnDefinition = "TIMESTAMP") // in ORACLE CAN BE DIFFERENT
    private LocalDateTime date;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false)
    private int duration;

    @ManyToOne
    @JoinColumn(name = "philharmonic_id", referencedColumnName = "id", nullable = false)
    private Philharmonic philharmonic;

    @ManyToOne
    @JoinColumn(name = "hall_id", referencedColumnName = "id", nullable = false)
    private Hall hall;

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

    public LocalDateTime getDate() {
        return this.date;
    }

    public String getDateString(){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String strDate = this.date.format(fmt);
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

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public void setDate(LocalDateTime date) {this.date = date; }

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

    public void setMusicians(Set<Musician> musicians) {
        this.musicians = musicians;
    }
}
