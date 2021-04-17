## Aircraft-Base-Maintenance-Managing-Tool Backend
---------
### Model - each table with its properties(columns) in the database. It will be populated with mockup data when the application is started.
1. Base entity<br>
   Keeps pretty self-explanatory data for each of the rest entities Id, Created on, Updated on
   
2. User
   - First, Last name, Password, Company email - self-explanatory, cannot be null;
   - CompanyNum - It is a predefined number, composed by the letter 'n' and 5 numbers; 
   - Role - a set of roles, from limited list, can be either USER or ADMIN and either MECHANIC or ENGINEER; 
   - Facility - the place where the user is located.
   
3. Aircraft<br>
   For simplicity only Airbus aircraft has been used.
   - OperatorName - Usually the company that owns or operates with the aircraft. The wide known name, the brand name of an operator of the aircraft;
   - OperatorICAOCode - a code with which the operator is registered in ICAO;
   - Type - aircraft type;
   - Model - aircraft model;
   - Registration - this the registration that the national authorities have given the aircraft when it has been entered in the national registers; 
   - Serial Number - a manufacturer number that is unique for an aircraft;
   - EngineManufacturer - the manufacturer of the engines;
   - EngineModelSeries - the engine model.

4. Facility <br>
   Where the whole overhaul/ maintenance event is physically performed
   - Name - the name of the place;
   - Country and City - the exact place where the facility is;
   - Manager - who is responsible for the facility;
   - Competences - which aircraft can have its periodical check here - it is not currently implemented, but it is intended;
   - Capacity - how many aircraft can simultaneously undergo maintenance.

5. Maintenance Event<br>
   A huge technical event that consists of many tasks.
   - MaintenanceNum - identification specific for an event;
   - StartDate - when the project begins;
   - EndDate - when the project ends;
   - Status - whether the project is upcoming, already opened or closed;
   - Facility - where the project is taking place;
   - Aircraft - which aircraft is the subject of the event;
   - Responsible Engineer - who is responsible for the preparation of the project.

6. Task<br>
   A single technical job.
   - TaskNum - identification specific for a task;
   - Code - 3-letter-code of the last, for easier identification of the core of the task;
   - Description - brief explanation of the core of the task;
   - isToolingAvailable - whether the facility is technically equipped for the task;
   - areJobCardsPrepared - whether the facility is administratively prepared for the task;
   - isQualityAssured - whether everything has been checked twice;
   - Maintenances - for which maintenance events this task has been called
   - PreparedBy - who are the users that has been taken part in the preparation process
   - Notifications - are there any issues that has been risen due to this task;
   - ApplicableAircraft - which aircraft this task can be applied to - it is not currently implemented, but it is intended.

7. Notification<br>
   Means of communication between the users, that is happening in the context of a maintenance event.
   - NotificationNum - identification specific for an issue;
   - Replies - an issue may require long back and forth communication between different users. It consists of description, author and attachment.
   - Author - the user that have created the issue;
   - Status - whether the issue has already been reviewed;
   - Classification - after closing the issue, it needs to be classified for statistics reasons;
   - DueDate - when this issue needs to be solved, between 3 and 5 days;
   - Maintenance - during which overhaul the issue has occurred. 
                  
