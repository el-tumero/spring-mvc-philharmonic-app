package pl.edu.pw.elka.bdbt.filharmonia.philharmonic;

import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.address.Address;
import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;
import pl.edu.pw.elka.bdbt.filharmonia.hall.Hall;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "philharmonics",
        uniqueConstraints = {
                @UniqueConstraint(name="philharmonic_name_unique", columnNames = "name"),
        }
)
public class Philharmonic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private Date creationDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany(mappedBy = "philharmonic")
    private List<Hall> halls;

    @OneToMany(mappedBy = "philharmonic")
    private List<User> users;

    @OneToMany(mappedBy = "philharmonic")
    private List<Concert> concerts;

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

    public List<Concert> getConcerts() {
        return concerts;
    }

    public Address getAddress() {
        return address;
    }
}
