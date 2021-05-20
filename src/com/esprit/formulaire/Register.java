/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.formulaire;

import Entity.User;
import WebService.UserService;
import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Lenovo
 */
public class Register extends Form {

    Form current;
    private static User User;

    public Register(Form previous, Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
        Container welcome = FlowLayout.encloseCenter(
                new Label("Sign Up | ", "WelcomeWhite"),
                new Label("HIKEAPP", "WelcomeBlue")
        );

        getTitleArea().setUIID("Container");
        
        ComboBox sexe = new ComboBox();
        sexe.addItem("Homme");
        sexe.addItem("Femme");
        
        
        TextField email = new TextField(null, "Entrer votre email ", 20, TextField.EMAILADDR);
        TextField password = new TextField(null, "Entrer votre mot de passe ", 20, TextField.PASSWORD);
        TextField cin = new TextField(null, "Entrer votre cin", 20, TextField.NUMERIC);
        TextField nom = new TextField(null, "Entrer votre nom", 20, TextField.ANY);
        TextField prenom = new TextField(null, "Entrer votre prenom ", 20, TextField.ANY);
        TextField age = new TextField(null, "Entrer votre age", 20, TextField.NUMERIC);
        TextField adresse = new TextField(null, "Entrer votre adresse ", 20, TextField.ANY);
        TextField tel = new TextField(null, "Entrer votre numero de telephone", 20, TextField.NUMERIC);
        

        

        email.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        cin.getAllStyles().setMargin(LEFT, 0);
        nom.getAllStyles().setMargin(LEFT, 0);
        prenom.getAllStyles().setMargin(LEFT, 0);
        age.getAllStyles().setMargin(LEFT, 0);
        adresse.getAllStyles().setMargin(LEFT, 0);
        tel.getAllStyles().setMargin(LEFT, 0);
        
        

        Button RegisterButton = new Button("Register");
        RegisterButton.setUIID("LoginButton");

        Button login = new Button("Sign In");
        login.setUIID("CreateNewAccountButton");
        
        RegisterButton.addActionListener(new ActionListener() {

           @Override
            public void actionPerformed(ActionEvent evt) {

                if ((email.getText().length() == 0) || (password.getText().length() == 0) || (cin.getText().length() == 0) || (nom.getText().length() == 0) || (prenom.getText().length() == 0)  ) {
                    Dialog.show("Alert", "Vous devez remplir tous les champs", new Command("OK"));
                } else {
                    try {
                        
                         
                        User u = new User(Integer.parseInt(cin.getText()), nom.getText(), prenom.getText(), Integer.parseInt(age.getText()), (String) sexe.getSelectedItem(), adresse.getText(), Integer.parseInt(tel.getText()), email.getText(), password.getText());
                           if (UserService.getInstance().Register(u)) {
                            Dialog.show("Success", "Votre compte a été creer avec success ", new Command("OK"));
                            new Login(current, res).show();
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Cin , Age et Telephone doivent etre un nombre", new Command("OK"));
                    }

                }
            }
        });
        
        

    
        login.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                new Login(current, res).show();

            }
        });
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if (!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }

        
        
        Container by = BoxLayout.encloseY(
                spaceLabel,
                welcome,
                BorderLayout.center(email),
                BorderLayout.center(password),
                BorderLayout.center(cin),
                BorderLayout.center(nom),
                BorderLayout.center(prenom),
                BorderLayout.center(age),
                BorderLayout.center(sexe),
                BorderLayout.center(adresse),
                BorderLayout.center(tel),
                
                
                RegisterButton,
                login
        );
        add(BorderLayout.CENTER, by);

        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
    }

}
