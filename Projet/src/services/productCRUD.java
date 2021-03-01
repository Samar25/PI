/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import tools.MyConnection;

/**
 *
 * @author Samar
 */
public class productCRUD {

    public productCRUD() {
    }

    public void addproduct(Product p) {
        try {
            String requete = "INSERT INTO Product (nom,categorie,prix,description) "
                    + "VALUES (?,?,?,?)";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            pst.setString(1, p.getNom());
            pst.setString(2, p.getCategorie());
            pst.setFloat(3, p.getPrix());
            pst.setString(4, p.getDescription());
            pst.executeUpdate();
            System.out.println("Personne ajoutée!");
        } catch (SQLException ex) {
            Logger.getLogger(productCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ObservableList<Product> getProductList() {

        ObservableList<Product> ProductList = FXCollections.observableArrayList();
        String requete = "SELECT * FROM product";
        try {
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            //Statement st;
            ResultSet rs;
            try {
                //System.out.println("AHAYYYAA!!!!");
                //st=conn.createStatement();
                //System.out.println("AHAYYYAA222!!!!");
                rs = pst.executeQuery(requete);
                Product p;

                while (rs.next()) {
                    p = new Product(rs.getInt("id"), rs.getString("nom"), rs.getString("categorie"), rs.getFloat("prix"), rs.getString("description"));
                    ProductList.add(p);
                }

            } catch (Exception ex) {
                //System.out.println("AHAYYYAA L7KEEEYAAAAA!!!!");
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            Logger.getLogger(productCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ProductList;
    }
 
    
   


}
