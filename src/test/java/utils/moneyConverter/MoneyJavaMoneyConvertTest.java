package utils.moneyConverter;

import dependencies.DependencyFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class MoneyJavaMoneyConvertTest {

    MoneyConverter moneyConverter = DependencyFactory.getMoneyConverter();

    @Test
    void convert() {
        assertNotNull(moneyConverter.convert("USD","EUR", BigDecimal.ONE));
    }
}