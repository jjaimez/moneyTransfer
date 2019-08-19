package utils.moneyConverter;

import java.math.BigDecimal;

public interface MoneyConverter {

    BigDecimal convert(String currencyFrom, String currencyTo, BigDecimal amount);
}
