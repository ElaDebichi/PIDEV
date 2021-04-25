/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class IndexFrontController implements Initializable {

    @FXML
    private Button event_tab;
    @FXML
    private AnchorPane list_tab;
    @FXML
    private Button article_tab;
    @FXML
    private Button btn_change;

    /**
     * Initializes the controller class.
     */
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        try {
            Pane newLoadedPane;
            // TODO
            newLoadedPane = FXMLLoader.load(getClass().getResource("AfficherEvenementFront.fxml"));
            list_tab.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void load_event(ActionEvent event) throws IOException {
        list_tab.getChildren().clear();
        
        try {
            Pane newLoadedPane;
            // TODO
            newLoadedPane = FXMLLoader.load(getClass().getResource("AfficherEvenementFront.fxml"));
            list_tab.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void load_article(ActionEvent event) {
        list_tab.getChildren().clear();
        
        try {
            Pane newLoadedPane;
            // TODO
            newLoadedPane = FXMLLoader.load(getClass().getResource("AfficherArticleFront.fxml"));
            list_tab.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @FXML
    private void back(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("Espace.fxml"));

        try {
            Parent root = loader.load();
            btn_change.getScene().setRoot(root);
        } catch (IOException ex) {
           
    }
    }
   
}
