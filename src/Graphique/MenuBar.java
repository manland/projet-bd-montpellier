package Graphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuBar extends JMenuBar implements ActionListener, MouseListener {
	
	private MainWindow main_window;
	private JMenu fichier;
	private JMenu aller_a;
	private JMenu options;
	private JMenu aide;
	private JDialog dialog_aproposde;
	private static final long serialVersionUID = 1L;

	public MenuBar(MainWindow main_window) {
		this.main_window = main_window;
		buildMenu();
	}
	
	public void buildMenu() {
		ImageIcon icon = null;
//		 icon = Utiles.createImageIcon("/"+Utiles.getTheme()+"/Images/Menu/fichier.png");
		fichier = creerMenu(Traductions.menuBar(Options.getLangue(), "constructeur_fichier"), KeyEvent.VK_F, Traductions.menuBar(Options.getLangue(), "constructeur_fichier_tootltip"));
        creerSousMenu(fichier, Traductions.menuBar(Options.getLangue(), "constructeur_nouvelletab"), "Nouvelle Tab", KeyEvent.VK_N, Traductions.menuBar(Options.getLangue(), "constructeur_nouvelletab_tooltip"), icon);
        fichier.addSeparator();
        creerSousMenu(fichier, Traductions.menuBar(Options.getLangue(), "fichier_deconnexion"), "deconnection", KeyEvent.VK_D,  Traductions.menuBar(Options.getLangue(), "fichier_deconnexion_tooltip"), icon);
        creerSousMenu(fichier, Traductions.menuBar(Options.getLangue(), "fichier_quitter"), "quitter", KeyEvent.VK_Q,  Traductions.menuBar(Options.getLangue(), "fichier_quitter_tooltip"), icon);

        aller_a = creerMenu(Traductions.menuBar(Options.getLangue(), "constructeur_allera"), KeyEvent.VK_A, Traductions.menuBar(Options.getLangue(), "constructeur_allera_tooltip"));
        creerSousMenu(aller_a, "Racine", "racine", KeyEvent.VK_R,  "Allez à la racine.", icon);
        aller_a.addSeparator();
        creerSousMenu(aller_a, "Personnes", "personnes", KeyEvent.VK_P, Traductions.menuBar(Options.getLangue(), "allera_personnes_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_personnels"), "personnels", KeyEvent.VK_P, Traductions.menuBar(Options.getLangue(), "allera_personnels_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_adherents"), "adherents", KeyEvent.VK_H, Traductions.menuBar(Options.getLangue(), "allera_adherents_tooltip"), icon);
        aller_a.addSeparator();
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_squash"), "squash", KeyEvent.VK_S, Traductions.menuBar(Options.getLangue(), "allera_squash_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_gym"), "gym", KeyEvent.VK_G, Traductions.menuBar(Options.getLangue(), "allera_gym_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_casier"), "casier", KeyEvent.VK_C, Traductions.menuBar(Options.getLangue(), "allera_casier_tooltip"), icon);
        aller_a.addSeparator();
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_vente"), "vente", KeyEvent.VK_V, Traductions.menuBar(Options.getLangue(), "allera_vente_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_location"), "location", KeyEvent.VK_L, Traductions.menuBar(Options.getLangue(), "allera_location_tooltip"), icon);
        aller_a.addSeparator();
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_clientinternet"), "client_internet", KeyEvent.VK_I, Traductions.menuBar(Options.getLangue(), "allera_clientinternet_tooltip"), icon);
        aller_a.addSeparator();
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_options"), "options", KeyEvent.VK_O, Traductions.menuBar(Options.getLangue(), "allera_options_tooltip"), icon);
        creerSousMenu(aller_a, Traductions.menuBar(Options.getLangue(), "allera_deconnection"), "deconnection", KeyEvent.VK_D, Traductions.menuBar(Options.getLangue(), "allera_deconnection_tooltip"), icon);
        
        options = creerMenu(Traductions.menuBar(Options.getLangue(), "constructeur_options"), KeyEvent.VK_O, Traductions.menuBar(Options.getLangue(), "constructeur_options_tooltip"));
        JMenu skins = creerMenu(Traductions.menuBar(Options.getLangue(), "options_skins"), KeyEvent.VK_S, Traductions.menuBar(Options.getLangue(), "options_skins_tooltip"));
        File file = new File("bin/Skins/");
	    if (file.isDirectory()) {
	      File [] files = file.listFiles();
	      for (int i=0; i<files.length; i++) {
	          creerSousMenu(skins, files[i].getName(), files[i].getName(), 0, files[i].getName(), icon);
	      }
	    }
	    options.add(skins);
	    JMenu langue = creerMenu(Traductions.menuBar(Options.getLangue(), "options_langues"), KeyEvent.VK_L, Traductions.menuBar(Options.getLangue(), "options_langues_tooltip"));
        creerSousMenu(langue, "Français", "fr", 0, "Choisissez la langue française.", icon);
        creerSousMenu(langue, "English", "en", 0, "Choisissez la langue anglaise.", icon);
        creerSousMenu(langue, "Italien", "it", 0, "Choisissez la langue italienne.", icon);
        creerSousMenu(langue, "Albanais", "al", 0, "Choisissez la langue albanaise.", icon);
	    options.add(langue);
	    options.addSeparator();
	    creerSousMenu(options, Traductions.menuBar(Options.getLangue(), "options_options"), "options", KeyEvent.VK_P, Traductions.menuBar(Options.getLangue(), "options_options_tooltip"), icon);
        
        aide = creerMenu(Traductions.menuBar(Options.getLangue(), "constructeur_aide"), 0, Traductions.menuBar(Options.getLangue(), "constructeur_aide_tooltip"));
        creerSousMenu(aide, Traductions.menuBar(Options.getLangue(), "aide_doc"), "documentation", KeyEvent.VK_U, Traductions.menuBar(Options.getLangue(), "aide_doc_tooltip"), icon);
        aide.addSeparator();
        creerSousMenu(aide, Traductions.menuBar(Options.getLangue(), "aide_aproposde"), "aproposde", KeyEvent.VK_E, Traductions.menuBar(Options.getLangue(), "aide_aproposde_tooltip"), icon);
	}
	
	public JMenu creerMenu(String titre, int mnemonic, String accessible_description) {
		JMenu menu = new JMenu(titre);
		menu.setMnemonic(mnemonic);
		menu.getAccessibleContext().setAccessibleDescription(accessible_description);
		menu.setToolTipText(accessible_description);
        add(menu);
        return menu;
	}
	
	public JMenuItem creerSousMenu(JMenu pere, String titre, String action_commande, int mnemonic, String accessible_description, ImageIcon icon) {
		JMenuItem menuItem = new JMenuItem(titre, icon);
		menuItem.setMnemonic(mnemonic);
		menuItem.getAccessibleContext().setAccessibleDescription(accessible_description);
		menuItem.setToolTipText(accessible_description);
        pere.add(menuItem);
        menuItem.addActionListener(this);
        menuItem.setActionCommand(action_commande);
        return menuItem;
	}
	
	public void updateMenus() {
		fichier.removeAll();
		aller_a.removeAll();
		aide.removeAll();
		removeAll();
		buildMenu();
		validate();
	}
	
	public void aproposde() {
		dialog_aproposde = new JDialog();
		dialog_aproposde.setMinimumSize(new Dimension(300, 200));
		JPanel interieur_fenetre = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre, BoxLayout.PAGE_AXIS);
		interieur_fenetre.setLayout(layout);
		dialog_aproposde.add(interieur_fenetre);
		dialog_aproposde.setVisible(true);
		dialog_aproposde.pack();
		
		JLabel label_apropos_de_1 = new JLabel(Traductions.menuBar(Options.getLangue(), "aproposde_titre"));
		interieur_fenetre.add(label_apropos_de_1);
		JLabel label_apropos_de_2 = new JLabel(Traductions.menuBar(Options.getLangue(), "aproposde_1"));
		interieur_fenetre.add(label_apropos_de_2);
		JLabel label_apropos_de_3 = new JLabel(Traductions.menuBar(Options.getLangue(), "aproposde_2"));
		interieur_fenetre.add(label_apropos_de_3);
		JLabel label_apropos_de_4 = new JLabel(Traductions.menuBar(Options.getLangue(), "aproposde_3"));
		interieur_fenetre.add(label_apropos_de_4);
		
		JButton boutton = new JButton(Traductions.menuBar(Options.getLangue(), "aproposde_boutton"));
		boutton.addMouseListener(this);
		interieur_fenetre.add(boutton);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//fichier
		if(event.getActionCommand().equals("Nouvelle Tab")) {
			main_window.ajouterTab();
		}
		else if(event.getActionCommand().equals("quitter")) {
			
		}
		//aller_a
		else if(event.getActionCommand().equals("racine")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_racine"), new PremiereVue(main_window));
		}
		else if(event.getActionCommand().equals("personnes")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_personnes"), new VuePersonnes(main_window));
		}
		else if(event.getActionCommand().equals("personnels")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_personnels"), new VuePersonnels(main_window));
		}
		else if(event.getActionCommand().equals("adherents")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_adherents"), new VueAdherents(main_window));
		}
		else if(event.getActionCommand().equals("squash")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_squash"), new VueSquash(main_window));
		}
		else if(event.getActionCommand().equals("gym")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_gym"), new VueGym(main_window));
		}
		else if(event.getActionCommand().equals("casier")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_casiers"), new VueCasier(main_window));
		}
		else if(event.getActionCommand().equals("vente")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_vente"), new VueVente(main_window));
		}
		else if(event.getActionCommand().equals("location")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_location"), new VueLocation(main_window));
		}
		else if(event.getActionCommand().equals("client_internet")) {
			try {
				Runtime.getRuntime().exec(Options.getExplorateurInternet()+" "+Options.getUrlClientInternet());
			} 
			catch (IOException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
		}
		else if(event.getActionCommand().equals("options")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_options"), new VueOptions(main_window));
		}
		else if(event.getActionCommand().equals("deconnection")) {
			main_window.changerTab(Traductions.menuBar(Options.getLangue(), "cmd_connexionbd"), new VueConnexionBd(main_window));
		}
		//Options
		else if(event.getActionCommand().equals("fr")) {
			Options.setLangue("fr");
			Options.enregistrerOptions();
			updateMenus();
			main_window.ajouterTab();
		}
		else if(event.getActionCommand().equals("en")) {
			Options.setLangue("en");
			Options.enregistrerOptions();
			updateMenus();
			main_window.ajouterTab();
		}
		else if(event.getActionCommand().equals("it")) {
			Options.setLangue("it");
			Options.enregistrerOptions();
			updateMenus();
			main_window.ajouterTab();
		}
		else if(event.getActionCommand().equals("al")) {
			Options.setLangue("al");
			Options.enregistrerOptions();
			updateMenus();
			main_window.ajouterTab();
		}
		//Aide
		else if(event.getActionCommand().equals("documentation")) {
			try {
				Runtime.getRuntime().exec(Options.getExplorateurInternet()+" "+Options.getUrlClientInternet()+"/aide.php");
			} 
			catch (IOException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
		}
		else if(event.getActionCommand().equals("aproposde")) {
			aproposde();
		}
		else {//Skin
			Utiles.setTheme(event.getActionCommand());
			Options.enregistrerOptions();
			main_window.ajouterTab();
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton boutton = null;
		try {
			boutton = (JButton)event.getSource();
		}
		catch(ClassCastException e) {
			return;
		}
		if(boutton.isEnabled()) {
			dialog_aproposde.setVisible(false);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
