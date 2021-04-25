/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.services;

import Edu.esprit.entities.Article;
import Edu.esprit.tools.MaConnexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 *
 * @author bensa
 */
public class ArticleService {
    
    
        Connection cnx;
    PreparedStatement ste;

    public ArticleService() {
        cnx = MaConnexion.getinstance().getCnx();
   
    }
    
    public void ajouterArticle(Article a){
   
        try {
            String sql = "insert into article(titre,description,date,img)"
                    +"values(?,?,?,?)";
            ste = cnx.prepareStatement(sql);
            ste.setString(1, a.getTitre());
            ste.setString(2, a.getDescription());
            ste.setDate(3, a.getDate());
            ste.setString(4, a.getImg());

            
            ste.executeUpdate();
            System.out.println("Article Ajoutée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public ObservableList<Article> afficherArticle() {
        ObservableList<Article> art = FXCollections.observableArrayList();
        
        String requete = "SELECT * FROM article";
        try {
            
            PreparedStatement pst;
            pst = cnx.prepareStatement(requete);
            ResultSet rs = pst.executeQuery(requete);
            while (rs.next()) {
                Image image = new Image(rs.getString(5)); //create img
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);
                art.add(new Article(rs.getInt(1),rs.getString(2), rs.getString(3),rs.getDate(4),imgV)); 
                System.out.print(art);
            }
            
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return art;
    }
    
    
    
    public void supprimerArticle(Article a) {
        String requete = "DELETE FROM article WHERE id=?";
        try {
            PreparedStatement pst = new MaConnexion().cnx.prepareStatement(requete);
            pst.setInt(1, a.getId());
            pst.executeUpdate();
            System.out.println("article Supprimée !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void modifierArticle(Article a) {
        String requete = "UPDATE article "
                + "SET id=?,titre=?,description=? ,date=?,img=? WHERE id=?";
        try {
            PreparedStatement pst = 
                    new MaConnexion().cnx.prepareStatement(requete);
            pst.setInt(6, a.getId());
            pst.setInt(1, a.getId());
            pst.setString(2, a.getTitre());
            pst.setString(3, a.getDescription());
            pst.setDate(4, a.getDate());
            pst.setString(5, a.getImg());
            
            pst.executeUpdate();
            System.out.println("Article Modfié !");
        } catch(SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    
public ObservableList<Article> searchArticle(String input) {
		ObservableList <Article> ListArticles = FXCollections.observableArrayList();
		
		try {
            String requete = "SELECT * FROM article  WHERE (`titre` like ? or `description` like ?)  ";
            PreparedStatement pst = cnx.prepareStatement(requete);
			pst.setString(1, "%"+input+"%");
			pst.setString(2, "%"+input+"%");
			
                       
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Image image = new Image(rs.getString("img")); //create img
                ImageView imgV = new ImageView(image);
                imgV.setFitHeight(80);
                imgV.setFitWidth(80);
                ListArticles.add(new Article(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getDate(4),imgV));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
		return ListArticles;
	}
    
    
}
