package pl.edu.pw.elka.bdbt.filharmonia.employee.conservator;

import pl.edu.pw.elka.bdbt.filharmonia.employee.Employee;
import pl.edu.pw.elka.bdbt.filharmonia.hall.Hall;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "conservators")
public class Conservator extends Employee {

    @Column(nullable = false)
    private boolean hasPermissionWorkAtHeight;

    @Column(nullable = false)
    private boolean hasLicenceForklift;

    public Conservator() {}

    @ManyToMany(mappedBy = "conservators")
    private List<Hall> halls = new ArrayList<Hall>();

    public boolean isHasPermissionWorkAtHeight() {
        return hasPermissionWorkAtHeight;
    }

    public void setHasPermissionWorkAtHeight(boolean hasPermissionWorkAtHeight) {
        this.hasPermissionWorkAtHeight = hasPermissionWorkAtHeight;
    }

    public boolean isHasLicenceForklift() {
        return hasLicenceForklift;
    }

    public void setHasLicenceForklift(boolean hasLicenceForklift) {
        this.hasLicenceForklift = hasLicenceForklift;
    }
}
