package org.example.dao.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.entity.user.User;
import org.example.entity.user.registration.ConfirmationMethod;
import org.example.entity.user.registration.Registration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> readAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList(); // JPQL-запрос
    }

    @Override
    public User read(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @Transactional
    public User create(User user) {
        Objects.requireNonNull(user);
        user.setRegistration(new Registration(
                null,
                LocalDate.now(),
                ConfirmationMethod.values()[ThreadLocalRandom.current().nextInt(ConfirmationMethod.values().length)],
                user
        ));
        entityManager.persist(user);
        User newbie = findByCredentials(user.getFirstName(), user.getLastName(), user.getEmail()).orElseThrow();
        return user;
    }

    @Override
    @Transactional
    public User update(User user, long id) {
        Objects.requireNonNull(user);
        user.setId(id);
        return entityManager.merge(user);
    }

    @Override
    public Optional<User> findByCredentials(String firstName, String lastName, String email) {
        List<User> users = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.firstName = :fn AND u.lastName=:ln AND u.email = :em",
                        User.class
                )
                .setParameter("fn", firstName)
                .setParameter("ln", lastName)
                .setParameter("em", email)
                .getResultList();
        if (users.size() > 1) throw new IllegalStateException("Expected 1 user, but found " + users.size());
        return users.stream().findFirst();
    }

    @Override
    @Transactional
    public void delete(long id) {
        Optional.ofNullable(read(id)).ifPresent(u -> entityManager.remove(u));
    }
}

