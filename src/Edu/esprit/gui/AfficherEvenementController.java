/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import Edu.esprit.entities.Evenements;
import Edu.esprit.services.EvenementsService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class AfficherEvenementController implements Initializable {

    EvenementsService en = new EvenementsService();
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
    private TableView<Evenements> afficher_evenement;
    @FXML
    private Button btn_del;
    @FXML
    private Button btn_ajout;
    @FXML
    private Button eventEdit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    private void deleteEvent(ActionEvent event) {
    
                Evenements e = null;
		e = afficher_evenement.getSelectionModel().getSelectedItem();
		if(e != null) {
			en.supprimerEvent(e);
                       
			Notifications n = Notifications.create() 
                              .title("SUCCESS")
                              .text("  Event suprrimé")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(3)); 
               n.darkStyle();
               n.show();
			   afficher_event();
		}
    
    }

    @FXML
    private void add_event(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterEvenement.fxml"));

        try {
            Parent root = loader.load();
            btn_ajout.getScene().setRoot(root);
        } catch (IOException ex) {
           
    }

        
    }

    @FXML
    private void modiferClicked(MouseEvent event) throws IOException {
        
        
        Evenements e = null;
		e = afficher_evenement.getSelectionModel().getSelectedItem();
		
		if(e != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateEvenement.fxml"));
			 
       
       
      
 Parent parent1 = loader.load();

       
          eventEdit.getScene().setRoot(parent1);
           UpdateEvenementController controller = (UpdateEvenementController) loader.getController();
            controller.setEvent(e);
			controller.updateField();
    
		}

        
        
    }

    
    
    
    
}
