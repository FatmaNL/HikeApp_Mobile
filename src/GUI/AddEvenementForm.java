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
import entities.Evenement;
import entities.Sentier;
import services.EvenementService;
import services.SentierService;

/**
 *
 * @author Fatma NL
 */
public class AddEvenementForm extends Form {
    
    public AddEvenementForm(Form previous) {
        
        setTitle("Ajouter un nouvel Evenement");
        setLayout(BoxLayout.y());
        
        TextField tfNom = new TextField("","Nom");
        TextField tfDepart= new TextField("", "Depart");
        TextField tfDestination= new TextField("", "Destination");
        TextField tfParticipants= new TextField("", "Nombre des participants");
        TextField tfDate= new TextField("", "Date");
        //Picker datePicker = new Picker();
        //datePicker.setType(Display.PICKER_TYPE_DATE);
        TextField tfDuree= new TextField("", "Durée");
        TextField tfPrix = new TextField("","Prix");
        TextField tfProgramme= new TextField("", "Programme");
        TextField tfContact= new TextField("", "Contact");
        TextField tfInfos= new TextField("", "Infos");
        TextField tfType= new TextField("", "Type");
        TextField tfCircuit= new TextField("", "Circuit");
        Button btnValider = new Button("Ajouter");
        
        btnValider.addActionListener((evt) -> {
                if ((tfNom.getText().length()==0)||(tfDepart.getText().length()==0)||(tfDestination.getText().length()==0)
                        ||(tfParticipants.getText().length()==0)||(tfDate.getText().length()==0)||(tfDuree.getText().length()==0)
                        ||(tfPrix.getText().length()==0)||(tfProgramme.getText().length()==0)||(tfContact.getText().length()==0)
                        ||(tfInfos.getText().length()==0)||(tfType.getText().length()==0)||(tfCircuit.getText().length()==0))
                    Dialog.show("Alert", "Veuillez remplir tous les champs!", "OK", null);
                else
                {
                    try {
                        Evenement e = new Evenement
                        (tfNom.getText(), tfDepart.getText(), tfDestination.getText(),
                         Integer.parseInt(tfParticipants.getText()), tfDate.getText(), Integer.parseInt(tfDuree.getText()),
                         Float.parseFloat(tfPrix.getText()), tfProgramme.getText(), tfContact.getText(),
                         tfInfos.getText(), tfType.getText(), tfCircuit.getText());
                        if(new EvenementService().addEvenement(e))
                            Dialog.show("Success","Ajouté avec succès", "OK", null);
                        else
                            Dialog.show("ERROR", "Server error", "OK", null);
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "champ invalide", "OK", null);
                    }
                    
                }
                
        });
        
        addAll(tfNom,tfDepart,tfDestination,tfParticipants,tfDate,tfDuree,tfPrix,tfProgramme,tfContact,tfInfos,tfType,tfCircuit, btnValider);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
}
