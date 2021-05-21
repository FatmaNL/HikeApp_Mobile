/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import services.EvenementService;
import services.SentierService;

/**
 *
 * @author Fatma NL
 */
public class EvenementListForm extends Form{
    
    public EvenementListForm(Form previous) {
        super("Liste des Evenements", BoxLayout.y());
        
        Button btnAddEvenement = new Button("Ajouter Evenement");
        btnAddEvenement.addActionListener(e -> new AddEvenementForm(this).show());
        addAll(btnAddEvenement);
        
        EvenementService es = new EvenementService();
        SpanLabel sp = new SpanLabel();
        sp.setText(es.getAllEvenements().toString());
        this.add(sp);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
