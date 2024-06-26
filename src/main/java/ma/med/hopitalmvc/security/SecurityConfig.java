package ma.med.hopitalmvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("med")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")
                        .build(),
                User.withUsername("admin")
                        .password(passwordEncoder.encode("1234"))
                        .roles("ADMIN", "USER")
                        .build(),
                User.withUsername("hamza")
                        .password(passwordEncoder.encode("1234"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(
                (form) -> form.defaultSuccessUrl("/user/index", true).
                        loginPage("/login").
                        permitAll()

        );
        http.rememberMe(
                (rememberMe) -> rememberMe.key("remember-me").
                        rememberMeServices(new TokenBasedRememberMeServices("remember-me", inMemoryUserDetailsManager()))

        );
        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/user/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/webjars/**").permitAll()
        );
        http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()

        );
        http.exceptionHandling(
                (exception) -> exception.accessDeniedPage("/notAuthorized")
        );
        return http.build();

    }
}
