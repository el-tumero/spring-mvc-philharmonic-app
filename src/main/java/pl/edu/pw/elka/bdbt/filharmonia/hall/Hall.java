package pl.edu.pw.elka.bdbt.filharmonia.hall;

import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;
import pl.edu.pw.elka.bdbt.filharmonia.employee.conservator.Conservator;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private boolean hasAirConditioning;

    @ManyToMany
    @JoinTable(
            name = "halls_conservators",
            joinColumns = @JoinColumn(name="hall_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="conservator_id", referencedColumnName = "id")
    )
    private Set<Conservator> conservators = new HashSet<Conservator>();

    @ManyToOne
    @JoinColumn(name = "philharmonic_id", referencedColumnName = "id", nullable = false)
    private Philharmonic philharmonic;

    @OneToMany(mappedBy = "hall")
    private List<Concert> concerts;

    public Hall() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isHasAirConditioning() {
        return hasAirConditioning;
    }

    public void setHasAirConditioning(boolean hasAirConditioning) {
        this.hasAirConditioning = hasAirConditioning;
    }
}
