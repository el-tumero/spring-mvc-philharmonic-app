package pl.edu.pw.elka.bdbt.filharmonia;

import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;
import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;
import pl.edu.pw.elka.bdbt.filharmonia.philharmonic.Philharmonic;
import pl.edu.pw.elka.bdbt.filharmonia.ticket.Ticket;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name="user_phone_number_unique", columnNames = "phoneNumber"),
                @UniqueConstraint(name = "user_email_unique", columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;


    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = true)
    private String phoneNumber;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(nullable = true)
    private Date birthdate;

    @OneToMany(mappedBy = "user")
    private List<Ticket> tickets;

    @OneToOne(mappedBy = "user")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "philharmonic_id", referencedColumnName = "id", nullable = false)
    private Philharmonic philharmonic;

    public User(){}

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() { return role; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public Employee getEmployee() { return employee; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) { this.role = role; }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhilharmonic(Philharmonic philharmonic) {
        this.philharmonic = philharmonic;
    }
}
