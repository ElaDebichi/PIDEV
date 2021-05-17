package com.mycompany.myapp.gui;

import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;

public class HomeForm extends BaseForm {

    Form current;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
    aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
    la méthode showBack*/
    
    public HomeForm(){
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("Home");
        setLayout(BoxLayout.y());
        
        //add(new Label("Choose an option"));
        Button btnAddTask = new Button("Add Task");
        Button btnListJob = new Button("List Job");
        Button btnListIntern = new Button("List Internship");
        Button btnAddIntern = new Button("Add Internship");

        btnAddTask.addActionListener(e -> new AddTaskForm(current).show());
        //btnListIntern.addActionListener(e -> new ListInternship(current).show());
        btnAddIntern.addActionListener(e -> new AddIntern(current).show());
        //btnListJob.addActionListener(e -> new ListJob(current).show());
        addAll(btnListJob, btnListIntern,btnAddIntern);
        
    }
}
