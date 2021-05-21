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
import entities.Sentier;
import entities.Transport;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author Fatma NL
 */
public class SentierService {
    private ConnectionRequest request;
    private boolean responseResult;
    private ArrayList<Sentier> sentiers;

    public SentierService() {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addSentier(Sentier s) {
        String url = Statics.BASE_URL + "/api/addsentier";
        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("nomsentier", s.getNomsentier());
        requestData.put("duree", s.getDuree());
        requestData.put("distance", s.getDistance());
        requestData.put("difficulte", s.getDifficulte());
        requestData.put("departsentier", s.getDepartsentier());
        requestData.put("destinationsentier", s.getDestinationsentier());
        
        String jsonData = JSONParser.mapToJson(requestData);
        
        request.setUrl(url);
        request.setRequestBody(jsonData);
        request.setContentType("application/json; charset=UTF-8");
        request.setPost(true);
        
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 201;
                request.removeResponseListener(this);          
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;
    }
    
    public ArrayList<Sentier> getAllSentiers(){
        String url = Statics.BASE_URL+"/listesentier";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responseData = new String(request.getResponseData());
                sentiers = parseSentiers(responseData);
                request.removeResponseListener(this);
            }
        });
        NetworkManager netMananager = NetworkManager.getInstance();
        
        netMananager.addToQueueAndWait(request);
        return sentiers;
    }
    
    public ArrayList<Sentier> parseSentiers(String jsonText){
        try {
            sentiers=new ArrayList<>();
            JSONParser j = new JSONParser();
            StringReader reader = new StringReader(jsonText);
            Map<String,Object> tasksListJson = j.parseJSON(reader);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Sentier s = new Sentier();
                float id = Float.parseFloat(obj.get("idsentier").toString());
                s.setId((int)id);
                s.setNomsentier(obj.get("nomsentier").toString());
                s.setDuree(obj.get("duree").toString());
                s.setDistance(obj.get("distance").toString());
                s.setDifficulte(obj.get("difficulte").toString());
                s.setDepartsentier(obj.get("departsentier").toString());
                s.setDestinationsentier(obj.get("destinationsentier").toString());
                sentiers.add(s);
            }
            
        } catch (IOException ex) {
            
        }
        
        return sentiers;
    }
    
    public boolean updateSentier(Sentier s){
        String url = Statics.BASE_URL+"/api/updatesentier/"+s.getId() ;
        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("nomsentier", s.getNomsentier());
        requestData.put("duree", s.getDuree());
        requestData.put("distance", s.getDistance());
        requestData.put("difficulte", s.getDifficulte());
        requestData.put("departsentier", s.getDepartsentier());
        requestData.put("destinationsentier", s.getDestinationsentier());
        
        String jsonData = JSONParser.mapToJson(requestData);
        
        request.setUrl(url);
        request.setRequestBody(jsonData);
        request.setContentType("application/json; charset=UTF-8");
        request.setPost(true);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 200;
                request.removeResponseListener(this);          
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;
    }
    
    public boolean DeleteSentier(Sentier s) {
        String url = Statics.BASE_URL + "/api/deletesentier/" + s.getId();
        request.setUrl(url);
        request.setPost(false);
        request.setHttpMethod("DELETE");
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                responseResult = request.getResponseCode() == 204;
                request.removeResponseListener(this); 

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return responseResult;
    }
   

}
