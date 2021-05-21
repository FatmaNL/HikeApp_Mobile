/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import jdk.nashorn.internal.runtime.Debug;

/**
 *
 * @author Fatma NL
 */
public class HomeForm extends Form{
    
    Form current;
    /*Garder traçe de la Form en cours pour la passer en paramètres 
    aux interfaces suivantes pour pouvoir y revenir plus tard en utilisant
    la méthode showBack*/
    
    public HomeForm() {
        current = this; //Récupération de l'interface(Form) en cours
        setTitle("HikeApp");
        setLayout(BoxLayout.y());

        add(new Label("Bienvenue"));
        Button btnListTransport = new Button("Liste des Transports");
        Button btnListSentiers = new Button("Liste des Sentiers");
        Button btnListEvenements = new Button("Liste des Evenements");

        btnListTransport.addActionListener(e -> new TransportListForm(current).show());
        btnListSentiers.addActionListener(e -> new SentierListForm(current).show());
        btnListEvenements.addActionListener(e -> new EvenementListForm(current).show());
        addAll(btnListTransport, btnListSentiers, btnListEvenements);

    }
    
}
