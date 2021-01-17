package pl.patrykdolata.lifemanager.mapper

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.patrykdolata.lifemanager.TestUtils
import pl.patrykdolata.lifemanager.TestUtils.withProperPrincipal
import pl.patrykdolata.lifemanager.model.NewAccount
import java.math.BigDecimal

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private lateinit var mapper: AccountMapper

    @BeforeEach
    fun init() {
        withProperPrincipal()
    }

    @Test
    fun `Mapping null NewAccount should throw NPE`() {
        val account: NewAccount? = null
        assertThrows<NullPointerException> { mapper.toEntity(account!!) }
    }

    @Test
    fun `Mapping proper NewAccount should return proper AccountEntity`() {
        val account = NewAccount("acc", BigDecimal("12.5"))
        val result = mapper.toEntity(account)
        assertNotNull(result)
        assertThat(result.name, equalTo(account.name))
        assertThat(result.balance, equalTo(account.startBalance))
        assertThat(result.user.id, equalTo(TestUtils.ID))
    }
}
