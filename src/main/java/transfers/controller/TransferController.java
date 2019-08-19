package transfers.controller;


import dependencies.DependencyFactory;
import exceptions.BadRequestException;
import exceptions.DoubleRequestException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import transfers.model.Transfer;
import transfers.requests.CreateTransferRequest;
import transfers.service.CreateTransferService;
import transfers.validators.CreateAccountRequestValidator;
import utils.jsonParser.JsonParser;
import utils.keyLock.KeyLock;


public class TransferController {

    static JsonParser jsonParser = DependencyFactory.getJsonParser();
    static CreateTransferService createTransferService = new CreateTransferService(DependencyFactory.getTransferRepository(), DependencyFactory.getAccountService(), DependencyFactory.getIdGenerator(), DependencyFactory.getMoneyConverter());
    static KeyLock keyLock = DependencyFactory.getKeyLock();


    public static Object createTransfer(Request request, Response response) {
        CreateTransferRequest createTransferRequest = jsonParser.fromJson(request.body(), CreateTransferRequest.class);

        if (!CreateAccountRequestValidator.INSTACE.isValid(createTransferRequest)) {
            throw new BadRequestException();
        }

        try {
            if (!keyLock.lock(createTransferRequest.getFromId())) {
                throw new DoubleRequestException();
            }

            if (!keyLock.lock(createTransferRequest.getToId())) {
                keyLock.unlock(createTransferRequest.getFromId());
                throw new DoubleRequestException();
            }

            Transfer transfer = createTransferService.create(createTransferRequest.getFromId(), createTransferRequest.getToId(), createTransferRequest.getAmount(), createTransferRequest.getCurrency());

            response.status(HttpStatus.CREATED_201);
            return jsonParser.toJson(transfer);

        } finally {
            keyLock.unlock(createTransferRequest.getFromId());
            keyLock.unlock(createTransferRequest.getToId());
        }

    }


}
