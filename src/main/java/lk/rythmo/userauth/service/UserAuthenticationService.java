package lk.rythmo.userauth.service;

import lk.rythmo.userauth.dto.UserCredentialsDTO;
import lk.rythmo.userauth.dto.UserDTO;
import lk.rythmo.userauth.dto.UserRegistrationDataDTO;
import lk.rythmo.userauth.exception.PasswordInvalidException;
import lk.rythmo.userauth.exception.UserNotFoundException;

import java.util.Optional;

public interface UserAuthenticationService {

    /**
     * Logs in with the given {@code username} and {@code password}.
     *
     * @param username
     * @param password
     * @return an {@link Optional} of a user when login succeeds
     */
    Optional<UserCredentialsDTO> login(String username, String password) throws UserNotFoundException, PasswordInvalidException;

    /**
     * Finds a user by its dao-key.
     *
     * @param token user dao key
     * @return
     */
    Optional<UserDTO> findByToken(String token);

    /**
     * Logs out the given input {@code user}.
     *
     * @param user the user to logout
     */
    void logout(UserDTO user);

    void logout(String authToken);

    void registerUser(UserRegistrationDataDTO userRegistrationDataDTO);

    Optional<UserCredentialsDTO> refreshToken(String refreshToken);
}
