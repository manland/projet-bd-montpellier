package Graphique;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Coeur.Mat.Location;
import Coeur.Mat.Locations;
import Coeur.Mat.LocationsListener;
import Coeur.Mat.MaterielLocation;
import Coeur.Mat.MaterielsLocations;
import Coeur.Mat.MaterielsLocationsListener;
import Coeur.Mat.TypeMaterielLoue;
import Coeur.Personne.Adherent;
import Coeur.Personne.Personne;
import Coeur.Personne.Personnes;

public class VueLocation extends JPanel implements MaterielsLocationsListener, LocationsListener, FocusListener, MouseListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	private MaterielsLocations materiels_locations;
	private Locations locations;
	private JSplitPane splitPane;
	private JPanel panel_location;
	private String[][] list_locations;
	private JTable table_location;
	private JButton boutton_vente_home;
	private JButton boutton_vente_add;
	private JButton boutton_vente_modifier;
	private JButton boutton_vente_supprimer;
	private JButton boutton_vente_facture;
	private JPanel panel_matos;
	private String[][] list_matos;
	private JTable table_matos;
	private JButton boutton_matos_home;
	private JButton boutton_matos_add;
	private JButton boutton_matos_modifier;
	private JButton boutton_matos_effacer;
	private JDialog fenetre_ajouter_modifier_location;
	private JPanel interieur_fenetre_location;
	private JComboBox comboBox_location_personne;
	private Vector<JComboBox> vector_type;
	private Vector<JButton> vector_boutton_plus;
	private Vector<JLabel> vector_prix;
	private JComboBox comboBox_reduction;
	private JLabel prix_total;
	private JDialog fenetre_ajouter_modifier_matos;
	private JPanel interieur_fenetre_matos;
	private JTextField textField_matos_type;
	private JTextField textField_matos_desc;
	private JTextField textField_matos_prix;
	private JTextField textField_matos_etat;

	public VueLocation(MainWindow main_window) {
		super(new BorderLayout());
		this.main_window = main_window;
		try {
			materiels_locations = new MaterielsLocations();
			locations = new Locations();
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		catch (ClassNotFoundException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		materiels_locations.addMaterielsLocationsListener(this);
		locations.addLocationsListener(this);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		buildPanelLocation();
		buildPanelMatos();
		add(splitPane);
	}

	public void buildPanelLocation() {
		try{splitPane.remove(panel_location);}catch(NullPointerException ee){}
		panel_location = new JPanel();
		panel_location.setMinimumSize(new Dimension(50, 1));
		panel_location.setBorder(BorderFactory.createTitledBorder("Ventes"));

		BoxLayout layout = new BoxLayout(panel_location, BoxLayout.PAGE_AXIS);
		panel_location.setLayout(layout);
		String [] columnsNames = {"N°", "Date", "Heure début", "Heure fin", "Personne", "Prix"};
		ArrayList<Location> list_locations_temp = locations.getLocations();
		list_locations = new String[list_locations_temp.size()][6];
		for(int i=0; i<list_locations_temp.size(); i++) {
			Personne personne = null;
			try {
				personne = new Personne(list_locations_temp.get(i).getIdPersonne());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list_locations[i][0] = ""+list_locations_temp.get(i).getNumLocation();
			list_locations[i][1] = Utiles.dateToString(list_locations_temp.get(i).getDateLocation());
			list_locations[i][2] = ""+list_locations_temp.get(i).getHeureDebut();
			list_locations[i][3] = ""+list_locations_temp.get(i).getHeureFin();
			list_locations[i][4] = personne.getIdPersonne()+" : "+personne.getNom()+" "+personne.getPrenom();
			list_locations[i][5] = ""+list_locations_temp.get(i).getPrixPaye();
		}
		table_location = new JTable(list_locations, columnsNames);
		table_location.addFocusListener(this);
		table_location.addMouseListener(this);
		JScrollPane scroll_pane_vente = new JScrollPane(table_location);
		table_location.setFillsViewportHeight(true);
		panel_location.add(scroll_pane_vente);
		JPanel panel_vente_fin = buildPanelLocationBouttons();
		panel_location.add(panel_vente_fin);
		
		splitPane.setTopComponent(panel_location);
	}
	
	public JPanel buildPanelLocationBouttons() {
		JPanel panel_location_bouttons = new JPanel(new GridLayout(1, 0));
		boutton_vente_home = new JButton("Home");
		boutton_vente_home.setHorizontalTextPosition(0);
		boutton_vente_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_home.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png"));
		boutton_vente_home.addMouseListener(this);
		panel_location_bouttons.add(boutton_vente_home);
		boutton_vente_add = new JButton("Ajouter");
		boutton_vente_add.setHorizontalTextPosition(0);
		boutton_vente_add.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_add.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/ajouter.png"));
		boutton_vente_add.addMouseListener(this);
		panel_location_bouttons.add(boutton_vente_add);
		boutton_vente_modifier = new JButton("Modifier");
		boutton_vente_modifier.setHorizontalTextPosition(0);
		boutton_vente_modifier.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_modifier.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/modifier.png"));
		boutton_vente_modifier.setEnabled(false);
		boutton_vente_modifier.addMouseListener(this);
		panel_location_bouttons.add(boutton_vente_modifier);
		boutton_vente_supprimer = new JButton("Supprimer");
		boutton_vente_supprimer.setHorizontalTextPosition(0);
		boutton_vente_supprimer.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_supprimer.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/supprimer.png"));
		boutton_vente_supprimer.setEnabled(false);
		boutton_vente_supprimer.addMouseListener(this);
		panel_location_bouttons.add(boutton_vente_supprimer);
		boutton_vente_facture = new JButton("Facture");
		boutton_vente_facture.setHorizontalTextPosition(0);
		boutton_vente_facture.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_facture.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/facture.png"));
		boutton_vente_facture.setEnabled(false);
		boutton_vente_facture.addMouseListener(this);
		panel_location_bouttons.add(boutton_vente_facture);
		return panel_location_bouttons;
	}
	
	public void buildPanelMatos() {
		try{splitPane.remove(panel_matos);}catch(NullPointerException ee){}
		panel_matos = new JPanel();
		panel_matos.setMinimumSize(new Dimension(50, 1));
		panel_matos.setBorder(BorderFactory.createTitledBorder("Matériels"));

		BoxLayout layout = new BoxLayout(panel_matos, BoxLayout.PAGE_AXIS);
		panel_matos.setLayout(layout);
		String [] columnsNames = {"N°", "Type materiel", "Date d'achat", "Etat", "Description", "Prix"};
		ArrayList<MaterielLocation> list_matos_temp = materiels_locations.getMaterielsLocations();
		if(list_matos_temp.size() == 0) {//Pour ne pas ajouter de vente s'il n'y a pas de matos
			boutton_vente_add.setEnabled(false);
		}
		else {
			boutton_vente_add.setEnabled(true);
		}
		list_matos = new String[list_matos_temp.size()][6];
		for(int i=0; i<list_matos_temp.size(); i++) {
			list_matos[i][0] = ""+list_matos_temp.get(i).getNumMateriel();
			list_matos[i][1] = list_matos_temp.get(i).getTypeMateriel();
			list_matos[i][2] = ""+Utiles.dateToString(list_matos_temp.get(i).getDateAchat());
			list_matos[i][3] = list_matos_temp.get(i).getEtat();
			list_matos[i][4] = list_matos_temp.get(i).getDescription();
			list_matos[i][5] = ""+list_matos_temp.get(i).getPrixBase();
		}
		table_matos = new JTable(list_matos, columnsNames);
		table_matos.addFocusListener(this);
		JScrollPane scroll_pane_matos = new JScrollPane(table_matos);
		table_matos.setFillsViewportHeight(true);
		panel_matos.add(scroll_pane_matos);
		JPanel panel_matos_fin = buildPanelMatosBouttons();
		panel_matos.add(panel_matos_fin);
		
		splitPane.setBottomComponent(panel_matos);
	}
	
	public JPanel buildPanelMatosBouttons() {
		JPanel panel_matos_bouttons = new JPanel(new GridLayout(1, 0));
		boutton_matos_home = new JButton("Home");
		boutton_matos_home.setHorizontalTextPosition(0);
		boutton_matos_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_home.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png"));
		boutton_matos_home.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_home);
		boutton_matos_add = new JButton("Ajouter");
		boutton_matos_add.setHorizontalTextPosition(0);
		boutton_matos_add.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_add.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/ajouter.png"));
		boutton_matos_add.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_add);
		boutton_matos_modifier = new JButton("Modifier");
		boutton_matos_modifier.setHorizontalTextPosition(0);
		boutton_matos_modifier.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_modifier.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/modifier.png"));
		boutton_matos_modifier.setEnabled(false);
		boutton_matos_modifier.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_modifier);
		boutton_matos_effacer = new JButton("Effacer");
		boutton_matos_effacer.setHorizontalTextPosition(0);
		boutton_matos_effacer.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_effacer.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/location/supprimer.png"));
		boutton_matos_effacer.setEnabled(false);
		boutton_matos_effacer.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_effacer);
		return panel_matos_bouttons;
	}
	
	public void dialogAjouterModifierLocation(Date date, int id_personne, String prix, ArrayList<TypeMaterielLoue> materiel) {
		fenetre_ajouter_modifier_location = new JDialog();
		if(prix.equals(""))
			fenetre_ajouter_modifier_location.setTitle("Ajouter");
		else
			fenetre_ajouter_modifier_location.setTitle("Modifier");
		fenetre_ajouter_modifier_location.setMinimumSize(new Dimension(300, 130));
		interieur_fenetre_location = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre_location, BoxLayout.PAGE_AXIS);
		interieur_fenetre_location.setLayout(layout);
		fenetre_ajouter_modifier_location.add(interieur_fenetre_location);
		fenetre_ajouter_modifier_location.setVisible(true);
		fenetre_ajouter_modifier_location.pack();
		
		//Personne
		Personnes personnes = null;
		try {
			personnes = new Personnes();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ArrayList<Personne> nom_adherent_temp = personnes.getPersonnes();
		String [] nom_personnes = new String [nom_adherent_temp.size()];
		for(int i=0; i<nom_adherent_temp.size(); i++) {
			nom_personnes[i] = nom_adherent_temp.get(i).getIdPersonne()+" : "+nom_adherent_temp.get(i).getNom()+" "+nom_adherent_temp.get(i).getPrenom();
		}
		JPanel panel_personne = new JPanel();
		BoxLayout layout1 = new BoxLayout(panel_personne, BoxLayout.LINE_AXIS);
		panel_personne.setLayout(layout1);
		JLabel label_personne = new JLabel("Personne : ");
		panel_personne.add(label_personne);
		comboBox_location_personne = new JComboBox(nom_personnes);
		if(materiel != null) {//si c'est une modification
			Personne p = null;
			try {
				p = new Personne(id_personne);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			comboBox_location_personne.setSelectedItem(""+id_personne+" : "+p.getNom()+" "+p.getPrenom());
		}
		comboBox_location_personne.addActionListener(this);
		panel_personne.add(comboBox_location_personne);
		interieur_fenetre_location.add(panel_personne);
		//Type
		vector_type = new Vector<JComboBox>();
		vector_boutton_plus = new Vector<JButton>();
		vector_prix = new Vector<JLabel>();
		if(materiel == null) {
			interieur_fenetre_location.add(buildNouveauArticle(0));
		}
		else {
			for(int i=0; i<materiel.size(); i++) {
				interieur_fenetre_location.add(buildNouveauArticle(materiel.get(i).getNumMateriel()));
				updateLabelPrix(i);
				if(i!=0) {
					fenetre_ajouter_modifier_location.setMinimumSize(new Dimension(fenetre_ajouter_modifier_location.getMinimumSize().width, fenetre_ajouter_modifier_location.getMinimumSize().height+30));
				}
			}
		}
		//Total
		interieur_fenetre_location.add(new JSeparator(SwingConstants.HORIZONTAL));
		JPanel panel_total = new JPanel();
		BoxLayout layout5 = new BoxLayout(panel_total, BoxLayout.LINE_AXIS);
		panel_total.setLayout(layout5);
		JLabel label_reduction = new JLabel("Reduction (%) : ");
		panel_total.add(label_reduction);
		String [] list_reduction = {"10", "20", "30", "40", "50", "60"};
		comboBox_reduction = new JComboBox(list_reduction);
		comboBox_reduction.addActionListener(this);
		comboBox_reduction.setEnabled(false);
		panel_total.add(comboBox_reduction);
		JLabel label_prix_prix = new JLabel("   Total : ");
		panel_total.add(label_prix_prix);
		prix_total = new JLabel("00,00 €");
		panel_total.add(prix_total);
		interieur_fenetre_location.add(panel_total);
		//Valider-Annuler
		JPanel panel_valider_annuler = new JPanel();
		BoxLayout layout6 = new BoxLayout(panel_valider_annuler, BoxLayout.LINE_AXIS);
		panel_valider_annuler.setLayout(layout6);
		JButton boutton_valider = new JButton("Valider");
		boutton_valider.addMouseListener(this);
		panel_valider_annuler.add(boutton_valider);
		JButton boutton_annuler = new JButton("Annuler");
		boutton_annuler.addMouseListener(this);
		panel_valider_annuler.add(boutton_annuler);
		interieur_fenetre_location.add(panel_valider_annuler);
		
		updatePrix();
		verificationAdherent();
	}
	
	public JPanel buildNouveauArticle(int type) {
		ArrayList<MaterielLocation> matos_location_temp = materiels_locations.getMaterielsLocations();
		String [] matos_location = new String [matos_location_temp.size()];
		int type_a_selectionner = 0;
		for(int i=0; i<matos_location_temp.size(); i++) {
			matos_location[i] = matos_location_temp.get(i).getNumMateriel()+" : "+matos_location_temp.get(i).getTypeMateriel();
			if(type == matos_location_temp.get(i).getNumMateriel()) {
				type_a_selectionner = i;
			}
		}
		JPanel panel = new JPanel();
		BoxLayout layout2 = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(layout2);
		JLabel label_type = new JLabel("Article : ");
		panel.add(label_type);
		JComboBox comboBox_location_type = new JComboBox(matos_location);
		comboBox_location_type.setSelectedIndex(type_a_selectionner);
		vector_type.add(comboBox_location_type);
		comboBox_location_type.setSelectedItem(type);
		panel.add(comboBox_location_type);
		//Boutton +
		if(vector_boutton_plus.size()>0) {
			vector_boutton_plus.get(vector_boutton_plus.size()-1).setEnabled(false);
		}
		JButton boutton = new JButton("+");
		boutton.addMouseListener(this);
		vector_boutton_plus.add(boutton);
		panel.add(boutton);
		//Prix
		JLabel label_prix = new JLabel("0");
		vector_prix.add(label_prix);
		panel.add(label_prix);
		return panel;
	}
	
	public void updatePrix() {
		int prix_toto = 0;
		for(int i=0; i<vector_prix.size(); i++) {
			prix_toto = prix_toto + Integer.parseInt(vector_prix.get(i).getText());
		}
		if(comboBox_reduction.isEnabled()) {
			prix_toto = prix_toto - ((prix_toto*Integer.parseInt(comboBox_reduction.getSelectedItem().toString())/100));
		}
		prix_total.setText(""+prix_toto+" €");
	}
	
	public void updateLabelPrix(int num_article) {
		boolean trouve = false;
		ArrayList<MaterielLocation> list_matos_temp = materiels_locations.getMaterielsLocations();
		for(int i=0; i<list_matos_temp.size() && !trouve; i++) {
			if(list_matos_temp.get(i).getNumMateriel() == Integer.parseInt(vector_type.get(num_article).getSelectedItem().toString().substring(0, vector_type.get(num_article).getSelectedItem().toString().indexOf(" ")))) {
				trouve = true;
				vector_prix.get(num_article).setText(""+list_matos_temp.get(i).getPrixBase());
			}
		}
	}
	
	public void ajouterLocation(Date date, int heure_debut, int heure_fin, ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix) {
		try {
			locations.ajouterLocation(date, heure_debut, heure_fin, materiel, id_personne, prix);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_location.setVisible(false);
	}
	
	public void modifierLocation(int num, Date date, int heure_debut, int heure_fin, ArrayList<TypeMaterielLoue> materiel, int id_personne, int prix) {
		try {
			locations.modifierLocation(num, date, heure_debut, heure_fin, materiel, id_personne, prix);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_location.setVisible(false);
	}
	
	public void supprimerVente(int num) {
		try {
			locations.supprimerLocation(num);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
	}
	
	public void dialogAjouterModifierMatos(String type, Date date, String etat, String desc, String prix) {
		fenetre_ajouter_modifier_matos = new JDialog();
		if(type.equals(""))
			fenetre_ajouter_modifier_matos.setTitle("Ajouter");
		else
			fenetre_ajouter_modifier_matos.setTitle("Modifier");
		fenetre_ajouter_modifier_matos.setMinimumSize(new Dimension(450, 150));
		interieur_fenetre_matos = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre_matos, BoxLayout.PAGE_AXIS);
		interieur_fenetre_matos.setLayout(layout);
		fenetre_ajouter_modifier_matos.add(interieur_fenetre_matos);
		fenetre_ajouter_modifier_matos.setVisible(true);
		fenetre_ajouter_modifier_matos.pack();
		
		//Type
		JPanel panel_nom = new JPanel();
		BoxLayout layout2 = new BoxLayout(panel_nom, BoxLayout.LINE_AXIS);
		panel_nom.setLayout(layout2);
		JLabel label_type = new JLabel("Type : ");
		panel_nom.add(label_type);
		textField_matos_type = new JTextField(type, 20);
		if(!type.equals(""))
			textField_matos_type.setEnabled(false);
		panel_nom.add(textField_matos_type);
		interieur_fenetre_matos.add(panel_nom);
		//Etat
		JPanel panel_etat = new JPanel();
		BoxLayout layout4 = new BoxLayout(panel_etat, BoxLayout.LINE_AXIS);
		panel_etat.setLayout(layout4);
		JLabel label_etat = new JLabel("Etat : ");
		panel_etat.add(label_etat);
		textField_matos_etat = new JTextField(etat, 20);
		panel_etat.add(textField_matos_etat);
		interieur_fenetre_matos.add(panel_etat);
		//Description
		JPanel panel_desc = new JPanel();
		BoxLayout layout3 = new BoxLayout(panel_desc, BoxLayout.LINE_AXIS);
		panel_desc.setLayout(layout3);
		JLabel label_desc = new JLabel("Description : ");
		panel_desc.add(label_desc);
		textField_matos_desc = new JTextField(desc, 20);
		panel_desc.add(textField_matos_desc);
		interieur_fenetre_matos.add(panel_desc);
		//Prix
		JPanel panel_prix = new JPanel();
		BoxLayout layout5 = new BoxLayout(panel_prix, BoxLayout.LINE_AXIS);
		panel_prix.setLayout(layout5);
		JLabel label_prix = new JLabel("Prix : ");
		panel_prix.add(label_prix);
		textField_matos_prix = new JTextField(prix, 20);
		panel_prix.add(textField_matos_prix);
		interieur_fenetre_matos.add(panel_prix);
		
		//Valider-Annuler
		JPanel panel_valider_annuler = new JPanel();
		BoxLayout layout6 = new BoxLayout(panel_valider_annuler, BoxLayout.LINE_AXIS);
		panel_valider_annuler.setLayout(layout6);
		JButton boutton_valider = new JButton("Valider");
		boutton_valider.addMouseListener(this);
		panel_valider_annuler.add(boutton_valider);
		JButton boutton_annuler = new JButton("Annuler");
		boutton_annuler.addMouseListener(this);
		panel_valider_annuler.add(boutton_annuler);
		interieur_fenetre_matos.add(panel_valider_annuler);
	}
	
	public void ajouterMatos(String type, Date date, String etat, String desc, int prix) {
		try {
			materiels_locations.ajouterMaterielLocation(type, date, etat, desc, prix);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_matos.setVisible(false);
	}
	
	public void modifierMatos(int num, String type, Date date, String etat, String desc, int prix) {
		try {
			materiels_locations.modifierMaterielLocation(num, type, date, etat, desc, prix);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_matos.setVisible(false);
	}
	
	public void supprimerMatos(int num_materiel) {
		try {
			materiels_locations.supprimerMaterielLocation(num_materiel);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
	}
	
	public void updateMatos() {
		try {
			materiels_locations = new MaterielsLocations();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		materiels_locations.addMaterielsLocationsListener(this);
		buildPanelMatos();
		splitPane.setDividerLocation(0.1);
	}
	
	public void updateLocations() {
		try {
			locations = new Locations();
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		} 
		catch (ClassNotFoundException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		locations.addLocationsListener(this);
		buildPanelLocation();
		splitPane.setDividerLocation(0.9);
	}
	
	public void verificationAdherent() {
		try {
			new Adherent(Integer.parseInt(comboBox_location_personne.getSelectedItem().toString().substring(0, comboBox_location_personne.getSelectedItem().toString().indexOf(" "))));
		} catch (SQLException e1) {
			comboBox_reduction.setEnabled(false);
			return;
		}
		comboBox_reduction.setEnabled(true);
		updatePrix();
	}
	
	@Override
	public void ajouterMaterielLocation(MaterielLocation materielLocation) {
		updateMatos();
	}

	@Override
	public void supprimerMaterielLocation(MaterielLocation materielLocation) {
		updateMatos();
	}

	@Override
	public void ajouterLocation(Location location) {
		updateLocations();
	}

	@Override
	public void supprimerLocation(Location location) {
		updateLocations();
	}

	@Override
	public void focusGained(FocusEvent event) {
		JTable table = null;
		try {
			table = (JTable)event.getComponent();
		}
		catch(ClassCastException e) {
			return;
		}
		if(table.isValid() && table.isEnabled()) {
			if(table == table_matos) {
				splitPane.setDividerLocation(0.1);
				if(table.getSelectedRow()>-1) {
					boutton_matos_modifier.setEnabled(true);
					boutton_matos_effacer.setEnabled(true);
				}
			}
			else if(table == table_location) {
				splitPane.setDividerLocation(0.9);
				if(table.getSelectedRow()>-1) {
					boutton_vente_facture.setEnabled(true);
					boutton_vente_modifier.setEnabled(true);
					boutton_vente_supprimer.setEnabled(true);
				}
			}
		}
	}

	@Override
	public void focusLost(FocusEvent event) {
//		JTable table = null;
//		try {
//			table = (JTable)event.getComponent();
//		}
//		catch(ClassCastException e) {
//			return;
//		}
//		if(table.isValid() && table.isEnabled()) {
//			if(table == table_matos) {
//				boutton_matos_modifier.setEnabled(false);
//				boutton_matos_effacer.setEnabled(false);
//			}
//			else if(table == table_location) {
//				boutton_vente_facture.setEnabled(false);
//				boutton_vente_modifier.setEnabled(false);
//				boutton_vente_supprimer.setEnabled(false);
//			}
//		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton boutton = null;
		try {
			boutton = (JButton)event.getComponent();
		}
		catch(ClassCastException e) {
			JTable table = null;
			try {
				table = (JTable)event.getComponent();
			}
			catch(ClassCastException ee) {
				return;
			}
			if(table == table_location) {
				if(table.getSelectedRow()>-1) {
					boutton_vente_facture.setEnabled(true);
					boutton_vente_modifier.setEnabled(true);
					boutton_vente_supprimer.setEnabled(true);
				}
				else {
					boutton_vente_facture.setEnabled(false);
					boutton_vente_modifier.setEnabled(false);
					boutton_vente_supprimer.setEnabled(false);
				}
			}
			return;
		}
		if(boutton.isValid() && boutton.isEnabled()) {
			if(boutton.getText().equals("Home")) {
				main_window.changerTab("Home", new PremiereVue(main_window));
			}
			else if(boutton == boutton_vente_add) {
				dialogAjouterModifierLocation(null, 0, "", null);
			}
			else if(boutton == boutton_vente_modifier) {
				int ligne = table_location.getSelectedRow();
				dialogAjouterModifierLocation(Utiles.stringToDate(list_locations[ligne][1].toString()), Integer.parseInt(list_locations[ligne][4].toString().substring(0, list_locations[ligne][4].toString().indexOf(" "))), list_locations[ligne][5].toString(), locations.getLocations().get(ligne).getMateriel());
			}
			else if(boutton == boutton_vente_supprimer) {
				int ligne = table_location.getSelectedRow();
				supprimerVente(Integer.parseInt(list_locations[ligne][0].toString()));
				splitPane.setDividerLocation(0.9);
			}
			else if(boutton == boutton_matos_add) {
				dialogAjouterModifierMatos("", null, "", "", "");
			}
			else if(boutton == boutton_matos_modifier) {
				int ligne = table_matos.getSelectedRow();
				dialogAjouterModifierMatos(list_matos[ligne][1].toString(), Utiles.stringToDate(list_matos[ligne][2].toString()), list_matos[ligne][3].toString(), list_matos[ligne][4].toString(), list_matos[ligne][5].toString());
			}
			else if(boutton == boutton_matos_effacer) {
				int ligne = table_matos.getSelectedRow();
				supprimerMatos(Integer.parseInt(list_matos[ligne][0].toString()));
				splitPane.setDividerLocation(0.1);
			}
			else if(boutton.getText().equals("Valider")) {
				if(fenetre_ajouter_modifier_matos != null) {
					if(fenetre_ajouter_modifier_matos.isVisible() && fenetre_ajouter_modifier_matos.getTitle().equals("Ajouter")) {
						ajouterMatos(textField_matos_type.getText(), new Date(Calendar.getInstance().getTimeInMillis()), textField_matos_etat.getText(), textField_matos_desc.getText(), Integer.parseInt(textField_matos_prix.getText()));
					}
					else if(fenetre_ajouter_modifier_matos.isVisible() && fenetre_ajouter_modifier_matos.getTitle().equals("Modifier")) {
						modifierMatos(Integer.parseInt(list_matos[table_matos.getSelectedRow()][0]), textField_matos_type.getText(), Utiles.stringToDate(list_matos[table_matos.getSelectedRow()][2]), textField_matos_etat.getText(), textField_matos_desc.getText(), Integer.parseInt(textField_matos_prix.getText()));
					}
				}
				if(fenetre_ajouter_modifier_location != null) {
					if(fenetre_ajouter_modifier_location.isVisible() && fenetre_ajouter_modifier_location.getTitle().equals("Ajouter")) {
						int id_personne = Integer.parseInt(comboBox_location_personne.getSelectedItem().toString().substring(0, comboBox_location_personne.getSelectedItem().toString().indexOf(" ")));
						ArrayList<TypeMaterielLoue> materiel = new ArrayList<TypeMaterielLoue>();
						int prix = Integer.parseInt(prix_total.getText().substring(0, prix_total.getText().indexOf(" ")));
						for(int i=0; i<vector_prix.size(); i++) {
							if(!vector_boutton_plus.get(i).isEnabled()) {
								TypeMaterielLoue type_materiel_loue = new TypeMaterielLoue(Integer.parseInt(vector_type.get(i).getSelectedItem().toString().substring(0, vector_type.get(i).getSelectedItem().toString().indexOf(" "))));
								materiel.add(type_materiel_loue);
							}
						}
						Calendar cal = Calendar.getInstance();
						int heure_debut = cal.get(Calendar.HOUR);
						int heure_fin = heure_debut+1;
						ajouterLocation(new Date(cal.getTimeInMillis()), heure_debut, heure_fin, materiel, id_personne, prix);
					}
					else if(fenetre_ajouter_modifier_location.isVisible() && fenetre_ajouter_modifier_location.getTitle().equals("Modifier")) {
						int ligne = table_location.getSelectedRow();
						int id_personne = Integer.parseInt(comboBox_location_personne.getSelectedItem().toString().substring(0, comboBox_location_personne.getSelectedItem().toString().indexOf(" ")));
						ArrayList<TypeMaterielLoue> materiel = new ArrayList<TypeMaterielLoue>();
						int prix = Integer.parseInt(prix_total.getText().substring(0, prix_total.getText().indexOf(" ")));
						for(int i=0; i<vector_prix.size(); i++) {
							if(!vector_boutton_plus.get(i).getText().equals("Quantite")) {
								TypeMaterielLoue type_materiel_loue = new TypeMaterielLoue(Integer.parseInt(vector_type.get(i).getSelectedItem().toString().substring(0, vector_type.get(i).getSelectedItem().toString().indexOf(" "))));
								materiel.add(type_materiel_loue);
							}
						}
						Date date = Utiles.stringToDate(list_locations[ligne][1].toString());
						int heure_debut = Integer.parseInt(list_locations[ligne][2].toString());
						int heure_fin = Integer.parseInt(list_locations[ligne][3].toString());
						modifierLocation(Integer.parseInt(list_locations[ligne][0].toString()), date, heure_debut, heure_fin, materiel, id_personne, prix);
					}
				}
			}
			else if(boutton.getText().equals("Annuler")) {
				if(fenetre_ajouter_modifier_matos != null) {
					if(fenetre_ajouter_modifier_matos.isVisible()) {
						fenetre_ajouter_modifier_matos.setVisible(false);
					}
				}
				if(fenetre_ajouter_modifier_location != null) {
					if(fenetre_ajouter_modifier_location.isVisible()) {
						fenetre_ajouter_modifier_location.setVisible(false);
					}
				}
			}
			else if(boutton.getText().equals("+")) {
				int num_article = -1;
				boolean trouve = false;
				for(int i=0; i<vector_boutton_plus.size() && !trouve; i++) {
					if(vector_boutton_plus.get(i) == boutton) {
						trouve = true;
						num_article = i;
					}
				}
				updateLabelPrix(num_article);
				if(trouve && num_article+1 == vector_type.size()) {
					interieur_fenetre_location.add(buildNouveauArticle(0), vector_type.size());
					fenetre_ajouter_modifier_location.setMinimumSize(new Dimension(fenetre_ajouter_modifier_location.getMinimumSize().width, fenetre_ajouter_modifier_location.getMinimumSize().height+30));
				}
				updatePrix();
			}
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
	public void actionPerformed(ActionEvent event) {
		JComboBox comboBox = null;
		try {
			comboBox = (JComboBox)event.getSource();
		}
		catch(ClassCastException ee) {
			return;
		}
		if(comboBox == comboBox_location_personne) {
			verificationAdherent();
		}
		else if(comboBox == comboBox_reduction) {
			updatePrix();
		}
		return;
	}
}
