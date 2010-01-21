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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Coeur.Gym.CoursGym;
import Coeur.Gym.CoursGyms;
import Coeur.Gym.CoursGymsListener;
import Coeur.Gym.CreneauCoursGym;
import Coeur.Gym.CreneauxCoursGym;
import Coeur.Gym.CreneauxCoursGymListener;
import Coeur.Gym.SalleGym;
import Coeur.Gym.SallesGym;
import Coeur.Personne.PersonnelSportif;
import Coeur.Personne.PersonnelsSportifs;

public class VueGym extends JPanel implements MouseListener, ActionListener, CoursGymsListener, CreneauxCoursGymListener {

	private static final long serialVersionUID = 1L;
	private Calendar calendar_semaine;
	private JPanel panel_interieur;
	private JScrollPane scroll_pane;
	private JComboBox comboBox_semaine;
	private JComboBox comboBox_annee;
	private String [] jourS = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
	private	String [] horaireS = {"9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22"};
	private JPanel panel_bas;
	private JScrollPane scrollPane_panel_bas;
	private JDialog panel_creer_cours;
	private JComponent boutton_clicke;
	private JComboBox comboBox_nouveau_cours;
	private JComboBox comboBox_prof;
	private JComboBox comboBox_id_salle;
	private JTextField textField_type_cours;
	
