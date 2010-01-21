package Graphique;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Coeur.SQL;

public class MainWindow extends JPanel {

	private JTabbedPane tabbedPane;
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		super(new GridLayout(1, 1));
	    
		Options.chargerOptions();
		
	    tabbedPane = new JTabbedPane();
	    
	    //Création fenetre de connexion bd
	    VueConnexionBd fenetre_connexion_bd = new VueConnexionBd(this);
	    ajouterTab(Traductions.mainWindow(Options.getLangue(), "constructeur_connexionbd"), "", null, fenetre_connexion_bd, 0);
	    
	    //Add the tabbed pane to this panel.
        add(tabbedPane);
        
        //On ajoute les scrollPanes
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}
    
    public void ajouterTab(String titre, String tooltip, ImageIcon icon, JComponent component, int mnemonic) {
	    tabbedPane.addTab(titre, icon, component, tooltip);
	    tabbedPane.setMnemonicAt(tabbedPane.getTabCount()-1, mnemonic);
	    tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
    }
    
    public void ajouterTab() {
    	if(SQL.estConnecte()) {
    		tabbedPane.addTab(Traductions.mainWindow(Options.getLangue(), "ajoutertab_racine"), Utiles.createImageIcon(Utiles.getTheme()+"/Images/Tab.png"), new PremiereVue(this), "");
    	}
    	else {
    	    ajouterTab(Traductions.mainWindow(Options.getLangue(), "ajoutertab_connexionbd"), "", null, new VueConnexionBd(this), 0);
    	}
    	tabbedPane.setSelectedIndex(tabbedPane.getTabCount()-1);
    }

    public void changerTab(String titre_tab, Component c) {
    	tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), titre_tab);
    	tabbedPane.setComponentAt(tabbedPane.getSelectedIndex(), c);
    }
    
    public JTabbedPane getTabPane() {
    	return tabbedPane;
    }
	
}
