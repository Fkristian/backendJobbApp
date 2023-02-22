package se.kth.iv1201.appserv.jobapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;

@Repository
public interface ApplicationStatusRepository extends JpaRepository <ApplicationStatus, Integer> {
    ApplicationStatus findByPersonId(int id);
}
