# 🏥 Cabinet Doctor: Desktop Clinic Management System

![Java](https://img.shields.io/badge/Java-Swing-orange)
![Build](https://img.shields.io/badge/Build_Tool-Apache_Ant-red)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Architecture](https://img.shields.io/badge/Pattern-MVC-green)

## 📖 Overview
**Cabinet Doctor** is a robust desktop ERP solution engineered to digitize the daily operations of medical clinics. Built with **Java Swing**, it provides a responsive, event-driven interface for healthcare professionals.

Unlike simple CRUD apps, this system implements a strict **MVC (Model-View-Controller)** architecture to separate the Graphical User Interface (`Interfaces`) from the Business Logic (`Controles`) and Data Access Layers (`Model`), ensuring code maintainability and scalability.

## 🚀 Key Features

### 🩺 Clinical Operations
* **Patient Records:** Comprehensive CRUD operations for patient demographics and medical history.
* **Medical Visits:** detailed tracking of consultations, diagnosis notes, and history.
* **Prescription Management (Ordonnances):** Automated generation of prescriptions linked to specific visits.

### 📅 Administration
* **Appointment Scheduling:** A dedicated "Rendezvous" module to manage time slots and queue management.
* **Reporting:** Visual overview of daily appointments and patient flow.

### 💻 Technical Highlights
* **Legacy Modernization:** Demonstrates proficiency in maintaining and building robust desktop applications using standard Java APIs.
* **Data Persistence:** Direct JDBC integration with **MySQL** for reliable, ACID-compliant data storage.
* **Custom Build System:** Utilizes **Apache Ant** for dependency management and build automation.

---

## 🛠️ Tech Stack

| Component | Technology |
| :--- | :--- |
| **Language** | Java SE (JDK 11+) |
| **GUI Framework** | Java Swing (AWT/JFC) |
| **Architecture** | Model-View-Controller (MVC) |
| **Database** | MySQL 8.0 |
| **Build Tool** | Apache Ant |
| **Driver** | MySQL Connector/J |

---

## 🏗️ Project Architecture

The codebase is organized into distinct layers to follow separation of concerns:

```bash
src/cabinetdoctor/
├── Interfaces/      # [VIEW] JPanel & JFrame forms (GUI Logic)
├── Controles/       # [CONTROLLER] DB Managers & Business Logic
├── Model/           # [MODEL] Data Entities (Patient, Visit Objects)
├── Files/           # [UTILS] File handling & Export utilities
└── photo/           # [ASSETS] Static UI resources
