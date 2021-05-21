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
import entities.Transport;
import services.TransportService;

/**
 *
 * @author Fatma NL
 */
public class DeleteTransportForm extends Form{
    
    public DeleteTransportForm(Form previous) {
        
        setTitle("Supprimer Transport");
        setLayout(BoxLayout.y());
        
        TextField tfId = new TextField("","ID");
        Button btnValider = new Button("Supprimer");
        
        btnValider.addActionListener((evt) -> {
                    try {
                        Transport t = new Transport();
                        t.setId(Integer.parseInt(tfId.getText()));
                        new TransportService().DeleteTransport(t);
                        Dialog.show("Success","Suprrimé avec succès", "OK", null);
                        new TransportListForm(this).show();
                        
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "champ invalide", "OK", null);
                    }
        });
        
        addAll(tfId,btnValider);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
}
