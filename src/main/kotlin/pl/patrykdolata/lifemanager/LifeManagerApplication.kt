package pl.patrykdolata.lifemanager

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import pl.patrykdolata.lifemanager.config.SecurityProperties
import pl.patrykdolata.lifemanager.mapper.UserMapper
import pl.patrykdolata.lifemanager.model.NewUser

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties::class)
class LifeManagerApplication

fun main(args: Array<String>) {
	runApplication<LifeManagerApplication>(*args)
}
