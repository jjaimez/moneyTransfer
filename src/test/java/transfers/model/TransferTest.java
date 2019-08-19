package transfers.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransferTest {

    @Test
    void idShouldBe1() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertEquals(transfer.getId(), "1");
    }

    @Test
    void fromIdShouldBe1() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertEquals(transfer.getFromId(), "1");
    }

    @Test
    void toIdShouldBe1() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertEquals(transfer.getToId(), "1");
    }

    @Test
    void currencyShouldBeUSD() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertEquals(transfer.getCurrency(), "USD");
    }


    @Test
    void sourcedAmountShouldBe1() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertEquals(transfer.getSourcedAmount(), BigDecimal.ONE);
    }

    @Test
    void ShouldBeNotEqual() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertFalse(transfer.equals(null));
    }

    @Test
    void ShouldBeNotEqualByValues() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        Transfer transfer2 = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ZERO);
        assertFalse(transfer.equals(transfer2));
    }

    @Test
    void ShouldBeEqualByReference() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertTrue(transfer.equals(transfer));
    }

    @Test
    void ShouldBeEqualByValues() {
        Transfer transfer = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        Transfer transfer2 = new Transfer("1","1","1","USD",BigDecimal.ONE,BigDecimal.ONE);
        assertTrue(transfer.equals(transfer2));
    }



}