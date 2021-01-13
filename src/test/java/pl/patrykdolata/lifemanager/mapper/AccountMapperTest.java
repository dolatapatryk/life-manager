package pl.patrykdolata.lifemanager.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.patrykdolata.lifemanager.TestUtils;
import pl.patrykdolata.lifemanager.domain.AccountEntity;
import pl.patrykdolata.lifemanager.model.NewAccount;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountMapperTest {

    @Autowired
    private AccountMapper mapper;

    @BeforeEach
    public void init() {
        TestUtils.withProperPrincipal();
    }

    @Test
    void toEntity_whenNullNewAccountModel_expectNull() {
        NewAccount account = null;
        AccountEntity result = mapper.toEntity(account);

        assertNull(result);
    }

    @Test
    void toEntity_whenProperNewAccountModel_expectProperEntity() {
        NewAccount account = new NewAccount("acc", new BigDecimal("12.5"));
        AccountEntity result = mapper.toEntity(account);

        assertNotNull(result);
        assertThat(result.getName(), is(account.getName()));
        assertThat(result.getBalance(), is(account.getStartBalance()));
        assertThat(result.getUser().getId(), is(TestUtils.ID));
    }
}
