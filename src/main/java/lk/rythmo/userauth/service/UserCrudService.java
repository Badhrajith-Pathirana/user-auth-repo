package lk.rythmo.userauth.service;

import lk.rythmo.userauth.entity.User;

import java.util.Optional;

public interface UserCrudService {
    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndPassword(String username, String password);


}
