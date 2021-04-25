/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import Edu.esprit.entities.Evenements;
import Edu.esprit.entities.User;
import Edu.esprit.services.EvenementsService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class AfficherEvenementFrontController implements Initializable {
    EvenementsService en = new EvenementsService();
    

    @FXML
    private TableView<Evenements> afficher_evenement;
    @FXML
    private TableColumn<?, ?> clm_date;
    @FXML
    private TableColumn<?, ?> clm_adr;
    @FXML
    private TableColumn<?, ?> clm_titre;
    @FXML
    private TableColumn<?, ?> clm_desc;
    @FXML
    private TableColumn<?, ?> clm_part;
    @FXML
    private TableColumn<?, ?> clm_max;
    @FXML
    private TableColumn<?, ?> clm_img;
    @FXML
    private Button btn_annuler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         loadData();
    }
    public void loadData() {
        afficher_event();
       
    }    

    
    
      public void afficher_event(){
        
        ObservableList<Evenements> events =  en.afficherEvenements();
   
        clm_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        clm_adr.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        clm_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        clm_desc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        clm_part.setCellValueFactory(new PropertyValueFactory<>("nbr_participants"));
        clm_max.setCellValueFactory(new PropertyValueFactory<>("nbr_max"));
        clm_img.setCellValueFactory(new PropertyValueFactory<>("image1"));
        afficher_evenement.setItems(events);
    }

    @FXML
    private void Attend(ActionEvent event) {
        
        Evenements e = null;
       e = afficher_evenement.getSelectionModel().getSelectedItem();
       User u= new User(); 
       u.setId(2);
       en.participation(e,u);  
        afficher_event();
        
        
    }

    @FXML
    private void Unattend(ActionEvent event) {
        
         Evenements e = null;
       e = afficher_evenement.getSelectionModel().getSelectedItem();
       User u= new User(); 
       u.setId(2);
       en.Annuler_participation(e,u);  
        afficher_event();
        
    }
}
