package Graphique;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Coeur.Personne.Personne;
import Coeur.Personne.PersonnelAdministratif;
import Coeur.Personne.PersonnelSportif;
import Coeur.Personne.PersonnelsAdministratifs;
import Coeur.Personne.PersonnelsAdministratifsListener;
import Coeur.Personne.PersonnelsSportifs;
import Coeur.Personne.PersonnelsSportifsListener;
import Coeur.Personne.Personnes;
import Coeur.Personne.PersonnesListener;

public class VuePersonnels extends JPanel implements MouseListener, TableModelListener, PersonnelsSportifsListener, PersonnelsAdministratifsListener, PersonnesListener {
	private static final long serialVersionUID = 1L;
	private String[] columnNames = {"Id", "Num Emplyé/Id Prof", "Nom", "Prénom", "Date de Naissance", "Adresse", "Affectation/Type", "Genre"};
	private Object[][] data;
	
	private JTable table;
	private JButton boutton_mod_user;
	private JButton boutton_del_user;
	private int ligne_selected;
	
	private JDialog fenetre_ajouter_modifier;
	private JPanel interieur_fenetre;
	private JComboBox comboBox_genre;
	private JTextField textField_nom;
	private JTextField textField_prenom;
	private JTextField textField_adresse;
	private JComboBox comboBox_jour;
	private JComboBox comboBox_mois;
	private JComboBox comboBox_annee;
	private JTextField textField_affectation;
	
	private PersonnelsSportifs personnels_sportif;
	private PersonnelsAdministratifs personnels_administratif;
	private Personnes personnes;
	private MainWindow main_window;
	
