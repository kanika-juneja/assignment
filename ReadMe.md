# ME Assignment

The goal of this project is to analyse financial transactions records received from the CSV file and display the relative account balance and the number of transactions included.

## Design

1) Created a Maven project.
2) Included the following dependencies in pom.xml -
```sh 
a) opencsv(4.6) - For csv related functionalities.
b) slf4j-api(1.7.25) - For logging
c) slf4j-simple(1.7.25) - For logging
d) maven-compiler-plugin(3.2) - For defining the java compiler version. 
   Currently, 1.8 is defined that can be changed according to the requirement.
e) exec-maven-plugin(1.6.0) - To run java app from the terminal.
```
3) Created 4 packages - 
```sh 
a) com.meProject - Contains CalculateRelativeAccountBalance class that is the main class.
b) com.meProject.constants - Contains MeConstants class that consists of constant values.
c) com.meProject.domain - Contains Transaction class that is the bean.
d) com.meProject.util - Contains DateUtil that consists of date utility methods.
```
4) Transactions.csv file is created with some sample data and is placed in the resources folder for now. Location and path can be changed as per requirement. The data in the file can be changed for testing.

5) Error trace is also printed in case of some error which can be removed as required.

## Build/Run

Go to the project directory and run the following commands for build and run - 

#Build
```sh 
mvn clean install
```
#Run
```sh 
mvn exec:java
```
After the code is run, it will ask for 3 inputs one by one as below- 
1) Enter the account number for which the relative amount is to be calculated:
2) Enter the fromDate in dd/MM/yyyy HH:mm:ss format :
3) Enter the toDate in dd/MM/yyyy HH:mm:ss format :


# Output
The output of the application will be displayed in console as below, where XX.XX N are amount is Dollars and Number of transactions considered respectively.

1. Relative balance for the period is : ±$XX.XX
2. Number of transactions included is : N



