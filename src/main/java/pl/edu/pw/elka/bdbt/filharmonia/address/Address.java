package pl.edu.pw.elka.bdbt.filharmonia.address;

import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String town;

    @Column(nullable = false)
    private String street;

    @Column(nullable = true, length = 5)
    private String apartamentNumber;

    @Column(nullable = false, length = 6)
    private String postcode;

    @OneToOne(mappedBy = "address")
    private Employee employee;

    @OneToOne(mappedBy = "address")
    private Philharmonic philharmonic;

    public Address() {}

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartamentNumber() {
        return apartamentNumber;
    }

    public void setApartamentNumber(String apartamentNumber) {
        this.apartamentNumber = apartamentNumber;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
