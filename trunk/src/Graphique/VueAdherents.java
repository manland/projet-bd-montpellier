package Graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Coeur.Forfait.Forfait;
import Coeur.Forfait.Forfaits;
import Coeur.Forfait.HistoriquesSouscriptions;
import Coeur.Forfait.Souscription;
import Coeur.Forfait.Souscriptions;
import Coeur.Forfait.SouscriptionsListener;
import Coeur.Personne.Adherent;
import Coeur.Personne.Adherents;
import Coeur.Personne.AdherentsListener;
import Coeur.Personne.Personne;
import Coeur.Personne.Personnes;
import Coeur.Personne.PersonnesListener;

public class VueAdherents extends JPanel implements MouseListener, TableModelListener, AdherentsListener, PersonnesListener, SouscriptionsListener {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = Traductions.vueAdherentsTab(Options.getLangue(), "columns");
	private Object[][] data;
	
	private JTable table;
	private JButton boutton_mod_user;
	private JButton boutton_del_user;
	private JButton boutton_forfait;
	private int ligne_selected;
	
	private JDialog fenetre_ajouter_modifier;
	private JPanel interieur_fenetre;
	private JTextField textField_nom;
	private JTextField textField_prenom;
	private JTextField textField_adresse;
	private JComboBox comboBox_jour;
	private JComboBox comboBox_mois;
	private JComboBox comboBox_annee;
	private Vector<JCheckBox> vector_forfait_checkBox;
	private Vector<JComboBox> vector_forfait_comboBox;
	
	private JPanel panel_forfait;
	private Vector<JCheckBox> vector_forfait_checkBox_modifier;
	private Vector<Vector<JComboBox>> vector_forfait_comboBox_modifier;
	
	private Adherents adherents;
	private Personnes personnes;
	private Forfaits forfaits;
	private ArrayList<Forfait> list_forfait;
	private Souscriptions souscriptions;
	private ArrayList<Souscription> list_souscriptions;
	private MainWindow main_window;
	
