# moneyTransfer

Java RESTful API for money transfers between accounts.

* Multi-currency supported. You can transfer money between two accounts with deferent money if and only if you post the transfer using one of the two currencies. If you post using a currency that neither of the accounts manages, the request will fail. 

**Create transfer**
----
  Returns json data about the transfer.

**URL** : `/transfer`

**Method** : `POST`
  
**Body**
 
 All fields must be sent.
    
``` json
  {
      "from_id": "source account id",
      "to_id": "receiver account id",
      "amount": "amount to be transfer",
      "currency": "currency" 
  }
```
**Data example** 
    
```json
  {   	
      "from_id": "10",
      "to_id": "13",
      "amount": 1,
      "currency": "USD" 
  }
```

**Success Response:**

**Code** : `201 CREATED`

**Body:** 
```json
    {
        "id": "e5ccd3fd-b8a5-4a03-975a-26daacc5e3ee",
        "from_id": "10",
        "to_id": "13",
        "sourced_amount": 1.15,
        "received_amount": 1.038280967858432685,
        "currency": "USD"
    }
```



**Run**
----

1) Unzip moneyTransfer-1.0.zip
2) cd moneyTransfer-1.0
3) java -jar moneyTransfer-1.0.jar



**Bostraped accounts**
----
```
  AccountId: "10"
  Balance account: 10
  Currency: "USD"

  AccountId: "11"
  Balance account: 0
  Currency: "USD"

  AccountId: "12"
  Balance account: 0
  Currency: "EUR"

  AccountId: "13"
  Balance account: 10
  Currency: "EUR"
```


**Test**
----

![alt text](https://github.com/jjaimez/moneyTransfer/blob/master/img/coverage.png)

