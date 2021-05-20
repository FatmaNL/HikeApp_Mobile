/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Commande;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.statics;

/**
 *
 * @author Asus
 */
public class ServiceCommande {
    
    
    public static ServiceCommande instance=null;
    public static boolean resultOK = true;
    private ConnectionRequest req;

    public ServiceCommande() {
         req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }

    public void ajouter(Commande t) {
        String url = statics.BASE_URL + "ajouterC?refcommande="+ t.getRefcommande()+"&etat=ds" +t.getEtat(); //création de l'URL
        ConnectionRequest cnx = new ConnectionRequest(url);
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener((e) -> {
           String str =new String(req.getResponseData()) ;
            System.out.println("data=="+str);
            
    });
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
   
    public ArrayList<Commande> AfficheCommandes( ){
       ArrayList<Commande> result = new ArrayList<>();
       String url=statics.BASE_URL+"afficheCd";
       req.setUrl(url);
       req.addResponseListener(new ActionListener<NetworkEvent>() {
           @Override
           public void actionPerformed(NetworkEvent evt) {
           JSONParser jsonp;
           jsonp=new JSONParser() ;
          try{
               Map<String,Object>mapCommandes =jsonp.parseJSON(new CharArrayReader(new String( req.getResponseData()).toCharArray()));
               List<Map<String,Object>> listOfMaps=(List<Map<String,Object>>)mapCommandes.get("root");
               for ( Map<String,Object> obj :listOfMaps){
                   Commande cd = new Commande();
                   String refcommande = obj.get("refcommande").toString();
                   String etat = obj.get("etat").toString();
                   cd.setEtat((String)etat);
                   cd.setRefcommande((String)refcommande);
                   String DateConverter = obj.get("datecommande").toString().substring(obj.get("datecommande").toString().indexOf("timestamp")+ 10 , obj.get("datecommande").toString().lastIndexOf("}"));
                   Date currentTime = new Date(Double.valueOf(DateConverter).longValue()* 1000);
                   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                   String dateString = formatter.format(currentTime);
                   cd.setDatecommande(dateString);
                   result.add(cd);
               }
           }
           catch(Exception ex){
               ex.printStackTrace();
           }
           
           }
       });
               NetworkManager.getInstance().addToQueueAndWait(req);
               return result;
    }
    
    public Commande RechercheCommande(String refcommande,Commande c){
        String url=statics.BASE_URL+"/rechercheCM?"+refcommande;
    
        req.setUrl(url);
        
        String str =new String(req.getResponseData()) ;
        req.addResponseListener(((evt) -> {
      JSONParser jsonp =new JSONParser() ;
           try{
               Map<String,Object>obj =jsonp.parseJSON(new CharArrayReader(new String(str).toCharArray()));
               
               c.setEtat(obj.get("etat").toString());
               c.setRefcommande(obj.get("refcommande").toString());
               c.setDatecommande(obj.get("datecommande").toString());

           }catch(IOException ex){
               System.out.println("error related to sql"+ex.getMessage());
           }
           System.out.println("data ==="+str);
    }));
                NetworkManager.getInstance().addToQueueAndWait(req);
return c;
    }

    public boolean SupprimerCommande(String refcommande){
        String url = statics.BASE_URL + "deleteCommande?refcommande="+ refcommande;
         req.setUrl(url);
     req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                req.removeResponseCodeListener(this);
                
               }
        });
     NetworkManager.getInstance().addToQueueAndWait(req);
     return resultOK;
    }
    
    public boolean ModifierCommande(Commande c){
        String url = statics.BASE_URL + "updateCommande?refcommande="+c.getRefcommande()+"&etat="+ c.getEtat();
        req.setUrl(url);
       req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
              resultOK = req.getResponseCode() == 200 ;
               req.removeResponseCodeListener(this);
               }
        });
            NetworkManager.getInstance().addToQueueAndWait(req);
     return resultOK;

    }
}
