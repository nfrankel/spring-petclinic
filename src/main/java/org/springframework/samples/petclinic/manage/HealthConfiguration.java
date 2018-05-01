package org.springframework.samples.petclinic.manage;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HealthConfiguration {

    @Bean
    public HealthIndicator customHealthIndicator() {

        return new HealthIndicator() {

            @Override
            public Health health() {
                return Health.up().withDetail("hello", "world").build();
            }
        };
    }
}
