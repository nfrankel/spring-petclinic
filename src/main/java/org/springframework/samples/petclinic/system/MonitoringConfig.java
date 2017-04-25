package org.springframework.samples.petclinic.system;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.dropwizard.DropwizardMetricServices;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Configuration
public class MonitoringConfig {

    @Bean
    @ExportMetricWriter
    public MetricWriter jmxMetricWriter(MBeanExporter mBeanExporter) {
        return new JmxMetricWriter(mBeanExporter);
    }

    @Bean
    GraphiteReporter graphiteExporter(MetricRegistry metricRegistry) {
        Graphite graphite = new Graphite("localhost", 22003);
        GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).prefixedWith("boot").build(graphite);
        reporter.start(500, TimeUnit.MILLISECONDS);
        return reporter;
    }

    @Bean
    MyCustomMetric myCustomMetric(DropwizardMetricServices dropwizardMetricServices) {
        return new MyCustomMetric(dropwizardMetricServices, new SecureRandom());
    }

    class MyCustomMetric {

        private final DropwizardMetricServices dropwizardMetricServices;
        private final Random random;

        MyCustomMetric(DropwizardMetricServices dropwizardMetricServices, Random random) {
            this.dropwizardMetricServices = dropwizardMetricServices;
            this.random = random;
        }

        @Scheduled(fixedRate = 10000)
        public void measureDemo() {
            dropwizardMetricServices.submit("gauge.random", random.nextInt(100));
        }
    }
}
