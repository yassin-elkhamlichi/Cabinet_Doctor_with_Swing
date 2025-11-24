# 🩺 Cabinet Doctor with Swing - Desktop Clinic Management System



[![Java](https://img.shields.io/badge/Java11-blue.svg)](https://www.java.com/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)



Cabinet Doctor with Swing is a robust desktop application developed in Java Swing, designed to streamline the management of a doctor\'s clinic. It offers comprehensive features for handling patient records, scheduling appointments, managing medical visits, and generating prescriptions, providing an efficient solution for healthcare professionals.



## 🌟 Features



- **Patient Management**: Efficiently add, view, update, and delete patient information.

- **Appointment Scheduling (Rendezvous)**: Manage patient appointments with ease, including scheduling and tracking.

- **Medical Visit Tracking**: Record and organize details of each patient visit.

- **Prescription Generation (Ordonnances)**: Create and manage patient prescriptions.

- **User-Friendly Interface**: Intuitive graphical user interface built with Java Swing for easy navigation and operation.

- **Database Integration**: Seamlessly connects with a MySQL database for persistent data storage.



## 🛠️ Technology Stack



- **Frontend**: Java Swing (for desktop GUI)

- **Backend**: Java

- **Database**: MySQL (requires a running MySQL server)

- **Build Tool**: Apache Ant



## 📋 Prerequisites



Before you can run this application, ensure you have the following installed:



- **Java Development Kit (JDK)**: Version 11 or higher (e.g., OpenJDK 11).

- **Apache Ant**: Version 1.10.x or higher.

- **MySQL Server**: A running MySQL database instance.

- **MySQL JDBC Driver**: The MySQL Connector/J library (usually included in the `lib` directory or needs to be added to the project\'s classpath).



## ⚡ Quick Installation & Setup



### 1. Clone the Repository



```bash

git clone https://github.com/yassin-elkhamlichi/Cabinet_Doctor_with_Swing.git

cd Cabinet_Doctor_with_Swing/CabinetDoctor

```



### 2. Database Setup



This application uses a MySQL database. You need to set up your database and configure the connection.



#### a. Create Database and Table



First, create a database in your MySQL server (e.g., `cabinet_doctor`). Then, import the `tables.sql` file located in the root of the cloned repository to create the necessary tables.



```bash

# Example using MySQL client

mysql -u your_username -p

CREATE DATABASE cabinet_doctor;

USE cabinet_doctor;

SOURCE /path/to/Cabinet_Doctor_with_Swing/tables.sql;

```



#### b. Configure Database Connection



The database connection details are likely hardcoded or configured within the Java source files (e.g., `cabinetdoctor/Controles/DBManager.java` or `cabinetdoctor/Controles/BDInfo.java`). You will need to locate these files and update the database URL, username, and password to match your MySQL setup.



Look for lines similar to these in the Java source code:



```java

String url = "jdbc:mysql://localhost:3306/cabinet_doctor";

String user = "root";

String password = "your_password";

```



**Important**: Replace `localhost:3306`, `cabinet_doctor`, `root`, and `your_password` with your actual database server details.



### 3. Build the Application



Navigate to the `CabinetDoctor` directory and build the project using Ant:



```bash

cd /home/ubuntu/Cabinet_Doctor_with_Swing/CabinetDoctor

ant

```



This command will compile the Java source files and create a runnable JAR file in the `dist` directory (e.g., `dist/CabinetDoctor.jar`).



### 4. Run the Application



After a successful build, you can run the application from the `CabinetDoctor` directory:



```bash

java -jar dist/CabinetDoctor.jar

```



**Note**: This is a Java Swing desktop application, so it requires a graphical environment to run. If you are running this on a server without a display, you might encounter `java.awt.HeadlessException`.



## 🗂️ Project Structure



```

Cabinet_Doctor_with_Swing/

├── CabinetDoctor/          # Main application source and build files

│   ├── build/              # Compiled classes and build artifacts

│   ├── build.xml           # Ant build script

│   ├── lib/                # External libraries (e.g., MySQL JDBC driver)

│   ├── nbproject/          # NetBeans project configuration

│   └── src/                # Java source code

│       └── cabinetdoctor/  # Main Java package

│           ├── Controles/  # Database connection and control logic

│           ├── Files/      # File handling utilities

│           ├── Interfaces/ # GUI forms and panels

│           ├── Model/      # Business logic and data services

│           └── photo/      # Image resources

├── ModelsDeDonnees.pdf     # Database schema documentation (PDF)

└── tables.sql              # SQL script for database table creation

```



## 📝 License



This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.



## 👥 Credits

- **Developer**: [Yassin El Khamlichi](https://github.com/yassin-elkhamlichi)
