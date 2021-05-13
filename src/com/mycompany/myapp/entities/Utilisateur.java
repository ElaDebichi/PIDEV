/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author sofie
 */
public class Utilisateur {
  private   int    id_utilisateur ;
  private   String username;
  private   String nom ;
  private   String prenom ;
  private   String password ;
  private   String tel ;
  private   String adresse;
  private int  id_role ;
  private   int etat;
  private   String email;

    public Utilisateur(int id_utilisateur, String username, String nom, String prenom, String password, String tel, String adresse, int id_role, int etat, String email) {
        this.id_utilisateur = id_utilisateur;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.tel = tel;
        this.adresse = adresse;
        this.id_role = id_role;
        this.etat = etat;
        this.email = email;
    }

    public Utilisateur(int id_utilisateur, String username, String nom, String prenom, String password, String tel, String adresse, String email) {
        this.id_utilisateur = id_utilisateur;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.tel = tel;
        this.adresse = adresse;
        this.email = email;
    }

    public Utilisateur() {
    }

    public Utilisateur(String username, String nom, String prenom, String password, String tel, String adresse, String email) {
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.tel = tel;
        this.adresse = adresse;
        this.email = email;
    }

  

   

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getId_role() {
        return id_role;
    }

    public void setId_role(int id_role) {
        this.id_role = id_role;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Utilisateur{" + "id_utilisateur=" + id_utilisateur + ", username=" + username + ", nom=" + nom + ", prenom=" + prenom + ", password=" + password + ", tel=" + tel + ", adresse=" + adresse + ", id_role=" + id_role + ", etat=" + etat + ", email=" + email + '}';
    }

  
  
  
    
}
