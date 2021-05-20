/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.formulaire;

import Entity.User;
import WebService.UserService;

import com.codename1.components.ImageViewer;
import com.codename1.messaging.Message;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.esprit.formulaire.Login;

import com.esprit.formulaire.SideMenuBaseForm;

/**
 *
 * @author Lenovo
 */
public class EditUser extends SideMenuBaseForm {
    
    Form current;
    public EditUser(User u, Resources res) {

        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("bleu.png");
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());

        tb.getUnselectedStyle().setBgImage(tintedImage);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);

        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent
                = BorderLayout.north(
                        BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH,
                        FlowLayout.encloseIn(
                                new Label("Profile: " +u.getPrenom()+u.getNom(), "WelcomeBlue")
                        //                                new Label("list", "WelcomeWhite")
                        ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);

        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(separator);

        ImageViewer img = new ImageViewer(res.getImage("issue.png"));
        Label nom = new Label("Nom :");
        Label prenom = new Label("Prenom:");
        Label email = new Label("Email :");
        Label age = new Label("Age :");
        Label adresse = new Label("adresse :");

        TextField nomm = new TextField();
        nomm.setUIID("TextFieldF");
        nomm.setText(u.getNom());

        TextField prenomm = new TextField();
        prenomm.setUIID("TextFieldF");
        prenomm.setText(u.getPrenom());
        
        TextField emaill = new TextField();
        emaill.setUIID("TextFieldF");
        emaill.setText(u.getEmail());
        
        TextField agee = new TextField();
        agee.setUIID("TextFieldF");
        agee.setText(Integer.toString(u.getAge()));

        TextField adressee = new TextField();
        adressee.setUIID("TextFieldF");
        adressee.setText(u.getAdresse());
        
        
       

        Container Container = new Container(BoxLayout.y());
        Container.addAll(nom, nomm, prenom, prenomm, email, emaill, age, agee, adresse, adressee);
        add(img);
        add(Container);

        Container ButtonContainer = new Container(new FlowLayout(Component.CENTER, Component.CENTER));

        Button Edit = new Button("Edit");
        ButtonContainer.add(Edit);

        add(ButtonContainer);
        revalidate();
        Edit.addActionListener(ev -> {

            u.setNom(nomm.getText());
            u.setPrenom(prenomm.getText());
            u.setEmail(emaill.getText());
            u.setAge(Integer.parseInt(agee.getText()));
            u.setAdresse(adressee.getText());

            if (UserService.getInstance().EditUser(u)) {
                Dialog.show("Success", "User Profile Edited Successfully", new Command("OK"));
                new Login(current,res).show();
            } else {
                Dialog.show("ERROR", "Server error", new Command("OK"));
            }

        });

        setupSideMenu(u, res);

    }

    

    @Override
    protected void showOtherForm(User u, Resources res) {
    }

}
