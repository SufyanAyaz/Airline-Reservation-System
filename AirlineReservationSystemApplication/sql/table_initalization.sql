DROP DATABASE IF EXISTS TESTAIRLINE;
CREATE DATABASE TESTAIRLINE;
USE TESTAIRLINE;
DROP USER IF EXISTS 'airline_client' @'localhost';
CREATE USER 'airline_client' @'localhost' IDENTIFIED BY 'securePassword69';
GRANT INSERT,
    UPDATE,
    DELETE,
    SELECT ON AIRLINE.* TO 'airline_client' @'localhost';
CREATE TABLE AIRCRAFT (
    Manufacturer VARCHAR(30) NOT NULL,
    Model VARCHAR(30) NOT NULL,
    Ordinary_Capacity INT,
    Comfort_Capacity INT,
    Business_Capacity INT,
    PRIMARY KEY (Model)
);
CREATE TABLE AIRPORT_LOCATION (
    Airport_Name VARCHAR(30) NOT NULL,
    Airport_Code VARCHAR(10) NOT NULL,
    PRIMARY KEY (Airport_Code)
);
CREATE TABLE LOGIN_INFO (
    Email VARCHAR(50) NOT NULL,
    Passkey VARCHAR(40) NOT NULL,
    DynSalt VARCHAR(10) NOT NULL,
    Login_Role VARCHAR(10) NOT NULL DEFAULT 'USER',
    PRIMARY KEY (Email)
);
CREATE TABLE AGENTS (
    First_Name VARCHAR(30) NOT NULL,
    Last_Name VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    ID INT NOT NULL,
    Company_Name VARCHAR(30),
    PRIMARY KEY (ID),
    FOREIGN KEY (Email) REFERENCES LOGIN_INFO(Email)
);
CREATE TABLE CREW_MEMBER (
    ID INT NOT NULL,
    First_Name VARCHAR(30) NOT NULL,
    Last_Name VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (Email) REFERENCES LOGIN_INFO(Email) ON DELETE cascade
);
DROP TABLE IF EXISTS CREDITCARD;
CREATE TABLE CREDITCARD (
    Card_Number BIGINT NOT NULL,
    Expiry INT NOT NULL,
    CVV INT NOT NULL,
    Balance FLOAT NOT NULL,
    PRIMARY KEY (Card_Number)
);
CREATE TABLE REGISTERED_USER (
    First_Name VARCHAR(30) NOT NULL,
    Last_Name VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    User_Address VARCHAR(30) NOT NULL,
    Postal_Code VARCHAR(10) NOT NULL,
    CreditCard bigint DEFAULT NULL,
    PRIMARY KEY (Email),
    FOREIGN KEY (Email) REFERENCES LOGIN_INFO(Email),
    FOREIGN KEY (CreditCard) REFERENCES CREDITCARD(Card_Number)
);
CREATE TABLE FLIGHT (
    Flight_Number INT AUTO_INCREMENT,
    Model VARCHAR(30) NOT NULL,
    Origin VARCHAR(10) NOT NULL,
    Destination VARCHAR(10) NOT NULL,
    Departure VARCHAR(20) NOT NULL,
    PRIMARY KEY (Flight_Number),
    FOREIGN KEY (Model) REFERENCES AIRCRAFT(Model)
);
CREATE TABLE CREW_ASSIGNMENTS (
    Crew_id INT NOT NULL,
    Flight_num INT NOT NULL,
    Role VARCHAR(30) NOT NULL,
    PRIMARY KEY (Crew_id, Flight_num),
    FOREIGN KEY (Crew_id) REFERENCES CREW_MEMBER(ID),
    FOREIGN KEY (Flight_num) REFERENCES FLIGHT(Flight_Number)
);
CREATE TABLE TICKET (
    Ticket_Num INT NOT NULL,
    Flight_Num INT NOT NULL,
    Seat_Row VARCHAR(5) NOT NULL,
    Seat_Num INT NOT NULL,
    Seat_Type VARCHAR(10) NOT NULL,
    Customer VARCHAR(50) NOT NULL,
    PRIMARY KEY (Ticket_Num),
    FOREIGN KEY (Flight_Num) REFERENCES FLIGHT(Flight_Number),
    FOREIGN KEY (Customer) REFERENCES LOGIN_INFO(Email)
);
-- Insert values into AIRCRAFT table
INSERT INTO AIRCRAFT (
        Manufacturer,
        Model,
        Ordinary_Capacity,
        Comfort_Capacity,
        Business_Capacity
    )
