package se.kth.iv1201.appserv.jobapp.domain.external.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    String firstname;
    String lastname;
    String emailaddress;
    String personnumber;
    String username;
    String password;
}
