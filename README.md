Task Management Application
Project Overview:
    The Task Management Application is a lightweight system (similar to Jira/Trello) that allows users to Register and log in. Create, update, delete, and view tasks. Link tasks to specific users.
    Use both a UI (Thymeleaf pages) and REST APIs for managing tasks. The project demonstrates good coding practices, REST integration, validation, and database persistence.
Technical Stack:
    Backend: Spring Boot (Java, Spring MVC, Spring Data JPA, Validation)
    Frontend: Thymeleaf + Bootstrap (responsive UI)
    Database: H2 / MySQL (configurable)
    Tools: IntelliJ IDEA, Postman (API Testing), GitHub
    Build Tool: Maven
Setup Instructions:
    Clone the repository
    git clone https://github.com/your-username/task-management-app.git
    cd task-management-app
    Build the project
    mvn clean install
    Run the application
    mvn spring-boot:run
Access the application:
    UI: http://localhost:8080
    REST APIs: http://localhost:8080/api
Features Implemented:
    User Registration & Login (with validation)
    Task CRUD Operations (Create, Read, Update, Delete)
    Session Handling (only logged-in user can manage tasks)
    REST APIs for both User and Task services
    Validation rules (email format, required fields, due date cannot be past date)
    Database persistence with JPA
Application Flow:
    User registers → stored in DB.
    User logs in → session created.
    Welcome page → shows logged-in user’s tasks.
    Add/Edit/Delete task → actions saved in DB.
    Logout → session cleared.
    REST APIs available for Users and Tasks (tested with Postman).
Additional Documentation:
    Full system architecture and workflow diagram
    and **UI Screenshots** provided in docs folder.
Setup Instructions (Local):
    Clone the repository:
     1.git clone https://github.com/shyamalasherly/task-management-app.git
     2.cd task-management-app
     3.mvn clean install
     4.mvn spring-boot:run
     5.Access the application
        UI: http://localhost:8080
        REST APIs: http://localhost:8080/api
Deployment Links:
    GitHub Repository: https://github.com/shyamalasherly/task-management-app.git
    Live Deployment (Render): https://task-management-app-kfgj.onrender.com
    UI (Render): https://task-management-app-kfgj.onrender.com
    API Base URL (Postman):https://task-management-app-kfgj.onrender.com/api/

API Endpoints:
User:
    Register → POST /api/register
    Login → POST /api/login
Task Management
    Get All Tasks → GET /api/tasks
    Get Task by ID → GET /api/tasks/{id}
    Create Task → POST /api/tasks
    Update Task → PUT /api/tasks/{id}
    Delete Task → DELETE /api/tasks/{id}
Sample Data for Testing:
    Register:
    {
    "username": "xxx",
    "email": "xxxx12@example.com",
    "password": "xxx123456"
    }
    Login:
    {
    "username": "xxx",
    "password": "xxx123456"
    }
    Create Task:
    {
    "title": "xxxtitle",
    "description": "xxx desc",
    "dueDate": "2025-09-16"
    "status": "PENDING"
    }
                                                        Author: shyamala B
