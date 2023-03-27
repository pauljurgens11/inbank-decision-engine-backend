# Inbank Decision Engine Backend

This application can calculate a suitable loan depending on your request!

It takes in some parameters (personal code, loan amount, loan period) and checks whether it's possible to give the client the loan he/she requests. If not, then this application also attempts to offer an alternative option to the client. The response gets sent back to the device that made the initial request. Pretty cool!

## Usage

**Requirements: Java 17, Gradle**

This application needs to be active in order to make requests from the frontend.

To start the application you need to run the ```DecisionEngineApplication.java``` file. Now POST requests can be made to the address of ```localhost:8080/api/engine```!

## Documentation



## License

[MIT](https://choosealicense.com/licenses/mit/)