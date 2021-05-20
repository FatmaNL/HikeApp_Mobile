/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package WebService;

import Entity.User;
import Utils.Utils;
import static Utils.Utils.BASE_URL;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Lenovo
 */
public class UserService {
    
    
    
    ArrayList<User> users;
    public static UserService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public User User = new User();

    private UserService() {
        req = new ConnectionRequest();
    }

    public static UserService getInstance() {

        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User parseUser(String jsonText) {
   
        User UserL = new User();
        try {
            JSONParser j = new JSONParser();

            Map<String, Object> UserListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

               
                
                float cin = Float.parseFloat(UserListJson.get("cin").toString());
                UserL.setCin((int) (cin));
                UserL.setNom(UserListJson.get("nom").toString());
                System.out.println(UserListJson.get("nom").toString());
                UserL.setPrenom(UserListJson.get("prenom").toString());
                float age = Float.parseFloat(UserListJson.get("age").toString());
                UserL.setAge((int) (age));
                UserL.setSexe(UserListJson.get("sexe").toString());
                UserL.setAdresse(UserListJson.get("adresse").toString());
                float tel = Float.parseFloat(UserListJson.get("tel").toString());
                UserL.setTel((int) (tel));
                UserL.setEmail(UserListJson.get("email").toString());
                UserL.setPassword(UserListJson.get("password").toString());
                UserL.setRoles(UserListJson.get("roles").toString());
                UserL.setStatus(UserListJson.get("status").toString());
            
        } catch (IOException ex) {  
            Dialog.show("ERROR", "identifiants incorrect ", null);
        }
        
        return UserL;
    }

    
    public boolean Register(User u ) {
        String url = Utils.BASE_URL + "user/signup?email="+u.getEmail()+"&password="+u.getPassword()+"&nom="+u.getNom()+"&prenom="+u.getPrenom()+"&cin="+u.getCin()+"&age="+u.getAge()+"&sexe="+u.getSexe()+"&adresse="+u.getAdresse()+"&tel="+u.getTel();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    
    public User Login(String username,String password) {
        String url =BASE_URL +"user/signin?email="+username+"&password="+password;
        System.out.println("urlll ="+BASE_URL);
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                User = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return User;
    }

     public ArrayList<User> getAllUsers() {
        String url = Utils.BASE_URL + "users";
                        System.out.println("urlll ="+BASE_URL);

        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parsingUsers(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    
     
      public ArrayList<User> parsingUsers(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            Map<String, Object> usersListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            List<Map<String, Object>> list = (List<Map<String, Object>>) usersListJson.get("root");
            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                User UserL = new User();
               
                 float cin = Float.parseFloat(obj.get("cin").toString());
                UserL.setCin((int) (cin));
                UserL.setNom(obj.get("nom").toString());
                UserL.setPrenom(obj.get("prenom").toString());
                float age = Float.parseFloat(obj.get("age").toString());
                UserL.setAge((int) (age));
                UserL.setSexe(obj.get("sexe").toString());
                UserL.setAdresse(obj.get("adresse").toString());
                float tel = Float.parseFloat(obj.get("tel").toString());
                UserL.setTel((int) (tel));
                UserL.setEmail(obj.get("email").toString());
                UserL.setPassword(obj.get("password").toString());
                UserL.setRoles(obj.get("roles").toString());
                UserL.setStatus(obj.get("status").toString());
                
                //Ajouter la tâche extraite de la réponse Json à la liste
                users.add(UserL);

            }

        } catch (IOException ex) {

        }
        /*
         A ce niveau on a pu récupérer une liste des tâches à partir
         de la base de données à travers un service web
        
         */
        return users;
    }
    
     public boolean EditUser(User u) {
        String url = Utils.BASE_URL + "user/edituser?cin="+u.getCin()+"&nom="+u.getNom()+"&prenom="+u.getPrenom()+"&email="+u.getEmail()+"&age="+u.getAge()+"&adresse="+u.getAdresse();
        req.setUrl(url);// Insertion de l'URL de notre demande de connexion
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
      
     }
}
