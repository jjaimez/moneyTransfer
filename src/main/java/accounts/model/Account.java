package accounts.model;

import java.math.BigDecimal;

public class Account {

    private final String id;
    private BigDecimal balance = BigDecimal.ZERO;
    private final String currency;

    public Account(String id, String currency) {
        this.id = id;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public void addAmount(BigDecimal amount) {
        balance = balance.add(amount);
    }

    public void subtractAmount(BigDecimal amount) {
        balance = balance.subtract(amount);
    }

}
