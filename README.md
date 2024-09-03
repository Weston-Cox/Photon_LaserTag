# Photon Laser Tag Java Application

Welcome to the official repository of the Photon Laser Tag software! This Java-based application is designed to power the classic Photon Laser Tag experience, providing team management, game mechanics, and more.

## Table of Contents
- [About](#about)
- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Contributors](#contributors)
- [License](#license)
- [Contact](#contact)

## About

The Photon Laser Tag Java application serves as the main software for managing and playing Photon Laser Tag games. Whether you're hosting a casual game or organizing a tournament, this software provides the essential tools needed to ensure a seamless laser tag experience.

## Features

- **Team Management:** Easily manage red and green teams.
- **Game Mechanics:** Enforce game rules and track scores.
- **Networking:** Support for UDP connections to sync game data across different systems.
- **Database Integration:** Persistent storage using PostgreSQL for player statistics and game history.

## Installation

### Prerequisites

Before you begin, ensure you have the following installed on your system:

- **[Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)**: Version 21
- **[Apache Maven](https://maven.apache.org/download.cgi)**: For project build management
- **PostgreSQL**: For database management (This program uses a VM that runs the PostgreSQL Database)

### Steps

1. **Clone the repository:**
   ```bash
   gh repo clone Weston-Cox/Photon_LaserTag
   cd Photon_LaserTag

2. **Install Dependencies**
   ```bash
   mvn clean install

3. **Compile the Application**
   ```bash
   mvn clean compile

### Usage

* Before attempting to run the application, start the virtual machine.
  1. Within the virtual machine, type the following command in the terminal to grab the IP address.
     ```bash
     hostname -I
     ```
      This command should list the IPv4 and the IPv6 addresses. **Copy the IPv4 address**

  2. **[SUBJECT TO CHANGE]** Now inside the project itself, navigate to the PostgreSQL.java file _(src/main/java/com/photon/DB/PostgreSQL.java)_
     
     Inside the constructor of the PostgreSQL class, change the IP address in the url to the IP address you copied from the virtual machine.
     ![image](https://github.com/user-attachments/assets/520d0553-85fd-48f8-b50d-c90153ec9d7c)

* To run the application, use the following command:

  ```bash
  mvn exec:java
  ```
## Contributors
  - **Cole Blankenship**
  - **Eric Giangiulio**
  - **Tyler Hannon**
  - **Weston Cox**
