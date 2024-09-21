package de.fynnkoch.core.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(withDefaults())
        .csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers(HttpMethod.GET)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST)
                    .hasRole("ADMIN"))
        .httpBasic(withDefaults());
    return httpSecurity.build();
  }

  @Bean
  public UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
    final UserDetails admin =
        User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();

    return new InMemoryUserDetailsManager(admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
