package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.example.entity.enums.Authority.ROLE_READ;
import static org.example.entity.enums.Authority.ROLE_WRITE;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain formLoginFilterChain(HttpSecurity http, AuthenticationManager manager) throws Exception {
        return http.csrf().disable() // временно отключаем защиту от подделки запросов
                // настройка правиль аутентификации
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/auth/login", "/auth/registration", "/error").permitAll() // какие url открыть
                                .requestMatchers(HttpMethod.GET, "/templates/**","/forecast/**").hasAnyAuthority(ROLE_READ.name(), ROLE_WRITE.name()) // какие url открыть
                                .requestMatchers(HttpMethod.POST, "/templates/**","/forecast/**").hasAuthority(ROLE_WRITE.name()) // какие url открыть
                                .requestMatchers(HttpMethod.PUT, "/templates/**","/forecast/**").hasAuthority(ROLE_WRITE.name()) // какие url открыть
                                .requestMatchers(HttpMethod.DELETE, "/templates/**","/forecast/**").hasAuthority(ROLE_WRITE.name()) // какие url открыть
                                .anyRequest().authenticated()) // для каких ролей
                // Настройка своей формы аутентификации
                .formLogin(form -> form
                        .loginPage("/auth/login") // страница аутентификации
                        .loginProcessingUrl("/process_login") // сюда будет выполнен post-запрос из формы аутентификации. Spring Security сам создаст метод для обработки пароля и логина из формы login.html
             //           .defaultSuccessUrl("/view/profile", true) // перенаправляем сюда в случае успешной аутентификации
                        .failureUrl("/auth/login?error") // если аутентификация не удалась, выдаём ту же страницу с параметром error
                )
                .logout(logout -> logout.logoutUrl("/auth/logout").logoutSuccessUrl("/auth/login")) // действия для выхода
                .authenticationManager(manager) // используем менеджер вместо отдельного провайдера
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance(); // пока без шифрования пароля в БД
    }

}