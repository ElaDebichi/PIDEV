/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codename1.uikit.cleanmodern;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import static com.codename1.ui.Image.createImage;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.netdev.mindspace.entites.User;
import com.netdev.mindspace.services.UserService;

import java.util.ArrayList;
import java.util.Date;


/**
 *
 * @author hp
 */
public class ListCandidatsForm extends BaseForm {
    Form current;
    public ListCandidatsForm(Resources res) {
        //setTitle("List tasks");
        //SpanLabel sp = new SpanLabel();
        //sp.setText(ServicePost.getInstance().getAllPosts().toString());
        //add(sp);
       // getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
       
       
      
        super("Newsfeed", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        current = this;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        //setTitle("Create a new post");
        getContentPane().setScrollVisible(false);
        
        
        tb.addSearchCommand(e -> {
        
        });
        
        Tabs swipe = new Tabs();
        
        Label s1 = new Label();
        Label s2 = new Label();
        
    Image img = res.getImage("social.jpg");
        //System.out.println(img);
    
//     addTab(swipe,s1, img,"","",res);
     
 /////////
     
       swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("My posts", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Others", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("New post", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("down-arrow.png"), "Container");


        partage.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
       // new AddPostForm(res).show();
        //  ListReclamationForm a = new ListReclamationForm(res);
          //  a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        mesListes.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(mesListes, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        
        
        //list of posts
        
        
         ArrayList<User> list = UserService.getInstance().getAllUsers();
         System.out.println(list);
         for(User p : list){
             String urlImage = "social.jpg";
             Image placeHolder = createImage(120,90);
             EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
             URLImage urlim = URLImage.createToStorage(enc,urlImage,urlImage,URLImage.RESIZE_SCALE);
             //System.out.println(p.getNblikes());
             
             
           //addButton(urlim,p.getDate(), p.getContent() + "\n", p.getNblikes() ,p,res);
             
           
           
           ScaleImageLabel image = new ScaleImageLabel(urlim);
           Container containerImg = new Container();
           image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
           
           
             
         }
         
        
      
    }
    
    private void addTab(Tabs swipe, Label spacer, Image image, String string, String text, Resources res) {
            
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        
        if(image.getHeight() < size)
        {
            image = image.scaledHeight(size);
        
        }
        
         if(image.getHeight() > Display.getInstance().getDisplayHeight() / 2)
        {
            image = image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        
        }
         
         ScaleImageLabel imageScale = new ScaleImageLabel(image);
         imageScale.setUIID("Container");
         imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
         Label overLay = new Label("","ImageOverLay");
         
         
         
         
         Container page1 = 
                 LayeredLayout.encloseIn(
                         imageScale,
                         overLay,
                         BorderLayout.south(
                         BoxLayout.encloseY(
                         new SpanLabel(text,"LargeWhiteText"),
                                 FlowLayout.encloseIn(),
                                 spacer)
                         
                         )
                 );
         swipe.addTab("", res.getImage("social.jpg"), page1);

    }
    
    
    
    public void bindButtonSelection(Button btn, Label l)
    {
        btn.addActionListener(e -> 
        {
            if(btn.isSelected()){
                updateArrowPosition(btn,l);
            }
        });
    
    }

    private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
        l.getParent().repaint();
    }

    private void addButton(Image img,String dt, String content, int like, User u, Resources res ) {
        
        
     //   String rawContent = Jsoup.clean(content, new Whitelist());
        
       int height = Display.getInstance().convertToPixels(11.5f);
       int width = Display.getInstance().convertToPixels(14f);
       
       Button image = new Button(img.fill(width, height));
       image.setUIID("Label");
       
        
        Container cnt = BorderLayout.west(image);
            Label d = new Label(like+" likes","NewsTopLine"); 
            Label c = new Label(like+"\n","NewsTopLine"); 
            Label date = new Label(dt+"\n","NewsTopLine"); 
            
            //TextArea ta = new TextArea(content);
            //ta.setUIID("NewsTopLine");
            //ta.setEditable(false);
            
            //Delete btn
            
            Label del = new Label(" ");
            del.setUIID("NewsTopLine");
            Style deleteStyle = new Style(del.getUnselectedStyle());
            //deleteStyle.setFgColor(0xf21f1f);
            
            FontImage deleteImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, deleteStyle);
            del.setIcon(deleteImage);
            del.setTextPosition(RIGHT);
            
            
            Label likes = new Label("  ");
            
                    
            del.setUIID("NewsTopLine");
            Style likeStyle = new Style(likes.getUnselectedStyle());
            likeStyle.setFgColor(0xf21f1f);
            
            FontImage likeImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, likeStyle);
            likes.setIcon(likeImage);
            likes.setTextPosition(RIGHT);
            
            
            
            
//            del.addPointerPressedListener(l ->{
//                
//                Dialog dia = new Dialog("Delete");
//                if(dia.show("Delete","Are you sure?","Cancel","Yes"))
//                {
//                    dia.dispose();
//                        }
//                else
//                {
//                    if(ServicePost.getInstance().deletePost(p.getId()))
//                        new ListPostsForm(res).show();
//                refreshTheme();
//                
//                }
//                
//            });
            
            
        cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(date),BoxLayout.encloseX(likes,d),BoxLayout.encloseX(c, del)));
        
        
        add(cnt);
        
        
            
            
    }
    
}
