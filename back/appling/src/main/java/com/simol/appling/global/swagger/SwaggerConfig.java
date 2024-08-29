package com.simol.appling.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Appling API", version = "1.0.0", description = "Appling API Documentation"))
public class SwaggerConfig {
}
