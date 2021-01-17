package pl.patrykdolata.lifemanager

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasItemInArray
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.env.Environment

@SpringBootTest
class LifeManagerApplicationTests {

	@Autowired
	private lateinit var env: Environment

	@Test
	fun contextLoads() = assertThat(env.activeProfiles, hasItemInArray("test"))

}
