package pl.patrykdolata.lifemanager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItemInArray;

@SpringBootTest
class LifeManagerApplicationTests {

    @Autowired
    private Environment env;

    @Test
    void contextLoads() {
        assertThat(env.getActiveProfiles(), hasItemInArray("test"));
    }

}
