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
public class DeleteSentierForm extends Form{
    
    public DeleteSentierForm(Form previous) {
        
        setTitle("Supprimer Sentier");
        setLayout(BoxLayout.y());
        
        TextField tfId = new TextField("","ID");
        Button btnValider = new Button("Supprimer");
        
        btnValider.addActionListener((evt) -> {
                    try {
                        Sentier s = new Sentier();
                        s.setId(Integer.parseInt(tfId.getText()));
                        if(new SentierService().DeleteSentier(s))
                            Dialog.show("Success","Suprrimé avec succès", "OK", null);
                        else
                            Dialog.show("ERROR", "Server error", "OK", null);
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "champ invalide", "OK", null);
                    }
        });
        
        addAll(tfId,btnValider);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());// Revenir vers l'interface précédente
                
    }
}
