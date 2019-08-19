package accounts.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void ShouldCreateAccountInZero() {
        Account account = new Account("1","USD");
        assertEquals(account.getBalance(), BigDecimal.ZERO);
    }

    @Test
    void idShouldBeEqual() {
        Account account = new Account("1","USD");
        assertEquals(account.getId(),"1");
    }

    @Test
    void currencyShouldBeEqual() {
        Account account = new Account("1","USD");
        assertEquals(account.getCurrency(),"USD");
    }

    @Test
    void ShouldAddAmount() {
        Account account = new Account("1","USD");
        account.addAmount(BigDecimal.ONE);
        assertEquals(account.getBalance(), BigDecimal.ONE);
    }

    @Test
    void ShouldAddNegativeAmount() {
        Account account = new Account("1","USD");
        account.addAmount(BigDecimal.ONE.negate());
        assertEquals(account.getBalance(), BigDecimal.ONE.negate());
    }

    @Test
    void ShouldSubtractAmount() {
        Account account = new Account("1","USD");
        account.addAmount(BigDecimal.ONE);
        account.addAmount(BigDecimal.ONE);
        account.subtractAmount(BigDecimal.ONE);
        assertEquals(account.getBalance(), BigDecimal.ONE);
    }
}