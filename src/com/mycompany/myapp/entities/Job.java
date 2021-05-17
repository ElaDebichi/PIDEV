package com.mycompany.myapp.entities;

import java.util.Date;

public class Job extends Offre{
    
    private String contrat;
    private double salary;
    private int category ;
    private String cat;

    public Job() {
    }

    public Job(int id, String libelle, String post, Date dateExpiration, String description, String level, String contrat, double salary, String cat) {
        super(id,libelle, post, dateExpiration, description, level);
        this.contrat = contrat;
        this.salary = salary;
        this.cat = cat;
    }
    
    public Job(int id, String libelle, String post, Date dateExpiration, String description, String level, String contrat, double salary, int cat) {
        super(id,libelle, post, dateExpiration, description, level);
        this.contrat = contrat;
        this.salary = salary;
        this.category = cat;
    }
    
    public Job(int id,String libelle, String post, Date dateExpiration, String level, String contrat, double salary, String cat) {
        super(id,libelle, post, dateExpiration,level);
        this.contrat = contrat;
        this.salary = salary;
        this.cat = cat;
    }
    
    public Job(String libelle, String post, String d, String description, String level, String contrat, double salary, String cat) {
        super(libelle, post, d,level, description);
        this.contrat = contrat;
        this.salary = salary;
        this.cat = cat;
    }
    
    //<editor-fold defaultstate="collapsed" desc="GETTERS & SETTERS">
    public String getContrat() {
        return contrat;
    }

    public void setContrat(String contrat) {
        this.contrat = contrat;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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
        return "Job{ " + super.toString() + ", contrat=" + contrat + ", salary=" + salary + ", category= " + cat + "}\n";
    }
    
      
}
