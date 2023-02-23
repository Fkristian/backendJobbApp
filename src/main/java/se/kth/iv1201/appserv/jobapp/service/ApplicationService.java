package se.kth.iv1201.appserv.jobapp.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;
import se.kth.iv1201.appserv.jobapp.domain.Availability;
import se.kth.iv1201.appserv.jobapp.domain.Competence;
import se.kth.iv1201.appserv.jobapp.domain.CompetenceProfile;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.ApplicationRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.StatusRequst;
import se.kth.iv1201.appserv.jobapp.domain.external.response.GenericResponse;
import se.kth.iv1201.appserv.jobapp.domain.internal.Competences;
import se.kth.iv1201.appserv.jobapp.domain.internal.Dates;
import se.kth.iv1201.appserv.jobapp.repository.ApplicationStatusRepository;
import se.kth.iv1201.appserv.jobapp.repository.AvailabilityRepository;
import se.kth.iv1201.appserv.jobapp.repository.CompetenceProfileRepository;
import se.kth.iv1201.appserv.jobapp.repository.CompetenceRepository;
import se.kth.iv1201.appserv.jobapp.repository.UserRepository;

import java.sql.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService {

    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;
    private final CompetenceRepository competenceRepository;
    private final CompetenceProfileRepository competenceProfileRepository;
    private final JwtService jwtService;
    private final ApplicationStatusRepository applicationStatusRepository;
    public List<User> getAllApplications() {
        return userRepository.findByRoleId(2);
    }

    public User getApplicationById(int id){
        return userRepository.findByPersonId(id);
    }

    @Transactional
    public ResponseEntity postApplication(ApplicationRequest applicationRequest, HttpServletRequest request) {
        String username = getUserFromToken(request);
        User user = userRepository.findByUsername(username);
        insertAvailability(applicationRequest, user.getPersonId());
        insertCompetence(applicationRequest, user.getPersonId());

        ApplicationStatus status = applicationStatusRepository.findByPersonId(user.getPersonId());
        status.setStatus("unhandled");
        applicationStatusRepository.save(status);

        return ResponseEntity.ok().build();
    }

    @Transactional
    public ResponseEntity updateApplicationStatus(StatusRequst statusRequst) {
        try{
            updateApplicationStatusConcurrently(statusRequst);
            return ResponseEntity.ok().build();
        }
        catch(OptimisticLockException e){
            return ResponseEntity.status(HttpStatusCode.valueOf(412)).build();
        }
    }

    private void updateApplicationStatusConcurrently(StatusRequst statusRequst) {
        ApplicationStatus status = applicationStatusRepository.findByPersonId(statusRequst.getPersonId());
        if(status.getVersion() != 1){
            throw new OptimisticLockException();
        }
        else{
            if(status == null){
                status.setPersonId((statusRequst.getPersonId()));
            }
            status.setStatus(statusRequst.getStatus());
            applicationStatusRepository.saveAndFlush(status);
        }
    }

    private void insertCompetence(ApplicationRequest applicationRequest, int id){
        for (Competences competences: applicationRequest.getCompetenceArray()) {

            int compId = competenceRepository.findByName(competences.getCompetence()).getCompetenceId();

            var competenceProfile = CompetenceProfile.builder()
                    .personId(id)
                    .competenceId(compId)
                    .yearsOfExperience(Double.parseDouble(competences.getYearsOfExperience()))
                    .build();
            competenceProfileRepository.save(competenceProfile);
        }
    }

    private void insertAvailability(ApplicationRequest applicationRequest, int id){
        for (Dates dates : applicationRequest.getAvailabilityArray()) {
            var availability = Availability.builder()
                    .personId(id)
                    .fromDate(Date.valueOf(dates.getStartDate()))
                    .toDate(Date.valueOf(dates.getEndDate()))
                    .build();
            availabilityRepository.save(availability);
        }
    }

    private String getUserFromToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        return jwtService.extractUsername(jwt);
    }
}
