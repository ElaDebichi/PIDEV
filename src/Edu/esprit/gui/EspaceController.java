/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author spicy
 */
public class EspaceController implements Initializable {

    @FXML
    private Button btn_admin;
    @FXML
    private Button btn_client;
    @FXML
    private Button btn_company;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void LoadAdmin(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Index.fxml"));

        try {
            Parent root = loader.load();
            btn_admin.getScene().setRoot(root);
        } catch (IOException ex) {
           
    }
        
    }

    @FXML
    private void LoadClient(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IndexFront.fxml"));

        try {
            Parent root = loader.load();
            btn_client.getScene().setRoot(root);
        } catch (IOException ex) {
           
    }
    }

    @FXML
    private void LoadCompany(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IndexFrontCompany.fxml"));

        try {
            Parent root = loader.load();
            btn_company.getScene().setRoot(root);
        } catch (IOException ex) {
           
    }
    }
    
}
