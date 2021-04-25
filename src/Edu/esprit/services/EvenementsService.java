/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.services;

import Edu.esprit.entities.Evenements;
import Edu.esprit.entities.User;
import Edu.esprit.tools.MaConnexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;



/**
 *
 * @author bensa
 */
public class EvenementsService {
    
    Connection cnx;
    PreparedStatement ste;

    public EvenementsService() {
        cnx = MaConnexion.getinstance().getCnx();
   
    }
    
    public void ajouterEvenements(Evenements e){
        
        
   
        try {
            String sql = "insert into evenements(date,adresse,titre,nom,nbr_participants,nbr_max,img)"
                    +"values(?,?,?,?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setDate(1, e.getDate());
            ste.setString(2, e.getAdresse());
            ste.setString(3, e.getTitre());
            ste.setString(4, e.getNom());
            ste.setInt(5, e.getNbr_participants());
            ste.setInt(6, e.getNbr_max());
           ste.setString(7, e.getImg());
           
            
            ste.executeUpdate();
            System.out.println("Evenemenet Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    /*public ObservableList<Evenements> afficherEvenements(){
    ObservableList<Evenements> events = FXCollections.observableArrayList();
   
        try {
             String sql = "select * from evenements";
     
             ste = cnx.prepareStatement(sql);
             ResultSet rs = ste.executeQuery();
             while (rs.next()) {   
                 Evenements e = new Evenements();
                 e.setId(rs.getInt(1));
                 e.setDate(rs.getDate(2));
                 e.setAdresse(rs.getString(3));
                 e.setTitre(rs.getString(4));
                 e.setNom(rs.getString(5));
                 e.setNbr_participants(rs.getInt(6));
                 e.setNbr_max(rs.getInt(7));
                 Image image = new Image(rs.getString(8)); //create img
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80); 
                 events.add(e);
               
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    
    return  events;
    
}*/
    public ObservableList<Evenements> afficherEvenements() {
        ObservableList<Evenements> e = FXCollections.observableArrayList();
        
        String requete = "SELECT * FROM evenements ";
        try {
            
            PreparedStatement pst;
            pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                Image image = new Image(rs.getString(8)); //create img
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);
                e.add(new Evenements(rs.getInt(1),rs.getDate(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),rs.getInt(7),imgV)); 
                System.out.print(e);
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return e;
    }
    
    
    public void supprimerEvent(Evenements e) {
        String requete = "DELETE FROM evenements WHERE id=?";
        try {
            PreparedStatement pst = new MaConnexion().cnx.prepareStatement(requete);
            pst.setInt(1, e.getId());
            pst.executeUpdate();
            System.out.println("evenement Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifierEvent(Evenements e) {
        String requete = "UPDATE evenements "
                + "SET id=?, date=?,adresse=? ,titre=? ,nom=? ,nbr_participants=?,"
                + "nbr_max=? , img = ? WHERE id=?";
        try {
            PreparedStatement pst = 
            new MaConnexion().cnx.prepareStatement(requete);
            pst.setInt(9, e.getId());
             pst.setInt(1, e.getId());
            pst.setDate(2, e.getDate());
            pst.setString(3, e.getAdresse());
            pst.setString(4, e.getTitre());
            pst.setString(5, e.getNom());
            pst.setInt(6, e.getNbr_participants());
            pst.setInt(7, e.getNbr_max());
            pst.setString(8, e.getImg());
                        
            pst.executeUpdate();
            System.out.println("evenement Modfié !");
        } catch(SQLException ex) {
            
            System.err.println(ex.getMessage());
        }
    }
    
    public void participation (Evenements e , User u)
    {
         String requete = "insert into participations(user_id,event_id) values(?,?)";
         String requete1 = "Update evenements SET nbr_participants=? Where id=?";
         String requete2="SELECT Count (*) From participations";
         ResultSet rc1 = null;
         int row=0;
         
         try {
         
            PreparedStatement pst =  new MaConnexion().cnx.prepareStatement(requete);
            PreparedStatement pst1 = new MaConnexion().cnx.prepareStatement(requete1);
            Statement stat = cnx.createStatement();

           
            pst.setInt(1, u.getId());
            pst.setInt(2, e.getId());
            pst.executeUpdate();
            rc1 = stat.executeQuery("SELECT COUNT(*) AS row FROM participations");
           rc1.next();
           row=rc1.getInt("row");
             pst1.setInt(1,row);
            pst1.setInt(2, e.getId());
             pst1.executeUpdate();
            System.out.println("Participation Ajouté !");
            
              Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text(" Participation Ajouté !")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        } catch(SQLException ex) {
            
            System.err.println(ex.getMessage());
            
            Notifications n = Notifications.create()
                              .title("FAILED")
                              .text(" vous avez deja confirmé votre participation !")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(2));
        }
         
         
        
    
    }
    
    
    
    public void Annuler_participation (Evenements e , User u)
    {
        if(e.getNbr_participants()>0)
        {
         String requete = "delete From participations where user_id=? and event_id=?";
         String requete1 = "Update evenements SET nbr_participants=? Where id=?";
       
         
         
         try {
            PreparedStatement pst =  new MaConnexion().cnx.prepareStatement(requete);
            PreparedStatement pst1 =  new MaConnexion().cnx.prepareStatement(requete1);
            pst.setInt(1, u.getId());
            pst.setInt(2, e.getId());
            System.out.print (u.getId());
            System.out.print(e.getId());
                    
              pst.executeUpdate();
             pst1.setInt(1, e.getNbr_participants()-1);
            pst1.setInt(2, e.getId());
           
            
            
                        
          
             pst1.executeUpdate();
            System.out.println("Participation annulé !");
            
               Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text(" Participation Annulé !")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        } catch(SQLException ex) {
            
            System.err.println(ex.getMessage());
        }
         
        }
        else
        {
            Notifications n = Notifications.create()
                              .title("FAILED")
                              .text(" il n'ya pas de participation pour annuler !")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(2));
               n.darkStyle();
               n.show();
        
        
        }
        
    
    }
    
   /* public void Annuler_participation (Evenements e , User u)
    {
          String requete = "delete  From participations where user_id=? and event_id=?";
         String requete1 = "Update evenements SET nbr_participants=? Where id=?";
       
         ResultSet rc1 = null;
         int row=0;
         
         try {
         
            PreparedStatement pst =  new MaConnexion().cnx.prepareStatement(requete);
            PreparedStatement pst1 = new MaConnexion().cnx.prepareStatement(requete1);
            Statement stat = cnx.createStatement();

           
            pst.setInt(1, u.getId());
            pst.setInt(2, e.getId());
            pst.executeUpdate();
            
            rc1 = stat.executeQuery("SELECT COUNT(*) AS row FROM participations");
            rc1.next();
            row=rc1.getInt("row");
            
            pst1.setInt(1,row);
            pst1.setInt(2, e.getId());
            pst1.executeUpdate();
            
            System.out.println("Participation Annulé !");
            
              Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text(" Participation Annulé !")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
        } catch(SQLException ex) {
            
            System.err.println(ex.getMessage());
        }
    }*/
         
    
}