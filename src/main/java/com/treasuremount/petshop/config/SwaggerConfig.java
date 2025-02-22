    package com.treasuremount.petshop.config;
    import org.springframework.web.reactive.function.client.WebClient;

    import io.swagger.v3.oas.annotations.OpenAPIDefinition;
    import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
    import io.swagger.v3.oas.annotations.info.Contact;
    import io.swagger.v3.oas.annotations.info.Info;
    import io.swagger.v3.oas.annotations.security.SecurityScheme;
    import io.swagger.v3.oas.annotations.servers.Server;
    import io.swagger.v3.oas.models.OpenAPI;
    import org.springdoc.core.models.GroupedOpenApi;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    @OpenAPIDefinition(
        info = @Info(
            title = "PetShop_Platform",
            description = "PetShop Platform Application Documentation",
            version = "1.0",
            contact = @Contact(name = "PetShop_Platform", url = "http://localhost:8080", email = "mugeshmukesh1817@gmail.com")
        ),
        servers = {
                @Server(url = "http://localhost:8080"),
                @Server(url = "https://petsshopapi-d6dccjc9bne5g7ce.centralindia-01.azurewebsites.net/")
        }

    )
    @SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Bearer token for authorization"
    )

    public class SwaggerConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new io.swagger.v3.oas.models.info.Info().title("PetShop_Platform")
                            .description("PetShop Platform Application Documentation")
                            .version("1.0"));
        }

        @Bean
        public GroupedOpenApi publicApi() {
            return GroupedOpenApi.builder()
                    .group("public")
                    .pathsToMatch("/**")
                    .build();
        }

    }

