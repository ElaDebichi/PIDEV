/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.entites;

import java.util.Date;

/**
 *
 * @author spicy
 */
public class Event {
    
    private int id,nbr_participants,nbr_max,id_emp;
    private String nom,adresse,titre;
    private String date;
    private String img;
    
    
    public Event() {
        this.nbr_participants=0;
    }
     public Event(int id,String date,String adresse,String titre, String nom,int nbr_participants,int nbr_max,String img) {
        this.id = id;
        this.nbr_max=nbr_max;
        this.nbr_participants=nbr_participants;
        this.nom = nom;
        this.titre = titre;
        this.adresse=adresse;
        this.date=date;
        this.img = img;

        
    }
     public Event(int id,String date,String adresse,String titre, String nom,int nbr_participants,int nbr_max,String img,int id_emp) {
        this.id = id;
        this.nbr_max=nbr_max;
        this.nbr_participants=nbr_participants;
        this.nom = nom;
        this.titre = titre;
        this.adresse=adresse;
        this.date=date;
        this.img = img;

        
    }
      public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTitre() {
        return titre;
    }
    
    
    public void setTitre(String titre) {
        this.titre = titre;
    }


    public void setAdresse (String adresse) {
        this.adresse = adresse;
    }
    
    public String getAdresse() {
        return adresse;
    }
    
    
    public void setDate (String date) {
        this.date = date;
    }
    
    public String getDate() {
        return date;
    }
    
    public void setNbr_participants (int nbr_participants) {
        this.nbr_participants = nbr_participants;
    }
    
    public int getNbr_participants () {
       return nbr_participants;
    }
   
     
    public void setNbr_max (int nbr_max) {
        this.nbr_max = nbr_max;
    }
   
    
    public int getNbr_max () {
       return nbr_max;
    }
    
    
   
public void setImg (String img) {
        this.img = img;
    }
   
    
    public String getImg () {
       return this.img;
    }
    
    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", titre=" + titre + ", description=" + nom +""
                + "Date=" + date + "Adresse="+ adresse +"nbr_participants= " +nbr_participants 
                +"nbr_max= " +nbr_max +"img=" + img +'}';
    }
    
}
