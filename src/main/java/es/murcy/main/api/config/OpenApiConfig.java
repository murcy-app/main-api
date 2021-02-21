package es.murcy.main.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  private final String apiName;
  private final String apiVersion;

  public OpenApiConfig(
          @Value("${project.name}") String apiName,
          @Value("${project.version}") String apiVersion) {
    this.apiName = apiName;
    this.apiVersion = apiVersion;
  }

  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    return new OpenAPI()
            .components(
                    new Components()
                            .addSecuritySchemes(securitySchemeName,
                                    new SecurityScheme()
                                            .name(securitySchemeName)
                                            .type(SecurityScheme.Type.HTTP)
                                            .scheme("bearer")
                                            .bearerFormat("JWT")
                            )
            ).externalDocs(new ExternalDocumentation()
                    .description("Github project")
                    .url("https://github.com/murcy-app/main-api")
            ).info(new Info()
                    .title(apiName)
                    .version(apiVersion)
                    .contact(new Contact()
                            .name("Jorge Rambla")
                            .email("murcy.app@gmail.com")
                            .url("https://www.linkedin.com/in/jorgerambla/")
                    )
            );
  }
}
