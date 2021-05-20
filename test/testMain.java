
import services.ServiceCommande;
//import utils.DataSource;
//import entities.Commande;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Asus
 */
public class testMain {
          /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
          
    ServiceCommande sp = new ServiceCommande();
       // sp.ajouter(new Commande("ddddddd", "ff"));
        
        sp.getInstance().AfficheCommandes();
        //sp.supprimer(new commande("dd"));
      // sp.modifier(new commande("dd", "occasionar"),"dd");
        //sp.afficher().forEach(System.out::println);
    }


}
