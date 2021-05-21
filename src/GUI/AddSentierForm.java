/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entities.Sentier;
import entities.Transport;
import services.SentierService;
import services.TransportService;

/**
 *
 * @author Fatma NL
 */
public class AddSentierForm extends Form{
    
    public AddSentierForm(Form previous) {
        
        setTitle("Ajouter un nouveau Sentier");
        setLayout(BoxLayout.y());
        
        TextField tfNom = new TextField("","Nom");
        TextField tfDuree= new TextField("", "Durée");
        TextField tfDistance= new TextField("", "Distance");
        TextField tfDifficulte= new TextField("", "Difficulte");
        TextField tfDepart= new TextField("", "Depart");
        TextField tfDestination= new TextField("", "Destination");
        Button btnValider = new Button("Ajouter");
        
        btnValider.addActionListener((evt) -> {
                if ((tfNom.getText().length()==0)||(tfDuree.getText().length()==0)||(tfDistance.getText().length()==0)
                        ||(tfDifficulte.getText().length()==0)||(tfDepart.getText().length()==0)||(tfDestination.getText().length()==0))
                    Dialog.show("Alert", "Veuillez remplir tous les champs!", "OK", null);
                else
                {
                    try {
                        Sentier s = new Sentier
                        (tfNom.getText(), tfDuree.getText(), tfDistance.getText(),
                         tfDifficulte.getText(), tfDepart.getText(), tfDestination.getText());
                        if(new SentierService().addSentier(s)){
                            Dialog.show("Success","Ajouté avec succès", "OK", null);
                            new TransportListForm(this).show();}
                        else
                            Dialog.show("ERROR", "Server error", "OK", null);
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "champ invalide", "OK", null);
                    }
                    
                }
                
        });
        
        addAll(tfNom,tfDuree,tfDistance,tfDifficulte,tfDepart,tfDestination,btnValider);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
}
