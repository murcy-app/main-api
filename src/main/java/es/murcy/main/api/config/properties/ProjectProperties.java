package es.murcy.main.api.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

import static es.murcy.main.api.util.StringUtils.EMPTY;

@Configuration
@ConfigurationProperties(prefix = "project")
@NoArgsConstructor
@Data
public class ProjectProperties {
  /**
   * This property describe current project version
   */
  @NotNull
  private String version;

  /**
   * This property describe current project name
   */
  @NotNull
  private String name;

  /**
   * This property describe current project description
   */
  private String description = EMPTY;
}
