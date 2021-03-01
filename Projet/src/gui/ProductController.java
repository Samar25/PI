/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import entities.Product;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.productCRUD;
import tools.MyConnection;


/**
 * FXML Controller class
 *
 * @author Samar
 */
public class ProductController implements Initializable {

    @FXML
    private TextField tfidP;
    @FXML
    private TextField tfnomP;
    @FXML
    private TextField tfcategorieP;
    @FXML
    private TextField tfprixP;
    @FXML
    private TextField tfdescriptionP;
    @FXML
    private Button btnAddP;
    @FXML
    private TableColumn<Product,Integer > colID;
    @FXML
    private TableColumn<Product, String> colNOM;
    @FXML
    private TableColumn<Product, Float> colPRIX;
    @FXML
    private TableColumn<Product, String> colDESCRIPTION;
    @FXML
    private TableColumn<Product, String> colCATEGORIE;
    @FXML
    private TableView<Product> ProductTabV;
    productCRUD pcr=new productCRUD();
    private int ID;
    @FXML
    private Button btnUPDATEP;
    @FXML
    private Button btnDELETEP;
    @FXML
    private Button btnSORTP;
    @FXML
    private TextField tfSearchProd;
       

    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showProducts();
        SearchProduct();
         
  
        
       
    }    
    @FXML
    private void AjouterProduit(ActionEvent event) {
        
          //try {
            //1- SAVE PERSON IN DATABASE
            String rNom= tfnomP.getText();
            String rCategorie= tfcategorieP.getText();
            String rPrix1=tfprixP.getText();
            float rPrix= Float.parseFloat(rPrix1);
            String rDescription=tfdescriptionP.getText();
            Product p = new Product(123, rNom, rCategorie,rPrix,rDescription);
            productCRUD pcd = new productCRUD();
            pcd.addproduct(p);
            //showProducts();
            showProducts();
            
  
            
           /* //2- REDIRECTION
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("PersonDetails.fxml"));
            Parent root = loader.load();
            PersonDetailsController pdc = loader.getController();
            pdc.setContNom(rNom);//injecter les variables du premier formulaire
            pdc.setContPrenom(rPrenom);
            tfNom.getScene().setRoot(root);*/
            
       /* } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }*/
    }

   public void showProducts() {
        System.out.println("DEBUGG!!!!");
        ObservableList<Product> list =pcr.getProductList();
        //ObservableList<Product> list = FXCollections.observableList(pcd.getProductList());
        colID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colNOM.setCellValueFactory(new PropertyValueFactory<Product, String>("nom"));
        colCATEGORIE.setCellValueFactory(new PropertyValueFactory<Product, String>("categorie"));
        colPRIX.setCellValueFactory(new PropertyValueFactory<Product, Float>("prix"));
        colDESCRIPTION.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        ProductTabV.setItems(list);
          
    }

    @FXML
    private void SetValue(MouseEvent event) {
        Product selected = ProductTabV.getSelectionModel().getSelectedItem();
        if (selected != null) {
            tfnomP.setText(selected.getNom());
            tfcategorieP.setText(selected.getCategorie());
            String Sprix = String.valueOf(selected.getPrix());
            tfprixP.setText(Sprix);
            tfdescriptionP.setText(selected.getDescription());
            ID = selected.getId();
        }
    }


    @FXML
    private void ModifierProduit(ActionEvent event) {
          try {
            String requete = "UPDATE product SET nom=?, categorie=?, prix=?, description=? WHERE id="+ID+"";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            pst.setString(1, tfnomP.getText());
            pst.setString(2, tfcategorieP.getText());
            String rPrix1=tfprixP.getText();
            float fPrix= Float.parseFloat(rPrix1);
            pst.setFloat(3, fPrix);
            pst.setString(4, tfdescriptionP.getText());
            pst.executeUpdate();
            System.out.println("Produit modifié!");
        } catch (SQLException ex) {
            Logger.getLogger(productCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
          showProducts();
         
          
    }

    @FXML
    private void SupprimerProduit(ActionEvent event) {
       
       try {
            String requete = "DELETE FROM product WHERE id="+ID+"";
            PreparedStatement pst
                    = new MyConnection().cn.prepareStatement(requete);
            pst.executeUpdate();
            System.out.println("Produit supprimé!");
        } catch (SQLException ex) {
            Logger.getLogger(productCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
          showProducts(); 
         
        
    }

    @FXML
    private void SortProduit(ActionEvent event) {
        
        /*  data.clear();
        Connection cnx = MyConnection.getInstance().getCnx();
        PreparedStatement ps;*/
         ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            String requete= "SELECT id,nom,categorie,prix,description FROM product ORDER BY prix ASC";

PreparedStatement pst= new MyConnection().cn.prepareStatement(requete);
ResultSet rs = pst.executeQuery();
          
            while(rs.next()){
               list.add(new Product (rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getString(5)));
            }
           
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
                colID.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        colNOM.setCellValueFactory(new PropertyValueFactory<Product, String>("nom"));
        colCATEGORIE.setCellValueFactory(new PropertyValueFactory<Product, String>("categorie"));
        colPRIX.setCellValueFactory(new PropertyValueFactory<Product, Float>("prix"));
        colDESCRIPTION.setCellValueFactory(new PropertyValueFactory<Product, String>("description"));
        ProductTabV.setItems(list);
       
    }
    
       private void SearchProduct() { 
ObservableList<Product> list =pcr.getProductList();
         // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Product> filteredData = new FilteredList<>(list, b -> true);
		
		// 2. Set the filter Predicate whenever the filter changes.
		tfSearchProd.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Product -> {
				// If filter text is empty, display all persons.
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				// Compare first name and last name of every person with filter text.
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Product.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches first name.
				} else if (Product.getCategorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches last name.
				}
				else if (String.valueOf(Product.getPrix()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Product> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(ProductTabV.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		ProductTabV.setItems(sortedData);
    }

 
   

 

}
