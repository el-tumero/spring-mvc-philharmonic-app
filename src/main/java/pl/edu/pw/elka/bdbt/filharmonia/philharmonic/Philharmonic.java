package pl.edu.pw.elka.bdbt.filharmonia.philharmonic;

import pl.edu.pw.elka.bdbt.filharmonia.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(
        name = "philharmonics",
        uniqueConstraints = {
                @UniqueConstraint(name="philharmonic_name_unique", columnNames = "name"),
        }
)
public class Philharmonic {
    @Id
    @SequenceGenerator(
            name="philharmonic_sequence",
            sequenceName = "philharmonic_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "philharmonic_sequence"
    )
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User owner;

    @Column(nullable = true)
    private Date creationDate;

    public Philharmonic(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
