package Graphique;

import java.awt.Color;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import Coeur.Casier.Casier;
import Coeur.Casier.Casiers;
import Coeur.Casier.ReservationCasierAnnee;
import Coeur.Casier.ReservationCasierSeance;
import Coeur.Casier.ReservationsCasiersAnnees;
import Coeur.Casier.ReservationsCasiersAnneesListener;
import Coeur.Casier.ReservationsCasiersSeances;
import Coeur.Casier.ReservationsCasiersSeancesListener;
import Coeur.Personne.Adherent;
import Coeur.Personne.Adherents;
import Coeur.Personne.Personne;
import Coeur.Personne.Personnes;

import com.toedter.calendar.JCalendar;

public class VueCasier extends JPanel implements MouseListener, ReservationsCasiersAnneesListener, ReservationsCasiersSeancesListener, ActionListener, FocusListener {

	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	private JPanel panel_haut;
	private JPanel panel_droite;
	private JCalendar calendar;
	private JScrollPane scroll_panel_droite;
	private JScrollPane scroll_panel_haut;
	private Casiers casiers;
	private Casier casier;
	private ReservationsCasiersAnnees reservations_casiers_annees;
	private ReservationsCasiersSeances reservations_casiers_seances;
	
	private JButton boutton_reserver;
	private JButton boutton_vison_annnee;
	private JButton boutton_recharger;
	private JButton boutton_annuler;
	
	private JDialog fenetre_reserver;
	private JPanel interieur_fenetre_reserver;
	private JPanel panel_annee;
	private JPanel panel_seance;
	private JComboBox comboBox_genre;
	private JComboBox comboBox_nom_annee;
	private JComboBox comboBox_annee_annee;
	private JComboBox comboBox_nom_seance;
	private JComboBox comboBox_horaire_seance;
	private JTable table_casiers_seance;
	
	private Adherents adherents;
	private Personnes personnes;
	
	

