/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package cabinetdoctor.Model;

import cabinetdoctor.Controles.BDInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author el-barae
 */
public class PatientService extends BDInfo {
    
    public static void ajouterTable(String cin, String nom, String prenom, String sexe, String ddn, String tele) throws SQLException {
        Connection con = null;
        Savepoint savepoint1 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint1 = con.setSavepoint("save1");

            PreparedStatement psmt = con.prepareStatement("INSERT INTO Patient VALUE (?,?,?,?,?,?)");

            psmt.setString(1, cin);
            psmt.setString(2, nom);
            psmt.setString(3, prenom);
            psmt.setString(4, sexe);
            psmt.setString(5, ddn);
            psmt.setString(6, tele);
            int i = psmt.executeUpdate();

            System.out.println(i + "Le patient inséré avec succès.");

            con.commit();
            con.close();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint1);
            }
            e.printStackTrace();
        }
    }
    
    public static void afficherTable(DefaultTableModel model) throws SQLException {
        String selectAll = "SELECT * FROM Patient;";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement sttm = con.createStatement();
             ResultSet res = sttm.executeQuery(selectAll)) {
            while (res.next()) {
                model.addRow(new Object[]{res.getString("cin"), res.getString("nom"),
                        res.getString("prenom"), res.getString("sexe").charAt(0),
                        res.getDate("ddn"), res.getString("tele")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    public static void exportP(File file) throws SQLException{
                 Connection con = null;
                Savepoint savepoint3= null;
	        try {
	         con = DriverManager.getConnection(url, user, password);
                    con.setAutoCommit(false);
                 savepoint3 = con.setSavepoint("save3");
	         Statement smt = con.createStatement() ;
	        String sql = "SELECT cin, nom, prenom, sexe, ddn, tele FROM Patient ORDER BY nom ASC";
            ResultSet res = smt.executeQuery(sql);  

            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        writer.write("    CIN\t|\tNom\t\t|\tPrenom\t\t|\tSexe\t|\tD.naissance\t|\ttelephone\n");
        writer.write("------------------------------------------------------------------------------------------------------------------------------\n");
       
        int l1 = 25, l2 = 15;
        while (res.next()) {
            String cin = String.format("%-" + l2 + "s", res.getString("cin"));
            String nom = String.format("%-" + l1 + "s", res.getString("nom"));
            String prenom = String.format("%-" + l1 + "s", res.getString("prenom"));
            String sexe = String.format("%-" + l2 + "s", res.getString("sexe"));
            String ddn = String.format("%-" + l1 + "s", res.getString("ddn"));
            String tele = String.format("%-" + l1 + "s", res.getString("tele"));

            writer.write(" " + cin + nom + prenom + sexe + ddn + tele + "\n");
        }

        // Fermer le flux d'écriture
        writer.close();
        System.out.println("Les données ont été écrites dans le fichier avec succès.");
           
	         con.close();

	        } catch (IOException | SQLException e) {

           con.rollback(savepoint3);

      }
    }
    
    public static void modifierP(String cin, String nom, String prenom, String sexe, String ddn, String tele) throws SQLException {
        Connection con = null;
        Savepoint savepoint = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint = con.setSavepoint("save");

            String sql = "UPDATE Patient SET nom = ?, prenom = ?, sexe = ?, ddn = ?, tele = ? WHERE cin = ?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, nom);
            psmt.setString(2, prenom);
            psmt.setString(3, sexe);
            psmt.setString(4, ddn);
            psmt.setString(5, tele);
            psmt.setString(6, cin);

            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le patient avec CIN " + cin + " a été modifié avec succès.");
            } else {
                System.out.println("Aucun patient avec le CIN " + cin + " trouvé.");
            }

            con.commit();
            con.close();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint);
                con.close();
            }
            e.printStackTrace();
        }
    }
    
    public static void supprimerP(String cin) throws SQLException {
        Connection con = null;
        Savepoint savepoint4 = null;
        try {
            con = DriverManager.getConnection(url, user, password);
            con.setAutoCommit(false);
            savepoint4 = con.setSavepoint("save4");
            String sql = "DELETE FROM Patient WHERE cin = ?";
            PreparedStatement psmt = con.prepareStatement(sql);
            psmt.setString(1, cin);
            int rowsAffected = psmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Le patient avec CIN " + cin + " a été supprimé avec succès.");
            } else {
                System.out.println("Aucun patient avec le CIN " + cin + " trouvé.");
            }

            con.commit();
            con.close();
        } catch (SQLException e) {
            if (con != null) {
                con.rollback(savepoint4);
                con.close();
            }
            e.printStackTrace();
        }
    }
    
}
