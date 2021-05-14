/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.entites;

import java.util.Date;

/**
 *
 * @author hp
 */
public class User {
    private int id;
    private String password;
    private int phone;
    private String address;
    private String town;
    private String fb;
    private String linkdin;
    private String description;
    private String img;
     private int nbr_follow;
    private String roles;
    private String company;
    private String categorie;
    private String discr;
     private String nom;
    private String prenom;
    private Date date_naissance;
    private String niv_etude;
    private String type_candidat;
    private int etat;
    private String block;

    public User(int id, String password, int phone, String address, String town, String fb, String linkdin, String description, String img, int nbr_follow, String roles, String company, String categorie, String discr, String nom, String prenom, Date date_naissance, String niv_etude, String type_candidat, int etat, String block) {
        this.id = id;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.town = town;
        this.fb = fb;
        this.linkdin = linkdin;
        this.description = description;
        this.img = img;
        this.nbr_follow = nbr_follow;
        this.roles = roles;
        this.company = company;
        this.categorie = categorie;
        this.discr = discr;
        this.nom = nom;
        this.prenom = prenom;
        this.date_naissance = date_naissance;
        this.niv_etude = niv_etude;
        this.type_candidat = type_candidat;
        this.etat = etat;
        this.block = block;
    }

    public User() {
    }
    
     public User(String nom , String prenom , String email , String town, String fb , String linkdin, String password, int phone) {
         this.nom=nom;
         this.prenom=prenom;
         this.address=email;
         this.town=town;
         this.fb=fb;
         this.linkdin=linkdin;
         this.password=password;
         this.phone=phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getFb() {
        return fb;
    }

    public void setFb(String fb) {
        this.fb = fb;
    }

    public String getLinkdin() {
        return linkdin;
    }

    public void setLinkdin(String linkdin) {
        this.linkdin = linkdin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getNbr_follow() {
        return nbr_follow;
    }

    public void setNbr_follow(int nbr_follow) {
        this.nbr_follow = nbr_follow;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDiscr() {
        return discr;
    }

    public void setDiscr(String discr) {
        this.discr = discr;
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

    public Date getDate_naissance() {
        return date_naissance;
    }

    public void setDate_naissance(Date date_naissance) {
        this.date_naissance = date_naissance;
    }

    public String getNiv_etude() {
        return niv_etude;
    }

    public void setNiv_etude(String niv_etude) {
        this.niv_etude = niv_etude;
    }

    public String getType_candidat() {
        return type_candidat;
    }

    public void setType_candidat(String type_candidat) {
        this.type_candidat = type_candidat;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", password=" + password + ", phone=" + phone + ", address=" + address + ", town=" + town + ", fb=" + fb + ", linkdin=" + linkdin + ", description=" + description + ", img=" + img + ", nbr_follow=" + nbr_follow + ", roles=" + roles + ", company=" + company + ", categorie=" + categorie + ", discr=" + discr + ", nom=" + nom + ", prenom=" + prenom + ", date_naissance=" + date_naissance + ", niv_etude=" + niv_etude + ", type_candidat=" + type_candidat + ", etat=" + etat + ", block=" + block + '}';
    }
    
    
    
    
}