VALUES ('Boeing', '747', 20, 15, 5),
    ('Airbus', 'A320', 40, 25, 15),
    ('Embraer', 'E190', 30, 20, 10);
-- Insert values into AIRPORT_LOCATION table
INSERT INTO AIRPORT_LOCATION (Airport_Name, Airport_Code)
VALUES ('J F. K Airport', 'JFK'),
    ('LA Airport', 'LAX'),
    ('London Heathrow Airport', 'LHR');
-- Insert values into LOGIN_INFO table
INSERT INTO LOGIN_INFO (Email, Passkey, DynSalt, Login_Role)
VALUES (
        'user1@example.com',
        'hashed_password1',
        'salt1',
        'USER'
    ),
    (
        'user2@example.com',
        'hashed_password5',
        'salt5',
        'TOURIST'
    ),
    (
        'agent1@example.com',
        'hashed_password2',
        'salt2',
        'AGENT'
    ),
    (
        'crew2@example.com',
        'hashed_password4',
        'salt4',
        'CREW'
    ),
    (
        'crew1@example.com',
        'hashed_password3',
        'salt3',
        'CREW'
    ),
    (
        'admin1@example.com',
        'hashed_password6',
        'salt6',
        'ADMIN'
    );
-- Insert values into AGENTS table
INSERT INTO AGENTS (First_Name, Last_Name, Email, ID, Company_Name)
VALUES (
        'Agent',
        'One',
        'agent1@example.com',
        1,
        'ABC Airlines'
    );
-- Insert values into CREW_MEMBER table
INSERT INTO CREW_MEMBER (ID, First_Name, Last_Name, Email)
VALUES (1, 'Crew', 'Member1', 'crew1@example.com'),
    (2, 'Crew', 'Member2', 'crew2@example.com');
-- Insert values into CREDITCARD table
INSERT INTO CREDITCARD (Card_Number, Expiry, CVV, Balance)
VALUES (1234567890123456, 1223, 123, 5000.00),
    (9876543210987654, 0522, 456, 3000.00);
-- Insert values into REGISTERED_USER table
INSERT INTO REGISTERED_USER (
        First_Name,
        Last_Name,
        Email,
        User_Address,
        Postal_Code,
        CreditCard
    )
VALUES (
        'John',
        'Doe',
        'user1@example.com',
        '123 Main St',
        '12345',
        1234567890123456
    ),
    (
        'Alice',
        'Smith',
        'user2@example.com',
        '456 Oak St',
        '67890',
        9876543210987654
    );
-- Insert values into FLIGHT table
INSERT INTO FLIGHT (Model, Origin, Destination, Departure)
VALUES ('747', 'JFK', 'LAX', '12/12/2023'),
    ('A320', 'LAX', 'LHR', '12/12/2023'),
    ('747', 'JFK', 'LAX', '12/12/2024'),
    ('A320', 'LAX', 'LHR', '12/12/2024');
-- Insert values into CREW_ASSIGNMENTS table
INSERT INTO CREW_ASSIGNMENTS (Crew_id, Flight_num, Role)
VALUES (1, 1, 'Pilot'),
    (2, 1, 'Co-pilot'),
    (1, 2, 'Flight Attendant');
-- Insert values into TICKET table
INSERT INTO TICKET (
        Ticket_Num,
        Flight_Num,
        Seat_Row,
        Seat_Num,
        Seat_Type,
        Customer
    )
VALUES (1, 1, 'A', 1, 'Economy', 'user1@example.com'),
    (2, 1, 'B', 2, 'Business', 'user2@example.com'),
    (3, 2, 'C', 3, 'Comfort', 'user1@example.com');
Select *
from Flight;