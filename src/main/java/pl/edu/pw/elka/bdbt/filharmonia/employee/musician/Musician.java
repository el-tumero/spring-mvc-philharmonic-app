package pl.edu.pw.elka.bdbt.filharmonia.employee.musician;

import pl.edu.pw.elka.bdbt.filharmonia.concert.Concert;
import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "musicians")
public class Musician extends Employee {

    @Column(nullable = false)
    private String education;

    @Column(nullable = false)
    private String specialization;

    @ManyToMany(mappedBy = "musicians")
    private List<Concert> concerts = new ArrayList<Concert>();

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

    public boolean isAvailable(Date date){
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        List<Concert> concertsInSameDate = this.concerts.stream().filter(concert -> fmt.format(concert.getDate()).equals(fmt.format(date))).collect(Collectors.toList());
        return concertsInSameDate.size() == 0;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

}
