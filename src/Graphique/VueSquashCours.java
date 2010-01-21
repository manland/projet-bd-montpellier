package Graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;

import Coeur.Cours.CoursSquash;
import Coeur.Cours.CoursSquashCollectif;
import Coeur.Cours.CoursSquashIndividuel;
import Coeur.Cours.CoursSquashs;
import Coeur.Cours.CoursSquashsCollectifs;
import Coeur.Cours.CoursSquashsCollectifsListener;
import Coeur.Cours.CoursSquashsIndividuels;
import Coeur.Cours.CoursSquashsIndividuelsListener;
import Coeur.Cours.CoursSquashsListener;
import Coeur.Cours.CreneauCoursSquashCollectif;
import Coeur.Cours.CreneauCoursSquashIndividuel;
import Coeur.Cours.CreneauxCoursSquashsCollectifs;
import Coeur.Cours.CreneauxCoursSquashsCollectifsListener;
import Coeur.Cours.CreneauxCoursSquashsIndividuels;
import Coeur.Cours.CreneauxCoursSquashsIndividuelsListener;
import Coeur.Cours.InscriptionCoursSquashCollectif;
import Coeur.Cours.InscriptionsCoursSquashsCollectifs;
import Coeur.Personne.Adherent;
import Coeur.Personne.Adherents;
import Coeur.Personne.PersonnelSportif;
import Coeur.Personne.PersonnelsSportifs;
import Coeur.TerrainSquash.TerrainSquash;
import Coeur.TerrainSquash.TerrainsSquash;

public class VueSquashCours extends JPanel implements MouseListener, ActionListener, CoursSquashsListener, CreneauxCoursSquashsCollectifsListener, CreneauxCoursSquashsIndividuelsListener, CoursSquashsCollectifsListener, CoursSquashsIndividuelsListener {
	
	private static final long serialVersionUID = 1L;
	private String [] jourS = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
	private	String [] horaireS = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
	
	private Calendar calendar_semaine;
	
	private JScrollPane scroll_pane;
	private JPanel panel_interieur;
	private JDialog panel_creer_cours;
	private JComboBox comboBox_type_cours;
	private JLabel label_nb_personne;
	private JComboBox comboBox_nb_personne;
	private JLabel label_num_adherent;
	private JComboBox comboBox_num_adherent;
	private JComboBox comboBox_prof;
	private JComboBox comboBox_num_terrain;
	private JButton boutton_clicke;
	private JComboBox comboBox_nouveau_cours;
	private JComboBox comboBox_semaine;
	private JComboBox comboBox_annee;
	
	private JPanel panel_bas;
	private JScrollPane scrollPane_panel_bas;
	
	private Vector<Integer> vector_id_cours_collectif;
	private Vector<Vector<JComboBox>> vector_adh_cours_coll;
	private Vector<Vector<JButton>> vector_adh_cours_coll_boutton;
	private Vector<JComboBox> vector_adh_cours_coll_simple;
	private Vector<JButton> vector_adh_cours_coll_boutton_simple;

	public VueSquashCours() {
		calendar_semaine = Calendar.getInstance();
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		buildSemaineCours();
	}
	
