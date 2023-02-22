package se.kth.iv1201.appserv.jobapp.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.kth.iv1201.appserv.jobapp.service.AvailabilityService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("any/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }
}
