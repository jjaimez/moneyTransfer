package transfers.repository;

import transfers.model.Transfer;

import java.util.Optional;

public interface TransferRepository {

    Transfer create(Transfer account);

    Optional<Transfer> findById(String id);

}