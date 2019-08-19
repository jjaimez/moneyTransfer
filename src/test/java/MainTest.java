import accounts.model.Account;
import accounts.repository.InMermoryAccountRepository;
import dependencies.DependencyFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import router.Router;
import spark.Spark;
import utils.keyLock.KeyLock;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    final int PORT = 8080;
    final String URI = "http://localhost:"+PORT+"/transfer";

    @BeforeEach
    void setUp(){
        Spark.port(PORT);
        new Router().init();
        Spark.awaitInitialization();

        Account a = new Account("1","USD");
        InMermoryAccountRepository.INSTANCE.create(a);
        DependencyFactory.getAccountService().addAmount(a, BigDecimal.TEN);

        a = new Account("2","USD");
        InMermoryAccountRepository.INSTANCE.create(a);

        a = new Account("3","EUR");
        InMermoryAccountRepository.INSTANCE.create(a);
    }

    @AfterEach
    void tearDown() {
        InMermoryAccountRepository.INSTANCE.delete("1");
        InMermoryAccountRepository.INSTANCE.delete("2");
        InMermoryAccountRepository.INSTANCE.delete("3");

        Spark.stop();
        Spark.awaitStop();
    }

    @Test
    void ShouldGet404() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:"+PORT+"/dasdsa");
        HttpResponse response = client.execute(request);
        assertEquals(404, response.getStatusLine().getStatusCode());
        client.close();
    }


    @Test
    void ShouldPostATransfer() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldTransferOneUSD() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
        assertEquals(DependencyFactory.getAccountService().getAccount("1").getBalance(),new BigDecimal(9));
        assertEquals(DependencyFactory.getAccountService().getAccount("2").getBalance(),BigDecimal.ONE);

        client.close();
    }

    @Test
    void ShouldGetDoubleRequest() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        KeyLock k = DependencyFactory.getKeyLock();
        k.lock("1");

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(423, response.getStatusLine().getStatusCode());

        k.unlock("1");

        client.close();
    }

    @Test
    void ShouldGetDoubleRequestAccount2() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        KeyLock k = DependencyFactory.getKeyLock();
        k.lock("2");

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(423, response.getStatusLine().getStatusCode());

        k.unlock("2");

        client.close();
    }

    @Test
    void ShouldGetBadRequestBadJson() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost request = new HttpPost("http://localhost:8080/transfer");

        String json = "{to_id':'2','amount':1,'currency':'USD'}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldGetBadRequestSameId() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("2","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldGetBadRequestInvalidAmount() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ZERO,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }


    @Test
    void ShouldGetBadRequestInvalidCurrency() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"DADAD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldGetBadRequestInsufficientAmount() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",new BigDecimal(11),"EUR");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldGetBadRequestNotCommonCurrency() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"EUR");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }

    @Test
    void ShouldGetBadRequestInvalidAccount() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("14","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(400, response.getStatusLine().getStatusCode());
        client.close();
    }


    @Test
    void ShouldTransferDoingMoneyConvertion() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","3",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        assertEquals(DependencyFactory.getAccountService().getAccount("1").getBalance(),new BigDecimal(9));
        assertEquals(DependencyFactory.getAccountService().getAccount("3").getBalance().compareTo(BigDecimal.ZERO),1);

        client.close();
    }



    @Test
    void ShouldTransfer3TimesDoingMoneyConvertion() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost request =  getRequest("1","2",BigDecimal.ONE,"USD");

        CloseableHttpResponse response = client.execute(request);
        client.close();

        client = HttpClients.createDefault();
        request = getRequest("2","3",BigDecimal.ONE,"USD");
        response = client.execute(request);
        client.close();

        client = HttpClients.createDefault();
        request = getRequest("3","1",BigDecimal.ONE,"USD");
        response = client.execute(request);

        assertEquals(DependencyFactory.getAccountService().getAccount("1").getBalance(),BigDecimal.TEN);
        assertEquals(DependencyFactory.getAccountService().getAccount("2").getBalance(),BigDecimal.ZERO);
        assertEquals(DependencyFactory.getAccountService().getAccount("3").getBalance().compareTo(BigDecimal.ZERO),0);

        client.close();
    }




    private HttpPost getRequest(String fromId, String toId, BigDecimal amount, String currency) throws UnsupportedEncodingException {
        HttpPost request = new HttpPost(URI);
        String json = "{'from_id':'"+fromId+"','to_id':'"+toId+"','amount':"+amount.toString()+",'currency':'"+currency+"'}";
        StringEntity entity = new StringEntity(json);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
        return request;
    }


}