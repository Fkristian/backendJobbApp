package se.kth.iv1201.appserv.jobapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.ApplicationRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.StatusRequst;
import se.kth.iv1201.appserv.jobapp.domain.external.response.GenericResponse;
import se.kth.iv1201.appserv.jobapp.domain.internal.Competences;
import se.kth.iv1201.appserv.jobapp.domain.internal.Dates;
import se.kth.iv1201.appserv.jobapp.service.ApplicationService;
import se.kth.iv1201.appserv.jobapp.service.JwtService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/application")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JwtService jwtService;

    public ApplicationController(ApplicationService applicationService, JwtService jwtService) {
        this.applicationService = applicationService;
        this.jwtService = jwtService;
    }

    @GetMapping("all")
    public ResponseEntity <List <User>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }
    @GetMapping("{id}")
    public ResponseEntity <User> getApplicationById(@PathVariable int id){

        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
    @PutMapping("update-status")
    public GenericResponse updateApplicationStatus(@RequestBody StatusRequst statusRequest){

        return applicationService.updateApplicationStatus(statusRequest);
    }
    @PostMapping("post")
    public ResponseEntity postApplication(@RequestBody ApplicationRequest applicationRequest, HttpServletRequest request){
        return applicationService.postApplication(applicationRequest, request);
    }

}
