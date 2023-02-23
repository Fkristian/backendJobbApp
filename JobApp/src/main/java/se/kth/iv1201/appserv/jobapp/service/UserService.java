package se.kth.iv1201.appserv.jobapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.kth.iv1201.appserv.jobapp.domain.ApplicationStatus;
import se.kth.iv1201.appserv.jobapp.domain.Role;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.RegisterRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.LogInRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.response.AuthenticationResponse;
import se.kth.iv1201.appserv.jobapp.repository.ApplicationStatusRepository;
import se.kth.iv1201.appserv.jobapp.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ApplicationStatusRepository applicationStatusRepository;


    @Transactional
    public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
        if(userRepository.findByUsername(request.getUsername()) == null) {
            var user = User.builder()
                    .name(request.getFirstname())
                    .email(request.getEmailaddress())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .pnr(request.getPersonnumber())
                    .roleId(2)
                    .surname(request.getLastname())
                    .username(request.getUsername())
                    .build();
            int personId = userRepository.save(user).getPersonId();

            var status = ApplicationStatus.builder()
                    .personId(personId)
                    .status("notapplied")
                    .build();
            applicationStatusRepository.save(status);

            var jwtToken = jwtService.genereateToken(user);
            return ResponseEntity.ok(AuthenticationResponse.builder().token(jwtToken).build());
        } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(409)).build();
            }
    }

    public AuthenticationResponse authenticate(LogInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername());
        //.orElseThrow() behöver Optional
        var jwtToken = jwtService.genereateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
