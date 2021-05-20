/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

//import java.sql.Date;

/**
 *
 * @author Asus
 */
public class Commande {
      private String refcommande;
    private String datecommande ;

    private String etat;

    public Commande(String refcommande, String etat) {
        this.refcommande = refcommande;
        this.etat = etat;
    }

     public Commande(String refcommande) {
        this.refcommande = refcommande;
        
    }

    public Commande() {
    
    }

    public Commande(String toString, String toString0, String format, int i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

   
    public void setRefcommande(String refcommande) {
        this.refcommande = refcommande;
    }

    public void setDatecommande(String datecommande) {
        this.datecommande = datecommande;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getRefcommande() {
        return refcommande;
    }

    public String getDatecommande() {
        return datecommande;
    }

    public Commande(String refcommande, String datecommande, String etat) {
        this.refcommande = refcommande;
        this.datecommande = datecommande;
        this.etat = etat;
    }

    public String getEtat() {
        return etat;
    }

    @Override
    public String toString() {
        return "commande{" + "refcommande=" + refcommande + ", datecommande=" + datecommande + ", etat=" + etat + '}';
    }
      
}
