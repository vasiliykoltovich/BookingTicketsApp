To test application please run application  and then fiddler tests inside folder files/Fiddler/Fiddler_tests.saz.
Please note that last two test with prefixes http://localhost:8100/pdf/bookTicket.. better to run via browser address bar or Postman application to get PDF
files.
To run application please use command "java -jar BookingApp-0.0.1-SNAPSHOT.jar" in folder where the BookingApp-0.0.1-SNAPSHOT.jar is placed.
Or you can start application via Maven command "mvn spring-boot:run" inside filder with root pom.xml.

To  test functionality with batch upload from files follow the next steps:
1. go to page http://localhost:8100/loadFiles.
2. On page you will find two forms with separate upload of configuration files for new users and for new events.
   Examples of configuration files inside files/batch as User.json and Event.json.

3. To load new Events click "Choose files" button in "Upload Events" section, choose Event.json file and click "Upload files" button. You will be redirected
   to page http://localhost:8100/loadEvents where you will see new added events from file.

4. To load new Users click "Choose files" button in "Upload Users" section, choose User.json file and click "Upload files" button. You will be redirected
   to page http://localhost:8100/loadUsers where you will see new added users from file.