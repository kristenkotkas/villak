package eu.kotkas.villak.core.config;

import eu.kotkas.villak.core.Main;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
      .addResourceHandler("/files/**")
      .addResourceLocations("file:///" + Main.getStaticFolderPath());
    // TODO: 8. dets. 2019 töötab ainult windowsil hetkel
  }
}
