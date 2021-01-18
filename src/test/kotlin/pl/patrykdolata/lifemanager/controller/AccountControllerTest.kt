package pl.patrykdolata.lifemanager.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import pl.patrykdolata.lifemanager.model.Account
import pl.patrykdolata.lifemanager.service.AccountService
import pl.patrykdolata.lifemanager.util.JsonUtils.asJsonString
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    companion object {
        private const val URL = "/api/accounts"
        private const val ID = 3
        private const val NAME = "acc1"
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var accountService: AccountService

    private val newAccount = Account(NAME, BigDecimal(12.5))

    @Test
    @WithMockUser(username = "user", password = "pass")
    @Throws(Exception::class)
    fun `Create account should return new account id and status 201`() {
        whenever(accountService.create(any())).thenReturn(ID.toLong())
        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newAccount)!!))
                .andExpect(status().isCreated)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", equalTo(ID)))
        verify(accountService).create(any())
    }
}
