/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package cabinetdoctor.Controles;

import java.sql.*;

/**
 *
 * @author el-barae
 */
public class DBManager extends BDInfo {

    public static void createDBTables() throws SQLException{
        try {
			String createBD = "Create Database if not exists cabinetdoctor;";
			Connection con = DriverManager.getConnection(url, user, password);
			Statement stm = con.createStatement();
			stm.execute(createBD);
			System.out.println("Database has been created.");
			String createRV = "Create Table if not exists RendezVous(\r\n"
					+ "    id int PRIMARY KEY AUTO_INCREMENT,\r\n"
					+ "    note text,\r\n"
					+ "    date date not NULL,\r\n"
					+ "    heure time,\r\n"
					+ "    cinP varchar(10),\r\n"
					+ "    foreign key (cinP) REFERENCES Patient(cin)\r\n"
					+ ");";
			String createV = "Create Table if not exists Visit(\r\n"
					+ "    id int PRIMARY KEY AUTO_INCREMENT,\r\n"
					+ "    symptoms text not NULL,\r\n"
					+ "    diagnostics text NOT NULL,\r\n"
					+ "    note text,\r\n"
					+ "    deh datetime,\r\n"
					+ "    type varchar(25),\r\n"
					+ "    montant int,\r\n"
					+ "    cin varchar(10),\r\n"
					+ "    foreign key (cin) REFERENCES Patient(cin)\r\n"
					+ ");";
			String createO = "Create Table if not exists Ordonnance(\r\n"
					+ "    id int PRIMARY KEY AUTO_INCREMENT,\r\n"
					+ "    idV int,\r\n"
					+ "    medicament text,\r\n"
					+ "    test text,\r\n"
					+ "    note text,\r\n"
					+ "    foreign key (idV) REFERENCES Visit(id)\r\n"
					+ ");";
			String createP = "Create Table if not exists Patient(\r\n"
					+ "    cin varchar(10) PRIMARY KEY NOT NULL,\r\n"
					+ "    nom varchar(25) NOT NULL,\r\n"
					+ "    prenom varchar(25) NOT NULL,\r\n"
					+ "    sexe varchar(1),\r\n"
					+ "    ddn date,\r\n"
					+ "    tele varchar(10)\r\n"
					+ ");";
			
			stm.executeUpdate(createP);
			stm.executeUpdate(createRV);
			stm.executeUpdate(createV);
			stm.executeUpdate(createO);
			System.out.println("Tables has been created.");
			con.close();
		}catch(SQLException e) {
			System.err.println(e);
		}

    }
    
    public static void createDBUsers() throws SQLException{
        try {
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stm = con.createStatement();

            String createUsersTable = "CREATE TABLE IF NOT EXISTS users (cin varchar(30) PRIMARY KEY NOT NULL, username varchar(30), password varchar(30), tdc time, ddc date, email text)";

            stm.executeUpdate(createUsersTable);
            // Insertion des données
            String insertIntoUsers = "INSERT INTO users (cin, username, password, tdc, ddc, email) VALUES ('L000000', 'user1', 'user1', CURTIME(), CURDATE(), 'user1@gmail.com')";
            
            System.out.println("\n++++++++++++++++----------  L'utilisateur user1 de password user1 cree avec succès pour le test.-----------++++++++++++++++++\n");

            stm.executeUpdate(insertIntoUsers);


            System.out.println("Tables créées avec succès.");

            con.close();
            System.out.println("Queries executed successfully.");
        } catch (SQLException e) {
            System.err.println(e);
        }
    
    }
}
