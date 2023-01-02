package pl.edu.pw.elka.bdbt.filharmonia.employee.musician;

import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;

import javax.persistence.*;

@Entity
@Table(name = "musicians")
public class Musician extends Employee {

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String specialization;


    public Musician() {}

    public String getEducation() {
        return education;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
