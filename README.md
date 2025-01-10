# Currency conversion
## Description

This is a simple spring boot application for currency conversion using public api

## Getting Started

### Dependencies

* Java 11 or higher
* Maven 3.6+ (for dependency management)
* IDE (Eclipse, intellij or springsuite) or terminal for running the application

## API endpoints
### 1. GET `/api/rates`
Used to fetch rates, default is USD.
eg: 
```
localhost:8080/api/rates?base=USD
```
### 2. POST `/api/convert`
Used for conversion of the currency
example post body:
```
{
"from": "USD",
"to": "EUR",
"amount": 100
}
```
example response body:
```
{
    "from": "USD",
    "to": "EUR",
    "convertedAmount": 97.08701057,
    "amount": 100.0
}
```

### Installing

* Clone the repository
```
git clone https://github.com/dhanush-kuma/convert.git
cd convert
```
* Create .env file in root directory, and add these lines with your public url and key
```
API_URL= Your_Api_url
API_KEY=Your_Api_Key
```
* Depending upon your client change the `url` structure in the service class, current client: `freecurrencyapi`
```
 String url = API_URL +"?apikey=" + API_KEY + "&base_currency="+ baseCurrency;
```

### Executing program

* Build project with maven
```
mvn clean install
```
* Run spring boot application
```
mvn spring-boot:run
```
* Alternatively if using IDE right click and run `ConvertApplication.java`