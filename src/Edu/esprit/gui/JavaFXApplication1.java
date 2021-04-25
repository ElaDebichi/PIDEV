/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;

import Edu.esprit.tools.MaConnexion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author bensa
 */
public class JavaFXApplication1 extends Application {
    
    @Override
    public void start(Stage primaryStage){
        try {
            Parent root=FXMLLoader.load(getClass().getResource("Espace.fxml"));
            
            Scene scene = new Scene(root, 814, 545);
            primaryStage.setTitle("Welcome");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(JavaFXApplication1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         MaConnexion cm = MaConnexion.getinstance();
   
        launch(args);
  
    }
    
}
