package lk.rythmo.userauth.service.impl;

import lk.rythmo.userauth.dto.UserCredentialsDTO;
import lk.rythmo.userauth.service.UserCredentialStore;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Component
public class TokenStore implements UserCredentialStore {

    private Map<String, UserCredentialsDTO> userCredentialsMap;

    public TokenStore() {
        this.userCredentialsMap = new HashMap<>();
    }

    @Override
    public void saveToken(UserCredentialsDTO userCredentialsDTO) {
        userCredentialsMap.put(userCredentialsDTO.getAuthToken(), userCredentialsDTO);
    }

    @Override
    public Optional<UserCredentialsDTO> findByToken(String token) {
        UserCredentialsDTO userCredentialsDTO = userCredentialsMap.get(token);

        if (userCredentialsDTO != null && userCredentialsDTO.getAuthExpire().isAfter(ZonedDateTime.now())) {
            return Optional.of(userCredentialsDTO);
        }

        return Optional.empty();
    }

    @Override
    public Optional<UserCredentialsDTO> findByRefreshToken(String refreshToken) {
        AtomicReference<Optional<UserCredentialsDTO>> userCredentialsDTO = new AtomicReference<>(Optional.empty());
        userCredentialsMap.forEach((key, token) -> {
            if (token.getRefreshToken().equals(refreshToken) && token.getRefreshExpire().isAfter(ZonedDateTime.now()))
                userCredentialsDTO.set(Optional.of(token));
        });

        return userCredentialsDTO.get();
    }

    @Override
    public void deleteCredentialByUsername(String username) {
        this.userCredentialsMap.remove(this.userCredentialsMap.values().stream().
                filter(credential -> credential.getUsername().equals(username)).findFirst().orElse(new UserCredentialsDTO()).getAuthToken());
    }

    @Override
    public void deleteCredentialByToken(String authToken) {
        this.userCredentialsMap.remove(authToken);
    }


}
