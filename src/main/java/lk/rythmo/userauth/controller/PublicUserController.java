package lk.rythmo.userauth.controller;

import lk.rythmo.userauth.dto.ResponseErrorDTO;
import lk.rythmo.userauth.dto.UserCredentialsDTO;
import lk.rythmo.userauth.dto.UserLoginDTO;
import lk.rythmo.userauth.dto.UserRegistrationDataDTO;
import lk.rythmo.userauth.exception.PasswordInvalidException;
import lk.rythmo.userauth.exception.UserNotFoundException;
import lk.rythmo.userauth.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/api/auth/public")
public class PublicUserController {

    @Autowired
    private UserAuthenticationService authentication;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginData) {
        Optional<UserCredentialsDTO> credentials = null;
        try {
            credentials = this.authentication.login(userLoginData.getUsername(), userLoginData.getPassword());
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(new ResponseErrorDTO("Invalid Username!", "40001"), HttpStatus.BAD_REQUEST);
        } catch (PasswordInvalidException e) {
            return new ResponseEntity<>(new ResponseErrorDTO("Invalid Username or Password!", "40002"), HttpStatus.BAD_REQUEST);
        }

        UserCredentialsDTO userCredentialsDTO = credentials.orElse(null);

        if (userCredentialsDTO == null) {
            return new ResponseEntity<ResponseErrorDTO>(new ResponseErrorDTO("Invalid Username!", "40001"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<UserCredentialsDTO>(userCredentialsDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody() UserRegistrationDataDTO registrationData) {
        this.authentication.registerUser(registrationData);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
