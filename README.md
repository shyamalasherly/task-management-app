Task Management Application
Project Overview
    The Task Management Application is a lightweight system (similar to Jira/Trello) that allows users to Register and log in. Create, update, delete, and view tasks. Link tasks to specific users.
    Use both a UI (Thymeleaf pages) and REST APIs for managing tasks. The project demonstrates good coding practices, REST integration, validation, and database persistence.
Technical Stack
    Backend: Spring Boot (Java, Spring MVC, Spring Data JPA, Validation)
    Frontend: Thymeleaf + Bootstrap (responsive UI)
    Database: H2 / MySQL (configurable)
    Tools: IntelliJ IDEA, Postman (API Testing), GitHub
    Build Tool: Maven
Setup Instructions
    Clone the repository
    git clone https://github.com/your-username/task-management-app.git
    cd task-management-app
    Build the project
    mvn clean install
    Run the application
    mvn spring-boot:run
Access the application
    UI: http://localhost:8080
    REST APIs: http://localhost:8080/api
Features Implemented
    User Registration & Login (with validation)
    Task CRUD Operations (Create, Read, Update, Delete)
    Session Handling (only logged-in user can manage tasks)
    REST APIs for both User and Task services
    Validation rules (email format, required fields, due date cannot be past date)
    Database persistence with JPA
Application Flow
    User registers → stored in DB.
    User logs in → session created.
    Welcome page → shows logged-in user’s tasks.
    Add/Edit/Delete task → actions saved in DB.
    Logout → session cleared.
    REST APIs available for Users and Tasks (tested with Postman).
Additional Documentation
    Full system architecture and workflow diagram
    and **UI Screenshots** provided in docs folder.
    



                                        Author: shyamala B
