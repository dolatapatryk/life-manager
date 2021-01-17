package pl.patrykdolata.lifemanager.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.web.cors.CorsConfiguration

@ConfigurationProperties(prefix = "security", ignoreInvalidFields = true)
@ConstructorBinding
data class SecurityProperties(val cors: CorsConfiguration)
