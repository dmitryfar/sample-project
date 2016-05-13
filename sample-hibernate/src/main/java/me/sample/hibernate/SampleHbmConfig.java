package me.sample.hibernate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(basePackages = { "me.sample.hibernate.service", "me.sample.hibernate.dao" }, excludeFilters = {
    @Filter(type = FilterType.ANNOTATION, value = Configuration.class) })
@ImportResource({
  "classpath:/database-config.xml"
})
public class SampleHbmConfig {

}
