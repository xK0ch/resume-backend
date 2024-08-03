package de.fynnkoch.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

import static java.time.ZonedDateTime.now;
import static java.time.temporal.ChronoUnit.MICROS;

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "dateTimeProvider")
public class AuditingConfiguration {

  @Bean
  public DateTimeProvider dateTimeProvider() {
    return () -> Optional.of(now().truncatedTo(MICROS));
  }
}
