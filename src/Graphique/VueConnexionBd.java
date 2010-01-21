package Graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Coeur.SQL;

public class VueConnexionBd extends JPanel implements MouseListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	private JTextField textField_nom;
	private JPasswordField textField_pass;
	private JTextField textField_adresse;
	private JCheckBox checkBox_sauvegarder_infos;
	private JPanel panel_interieur;
	private JLabel erreur;
	private JButton boutton_valider;
	private JTextField textField_proprietaire;
	private JDialog dialog_proprietaire;

	public VueConnexionBd(MainWindow main_window) {
		super(new GridBagLayout());
		setBackground(new Color(60, 60, 200));
		GridBagConstraints c = new GridBagConstraints();
		this.main_window = main_window;
		panel_interieur = new JPanel(new GridLayout(7, 2));
		panel_interieur.setBorder(BorderFactory.createTitledBorder(Traductions.vueConnexionBd(Options.getLangue(), "border_connexion")));
		panel_interieur.setOpaque(false);
		panel_interieur.setMaximumSize(new Dimension(200, 200));
		c.gridx = 10;
		add(panel_interieur, c);
		//Pseudo
		JLabel label_nom = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "pseudo")+" : ");
		panel_interieur.add(label_nom);
		textField_nom = new JTextField(20);
		textField_nom.setText(Options.getPseudoOracle());
		textField_nom.addKeyListener(this);
		panel_interieur.add(textField_nom);
		//Pass
		JLabel label_pass = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "passe")+" : ");
		panel_interieur.add(label_pass);
		textField_pass = new JPasswordField(20);
		textField_pass.setText(Options.getPasseOracle());
		textField_pass.addKeyListener(this);
		panel_interieur.add(textField_pass);
		//Serveur Adresse
		JLabel label_adresse = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "adresse")+" : ");
		panel_interieur.add(label_adresse);
		textField_adresse = new JTextField(Options.getServeurOracle(), 20);
		textField_adresse.addKeyListener(this);
		panel_interieur.add(textField_adresse);
		//Enregsitrer sur pc
		checkBox_sauvegarder_infos = new JCheckBox(Traductions.vueConnexionBd(Options.getLangue(), "enregistrer"));
		checkBox_sauvegarder_infos.setSelected(true);
		checkBox_sauvegarder_infos.setOpaque(false);
		add(checkBox_sauvegarder_infos, c);
		//Bouttons
		boutton_valider = new JButton(Traductions.vueConnexionBd(Options.getLangue(), "valider"));
		boutton_valider.setMaximumSize(new Dimension(30 , 30));
		boutton_valider.addMouseListener(this);
		add(boutton_valider, c);
		//Erreur
    	erreur = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "erreur"));
    	erreur.setVisible(false);
    	erreur.setBackground(new Color(255, 0, 0));
    	erreur.setForeground(new Color(255, 0, 0));
    	add(erreur, c);
    	
    	if(Options.getProprietaire().equals("")) {
    		dialogProprietaire();
    	}
    	else {
    		SQL.setProprietaire(Options.getProprietaire());
    	}
	}
	
	public void verifierPseudoPassAdresse() {
		SQL.setUtilisateur(textField_nom.getText());
		new String();
		SQL.setMotDePasse(String.copyValueOf(textField_pass.getPassword()));
		SQL.setUrl(Options.getServeurOracleDebut()+textField_adresse.getText());
		try {
			SQL.sqlConnexion();
			if(!SQL.tablesInstallees()) {
				SQL.creerTable();
			}
        } 
		catch (Exception e) {
        	textField_nom.setBackground(new Color(255, 0, 0));
        	textField_nom.setText("");
        	textField_pass.setBackground(new Color(255, 0, 0));
        	textField_pass.setText("");
        	erreur.setVisible(true);
            return;
        }
        setVisible(false);
        if(checkBox_sauvegarder_infos.isSelected()) {
        	Options.setPseudoOracle(textField_nom.getText());
        	Options.setPasseOracle(String.copyValueOf(textField_pass.getPassword()));
        	Options.setServeurOracle(textField_adresse.getText());
        	Options.enregistrerOptions();
        }
        main_window.changerTab(Traductions.vueConnexionBd(Options.getLangue(), "racine"), new PremiereVue(main_window));
	}
	
	public void dialogProprietaire() {
		dialog_proprietaire = new JDialog();
		dialog_proprietaire.setTitle(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_titre"));
		
		JPanel interieur_fenetre = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre, BoxLayout.PAGE_AXIS);
		interieur_fenetre.setLayout(layout);
		dialog_proprietaire.add(interieur_fenetre);
		
		JPanel panel_texte_explicatif = new JPanel();
		BoxLayout layout1 = new BoxLayout(panel_texte_explicatif, BoxLayout.PAGE_AXIS);
		panel_texte_explicatif.setLayout(layout1);
		JLabel label_texte_explicatif_1 = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_label1"));
		JLabel label_texte_explicatif_2 = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_label2"));
		JLabel label_texte_explicatif_3 = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_label3"));
		JLabel label_texte_explicatif_4 = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_label4"));
		panel_texte_explicatif.add(label_texte_explicatif_1);
		panel_texte_explicatif.add(label_texte_explicatif_2);
		panel_texte_explicatif.add(label_texte_explicatif_3);
		panel_texte_explicatif.add(label_texte_explicatif_4);
		interieur_fenetre.add(panel_texte_explicatif);
		
		JPanel panel_propretaire = new JPanel();
		BoxLayout layout2 = new BoxLayout(panel_propretaire, BoxLayout.LINE_AXIS);
		panel_propretaire.setLayout(layout2);
		JLabel label = new JLabel(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_propretaire")+" : ");
		panel_propretaire.add(label);
		textField_proprietaire = new JTextField("");
		panel_propretaire.add(textField_proprietaire);
		interieur_fenetre.add(panel_propretaire);
		
		JPanel panel_boutton = new JPanel();
		JButton boutton = new JButton(Traductions.vueConnexionBd(Options.getLangue(), "dialogproprietaire_valider"));
		boutton.addMouseListener(this);
		panel_boutton.add(boutton);
		interieur_fenetre.add(panel_boutton);
		
		dialog_proprietaire.setVisible(true);
		dialog_proprietaire.pack();
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton valider = (JButton)event.getSource();
		if(valider == boutton_valider) {
			if(valider.getText().equals(Traductions.vueConnexionBd(Options.getLangue(), "valider"))) {
				verifierPseudoPassAdresse();
			}
		}
		else {
			Options.setProprietaire(textField_proprietaire.getText());
			Options.enregistrerOptions();
			SQL.setProprietaire(textField_proprietaire.getText());
			dialog_proprietaire.setVisible(false);
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

	@Override
	public void keyPressed(KeyEvent event) {
		if(event.getKeyCode() == 10) {
			verifierPseudoPassAdresse();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}
}