	public void buildSemaineCours() {
		try {remove(scroll_pane);}catch(NullPointerException e) {}
		panel_interieur = new JPanel();
		BoxLayout layout = new BoxLayout(panel_interieur, BoxLayout.PAGE_AXIS);
		panel_interieur.setLayout(layout);
		scroll_pane = new JScrollPane(panel_interieur);
		
		JPanel panel_choix_semaine = new JPanel();
		BoxLayout layout_panel_semaine = new BoxLayout(panel_choix_semaine, BoxLayout.LINE_AXIS);
		panel_choix_semaine.setLayout(layout_panel_semaine);
		JLabel label_semaine = new JLabel("Semaine n° : ");
		panel_choix_semaine.add(label_semaine);
		
		Calendar cal = Calendar.getInstance();
		int nb_semaine_totale = 0;
		int compteur = 0;
		while(nb_semaine_totale<2) {
			cal.set(calendar_semaine.get(Calendar.YEAR), Calendar.DECEMBER, 31-compteur);
			nb_semaine_totale = cal.get(Calendar.WEEK_OF_YEAR);
			compteur++;
		}
		String [] list_semaine = new String [nb_semaine_totale];
		for(int i=1; i<nb_semaine_totale+1; i++) {
			cal.set(Calendar.WEEK_OF_YEAR, i);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			String jour1 = cal.get(Calendar.DAY_OF_MONTH)+" "+Utiles.moisToString(cal.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
			String jour2 = cal.get(Calendar.DAY_OF_MONTH)+" "+Utiles.moisToString(cal.get(Calendar.MONTH))+" "+cal.get(Calendar.YEAR);
			list_semaine[i-1] = ""+i+" ---> du "+jour1+" au "+jour2;
		}
		comboBox_semaine = new JComboBox(list_semaine);
		comboBox_semaine.addActionListener(this);
		comboBox_semaine.setMaximumSize(new Dimension(400, 30));
		comboBox_semaine.setMinimumSize(new Dimension(400, 30));
		comboBox_semaine.setSize(new Dimension(400, 30));
		comboBox_semaine.setPreferredSize(new Dimension(400, 30));
		comboBox_semaine.setSelectedIndex(calendar_semaine.get(Calendar.WEEK_OF_YEAR)-1);
		panel_choix_semaine.add(comboBox_semaine);
		
		JLabel label_annee = new JLabel("    -    Année : ");
		panel_choix_semaine.add(label_annee);
		String [] list_annees = new String [100];
		int premiere_annee = 2008;
		for(int i=0; i<100; i++) {
			list_annees[i] = ""+ (premiere_annee + i);
		}
		comboBox_annee = new JComboBox(list_annees);
		comboBox_annee.addActionListener(this);
		comboBox_annee.setMaximumSize(new Dimension(100, 30));
		comboBox_annee.setMinimumSize(new Dimension(100, 30));
		comboBox_annee.setSize(new Dimension(100, 30));
		comboBox_annee.setPreferredSize(new Dimension(100, 30));
		comboBox_annee.setSelectedItem(""+calendar_semaine.get(Calendar.YEAR));
		panel_choix_semaine.add(comboBox_annee);
		
		panel_interieur.add(panel_choix_semaine);
		
		Calendar calendar = Calendar.getInstance();
		//calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(comboBox_semaine.getSelectedItem().toString()));
		calendar.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(comboBox_semaine.getSelectedItem().toString().substring(0, comboBox_semaine.getSelectedItem().toString().indexOf(" "))));
		calendar.set(Calendar.YEAR, Integer.parseInt(comboBox_annee.getSelectedItem().toString()));
		
		if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -1);
		}
		else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -2);
		}
		else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -3);
		}
		else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -4);
		}
		else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			calendar.add(Calendar.DAY_OF_WEEK, -5);
		}
		//set du comboBox semaine
		comboBox_semaine.setSelectedItem(""+calendar.get(Calendar.WEEK_OF_YEAR));
		//calcul des calendars des autre jour
		Calendar [] tab_calendar = new Calendar[6];
		tab_calendar[0] = calendar;
		Calendar calendar_mardi = Calendar.getInstance();
		calendar_mardi.setTimeInMillis(calendar.getTimeInMillis());
		calendar_mardi.add(Calendar.DAY_OF_YEAR, 1);
		tab_calendar[1] = calendar_mardi;
		Calendar calendar_mercredi = Calendar.getInstance();
		calendar_mercredi.setTimeInMillis(calendar_mardi.getTimeInMillis());
		calendar_mercredi.add(Calendar.DAY_OF_YEAR, 1);
		tab_calendar[2] = calendar_mercredi;
		Calendar calendar_jeudi = Calendar.getInstance();
		calendar_jeudi.setTimeInMillis(calendar_mercredi.getTimeInMillis());
		calendar_jeudi.add(Calendar.DAY_OF_YEAR, 1);
		tab_calendar[3] = calendar_jeudi;
		Calendar calendar_vendredi = Calendar.getInstance();
		calendar_vendredi.setTimeInMillis(calendar_jeudi.getTimeInMillis());
		calendar_vendredi.add(Calendar.DAY_OF_YEAR, 1);
		tab_calendar[4] = calendar_vendredi;
		Calendar calendar_samedi = Calendar.getInstance();
		calendar_samedi.setTimeInMillis(calendar_vendredi.getTimeInMillis());
		calendar_samedi.add(Calendar.DAY_OF_YEAR, 1);
		tab_calendar[5] = calendar_samedi;
		
		Calendar cal_col = Calendar.getInstance();
		cal_col.set(Calendar.WEEK_OF_YEAR, calendar.get(Calendar.WEEK_OF_YEAR));
		int mois_debut = cal_col.get(Calendar.MONTH);
		cal_col.set(Calendar.DAY_OF_MONTH, 1);
			if(mois_debut == 1) {
				cal_col.set(Calendar.MONTH, Calendar.JANUARY);
			}
			else if(mois_debut == 2) {
				cal_col.set(Calendar.MONTH, Calendar.JANUARY);
			}
			else if(mois_debut == 4) {
				cal_col.set(Calendar.MONTH, Calendar.APRIL);
			}
			else if(mois_debut == 5) {
				cal_col.set(Calendar.MONTH, Calendar.APRIL);
			}
			else if(mois_debut == 7) {
				cal_col.set(Calendar.MONTH, Calendar.JULY);
			}
			else if(mois_debut == 8) {
				cal_col.set(Calendar.MONTH, Calendar.JULY);
			}
			else if(mois_debut == 10) {
				cal_col.set(Calendar.MONTH, Calendar.OCTOBER);
			}
			else if(mois_debut == 11) {
				cal_col.set(Calendar.MONTH, Calendar.OCTOBER);
			}
		
		CreneauxCoursSquashsCollectifs creneaux_cours_squashs_collectifs = null;
		CreneauxCoursSquashsIndividuels creneaux_cours_squashs_individuels = null;
		JLabel label = null;
		JPanel panel = null;
		for(int horaire=0; horaire<horaireS.length; horaire++) {
			panel = new JPanel();
			BoxLayout layout_panel = new BoxLayout(panel, BoxLayout.LINE_AXIS);
			panel.setLayout(layout_panel);
			panel_interieur.add(panel);
			for(int jour=0; jour<jourS.length+1; jour++) {
				int width_petit_label = 70;
				int height_petit_label = 50;
				int width_grand_boutton = 160;
				int heignt_grand_boutton = 50;
				if(jour == 0 && horaire == 0) {
					label = new JLabel("Horaire");
					label.setMaximumSize(new Dimension(width_petit_label, height_petit_label));
					label.setMinimumSize(new Dimension(width_petit_label, height_petit_label));
					label.setPreferredSize(new Dimension(width_petit_label, height_petit_label));
					label.setSize(new Dimension(width_petit_label, height_petit_label));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setBackground(new Color(255, 255, 255));
					label.setOpaque(true);
					label.setBorder(BorderFactory.createLineBorder(Color.black));
					panel.add(label);
				}
				else if(horaire == 0) {
					int i = jour-1;
					label = new JLabel(jourS[i]+" "+Utiles.dateToString(new Date(tab_calendar[i].getTimeInMillis())));
					label.setMaximumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					label.setMinimumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					label.setPreferredSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					label.setSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setBackground(new Color(255, 255, 255));
					label.setOpaque(true);
					label.setBorder(BorderFactory.createLineBorder(Color.black));
					panel.add(label);
				}
				else if(jour == 0) {
					int j = horaire-1;
					label = new JLabel(horaireS[j]);
					label.setMaximumSize(new Dimension(width_petit_label, height_petit_label));
					label.setMinimumSize(new Dimension(width_petit_label, height_petit_label));
					label.setPreferredSize(new Dimension(width_petit_label, height_petit_label));
					label.setSize(new Dimension(width_petit_label, height_petit_label));
					label.setHorizontalAlignment(SwingConstants.CENTER);
					label.setBackground(new Color(255, 255, 255));
					label.setOpaque(true);
					label.setBorder(BorderFactory.createLineBorder(Color.black));
					panel.add(label);
				}
				else {
					int i = jour-1;
					int j = horaire-1;
					JButton boutton = new JButton("Ajouter un cours");
					boutton.setMaximumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					boutton.setMinimumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					boutton.setPreferredSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					boutton.setSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
					boutton.setToolTipText(Utiles.dateToString(new Date(tab_calendar[i].getTimeInMillis())) + " - " + horaireS[j]);
					boutton.addMouseListener(this);
					panel.add(boutton);
					try {
						creneaux_cours_squashs_collectifs = new CreneauxCoursSquashsCollectifs(new Date(cal_col.getTimeInMillis()));
						creneaux_cours_squashs_individuels = new CreneauxCoursSquashsIndividuels(tab_calendar[i].get(Calendar.DAY_OF_MONTH), tab_calendar[i].get(Calendar.MONTH), tab_calendar[i].get(Calendar.DAY_OF_MONTH), tab_calendar[i].get(Calendar.MONTH), tab_calendar[i].get(Calendar.YEAR));
					} catch (SQLException e) {
						e.printStackTrace();
					}
					ArrayList<CreneauCoursSquashCollectif> list_creneau_cours_squash_collectif = creneaux_cours_squashs_collectifs.getCoursSquashs();
					ArrayList<CreneauCoursSquashIndividuel> list_creneau_cours_squash_individuel = creneaux_cours_squashs_individuels.getCoursSquashs();
					boolean trouve = false;
					for(int num_list=0; num_list<list_creneau_cours_squash_individuel.size() && !trouve; num_list++) {
						if(list_creneau_cours_squash_individuel.get(num_list).getHeureDebut() == Integer.parseInt(horaireS[j])) {
							boutton.setText("Détails");
							boutton.setForeground(new Color(30, 30, 200));
							boutton.setMaximumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
							boutton.setMinimumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
							boutton.setPreferredSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
							boutton.setSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
							trouve = true;//On continue pas le for car un seul cours suffit ici
						}
					}
					if(!trouve) {
						for(int num_col=0; num_col<list_creneau_cours_squash_collectif.size(); num_col++) {
							System.out.println(list_creneau_cours_squash_collectif.size());
							if(list_creneau_cours_squash_collectif.get(num_col).getJour() == i &&
									(list_creneau_cours_squash_collectif.get(num_col).getHeureDebut() == Integer.parseInt(horaireS[j]) ||
									list_creneau_cours_squash_collectif.get(num_col).getHeureDebut()+1 == Integer.parseInt(horaireS[j]))) {
								boutton.setText("Détails");
								boutton.setForeground(new Color(30, 30, 200));
								boutton.setMaximumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
								boutton.setMinimumSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
								boutton.setPreferredSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
								boutton.setSize(new Dimension(width_grand_boutton, heignt_grand_boutton));
								trouve = true;//On continue pas le for car un seul cours suffit ici
							}
						}
					}
				}
			}
		}
		add(scroll_pane, 0);
		validate();
	}
	
	public void buildPanelAjouterCours() {
		panel_creer_cours = new JDialog();
		panel_creer_cours.setTitle("Nouveau");
		panel_creer_cours.setMinimumSize(new Dimension(200, 200));
		JPanel panel_interieur = new JPanel();
		BoxLayout layout_panel_creer_cours = new BoxLayout(panel_interieur, BoxLayout.PAGE_AXIS);
		panel_interieur.setLayout(layout_panel_creer_cours);
		panel_interieur.setBorder(BorderFactory.createTitledBorder(boutton_clicke.getToolTipText()+"h00"));

		//Cours
		JPanel panel_cours = new JPanel();
		BoxLayout layout_panel_cours = new BoxLayout(panel_cours, BoxLayout.PAGE_AXIS);
		panel_cours.setLayout(layout_panel_cours);
		panel_cours.setBorder(BorderFactory.createTitledBorder("Cours"));
			//Nouveau cours ou cours déjà existant
			CoursSquashs cours_squashs = null;
			try {
				cours_squashs = new CoursSquashs();
			} catch (SQLException e1) {
				Utiles.erreur(e1.getLocalizedMessage());
			}
			ArrayList<CoursSquash> list_cours_squash = cours_squashs.getCoursSquashs();
			String [] list_cours = new String [list_cours_squash.size()+1];
			list_cours[0] = "Nouveau cours";
			for(int i=1; i<list_cours_squash.size(); i++) {
				PersonnelSportif personnel = null;
				try {
					personnel = new PersonnelSportif(list_cours_squash.get(i).getIdProf());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				list_cours[i] = list_cours_squash.get(i).getIdCours() + " : " + personnel.getNom() + " " + personnel.getPrenom() + " - " + list_cours_squash.get(i).getTypeCours();
			}
			comboBox_nouveau_cours = new JComboBox(list_cours);
			comboBox_nouveau_cours.addActionListener(this);
			panel_cours.add(comboBox_nouveau_cours);
			
			//Type = individuel ou collectif
			JPanel panel_type = new JPanel();
			BoxLayout layout_panel_type = new BoxLayout(panel_type, BoxLayout.LINE_AXIS);
			panel_type.setLayout(layout_panel_type);
			JLabel label_type = new JLabel("Type de cours : ");
			panel_type.add(label_type);
			String [] list_type_cours = {"individuel", "collectif"};
			comboBox_type_cours = new JComboBox(list_type_cours);
			comboBox_type_cours.addActionListener(this);
			panel_type.add(comboBox_type_cours);
			
			panel_cours.add(panel_type);
			
			//Prof
			JPanel panel_prof = new JPanel();
			BoxLayout layout_panel_prof = new BoxLayout(panel_prof, BoxLayout.LINE_AXIS);
			panel_prof.setLayout(layout_panel_prof);
			JLabel label_prof = new JLabel("Professeur : ");
			panel_prof.add(label_prof);
			PersonnelsSportifs personnels_sportifs = null;
			try {
				personnels_sportifs = new PersonnelsSportifs();
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			ArrayList<PersonnelSportif> list_personnel_sportif = personnels_sportifs.getPersonnelSportifs();
			String [] list_prof = new String[list_personnel_sportif.size()];
			for(int i=0; i<list_personnel_sportif.size(); i++) {
				list_prof[i] = list_personnel_sportif.get(i).getIdPersonne()+" : "+list_personnel_sportif.get(i).getNom()+" "+list_personnel_sportif.get(i).getPrenom();
			}
			comboBox_prof = new JComboBox(list_prof);
			panel_prof.add(comboBox_prof);
			
			panel_cours.add(panel_prof);
			
		panel_interieur.add(panel_cours);
		
		//Nb Personne setVisible(false) car pour individuel cela ne s'applique pas
		JPanel panel_nb_personne = new JPanel(new GridLayout(0, 2));
		
		label_nb_personne = new JLabel("Nombre de personnes : ");
		label_nb_personne.setVisible(false);
		panel_nb_personne.add(label_nb_personne);
		String [] list_nb_personnes = {"2", "3", "4", "5"};
		comboBox_nb_personne = new JComboBox(list_nb_personnes);
		comboBox_nb_personne.setVisible(false);
		panel_nb_personne.add(comboBox_nb_personne);
		
		panel_interieur.add(panel_nb_personne);
		
		//Num adhérent
		JPanel panel_num_adherent = new JPanel();
		
		label_num_adherent = new JLabel("Adhérent : ");
		panel_num_adherent.add(label_num_adherent);
		
		Adherents adherents = null;
		try {
			adherents = new Adherents();
		} catch (SQLException e1) {
			Utiles.erreur(e1.getLocalizedMessage());
		}
		ArrayList<Adherent> list_adherents = adherents.getAdherents();
		String [] list_adherentS = new String [list_adherents.size()];
		for(int i=0; i<list_adherentS.length; i++) {
			list_adherentS[i] = list_adherents.get(i).getNumAdherent() + " : " + list_adherents.get(i).getNom() + " " + list_adherents.get(i).getPrenom();
		}
		
		comboBox_num_adherent = new JComboBox(list_adherentS);
		panel_num_adherent.add(comboBox_num_adherent);
		
		panel_interieur.add(panel_num_adherent);
		
		//Terrain
		JPanel panel_terrain = new JPanel();
		
		JLabel label_terrain = new JLabel("Terrain : ");
		panel_terrain.add(label_terrain);
		TerrainsSquash terrains_squash = null;
		try {
			terrains_squash = new TerrainsSquash();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ArrayList<TerrainSquash> list_terrain_squash = terrains_squash.getTerrains();
		String [] list_terrains = new String[list_terrain_squash.size()];
		for(int i=0; i<list_terrain_squash.size(); i++) {
			list_terrains[i] = ""+list_terrain_squash.get(i).getNumTerrain();
		}
		comboBox_num_terrain = new JComboBox(list_terrains);
		panel_terrain.add(comboBox_num_terrain);
		
		panel_interieur.add(panel_terrain);
		
		//Bouttons
		JPanel panel_boutton = new JPanel(new GridLayout(0, 2));
		JButton boutton_valider = new JButton("Valider");
		boutton_valider.addMouseListener(this);
		panel_boutton.add(boutton_valider);
		JButton boutton_annuler = new JButton("Annuler");
		boutton_annuler.addMouseListener(this);
		panel_boutton.add(boutton_annuler);
		
		panel_interieur.add(panel_boutton);
		
		panel_creer_cours.add(panel_interieur);
		panel_creer_cours.setVisible(true);
		panel_creer_cours.pack();
	}
	
	public void buildPanelCours(Date date, int horaire) {		
		Calendar calendar = Calendar.getInstance();//pour individuel
		calendar.setTimeInMillis(date.getTime());
		int jour_debut = calendar.get(Calendar.DAY_OF_MONTH);
		int mois_debut = calendar.get(Calendar.MONTH);
		int jour_fin = jour_debut;
		int mois_fin = mois_debut;
		int annee = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.DAY_OF_MONTH, 1);//pour Collectif
			if(mois_debut == 1) {
				calendar.set(Calendar.MONTH, Calendar.JANUARY);
			}
			else if(mois_debut == 2) {
				calendar.set(Calendar.MONTH, Calendar.JANUARY);
			}
			else if(mois_debut == 4) {
				calendar.set(Calendar.MONTH, Calendar.APRIL);
			}
			else if(mois_debut == 5) {
				calendar.set(Calendar.MONTH, Calendar.APRIL);
			}
			else if(mois_debut == 7) {
				calendar.set(Calendar.MONTH, Calendar.JULY);
			}
			else if(mois_debut == 8) {
				calendar.set(Calendar.MONTH, Calendar.JULY);
			}
			else if(mois_debut == 10) {
				calendar.set(Calendar.MONTH, Calendar.OCTOBER);
			}
			else if(mois_debut == 11) {
				calendar.set(Calendar.MONTH, Calendar.OCTOBER);
			}
		CreneauxCoursSquashsCollectifs creneaux_cours_squashs_collectifs = null;
		CreneauxCoursSquashsIndividuels creneaux_cours_squashs_individuels = null;
		
		try {
			creneaux_cours_squashs_collectifs = new CreneauxCoursSquashsCollectifs(new Date(calendar.getTimeInMillis()));
			creneaux_cours_squashs_individuels = new CreneauxCoursSquashsIndividuels(jour_debut, mois_debut, jour_fin, mois_fin, annee);
		} catch (SQLException e1) {
			Utiles.erreur(e1.getLocalizedMessage());
		}

		ArrayList<CreneauCoursSquashCollectif> list_creneaux_cours_squashs_collectif = creneaux_cours_squashs_collectifs.getCoursSquashs();
		ArrayList<CreneauCoursSquashIndividuel> list_creneaux_cours_squashs_individuel = creneaux_cours_squashs_individuels.getCoursSquashs();
		try {remove(scrollPane_panel_bas);}catch(NullPointerException e) {}
		panel_bas = new JPanel();
		scrollPane_panel_bas = new JScrollPane(panel_bas);
		scrollPane_panel_bas.setMaximumSize(new Dimension(1000, 200));
		scrollPane_panel_bas.setMinimumSize(new Dimension(1000, 200));
		scrollPane_panel_bas.setPreferredSize(new Dimension(1000, 200));
		BoxLayout layout = new BoxLayout(panel_bas, BoxLayout.PAGE_AXIS);
		panel_bas.setLayout(layout);
		
		int cpt = 0;//compteur pour affichage des cours "Cours+cpt"
		
		//individuel
		for(int cours=0; cours<list_creneaux_cours_squashs_individuel.size(); cours++) {
			if(list_creneaux_cours_squashs_individuel.get(cours).getHeureDebut() == horaire) {
				JPanel panel = new JPanel();
				cpt++;
				panel.setBorder(BorderFactory.createTitledBorder("Cours"+cpt));
				BoxLayout layout1 = new BoxLayout(panel, BoxLayout.LINE_AXIS);
				panel.setLayout(layout1);
				
				//Récupération du cours
				CoursSquash cours_squash = null;
				try {
					cours_squash = new CoursSquash(list_creneaux_cours_squashs_individuel.get(cours).getIdCours());
				} catch (SQLException e1) {
					Utiles.erreur(e1.getLocalizedMessage());
				}
				//Type = individuel ou collectif
				JPanel panel_type = new JPanel();
				JLabel label_panel_type = new JLabel("Type de cours : ");
				panel_type.add(label_panel_type);
				String [] list_type_cours = {"individuel", "collectif"};
				JComboBox comboBox_type_cours = new JComboBox(list_type_cours);
				comboBox_type_cours.setMaximumSize(new Dimension(200, 30));
				comboBox_type_cours.setSelectedItem(cours_squash.getTypeCours());
				comboBox_type_cours.addActionListener(this);
				panel_type.add(comboBox_type_cours);
				panel.add(panel_type);
				
				//Prof
				JPanel panel_prof = new JPanel();
				JLabel label_panel_prof = new JLabel("Professeur : ");
				panel_prof.add(label_panel_prof);
				PersonnelsSportifs personnels_sportifs = null;
				try {
					personnels_sportifs = new PersonnelsSportifs();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				ArrayList<PersonnelSportif> list_personnel_sportif = personnels_sportifs.getPersonnelSportifs();
				String [] list_prof = new String[list_personnel_sportif.size()];
				String id_prof = "";
				for(int i=0; i<list_personnel_sportif.size(); i++) {
					list_prof[i] = list_personnel_sportif.get(i).getIdProf()+" : "+list_personnel_sportif.get(i).getNom()+" "+list_personnel_sportif.get(i).getPrenom();
					if(cours_squash.getIdProf() == list_personnel_sportif.get(i).getIdProf()) {
						id_prof = list_prof[i];
					}
				}
				JComboBox comboBox_prof = new JComboBox(list_prof);
				comboBox_prof.setMaximumSize(new Dimension(200, 30));
				comboBox_prof.setSelectedItem(id_prof);
				panel_prof.add(comboBox_prof);
				panel.add(panel_prof);
				
				CoursSquashIndividuel cours_squash_individuel = null;
				try {
					cours_squash_individuel = new CoursSquashIndividuel(cours_squash.getIdCours());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				//Num adhérent
				JPanel panel_num_adherent = new JPanel();
				
				JLabel label_num_adherent = new JLabel("Adhérent : ");
				panel_num_adherent.add(label_num_adherent);
				
				Adherents adherents = null;
				try {
					adherents = new Adherents();
				} catch (SQLException e1) {
					Utiles.erreur(e1.getLocalizedMessage());
				}
				ArrayList<Adherent> list_adherents = adherents.getAdherents();
				String [] list_adherentS = new String [list_adherents.size()];
				for(int i=0; i<list_adherentS.length; i++) {
					list_adherentS[i] = list_adherents.get(i).getNumAdherent() + " : " + list_adherents.get(i).getNom() + " " + list_adherents.get(i).getPrenom();
				}
				
				JComboBox comboBox_num_adherent = new JComboBox(list_adherentS);
				Adherent adh = null;
				try {
					adh = new Adherent(cours_squash_individuel.getNumAdherent());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				comboBox_num_adherent.setSelectedItem(adh.getNumAdherent()+" : "+adh.getNom()+" "+adh.getPrenom());
				panel_num_adherent.add(comboBox_num_adherent);
				
				panel.add(panel_num_adherent);
				
				//Terrain
				JPanel panel_terrain = new JPanel();
				JLabel label_panel_terrain = new JLabel("Terrain : ");
				panel_terrain.add(label_panel_terrain);
				TerrainsSquash terrains_squash = null;
				try {
					terrains_squash = new TerrainsSquash();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				ArrayList<TerrainSquash> list_terrain_squash = terrains_squash.getTerrains();
				String [] list_terrains = new String[list_terrain_squash.size()];
				for(int i=0; i<list_terrain_squash.size(); i++) {
					list_terrains[i] = ""+list_terrain_squash.get(i).getNumTerrain();
				}
				JComboBox comboBox_num_terrain = new JComboBox(list_terrains);
				comboBox_num_terrain.setMaximumSize(new Dimension(200, 30));
				comboBox_num_terrain.setSelectedItem(""+list_creneaux_cours_squashs_individuel.get(cours).getNumTerrainSquash());
				panel_terrain.add(comboBox_num_terrain);
				panel.add(panel_terrain);
				
				JButton boutton = new JButton("+");
				boutton.setToolTipText(Utiles.dateToString(date) + " - " + horaire);
				boutton.addMouseListener(this);
				panel.add(boutton);
				
				panel_bas.add(panel);
			}
		}
		
		//Collectif
		calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int jour = calendar.get(Calendar.DAY_OF_WEEK);
		System.out.println("jour avant : "+jour);
		if(jour == 2) {
			jour = 0;
		}
		else if(jour == 3) {
			jour = 1;
		}
		else if(jour == 4) {
			jour = 2;
		}
		else if(jour == 5) {
			jour = 3;
		}
		else if(jour == 6) {
			jour = 4;
		}
		else if(jour == 7) {
			jour = 5;
		}
		else if(jour == 1) {
			jour = 6;
		}

		vector_id_cours_collectif = new Vector<Integer>();
		vector_adh_cours_coll = new Vector<Vector<JComboBox>>();
		vector_adh_cours_coll_boutton = new Vector<Vector<JButton>>();
		for(int cours=0; cours<list_creneaux_cours_squashs_collectif.size(); cours++) {
			if(list_creneaux_cours_squashs_collectif.get(cours).getJour() == jour &&
					(list_creneaux_cours_squashs_collectif.get(cours).getHeureDebut() == horaire ||
					(list_creneaux_cours_squashs_collectif.get(cours).getHeureFin() - list_creneaux_cours_squashs_collectif.get(cours).getHeureDebut() == 2 &&//Cours collectif
							list_creneaux_cours_squashs_collectif.get(cours).getHeureDebut()+1 == horaire))) {
				JPanel panel = new JPanel();
				cpt++;
				panel.setBorder(BorderFactory.createTitledBorder("Cours"+cpt));
				BoxLayout layout1 = new BoxLayout(panel, BoxLayout.LINE_AXIS);
				panel.setLayout(layout1);
				
				//Récupération du cours
				CoursSquash cours_squash = null;
				try {
					cours_squash = new CoursSquash(list_creneaux_cours_squashs_collectif.get(cours).getIdCours());
				} catch (SQLException e1) {
					Utiles.erreur(e1.getLocalizedMessage());
				}
				vector_id_cours_collectif.add(cours_squash.getIdCours());
				//Type = individuel ou collectif
				JPanel panel_type = new JPanel();
				JLabel label_panel_type = new JLabel("Type de cours : ");
				panel_type.add(label_panel_type);
				String [] list_type_cours = {"individuel", "collectif"};
				JComboBox comboBox_type_cours = new JComboBox(list_type_cours);
				comboBox_type_cours.setMaximumSize(new Dimension(200, 30));
				comboBox_type_cours.setSelectedItem(cours_squash.getTypeCours());
				comboBox_type_cours.addActionListener(this);
				panel_type.add(comboBox_type_cours);
				panel.add(panel_type);
				
				//Prof
				JPanel panel_prof = new JPanel();
				JLabel label_panel_prof = new JLabel("Professeur : ");
				panel_prof.add(label_panel_prof);
				PersonnelsSportifs personnels_sportifs = null;
				try {
					personnels_sportifs = new PersonnelsSportifs();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				ArrayList<PersonnelSportif> list_personnel_sportif = personnels_sportifs.getPersonnelSportifs();
				String [] list_prof = new String[list_personnel_sportif.size()];
				String id_prof = "";
				for(int i=0; i<list_personnel_sportif.size(); i++) {
					list_prof[i] = list_personnel_sportif.get(i).getIdProf()+" : "+list_personnel_sportif.get(i).getNom()+" "+list_personnel_sportif.get(i).getPrenom();
					if(cours_squash.getIdProf() == list_personnel_sportif.get(i).getIdProf()) {
						id_prof = list_prof[i];
					}
				}
				JComboBox comboBox_prof = new JComboBox(list_prof);
				comboBox_prof.setMaximumSize(new Dimension(200, 30));
				comboBox_prof.setSelectedItem(id_prof);
				panel_prof.add(comboBox_prof);
				panel.add(panel_prof);
				
				
				CoursSquashCollectif cours_squash_collectif = null;
				try {
					cours_squash_collectif = new CoursSquashCollectif(cours_squash.getIdCours());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}

				JPanel panel_nb_personne = new JPanel();
				JLabel label_panel_nb_personne = new JLabel("Nombre de personnes : ");
				panel_nb_personne.add(label_panel_nb_personne);
				String [] list_nb_personnes = {"2", "3", "4", "5"};
				JComboBox comboBox_nb_personne = new JComboBox(list_nb_personnes);
				comboBox_nb_personne.setSelectedItem(""+cours_squash_collectif.getNbPersonne());
				comboBox_nb_personne.setMaximumSize(new Dimension(200, 30));
				panel_nb_personne.add(comboBox_nb_personne);
				panel.add(panel_nb_personne);
				
				JPanel panel_adherents = new JPanel();
				BoxLayout layout_panel_adherents = new BoxLayout(panel_adherents, BoxLayout.PAGE_AXIS);
				panel_adherents.setLayout(layout_panel_adherents);
				vector_adh_cours_coll_simple = new Vector<JComboBox>();
				vector_adh_cours_coll_boutton_simple = new Vector<JButton>();
				InscriptionsCoursSquashsCollectifs inscriptions_cours_squashs_collectifs = null;
				try {
					inscriptions_cours_squashs_collectifs = new InscriptionsCoursSquashsCollectifs(cours_squash_collectif.getIdCours());
				} catch (SQLException e1) {
					Utiles.erreur(e1.getLocalizedMessage());
				}
				ArrayList<InscriptionCoursSquashCollectif> list_inscrits = inscriptions_cours_squashs_collectifs.getInscriptionsCoursSquashsCollectifs();
				for(int i=0; i<cours_squash_collectif.getNbPersonne(); i++) {
					if(i<list_inscrits.size()) {
						panel_adherents.add(buildPanelAdhCoursColl(list_inscrits.get(i).getNumAdherent()));
					}
					else {
						panel_adherents.add(buildPanelAdhCoursColl(-1));
					}
				}
				vector_adh_cours_coll.add(vector_adh_cours_coll_simple);
				vector_adh_cours_coll_boutton.add(vector_adh_cours_coll_boutton_simple);
				panel.add(panel_adherents);
				
				//Terrain
				JPanel panel_terrain = new JPanel();
				JLabel label_panel_terrain = new JLabel("Terrain : ");
				panel_terrain.add(label_panel_terrain);
				TerrainsSquash terrains_squash = null;
				try {
					terrains_squash = new TerrainsSquash();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				ArrayList<TerrainSquash> list_terrain_squash = terrains_squash.getTerrains();
				String [] list_terrains = new String[list_terrain_squash.size()];
				for(int i=0; i<list_terrain_squash.size(); i++) {
					list_terrains[i] = ""+list_terrain_squash.get(i).getNumTerrain();
				}
				JComboBox comboBox_num_terrain = new JComboBox(list_terrains);
				comboBox_num_terrain.setMaximumSize(new Dimension(200, 30));
				comboBox_num_terrain.setSelectedItem(""+list_creneaux_cours_squashs_collectif.get(cours).getNumTerrainSquash());
				panel_terrain.add(comboBox_num_terrain);
				panel.add(panel_terrain);
				
				JButton boutton = new JButton("+");
				boutton.setToolTipText(Utiles.dateToString(date) + " - " + horaire);
				boutton.addMouseListener(this);
				panel.add(boutton);
				
				panel_bas.add(panel);
			}
		}
		
		add(scrollPane_panel_bas);
		validate();
	}
	
	public JPanel buildPanelAdhCoursColl(int num_adh) {
		JPanel panel = new JPanel();
		BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(layout);
		Adherents adherents = null;
		try {
			adherents = new Adherents();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ArrayList<Adherent> list_temp_adherents = adherents.getAdherents();
		String [] list_adherents = new String [list_temp_adherents.size()];
		int selected_index = 0;
		for(int i=0; i<list_temp_adherents.size(); i++) {
			list_adherents[i] = list_temp_adherents.get(i).getNumAdherent()+" : "+list_temp_adherents.get(i).getNom()+" "+list_temp_adherents.get(i).getPrenom();
			if(num_adh == list_temp_adherents.get(i).getNumAdherent()) {
				selected_index = i;
			}
		}
		JComboBox comboBox = new JComboBox(list_adherents);
		comboBox.setMaximumSize(new Dimension(200, 30));
		comboBox.setSelectedIndex(selected_index);
		if(num_adh!=-1) {
			comboBox.setEnabled(false);
		}
		vector_adh_cours_coll_simple.add(comboBox);
		panel.add(comboBox);
		
		JButton boutton = new JButton("Bloquer");
		if(num_adh!=-1) {
			boutton.setEnabled(false);
		}
		vector_adh_cours_coll_boutton_simple.add(boutton);
		boutton.addMouseListener(this);
		panel.add(boutton);
		
		return panel;
	}
	
	public void ajouterCoursIndividuel(CoursSquash coursSquash) {
		CoursSquashsIndividuels cours_quash_indiv = null;
		try {
			cours_quash_indiv = new CoursSquashsIndividuels();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		cours_quash_indiv.addCoursSquashsIndividuelsListener(this);
		try {
			int num_adh = Integer.parseInt(comboBox_num_adherent.getSelectedItem().toString().substring(0, comboBox_num_adherent.getSelectedItem().toString().indexOf(" ")));
			cours_quash_indiv.ajouterCoursSquashIndividuel(coursSquash.getIdCours(), num_adh);
		} catch (NumberFormatException e) {
			Utiles.erreur(e.getLocalizedMessage());
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
	}
	
	public void ajouterCreneauIndividuel(CoursSquash coursSquash) {
		CreneauxCoursSquashsIndividuels creneaux_cours_squashs = null;
		try {
			creneaux_cours_squashs = new CreneauxCoursSquashsIndividuels();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		creneaux_cours_squashs.addCreneauxCoursSquashsIndividuelsListener(this);
		Date date = Utiles.stringToDate(boutton_clicke.getToolTipText().substring(0, boutton_clicke.getToolTipText().indexOf("-")-1));
		int heure_debut = Integer.parseInt(boutton_clicke.getToolTipText().substring(boutton_clicke.getToolTipText().indexOf("-")+2));
		int heure_fin = heure_debut+1;
		try {
			creneaux_cours_squashs.ajouterCreneauCoursSquashIndividuel(coursSquash.getIdCours(), (comboBox_num_terrain.getSelectedIndex()+1), date, heure_debut, heure_fin);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		JButton boutton = new JButton();
		try {
			boutton = (JButton)event.getComponent();
		}
		catch(ClassCastException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		if(boutton.isEnabled()) {
			if(boutton.getText().equals("Ajouter un cours") || boutton.getText().equals("+")) {
				boutton_clicke = boutton;
				buildPanelAjouterCours();
			}
			else if(boutton.getText().equals("Valider")) {
				if(comboBox_nouveau_cours.getSelectedIndex() == 0) {//Nouveau cours
					CoursSquashs cours_squash = null;
					try {
						cours_squash = new CoursSquashs();
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					cours_squash.addCoursSquashsListener(this);
					String prof = comboBox_prof.getSelectedItem().toString();
					prof = prof.substring(0, prof.indexOf(" "));
					int id_prof = Integer.parseInt(prof);
					try {
						cours_squash.ajouterCoursSquash(id_prof, comboBox_type_cours.getSelectedItem().toString());
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
				}
				else {
					String id = comboBox_nouveau_cours.getSelectedItem().toString().substring(0, comboBox_nouveau_cours.getSelectedItem().toString().indexOf(" "));
					int id_cours_squash = Integer.parseInt(id);
					CoursSquash cours_squash = null;
					try {
						cours_squash = new CoursSquash(id_cours_squash);
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					ajouterCoursSquash(cours_squash);
				}
			}
			else if(boutton.getText().equals("Annuler")) {
				panel_creer_cours.setVisible(false);
			}
			else if(boutton.getText().equals("Détails")) {
				Date date = Utiles.stringToDate(boutton.getToolTipText().substring(0, boutton.getToolTipText().indexOf("-")-1));
				int heure_debut = Integer.parseInt(boutton.getToolTipText().substring(boutton.getToolTipText().indexOf("-")+2));
				buildPanelCours(date, heure_debut);
			}
			else if(boutton.getText().equals("Bloquer")) {
				boolean trouve = false;
				for(int i=0; i<vector_adh_cours_coll.size() && !trouve; i++) {
					for(int j=0; j<vector_adh_cours_coll.get(i).size(); j++) {
						if(vector_adh_cours_coll_boutton.get(i).get(j) == boutton) {
							trouve = true;
							InscriptionsCoursSquashsCollectifs inscriptions_cours_squashs_collectifs = null;
							try {
								inscriptions_cours_squashs_collectifs = new InscriptionsCoursSquashsCollectifs();
							} catch (SQLException e) {
								Utiles.erreur(e.getLocalizedMessage());
							}
							String adh = vector_adh_cours_coll.get(i).get(j).getSelectedItem().toString();
							int num_adherent = Integer.parseInt(adh.substring(0, adh.indexOf(" ")));
							try {
								inscriptions_cours_squashs_collectifs.ajouterCoursSquash(vector_id_cours_collectif.get(i), num_adherent);
							} catch (SQLException e) {
								Utiles.erreur(e.getLocalizedMessage());
								return;
							}
							boutton.setEnabled(false);
							vector_adh_cours_coll.get(i).get(j).setEnabled(false);
						}
					}
				}
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
		JComboBox comboBox = new JComboBox();
		try {
			comboBox = (JComboBox)event.getSource();
		}
		catch(ClassCastException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		if(comboBox == comboBox_type_cours) {
			if(comboBox_type_cours.getSelectedIndex() == 0) {//individuel
				label_num_adherent.setVisible(true);
				comboBox_num_adherent.setVisible(true);
				label_nb_personne.setVisible(false);
				comboBox_nb_personne.setVisible(false);
			}
			else {//collectif
				label_nb_personne.setVisible(true);
				comboBox_nb_personne.setVisible(true);
				label_num_adherent.setVisible(false);
				comboBox_num_adherent.setVisible(false);
			}
			panel_creer_cours.validate();
		}
		else if(comboBox == comboBox_nouveau_cours) {
			if(comboBox_nouveau_cours.getSelectedIndex() == 0) {
				comboBox_type_cours.setEnabled(true);
				comboBox_prof.setEnabled(true);
				comboBox_nb_personne.setEnabled(true);
				comboBox_num_adherent.setEnabled(true);
			}
			else {
				String ligne_selected = comboBox_nouveau_cours.getSelectedItem().toString();
				int id_cours = Integer.parseInt(ligne_selected.substring(0, ligne_selected.indexOf(":")-1));
				CoursSquash cours_squash = null;
				try {
					cours_squash = new CoursSquash(id_cours);
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				int id_prof = cours_squash.getIdProf();
				String type = cours_squash.getTypeCours();
				comboBox_type_cours.setSelectedItem(type);
				comboBox_type_cours.setEnabled(false);
				PersonnelSportif personnel = null;
				try {
					personnel = new PersonnelSportif(id_prof);
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				comboBox_prof.setSelectedItem(id_prof + " : " + personnel.getNom() + " " + personnel.getPrenom());
				comboBox_prof.setEnabled(false);
				
				if(type.equals("collectif")) {
					CoursSquashCollectif cours_col = null;
					try {
						cours_col = new CoursSquashCollectif(id_cours);
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					int nb_personne = cours_col.getNbPersonne();
					comboBox_nb_personne.setSelectedItem(""+nb_personne);
					comboBox_nb_personne.setEnabled(false);
				}
				else {
					CoursSquashIndividuel cours_indiv = null;
					try {
						cours_indiv = new CoursSquashIndividuel(id_cours);
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					Adherent adh = null;
					try {
						adh = new Adherent(cours_indiv.getNumAdherent());
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					comboBox_num_adherent.setSelectedItem(cours_indiv.getNumAdherent() + " : " + adh.getNom() + " " + adh.getPrenom());
					comboBox_num_adherent.setEnabled(false);
				}
			}
		}
		else if(comboBox == comboBox_semaine) {
			//int nouvelle_semaine = Integer.parseInt(comboBox_semaine.getSelectedItem().toString());
			int nouvelle_semaine = Integer.parseInt(comboBox_semaine.getSelectedItem().toString().substring(0, comboBox_semaine.getSelectedItem().toString().indexOf(" ")));
			if(nouvelle_semaine != calendar_semaine.get(Calendar.WEEK_OF_YEAR)) {
				calendar_semaine.set(Calendar.WEEK_OF_YEAR, nouvelle_semaine);
				buildSemaineCours();
			}
		}
		else if(comboBox == comboBox_annee) {
			if(Integer.parseInt(comboBox_annee.getSelectedItem().toString()) != calendar_semaine.get(Calendar.YEAR)) {
				calendar_semaine.set(Calendar.YEAR, Integer.parseInt(comboBox_annee.getSelectedItem().toString()));
				buildSemaineCours();
			}
		}
	}

	@Override
	public void ajouterCoursSquash(CoursSquash coursSquash) {
		if(comboBox_type_cours.getSelectedIndex()==0) {//individuel
			ajouterCoursIndividuel(coursSquash);
			ajouterCreneauIndividuel(coursSquash);
			panel_creer_cours.setVisible(false);
		}
		else {//collectif
			CreneauxCoursSquashsCollectifs creneaux_cours_squashs = null;
			try {
				creneaux_cours_squashs = new CreneauxCoursSquashsCollectifs();
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			creneaux_cours_squashs.addCreneauxCoursSquashsCollectifsListener(this);
			Date date = Utiles.stringToDate(boutton_clicke.getToolTipText().substring(0, boutton_clicke.getToolTipText().indexOf("-")-1));
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date.getTime());
			int heure_debut = Integer.parseInt(boutton_clicke.getToolTipText().substring(boutton_clicke.getToolTipText().indexOf("-")+2));
			int heure_fin = heure_debut+2;
			try {
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.DAY_OF_MONTH, 1);
				int mois_debut = calendar.get(Calendar.MONTH);
				if(mois_debut == 1) {
					calendar.set(Calendar.MONTH, Calendar.JANUARY);
				}
				else if(mois_debut == 2) {
					calendar.set(Calendar.MONTH, Calendar.JANUARY);
				}
				else if(mois_debut == 4) {
					calendar.set(Calendar.MONTH, Calendar.APRIL);
				}
				else if(mois_debut == 5) {
					calendar.set(Calendar.MONTH, Calendar.APRIL);
				}
				else if(mois_debut == 7) {
					calendar.set(Calendar.MONTH, Calendar.JULY);
				}
				else if(mois_debut == 8) {
					calendar.set(Calendar.MONTH, Calendar.JULY);
				}
				else if(mois_debut == 10) {
					calendar.set(Calendar.MONTH, Calendar.OCTOBER);
				}
				else if(mois_debut == 11) {
					calendar.set(Calendar.MONTH, Calendar.OCTOBER);
				}
				Date date_semetre = new Date(calendar.getTimeInMillis());
				creneaux_cours_squashs.ajouterCreneauCoursSquashCollectif(coursSquash.getIdCours(), (comboBox_num_terrain.getSelectedIndex()+1), (cal.get(Calendar.DAY_OF_WEEK)-2), heure_debut, heure_fin, date_semetre);
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			
			CoursSquashsCollectifs cours_quash_collectifs = null;
			try {
				cours_quash_collectifs = new CoursSquashsCollectifs();
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			cours_quash_collectifs.addCoursSquashsCollectifsListener(this);
			try {
				cours_quash_collectifs.ajouterCoursSquashCollectif(coursSquash.getIdCours(), Integer.parseInt(comboBox_nb_personne.getSelectedItem().toString()));
			} catch (NumberFormatException e) {
				Utiles.erreur(e.getLocalizedMessage());
			} catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			panel_creer_cours.setVisible(false);
		}
	}

	@Override
	public void supprimerCoursSquash(CoursSquash coursSquash) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCoursSquashCollectif(CoursSquashCollectif coursSquashCollectif) {
		Date date = Utiles.stringToDate(boutton_clicke.getToolTipText().substring(0, boutton_clicke.getToolTipText().indexOf("-")-1));
		int heure_debut = Integer.parseInt(boutton_clicke.getToolTipText().substring(boutton_clicke.getToolTipText().indexOf("-")+2));
		buildPanelCours(date, heure_debut);
	}

	@Override
	public void supprimerCoursSquashCollectif(CoursSquashCollectif coursSquashCollectif) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCoursSquashIndividuel(CoursSquashIndividuel coursSquashIndividuel) {
		Date date = Utiles.stringToDate(boutton_clicke.getToolTipText().substring(0, boutton_clicke.getToolTipText().indexOf("-")-1));
		int heure_debut = Integer.parseInt(boutton_clicke.getToolTipText().substring(boutton_clicke.getToolTipText().indexOf("-")+2));
		buildPanelCours(date, heure_debut);
	}

	@Override
	public void supprimerCoursSquashIndividuel(CoursSquashIndividuel coursSquashIndividuel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCreneauCoursSquashCollectif(CreneauCoursSquashCollectif creneauCoursSquashCollectif) {
		buildSemaineCours();
		try {remove(scrollPane_panel_bas);}catch(NullPointerException e) {}
	}

	@Override
	public void supprimerCreneauCoursSquashCollectif(
			CreneauCoursSquashCollectif creneauCoursSquashCollectif) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCreneauCoursSquashIndividuel(
			CreneauCoursSquashIndividuel creneauCoursSquashIndividuel) {
		buildSemaineCours();
		try {remove(scrollPane_panel_bas);}catch(NullPointerException e) {}
		
	}

	@Override
	public void supprimerCreneauCoursSquashIndividuel(
			CreneauCoursSquashIndividuel creneauCoursSquashIndividuel) {
		// TODO Auto-generated method stub
		
	}
	
}
