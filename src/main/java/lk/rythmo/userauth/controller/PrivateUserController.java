package lk.rythmo.userauth.controller;

import lk.rythmo.userauth.dto.UserDTO;
import lk.rythmo.userauth.service.UserAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/private")
public class PrivateUserController {

    @Autowired
    private UserAuthenticationService authenticationService;

    @PostMapping("/user")
    public UserDTO getUserByTokenID(@RequestHeader("authorization") String token) {
        Optional<UserDTO> userDTO = this.authenticationService.findByToken(token);

        return userDTO.orElse(null);
    }
}
