/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.entites;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class Formation {
         private int  idformation ;
           private String nomformation;
           private double nbrParticipants;
           private String sujetdeformation;

    public Formation() {
    }

    public Formation(int idformation, String nomformation, double nbrParticipants, String sujetdeformation) {
        this.idformation = idformation;
        this.nomformation = nomformation;
        this.nbrParticipants = nbrParticipants;
        this.sujetdeformation = sujetdeformation;
    }

    public Formation(String nomformation, double nbrParticipants, String sujetdeformation) {
        this.nomformation = nomformation;
        this.nbrParticipants = nbrParticipants;
        this.sujetdeformation = sujetdeformation;
    }
      public Formation(String nomformation,  String sujetdeformation ,double nbrParticipants) {
        this.nomformation = nomformation;
     
        this.sujetdeformation = sujetdeformation;
           this.nbrParticipants = nbrParticipants;
    }

    public Formation(String toString, String toString0, String toString1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getIdformation() {
        return idformation;
    }

    public void setIdformation(int idformation) {
        this.idformation = idformation;
    }

    public String getNomformation() {
        return nomformation;
    }

    public void setNomformation(String nomformation) {
        this.nomformation = nomformation;
    }

    public double getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(double nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    public String getSujetdeformation() {
        return sujetdeformation;
    }

    public void setSujetdeformation(String sujetdeformation) {
        this.sujetdeformation = sujetdeformation;
    }

    @Override
    public String toString() {
        return "Formation{" + "idformation=" + idformation + ", nomformation=" + nomformation + ", nbrParticipants=" + nbrParticipants + ", sujetdeformation=" + sujetdeformation + '}';
    }


           
}
