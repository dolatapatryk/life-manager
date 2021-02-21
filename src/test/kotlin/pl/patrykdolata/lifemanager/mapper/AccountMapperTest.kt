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
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.model.Account
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
        val account: Account? = null
        assertThrows<NullPointerException> { mapper.toEntity(account!!) }
    }

    @Test
    fun `Mapping proper NewAccount should return proper AccountEntity`() {
        val account = Account("acc", BigDecimal("12.5"))
        val result = mapper.toEntity(account)
        assertNotNull(result)
        assertThat(result.name, equalTo(account.name))
        assertThat(result.balance, equalTo(account.balance))
        assertThat(result.user.id, equalTo(TestUtils.ID))
    }

    @Test
    fun `Mapping null AccountEntity should throw NPE`() {
        val accountEntity: AccountEntity? = null
        assertThrows<NullPointerException> { mapper.toModel(accountEntity!!) }
    }

    @Test
    fun `Mapping proper AccountEntity should return proper Account`() {
        val accountEntity = AccountEntity(UserEntity())
        accountEntity.apply {
            id = 5L
            name = "acc1"
            balance = BigDecimal(12.5)
        }
        val result = mapper.toModel(accountEntity)
        assertNotNull(result)
        assertThat(result.name, equalTo(accountEntity.name))
        assertThat(result.balance, equalTo(accountEntity.balance))
    }
}
