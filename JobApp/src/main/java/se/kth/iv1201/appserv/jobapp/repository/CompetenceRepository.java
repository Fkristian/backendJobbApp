package se.kth.iv1201.appserv.jobapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.iv1201.appserv.jobapp.domain.Competence;

@Repository
public interface CompetenceRepository extends JpaRepository <Competence, Integer> {
    Competence findByName(String name);
}