	//100 casiers de 1 à 70 - 71 à 100
	//70 à l'année de 1 à 70
	//30 à la scéance (1heure) 71 à 100
	public VueCasier(MainWindow main_window) {
		try {
			casiers = new Casiers();
			while(creerCasierSql()){}
			reservations_casiers_annees = new ReservationsCasiersAnnees();
			reservations_casiers_seances = new ReservationsCasiersSeances();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		reservations_casiers_annees.addReservationsCasiersAnneesListener(this);
		reservations_casiers_seances.addReservationsCasiersSeancesListener(this);
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		this.main_window = main_window;
		buildPanelHautALaSceance();
		
		//Retour home
		JPanel panel_icones = new JPanel(new GridLayout(1, 0));
		panel_icones.setMaximumSize(new Dimension(main_window.getWidth()*2, 100));
		ImageIcon retour = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png");
		JButton boutton_retour_home = new JButton(Traductions.vueCasier(Options.getLangue(), "boutton_home"), retour);
		boutton_retour_home.setHorizontalTextPosition(0);
		boutton_retour_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_retour_home.addMouseListener(this);
		panel_icones.add(boutton_retour_home);
		//Vision à l'année
		ImageIcon annee = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Annee.png");
		boutton_vison_annnee = new JButton(Traductions.vueCasier(Options.getLangue(), "boutton_vue_annee"), annee);
		boutton_vison_annnee.setHorizontalTextPosition(0);
		boutton_vison_annnee.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vison_annnee.addMouseListener(this);
		panel_icones.add(boutton_vison_annnee);
		//Recharger
		ImageIcon recharger = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Recharger.png");
		boutton_recharger = new JButton(Traductions.vueCasier(Options.getLangue(), "boutton_recharger"), recharger);
		boutton_recharger.setHorizontalTextPosition(0);
		boutton_recharger.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_recharger.addMouseListener(this);
		panel_icones.add(boutton_recharger);
		//Réserver
		ImageIcon reserver = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Reserver.png");
		boutton_reserver = new JButton(Traductions.vueCasier(Options.getLangue(), "boutton_reserver"), reserver);
		boutton_reserver.setEnabled(false);
		boutton_reserver.setHorizontalTextPosition(0);
		boutton_reserver.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_reserver.addMouseListener(this);
		panel_icones.add(boutton_reserver);
		//Annuler
		ImageIcon annuler = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Annuler.png");
		boutton_annuler = new JButton(Traductions.vueCasier(Options.getLangue(), "boutton_annuler"), annuler);
		boutton_annuler.setHorizontalTextPosition(0);
		boutton_annuler.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_annuler.addMouseListener(this);
		boutton_annuler.setEnabled(false);
		panel_icones.add(boutton_annuler);
		
		add(panel_icones);
	}
	
	public void buildPanelHautALaSceance() {
		try {remove(panel_haut);}catch(NullPointerException e) {}
		panel_haut = new JPanel(new GridLayout(1, 2));
		calendar = new JCalendar();
		panel_haut.add(calendar);
		buildPanelDroiteCasiers();
		panel_haut.add(scroll_panel_droite);
		add(panel_haut, 0, 0);
		validate();
	}
	
	public void buildPanelHautALAnnee() {
		try {remove(panel_haut);}catch(NullPointerException e) {}
		panel_haut = new JPanel(new GridLayout(1, 1));
		JPanel panel_haut_temp = new JPanel(new GridLayout(0, 5));
		scroll_panel_haut = new JScrollPane(panel_haut_temp);
		ReservationsCasiersAnnees reservations_casiers_annees = null;
		try {
			reservations_casiers_annees = new ReservationsCasiersAnnees(2010);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ReservationCasierAnnee> list_casiers = reservations_casiers_annees.getCasiers();
		for(int i=1; i<71; i++) {
			JButton boutton = new JButton();
			boutton.setText(""+i);
			boutton.setHorizontalTextPosition(0);
			boutton.setVerticalTextPosition(JLabel.BOTTOM);
			boutton.addMouseListener(this);
			boutton.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/casier-vide.png"));
			for(int j=0; j<list_casiers.size(); j++) {
				if(i == list_casiers.get(j).getNumCasier()) {
					boutton.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/casier-plein.png"));
					boutton.setForeground(new Color(255, 0, 0));
				}
			}
			panel_haut_temp.add(boutton);
		}
		panel_haut.add(scroll_panel_haut);
		add(panel_haut, 0, 0);
		validate();
	}
	
	public void buildPanelDroiteCasiers() {
		try {panel_haut.remove(scroll_panel_droite);}catch(NullPointerException e) {}
		panel_droite = new JPanel(new GridLayout(0, 3));
		scroll_panel_droite = new JScrollPane(panel_droite);
		ReservationsCasiersSeances reservations_casiers_seances = null;
		try {
			reservations_casiers_seances = new ReservationsCasiersSeances(calendar.getDayChooser().getDay(), calendar.getMonthChooser().getMonth(), calendar.getYearChooser().getYear());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ReservationCasierSeance> list_casiers = reservations_casiers_seances.getCasiers();
		Integer [] compteur_nb_res_casier = new Integer[31];
		for(int i=0; i<31; i++) {
			compteur_nb_res_casier[i] = new Integer(0);
		}
		for(int i=71; i<101; i++) {
			ImageIcon icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/casier-vide.png");
			JButton boutton = new JButton(""+i);
			for(int cas = 0; cas<list_casiers.size(); cas++) {
				if(list_casiers.get(cas).getNumCasier() == i) {
					icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/casier-commence.png");
					boutton.setForeground(new Color(0, 255, 0));
					compteur_nb_res_casier[i-71]++;
				}
			}
			if(compteur_nb_res_casier[i-71] >= 12) {
				boutton.setForeground(new Color(255, 0, 0));
				icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/casier-plein.png");
			}
			boutton.setIcon(icon);
			boutton.setHorizontalTextPosition(0);
			boutton.setVerticalTextPosition(JLabel.BOTTOM);
			boutton.addMouseListener(this);
			panel_droite.add(boutton);
		}
		panel_haut.add(scroll_panel_droite);
		panel_haut.validate();
	}
	
	public void buildPanelDroiteCasierAnnee(int num_casier) {
		try {panel_haut.remove(scroll_panel_droite);}catch(NullPointerException e) {}
		panel_droite = new JPanel(new GridLayout(0, 1));
		BoxLayout layout = new BoxLayout(panel_droite, BoxLayout.PAGE_AXIS);
		panel_droite.setLayout(layout);
		scroll_panel_droite = new JScrollPane(panel_droite);
		//Titre
		panel_droite.setBorder(BorderFactory.createTitledBorder(Traductions.vueCasier(Options.getLangue(), "buildpaneldroitecasierannee_border")+num_casier));
		ReservationsCasiersAnnees reservations_casiers_annees = null;
		//Infos
		JLabel infos = new JLabel();
		try {
			adherents = new Adherents();
			reservations_casiers_annees = new ReservationsCasiersAnnees(num_casier, 2010);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		reservations_casiers_annees.addReservationsCasiersAnneesListener(this);
		ArrayList<ReservationCasierAnnee> list_casiers = reservations_casiers_annees.getCasiers();
		boolean trouve = false;
		for(int i=0; i<list_casiers.size() && !trouve; i++) {
			if(list_casiers.get(i).getNumCasier() == num_casier) {
				ArrayList<Adherent> list_adherents = adherents.getAdherents();
				for(int j=0; j<list_adherents.size() && !trouve; j++) {
					if(list_casiers.get(i).getNumAdherent() == list_adherents.get(j).getNumAdherent()) {
						trouve = true;
						infos.setText(list_adherents.get(j).getNom());
					}
				}
			}
		}
		if(!trouve) {
			infos.setText(Traductions.vueCasier(Options.getLangue(), "buildpaneldroitecasieranne_vide"));
		}
		panel_droite.add(infos);
		panel_haut.add(scroll_panel_droite);
		panel_haut.validate();
	}
	
	public void buildPanelDroiteCasierSeance(int num_casier) {
		try {panel_haut.remove(scroll_panel_droite);}catch(NullPointerException e) {}
		panel_droite = new JPanel(new GridLayout(0, 1));
		BoxLayout layout = new BoxLayout(panel_droite, BoxLayout.PAGE_AXIS);
		panel_droite.setLayout(layout);
		//Titre
		panel_droite.setBorder(BorderFactory.createTitledBorder(Traductions.vueCasier(Options.getLangue(), "buildpaneldroitecasierseance_border")+num_casier));

		//Table
		String [] stringColumns = Traductions.vueCasierTab(Options.getLangue(), "buildpaneldroitecasierseance_columns");
		ReservationsCasiersSeances reservations_casiers_seances1 = null;
		try {
			personnes = new Personnes();
			reservations_casiers_seances1 = new ReservationsCasiersSeances(num_casier, calendar.getDayChooser().getDay(), calendar.getMonthChooser().getMonth(), calendar.getYearChooser().getYear());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ReservationCasierSeance> list_seances = reservations_casiers_seances1.getCasiers();
		Object [][] donnees = new Object[13][4];
		for(int i=9; i<22; i++) {
			donnees[i-9][0] = i;
			donnees[i-9][1] = null;
			donnees[i-9][2] = null;
			donnees[i-9][3] = null;
			boolean trouve = false;
			try {
				personnes = new Personnes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(int j=0; j<list_seances.size() && !trouve; j++) {
				if(list_seances.get(j).getHeureDebut() == i) {
					Personne p = null;
					try {
						p = new Personne(list_seances.get(j).getIdPersonne());
					} 
					catch (SQLException e) {
						e.printStackTrace();
					}
					donnees[i-9][1] = p.getIdPersonne();
					donnees[i-9][2] = p.getNom();
					donnees[i-9][3] = p.getPrenom();
					trouve = true;
				}
			}
		}
		table_casiers_seance = new JTable(donnees, stringColumns);
		table_casiers_seance.addFocusListener(this);
		table_casiers_seance.setFillsViewportHeight(true);
		table_casiers_seance.setAutoCreateRowSorter(true);
		scroll_panel_droite = new JScrollPane(table_casiers_seance);
		panel_haut.add(scroll_panel_droite);
		panel_haut.validate();
	}
	
	public JPanel creerPanelAnnee() {
		JPanel panel_annee = new JPanel(new GridLayout(2, 2));
		panel_annee.setMaximumSize(new Dimension(450, 90));
		panel_annee.setBorder(BorderFactory.createTitledBorder(Traductions.vueCasier(Options.getLangue(), "creerpanelannee_border")));

		//Nom adhérent
		JLabel label_nom = new JLabel(Traductions.vueCasier(Options.getLangue(), "creerpanelannee_nom_adh")+" : ");
		panel_annee.add(label_nom);
		adherents = null;
		try {
			adherents = new Adherents();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ArrayList<Adherent> nom_adherent_temp = adherents.getAdherents();
		String [] nom_adherent = new String [nom_adherent_temp.size()];
		for(int i=0; i<nom_adherent_temp.size(); i++) {
			nom_adherent[i] = nom_adherent_temp.get(i).getNumAdherent()+" : "+nom_adherent_temp.get(i).getNom()+" "+nom_adherent_temp.get(i).getPrenom();
		}
		comboBox_nom_annee = new JComboBox(nom_adherent);
		comboBox_nom_annee.setMaximumSize(new Dimension(200, 30));
		comboBox_nom_annee.setEditable(true);
		panel_annee.add(comboBox_nom_annee);
		//Annee
		JLabel label_annee = new JLabel(Traductions.vueCasier(Options.getLangue(), "creerpanelannee_annee")+" : ");
		panel_annee.add(label_annee);
		String [] anneeS = {"2010", "2011", "2012", "2013", "2014", "2015"};
		comboBox_annee_annee = new JComboBox(anneeS);
		comboBox_annee_annee.setMaximumSize(new Dimension(200, 30));
		comboBox_annee_annee.setEditable(true);
		panel_annee.add(comboBox_annee_annee);
		//Fin
		interieur_fenetre_reserver.add(panel_annee);
		return panel_annee;
	}
	
	public JPanel creerPanelSeance() {
		JPanel panel_seance = new JPanel(new GridLayout(0, 2));
		panel_seance.setMaximumSize(new Dimension(450, 80));
		panel_seance.setBorder(BorderFactory.createTitledBorder(Traductions.vueCasier(Options.getLangue(), "creerpanelseance_border")));

		//Nom adhérent
		personnes = null;
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
		JLabel label_nom = new JLabel(Traductions.vueCasier(Options.getLangue(), "creerpanelseance_nom")+" : ");
		panel_seance.add(label_nom);
		comboBox_nom_seance = new JComboBox(nom_personnes);
		comboBox_nom_seance.setMaximumSize(new Dimension(200, 30));
		comboBox_nom_seance.setEditable(true);
		panel_seance.add(comboBox_nom_seance);
		//Horaire
		JLabel label_horaire = new JLabel(Traductions.vueCasier(Options.getLangue(), "creerpanelseance_horaire")+" : ");
		panel_seance.add(label_horaire);
		String [] horaireS = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
		comboBox_horaire_seance = new JComboBox(horaireS);
		comboBox_horaire_seance.setEditable(true);
		int select = table_casiers_seance.getSelectedRow();
		if(select != -1) {
			comboBox_horaire_seance.setSelectedIndex(select);
		}
		panel_seance.add(comboBox_horaire_seance);
		
		return panel_seance;
	}
	
	public void reserverCasierDialog(int type) {
		fenetre_reserver = new JDialog();
		fenetre_reserver.setTitle(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_titre"));
		fenetre_reserver.setMinimumSize(new Dimension(450, 260));
		interieur_fenetre_reserver = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre_reserver, BoxLayout.PAGE_AXIS);
		interieur_fenetre_reserver.setLayout(layout);
		fenetre_reserver.add(interieur_fenetre_reserver);
		fenetre_reserver.setVisible(true);
		fenetre_reserver.pack();

		//Num casier
		interieur_fenetre_reserver.setBorder(BorderFactory.createTitledBorder(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_border")+casier.getNumCasier()));
		
		//Type = Annee ou Sceance
		JPanel pan = new JPanel();
		BoxLayout layout2 = new BoxLayout(pan, BoxLayout.LINE_AXIS);
		pan.setLayout(layout2);
		JLabel label_genre = new JLabel(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_type")+" : ");
		pan.add(label_genre);
		String[] genresString = Traductions.vueCasierTab(Options.getLangue(), "reservercaiserdialog_type");
		comboBox_genre = new JComboBox(genresString);
		comboBox_genre.addActionListener(this);
		comboBox_genre.addMouseListener(this);
		comboBox_genre.setSelectedIndex(type);
		comboBox_genre.setMaximumSize(new Dimension(200, 30));
		pan.add(comboBox_genre);
		interieur_fenetre_reserver.add(pan, 0);
		
		//Panel
		if(type == 0)
			panel_annee = creerPanelAnnee();
		else
			panel_annee = creerPanelSeance();
		interieur_fenetre_reserver.add(panel_annee, 1);
		
		//Bouttons
		JPanel pan2 = new JPanel();
		BoxLayout layout3 = new BoxLayout(pan2, BoxLayout.LINE_AXIS);
		pan2.setLayout(layout3);
		JButton boutton_valider = new JButton(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_boutton_valider"));
		boutton_valider.addMouseListener(this);
		pan2.add(boutton_valider);
		JButton boutton_annuler = new JButton(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_boutton_annuler"));
		boutton_annuler.addMouseListener(this);
		pan2.add(boutton_annuler);
		interieur_fenetre_reserver.add(pan2, 2);
		
	}
	
	public void reserverCasier() {
		if(comboBox_genre.getSelectedIndex() == 0) {//Pour une année
			int reservation_num_adherent = Integer.parseInt(comboBox_nom_annee.getSelectedItem().toString().substring(0, comboBox_nom_annee.getSelectedItem().toString().indexOf(" ")));
			if(casier != null) {
				try {
					reservations_casiers_annees.ajouterReservationCasierAnnee(casier.getNumCasier(), reservation_num_adherent, Integer.parseInt((comboBox_annee_annee.getItemAt(comboBox_annee_annee.getSelectedIndex()).toString())));
				} 
				catch (NumberFormatException e) {
					Utiles.erreur(e.getLocalizedMessage());
					return;
				} 
				catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
					return;
				}
				Utiles.erreur("Cet adhérent doit payer 70€ (dont 20€ de caution)");
				fenetre_reserver.setVisible(false);
			}
		}
		else if(comboBox_genre.getSelectedIndex() == 1) {//Pour une séance
			int id_personne = Integer.parseInt(comboBox_nom_seance.getSelectedItem().toString().substring(0, comboBox_nom_seance.getSelectedItem().toString().indexOf(" ")));
			if(casier != null) {
				Calendar date_location = Calendar.getInstance();
				date_location.set(calendar.getYearChooser().getYear(), calendar.getMonthChooser().getMonth(), calendar.getDayChooser().getDay());
				int heure_debut = Integer.parseInt(comboBox_horaire_seance.getSelectedItem().toString());
				int heure_fin = heure_debut + 1;
				try {
					reservations_casiers_seances.ajouterReservationCasierSeance(casier.getNumCasier(), new Date(date_location.getTimeInMillis()), heure_debut, heure_fin, id_personne, -1);
				} 
				catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
					return;
				}
				Utiles.erreur("Cet adhérent doit payer 7€ (dont 5€ de caution)");
				fenetre_reserver.setVisible(false);
			}
		}
	}
	
	public void annulerReservationCasier() {
		Calendar date = Calendar.getInstance();
		if(boutton_vison_annnee.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_vue_seance"))) {//Annee
			try {
				reservations_casiers_annees.supprimerReservationCasierAnnee(casier.getNumCasier(), date.get(Calendar.YEAR));
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
		}
		else if(boutton_vison_annnee.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_vue_annee"))) {//Seance
			date.set(calendar.getYearChooser().getYear(), calendar.getMonthChooser().getMonth(), calendar.getDayChooser().getDay());
			try {
				reservations_casiers_seances.supprimerReservationCasierSeance(
						casier.getNumCasier(), 
						new Date(date.getTimeInMillis()), 
						table_casiers_seance.getSelectedRow()+9);
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton boutton = null;
		try {
			boutton = (JButton)event.getComponent();
		}
		catch(ClassCastException e) {
			return;
		}
		if(boutton.isValid() && boutton.isEnabled()) {
			if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_home"))) {
				main_window.changerTab(Traductions.vueCasier(Options.getLangue(), "boutton_home"), new PremiereVue(main_window));
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_recharger"))) {
				boutton_reserver.setEnabled(false);
				buildPanelDroiteCasiers();
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_vue_annee"))) {
				boutton_annuler.setEnabled(false);
				boutton_reserver.setEnabled(false);
				boutton_recharger.setEnabled(false);
				boutton_vison_annnee.setText(Traductions.vueCasier(Options.getLangue(), "boutton_vue_seance"));
				boutton_vison_annnee.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Seance.png"));
				buildPanelHautALAnnee();
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_vue_seance"))) {
				boutton_annuler.setEnabled(false);
				boutton_reserver.setEnabled(false);
				boutton_recharger.setEnabled(true);
				boutton_vison_annnee.setText(Traductions.vueCasier(Options.getLangue(), "boutton_vue_annee"));
				boutton_vison_annnee.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Casier/Annee.png"));
				buildPanelHautALaSceance();
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "boutton_reserver"))) {
				if(casier.getNumCasier()>70)
					reserverCasierDialog(1);
				else
					reserverCasierDialog(0);
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_boutton_valider"))) {
				reserverCasier();
			}
			else if(boutton.getText().equals(Traductions.vueCasier(Options.getLangue(), "reservercaiserdialog_boutton_annuler"))) {
				if(fenetre_reserver != null) {
					if(fenetre_reserver.isVisible()) {
						fenetre_reserver.setVisible(false);
					}
					else
						annulerReservationCasier();
				}
				else
					annulerReservationCasier();
			}
			
			else {
				Integer num_casier = 0;
				try {
					num_casier = Integer.parseInt(boutton.getText());
				}
				catch(Exception exep) {
					Utiles.erreur(exep.getLocalizedMessage());
				}
				boutton_reserver.setEnabled(true);
				if(num_casier>70) {
					buildPanelDroiteCasierSeance(num_casier);
				}
				else {
					buildPanelDroiteCasierAnnee(num_casier);
					boutton_annuler.setEnabled(true);
				}
				casier = new Casier(num_casier);
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
	public void ajouterReservationCasierAnnee(ReservationCasierAnnee reservationCasierAnnee) {
		boutton_reserver.setEnabled(true);
		buildPanelHautALAnnee();
		buildPanelDroiteCasierAnnee(reservationCasierAnnee.getNumCasier());
		casier = new Casier(reservationCasierAnnee.getNumCasier());
	}

	@Override
	public void supprimerReservationCasierAnnee(ReservationCasierAnnee reservationCasierAnnee) {
		boutton_reserver.setEnabled(true);
		buildPanelHautALAnnee();
		buildPanelDroiteCasierAnnee(reservationCasierAnnee.getNumCasier());
		casier = new Casier(reservationCasierAnnee.getNumCasier());
	}

	@Override
	public void ajouterReservationCasierSeance(ReservationCasierSeance reservationCasierSeance) {
		boutton_reserver.setEnabled(true);
		buildPanelDroiteCasierSeance(reservationCasierSeance.getNumCasier());
		casier = new Casier(reservationCasierSeance.getNumCasier());
	}

	@Override
	public void supprimerReservationCasierSeance(ReservationCasierSeance reservationCasierSeance) {
		boutton_reserver.setEnabled(true);
		buildPanelDroiteCasierSeance(casier.getNumCasier());
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		if(action.getActionCommand().equals("comboBoxChanged") && ((JComponent)action.getSource()).isValid()) {
			if(comboBox_genre.getSelectedIndex() == 0) {
				try{interieur_fenetre_reserver.remove(panel_annee);}catch(NullPointerException ee){}
				try{interieur_fenetre_reserver.remove(panel_seance);}catch(NullPointerException ee){}
				panel_annee = creerPanelAnnee();
				interieur_fenetre_reserver.add(panel_annee, 1);
				interieur_fenetre_reserver.validate();
			}
			else {
				try{interieur_fenetre_reserver.remove(panel_seance);}catch(NullPointerException ee){}
				try{interieur_fenetre_reserver.remove(panel_annee);}catch(NullPointerException ee){}
				panel_seance = creerPanelSeance();
				interieur_fenetre_reserver.add(panel_seance, 1);
				interieur_fenetre_reserver.validate();
			}
		}
		
	}
	
	public boolean creerCasierSql() {
		try {
			casiers.ajouterCasier();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		if(table_casiers_seance.getSelectedRow()>=0) {
			boutton_annuler.setEnabled(true);
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		boutton_annuler.setEnabled(false);
	}
}
