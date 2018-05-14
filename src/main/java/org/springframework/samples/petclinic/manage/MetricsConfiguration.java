package org.springframework.samples.petclinic.manage;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class MetricsConfiguration {

    @Bean
    public Counter dummyCounter(MeterRegistry registry) {
        Counter counter = registry.counter("dummy.counter");
        counter.increment();
        counter.increment(3);
        counter.increment();
        counter.increment(5);
        return counter;
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public Gauge dummyGauge(MeterRegistry registry, Random random) {
        return Gauge.builder("dummy.gauge", random, r -> r.nextInt(10)).register(registry);
    }

    @Bean
    public MeterRegistryCustomizer customizer() {
        return registry -> registry.config().commonTags("petclinic");
    }
}
