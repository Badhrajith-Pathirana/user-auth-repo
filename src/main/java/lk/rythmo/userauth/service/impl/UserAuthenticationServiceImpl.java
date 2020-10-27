package lk.rythmo.userauth.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.rythmo.userauth.dto.UserCredentialsDTO;
import lk.rythmo.userauth.dto.UserDTO;
import lk.rythmo.userauth.dto.UserRegistrationDataDTO;
import lk.rythmo.userauth.entity.User;
import lk.rythmo.userauth.exception.PasswordInvalidException;
import lk.rythmo.userauth.exception.UserNotFoundException;
import lk.rythmo.userauth.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.Optional;

@Component
public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Autowired
    private UserCrudService users;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private TokenGeneratorService tokenGeneratorService;

    @Autowired
    private UserCredentialStore tokenStore;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public Optional<UserCredentialsDTO> login(String username, String password) throws UserNotFoundException, PasswordInvalidException {
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptionService.harden(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        Optional<User> optionalUser = users.findByUsername(username);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();

        if (user.getPassword().equals(encryptedPassword)) {
            UserCredentialsDTO userCredentialsDTO = this.getUserCredentials(user.getUsername());

            this.tokenStore.saveToken(userCredentialsDTO);
            return Optional.of(userCredentialsDTO);
        } else {
            throw new PasswordInvalidException();
        }
    }

    @Override
    public Optional<UserDTO> findByToken(String token) {
        Optional<UserCredentialsDTO> userByToken = this.tokenStore.findByToken(token);

        if (userByToken.isPresent()) {
            Optional<User> user = this.users.findByUsername(userByToken.get().getUsername());

            return Optional.of(objectMapper.convertValue(user, UserDTO.class));
        }

        return Optional.empty();
    }

    @Override
    public void logout(UserDTO user) {
        this.tokenStore.deleteCredentialByUsername(user.getUsername());
    }

    @Override
    public void logout(String authToken) {
        this.tokenStore.deleteCredentialByToken(authToken);
    }

    @Override
    public void registerUser(UserRegistrationDataDTO userRegistrationDataDTO) {
        String encryptedPassword = null;
        try {
            encryptedPassword = encryptionService.harden(userRegistrationDataDTO.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        User user = new User(userRegistrationDataDTO.getUserId(), userRegistrationDataDTO.getUsername(), encryptedPassword);

        this.users.save(user);
    }

    @Override
    public Optional<UserCredentialsDTO> refreshToken(String refreshToken) {
        Optional<UserCredentialsDTO> byRefreshToken = this.tokenStore.findByRefreshToken(refreshToken);

        if (byRefreshToken.isPresent()) {
            String username = byRefreshToken.get().getUsername();
            this.tokenStore.deleteCredentialByUsername(username);
            UserCredentialsDTO userCredentials = this.getUserCredentials(username);

            this.tokenStore.saveToken(userCredentials);
            return Optional.of(userCredentials);
        } else {
            return Optional.empty();
        }

    }

    private UserCredentialsDTO getUserCredentials(String username) {
        UserCredentialsDTO userCredentialsDTO = new UserCredentialsDTO();
        userCredentialsDTO.setAccessDate(ZonedDateTime.now());
        userCredentialsDTO.setAuthToken(tokenGeneratorService.generateToken());
        userCredentialsDTO.setRefreshToken(tokenGeneratorService.generateToken());
        userCredentialsDTO.setRefreshExpire(ZonedDateTime.now().plusHours(1L));
        userCredentialsDTO.setAuthExpire(ZonedDateTime.now().plusMinutes(30L));
        userCredentialsDTO.setUsername(username);
        Optional<UserCredentialsDTO> userByAuth = Optional.empty();
        Optional<UserCredentialsDTO> userByRefresh = Optional.empty();

        do {
            userByAuth = this.tokenStore.findByToken(userCredentialsDTO.getAuthToken());
            userByRefresh = this.tokenStore.findByRefreshToken(userCredentialsDTO.getRefreshToken());
        } while (userByAuth.isPresent() || userByRefresh.isPresent());

        return userCredentialsDTO;
    }
}
