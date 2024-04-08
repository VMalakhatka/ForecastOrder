package org.example.entity.user.credentials;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Credentials {
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;;
}
