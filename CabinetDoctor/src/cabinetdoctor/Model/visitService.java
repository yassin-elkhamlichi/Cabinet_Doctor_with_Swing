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
 * @author yassin
 */
public class visitService extends BDInfo {
    
    public static int ajouterVisite(String symptoms, String diagnostics, String note, String deh, String type, int montant, String cin) throws SQLException {
    Connection con = null;
    Savepoint savepoint1 = null;
    int id = 1;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint1 = con.setSavepoint("save1");
        

        PreparedStatement psmt = con.prepareStatement("INSERT INTO Visit (symptoms, diagnostics, note, deh, type, montant, cin) VALUES (?,?,?,?,?,?,?)");

        psmt.setString(1, symptoms);
        psmt.setString(2, diagnostics);
        psmt.setString(3, note);
        psmt.setString(4, deh);
        psmt.setString(5, type);
        psmt.setInt(6, montant);
        psmt.setString(7, cin);
        
        int i = psmt.executeUpdate();

        System.out.println(i + " La visite a été insérée avec succès.");
        
        Statement stmt = con.createStatement();
        String sql = "SELECT MAX(id) AS last_id FROM Visit;";
        ResultSet rs = stmt.executeQuery(sql);
    
        if (rs.next()) {
            id = rs.getInt("last_id");
        }

        con.commit();
        con.close();
    } catch (SQLException e) {
        if (con != null) {
            con.rollback(savepoint1);
        }
        e.printStackTrace();
    }
        return id;
}


    public static void afficherVisites(DefaultTableModel model) throws SQLException {
    String selectAll = "SELECT * FROM Visit;";
    try (Connection con = DriverManager.getConnection(url, user, password);
         Statement sttm = con.createStatement();
         ResultSet res = sttm.executeQuery(selectAll)) {
        while (res.next()) {
            model.addRow(new Object[]{res.getInt("id"), res.getString("symptoms"),
                    res.getString("diagnostics"), res.getString("note"),
                    res.getTimestamp("deh"), res.getString("type"),
                    res.getInt("montant"), res.getString("cin")});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

    public static void exportVisites(File file) {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT symptoms, diagnostics, note, deh, type, montant, cin FROM Visit WHERE deh >= CURDATE() ORDER BY deh";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int l1 = 40, l2 = 15, l3 = 25; 

        writer.write(String.format("%-" + l3 + "s | %-" + l3 + "s | %-" + l3 + "s | %-" + l1 + "s | %-" + l1 + "s | %-" + l2 + "s | %-" + l2 + "s\n", "Symptômes", "Diagnostic", "Note", "Date/Heure", "Type", "Montant", "CIN"));
        writer.write("--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");

        while (res.next()) {
    String symptoms = String.format("%-" + l3 + "s", res.getString("symptoms"));
    String diagnostics = String.format("%-" + l3 + "s", res.getString("diagnostics"));
    String note = String.format("%-" + l3 + "s", res.getString("note"));
    String deh = String.format("%-" + l1 + "s", res.getString("deh"));
    String type = String.format("%-" + l1 + "s", res.getString("type"));
    String montant = String.format("%-" + l2 + "s", res.getInt("montant"));
    String cin = String.format("%-" + l2 + "s", res.getString("cin"));

         writer.write(symptoms + " | " + diagnostics + " | " + note + " | " + deh + " | " + type + " | " + montant + " | " + cin + "\n");
        }

        writer.close();

        con.close();
    } catch (SQLException | IOException e) {
        System.out.println(e);
    }
}
    public static void modifierVisite(int id, String symptoms, String diagnostics, String note, String deh, String type, int montant, String cin) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "UPDATE Visit SET symptoms = ?, diagnostics = ?, note = ?, deh = ?, type = ?, montant = ?, cin = ? WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setString(1, symptoms);
        psmt.setString(2, diagnostics);
        psmt.setString(3, note);
        psmt.setString(4, deh);
        psmt.setString(5, type);
        psmt.setInt(6, montant);
        psmt.setString(7, cin);
        psmt.setInt(8, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("La visite avec l'ID " + id + " a été modifiée avec succès.");
        } else {
            System.out.println("Aucune visite avec l'ID " + id + " trouvée.");
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

   public static void supprimerVisite(int id) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "DELETE FROM Visit WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setInt(1, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("La visite avec l'ID " + id + " a été supprimée avec succès.");
        } else {
            System.out.println("Aucune visite avec l'ID " + id + " trouvée.");
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


  public static void supprimerVisitesExpirées() throws SQLException {
    Connection cn = null;
    Savepoint savepoint = null;
    try {
        cn = DriverManager.getConnection(url, user, password);
        cn.setAutoCommit(false);
        savepoint = cn.setSavepoint("save");

        Statement smt = cn.createStatement();
        String deleteQuery = "DELETE FROM Visit WHERE deh < CURDATE();";
        int rowsAffected = smt.executeUpdate(deleteQuery);
        
        System.out.println(rowsAffected + " visites expirées ont été supprimées avec succès.");

        cn.commit();
        cn.close();
    } catch (SQLException e) {
        if (cn != null) {
            cn.rollback(savepoint);
            cn.close();
        }
        e.printStackTrace();
    }
}


}
