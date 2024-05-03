# LifeCycle@KN

## Requirements:
- Docker

## How to Run
- There is no setup needed. In the project's root folder, run '(sudo) docker compose up'. This will up 3 services: the `MySQL database`, the `Springboot backend` application and the `Vue frontend` app.

- **For easier testing purposes**: you can run only the MySQL database with '(sudo) docker compose up mysql'. This will let the MySQL server run concurrently while you use IntelliJ (or whatever you choose to run Java Gradle projects) to run the Springboot backend and avoid having to down the Springboot container; just remember to change Spring's datasource to suit your needs in the [application.resources file](./backend/src/main/resources/application.properties). 
For easy management and coverage of the database, I recommend installing the `SQLTools` extension and adding the MySQL database server's connection as follows:

![SQLTools database setup](./docs/images/sqltools_setup.png)

NOTE: SQLTools opens session files by default - you can turn this off in the extension settings (*sqltools.autoOpenSessionFiles*). 

Furthermore, it might be helpful to test requests and overall network traffic with an auxiliary app like `Postman` or [`Bruno`](https://www.usebruno.com/downloads).

## Endpoints & Accesses

### MySQL: 
- Port: 3306
- Database name: processdb 
- Base username and Password: base_user, base_user 
- Root username and Password: root, 123456

### Springboot
- Port: 8081

### Vue: 
- Port: 5173
- Axios API: 8081 (**to be changed**)

## High-level Architecture 

![High-level Architecture](./docs/images/top-architecture.png)

## IMPORTANT NOTES:

### Springboot's 'lazy_load_no_trans'

- Anti-pattern in [application.resources file](./backend/src/main/resources/application.properties) to be disabled later, currently only active for easier database entity testing. Explanation found [here](https://stackoverflow.com/questions/25362831/solve-hibernate-lazy-init-issue-with-hibernate-enable-lazy-load-no-trans/25367976#25367976).