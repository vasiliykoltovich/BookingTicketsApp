DELETE FROM AUDITORIUM

INSERT INTO AUDITORIUM (NAME, SEATS_NUMBER, VIP_SEATS) VALUES ('Blue hall', 500, '25,26,27,28,29,30,31,32,33,34,35');
INSERT INTO AUDITORIUM (NAME, SEATS_NUMBER, VIP_SEATS) VALUES ('Red hall', 800, '25,26,27,28,29,30,31,32,33,34,35,75,76,77,78,79,80,81,82,83,84,85');
INSERT INTO AUDITORIUM (NAME, SEATS_NUMBER, VIP_SEATS) VALUES ('Yellow hall', 1000, '25,26,27,28,29,30,31,32,33,34,35,75,76,77,78,79,80,81,82,83,84,85,105,106,107,108,109,110,111,112,113,114,115');

DELETE FROM USER

INSERT INTO USER (email,NAME,EVENT_DATE) VALUES ('dmitriy.vbabichev@gmail.com', 'Dmytro Babichev', '1984-01-04');
INSERT INTO USER (email,NAME,EVENT_DATE) VALUES ('vasil.koltovich@gmail.com', 'Vasil Koltovich', '1985-11-16');

DELETE FROM  EVENT

INSERT INTO EVENT (NAME,RATE,BASE_PRICE,DATE_TIME,AUDITORIUM) VALUES ('The revenant', 'HIGH', 60,'2018-01-04',1);
INSERT INTO EVENT (NAME,RATE,BASE_PRICE,DATE_TIME,AUDITORIUM) VALUES ('Meeting', 'HIGH', 90,'2018-02-04',2);
INSERT INTO EVENT (NAME,RATE,BASE_PRICE,DATE_TIME,AUDITORIUM) VALUES ('Int. Convention', 'HIGH', 85,'2018-05-06',3);


