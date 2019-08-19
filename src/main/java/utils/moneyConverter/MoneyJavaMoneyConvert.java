package utils.moneyConverter;

import org.javamoney.moneta.Money;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.ExchangeRateProvider;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;

public enum  MoneyJavaMoneyConvert implements MoneyConverter {
    INSTANCE;

    @Override
    public BigDecimal convert(String currencyFrom, String currencyTo, BigDecimal amount) {

        
        MonetaryAmount fromMoney = Monetary
                .getAmountFactory(Money.class)
                .setCurrency(currencyFrom)
                .setNumber(amount)
                .create();

        ExchangeRateProvider rateProvider = MonetaryConversions.getExchangeRateProvider("ECB", "IMF");
        CurrencyConversion conversion = rateProvider.getCurrencyConversion(currencyTo);

        MonetaryAmount convertedAmount = fromMoney.with(conversion);
        return convertedAmount.getNumber().numberValue(BigDecimal.class);

    }
}
