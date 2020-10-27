package lk.rythmo.userauth.service;

import lk.rythmo.userauth.dto.UserCredentialsDTO;
import lk.rythmo.userauth.entity.User;

import java.util.Optional;

public interface UserCredentialStore {

    void saveToken(UserCredentialsDTO userCredentialsDTO);

    Optional<UserCredentialsDTO> findByToken(String token);

    Optional<UserCredentialsDTO> findByRefreshToken(String refreshToken);

    void deleteCredentialByUsername(String username);

    void deleteCredentialByToken(String authToken);

}
