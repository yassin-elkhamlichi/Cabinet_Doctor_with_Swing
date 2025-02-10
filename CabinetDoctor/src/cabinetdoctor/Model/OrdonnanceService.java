package cabinetdoctor.Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */

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
public class OrdonnanceService extends BDInfo {

   public static int ajouterOrdonnance(String medicament, String test, String note, int idV) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    int idO = 1;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        PreparedStatement psmt = con.prepareStatement("INSERT INTO Ordonnance (idV, medicament, test, note) VALUES (?,?,?,?)");

        psmt.setInt(1, idV);
        psmt.setString(2, medicament);
        psmt.setString(3, test);
        psmt.setString(4, note);
        int i = psmt.executeUpdate();

        System.out.println(i + "L'ordonnance a été insérée avec succès.");
        
        Statement stmt = con.createStatement();
        String sql = "SELECT MAX(id) AS last_id FROM Ordonnance;";
        ResultSet rs = stmt.executeQuery(sql);
    
        if (rs.next()) {
            idO = rs.getInt("last_id");
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
       return idO;
}

  public static void afficherOrdonnances(DefaultTableModel model) throws SQLException {
    String selectAll = "SELECT * FROM Ordonnance;";
    try (Connection con = DriverManager.getConnection(url, user, password);
         Statement sttm = con.createStatement();
         ResultSet res = sttm.executeQuery(selectAll)) {
        while (res.next()) {
            model.addRow(new Object[]{res.getInt("id"),
                    res.getString("medicament"),res.getString("note"), res.getString("test"),
                     res.getInt("idV")});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e;
    }
}

public static void exportOrdonnance(File file) {
    try {
        Connection con = DriverManager.getConnection(url, user, password);
        Statement smt = con.createStatement();
        String sql = "SELECT medicament, test, note FROM Ordonnance";
        ResultSet res = smt.executeQuery(sql);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int l1 = 25; 

        writer.write(String.format("%-" + l1 + "s | %-" + l1 + "s | %-" + l1 + "s\n", "Médicament", "Test", "Note"));
        writer.write("--------------------------------------------------------------------------------------------------------------------\n");
        while (res.next()) {
            String medicament = String.format("%-" + l1 + "s", res.getString("medicament"));
            String test = String.format("%-" + l1 + "s", res.getString("test"));
            String note = String.format("%-" + l1 + "s", res.getString("note"));

            System.out.println(medicament + " | " + test + " | " + note);

            writer.write(medicament + " | " + test + " | " + note + "\n");
        }


        writer.close();
        System.out.println("Les données des ordonnances ont été écrites dans le fichier avec succès.");

        con.close();
    } catch (SQLException | IOException e) {
        System.out.println(e);
    }
}

public static void modifierOrdonnance(int id, int idV, String medicament, String test, String note) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "UPDATE Ordonnance SET idV = ?, medicament = ?, test = ?, note = ? WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setInt(1, idV);
        psmt.setString(2, medicament);
        psmt.setString(3, test);
        psmt.setString(4, note);
        psmt.setInt(5, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("L'ordonnance avec l'ID " + id + " a été modifiée avec succès.");
        } else {
            System.out.println("Aucune ordonnance avec l'ID " + id + " trouvée.");
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

   public static void supprimerOrdonnance(int id) throws SQLException {
    Connection con = null;
    Savepoint savepoint = null;
    try {
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
        savepoint = con.setSavepoint("save");

        String sql = "DELETE FROM Ordonnance WHERE id = ?";
        PreparedStatement psmt = con.prepareStatement(sql);
        psmt.setInt(1, id);

        int rowsAffected = psmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("L'ordonnance avec l'ID " + id + " a été supprimée avec succès.");
        } else {
            System.out.println("Aucune ordonnance avec l'ID " + id + " trouvée.");
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

}
