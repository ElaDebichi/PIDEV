package com.mycompany.myapp.gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.mycompany.myapp.entities.Internship;
import com.mycompany.myapp.services.InternshipService;
import java.util.Date;

public class AddIntern extends Form{
    public AddIntern(Form previous) {
        
        setTitle("Add a new Internship");
        setLayout(BoxLayout.y());
        
        TextField tfLibelle = new TextField("","Libelle");
        TextField tfPoste = new TextField("","Poste");
        Picker date = new Picker();
        TextField tfDescription = new TextField("","Description");
        TextField tfNiveau = new TextField("","Niveau");
        TextField tfDuree = new TextField("","Duree");
        TextField tfCategorie = new TextField("", "Categorie");
        
        Button btnValider = new Button("Add");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfLibelle.getText().length()==0)||(tfPoste.getText().length()==0) || 
                        (date.getText().length()==0)||(tfDescription.getText().length()==0) || 
                        (tfNiveau.getText().length()==0)||(tfDuree.getText().length()==0) || 
                        (tfCategorie.getText().length()==0) )
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Internship i = new Internship(
                                tfLibelle.getText(), tfPoste.getText(), date.getText(), 
                                tfDescription.getText(), tfNiveau.getText(), Integer.parseInt(tfDuree.getText()), 
                                Integer.parseInt(tfCategorie.getText()));
                                //Integer.parseInt(tfStatus.getText()), tfName.getText());
                        if( InternshipService.getInstance().add(i))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                }
               
            }
        });
        
        addAll(tfLibelle,tfPoste,date,tfDescription,tfNiveau,tfDuree,tfCategorie,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
}