	public VueAdherents(MainWindow main_window) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		this.main_window = main_window;
		//Récupération des données
		try {
			adherents = new Adherents();
			personnes = new Personnes();
			forfaits = new Forfaits();
			souscriptions = new Souscriptions();
		} catch (Exception e) {
			e.printStackTrace();
		}
		list_forfait = forfaits.getForfaits();
		adherents.addAdherentsListener(this);
		personnes.addPersonnesListener(this);
		souscriptions.addSouscriptionsListener(this);
		//table
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(this);
		table.getModel().addTableModelListener(this);
		updateTable();
		add(scrollPane);
		//Icones du dessous
		JPanel icones = new JPanel(new GridLayout(1, 4));
		ImageIcon retour = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png");
		JButton boutton_retour_home = new JButton(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonhome"), retour);
		boutton_retour_home.setHorizontalTextPosition(0);
		boutton_retour_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_retour_home.addMouseListener(this);
		boutton_retour_home.setMaximumSize(new Dimension(200, 30));
		icones.add(boutton_retour_home);
		ImageIcon add_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/add-user.png");
		JButton boutton_add_user = new JButton(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonajouter"), add_user);
		boutton_add_user.setHorizontalTextPosition(0);
		boutton_add_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_add_user.addMouseListener(this);
		boutton_add_user.setMaximumSize(new Dimension(200, 30));
		icones.add(boutton_add_user);
		ImageIcon mod_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/edit-user.png");
		boutton_mod_user = new JButton(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonmodifier"), mod_user);
		boutton_mod_user.setHorizontalTextPosition(0);
		boutton_mod_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_mod_user.addMouseListener(this);
		boutton_mod_user.setEnabled(false);
		boutton_mod_user.setMaximumSize(new Dimension(200, 30));
		icones.add(boutton_mod_user);
		ImageIcon del_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/remove-user.png");
		boutton_del_user = new JButton(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonsupprimer"), del_user);
		boutton_del_user.setHorizontalTextPosition(0);
		boutton_del_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_del_user.addMouseListener(this);
		boutton_del_user.setEnabled(false);
		boutton_del_user.setMaximumSize(new Dimension(200, 30));
		icones.add(boutton_del_user);
		boutton_forfait = new JButton(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonforfaits"));
		boutton_forfait.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/forfait.png"));
		boutton_forfait.setHorizontalTextPosition(0);
		boutton_forfait.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_forfait.addMouseListener(this);
		boutton_forfait.setEnabled(false);
		icones.add(boutton_forfait);
		add(icones);
	}
	
	public void ajouterAdherent(String nom, String prenom, String date, String adresse) {
		try {
			personnes.ajouterPersonne(nom, prenom, Date.valueOf(date), adresse);
		} 
		catch (Exception e) {
			ArrayList<Personne> list_personne = personnes.getPersonnes();
			boolean trouve = false;
			int id_personne = -1;
			for(int i=0; i<list_personne.size() && !trouve; i++) {
				if(list_personne.get(i).getNom() == nom && list_personne.get(i).getPrenom() == prenom) {
					trouve = true;
					id_personne = list_personne.get(i).getIdPersonne();
				}
			}
			if(trouve) {
				try {
					adherents.ajouterAdherent(id_personne);
				} 
				catch (Exception ee) {
					JLabel erreur = new JLabel(ee.getLocalizedMessage());
					erreur.setForeground(new Color(255, 0, 0));
					interieur_fenetre.add(erreur);
					fenetre_ajouter_modifier.setSize(new Dimension(fenetre_ajouter_modifier.getWidth(), fenetre_ajouter_modifier.getHeight()+30));
					return;
				}
				fenetre_ajouter_modifier.setVisible(false);
			}
			else {
				JLabel erreur = new JLabel(e.getLocalizedMessage());
				erreur.setForeground(new Color(255, 0, 0));
				interieur_fenetre.add(erreur);
				fenetre_ajouter_modifier.setSize(new Dimension(fenetre_ajouter_modifier.getWidth(), fenetre_ajouter_modifier.getHeight()+30));
				return;
			}
		}
		fenetre_ajouter_modifier.setVisible(false);
	}
	
	public void modifierAdherent(int ligne_selected, String nom, String prenom, String date, String adresse) {
		try {
			adherents.modifierAdherent(Integer.parseInt(data[ligne_selected][0].toString()), nom, prenom, Date.valueOf(date), adresse);
		} 
		catch (NumberFormatException e) {
			e.printStackTrace();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		fenetre_ajouter_modifier.setVisible(false);
	}
	
	public void supprimerAdherentH(int ligne_selected) {
		try {
			adherents.supprimerAdherent(Integer.parseInt(data[ligne_selected][1].toString()));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ajouterModifier(String nom, String prenom, String date, String adresse) {
		fenetre_ajouter_modifier = new JDialog();
		if(nom.equals(""))
			fenetre_ajouter_modifier.setTitle(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_ajouter"));
		else
			fenetre_ajouter_modifier.setTitle(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_modifier"));
		fenetre_ajouter_modifier.setSize(470, 270);
		fenetre_ajouter_modifier.setMinimumSize(new Dimension(470, 270));
		interieur_fenetre = new JPanel(new GridLayout(0, 2));
		fenetre_ajouter_modifier.add(interieur_fenetre);
		fenetre_ajouter_modifier.setVisible(true);
		fenetre_ajouter_modifier.pack();
		
		//Nom
		JLabel label_nom = new JLabel(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_nom")+" : ");
		interieur_fenetre.add(label_nom);
		textField_nom = new JTextField(nom, 20);
		interieur_fenetre.add(textField_nom);
		//Prénom
		JLabel label_prenom = new JLabel(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_prenom")+" : ");
		interieur_fenetre.add(label_prenom);
		textField_prenom = new JTextField(prenom, 20);
		interieur_fenetre.add(textField_prenom);
		//Adresse
		JLabel label_adresse = new JLabel(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_adresse")+" : ");
		interieur_fenetre.add(label_adresse);
		textField_adresse = new JTextField(adresse, 20);
		interieur_fenetre.add(textField_adresse);
		//DateNaissance
		JLabel label_date = new JLabel(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_datedenaissance")+" : ");
		interieur_fenetre.add(label_date);
		JPanel panel_date = new JPanel();
		BoxLayout layout = new BoxLayout(panel_date, BoxLayout.LINE_AXIS);
		panel_date.setLayout(layout);
		String [] list_jour = new String [31];
		for(int i=1; i<32; i++) {
			list_jour[i-1] = ""+i;
		}
		comboBox_jour = new JComboBox(list_jour);
		panel_date.add(comboBox_jour);
		String [] list_mois = Traductions.vueAdherentsTab(Options.getLangue(), "mois");
		comboBox_mois = new JComboBox(list_mois);
		panel_date.add(comboBox_mois);
		String [] list_annee = new String [200];
		for(int i=1900; i<2100; i++) {
			list_annee[i-1900] = ""+i;
		}
		comboBox_annee = new JComboBox(list_annee);
		panel_date.add(comboBox_annee);
		interieur_fenetre.add(panel_date);
		//Set si modif
		if(date != "") {
			Calendar date_sql = Calendar.getInstance();
			date_sql.setTime(Date.valueOf(date));
			comboBox_jour.setSelectedIndex(date_sql.get(Calendar.DATE)-1);
			comboBox_mois.setSelectedIndex(date_sql.get(Calendar.MONTH));
			comboBox_annee.setSelectedItem(""+date_sql.get(Calendar.YEAR));
		}
		//Forfaits
		String [] temp_forfaits = Traductions.vueAdherentsTab(Options.getLangue(), "forfaits");
		//ArrayList<Forfait> list_forfait;
		vector_forfait_checkBox = new Vector<JCheckBox>(list_forfait.size());
		vector_forfait_comboBox = new Vector<JComboBox>(list_forfait.size());
		for(int i=0; i<list_forfait.size(); i++) {
			JCheckBox checkBox = new JCheckBox(list_forfait.get(i).getTypeForfait());
			vector_forfait_checkBox.add(checkBox);
			interieur_fenetre.add(checkBox);
			JComboBox comboBox = new JComboBox(temp_forfaits);
			vector_forfait_comboBox.add(comboBox);
			interieur_fenetre.add(comboBox);
		}
		//Bouttons
		JButton boutton_valider = new JButton(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_valider"));
		boutton_valider.addMouseListener(this);
		interieur_fenetre.add(boutton_valider);
		JButton boutton_annuler = new JButton(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_annuler"));
		boutton_annuler.addMouseListener(this);
		interieur_fenetre.add(boutton_annuler);
	}
	
	public void updateTable() {
		ArrayList<Adherent> list_personnes = adherents.getAdherents();
		data = new Object[list_personnes.size()][7];
		for(int i=0; i<list_personnes.size(); i++) {
			data[i][0] = list_personnes.get(i).getIdPersonne();
			data[i][1] = list_personnes.get(i).getNumAdherent();
			data[i][2] = list_personnes.get(i).getNom();
			data[i][3] = list_personnes.get(i).getPrenom();
			data[i][4] = Utiles.dateToString(list_personnes.get(i).getDate());
			data[i][5] = list_personnes.get(i).getAdresse();
		}
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		table.setModel(dtm);
		table.update(getGraphics());
	}
	
	public void buildPanelForfait(int num_adh) {
		try {remove(panel_forfait);}catch(NullPointerException e) {}
		panel_forfait = new JPanel();
		BoxLayout layout = new BoxLayout(panel_forfait, BoxLayout.LINE_AXIS);
		panel_forfait.setLayout(layout);
		
		//Ajout
		JPanel panel_forfait_ajout = new JPanel(new GridLayout(0, 2));
		panel_forfait_ajout.setBorder(BorderFactory.createTitledBorder(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_ajouter")));
		String [] temp_forfaits = Traductions.vueAdherentsTab(Options.getLangue(), "forfaits");
		//ArrayList<Forfait> list_forfait;
		vector_forfait_checkBox = new Vector<JCheckBox>(list_forfait.size());
		vector_forfait_comboBox = new Vector<JComboBox>(list_forfait.size());
		for(int i=0; i<list_forfait.size(); i++) {
			JCheckBox checkBox = new JCheckBox(list_forfait.get(i).getTypeForfait());
			vector_forfait_checkBox.add(checkBox);
			panel_forfait_ajout.add(checkBox);
			JComboBox comboBox = new JComboBox(temp_forfaits);
			vector_forfait_comboBox.add(comboBox);
			panel_forfait_ajout.add(comboBox);
		}
		JButton boutton_forfait_ajouter_valider = new JButton(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_valider"));
		boutton_forfait_ajouter_valider.addMouseListener(this);
		panel_forfait_ajout.add(boutton_forfait_ajouter_valider);
		JButton boutton_forfait_ajouter_reset = new JButton(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_toutvider"));
		boutton_forfait_ajouter_reset.addMouseListener(this);
		panel_forfait_ajout.add(boutton_forfait_ajouter_reset);
		panel_forfait.add(panel_forfait_ajout);
		
		//Modification/Suppression
		JPanel panel_forfait_modifier = new JPanel();
		BoxLayout layout2 = new BoxLayout(panel_forfait_modifier, BoxLayout.PAGE_AXIS);
		panel_forfait_modifier.setLayout(layout2);
		panel_forfait_modifier.setBorder(BorderFactory.createTitledBorder(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_modifiersupprimer")));
		//ArrayList<Forfait> list_forfait;
		vector_forfait_checkBox_modifier = new Vector<JCheckBox>(list_forfait.size());
		vector_forfait_comboBox_modifier = new Vector<Vector<JComboBox>>(list_forfait.size());
		for(int i=0; i<list_forfait.size(); i++) {
			vector_forfait_comboBox_modifier.add(new Vector<JComboBox>(6));
		}
		Souscriptions temp_souscriptions = null;
		HistoriquesSouscriptions temp_souscriptions_historique = null;
		try {
			temp_souscriptions = new Souscriptions(num_adh);
			temp_souscriptions_historique = new HistoriquesSouscriptions(num_adh);
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		list_souscriptions = temp_souscriptions.getSouscriptions();
		for(int i=0; i<list_souscriptions.size(); i++) {
			JPanel panel_temp = new JPanel();
			BoxLayout layout_temp = new BoxLayout(panel_temp, BoxLayout.LINE_AXIS);
			panel_temp.setLayout(layout_temp);
			
			JCheckBox checkBox = new JCheckBox(list_souscriptions.get(i).getTypeForfait());
			vector_forfait_checkBox_modifier.add(checkBox);
			panel_temp.add(checkBox);
			
			panel_temp.add(buildPanelDate(list_souscriptions.get(i).getDateDebut(), i));
			panel_temp.add(buildPanelDate(list_souscriptions.get(i).getDateFin(), i));
			
			panel_forfait_modifier.add(panel_temp);
		}
		panel_forfait_modifier.add(new JSeparator());
		ArrayList<Souscription> list_souscriptions = temp_souscriptions_historique.getSouscriptions();
		for(int i=0; i<list_souscriptions.size(); i++) {
			JPanel panel_temp = new JPanel();
			BoxLayout layout_temp = new BoxLayout(panel_temp, BoxLayout.LINE_AXIS);
			panel_temp.setLayout(layout_temp);
			
			JCheckBox checkBox = new JCheckBox(list_souscriptions.get(i).getTypeForfait());
			vector_forfait_checkBox_modifier.add(checkBox);
			panel_temp.add(checkBox);
			
			panel_temp.add(buildPanelDate(list_souscriptions.get(i).getDateDebut(), i));
			panel_temp.add(buildPanelDate(list_souscriptions.get(i).getDateFin(), i));
			
			panel_forfait_modifier.add(panel_temp);
		}
		JPanel panel_boutton = new JPanel();
		BoxLayout layout_temp = new BoxLayout(panel_boutton, BoxLayout.LINE_AXIS);
		panel_boutton.setLayout(layout_temp);
//		JButton boutton_modifier_forfait = new JButton(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_modifier"));
//		boutton_modifier_forfait.addMouseListener(this);
//		panel_boutton.add(boutton_modifier_forfait);
		JButton boutton_supprimer_forfait = new JButton(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_supprimer"));
		boutton_supprimer_forfait.addMouseListener(this);
		panel_boutton.add(boutton_supprimer_forfait);
		JButton boutton_forfait_hide = new JButton(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_cacher"));
		boutton_forfait_hide.addMouseListener(this);
		panel_boutton.add(boutton_forfait_hide);
		
		panel_forfait_modifier.add(panel_boutton);
		
		JScrollPane scroll_panel_forfait_modifier = new JScrollPane(panel_forfait_modifier);
//		scroll_panel_forfait_modifier.add(panel_forfait_modifier);
		panel_forfait.add(scroll_panel_forfait_modifier);
		
		add(panel_forfait, 1);
		validate();
	}
	
	public JPanel buildPanelDate(Date d, int place_vector) {
		JPanel panel_date = new JPanel();
		BoxLayout layout = new BoxLayout(panel_date, BoxLayout.LINE_AXIS);
		panel_date.setLayout(layout);
		
		String [] list_jour = new String [31];
		for(int i=1; i<32; i++) {
			list_jour[i-1] = ""+i;
		}
		JComboBox comboBox_jour = new JComboBox(list_jour);
		comboBox_jour.setMaximumSize(new Dimension(500, 30));
		panel_date.add(comboBox_jour);
		
		String [] list_mois = Traductions.vueAdherentsTab(Options.getLangue(), "mois");
		JComboBox comboBox_mois = new JComboBox(list_mois);
		comboBox_mois.setMaximumSize(new Dimension(500, 30));
		panel_date.add(comboBox_mois);
		
		String [] list_annee = new String [200];
		for(int i=1900; i<2100; i++) {
			list_annee[i-1900] = ""+i;
		}
		JComboBox comboBox_annee = new JComboBox(list_annee);
		comboBox_annee.setMaximumSize(new Dimension(500, 30));
		panel_date.add(comboBox_annee);

		Calendar date_sql = Calendar.getInstance();
		date_sql.setTime(d);
		comboBox_jour.setSelectedIndex(date_sql.get(Calendar.DATE)-1);
		comboBox_mois.setSelectedIndex(date_sql.get(Calendar.MONTH));
		comboBox_annee.setSelectedItem(""+date_sql.get(Calendar.YEAR));
		
		vector_forfait_comboBox_modifier.get(place_vector).add(comboBox_jour);
		vector_forfait_comboBox_modifier.get(place_vector).add(comboBox_mois);
		vector_forfait_comboBox_modifier.get(place_vector).add(comboBox_annee);
		
		return panel_date;
	}
	
	public void verifierForfaitsAjouter(int num_Adherent) {
		int prix = 0;
		for(int i=0; i<vector_forfait_checkBox.size(); i++) {
			if(vector_forfait_checkBox.get(i).isSelected()) {
				Date date_debut = new Date(Calendar.getInstance().getTimeInMillis());
				int type = vector_forfait_comboBox.get(i).getSelectedIndex();
				String type_forfait = vector_forfait_checkBox.get(i).getText();
				Date date_fin = null;
				if(type == 0) {//Mois
					Calendar date = Calendar.getInstance();
					date.add(Calendar.DATE, 30);
					date_fin = new Date(date.getTimeInMillis());
					for(int forfait=0; forfait<list_forfait.size(); forfait++) {
						if(type_forfait.equals(list_forfait.get(forfait).getTypeForfait())) {
							prix = prix + list_forfait.get(forfait).getPrixMois();
						}
					}
				}
				else if(type == 1) {//trimestre
					Calendar date = Calendar.getInstance();
					date.add(Calendar.DATE, 90);
					date_fin = new Date(date.getTimeInMillis());
					for(int forfait=0; forfait<list_forfait.size(); forfait++) {
						if(type_forfait.equals(list_forfait.get(forfait).getTypeForfait())) {
							prix = prix + list_forfait.get(forfait).getPrixTrimestre();
						}
					}
				}
				else if(type == 2) {//semestre
					Calendar date = Calendar.getInstance();
					date.add(Calendar.DATE, 180);
					date_fin = new Date(date.getTimeInMillis());
					for(int forfait=0; forfait<list_forfait.size(); forfait++) {
						if(type_forfait.equals(list_forfait.get(forfait).getTypeForfait())) {
							prix = prix + list_forfait.get(forfait).getPrixSemestre();
						}
					}
				}
				else if(type == 3) {//annee
					Calendar date = Calendar.getInstance();
					date.add(Calendar.DATE, 365);
					date_fin = new Date(date.getTimeInMillis());
					for(int forfait=0; forfait<list_forfait.size(); forfait++) {
						if(type_forfait.equals(list_forfait.get(forfait).getTypeForfait())) {
							prix = prix + list_forfait.get(forfait).getPrixAnnee();
						}
					}
				}
				try {
					souscriptions.ajouterSouscription(list_forfait.get(i).getTypeForfait(), num_Adherent, date_debut, date_fin);
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
			}
		}
		double reduction = 0;
		if(prix>=100 && prix<=250) {
			reduction = prix*0.05;
			prix = (int) (prix - reduction);
		}
		else if(prix>250 && prix<=400) {
			reduction = prix*0.1;
			prix = (int) (prix - reduction);
		}
		else if(prix>400 && prix<=550) {
			reduction = prix*0.15;
			prix = (int) (prix - reduction);
		}
		else if(prix>550 && prix<=800) {
			reduction = prix*0.2;
			prix = (int) (prix - reduction);
		}
		else if(prix>800 && prix<=1500) {
			reduction = prix*0.3;
			prix = (int) (prix - reduction);
		}
		else if(prix>1500 && prix<=3000) {
			reduction = prix*0.4;
			prix = (int) (prix - reduction);
		}
		if(reduction!=0) {
			Utiles.erreur("Cet adhérent doit payer : "+prix+"€ dont une réduction de "+reduction+"€");
		}
		else {
			Utiles.erreur("Cet adhérent doit payer : "+prix+"€");
		}
		buildPanelForfait(num_Adherent);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JTable table = new JTable();
		try {
			table = (JTable)event.getComponent();
		}
		catch(ClassCastException e) {
			JButton boutton = new JButton();
			try {
				boutton = (JButton)event.getComponent();
			}
			catch(ClassCastException ee) {
				Utiles.erreur(ee.getLocalizedMessage());
			}
			if(boutton.isEnabled()) {
				if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonhome"))) {
					main_window.changerTab(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonhome"), new PremiereVue(main_window));
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonajouter"))) {
					ajouterModifier("", "", "", "");
				}
				else if(boutton == boutton_mod_user) {
					ajouterModifier(data[ligne_selected][2].toString(), data[ligne_selected][3].toString(), Utiles.stringToDate(data[ligne_selected][4].toString()).toString(), data[ligne_selected][5].toString());
				}
				else if(boutton == boutton_del_user) {
					supprimerAdherentH(ligne_selected);
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "constructeur_bouttonforfaits"))) {
					buildPanelForfait(Integer.parseInt(data[ligne_selected][1].toString()));
				}
				else if(boutton.getText().equals("Tout vider")) {
					buildPanelForfait(Integer.parseInt(data[ligne_selected][1].toString()));
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_valider"))) {
					if(fenetre_ajouter_modifier != null) {
						if(fenetre_ajouter_modifier.isVisible()) {
							String date = comboBox_annee.getSelectedItem().toString()+"-"+(comboBox_mois.getSelectedIndex()+1)+"-"+(comboBox_jour.getSelectedIndex()+1);
							if(fenetre_ajouter_modifier.getTitle().equals(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_ajouter"))) {
								ajouterAdherent(textField_nom.getText(), textField_prenom.getText(), date, textField_adresse.getText());
							}
							else if(fenetre_ajouter_modifier.getTitle().equals(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_modifier"))) {
								modifierAdherent(ligne_selected, textField_nom.getText(), textField_prenom.getText(), date, textField_adresse.getText());
							}
						}
						else {//Forfait
							verifierForfaitsAjouter(Integer.parseInt(data[ligne_selected][1].toString()));
						}
					}
					else {//Forfait
						verifierForfaitsAjouter(Integer.parseInt(data[ligne_selected][1].toString()));
					}
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "ajoutermodifier_annuler"))) {
					fenetre_ajouter_modifier.setVisible(false);
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_modifier"))) {
					for(int i=0; i<vector_forfait_checkBox_modifier.size(); i++) {
						if(vector_forfait_checkBox_modifier.get(i).isSelected()) {
							int jour_debut = Integer.parseInt(vector_forfait_comboBox_modifier.get(i).get(0).getSelectedItem().toString());
							String mois_debut = vector_forfait_comboBox_modifier.get(i).get(1).getSelectedItem().toString();
							int annee_debut = Integer.parseInt(vector_forfait_comboBox_modifier.get(i).get(2).getSelectedItem().toString());
							Date date_debut = Utiles.stringToDate(annee_debut+" "+mois_debut+" "+jour_debut);
							int jour_fin = Integer.parseInt(vector_forfait_comboBox_modifier.get(i).get(3).getSelectedItem().toString());
							String mois_fin = vector_forfait_comboBox_modifier.get(i).get(4).getSelectedItem().toString();
							int annee_fin = Integer.parseInt(vector_forfait_comboBox_modifier.get(i).get(5).getSelectedItem().toString());
							Date date_fin = Utiles.stringToDate(annee_fin+" "+mois_fin+" "+jour_fin);
							String type_forfait = vector_forfait_checkBox_modifier.get(i).getText();
							int num_adh = Integer.parseInt(data[ligne_selected][1].toString());
							try {
								souscriptions.supprimerSouscription(type_forfait, num_adh, list_souscriptions.get(i).getDateDebut(), list_souscriptions.get(i).getDateFin());
								souscriptions.ajouterSouscription(type_forfait, num_adh, date_debut, date_fin);
							} catch (NumberFormatException e1) {
								Utiles.erreur(e1.getLocalizedMessage());
							} catch (SQLException e1) {
								Utiles.erreur(e1.getLocalizedMessage());
							}
						}
					}
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_supprimer"))) {
					for(int i=0; i<vector_forfait_checkBox_modifier.size(); i++) {
						if(vector_forfait_checkBox_modifier.get(i).isSelected()) {
							try {
								souscriptions.supprimerSouscription(vector_forfait_checkBox_modifier.get(i).getText(), 
										Integer.parseInt(data[ligne_selected][1].toString()), 
										list_souscriptions.get(i).getDateDebut(), 
										list_souscriptions.get(i).getDateFin());
							} catch (NumberFormatException e1) {
								Utiles.erreur(e1.getLocalizedMessage());
							} catch (SQLException e1) {
								Utiles.erreur(e1.getLocalizedMessage());
							}
						}
					}
					buildPanelForfait(Integer.parseInt(data[ligne_selected][1].toString()));
				}
				else if(boutton.getText().equals(Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_cacher"))) {
					try {remove(panel_forfait);}catch(NullPointerException ee) {}
					validate();
				}
			}
		}
		if(table.getSelectedRow()>=0) {
			ligne_selected = table.getSelectedRow();
			boutton_mod_user.setEnabled(true);
			boutton_del_user.setEnabled(true);
			boutton_forfait.setEnabled(true);
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
	public void tableChanged(TableModelEvent e) {
		updateTable();
	}

	@Override
	public void ajouterAdherent(Adherent adherent) {
		verifierForfaitsAjouter(adherent.getNumAdherent());
		updateTable();
	}

	@Override
	public void supprimerAdherent(Adherent adherent) {
		updateTable();
	}

	@Override
	public void ajouterPersonne(Personne personne) {
		try {
			adherents.ajouterAdherent(personne.getIdPersonne());
		} catch (Exception e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		updateTable();
	}

	@Override
	public void supprimerPersonne(Personne personne) {
		updateTable();
	}

	@Override
	public void ajouterSouscription(Souscription souscription) {
//		buildPanelForfait(souscription.getNumAdherent());
	}

	@Override
	public void supprimerSouscription(Souscription souscription) {
		
	}

}
