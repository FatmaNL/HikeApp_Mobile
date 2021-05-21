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
public class EditTransportForm extends Form{
    
    public EditTransportForm(Form previous) {
        
        setTitle("Modifier Transport");
        setLayout(BoxLayout.y());
        
        TextField tfId = new TextField("","ID");
        TextField tfType = new TextField("","Type");
        TextField tfVolume= new TextField("", "Volume Max");
        TextField tfNbtransports= new TextField("", "Nombre des transports");
        Button btnValider = new Button("Modifier");
        
        btnValider.addActionListener((evt) -> {
                if ((tfType.getText().length()==0)||(tfVolume.getText().length()==0)
                        ||(tfNbtransports.getText().length()==0)||(tfId.getText().length()==0))
                    Dialog.show("Alert", "Veuillez remplir tous les champs!", "OK", null);
                else
                {
                    try {
                        Transport t = new Transport
                        (Integer.parseInt(tfId.getText()),tfType.getText(), Integer.parseInt(tfVolume.getText()), Integer.parseInt(tfNbtransports.getText()));
                        if(new TransportService().updateTransport(t)){
                            Dialog.show("Success","Modifié avec succès", "OK", null);
                            new TransportListForm(this).show();}
                        else
                            Dialog.show("ERROR", "Server error", "OK", null);
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "champ invalide", "OK", null);
                    }
                    
                }
                
        });
        
        addAll(tfId,tfType,tfVolume,tfNbtransports,btnValider);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
    
}
