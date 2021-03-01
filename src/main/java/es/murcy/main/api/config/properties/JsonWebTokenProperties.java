package es.murcy.main.api.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@NoArgsConstructor
@Data
public class JsonWebTokenProperties {

  /**
   * This property defines the total <b>Json Web Token</b> validity
   */
  private Validity validity = new Validity();

  /**
   * This property defines the secret token in the <b>Json Web Token</b> authentication system
   */
  @NotNull
  @NotEmpty
  private String secret;

  @NoArgsConstructor
  @Data
  public static class Validity {

    /**
     * This property defines the <b>Json Web Token</b> validity days
     */
    @Min(0)
    private long days = 1;

    /**
     * This property defines the <b>Json Web Token</b> validity hours
     */
    @Min(0)
    private long hours = 0;

    /**
     * This property defines the <b>Json Web Token</b> validity minutes
     */
    @Min(0)
    private long minutes = 0;
  }
}
