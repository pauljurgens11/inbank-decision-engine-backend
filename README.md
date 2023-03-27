# Inbank Decision Engine

This application can calculate a suitable loan depending on your request!

The back-end uses a REST API to analyse a client's loan request and respond with a suitable response. The response depends on the client's entered loan amount, period and their credit modifier.

**NB! The last 3 digits of a client's personal code determine their credit modifier:**
* 000-199: unknown credit modifier
* 200-399: client in debt
* 400-599: 100 credit modifier
* 600-799: 300 credit modifier
* 800-999: 1000 credit modifier

## Requirements

* For running the back-end:
  * Java 17
  * Gradle
* For running the front-end:
  * Flutter 3.3.10

## Usage

1. Clone the ["inbank-decision-engine-backend"](https://github.com/pauljurgens11/inbank-decision-engine-backend) repository to your device.
2. Navigate to the root folder.
3. Run the command ```gradle build```.
4. Run the command ```java -jar build/libs/decision-engine-1.0.jar```. 
   * The server should now be running, as indicated by the console.
   * Alternatevly, you can open the project in the IDE of your choice and execute ```DecisionEngineApplication.java```.


5. Clone the ["inbank-decision-engine-frontend"](https://github.com/pauljurgens11/inbank-decision-engine-frontend) repository to your device.
6. Navigate to the root folder
7. Run the command ```flutter run --release```.
8. Enjoy!


## Documentation

<details>
  <summary>Back-end documentation:</summary>

#### Technologies used:
 * Java 17
 * Spring

#### API
The application has a POST endpoint ```/api/engine```.

Request example:
```json
{
  "personalCode": "99999999999",
  "loanAmount": "3000",
  "loanPeriod": "60"
}
```

Requests made to this endpoint should contain these JSON fields:
* personalcode - string, 11 digits (the client's personal code)
* loanamount - string, amount that the client wants to loan (EUR)
* loanperiod - string, period that the client wants the loan to be in (months)

Response example:
```json
{
  "response": true,
  "loanAmount": "3000",
  "loanPeriod": "60",
  "message": "Success! We can offer you this loan:"
}
```

Responses by the server contain these JSON fields:
* response - boolean, indicates whether a suitable loan could be found
* loanamount - string, amount that the service can loan to the client (EUR)
* loanperiod - string, period that the service can offer to the client (months)
* message - string, message giving info about the response and potential errors

If the client can not apply for a loan for any reason, then info about the reason can be found in the "message" field of the response. The response always has a status code of 200.

#### Project structure
* Project can be executed by running ```DecisionEngineApplication.java```.
* All API related logic, including the response and request classes, can be found in the "api" directory.
* Business logic can be found in the "service" directory.
  * ```DecisionEngine.java``` receives the request parameters and sends back a response.
  * ```LoanCalculator.java``` is where calculating the client's credit score happens.

</details>
<p></p>
<details>
  <summary>Front-end documentation:</summary>

#### Technologies used:
* Flutter 3.3.10

#### Project structure
* Project can be executed by running ```main.dart```.
* All separate screens can be found in the "pages" directory.
* The API request is sent and received in the ```loan_page_form.dart``` file.

</details>

## Contributors

The application was developed by Paul Jürgens.

## License

[MIT](https://choosealicense.com/licenses/mit/)
