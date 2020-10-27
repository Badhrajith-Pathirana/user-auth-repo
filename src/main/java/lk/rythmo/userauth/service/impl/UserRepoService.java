package lk.rythmo.userauth.service.impl;

import lk.rythmo.userauth.entity.User;
import lk.rythmo.userauth.repository.UserRepository;
import lk.rythmo.userauth.service.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepoService implements UserCrudService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(final User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> find(final String id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
}
