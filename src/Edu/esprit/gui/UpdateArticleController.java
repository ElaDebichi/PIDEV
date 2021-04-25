/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Edu.esprit.gui;


import Edu.esprit.entities.Article;
import Edu.esprit.services.ArticleService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author bensa
 */
public class UpdateArticleController implements Initializable {

    @FXML
    private TextField titre_txt;
    @FXML
    private TextArea desc_txt;
    @FXML
    private DatePicker date_pic;
    @FXML
    private Button btn_update;
    @FXML
    private ImageView imageToPost;
    @FXML
    private Button addImage;
    String imgUrl  ="";
    private FileChooser uploadPic;
    private File picPath;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void addImage(ActionEvent event) {
         Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        uploadPic = new FileChooser();
        uploadPic.setTitle("Select the image you want to add");
        picPath = uploadPic.showOpenDialog(stage);
        System.out.println(picPath.toString());
        try {
            imgUrl = picPath.toURI().toURL().toExternalForm();

            BufferedImage buffImage = ImageIO.read(picPath);
            Image up = SwingFXUtils.toFXImage(buffImage, null); 
           imageToPost.setImage(up);
        } catch(IOException ex){
            System.err.println(ex.getMessage());
        }
        
    }

    Article a=new Article();
    @FXML
    private void UpdatArticle(MouseEvent event) {
        
         ArticleService as = new ArticleService(); 
         if (!titre_txt.getText().equals("") && !desc_txt.getText().equals("")) {
                                         String image =imgUrl;
           
          			as.modifierArticle(new Article(a.getId(), titre_txt.getText(), desc_txt.getText(),Date.valueOf(date_pic.getValue())
                                        ,image
						 
                                     
				));
                            Notifications n = Notifications.create()
                              .title("SUCCESS")
                              .text("  User Modifié")
                              .position(Pos.TOP_CENTER)
                              .hideAfter(Duration.seconds(1));
               n.darkStyle();
               n.show();
          
			   FXMLLoader loader = new FXMLLoader(getClass().getResource("Index.fxml"));

        try {
            Parent root = loader.load();
            btn_update.getScene().setRoot(root);
        } catch (IOException ex) {
                          
          
          
		
    }
    
}
    }
    
    
      public void setArt(Article a) {
		this.a = a;
	}
     private AnchorPane ap;

    public void updateField() throws IOException {
		titre_txt.setText(a.getTitre());
		desc_txt.setText(a.getDescription());
                
 
		
                   Image image ;
                image = new Image(a.getImg());
        
            this.imageToPost.setImage(image);
                Date d = a.getDate();
              
               date_pic.setValue(d.toLocalDate());
               AnchorPane pane = FXMLLoader.load(getClass().getResource("Index.fxml"));
              
               
              
                
		
	}
    
}
