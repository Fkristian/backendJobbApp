package se.kth.iv1201.appserv.jobapp.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.kth.iv1201.appserv.jobapp.domain.external.response.AuthenticationResponse;
import se.kth.iv1201.appserv.jobapp.service.JwtService;
import se.kth.iv1201.appserv.jobapp.domain.Role;
import se.kth.iv1201.appserv.jobapp.domain.User;
import se.kth.iv1201.appserv.jobapp.domain.external.request.LogInRequest;
import se.kth.iv1201.appserv.jobapp.domain.external.request.RegisterRequest;
import se.kth.iv1201.appserv.jobapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
        Role role = new Role(2, "applicant");
        var user = User.builder()
                .name(request.getFirstname())
                .email(request.getEmailaddress())
                .password(passwordEncoder.encode(request.getPassword()))
                .pnr(request.getPersonnumber())
                .roleId(2)
                .surname(request.getLastname())
                .username(request.getUsername())
                .build();
                repository.save(user);
        var jwtToken = jwtService.genereateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(LogInRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getUsername()
                )
        );
        var user = repository.findByUsername(request.getUsername());
        //.orElseThrow() behöver Optional
        var jwtToken = jwtService.genereateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


}
