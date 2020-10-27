package lk.rythmo.userauth.service.impl;

import lk.rythmo.userauth.service.TokenGeneratorService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGeneratorService implements TokenGeneratorService {
    @Override
    public String generateToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
