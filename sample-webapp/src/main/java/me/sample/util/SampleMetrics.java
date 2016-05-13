package me.sample.util;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;

/**
 * This class used to instantiate metrics
 */
@Component
public class SampleMetrics {
  private final Logger logger = LoggerFactory.getLogger(SampleMetrics.class);

  private static volatile SampleMetrics instance;

  public static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
  // private final Meter requests = METRIC_REGISTRY.meter("requests");

  @PostConstruct
  private void init() {
    // ConsoleReporter reporter = ConsoleReporter.forRegistry(METRIC_REGISTRY)
    // .convertRatesTo(TimeUnit.SECONDS)
    // .convertDurationsTo(TimeUnit.MILLISECONDS)
    // .build();
    // reporter.start(60, TimeUnit.SECONDS);

    final JmxReporter jmxReporter = JmxReporter.forRegistry(METRIC_REGISTRY).build();
    jmxReporter.start();
  }

  public static SampleMetrics getInstance() {
    SampleMetrics localInstance = instance;
    if (localInstance == null) {
      synchronized (SampleMetrics.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new SampleMetrics();
        }
      }
    }
    return localInstance;
  }
}
