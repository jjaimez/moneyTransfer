package accounts.service;

import accounts.model.Account;
import accounts.repository.AccountRepository;
import exceptions.BadRequestException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    AccountRepository repositoryMock = mock(AccountRepository.class);

    AccountService accountService = new AccountService(repositoryMock);

    @Test
    void ShouldGetAccount() {
        Account account = new Account("1","USD");
        Optional<Account> optional = Optional.of(account);

        when(repositoryMock.findById("1")).thenReturn(optional);

        Account savedAccount = accountService.getAccount("1");

        assertEquals(savedAccount.getCurrency(),account.getCurrency());
    }


    @Test
    void ShouldGetAnException() {
        Optional<Account> optional = Optional.empty();

        when(repositoryMock.findById("1")).thenReturn(optional);

        assertThrows(BadRequestException.class, () -> accountService.getAccount("1"));
    }

    @Test
    void ShouldGetABadRequestExceptionFromGetAccount() {
        Optional<Account> optional = Optional.empty();

        when(repositoryMock.findById("1")).thenReturn(optional);

        assertThrows(BadRequestException.class, () -> accountService.getAccount("1"));
    }

    @Test
    void ShouldGetABadRequestExceptionFromSubtractAmount() {
        Account account = new Account("1","USD");
        assertThrows(BadRequestException.class, () -> accountService.subtractAmount(account,BigDecimal.ONE));
    }

    @Test
    void ShouldAddAmount() {
        Account account = new Account("1","USD");
        Account accountOne = new Account("1","USD");
        accountOne.addAmount(BigDecimal.ONE);

        when(repositoryMock.update(account)).thenReturn(accountOne);

        Account updatedAccount = accountService.addAmount(account, BigDecimal.ONE);

        assertEquals(updatedAccount,accountOne);
    }


    @Test
    void ShouldSubtractAmount() {
        Account account = new Account("1","USD");
        Account accountOne = new Account("1","USD");
        accountOne.addAmount(BigDecimal.ONE);

        when(repositoryMock.update(accountOne)).thenReturn(account);

        Account updatedAccount = accountService.subtractAmount(accountOne, BigDecimal.ONE);

        assertEquals(updatedAccount,account);
    }

}
