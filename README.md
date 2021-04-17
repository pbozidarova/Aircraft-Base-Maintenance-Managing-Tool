## Aircraft-Base-Maintenance-Managing-Tool
The Aircraft manufacturer, the Aircraft Operator, the European Union Aviation Safety Agency are all important units that strictly monitor the safety of the operation of an aircraft.
Trough out the aircraft lifecycle it needs to undergo complex technical checks that consists of many tasks such as providing access, cleaning, painting, non-destructive testing, performance checks. 
Such a great overhaul is also known as D or C check - huge base maintenance that requires licenced facilities, personnel and perfect administration. 

The goal of this application is to facilitate the documentation of such a check and to provide an easy way of communicating any issues between the engineering and the technical staff. 

In short - We have **AIRCRAFT** that need to have **MAINTENANCE** at specific **FACILITY**. 
This **MAINTENANCE** consists of multiple technical **TASKS** and while performing them different **ISSUES** may occur. 

- Heroku hosted 
    - Frontend: https://abmmt-fe.herokuapp.com/
    - Backend: https://abmmtool.herokuapp.com/

- Localy configured ports:
    - Frontend: http://localhost:3200/
    - Backend: http://localhost:8000/

---------
## Technologies and libraries used in the frontend
- React Application - A JavaScript library for building user interfaces
- Axios library - Promise based HTTP client for the browser and node.js
- Material-UI, the world's most popular React UI framework.
    

## Technologies and libraries used in the backend
- REST Application - the backend lies in the principles of a REST service. 
- Spring Boot - framework that helps you create stand-alone, production-grade Spring based Applications.
- JSON Web Tokens - an open, industry standard RFC 7519 method for representing claims securely between two parties.
- Cloudinary - quickly and easily create, manage and deliver their digital experiences across any browser, device and bandwidth.

> More details of the used concepts and their realization can be found in each of the folders README.MD files.

## Main resources
- The Softuni courses on <a href="https://softuni.bg/trainings/2612/spring-data-february-2020">Spring Data</a>, <a href="https://softuni.bg/trainings/2844/spring-fundamentals-may-2020">Spring Fundamentals</a> and <a href="https://softuni.bg/trainings/3026/spring-advanced-june-2020">Spring Advanced.</a>
- The JavaBrains <a href="https://youtu.be/X80nJ5T7YpE">tutorial</a> on JWT token.
- The in28minutes <a href="https://github.com/pbozidarova/Full-Stack-Tutorial-Spring-React">udemy tutorial</a> on developing a full-stack application.
- The dashboard layout has been based on <a href="https://material-ui.com/getting-started/templates/dashboard/">this Materials UI template</a>.