Here is a simple diagram of the table relation: 
<a target="_blank" href="https://i.imgur.com/lCbMDlG.png">
![Diagram](https://i.imgur.com/lCbMDlG.png) 
</a>

### Service! (The functionality)

1. Initial application run
   With the initial launch, the mock data from the resource files will fill the tables.
   Please keep in mind that a part of the prefilled data is randomly generated, mostly for the joined tables but not limited only to them. 
   Every time when you drop the database and restart the application fairly data will be populated. 
   For easier reading the code, the seeding services are mostly separated from the rest of the functionality.  

2. Security / Authentication
   The application is supposed to be an official site with a specific purpose, where you are not free to create your own account. 
   You need to be provided with one from an admin.                               

   Each user logs in with theirs company number and password with a post request is sent to the /authenticate URL.
   When the username and password match a user from the database, a JWT is generated and send back as a response and every other request uses it later on for identification.
   In the security configuration only /authenticate URL is enabled for all and for every other request a JWT filter is applied.       

3. Read/write functionality
   JPA repositories are used for this functionality.    
   With the help of GET/POST/PUT requests and simple DTO/View models the data reaches the frontend
   and is written down back in the database.

4. Exception Handling
   Custom exceptions are thrown when invalid update and create requests are being sent.
   In the configuration package via ErrorConfiguration custom error attributes are added to the body of the errors.
   For the unexpected errors a global controller advices is also set. 

5. Caching
   The fetching of all tasks, notifications and maintenance is cached. 
   The cache is evicted when update/create is initiated for this entity.
   
6. AOP
   Using the annotations @TrackCreation and @TrackUpdating the program tracks how many times a create or update method have been called, checks the count of the successful ones and logs it.

7. Events
   Post creating of maintenance initiates a transaction of less than 400 random tasks, that are going to be performed in this event.
   This code can be found in the e_maintenance/event package.
   
8. Scheduling
   At 01:00 on every day-of-month the program prints a list with all overdue notifications.

   Once a day the program checks the start and end dates of every project and changes accordingly the status to UPCOMING, OPENED or CLOSED.

9. Interceptor
   The method and url of each request are logged.
   It is created in its own package, but it is registered in the config package.
   
10. Unit and integration tests
    The unit tests can be reviewed in the applicable section.
    
    A special configuration with a WithMockCustomUser annotation and WithMockCustomUserSecurityContextFactory
    were implemented in order to satisfy the security implementation and the integration tests.

### REST API (WEB)
  The program represents a REST API where you can request to read, update and create data. Delete functionality is not implemented due to law restrictions there needs to be a trace for everything done on an aircraft.  
  The create and update endpoints are in a separate files. 
  Below are listed all endpoints from each of the controllers.
- User       
  
      GET
      - localhost:3200/users/all
      - localhost:3200/users/{companyNum}
          HATEOAS:
          - localhost:3200/tasks/user/{companyNum}
          - localhost:3200/maintenance/user/{companyNum}
          - localhost:3200/notifications/user/{companyNum}
      ADD
      - localhost:3200/users/{companyNum}/create
      UPDATE
      - localhost:3200/users/{companyNum}/update

- Maintenance
       
      GET
      - localhost:3200/maintenance/all
      - localhost:3200/maintenance/{maintenanceNum}
          - HATEOAS:
             localhost:3200/tasks/maintenance/{maintenanceNum}
             localhost:3200/notifications/maintenance/{maintenanceNum}
       ADD
      - localhost:3200/maintenance/{maintenanceNum}/create
      UPDATE
      - localhost:3200/maintenance/{maintenanceNum}/update

- Tasks
  
      GET
      - localhost:3200/tasks/all
      - localhost:3200/tasks/{taskNum}
      - localhost:3200/tasks/user/{companyNum}
      - localhost:3200/tasks/maintenance/{maintenanceNum}
          HATEOAS: 
          - localhost:3200/maintenance/task/{taskNum}
          - localhost:3200/notifications/task/{taskNum}
      ADD
      - localhost:3200/tasks/{taskNum}/create
      UPDATE
      - localhost:3200/tasks/{taskNum}/update       

- Facilities

      GET
      - localhost:3200/facilities/all
          HATEOAS: 
          - localhost:3200/maintenance/facility/{name}
          - localhost:3200/users/facility/{name}
      ADD
      - localhost:3200/facilities/{name}/create
      UPDATE
      - localhost:3200/facilities/{name}/update

- Aircraft
  
      GET
      - localhost:3200/aircraft/all
      ADD
      - localhost:3200/aircraft/{registration}/create
      UPDATE
      - localhost:3200/aircraft/{registration}/update

- Notifications

      GET
      - localhost:3200/notifications/all
      - localhost:3200/notifications/user/{companyNum}
      - localhost:3200/notifications/maintenance/{maintenanceNum}
      ADD
      - localhost:3200/notifications/{notificationNum}/create
      UPDATE
      - localhost:3200/notifications/notificationNum/update

- Replies 

        GET
        - localhost:3200/replies/{notificationNum}
          ADD
        - localhost:3200/replies/{notificationNum}/create

 
