package pl.edu.pw.elka.bdbt.filharmonia.employee;

import org.hibernate.validator.constraints.Length;
import pl.edu.pw.elka.bdbt.filharmonia.User;
import pl.edu.pw.elka.bdbt.filharmonia.address.Address;
import pl.edu.pw.elka.bdbt.filharmonia.employee.musician.Musician;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(name="employee_pesel_unique", columnNames = "pesel")
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, length = 11)
    protected String pesel;

    @Column(nullable = false, length = 26)
    protected String iban;

    @Column(nullable = false)
    protected String gender;

    @Column(nullable = true)
    protected int salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    protected User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    protected Address address;

    public Employee() {}

    public Long getId() {
        return id;
    }

    public String getPesel() {
        return pesel;
    }

    public String getIban() {
        return iban;
    }

    public String getGender() {
        return gender;
    }

    public int getSalary() { return salary; }

    public User getUser() {
        return user;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
