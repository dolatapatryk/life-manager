package pl.patrykdolata.lifemanager.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

@ConfigurationProperties(prefix = "security", ignoreUnknownFields = false)
public class SecurityProperties {

    @Getter
    private final CorsConfiguration cors = new CorsConfiguration();
}
