/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.esprit.formulaire;


import Entity.User;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Lenovo
 */
class Welcome extends Form {

    Form current;

    public Welcome(User User, Resources res) {
        super(new LayeredLayout());
        getTitleArea().removeAll();
        getTitleArea().setUIID("Container");

        setTransitionOutAnimator(CommonTransitions.createUncover(CommonTransitions.SLIDE_HORIZONTAL, true, 400));

        Tabs walkthruTabs = new Tabs();
        walkthruTabs.setUIID("Container");
        walkthruTabs.getContentPane().setUIID("Container");
        walkthruTabs.getTabsContainer().setUIID("Container");
        walkthruTabs.hideTabs();
        
        ImageViewer img = new ImageViewer(res.getImage("unnamed.png").scaled(500, 500));
        //Image profilePic = res.getImage(""+User.getUserPhoto());
        Image mask = res.getImage("round-mask.png");
        //profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        //Label profilePicLabel = new Label( /*profilePic **/ "ProfilePicTitle");
        //profilePicLabel.setMask(mask.createMask());

        Label bottomSpace = new Label();

        Container tab1 = BorderLayout.centerAbsolute(BoxLayout.encloseY(
                BorderLayout.center(img),
                //profilePicLabel,
                new Label("Welcome " + User.getPrenom()+ " To HIKEAPP ", "WalkthruWhite"),
                new SpanLabel(""),
                bottomSpace
        ));
        tab1.setUIID("WalkthruTab1");

        walkthruTabs.addTab("", tab1);

        add(walkthruTabs);

        ButtonGroup bg = new ButtonGroup();
        Image unselectedWalkthru = res.getImage("unselected-walkthru.png");
        Image selectedWalkthru = res.getImage("selected-walkthru.png");
        RadioButton[] rbs = new RadioButton[walkthruTabs.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(CENTER);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        walkthruTabs.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Button skip = new Button("Start ");
        skip.setUIID("SkipButton");
        skip.addActionListener(
                new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        if (User != null) {

                            if (User.getRoles().equals("[ROLE_ADMIN, ROLE_USER]") || User.getRoles().equals("[ROLE_ADMIN]")) {

                                new AdminProfile(User, res).show();
                            } else if (User.getRoles().equals("[ROLE_USER]")) {
                                new UserProfile(User, res).show();
                            } else if (User.getRoles().equals("[ROLE_ORGANISATEUR], ROLE_USER]")) {

                                new OrganisateurProfile(User, res).show();
                            }
                        }else{
                                            Dialog.show("Alert", "", new Command("OK"));

                        }

                    }
                }
        );

        Container southLayout = BoxLayout.encloseY(
                radioContainer,
                skip
        );
        add(BorderLayout.south(
                southLayout
        ));

        // visual effects in the first show
        addShowListener(e -> {
//            notesPlaceholder.getParent().replace(notesPlaceholder, notesLabel, CommonTransitions.createFade(1500));
        });
    }

}