	public VueGym(MainWindow main_window) {
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
		
		CreneauxCoursGym creneaux_cours_gym = null;
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
						creneaux_cours_gym = new CreneauxCoursGym();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					ArrayList<CreneauCoursGym> list_creneau_cours_gym = creneaux_cours_gym.getCreneauxCoursGym();
					boolean trouve = false;
					for(int num_list=0; num_list<list_creneau_cours_gym.size() && !trouve; num_list++) {
					//	System.out.println("i="+num_list+"  --->  "+list_creneau_cours_gym.get(num_list).getHeureDebut()+"  ---->   "+Integer.parseInt(horaireS[j]));
						if(list_creneau_cours_gym.get(num_list).getHeureDebut() == Integer.parseInt(horaireS[j])) {
							Calendar cal1 = Calendar.getInstance();
							cal1.setTime(list_creneau_cours_gym.get(num_list).getDateCours());
							
							//System.out.println(tab_calendar[i].get(Calendar.DAY_OF_YEAR));
							Calendar cal2 = Calendar.getInstance();
							cal2.set(Calendar.DAY_OF_WEEK, i);
							cal2.set(Calendar.MONTH, calendar_semaine.get(Calendar.MONTH));
							cal2.set(Calendar.YEAR, calendar_semaine.get(Calendar.YEAR));
//							System.out.println(num_list);
//							System.out.println(cal1.get(Calendar.DAY_OF_YEAR)+"=="+cal2.get(Calendar.DAY_OF_YEAR));
//							System.out.println(cal1.get(Calendar.YEAR)+"=="+cal2.get(Calendar.YEAR));
						
							if(cal1.get(Calendar.DAY_OF_YEAR)==tab_calendar[i].get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == tab_calendar[i].get(Calendar.YEAR)) {
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
			CoursGyms cours_gyms = null;
			try {
				cours_gyms = new CoursGyms();
			} catch (SQLException e1) {
				Utiles.erreur(e1.getLocalizedMessage());
			}
			ArrayList<CoursGym> list_cours_gym = cours_gyms.getCoursGyms();
			String [] list_cours = new String [list_cours_gym.size()+1];
			list_cours[0] = "Nouveau cours";
			for(int i=1; i<list_cours_gym.size(); i++) {
				PersonnelSportif personnel = null;
				try {
					personnel = new PersonnelSportif(list_cours_gym.get(i).getIdProf());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				list_cours[i] = list_cours_gym.get(i).getTypeCours() + " : " + personnel.getNom() + " " + personnel.getPrenom();
			}
			comboBox_nouveau_cours = new JComboBox(list_cours);
			comboBox_nouveau_cours.addActionListener(this);
			panel_cours.add(comboBox_nouveau_cours);
			
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
				list_prof[i] = list_personnel_sportif.get(i).getIdProf()+" : "+list_personnel_sportif.get(i).getNom()+" "+list_personnel_sportif.get(i).getPrenom();
			}
			comboBox_prof = new JComboBox(list_prof);
			panel_prof.add(comboBox_prof);
			
			panel_cours.add(panel_prof);
			
		panel_interieur.add(panel_cours);
		
		//Type du cours
		JPanel panel_type_cours = new JPanel();
		JLabel label_type_cours = new JLabel("Type : ");
		panel_type_cours.add(label_type_cours);
		textField_type_cours = new JTextField("", 30);
		panel_type_cours.add(textField_type_cours);
		panel_interieur.add(panel_type_cours);
		
		//Terrain
		JPanel panel_terrain = new JPanel();
		
		JLabel label_terrain = new JLabel("Terrain : ");
		panel_terrain.add(label_terrain);
		SallesGym salles_gyms = null;
		try {
			salles_gyms = new SallesGym();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ArrayList<SalleGym> list_salle_gym = salles_gyms.getSallesGym();
		String [] list_salles = new String[list_salle_gym.size()];
		for(int i=0; i<list_salle_gym.size(); i++) {
			list_salles[i] = ""+list_salle_gym.get(i).getIdSalle();
		}
		comboBox_id_salle = new JComboBox(list_salles);
		panel_terrain.add(comboBox_id_salle);
		
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
		int mois_debut = calendar.get(Calendar.MONTH);
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
			
		CreneauxCoursGym creneaux_cours_gym = null;
		
		try {
			creneaux_cours_gym = new CreneauxCoursGym();
		} catch (SQLException e1) {
			Utiles.erreur(e1.getLocalizedMessage());
		}

		ArrayList<CreneauCoursGym> list_creneaux_cours_gym = creneaux_cours_gym.getCreneauxCoursGym();
		try {remove(scrollPane_panel_bas);}catch(NullPointerException e) {}
		panel_bas = new JPanel();
		scrollPane_panel_bas = new JScrollPane(panel_bas);
		scrollPane_panel_bas.setMinimumSize(new Dimension(30, 300));
		BoxLayout layout = new BoxLayout(panel_bas, BoxLayout.PAGE_AXIS);
		panel_bas.setLayout(layout);
		
		
		int cpt = 0;//compteur pour affichage des cours "Cours+cpt"
		for(int cours=0; cours<list_creneaux_cours_gym.size(); cours++) {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(list_creneaux_cours_gym.get(cours).getDateCours());
			
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(date);
			if(list_creneaux_cours_gym.get(cours).getHeureDebut() == horaire && 
					cal1.get(Calendar.DAY_OF_YEAR)==cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
				JPanel panel = new JPanel();
				cpt++;
				panel.setBorder(BorderFactory.createTitledBorder("Cours"+cpt));
				BoxLayout layout1 = new BoxLayout(panel, BoxLayout.LINE_AXIS);
				panel.setLayout(layout1);
				
				//Récupération du cours
				CoursGym cours_gym = null;
				try {
					cours_gym = new CoursGym(list_creneaux_cours_gym.get(cours).getTypeCours());
				} 
				catch (SQLException e1) {
					Utiles.erreur(e1.getLocalizedMessage());
				}

				//Type de cours
				JPanel panel_type = new JPanel();
				JLabel label_panel_type = new JLabel("Type de cours : ");
				panel_type.add(label_panel_type);
				JTextField textField_type_cours = new JTextField(cours_gym.getTypeCours());
				panel_type.add(textField_type_cours);
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
					if(cours_gym.getIdProf() == list_personnel_sportif.get(i).getIdProf()) {
						id_prof = list_prof[i];
					}
				}
				JComboBox comboBox_prof = new JComboBox(list_prof);
				comboBox_prof.setMaximumSize(new Dimension(200, 30));
				comboBox_prof.setSelectedItem(id_prof);
				panel_prof.add(comboBox_prof);
				panel.add(panel_prof);
				
				//Terrain
				JPanel panel_salle = new JPanel();
				JLabel label_panel_salle = new JLabel("Salle : ");
				panel_salle.add(label_panel_salle);
				SallesGym salles_gym = null;
				try {
					salles_gym = new SallesGym();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				ArrayList<SalleGym> list_salles_gym = salles_gym.getSallesGym();
				String [] list_terrains = new String[list_salles_gym.size()];
				String terrain_a_selectionner = "";
				for(int i=0; i<list_salles_gym.size(); i++) {
					list_terrains[i] = ""+list_salles_gym.get(i).getIdSalle();
					if(list_salles_gym.get(i).getIdSalle() == list_creneaux_cours_gym.get(cours).getIdSalle()) {
						terrain_a_selectionner = ""+list_salles_gym.get(i).getIdSalle();
					}
				}
				JComboBox comboBox_num_salle = new JComboBox(list_terrains);
				comboBox_num_salle.setMaximumSize(new Dimension(200, 30));
				comboBox_num_salle.setSelectedItem(terrain_a_selectionner);
				panel_salle.add(comboBox_num_salle);
				panel.add(panel_salle);
				
				//Capacité
				SalleGym salle_gym = null;
				try {
					salle_gym = new SalleGym(list_creneaux_cours_gym.get(cours).getIdSalle());
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				JPanel panel_capacite_salle = new JPanel();
				JLabel label_panel_capacite_salle = new JLabel("Salle : ");
				panel_capacite_salle.add(label_panel_capacite_salle);
				JLabel label_capacite_salle = new JLabel(""+salle_gym.getIdSalle());
				panel_capacite_salle.add(label_capacite_salle);
				
				//bouton pour ajouter un nouveau cours
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
					CoursGyms cours_gym = null;
					try {
						cours_gym = new CoursGyms();
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					cours_gym.addCoursGymsListener(this);
					String prof = comboBox_prof.getSelectedItem().toString();
					prof = prof.substring(0, prof.indexOf(" "));
					int id_prof = Integer.parseInt(prof);
					try {
						cours_gym.ajouterCoursGym(textField_type_cours.getText(), id_prof);
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
				}
				else {
					String type = comboBox_nouveau_cours.getSelectedItem().toString().substring(0, comboBox_nouveau_cours.getSelectedItem().toString().indexOf(" "));
					CoursGym cours_gym = null;
					try {
						cours_gym = new CoursGym(type);
					} catch (SQLException e) {
						Utiles.erreur(e.getLocalizedMessage());
					}
					ajouterCoursGym(cours_gym);
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
		if(comboBox == comboBox_nouveau_cours) {
			if(comboBox_nouveau_cours.getSelectedIndex() == 0) {
				textField_type_cours.setEnabled(true);
				comboBox_prof.setEnabled(true);
			}
			else {
				String ligne_selected = comboBox_nouveau_cours.getSelectedItem().toString();
				String type_cours = ligne_selected.substring(0, ligne_selected.indexOf(":")-1);
				CoursGym cours_gym = null;
				try {
					cours_gym = new CoursGym(type_cours);
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				int id_prof = cours_gym.getIdProf();
				String type = cours_gym.getTypeCours();
				textField_type_cours.setText(type);
				textField_type_cours.setEnabled(false);
				PersonnelSportif personnel = null;
				try {
					personnel = new PersonnelSportif(id_prof);
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				comboBox_prof.setSelectedItem(id_prof + " : " + personnel.getNom() + " " + personnel.getPrenom());
				comboBox_prof.setEnabled(false);
			}
		}
		else if(comboBox == comboBox_semaine) {
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
	public void ajouterCoursGym(CoursGym coursGym) {
		CreneauxCoursGym creneaux_cours_gym = null;
		try {
			creneaux_cours_gym = new CreneauxCoursGym();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		Date date_cours = Utiles.stringToDate(boutton_clicke.getToolTipText().substring(0, boutton_clicke.getToolTipText().indexOf("-")-1));
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date_cours.getTime());
		int heure_debut = Integer.parseInt(boutton_clicke.getToolTipText().substring(boutton_clicke.getToolTipText().indexOf("-")+2));
		int heure_fin = heure_debut+1;
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
			creneaux_cours_gym.ajouterCreneauCoursGym(Integer.parseInt(comboBox_id_salle.getSelectedItem().toString()), textField_type_cours.getText(), date_cours, heure_debut, heure_fin, date_semetre);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		panel_creer_cours.setVisible(false);
	}

	@Override
	public void supprimerCoursGym(CoursGym coursGym) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ajouterCreneauCoursGym(CreneauCoursGym creneauCoursGym) {
		buildSemaineCours();
		try {remove(panel_bas);}catch(NullPointerException e) {}
	}

	@Override
	public void supprimerCreneauCoursGym(CreneauCoursGym creneauCoursGym) {
		// TODO Auto-generated method stub
		
	}
}
