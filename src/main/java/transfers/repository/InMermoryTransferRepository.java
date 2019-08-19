package transfers.repository;


import transfers.model.Transfer;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public enum  InMermoryTransferRepository implements TransferRepository {
    INSTANCE;


    private final Map<String, Transfer> transfers = new ConcurrentHashMap<>();

    @Override
    public Transfer create(Transfer transfer) {
       transfers.put(transfer.getId(),transfer);
        return transfer;
    }

    @Override
    public Optional<Transfer> findById(String id) {
        return Optional.ofNullable(transfers.get(id));
    }

}
