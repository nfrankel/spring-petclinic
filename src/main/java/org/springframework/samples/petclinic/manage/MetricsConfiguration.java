package org.springframework.samples.petclinic.manage;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
