package org.springframework.samples.petclinic.system;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import org.springframework.boot.actuate.autoconfigure.ExportMetricWriter;
import org.springframework.boot.actuate.metrics.jmx.JmxMetricWriter;
import org.springframework.boot.actuate.metrics.writer.MetricWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jmx.export.MBeanExporter;

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
}
