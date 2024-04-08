package org.example.dao.user;

import org.example.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    User create(User user);
    User read(long id);
    List<User> readAll();
    User update(User user, long id);
    Optional<User> findByCredentials(String firstName, String lastName, String email);
    void delete(long id);
}
