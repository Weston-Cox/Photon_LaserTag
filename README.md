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
- **Networking:** Support for UDP connections to sync and transmit game data across different systems.
- **Database Integration:** Persistent storage using PostgreSQL for player ID and Codename.

## Installation

### Prerequisites

**Before you begin, ensure you have the following installed on your system:**

- **[Java Development Kit (JDK)](https://www.oracle.com/java/technologies/downloads/#jdk21-windows)**: Version 21

  1. Check your Java version in the VM's terminal:  `java --version`
 
  2. If your version is anything other than 21, navigate to [this](https://www.oracle.com/java/technologies/downloads/#java21) link in the VM's Web Browser
 
  3. Download the correct package for your Linux machine. For us, it was the **x64 Debian Package**
 
  4. After downloading, the package should be in your ~/Downloads folder. Navigate to that in a terminal.
 
  5. Next, we need to de-package the .deb file. Within the ~/Downloads directory, run this command in the terminal: `sudo dpkg -i jdk-21_linux-x64_bin.deb`. At this point, Linux does a bunch of the work for us.
 
  6. Now, the Java version should have automatically switched to version 21. To double check, run the `java --version` command.
 
   - **If for any reason you do not have the correct Java version listed, we must run a couple commands to change it.**

      1. Run the `sudo update-alternatives –config java` command to open up a menu. Choose the correct Java version.

      2. Run the `sudo update-alternatives -config javac`  command. Choose the correct Java Compiler version.
   
- **[Apache Maven](https://maven.apache.org/download.cgi)**: For project build management

  1. Check if Maven is installed on your VM by typing: `mvn --version` on your terminal.
 
  2. If not, type `sudo apt install maven`
 
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

* **Before attempting to run the application, we must do two things:**

  1.  Start the virtual machine (self-explanatory)

  2.  Run the edited traffic generator within the repo.
  
      - To do this, navigate to the \Helpers directory from the root of the project.
        ```bash
        cd src/main/java/com/photon/Helpers

      - Then, run the `python_trafficgenerator_v2.py` by typing your respective python command:
        ```bash
        python3 python_trafficgenerator_v2.py

      - The terminal should then prompt you to type in the equipment IDs for the red and green players. Be sure to type the exact same equipment IDs that you entered in the Initial Screen.
        ![image](https://github.com/user-attachments/assets/c73f3c41-f68a-4c4b-b9a0-ee6963af4016)


* To run the application, use the following command:

  ```bash
  mvn exec:java
  ```
## Contributors
  - **[Cole Blankenship](https://github.com/Cole-Blankenship)**
  - **[Eric Giangiulio](https://github.com/ericgiang)**
  - **[Tyler Hannon](https://github.com/Tyler-Hannon)**
  - **[Weston Cox](https://github.com/Weston-Cox)**
