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
    
### Service! The functionality of each entity
1. User
   A user can be an admin or a simple user of this application.
   An engineer that can create a single task (technical job);
   a mechanic that performs it.
   
### REST API
- User
       
        GET
      - localhost:3200/users/all
      - localhost:3200/users/{companyNum}
      - HATEOAS:
         - localhost:3200/tasks/user/{companyNum}
         - localhost:3200/maintenance/user/{companyNum}
         - localhost:3200/issues/user/{companyNum}

        ADD
      - localhost:3200/users/{companyNum}/create
        UPDATE
      - localhost:3200/users/{companyNum}/update


- Maintenance
   
      - localhost:3200/maintenance/all
      - localhost:3200/maintenance/{maintenanceNum}
      - HATEOAS:
         localhost:3200/tasks/maintenance/{maintenanceNum}
         localhost:3200/issues/maintenance/{maintenanceNum}

- Tasks

      localhost:3200/tasks/all
      localhost:3200/tasks/{taskNum}
      localhost:3200/tasks/user/{companyNum}
      localhost:3200/tasks/maintenance/{maintenanceNum}

- Notifications
      
      localhost:3200/notifications/all
      localhost:3200/notifications/user/{companyNum}
      localhost:3200/notifications/maintenance/{maintenanceNum}

- Facilities
  
      localhost:3200/facilities/all
  
- Aircraft
      
      localhost:3200/aircraft/all

### Security
    

### Exception Handling
    
### Caching
 The fetching of all tasks, notifications and maintenance is cached. The cach is evicted when update/create is initiated for this entity.
 
### Events
Post creating of maintenance initiates a transaction of less than 400 random tasks, that are going to be performed in this event.

### Scheduling
At 01:00 on every day-of-month the program prints a list with all overdue notifications.

Once a day the program checks the start and end dates of every project and changes accordingly the status to UPCOMING, OPENED or CLOSED.

### AOP
Using the annotations @TrackCreation and @TrackUpdating the program tracks how many times a create or update method have been called, checks the count of the successful ones and logs it.

### Interceptor
The method and url of each request are logged. 