	public VuePersonnels(MainWindow main_window) {
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		this.main_window = main_window;
		//Récupération des données
		try {
			personnels_sportif = new PersonnelsSportifs();
			personnels_administratif = new PersonnelsAdministratifs();
			personnes = new Personnes();
		} catch (Exception e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		personnels_sportif.addPersonnelsSportifsListener(this);
		personnels_administratif.addPersonnelsAdministratifsListener(this);
		personnes.addPersonnesListener(this);
		ArrayList<PersonnelSportif> list_personnel_sportif = personnels_sportif.getPersonnelSportifs();
		ArrayList<PersonnelAdministratif> list_personnel_administratif = personnels_administratif.getPersonnelsAdministratifs();
		data = new Object[list_personnel_sportif.size()+list_personnel_administratif.size()][8];
		for(int i=0; i<list_personnel_sportif.size(); i++) {
			data[i][0] = list_personnel_sportif.get(i).getIdPersonne();
			data[i][1] = list_personnel_sportif.get(i).getIdProf();
			data[i][2] = list_personnel_sportif.get(i).getNom();
			data[i][3] = list_personnel_sportif.get(i).getPrenom();
			data[i][4] = Utiles.dateToString(list_personnel_sportif.get(i).getDate());
			data[i][5] = list_personnel_sportif.get(i).getAdresse();
			data[i][6] = list_personnel_sportif.get(i).getTypeSport();
			data[i][7] = "Sportif";
		}
		for(int i=list_personnel_sportif.size(); i<list_personnel_administratif.size()+list_personnel_sportif.size(); i++) {
			data[i][0] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getIdPersonne();
			data[i][1] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getNumEmploye();
			data[i][2] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getNom();
			data[i][3] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getPrenom();
			data[i][4] = Utiles.dateToString(list_personnel_administratif.get(i-list_personnel_sportif.size()).getDate());
			data[i][5] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getAdresse();
			data[i][6] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getAffectation();
			data[i][7] = "Administratif";
		}
		//table
		table = new JTable(data, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.addMouseListener(this);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.getModel().addTableModelListener(this);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(1).setMaxWidth(20);
		add(scrollPane);
		//Icones du dessous
		JPanel icones = new JPanel(new GridLayout(1, 4));
		ImageIcon retour = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png");
		JButton boutton_retour_home = new JButton("Home", retour);
		boutton_retour_home.setHorizontalTextPosition(0);
		boutton_retour_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_retour_home.addMouseListener(this);
		boutton_retour_home.setMaximumSize(new Dimension(200, 50));
		icones.add(boutton_retour_home);
		ImageIcon add_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/add-user.png");
		JButton boutton_add_user = new JButton("Ajouter", add_user);
		boutton_add_user.setHorizontalTextPosition(0);
		boutton_add_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_add_user.addMouseListener(this);
		boutton_add_user.setMaximumSize(new Dimension(200, 50));
		icones.add(boutton_add_user);
		ImageIcon mod_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/edit-user.png");
		boutton_mod_user = new JButton("Modifier", mod_user);
		boutton_mod_user.setHorizontalTextPosition(0);
		boutton_mod_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_mod_user.addMouseListener(this);
		boutton_mod_user.setEnabled(false);
		boutton_mod_user.setMaximumSize(new Dimension(200, 50));
		icones.add(boutton_mod_user);
		ImageIcon del_user = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/remove-user.png");
		boutton_del_user = new JButton("Supprimer", del_user);
		boutton_del_user.setHorizontalTextPosition(0);
		boutton_del_user.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_del_user.addMouseListener(this);
		boutton_del_user.setEnabled(false);
		boutton_del_user.setMaximumSize(new Dimension(200, 50));
		icones.add(boutton_del_user);
		add(icones);
	}
	
	public void ajouterPersonnel(String nom, String prenom, String date, String adresse, String affectation) {
		try {
			personnes.ajouterPersonne(nom, prenom, Date.valueOf(date), adresse);
		} 
		catch (Exception e) {
			ArrayList<Personne> list_personne = personnes.getPersonnes();
			boolean trouve = false;
			int id_personne = -1;
			for(int i=0; i<list_personne.size() && !trouve; i++) {
				if(list_personne.get(i).getNom().equals(nom) && list_personne.get(i).getPrenom().equals(prenom)) {
					trouve = true;
					id_personne = list_personne.get(i).getIdPersonne();
				}
			}
			if(trouve) {
				try {
					if(comboBox_genre.getSelectedItem().toString().equals("Personnel Sportif"))
						personnels_sportif.ajouterPersonnelSportif(id_personne, textField_affectation.getText());
					else
						personnels_administratif.ajouterPersonnelAdministratif(id_personne, textField_affectation.getText());
				} 
				catch (Exception ee) {
					Utiles.erreur(e.getLocalizedMessage());
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
	
	public void mofidierPersonnel(int ligne_selected, String nom, String prenom, String date, String adresse, String affectation) {
		try {
			if(data[ligne_selected][7].toString().equals("Sportif"))
				personnels_sportif.modifierPersonnelSportif(Integer.parseInt(data[ligne_selected][0].toString()), nom, prenom, Date.valueOf(date), adresse, affectation);
			else
				personnels_administratif.modifierPersonnelAdministratif(Integer.parseInt(data[ligne_selected][0].toString()), nom, prenom, Date.valueOf(date), adresse, affectation);
		} catch (NumberFormatException e) {
			Utiles.erreur(e.getLocalizedMessage());
		} catch (Exception e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		fenetre_ajouter_modifier.setVisible(false);
	}
	
	public void supprimerPersonnel(int ligne_selected) {
		try {
			if(data[ligne_selected][7].toString().equals("Sportif"))
				personnels_sportif.supprimerPersonnelSportif(Integer.parseInt(data[ligne_selected][1].toString()));
			else
				personnels_administratif.supprimerPersonnelAdministratif(Integer.parseInt(data[ligne_selected][1].toString()));
		} catch (NumberFormatException e) {
			Utiles.erreur(e.getLocalizedMessage());
		} catch (Exception e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
	}
	
	public void ajouterModifier(int genre, String nom, String prenom, String date, String adresse, String affectation) {
		fenetre_ajouter_modifier = new JDialog();
		if(nom.equals(""))
			fenetre_ajouter_modifier.setTitle("Ajouter");
		else
			fenetre_ajouter_modifier.setTitle("Modifier");
		fenetre_ajouter_modifier.setSize(450, 180);
		fenetre_ajouter_modifier.setMinimumSize(new Dimension(450, 180));
		interieur_fenetre = new JPanel(new GridLayout(0, 2));
		
		//Genre = admin ou sportif
		JLabel label_genre = new JLabel("Genre : ");
		interieur_fenetre.add(label_genre);
		String[] genresString = { "Personnel Administratif", "Personnel Sportif" };
		comboBox_genre = new JComboBox(genresString);
		if(genre == -1)
			comboBox_genre.setEnabled(false);
		else
			comboBox_genre.setSelectedIndex(genre);
		interieur_fenetre.add(comboBox_genre);
		//Nom
		JLabel label_nom = new JLabel("Nom : ");
		interieur_fenetre.add(label_nom);
		textField_nom = new JTextField(nom, 20);
		interieur_fenetre.add(textField_nom);
		//Prénom
		JLabel label_prenom = new JLabel("Prénom : ");
		interieur_fenetre.add(label_prenom);
		textField_prenom = new JTextField(prenom, 20);
		interieur_fenetre.add(textField_prenom);
		//Adresse
		JLabel label_adresse = new JLabel("Adresse : ");
		interieur_fenetre.add(label_adresse);
		textField_adresse = new JTextField(adresse, 20);
		interieur_fenetre.add(textField_adresse);
		//DateNaissance
		JLabel label_date = new JLabel("Date de Naissance : ");
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
		String [] list_mois = {"Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
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
		//Affectation
		JLabel label_affectation = new JLabel("Affectation/Type : ");
		interieur_fenetre.add(label_affectation);
		textField_affectation = new JTextField(affectation, 20);
		interieur_fenetre.add(textField_affectation);
		//Bouttons
		JButton boutton_valider = new JButton("Valider");
		boutton_valider.addMouseListener(this);
		interieur_fenetre.add(boutton_valider);
		JButton boutton_annuler = new JButton("Annuler");
		boutton_annuler.addMouseListener(this);
		interieur_fenetre.add(boutton_annuler);
		
		fenetre_ajouter_modifier.add(interieur_fenetre);
		fenetre_ajouter_modifier.setVisible(true);
		fenetre_ajouter_modifier.pack();
	}
	
	public void updateTable() {
		ArrayList<PersonnelSportif> list_personnel_sportif = personnels_sportif.getPersonnelSportifs();
		ArrayList<PersonnelAdministratif> list_personnel_administratif = personnels_administratif.getPersonnelsAdministratifs();
		data = new Object[list_personnel_sportif.size()+list_personnel_administratif.size()][8];
		for(int i=0; i<list_personnel_sportif.size(); i++) {
			data[i][0] = list_personnel_sportif.get(i).getIdPersonne();
			data[i][1] = list_personnel_sportif.get(i).getIdProf();
			data[i][2] = list_personnel_sportif.get(i).getNom();
			data[i][3] = list_personnel_sportif.get(i).getPrenom();
			data[i][4] = Utiles.dateToString(list_personnel_sportif.get(i).getDate());
			data[i][5] = list_personnel_sportif.get(i).getAdresse();
			data[i][6] = list_personnel_sportif.get(i).getTypeSport();
			data[i][7] = "Sportif";
		}
		for(int i=list_personnel_sportif.size(); i<list_personnel_administratif.size()+list_personnel_sportif.size(); i++) {
			data[i][0] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getIdPersonne();
			data[i][1] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getNumEmploye();
			data[i][2] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getNom();
			data[i][3] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getPrenom();
			data[i][4] = Utiles.dateToString(list_personnel_administratif.get(i-list_personnel_sportif.size()).getDate());
			data[i][5] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getAdresse();
			data[i][6] = list_personnel_administratif.get(i-list_personnel_sportif.size()).getAffectation();
			data[i][7] = "Administratif";
		}
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		table.setModel(dtm);
		table.update(getGraphics());
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.getColumnModel().getColumn(1).setMaxWidth(20);
	}
	
	public void updateTableSansRechargerData() {
		DefaultTableModel dtm = new DefaultTableModel(data, columnNames);
		table.setModel(dtm);
		table.update(getGraphics());
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
				if(boutton.getText().equals("Home")) {
					main_window.changerTab("Home", new PremiereVue(main_window));
				}
				else if(boutton.getText().equals("Ajouter")) {
					ajouterModifier(0, "", "", "", "", "");
				}
				else if(boutton.getText().equals("Modifier")) {
					ajouterModifier(-1, data[ligne_selected][2].toString(), data[ligne_selected][3].toString(), Utiles.stringToDate(data[ligne_selected][4].toString()).toString(), data[ligne_selected][5].toString(),  data[ligne_selected][6].toString());
				}
				else if(boutton.getText().equals("Supprimer")) {
					supprimerPersonnel(ligne_selected);
				}
				
				else if(boutton.getText().equals("Valider")) {
					String date = comboBox_annee.getSelectedItem().toString()+"-"+(comboBox_mois.getSelectedIndex()+1)+"-"+comboBox_jour.getSelectedIndex()+1;
					if(fenetre_ajouter_modifier.getTitle().equals("Ajouter")) {
						ajouterPersonnel(textField_nom.getText(), textField_prenom.getText(), date, textField_adresse.getText(), textField_affectation.getText());
					}
					else if(fenetre_ajouter_modifier.getTitle().equals("Modifier")) {
						mofidierPersonnel(ligne_selected, textField_nom.getText(), textField_prenom.getText(), date, textField_adresse.getText(), textField_affectation.getText());
					}
				}
				else if(boutton.getText().equals("Annuler")) {
					fenetre_ajouter_modifier.setVisible(false);
				}
			}
		}
		if(table.getSelectedRow()>=0) {
			ligne_selected = table.getSelectedRow();
			boutton_mod_user.setEnabled(true);
			boutton_del_user.setEnabled(true);
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
	public void ajouterPersonnelSportif(PersonnelSportif personnelSportif) {
		updateTable();
	}

	@Override
	public void supprimerPersonnelSportif(PersonnelSportif personnelSportif) {
		updateTable();
	}

	@Override
	public void ajouterPersonnelAdministratif(PersonnelAdministratif personnelAdministratif) {
		updateTable();
	}

	@Override
	public void supprimerPersonnelAdministratif(PersonnelAdministratif personnelAdministratif) {
		updateTable();
	}

	@Override
	public void ajouterPersonne(Personne personne) {
		try {
			if(comboBox_genre.getSelectedItem().toString().equals("Personnel Sportif"))
				personnels_sportif.ajouterPersonnelSportif(personne.getIdPersonne(), textField_affectation.getText());
			else
				personnels_administratif.ajouterPersonnelAdministratif(personne.getIdPersonne(), textField_affectation.getText());
		} 
		catch (Exception e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
	}

	@Override
	public void supprimerPersonne(Personne personne) {
	}

}
