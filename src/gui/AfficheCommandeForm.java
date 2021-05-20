/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import entities.Commande;
import java.util.ArrayList;
import services.ServiceCommande;

/**
 *
 * @author Asus
 */
public class AfficheCommandeForm extends BaseForm{
    public AfficheCommandeForm(Resources res){
            super("Newsfeed",BoxLayout.y());
 Form current; 
    Toolbar tb = new Toolbar(true);
     current = this;
     setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Liste des commandes");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e ->{
            
        });
        Tabs swipe = new Tabs();
        Label S1= new Label();
        Label S2= new Label();
        addTab(swipe,S1,res.getImage("hiking-header.jpg"),"","",res);
        //
        
          swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, S1, S2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        RadioButton mesListes = RadioButton.createToggle("Mes Commandes", barGroup);
        mesListes.setUIID("SelectBar");
        RadioButton liste = RadioButton.createToggle("Autres", barGroup);
        liste.setUIID("SelectBar");
        RadioButton partage = RadioButton.createToggle("Commander", barGroup);
        partage.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");


        mesListes.addActionListener((e) -> {
               InfiniteProgress ip = new InfiniteProgress();
        final Dialog ipDlg = ip.showInifiniteBlocking();
        
         AfficheCommandeForm a = new AfficheCommandeForm(res);
            a.show();
            refreshTheme();
        });

        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(3, mesListes, liste, partage),
                FlowLayout.encloseBottom(arrow)
        ));

        partage.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            UpdateArrowPosition(partage, arrow);
        });
        bindButtonSelection(mesListes, arrow);
        bindButtonSelection(liste, arrow);
        bindButtonSelection(partage, arrow);
        // special case for rotation
        addOrientationListener(e -> {
            UpdateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
 ArrayList<Commande>list= ServiceCommande.getInstance().AfficheCommandes();
    for (Commande c : list){
        String urlImage = "hiking-header.jpg";
        Image placeHolder = Image.createImage(120, 90);
        EncodedImage enc = EncodedImage.createFromImage(placeHolder, false);
        URLImage ur = URLImage.createToFileSystem(enc, urlImage, urlImage, URLImage.RESIZE_SCALE);
    addButton( ur,c,res);
    ScaleImageLabel image = new ScaleImageLabel(ur);
    Container cim = new Container();
    image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
}

        
}
   private void addTab(Tabs swipe,Label spacer, Image image, String string, String text, Resources res) {
        int size = Math.min(Display.getInstance().getDisplayWidth(),Display.getInstance().getDisplayHeight());
        
        if (image.getHeight()< size ){
            image=image.scaledHeight(size);
        }
        
          if (image.getHeight()> Display.getInstance().getDisplayHeight() / 2){
              
            image=image.scaledHeight(Display.getInstance().getDisplayHeight() / 2);  
        }
          ScaleImageLabel imageScale = new ScaleImageLabel(image);
          imageScale.setUIID("container");
          imageScale.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
          
          Label overLay = new Label("","ImageOverlay");
          Container page1 = 
                  LayeredLayout.encloseIn(imageScale,
                          overLay,
                          BorderLayout.south(BoxLayout.encloseY(
                          new SpanLabel(text,"LargeWhiteText"),
                                  
                                  spacer
                                  ))
                  );
          swipe.addTab("",res.getImage("hiking-header.jpg"), page1);
          
    }

    public void bindButtonSelection(Button btn , Label l){
        btn.addActionListener(e -> {
        if (btn.isSelected()){
            UpdateArrowPosition(btn,l);
        }
    });
        
    }
            
  

    private void UpdateArrowPosition(Button btn, Label l) {
    l.getUnselectedStyle().setMargin(LEFT, btn.getX() + btn.getWidth() / 2 - l.getWidth() / 2);
    l.getParent().repaint();
    }

    private void addButton(Image img, Commande c, Resources res) {
          int height= Display.getInstance().convertToPixels(11.5f);
        int width= Display.getInstance().convertToPixels(14f);
        Button image = new Button(img.fill(width, height));
        image.setUIID("Label");
        
   Container cnt = BorderLayout.west(image);
  Label reff = new Label("Reference:"+c.getRefcommande(),"NewsTopLine2");
  Label datee = new Label("Date:"+c.getDatecommande(),"NewsTopLine2");
  Label etttat = new Label("Etat:"+c.getEtat(),"NewsTopLine2");
  
  
  Label Supp = new Label(" ");
  Supp.setUIID("NewsTopline");
  Style suppStyle = new Style(Supp.getUnselectedStyle());
  suppStyle.setFgColor(0xf21f1f);
        FontImage suppIMage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, suppStyle);
        Supp.setIcon(suppIMage);
        Supp.setTextPosition(RIGHT);
        Supp.addPointerPressedListener(l->{
            Dialog dig = new Dialog("Suppression");
            if (dig.show("Suppression","Are you sure","Annuler","Oui")){
                dig.dispose();
            }
            else {
                dig.dispose();
                if (ServiceCommande.getInstance().SupprimerCommande(c.getRefcommande())){
                    new AfficheCommandeForm(res).show();
                }
            }
        });
        
        
        Label Modifier = new Label(" ");
  Modifier.setUIID("NewsTopline");
  Style ModifierStyle = new Style(Modifier.getUnselectedStyle());
  ModifierStyle.setFgColor(0xf21f1f);
        FontImage ModifierIMage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, suppStyle);
        Modifier.setIcon(ModifierIMage);
         Modifier.setTextPosition(LEFT);
      
         
         Modifier.addPointerPressedListener(l->{
            new ModifierCommandeForm(res,c).show();
        });
            cnt.add(BorderLayout.CENTER, BoxLayout.encloseY(BoxLayout.encloseX(datee)
                    ,BoxLayout.encloseX(reff),BoxLayout.encloseX(etttat,Modifier,Supp)));
  
  
   add(cnt);
    }

  
}
