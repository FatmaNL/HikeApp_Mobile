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
import entities.Transport;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import utils.DataSource;
import utils.Statics;

/**
 *
 * @author Fatma NL
 */
public class TransportService {

    private ConnectionRequest request;
    private boolean responseResult;
    private ArrayList<Transport> transports;
    public String result = "";

    public TransportService() {
        request = DataSource.getInstance().getRequest();
    }
    
    public boolean addTransport(Transport t) {
        String url = Statics.BASE_URL + "/api/addtransport";
        
        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("type", t.getType());
        requestData.put("volumemax", t.getVolumemax());
        requestData.put("nombre_transports", t.getNombre_transports());
        
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
    
    public ArrayList<Transport> getAllTransports(){
        String url = Statics.BASE_URL+"/listetransport";
        request.setUrl(url);
        request.setPost(false);
        request.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                String responseData = new String(request.getResponseData());
                transports = parseTransports(responseData);
                request.removeResponseListener(this);
            }
        });
        
        NetworkManager netMananager = NetworkManager.getInstance();
        
        netMananager.addToQueueAndWait(request);
        return transports;
    }
    
    public ArrayList<Transport> parseTransports(String jsonText){
        try {
            transports=new ArrayList<>();
            JSONParser j = new JSONParser();
            StringReader reader = new StringReader(jsonText);
            Map<String,Object> tasksListJson = j.parseJSON(reader);
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            
            for(Map<String,Object> obj : list){
                Transport t = new Transport();
                float id = Float.parseFloat(obj.get("idtransport").toString());
                t.setId((int)id);
                t.setType(obj.get("type").toString());
                t.setVolumemax(((int)Float.parseFloat(obj.get("volumemax").toString())));
                t.setNombre_transports(((int)Float.parseFloat(obj.get("nombre_transports").toString())));
                transports.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        
        return transports;
    }
    
    public boolean updateTransport(Transport t){
        String url = Statics.BASE_URL+"/api/updatetransport/"+t.getId() ;
        Map<String, Object> requestData = new LinkedHashMap<>();
        requestData.put("type", t.getType());
        requestData.put("volumemax", t.getVolumemax());
        requestData.put("nombre_transports", t.getNombre_transports());
        
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
    
    public boolean DeleteTransport(Transport t) {
        String url = Statics.BASE_URL + "/api/deletetransport/" + t.getId();
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
