/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netdev.mindspace.services;

import com.netdev.mindspace.entites.Formation;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.netdev.mindspace.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class Formationservice {
    public boolean resultOK;
    private ConnectionRequest req;
    private static Formationservice  instance;

     public ArrayList<Formation> formations;

    
    public Formationservice() 
    {
        req = new ConnectionRequest();
    }
    
    public static Formationservice getInstance() 
    {
        if (instance == null) 
        {
            instance = new Formationservice();
        }
        return instance;
    }
    
   


    public ArrayList<Formation> parseFormation(String jsonText) throws ParseException, IOException{
        {
        JSONParser j = new JSONParser();
                try
                {
                     formations = new ArrayList<>();

            Map<String, Object> formationsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) formationsListJson.get("root");
            for (Map<String, Object> obj : list) {
                    
                        Formation formation = new Formation(
                                   
                                    obj.get("nomformation").toString(),
                                    obj.get("sujetdeformation").toString(),
                                   (double) obj.get("nbrParticipants"));
                    
                                 
                        formations.add(formation);
                    }
                } catch (IOException ex)
                {
                    System.out.println("Error!!!" + ex.getMessage());
                    Dialog.show("Error!!!", ex.getMessage(), "OK", null);
                }
            return formations;
        }
    }
    
    public ArrayList<Formation> getAllChallenges()
    {
        String url = Statics.BASE_URL+"/mobile/displayFormations";
        System.out.println(url);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try 
                {
                    formations = parseFormation(new String(req.getResponseData()));
                    req.removeResponseListener(this);
                }
                catch (ParseException ex)
                {
                    System.out.println("error!!" + ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("error: " + ex.getMessage());
                }
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return formations;
    }
}
