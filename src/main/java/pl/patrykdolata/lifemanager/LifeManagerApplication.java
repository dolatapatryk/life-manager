package pl.patrykdolata.lifemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.patrykdolata.lifemanager.config.SecurityProperties;

@SpringBootApplication
@EnableConfigurationProperties({SecurityProperties.class})
public class LifeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LifeManagerApplication.class, args);
	}

}
