# My Budget - backend

Spring Boot-based REST web API backend for My Budget application.

## Data storage

MySQL 8.0 is used and expected to be running at `localhost:3306`, database named `mybudget` needs to exist, and an account that has access to it with username `mybudgetapp` and password `budgetApp2024`. These can be created with:

```SQL
CREATE DATABASE mybudget;
CREATE USER 'mybudgetapp'@'localhost' IDENTIFIED BY 'budgetApp2024';
```

## Running the backend

Backend can be started with:

```
mvn clean compile exec:java
```

And tests can be run with:

```
mvn test
```

## Requirements

- Java 17+
- Maven 3+

# My Budget - frontend

Budget App is a financial management application that allows users to track income and expenses related to their accounts which can all be in different currencies.

## Installation

To get started with My Budget frontend, you need to have Node.js and npm installed on your machine. Install dependecies with:

```
npm install
```

## Running the frontend

To run the project locally:

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`.

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
