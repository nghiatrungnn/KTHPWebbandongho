package com.example.KTHP.webdongho.config;

        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        import io.swagger.v3.oas.models.OpenAPI;
        import io.swagger.v3.oas.models.info.Info;

         @Configuration
 public class ProjectConfig {
 @Bean
 OpenAPI openAPI() {
         return new OpenAPI().info(new Info()
                 .title("Hello Swagger, my name is Trung")
                 .version("3.0.1")
                 );
         }}