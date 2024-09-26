package de.fynnkoch.core.configurations;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  @Value("${login.username}")
  private String username;

  @Value("${login.password}")
  private String password;

  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
    httpSecurity
        .cors(
            cors ->
                cors.configurationSource(
                    request -> new CorsConfiguration().applyPermitDefaultValues()))
        .csrf((csrf) -> csrf.ignoringRequestMatchers("/**"))
        .authorizeHttpRequests(
            (requests) ->
                requests
                    .requestMatchers(HttpMethod.GET)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST)
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PATCH)
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT)
                    .hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE)
                    .hasRole("ADMIN"))
        .httpBasic(withDefaults());
    return httpSecurity.build();
  }

  @Bean
  public UserDetailsService userDetailsService(final PasswordEncoder passwordEncoder) {
    final UserDetails admin =
        User.withUsername(this.username)
            .password(passwordEncoder.encode(this.password))
            .roles("ADMIN")
            .build();

    return new InMemoryUserDetailsManager(admin);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
