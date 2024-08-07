## Github Api Task

The main functionality of the application is to list all repositories of a given GitHub username which are not forks. The API provides:
-Repository Name
-Owner Login
-For each branch: its name and the last commit SHA

### Technologies
* Java 21
* Spring Boot 3
* Maven

### Building
To build the project, navigate to the project directory and execute:
```
mvn clean install
```
This will compile the code, run tests, and package the application into a JAR file.

### Running
To run the project after building, execute:
```
mvn spring-boot:run
```
This will start the Spring Boot application on the default port (usually http://localhost:8080).

### Usage
To fetch repositories for a specific GitHub user, send a GET request to the following endpoint:
```
GET /api/github/repositories/{username}
Accept: application/json
```
### Example
Replace {username} with the desired GitHub username. For example, to fetch repositories for the user octocat, use the following URL:
```
GET http://localhost:8080/api/github/repositories/octocat
Accept: application/json
```
### Error Handling
When a user tries to fetch data for a non-existent GitHub user:
```
{
    "status": 404,
    "message": "User not found"
}
```
