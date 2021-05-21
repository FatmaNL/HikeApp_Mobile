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
import services.SentierService;
/**
 *
 * @author Fatma NL
 */
public class SentierListForm extends Form{
    
    public SentierListForm(Form previous) {
        super("Liste des Sentiers", BoxLayout.y());
        
        Button btnAddSentier = new Button("Ajouter Sentier");
        Button btnEditSentier = new Button("Modifier Sentier");
        Button btnDeleteSentier = new Button("Supprimer Sentier");
        btnAddSentier.addActionListener(e -> new AddSentierForm(this).show());
        btnEditSentier.addActionListener(e -> new EditSentierForm(this).show());
        btnDeleteSentier.addActionListener(e -> new DeleteSentierForm(this).show());
        addAll(btnAddSentier, btnEditSentier, btnDeleteSentier);
        
        SentierService ss = new SentierService();
        SpanLabel sp = new SpanLabel();
        sp.setText(ss.getAllSentiers().toString());
        this.add(sp);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
