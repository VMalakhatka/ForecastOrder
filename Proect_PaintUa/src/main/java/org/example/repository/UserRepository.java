package org.example.repository;

import org.example.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
 //   List<User> findAllByLastNameAndFirstNameAndEmail(String lastName, String firstName, String email);
    Optional<User> findByCredentials_Username(String username);
}