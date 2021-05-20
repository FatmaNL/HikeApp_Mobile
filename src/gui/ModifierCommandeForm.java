/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Commande;
import services.ServiceCommande;


/**
 *
 * @author Asus
 */
public class ModifierCommandeForm extends BaseForm {
    Form current; 
    public ModifierCommandeForm(Resources res, Commande c){
        super("Newsfeed",BoxLayout.y());
 
    Toolbar tb = new Toolbar(true);
     current = this;
     setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("");
        getContentPane().setScrollVisible(false);
        
        
        //super.addSideMenu(res);
        TextField Etat = new TextField(c.getEtat(),"Etat",20,TextField.ANY);
        Etat.setUIID("NewsTopLine");
        //Etat.setSingleLineTextArea(true);
        Button btnModifier =new Button("modifier");
            btnModifier.setUIID("Button");
            btnModifier.addPointerPressedListener(e ->{
                c.setEtat(Etat.getText());
                
       
            if (ServiceCommande.getInstance().ModifierCommande(c)){
                 new AfficheCommandeForm(res).show();
            }
             });
            Button btnAnnuler =new Button("Annuler");
            btnAnnuler.addActionListener(e -> {
                new AfficheCommandeForm(res).show();
            });
            Label l1 = new Label("");
            Label l2 = new Label("");
            Label l3 = new Label("");
            Label l4 = new Label("");
            Label l5 = new Label("");
            Container content = BoxLayout.encloseY(Etat,
                    btnModifier,btnAnnuler);
            add(content);
            show();
            
    }
}
