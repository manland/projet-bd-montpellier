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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.toedter.calendar.JCalendar;

import Coeur.Personne.Adherent;
import Coeur.Personne.Personne;
import Coeur.Personne.Personnes;
import Coeur.TerrainSquash.ReservationTerrainSquash;
import Coeur.TerrainSquash.ReservationsTerrainsSquash;
import Coeur.TerrainSquash.ReservationsTerrainsSquashListener;
import Coeur.TerrainSquash.TerrainSquash;
import Coeur.TerrainSquash.TerrainsSquash;

public class VueSquash extends JPanel implements MouseListener, ReservationsTerrainsSquashListener, FocusListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	
	private JButton boutton_cours_terrain;
	private JButton boutton_recharger;
	private JButton boutton_reserver;
	private JButton boutton_annuler;
	
	private JPanel panel_haut;
	private JCalendar calendar;
	private JScrollPane scroll_panel_droite;
	private JPanel panel_droite;
	private JTable table_terrain_seance;
	private JDialog fenetre_reserver;
	private JPanel interieur_fenetre_reserver;
	private JComboBox comboBox_nom_seance;
	private JComboBox comboBox_annee_seance;
	private JComboBox comboBox_horaire_seance;
	private JComboBox comboBox_horaire_seance_minute;
	private JLabel textField_prix;
	private Integer num_terrain;
	
	private TerrainsSquash terrains_squash;
	private ReservationsTerrainsSquash reservations_terrains_squash ;
	private ArrayList<ReservationTerrainSquash> list_seances;
	private JCheckBox checkBox_carte;
	
	public VueSquash(MainWindow main_window) {
		try {
			terrains_squash = new TerrainsSquash();
			reservations_terrains_squash = new ReservationsTerrainsSquash();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		reservations_terrains_squash.addReservationsTerrainsListener(this);
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		this.main_window = main_window;
		buildPanelHaut();
		
		//Retour home
		JPanel panel_icones = new JPanel(new GridLayout(1, 0));
		panel_icones.setMaximumSize(new Dimension(main_window.getWidth()*2, 100));
		ImageIcon retour = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png");
		JButton boutton_retour_home = new JButton("Home", retour);
		boutton_retour_home.setHorizontalTextPosition(0);
		boutton_retour_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_retour_home.addMouseListener(this);
		panel_icones.add(boutton_retour_home);
		//Cours/Terrains
		ImageIcon cours_terrain = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Cours.png");
		boutton_cours_terrain = new JButton("Vue Cours", cours_terrain);
		boutton_cours_terrain.setHorizontalTextPosition(0);
		boutton_cours_terrain.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_cours_terrain.addMouseListener(this);
		panel_icones.add(boutton_cours_terrain);
		//Recharger
		ImageIcon recharger = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Recharger.png");
		boutton_recharger = new JButton("Recharger", recharger);
		boutton_recharger.setHorizontalTextPosition(0);
		boutton_recharger.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_recharger.addMouseListener(this);
		panel_icones.add(boutton_recharger);
		//Réserver
		ImageIcon reserver = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Reserver.png");
		boutton_reserver = new JButton("Réserver", reserver);
		boutton_reserver.setEnabled(false);
		boutton_reserver.setHorizontalTextPosition(0);
		boutton_reserver.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_reserver.addMouseListener(this);
		panel_icones.add(boutton_reserver);
		//Annuler
		ImageIcon annuler = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Annuler.png");
		boutton_annuler = new JButton("Annuler", annuler);
		boutton_annuler.setHorizontalTextPosition(0);
		boutton_annuler.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_annuler.addMouseListener(this);
		boutton_annuler.setEnabled(false);
		panel_icones.add(boutton_annuler);
		
		add(panel_icones);
	}
	
	public void buildPanelHaut() {
		try {remove(panel_haut);}catch(NullPointerException e) {}
		panel_haut = new JPanel(new GridLayout(1, 2));
		calendar = new JCalendar();
		panel_haut.add(calendar);
		buildPanelDroiteTerrains();
		panel_haut.add(scroll_panel_droite);
		add(panel_haut, 0, 0);
		validate();
	}
	
	public void buildPanelHautCours() {
		try {remove(panel_haut);}catch(NullPointerException e) {}
		panel_haut = new VueSquashCours();
		add(panel_haut, 0, 0);
		validate();
	}
	
	public void buildPanelDroiteTerrains() {
		try {panel_haut.remove(scroll_panel_droite);}catch(NullPointerException e) {}
		panel_droite = new JPanel(new GridLayout(0, 3));
		scroll_panel_droite = new JScrollPane(panel_droite);
		ArrayList<TerrainSquash> list_terrains = terrains_squash.getTerrains();
		ReservationsTerrainsSquash reservations_terrains_squash = null;
		try {
			reservations_terrains_squash = new ReservationsTerrainsSquash(calendar.getDayChooser().getDay(), calendar.getMonthChooser().getMonth(), calendar.getYearChooser().getYear());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		ArrayList<ReservationTerrainSquash> list_reservations = reservations_terrains_squash.getTerrains();
		Integer [] compteur_nb_res_creaneau = new Integer[31];
		for(int i=0; i<31; i++) {
			compteur_nb_res_creaneau[i] = new Integer(0);
		}
		for(int i=1; i<list_terrains.size()+1; i++) {
			ImageIcon icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/terrain-vide.png");
			JButton boutton = new JButton(""+i);
			for(int cas = 0; cas<list_reservations.size(); cas++) {
				if(list_reservations.get(cas).getNumTerrainSquash() == i) {
					icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/terrain-commence.png");
					boutton.setForeground(new Color(0, 255, 0));
					compteur_nb_res_creaneau[i]++;
				}
			}
			if(compteur_nb_res_creaneau[i] >= 12) {
				boutton.setForeground(new Color(255, 0, 0));
				icon = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/terrain-plein.png");
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
	
	public void buildPanelDroiteTerrain(int num_terrain) {
		try {panel_haut.remove(scroll_panel_droite);}catch(NullPointerException e) {}
		panel_droite = new JPanel(new GridLayout(0, 1));
		BoxLayout layout = new BoxLayout(panel_droite, BoxLayout.PAGE_AXIS);
		panel_droite.setLayout(layout);
		//Titre
		panel_droite.setBorder(BorderFactory.createTitledBorder("Terrain n°"+num_terrain));

		//Table
		String [] stringColumns = {"Heure début", "Minute début", "Heure fin", "Minute fin", "Prix", "N°", "Nom", "Prenom"};
		list_seances = null;
		ReservationsTerrainsSquash reservations_terrains_squash = null;
		try {
			reservations_terrains_squash = new ReservationsTerrainsSquash(num_terrain, calendar.getDayChooser().getDay(), calendar.getMonthChooser().getMonth(), calendar.getYearChooser().getYear());
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		list_seances = reservations_terrains_squash.getTerrains();
		Object [][] donnees = new Object[list_seances.size()][8];
		for(int i=0; i<list_seances.size(); i++) {
			Personne p = null;
			try {
				p = new Personne(list_seances.get(i).getIdPersonne());
			} 
			catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			donnees[i][0] = list_seances.get(i).getHeureDebut();
			donnees[i][1] = list_seances.get(i).getMinuteDebut();
			donnees[i][2] = list_seances.get(i).getHeureFin();
			donnees[i][3] = list_seances.get(i).getMinuteFin();
			donnees[i][4] = list_seances.get(i).getPrixPaye();
			donnees[i][5] = p.getIdPersonne();
			donnees[i][6] = p.getNom();
			donnees[i][7] = p.getPrenom();
		}
		table_terrain_seance = new JTable(donnees, stringColumns);
		table_terrain_seance.addFocusListener(this);
		table_terrain_seance.setFillsViewportHeight(true);
		table_terrain_seance.setAutoCreateRowSorter(true);
		scroll_panel_droite = new JScrollPane(table_terrain_seance);
		panel_haut.add(scroll_panel_droite);
		panel_haut.validate();
	}
	
	public void reserverTerrainDialog() {
		fenetre_reserver = new JDialog();
		fenetre_reserver.setTitle("Réservation");
		fenetre_reserver.setMinimumSize(new Dimension(450, 260));
		interieur_fenetre_reserver = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre_reserver, BoxLayout.PAGE_AXIS);
		interieur_fenetre_reserver.setLayout(layout);
		fenetre_reserver.add(interieur_fenetre_reserver);
		fenetre_reserver.setVisible(true);
		fenetre_reserver.pack();

		//Num casier
		interieur_fenetre_reserver.setBorder(BorderFactory.createTitledBorder("Terrain n°"+num_terrain));
		
		//Panel
		JPanel panel_seance = new JPanel(new GridLayout(0, 2));
		panel_seance.setMaximumSize(new Dimension(450, 110));

		//Nom personnes
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
		JLabel label_nom = new JLabel("Nom : ");
		panel_seance.add(label_nom);
		comboBox_nom_seance = new JComboBox(nom_personnes);
		comboBox_nom_seance.addActionListener(this);
		comboBox_nom_seance.setMaximumSize(new Dimension(200, 30));
		comboBox_nom_seance.setEditable(true);
		panel_seance.add(comboBox_nom_seance);
		//Annee
		JLabel label_annee = new JLabel("Année : ");
		panel_seance.add(label_annee);
		String [] anneeS = new String[5];
		for(int i=0; i<5; i++) {
			anneeS[i] = ""+(Calendar.getInstance().get(Calendar.YEAR)+i);
		}
		comboBox_annee_seance = new JComboBox(anneeS);
		comboBox_annee_seance.setMaximumSize(new Dimension(200, 30));
		comboBox_annee_seance.setEditable(true);
		panel_seance.add(comboBox_annee_seance);
		//Horaire
		JLabel label_horaire = new JLabel("Heure : ");
		panel_seance.add(label_horaire);
		String [] horaireS = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
		comboBox_horaire_seance = new JComboBox(horaireS);
		comboBox_horaire_seance.setEditable(true);
		int select = table_terrain_seance.getSelectedRow();
		if(select != -1) {
			comboBox_horaire_seance.setSelectedIndex(select);
		}
		panel_seance.add(comboBox_horaire_seance);
		interieur_fenetre_reserver.add(panel_seance);
		//Minute
		JLabel label_minute = new JLabel("Minute : ");
		panel_seance.add(label_minute);
		String [] minuteS = {"0", "10", "20", "30", "40", "50"};
		comboBox_horaire_seance_minute = new JComboBox(minuteS);
		comboBox_horaire_seance_minute.setEditable(true);
		panel_seance.add(comboBox_horaire_seance_minute);
		interieur_fenetre_reserver.add(panel_seance);
		//Prix
		JLabel label_prix = new JLabel("Prix : ");
		panel_seance.add(label_prix);
		textField_prix = new JLabel("10 €");
		panel_seance.add(textField_prix);
		interieur_fenetre_reserver.add(panel_seance);
		//ou carte
		checkBox_carte = new JCheckBox("Carte de 10 séances");
		checkBox_carte.addMouseListener(this);
		interieur_fenetre_reserver.add(checkBox_carte);
		
		//Bouttons
		JPanel pan2 = new JPanel();
		BoxLayout layout3 = new BoxLayout(pan2, BoxLayout.LINE_AXIS);
		pan2.setLayout(layout3);
		JButton boutton_valider = new JButton("Valider");
		boutton_valider.addMouseListener(this);
		pan2.add(boutton_valider);
		JButton boutton_annuler = new JButton("Annuler");
		boutton_annuler.addMouseListener(this);
		pan2.add(boutton_annuler);
		interieur_fenetre_reserver.add(pan2);
		
		siAdherentMiniPrix(comboBox_nom_seance);
	}
	
	public void reserverTerrain() {
		
		int reservation_id_personne = Integer.parseInt(comboBox_nom_seance.getSelectedItem().toString().substring(0, comboBox_nom_seance.getSelectedItem().toString().indexOf(" ")));
		Calendar cal = Calendar.getInstance();
		cal.set(calendar.getYearChooser().getYear(), calendar.getMonthChooser().getMonth(), calendar.getDayChooser().getDay());
		Date date = new Date(cal.getTimeInMillis());
		int heure_debut = Integer.parseInt(comboBox_horaire_seance.getSelectedItem().toString());
		int minute_debut = Integer.parseInt(comboBox_horaire_seance_minute.getSelectedItem().toString());
		int heure_fin = heure_debut;
		int minute_fin = minute_debut+40;
		if(minute_fin == 60) {
			minute_fin = 0;
			heure_fin = heure_fin+1;
		}
		else if(minute_fin > 60){
			heure_fin = heure_fin+1;
			minute_fin = minute_fin%60;
		}
		if(minute_fin == 60) {
			minute_fin = 0;
		}
		int prix = Integer.parseInt(textField_prix.getText().substring(0, textField_prix.getText().indexOf(" ")));
		try {
			reservations_terrains_squash.ajouterReservationTerrainSquash(num_terrain, date, heure_debut, heure_fin, minute_debut, minute_fin, reservation_id_personne, prix);
		} 
		catch (NumberFormatException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_reserver.setVisible(false);
	}
	
	public void annulerReservationTerrain() {
		int ligne_sected = table_terrain_seance.getSelectedRow();
		int heure_debut = list_seances.get(ligne_sected).getHeureDebut();
		int minute_debut = list_seances.get(ligne_sected).getMinuteDebut();
		int heure_fin = list_seances.get(ligne_sected).getHeureFin();
		int minute_fin = list_seances.get(ligne_sected).getMinuteFin();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, calendar.getDayChooser().getDay());
		cal.set(Calendar.MONTH, calendar.getMonthChooser().getMonth());
		cal.set(Calendar.YEAR, calendar.getYearChooser().getYear());
		Date date = new Date(cal.getTimeInMillis());
		try {
			reservations_terrains_squash.supprimerReservationTerrainSquash(num_terrain, date, heure_debut, heure_fin, minute_debut, minute_fin);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
	}

	@Override
	public void ajouterReservationTerrainSquash(ReservationTerrainSquash reservationCasierSeance) {
		buildPanelDroiteTerrain(reservationCasierSeance.getNumTerrainSquash());
	}

	@Override
	public void supprimerReservationTerrainSquash(ReservationTerrainSquash reservationCasierSeance) {
		buildPanelDroiteTerrain(num_terrain);
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton boutton = null;
		try {
			boutton = (JButton)event.getComponent();
		}
		catch(ClassCastException e) {
			JCheckBox checkBox = null;
			try {
				checkBox = (JCheckBox)event.getComponent();
			}
			catch(ClassCastException ee) {
				return;
			}
			if(checkBox.isSelected()) {
				textField_prix.setText("0 €");
			}
			else {
				textField_prix.setText("5 €");
			}
			siAdherentMiniPrix(comboBox_nom_seance);
			return;
		}
		if(boutton.isValid() && boutton.isEnabled()) {
			if(boutton.getText().equals("Home")) {
				main_window.changerTab("Home", new PremiereVue(main_window));
			}
			else if(boutton == boutton_recharger) {
				boutton_reserver.setEnabled(false);
				buildPanelDroiteTerrains();
			}
			else if(boutton == boutton_reserver) {
				reserverTerrainDialog();
			}
			else if(boutton == boutton_cours_terrain) {
				if(boutton.getText().equals("Vue Cours")) {
					ImageIcon cours_terrain = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Terrain.png");
					boutton_cours_terrain.setIcon(cours_terrain);
					boutton_cours_terrain.setText("Vue Terrain");
					boutton_recharger.setEnabled(false);
					buildPanelHautCours();
				}
				else {
					ImageIcon cours_terrain = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/Squash/Cours.png");
					boutton_cours_terrain.setText("Vue Cours");
					boutton_cours_terrain.setIcon(cours_terrain);
					boutton_recharger.setEnabled(true);
					buildPanelHaut();
				}
			}
			else if(boutton.getText().equals("Valider")) {
				reserverTerrain();
			}
			else if(boutton.getText().equals("Annuler")) {
				if(fenetre_reserver != null) {
					if(fenetre_reserver.isVisible()) {
						fenetre_reserver.setVisible(false);
					}
					else
						annulerReservationTerrain();
				}
				else
					annulerReservationTerrain();
			}
			else {
				num_terrain = 0;
				try {
					num_terrain = Integer.parseInt(boutton.getText());
				}
				catch(Exception exep) {
					return;
				}
				boutton_reserver.setEnabled(true);
				buildPanelDroiteTerrain(num_terrain);
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
	public void focusGained(FocusEvent arg0) {
		if(table_terrain_seance.getSelectedRow()>=0) {
			boutton_annuler.setEnabled(true);
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		boutton_annuler.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		siAdherentMiniPrix((JComboBox) event.getSource());
	}
	
	public void siAdherentMiniPrix(JComboBox comboBox) {
		if(!textField_prix.getText().equals("0 €")) {
			Adherent adh = null;
			try {
				adh = new Adherent(Integer.parseInt(comboBox.getSelectedItem().toString().substring(0, comboBox.getSelectedItem().toString().indexOf(" "))));
			} 
			catch (NumberFormatException e) {} 
			catch (SQLException e) {}
			if(adh!=null) {
				textField_prix.setText("5 €");
			}
			else {
				textField_prix.setText("10 €");
			}
		}
	}
}
