package me.sample.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import me.sample.util.SampleMetrics;

@Configuration
@ComponentScan(basePackages = "me.sample")
/*@PropertySources(value = {
		@org.springframework.context.annotation.PropertySource("classpath:application-default.properties"),
		@org.springframework.context.annotation.PropertySource("file:${catalina.home}/application.properties")
})*/
@EnableWebMvc
public class SampleConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/bootstrap/**").addResourceLocations("/bootstrap/");
    registry.addResourceHandler("/css/**").addResourceLocations("/css/");
    registry.addResourceHandler("/jquery/**").addResourceLocations("/jquery/");
    registry.addResourceHandler("/js/**").addResourceLocations("/js/");
  }

  @Bean
  SampleMetrics objectEditorMetrics() {
    return SampleMetrics.getInstance();
  }
}
