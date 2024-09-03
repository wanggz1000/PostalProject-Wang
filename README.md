# Postal Project Documentation

## About the project

This project was created by Guanzhong Wang.

We developed the frontend using a straightforward JavaFX interface, while the API was constructed with the SpringBoot framework. For message and request coordination among services and databases, we utilized RabbitMQ. The databases themselves are PostgreSQL instances. To provide consistent working environments across the development team, we employed GitHub for version control, hosting our repository. Additionally, we used Docker containers to manage the databases and the RabbitMQ instance.


## Set-Up and Installation

### Clone the project repository

To use our application first you need to clone this whole project from our https://github.com/wanggz1000/PostalProject-Wang.git
You can either do this with for example IntelliJ's built-in tool or run this command in your preferred command line interpreter within the directory you wish to save the project in:

```bash
cd "path/to/your/desired/folder/"
git clone "https://github.com/wanggz1000/PostalProject-Wang.git"
```

### Start application modules and services

Then follow these steps:

1. Start your Docker Desktop application and then open your preferred command line interpreter and run the commands below. This will run the docker-compose file and create the databases and the RabbitMQ queue in a containerised environment.
    ```bash
    cd "./docker" 
    docker compose up
    ```
2. Once the containers are up and running you need to open this project in IntelliJ.
3. Then run these parts of the application in this order:  
   Step 1: Java FX UI  
   Step 2: SpringBoot API  
   Step 3: PackageService      

After you have started all these you can now use the application.

## Lessons Learned

Throughout the development of this project, our team gained following key lessons:

1. **Enhanced Teamwork with Version Control:** 
   Utilizing GitHub as our version control system significantly boosted our teamwork and code management capabilities. This tool enabled simultaneous work on various segments of     the project, efficient tracking of modifications, code merging, and conflict resolution.

2. **Streamlined Development Environments with Docker:** 
   Docker containers were instrumental in accelerating and simplifying our development process. By containerizing our databases and the RabbitMQ instance, we achieved environment    consistency across all team members.
3. **Navigating the Learning Curve of Emerging Technologies:**
   Integrating new technologies such as JavaFX, Spring Boot, and RabbitMQ presented a learning challenge. However, thorough documentation and accessible online resources were        pivotal in helping us acquire necessary skills and effectively implement these technologies. Understanding the mechanics of message exchanges in RabbitMQ was crucial,             necessitating a consensus on communication patterns.
4. **Effective Communication and Project Coordination:** 
   Clear and consistent communication among team members was crucial for our project's success. Regular meetings, precise task delegations, and continuous updates ensured            alignment and shared understanding within the team.
5. **Understanding system architecture and component integration:** 
   The development of our distributed system demanded a deep understanding of the system architecture and the interactions between various components. It was essential for all       team members to grasp the design principles and theoretical underpinnings of our project.
6. **Error handling and troubleshooting:**
   Throughout the project, we learned the importance of thorough error handling and debugging 
   to identify and resolve issues promptly.

There is significant potential for improvement, particularly in enhancing the modularity and reusability of our components. This will be crucial for future scalability and more frequent use of interfaces, an area we overlooked due to time constraints from concurrent projects each team member was involved with. Additionally, we faced certain limitations imposed by management directives regarding the use of specific technologies like JavaFX and SpringBoot, which didn't always align with the varied skill sets and experiences within our team. Moving forward, we aim to adopt a more flexible approach to technology choices. Despite these challenges, the project was a valuable learning experience that will benefit our future endeavors.


## Tracked Time

In this table you can see the days every team member worked on and the hours we invested individually. Please consider that these numbers are rough estimates:

| Date       | Activity                                  | Hours Worked p.P. |
|------------|-------------------------------------------|-------------------|
| 27.08.2024 | Work on DCD                               | 4                 |
| 28.08.2024 | DCD error fixed                           | 2                 |
| 29.08.2024 | All other services                        | 5                 |
| 03.09.2024 | DCR hotfix and documentation update       | 4                 |
| **Total**  |                                           | **15**            |






