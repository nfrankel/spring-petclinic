package org.springframework.samples.petclinic.system;

import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.actuate.health.Health.down;

@Configuration
public class HealthConfig {

    @Bean
    public HealthIndicator customHealthIndicator() {
        return () -> down().withDetail("dummy", "Detail").build();
    }
}
