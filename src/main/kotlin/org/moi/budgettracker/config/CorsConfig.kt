package org.moi.budgettracker.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class CorsConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")  // Apply to all endpoints
            .allowedOrigins(
                "http://localhost:3000",
                "http://localhost:3001"
            )
            .allowedMethods(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "PATCH",
                "OPTIONS"
            )
            .allowedHeaders("*")  // Allow all headers including User-Id
            .allowCredentials(true)
            .maxAge(3600)
    }
}