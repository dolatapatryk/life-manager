package pl.patrykdolata.lifemanager.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import javax.servlet.ServletContext

@Configuration
class WebConfigurer(private val environment: Environment, private val securityProperties: SecurityProperties)
    : ServletContextInitializer {

    private val log: Logger = LoggerFactory.getLogger(WebConfigurer::class.java)

    override fun onStartup(servletContext: ServletContext?) {
        if (environment.activeProfiles.isNotEmpty()) {
            log.info("Web application configuration, using profiles: ${environment.activeProfiles}")
        }
        log.info("Web application fully configured")
    }

    @Bean
    fun corsFilter(): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = securityProperties.cors
        if (!corsConfiguration.allowedOrigins.isNullOrEmpty()) {
            log.debug("Registering CORS filter")
            source.registerCorsConfiguration("/api/**", corsConfiguration)
            source.registerCorsConfiguration("/v2/api-docs", corsConfiguration)
        }
        return CorsFilter(source)
    }
}
