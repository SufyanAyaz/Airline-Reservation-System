# Airline-Reservation-System
An airline reservation system tailored to a single airline company

How to Run:

First build the database in MySQL Workbench using the sql script provided.

Proceed to the "AirlineReservationSystemApplication" directory through your command line. Once in this directory, run the following command to compile the code:

javac -cp ;./mysql-connector-j-8.2.0.jar;lib/javax.mail.jar;lib/activation.jar src/Boundaries/*.java src/Controller/*.java src/Controller/Accounts/*.java src/Entities/*.java

After running this command, you can run the code itself by entering "src" directory and then entering the following command in the command line:

java -cp ;../lib/mysql-connector-j-8.2.0.jar;../lib/javax.mail.jar;../lib/activation.jar Boundaries.startingView