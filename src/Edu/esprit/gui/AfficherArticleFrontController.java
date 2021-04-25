/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import Edu.esprit.entities.Article;
import Edu.esprit.services.ArticleService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class AfficherArticleFrontController implements Initializable {
 ArticleService as = new ArticleService();
   
    @FXML
    private TableView<Article> afficher_article;
    @FXML
    private TableColumn<?, ?> clm_titre;
    @FXML
    private TableColumn<?, ?> clm_desc;
    @FXML
    private TableColumn<?, ?> clm_date;
    @FXML
    private TableColumn<?, ?> clm_img;
    @FXML
    private TextField search;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         loadData();
        // TODO
    }    
     public void loadData() {
        afficher_art();
       
    }    
     public void afficher_art(){
        
        ObservableList<Article> arts =  as.afficherArticle();
   
        clm_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
       
        clm_desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        clm_date.setCellValueFactory(new PropertyValueFactory<>("date"));
  
        clm_img.setCellValueFactory(new PropertyValueFactory<>("image1"));
        afficher_article.setItems(arts);
    }
    @FXML
    private void searchTextChanged(InputMethodEvent event) {
    }

    @FXML
    private void searchKeyRelaesed(KeyEvent event) {
         afficher_article.getItems().setAll(as.searchArticle(search.getText()));

    }
    
}
