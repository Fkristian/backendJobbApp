package se.kth.iv1201.appserv.jobapp.controller;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.RegisterRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.LogInRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.response.AuthenticationResponse;
import se.kth.iv1201.appserv.jobapp.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public void testDeployment(){
        System.out.println("we are deployed");
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){

        return userService.register(request);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LogInRequest request){
        return ResponseEntity.ok(userService.authenticate(request));
    }

}
