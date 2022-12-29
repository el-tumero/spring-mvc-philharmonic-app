package pl.edu.pw.elka.bdbt.filharmonia.philharmonic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhilharmonicRepository extends JpaRepository<Philharmonic, Long> {}
