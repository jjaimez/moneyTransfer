package transfers.validators;

import org.junit.jupiter.api.Test;
import transfers.requests.CreateTransferRequest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CreateAccountRequestValidatorTest {


    @Test
    void ShuldBeValid() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("fromId", "toId", BigDecimal.TEN, "USD");
        assertTrue(CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }


    @Test
    void ShuldBeInvalidAmount() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("fromId", "toId", BigDecimal.ZERO, "USD");
        assertFalse(CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }

    @Test
    void ShuldBeInvalidEqualId() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("1", "1", BigDecimal.ONE, "USD");
        assertFalse(CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }


    @Test
    void ShuldBeInvalidEmptyId() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("", "toId", BigDecimal.TEN, "USD");
        assertFalse( CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }
    @Test
    void ShuldBeInvalidCurrency() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("fromId", "toId", BigDecimal.TEN, "FAKE CURRENCY");
        assertFalse( CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }


    @Test
    void ShuldBeInvalidNullAmount() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("fromId", "toId", null, "USD");
        assertFalse(CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }

    @Test
    void ShuldBeInvalidNullId() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest(null, "1", BigDecimal.ONE, "USD");
        assertFalse(CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }

    @Test
    void ShuldBeInvalidNullCurrency() {
        CreateTransferRequest createTransferRequest = new CreateTransferRequest("fromId", "toId", BigDecimal.TEN, null);
        assertFalse( CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest));
    }
}