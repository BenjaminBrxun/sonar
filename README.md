# Template repository for the Software Engineering lecture

> [!IMPORTANT]
> Replace this README with a detailed description of your project, your team, and instructions on how to run it.

> [!IMPORTANT]
> If you choose to keep your repository private, make sure to invite all your team members and teaching staff. Ask for their usernames if needed.

Provide a general introduction to your project. Describe the purpose, goals, and the technologies used. Explain the value your project offers.

## Team
List the team members involved in the project:

Team Leader: 
- Till Patron (Backend-Experte)

Members:
- Fabian Serves (Architektur-Experte)
- Max van Lier (Infrastruktur-Experte)
- Nils Palberg (UML-Experte)
- Micha keiten (Projektleiter)
- Benjamin Braun (SCRUM-Experte)
- Patrick Drechsel (Frontend-Experte)

## Quickstart

This section outlines the steps required to get your project up and running quickly:

```bash,ignore
# Example: Start a PostgreSQL database using Docker
$ docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -d postgres

# Example: Start the project (e.g., using Spring Boot)
$ ./mvnw spring-boot:ru
```

## Prerequisites

Detail all the necessary prerequisites for running your project, such as:

Operating System: (e.g., Linux, macOS, Windows)

Software: Docker, Java, Maven

Prototyp: [Figma Prototyp](https://www.figma.com/proto/G1wOTb3iaOJeZSlVaQ27tN/SWT1-Prototype?page-id=132%3A558&node-id=153-560&viewport=343%2C-544%2C0.44&t=oGkMymRAoTJUZyHt-1&scaling=scale-down&content-scaling=fixed&starting-point-node-id=153%3A560&show-proto-sidebar=1)
Passwort: swt1

### Tech-Stack: 
  - React (Frontend) https://react.dev
  - Springboot (Backend): https://start.spring.io/
  - PostgreSQL (Database): https://postgresql.org

Ports: (e.g., port 8080, if applicable)

## Installation and Setup

Provide step-by-step instructions on how to clone the repository, install the project, and configure it:

1. Clone the repository:
```bash,ignore
$ git clone https://github.com/YourRepository.git
```

2. Navigate to the project directory:
```bash,ignore
$ cd ProjectName
```

3. Adjust configuration files:

[//]: # (Modify configuration files &#40;e.g., `.env`, `application.properties`&#41; as required.)

Im Verzeichnis `sonar` muss vor dem ersten Starten ein neues Verzeichnis mit dem Namen `.env` erstellt werden.
In diesem Verzeichnis wird die Datei `credentials.env` erstellt.
In dieser Datei werden jetzt die Werte für `POSTGRES_USER` und `POSTGRES_PASSWORD` gesetzt.

Zum Starten des Sonar-Backends ist eine Konfiguration der Login-Daten für die PostgreSQL-Datenbank nötig.
Im Folgenden ist eine bebilderte Anleitung:

Über das Menü `Edit Configuration` können die Umgebungsvariablen, die zum Starten der Anwendung benötigt werden,
gesetzt werden.

![Edit Configuration](documentation/images/config_screen.png)

Mit einem Click auf `Modify options` erscheint das folgende Dropdown-Menü, in welchem `Envorinment variables` angeklickt
werden muss.

![Dropdown Menu](documentation/images/dropdown.png)

Anschließend werden die Werte für `SPRING_DATASOURCE_USERNAME` und `SPRING_DATA_PASSWORD` gesetzt.
Diese Werte müssen denen aus der `sonar/.env/credentials.env` entsprechen.

![Environment variables](documentation/images/env_input.png)

## Running the Project

Explain in detail how to run the project, including:

Starting the database

Initializing data (if needed, via scripts)

Starting the server

```bash,ignore
# Example: Initialize the database
$ ./init-db.sh

# Start the project
$ ./mvnw spring-boot:run
```

## Project structure
Provide an overview of the directory structure to help contributors navigate the project:
```bash,ignore
ProjectName/
├── project_one/        # Description of this subproject
├── docs/               # Documentation
├── tests/              # Test cases
└── README.md           # This file
```
