# AccountSystem
Java Rest API for banking system, with support for transactions between accounts

Technology Stack
----------------
The API was developed and tested using the following libraries:
* Jetty
* Jersey
* JAX-RS
* H2 In-Memory Database
* Apache Commons DBUtils
* HK2 Dependency Injection Kernel
* JUnit
* Mockito
* RestAssured
* Maven exec plugin

Run the application
-------------------
There's 2 ways of starting the server:
1. Using the Maven command

To run using Maven, first of all make sure Maven is configured to work in the CLI and type the following commands:

~~~~~~~~~~~~~~~~
mvn compile
mvn exec:java 
~~~~~~~~~~~~~~~~

The compile command is necessary only once to build all of the files and compile the classes. After that, the execute command will only be necessary to run the server (unless changes are made to the code).

2. Using a Java IDE


Using either a Eclipse or IntelliJ, import the Maven project and run the main method in Application.java. This will start the application on port 8080.


Both methods will start the server, which will run a SQL setup file that will configure the database and some test data.

Run the tests
-------------
To run the tests, **make sure the application is running first**, then, in a CLI do:

~~~~~~~~~~~~
mvn test 
~~~~~~~~~~~~

This will run both unit and integration tests. However, the application **needs to be running** as RestAssured will need to hit the endpoints. Additionally, the tests can easily be run from a Java IDE.


Endpoints
---------

**1. User**
* **GET /user/get/{id}**
  * returns a JSON list containing user with the specified id
  * returns an empty list if no user exists for the given id

* **GET /user/get**
  * returns a JSON list with all existing users

* **POST /user/add**
  * adds a user to the database
  * expects a JSON User object with the following parameters and types:
  ~~~~~~~~~~~~~~~~~~~~~~~~
    {
      username: String,
      firstName: String,
      lastName: String 
    }
  ~~~~~~~~~~~~~~~~~~~~~~~~

**2. Account**
* **GET /account/get/{id}**
  * returns a JSON list containing the account with the specified id
  * returns an empty list if no account exists for the given id
  
* **GET /account/user/{id}**
  * returns a JSON list containing all accounts for the specified user id
  * returns an empty list if no accounts exists for the given user id

* **POST /account/add**
  * adds an account to the database
  * expects a JSON Account object with the following parameters and types:
  ~~~~~~~~~~~~~~~~~~~~
    {
      ownerId: Number,
      currency: String
    }
  ~~~~~~~~~~~~~~~~~~~~
  * the ownerId field needs to be a User id that has already been created, or the request will fail


**3. Transaction**
* **GET /transaction/get**
  * returns a JSON list containing all account transactions
  
* **GET /transaction/get/{id}**
  * returns a JSON list containing the transaction with the specified id
  * returns an empty list if the transaction is not found

* **POST /transaction/withdraw**
  * processes a withdrawal from an account
  * expects a JSON Withdrawal object with the following parameters and types:
  ~~~~~~~~~~~~~~~~~~~~~~~~
    {
      fromAccount: Number,
      amount: Number(at most two decimals),
      currency: String
      type: "Withdrawal",
    }
  ~~~~~~~~~~~~~~~~~~~~~~~~
  * the account field corresponds to the account ID for which the withdrawal is processed
  * the type String must be "Withdrawal" in this case
  * the cases when withdrawals fail are found below

* **POST /transaction/deposit**
  * processes a deposit from an account
  * expects a JSON Deposit object with the following parameters and types:
  ~~~~~~~~~~~~~~~~~~~~~~~~
    {
      toAccount: Number,
      amount: Number(at most two decimals),
      currency: String
      type: "Deposit",
    }
  ~~~~~~~~~~~~~~~~~~~~~~~~
  * the account field corresponds to the account ID for which the deposit is processed
  * the type String must be "Deposit" in this case
  * the cases when deposits fail are found below

* **POST /transaction/transfer**
  * processes a transfer from an account to another
  * expects a JSON Transfer object with the following parameters and types:
  ~~~~~~~~~~~~~~~~~~~~~~~~
    {
      fromAccount: Number,
      toAccount: Number,
      amount: Number(at most two decimals),
      currency: String
      type: "Transfer",
    }
  ~~~~~~~~~~~~~~~~~~~~~~~~
  * the account fields corresponds to the account IDs for which the transfer is processed
  * the type String must be "Deposit" in this case
  * the cases when deposits fail are found below

Transaction Rules
-----------------
* **Withdrawal**
  * amount cannot be negative or zero
  * account must have more funds than the transaction amount
  * the currency of the withdrawal must be the same as the one of the account from which the money is withdrawn
  * the amount may not contain more than 2 decimal places (e.g. 5.003)
  
* **Deposit**
  * amount cannot be negative or zero
  * the amount may not contain more than 2 decimal places (e.g. 5.003)
  * contrary to withdrawals, if the currency does not exist, one of the following happens:
    * an account in the deposit currency is found for the user of the initial transaction
    * an account with the deposit currency is created if the user does not already have one

* **Transfer**
  * transfer cannot be from an account to the same account (cyclic redundancy)
  * amount cannot be negative or zero
  * withdrawal account must have more funds than the transaction amount
  * the amount may not contain more than 2 decimal places (e.g. 5.003)
  * the currency of the transfer must be the same as the one of the account from which the money is withdrawn
  * for two accounts owned by the same user and with different currencies, transfer is not supported **(exchange between currencies is not supported)**
  * same as the deposit, if the receiving account does not have the transaction currency, on of the following happens:
    * an account in the deposit currency is found for the user of the initial transaction
    * an account with the deposit currency is created if the user does not already have one

Responses and status codes
--------------------------
All of the responses given by the query provide a helpful message that specifies what went wrong.
The status codes that can be returned are:
* **200 OK** - returns when a request was successful
* **400 Bad Request** - returns when the request cannot be processed correctly (e.g. when a provided ID doesn't exist in the database)
* **403 Forbidden** - returns when a main rule regarding transactions is broken (e.g. amount is negative)
* **500 Internal Server Error** - this is usually returned when the server cannot start or querries fail to execute

Examples of request body objects
--------------------------------
~~~~~~~~~~~~~~~~~~~~
User: {
  username: "emusk",
  firstName: "Elon",
  lastName: "Musk"
}

Account: {
  ownerId: 1,
  currency: "GBP"
}

Withdrawal: {
  "fromAccount": 1,
  "amount": 50,
  "currency": "GBP",
  "type": "Withdrawal"
}

Deposit: {
  "toAccount": 1,
  "amount": 1000,
  "currency": "GBP",
  "type": "Deposit"
}

Transfer: {
  "fromAccount": 1,
  "toAccount": 2,
  "amount": 10,
  "currency": "GBP",
  "type": "Transfer"
}
~~~~~~~~~~~~~~~~~~~~~~~~

Potential future improvements
-----------------------------
* implement logic for currency exchange using an external API
* implement support to only use existing currencies and not invalid, innexistent ones (possibly through an enum)
* add more functionality
