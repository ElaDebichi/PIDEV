package com.netdev.mindspace.entites;

import java.util.Date;

public class Internship extends Offre{
    
    private int duration;
    private String discr;
    private int category ;
    private String cat ;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    
    

    public Internship() {
    }
    
    public Internship(int id,String libelle, String post, Date dateExpiration, String description, String level, int duration, int category) {
        super(id,libelle, post, dateExpiration,description,level);
        this.duration = duration;
        this.category = category;
    }
    
    public Internship(String libelle, String post, String dateExpiration, String description, String level, int duration, int category) {
        super(libelle, post, dateExpiration,description,level);
        this.duration = duration;
        this.category = category;
    }

    public Internship(int id, String libelle, String post, Date dateExpiration, String level, int duration, String cat) {
        super(id, libelle,post,dateExpiration,level);
        this.duration = duration;
        this.cat = cat;
    }
    
    public Internship(int id, String libelle, String post, Date dateExpiration, String description, String level, int duration, String cat) {
        super(id, libelle,post,dateExpiration,description,level);
        this.duration = duration;
        this.cat = cat;
    }
    
    public Internship( String libelle, String post, String d, String description, String level, int duration, String cat) {
        super(libelle,post,d,description,level);
        this.duration = duration;
        this.cat = cat;
    }
    
    //<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public String getDiscr(){
        return discr;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
    //</editor-fold>
    
    @Override
    public String toString() {
        return "Internship{ " + super.toString() + ", duration=" + duration + ", category= "+ cat + "}\n";
    }
    
}

