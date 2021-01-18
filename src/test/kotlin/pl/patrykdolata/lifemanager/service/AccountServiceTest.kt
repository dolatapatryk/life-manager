package pl.patrykdolata.lifemanager.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.mock.mockito.SpyBean
import pl.patrykdolata.lifemanager.domain.AccountEntity
import pl.patrykdolata.lifemanager.domain.UserEntity
import pl.patrykdolata.lifemanager.mapper.AccountMapper
import pl.patrykdolata.lifemanager.model.NewAccount
import pl.patrykdolata.lifemanager.repository.AccountRepository
import java.math.BigDecimal

@SpringBootTest
class AccountServiceTest {

    companion object {
        private const val ID = 2L
    }

    @Autowired
    private lateinit var accountService: AccountService

    @MockBean
    private lateinit var accountMapper: AccountMapper

    @MockBean
    private lateinit var accountRepository: AccountRepository

    private lateinit var newAccount: NewAccount
    private lateinit var accountEntity: AccountEntity

    @BeforeEach
    fun init() {
        newAccount = NewAccount("acc1", BigDecimal(12.5))
        accountEntity = AccountEntity(UserEntity())
        accountEntity.id = ID
    }

    @Test
    fun `Create account for currently logged user should return new account id`() {
        whenever(accountMapper.toEntity(any())).thenReturn(accountEntity)
        doReturn(accountEntity).whenever(accountRepository).save(any())
        val id: Long = accountService.create(newAccount)

        assertThat(id, equalTo(ID))
        verify(accountMapper).toEntity(newAccount)
        verify(accountRepository).save(any())
    }
}
