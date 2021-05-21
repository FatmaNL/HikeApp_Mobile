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
import entities.Evenement;
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
public class EvenementService {
    private ConnectionRequest request;
    private boolean responseResult;
    private ArrayList<Evenement> evenements;

    public EvenementService() {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addEvenement(Evenement e) {
        String url = Statics.BASE_URL + "/api/addevenement";
        
        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("nomevenement", e.getNomevenement());
        requestData.put("depart", e.getDepart());
        requestData.put("destination", e.getDestination());
        requestData.put("nbparticipant", e.getNbparticipant());
        requestData.put("dateevenement", e.getDateevenement());
        requestData.put("duree", e.getDuree());
        requestData.put("prix", e.getPrix());
        requestData.put("programme", e.getProgramme());
        requestData.put("contact", e.getContact());
        requestData.put("infos", e.getInfos());
        requestData.put("type", e.getType());
        requestData.put("circuit", e.getCircuit());
        
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
    
    public ArrayList<Evenement> getAllEvenements(){
        String url = Statics.BASE_URL+"/listeevenement";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responseData = new String(request.getResponseData());
                evenements = parseEvenements(responseData);
                request.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return evenements;
    }
    
    public ArrayList<Evenement> parseEvenements(String jsonText){
        try {
            evenements=new ArrayList<>();
            JSONParser j = new JSONParser();
            StringReader reader = new StringReader(jsonText);
            Map<String,Object> tasksListJson = j.parseJSON(reader);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Evenement e = new Evenement();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int)id);
                e.setNomevenement(obj.get("nomevenement").toString());
                e.setDepart(obj.get("depart").toString());
                e.setDestination(obj.get("destination").toString());
                e.setNbparticipant(((int)Float.parseFloat(obj.get("nbparticipant").toString())));
                //e.setDateevenement(obj.get("datevenement").toString());
                e.setDuree(((int)Float.parseFloat(obj.get("duree").toString())));
                e.setPrix(((float)Float.parseFloat(obj.get("prix").toString())));
                e.setProgramme(obj.get("programme").toString());
                e.setContact(obj.get("contact").toString());
                e.setInfos(obj.get("infos").toString());
                e.setType(obj.get("type").toString());
                
                //String circuit= obj.get("circuit").toString();
                //if (circuit!=null){
                //    e.setCircuit(circuit);
                //}
                //e.setCircuit(obj.get("circuit").toString());
                
                evenements.add(e);
            }
            
        } catch (IOException ex) {
            
        }
        
        return evenements;
    }

}
