package pt.model.services;

import org.springframework.http.ResponseEntity;
import pt.model.payload.request.SigninRequest;
import pt.model.payload.request.SignupRequest;

public interface IAuthenticationService {
    ResponseEntity<?> signupPhatTU(SignupRequest request);
    ResponseEntity<?> signinPhatTu(SigninRequest request);
}
