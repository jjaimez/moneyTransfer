package transfers.service;

import accounts.model.Account;
import accounts.service.AccountService;
import exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import transfers.model.Transfer;
import transfers.repository.TransferRepository;
import utils.IdGenerator;
import utils.moneyConverter.MoneyConverter;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateTransferServiceTest {


    IdGenerator idGenerator = mock(IdGenerator.class);
   TransferRepository transferRepository = mock(TransferRepository.class);
    MoneyConverter moneyConverter = mock(MoneyConverter.class);
   AccountService accountService = mock(AccountService.class);


    CreateTransferService createTransferService = new CreateTransferService(transferRepository,accountService,idGenerator,moneyConverter);

    @Test
    void ShouldThrowBadRequestException(){
        Account accountOne = new Account("1","USD");
        Account accountTwo = new Account("2","EUR");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        assertThrows(BadRequestException.class, () ->  createTransferService.create("1","2",BigDecimal.ONE, "AAA"));

    }


    @Test
    void ShouldCreateTransferenceWithMoneyConversionTo(){
        Account accountOne = new Account("1","USD");
        Account accountTwo = new Account("2","EUR");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        when(moneyConverter.convert("USD","EUR",BigDecimal.ONE)).thenReturn(BigDecimal.TEN);

        when(accountService.subtractAmount(accountOne,BigDecimal.ONE)).thenReturn(accountOne);
        when(accountService.addAmount(accountTwo, BigDecimal.TEN)).thenReturn(accountTwo);

        when(idGenerator.generate()).thenReturn("3");
        Optional<Transfer> optional =  Optional.empty();
        when(transferRepository.findById("3")).thenReturn(optional);

        Transfer transfer = new Transfer("3", "1", "2", "USD", BigDecimal.ONE, BigDecimal.TEN);
        when(transferRepository.create(transfer)).thenReturn(transfer);

        Transfer savedTransfer = createTransferService.create("1","2",BigDecimal.ONE, "USD");

        assertEquals(transfer,savedTransfer);
    }


    @Test
    void ShouldCreateTransferenceWithMoneyConversionFrom(){
        Account accountOne = new Account("1","EUR");
        Account accountTwo = new Account("2","USD");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        when(moneyConverter.convert("USD","EUR",BigDecimal.ONE)).thenReturn(BigDecimal.TEN);

        when(accountService.subtractAmount(accountOne,BigDecimal.TEN)).thenReturn(accountOne);
        when(accountService.addAmount(accountTwo, BigDecimal.ONE)).thenReturn(accountTwo);

        when(idGenerator.generate()).thenReturn("3");
        Optional<Transfer> optional =  Optional.empty();
        when(transferRepository.findById("3")).thenReturn(optional);

        Transfer transfer = new Transfer("3", "1", "2", "USD", BigDecimal.TEN, BigDecimal.ONE);
        when(transferRepository.create(transfer)).thenReturn(transfer);

        Transfer savedTransfer = createTransferService.create("1","2",BigDecimal.ONE, "USD");

        assertEquals(transfer,savedTransfer);
    }

    @Test
    void ShouldCreateTransferenceWithoutMoneyConversion(){
        Account accountOne = new Account("1","USD");
        Account accountTwo = new Account("2","USD");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        when(accountService.subtractAmount(accountOne,BigDecimal.ONE)).thenReturn(accountOne);
        when(accountService.addAmount(accountTwo, BigDecimal.TEN)).thenReturn(accountTwo);

        when(idGenerator.generate()).thenReturn("3");
        Optional<Transfer> optional =  Optional.empty();
        when(transferRepository.findById("3")).thenReturn(optional);

        Transfer transfer = new Transfer("3", "1", "2", "USD", BigDecimal.ONE, BigDecimal.ONE);
        when(transferRepository.create(transfer)).thenReturn(transfer);

        Transfer savedTransfer = createTransferService.create("1","2",BigDecimal.ONE, "USD");

        assertEquals(transfer,savedTransfer);
    }


    @Test
    void ShouldAccountServiceThrowBadRequestException(){
        Account accountOne = new Account("1","USD");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("3")).thenThrow(new BadRequestException());

        assertThrows(BadRequestException.class, () ->  createTransferService.create("1","3",BigDecimal.ONE, "USD"));

    }


    @Test
    void ShouldSubtractionThrowBadRequestException(){
        Account accountOne = new Account("1","USD");
        Account accountTwo = new Account("2","USD");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        when(accountService.subtractAmount(accountOne,BigDecimal.ONE)).thenThrow(new BadRequestException());

        assertThrows(BadRequestException.class, () ->  createTransferService.create("1","2",BigDecimal.ONE, "USD"));

    }

    @Test
    void ShouldCreateTransferenceGeneratingTwoIds(){
        Account accountOne = new Account("1","USD");
        Account accountTwo = new Account("2","USD");

        when(accountService.getAccount("1")).thenReturn(accountOne);
        when(accountService.getAccount("2")).thenReturn(accountTwo);

        when(accountService.subtractAmount(accountOne,BigDecimal.ONE)).thenReturn(accountOne);
        when(accountService.addAmount(accountTwo, BigDecimal.TEN)).thenReturn(accountTwo);

        when(idGenerator.generate()).thenReturn("1");
        Optional<Transfer> optional =  Optional.of( new Transfer("1", "1", "2", "USD", BigDecimal.ONE, BigDecimal.ONE));
        when(transferRepository.findById("1")).thenReturn(optional);

        when(idGenerator.generate()).thenReturn("3");
        Optional<Transfer> emptyOptional =  Optional.empty();
        when(transferRepository.findById("3")).thenReturn(emptyOptional);

        Transfer transfer = new Transfer("3", "1", "2", "USD", BigDecimal.ONE, BigDecimal.ONE);
        when(transferRepository.create(transfer)).thenReturn(transfer);

        Transfer savedTransfer = createTransferService.create("1","2",BigDecimal.ONE, "USD");

        assertEquals(transfer,savedTransfer);
    }

}