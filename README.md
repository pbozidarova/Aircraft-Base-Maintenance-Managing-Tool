## Aircraft-Base-Maintenance-Managing-Tool
The Aircraft manufacturer, the Aircraft Operator, the European Union Aviation Safety Agency are all important units that strictly monitor the safety of the operations of an aircraft.
Trough out its lifecycle it needs to undergo complex technical checked that consists of many tasks such as providing access, cleaning, painting, non-destructive testing, performance checks.
Such overhaul is also known as D or C check, huge base maintenance that requires licenced facilities, personnel and perfect administration. 

The goal of this application if to facilitate the documentation of such a check and to provide a great way of communicating any issues between the engineering and the technical staff. 

---------
### Model - each table with its properties(columns) in the database. It will be populated with mockup data when the application is started.
1. User
   - First, Last name, Password, Company email - self-explanatory, cannot be null;
   - CompanyId - It is a predefined number, composed by the letter 'n' and 5 numbers; 
   - Role - a set of roles, from limited list, can be either User or Admin and either mechanic or engineer; 
   
2. Aircraft
   - OperatorName - Usually the company that owns or operates with the aircraft. The wide known name, the brand name of an operator of the aircraft;
   - OperatorICAOCode - a code with which the operator is registered in ICAO;
   - Type - aircraft type;
   - Model - aircraft model;
   - Registration - this the registration that the national authorities have given the aircraft when it has been entered in the national registers; 
   - Serial Number - a manufacturer number that is unique for an aircraft;
   - EngineManufacturer - the manufacturer of the engines;
   - EngineModelSeries - the engine model;
   - for simplicity only Airbus aircraft have been used;
3. Task
   A single technical job.
4. Maintenance Event
   A huge technical event that consists of many tasks.
5. Ticket
   Means of communication between the users, that is happening in the context of a maintenance event.
6. Base 
   Where the whole maintenance event is physically performed
   
### Serivce! The functionality of each entity
1. User
   A user can be an admin or a simple user of this application.
   An engineer that can create a single task (technical job);

a mechanic that performs it.