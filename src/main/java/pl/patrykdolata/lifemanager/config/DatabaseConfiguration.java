package pl.patrykdolata.lifemanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("pl.patrykdolata.lifemanager.repository")
@EnableTransactionManagement
public class DatabaseConfiguration {
}
