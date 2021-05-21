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
import services.TransportService;

/**
 *
 * @author Fatma NL
 */
public class TransportListForm extends Form{
    
    public TransportListForm(Form previous) {
        super("Liste des Transports", BoxLayout.y()); 
        Button btnAddTransport = new Button("Ajouter Transport");
        Button btnEditTransport = new Button("Modifier Transport");
        Button btnDeleteTransport = new Button("Supprimer Transport");
        
        
        btnAddTransport.addActionListener(e -> new AddTransportForm(this).show());
        btnEditTransport.addActionListener(e -> new EditTransportForm(this).show());
        btnDeleteTransport.addActionListener(e -> new DeleteTransportForm(this).show());
        
        addAll(btnAddTransport, btnEditTransport, btnDeleteTransport);

        
        TransportService ts = new TransportService();
        SpanLabel sp = new SpanLabel();
        sp.setText(ts.getAllTransports().toString());
        this.add(sp);
        this.getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
