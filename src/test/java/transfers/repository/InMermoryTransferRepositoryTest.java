package transfers.repository;

import dependencies.DependencyFactory;
import org.junit.jupiter.api.Test;
import transfers.model.Transfer;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMermoryTransferRepositoryTest {

    TransferRepository repository = DependencyFactory.getTransferRepository();

    @Test
    void findByIdShoulFind() {
        Transfer transfer = new Transfer("1", "fromId", "toId", "USD", BigDecimal.ONE, BigDecimal.ONE);
        repository.create(transfer);
        Optional<Transfer> optional = repository.findById("1");
        assertTrue(optional.isPresent());
    }


    @Test
    void findByIdShoulNotFind() {
        Optional<Transfer> optional = repository.findById("FALSE");
        assertFalse(optional.isPresent());
    }

}