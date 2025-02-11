﻿# LifeCycle@KN

## Requirements:
- Docker (Docker Compose specifically)

## How to Run
### Setup 
There should be no prior setup needed if you are running this from a release-level version. However, it might be possible that the frontend asks for a `.env` file, which is bundled with the releases. If this file is not present inside the `frontend` folder, please ask the team for it.

### Running 
In the project's root folder, run '(sudo) docker compose up'. This will up 3 services: the `MySQL database`, the `Spring Boot backend` application and the `Vue frontend` app.

- **For easier backend testing purposes**: you can run only the MySQL database with '(sudo) docker compose up mysql'. This will let the MySQL server run concurrently while you use IntelliJ (or whatever you choose to run Java Gradle projects) to run the Spring Boot backend and avoid having to down the Spring Boot container everytime - however, remember to change Spring's datasource to suit your needs in the [application.resources file](./backend/src/main/resources/application.properties) (more details can be found there).  
Furthermore, for easy management and coverage of the database, it is recommended to install an auxiliary tool like VSCode's `SQLTools` extension and adding the MySQL database server's connection as follows:

![SQLTools database setup](./docs/images/sqltools_setup.png)

NOTE: SQLTools opens session files by default - you can turn this off in the extension settings (*sqltools.autoOpenSessionFiles*). 

Furthermore, it might be helpful to test requests and overall network traffic with an auxiliary app like `Postman` or [`Bruno`](https://www.usebruno.com/downloads).

## Endpoints & Accesses

#### MySQL: 
- Port: 3306
- Database name: processdb 
- Base username and Password: base_user, base_user 
- Root username and Password: root, 123456

#### Spring Boot
- Port: 8081

#### Vue: 
- Port: 5173
- Axios API: 8081

## High-level Architecture 

![High-level Architecture](./docs/images/top-architecture.png)

Lifecycle's frontend is a [Vue](https://vuejs.org/) web app that communicates with the backend through [Axios](https://axios-http.com/docs/intro)-handled requests. These requests are received by the backend through [Spring Boot](https://spring.io/projects/spring-boot)'s *Controllers* or *Services*, which will then be able to exchange information with and modify the [MySQL](https://www.mysql.com/) database, using [Spring Data Java Persistence API](https://spring.io/projects/spring-data-jpa).

There are a handful of online applications with very similar architectures to this one, and to get familiarized, we recommend following these two guides: [`Vue, Spring Boot & MySQL CRUD guide`](https://www.bezkoder.com/spring-boot-vue-js-mysql/) and [`Spring Boot JPA with MySQL guide`](https://www.bezkoder.com/spring-boot-jpa-crud-rest-api/).

## Relational Model

![UML](./docs/images/class_uml.png)

### Users & Roles
Access to the platforms's functionalities requires authentication, and authentication is split between three roles as follows:
- `EMPLOYEE`: has read permissions over public, personal and assigned-to information. This means that they can, for example, see onboarder statistics, assigned processes, personal user information and step details.
- `HR`: full read permissions - can see all the information on the platform. Has write (CRUD) permissions over processes and can create onboarders, but cannot manage other users.
- `ADMIN`:  Has both full read and write permissions, and are the ones that manage (CRUD) users on the platform, including updating user roles. 

**For testing purposes**, sign-in with the mock accounts present in [data.sql](/backend/src/main/resources/data.sql), which have different roles assigned to them. The passwords all follow the same naming conventions: for `user1@example.com`, the password is `password1`; for `user2@example.com`, the password is `password2`, and so on and so forth. 

## IMPORTANT NOTES:

### Hibernate's *ddl-auto* (create, create-drop, validate, update)
We set this value to `update` so that a table will be created in the database automatically corresponding to defined data model.
With this, any change to the model will also trigger an update to the table.
For production, this property should be 'none'.  
More info & security practices can be found here: https://spring.io/guides/gs/accessing-data-mysql

### Spring Boot's 'lazy_load_no_trans'
This is an anti-pattern in [application.resources file](./backend/src/main/resources/application.properties) to be disabled later, currently only active for easier database entity testing. Should be removed when project is in the production stage. Explanation found [here](https://stackoverflow.com/questions/25362831/solve-hibernate-lazy-init-issue-with-hibernate-enable-lazy-load-no-trans/25367976#25367976).

### Possible CORS Problems
If you are struggling with CORS errors when running the application, do check [this file](./backend/src/main/java/com/lifecycle/backend/config/WebSecurityConfig.java) in the backend folder, because it might have to do with the configured allowed CORS endpoints. In the `corsConfigurationSource` function, change "localhost:5173" to your preferred allowed origin point(s). 

### Email Notifications not working
Due to Google's new security measures, unverified applications (like this one) need to use [Google App Passwords](https://knowledge.workspace.google.com/kb/how-to-create-app-passwords-000009237) to securely access Gmail's services. Because we are using a dummy email account with SMTP, our account doesn't have the necessary privileges to send emails, so it is not currently working. Even then, the notification logic for Owner/Backup notifications is implemented, although commented.

### Frontend padding problem
Due to Vuetify's `main.css` (inside *frontend/node_modules/vuetify/lib/styles/main.css*) resetting all margin & padding to 0, some of the frontend's components have these properties overwritten, leading to some visual bugs that (sometimes) break the site's usability, like when trying to advance an onboarder's process. The work-around currently in use is the commenting out of these lines in the file, and running the container as is. However, when using *npm install* (like when running the project for the first time), this visual bug might come back, because the node_modules will be updated. Until further work is done, the easiest way to deal with this is with the aforementioned method.
