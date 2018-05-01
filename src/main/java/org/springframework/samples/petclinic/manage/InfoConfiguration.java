package org.springframework.samples.petclinic.manage;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InfoConfiguration {

    @Bean
    public InfoContributor helloInfoContributor() {

        return new InfoContributor() {

            @Override
            public void contribute(Info.Builder builder) {
                builder.withDetail("hello", "world!");
            }
        };
    }
}
