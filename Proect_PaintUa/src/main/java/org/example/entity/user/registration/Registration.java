package org.example.entity.user.registration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.user.User;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "registrations")
public class Registration {
    @Id
    @Column(name = "user_id")
    private Long userId;
    private LocalDate registrationDate;
    @Enumerated(EnumType.STRING)
    private ConfirmationMethod confirmationMethod;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
