package accounts.repository;

import accounts.model.Account;
import dependencies.DependencyFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMermoryAccountRepositoryTest {

    AccountRepository repository = DependencyFactory.getAccountRepository();

    @BeforeEach
    void setUp() {
        Account account = new Account("1","USD");
        InMermoryAccountRepository.INSTANCE.create(account);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void findByIdShoulFind() {
        Optional<Account> optional = repository.findById("1");
        assertTrue(optional.isPresent());
    }

    @Test
    void findByIdShoulNotFind() {
        Optional<Account> optional = repository.findById("FALSE");
        assertFalse(optional.isPresent());
    }

    @Test
    void ShouldUpdate() {
        Account account = repository.findById("1").get();

        account.addAmount(BigDecimal.ONE);

        repository.update(account);

        account = repository.findById("1").get();

        assertEquals(BigDecimal.ONE,account.getBalance());
    }

    @Test
    void ShouldNotUpdate() {
        Account account = new Account("ShouldNotUpdate","USD");

        repository.update(account);

        Optional<Account> optional = repository.findById("ShouldNotUpdate");

        assertFalse(optional.isPresent());
    }
}