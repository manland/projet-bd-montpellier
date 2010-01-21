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

import Coeur.Mat.MaterielVente;
import Coeur.Mat.MaterielsVentes;
import Coeur.Mat.MaterielsVentesListener;
import Coeur.Mat.TypeMaterielAchete;
import Coeur.Mat.Vente;
import Coeur.Mat.Ventes;
import Coeur.Mat.VentesListener;
import Coeur.Personne.Adherent;
import Coeur.Personne.Personne;
import Coeur.Personne.Personnes;

public class VueVente extends JPanel implements MouseListener, MaterielsVentesListener, VentesListener, FocusListener, ActionListener {

	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	private MaterielsVentes materiels_ventes;
	private Ventes ventes;
	private Object [][] list_ventes;
	private Object [][] list_matos;
	private JPanel panel_vente;
	private JButton boutton_vente_home;
	private JButton boutton_vente_add;
	private JButton boutton_vente_modifier;
	private JButton boutton_vente_supprimer;
	private JButton boutton_vente_facture;
	private JPanel panel_matos;
	private JButton boutton_matos_home;
	private JButton boutton_matos_add;
	private JButton boutton_matos_modifier;
	private JButton boutton_matos_effacer;
	
	private JSplitPane splitPane;
	
	private JDialog fenetre_ajouter_modifier_vente;
	private JPanel interieur_fenetre_vente;
	private Vector<JComboBox> vector_type;
	private Vector<JTextField> vector_quantite;
	private Vector<JLabel> vector_prix;
	private JComboBox comboBox_vente_personne;
	private JLabel prix_total;
	private JComboBox comboBox_reduction;
	
	private JDialog fenetre_ajouter_modifier_matos;
	private JPanel interieur_fenetre_matos;
	private JTextField textField_matos_type;
	private JTextField textField_matos_desc;
	private JTextField textField_matos_quantite;
	private JTextField textField_matos_prix;
	
	private JTable table_vente;
	private JTable table_matos;
	
	
	public VueVente(MainWindow main_window) {
		super(new BorderLayout());
		this.main_window = main_window;
		try {
			materiels_ventes = new MaterielsVentes();
			ventes = new Ventes();
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		catch (ClassNotFoundException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		materiels_ventes.addMaterielsVentesListener(this);
		ventes.addVentesListener(this);
		
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		buildPanelVente();
		buildPanelMatos();
		add(splitPane);
	}
	
	public void buildPanelVente() {
		try{splitPane.remove(panel_vente);}catch(NullPointerException ee){}
		panel_vente = new JPanel();
		panel_vente.setMinimumSize(new Dimension(50, 1));
		panel_vente.setBorder(BorderFactory.createTitledBorder("Ventes"));

		BoxLayout layout = new BoxLayout(panel_vente, BoxLayout.PAGE_AXIS);
		panel_vente.setLayout(layout);
		String [] columnsNames = {"N°", "Date", "Personne", "Prix"};
		ArrayList<Vente> list_ventes_temp = ventes.getVentes();
		list_ventes = new String[list_ventes_temp.size()][4];
		for(int i=0; i<list_ventes_temp.size(); i++) {
			Personne personne = null;
			try {
				personne = new Personne(list_ventes_temp.get(i).getIdPersonne());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			list_ventes[i][0] = ""+list_ventes_temp.get(i).getNumVente();
			list_ventes[i][1] = Utiles.dateToString(list_ventes_temp.get(i).getDateVente());
			list_ventes[i][2] = personne.getIdPersonne()+" : "+personne.getNom()+" "+personne.getPrenom();
			list_ventes[i][3] = ""+list_ventes_temp.get(i).getPrixPaye();
		}
		table_vente = new JTable(list_ventes, columnsNames);
		table_vente.addFocusListener(this);
		table_vente.addMouseListener(this);
		JScrollPane scroll_pane_vente = new JScrollPane(table_vente);
		table_vente.setFillsViewportHeight(true);
		panel_vente.add(scroll_pane_vente);
		JPanel panel_vente_fin = buildPanelVenteBouttons();
		panel_vente.add(panel_vente_fin);
		
		splitPane.setTopComponent(panel_vente);
	}
	
	public JPanel buildPanelVenteBouttons() {
		JPanel panel_vente_bouttons = new JPanel(new GridLayout(1, 0));
		boutton_vente_home = new JButton("Home");
		boutton_vente_home.setHorizontalTextPosition(0);
		boutton_vente_home.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_home.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Home.png"));
		boutton_vente_home.addMouseListener(this);
		panel_vente_bouttons.add(boutton_vente_home);
		boutton_vente_add = new JButton("Ajouter");
		boutton_vente_add.setHorizontalTextPosition(0);
		boutton_vente_add.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_add.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/ajouter.png"));
		boutton_vente_add.addMouseListener(this);
		panel_vente_bouttons.add(boutton_vente_add);
		boutton_vente_modifier = new JButton("Modifier");
		boutton_vente_modifier.setHorizontalTextPosition(0);
		boutton_vente_modifier.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_modifier.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/modifier.png"));
		boutton_vente_modifier.setEnabled(false);
		boutton_vente_modifier.addMouseListener(this);
		panel_vente_bouttons.add(boutton_vente_modifier);
		boutton_vente_supprimer = new JButton("Supprimer");
		boutton_vente_supprimer.setHorizontalTextPosition(0);
		boutton_vente_supprimer.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_supprimer.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/supprimer.png"));
		boutton_vente_supprimer.setEnabled(false);
		boutton_vente_supprimer.addMouseListener(this);
		panel_vente_bouttons.add(boutton_vente_supprimer);
		boutton_vente_facture = new JButton("Facture");
		boutton_vente_facture.setHorizontalTextPosition(0);
		boutton_vente_facture.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_vente_facture.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/facture.png"));
		boutton_vente_facture.setEnabled(false);
		boutton_vente_facture.addMouseListener(this);
		panel_vente_bouttons.add(boutton_vente_facture);
		return panel_vente_bouttons;		
	}
	
	public void buildPanelMatos() {
		try{splitPane.remove(panel_matos);}catch(NullPointerException ee){}
		panel_matos = new JPanel();
		panel_matos.setMinimumSize(new Dimension(50, 1));
		panel_matos.setBorder(BorderFactory.createTitledBorder("Matériels"));

		BoxLayout layout = new BoxLayout(panel_matos, BoxLayout.PAGE_AXIS);
		panel_matos.setLayout(layout);
		String [] columnsNames = {"Type materiel", "Description", "Quantité", "Prix"};
		ArrayList<MaterielVente> list_matos_temp = materiels_ventes.getMaterielsVentes();
		if(list_matos_temp.size() == 0) {//Pour ne pas ajouter de vente s'il n'y a pas de matos
			boutton_vente_add.setEnabled(false);
		}
		else {
			boutton_vente_add.setEnabled(true);
		}
		list_matos = new String[list_matos_temp.size()][4];
		for(int i=0; i<list_matos_temp.size(); i++) {
			list_matos[i][0] = list_matos_temp.get(i).getTypeMateriel();
			list_matos[i][1] = list_matos_temp.get(i).getDescription();
			list_matos[i][2] = ""+list_matos_temp.get(i).getQuantite();
			list_matos[i][3] = ""+list_matos_temp.get(i).getPrixBase();
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
		boutton_matos_add.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/ajouter.png"));
		boutton_matos_add.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_add);
		boutton_matos_modifier = new JButton("Modifier");
		boutton_matos_modifier.setHorizontalTextPosition(0);
		boutton_matos_modifier.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_modifier.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/modifier.png"));
		boutton_matos_modifier.setEnabled(false);
		boutton_matos_modifier.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_modifier);
		boutton_matos_effacer = new JButton("Effacer");
		boutton_matos_effacer.setHorizontalTextPosition(0);
		boutton_matos_effacer.setVerticalTextPosition(SwingConstants.BOTTOM);
		boutton_matos_effacer.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/vente/supprimer.png"));
		boutton_matos_effacer.setEnabled(false);
		boutton_matos_effacer.addMouseListener(this);
		panel_matos_bouttons.add(boutton_matos_effacer);
		return panel_matos_bouttons;		
	}
	
	public void dialogAjouterModifierVente(String date, int id_personne, String prix, ArrayList<TypeMaterielAchete> materiel) {
		fenetre_ajouter_modifier_vente = new JDialog();
		if(prix.equals(""))
			fenetre_ajouter_modifier_vente.setTitle("Ajouter");
		else
			fenetre_ajouter_modifier_vente.setTitle("Modifier");
		fenetre_ajouter_modifier_vente.setMinimumSize(new Dimension(300, 130));
		interieur_fenetre_vente = new JPanel();
		BoxLayout layout = new BoxLayout(interieur_fenetre_vente, BoxLayout.PAGE_AXIS);
		interieur_fenetre_vente.setLayout(layout);
		fenetre_ajouter_modifier_vente.add(interieur_fenetre_vente);
		fenetre_ajouter_modifier_vente.setVisible(true);
		fenetre_ajouter_modifier_vente.pack();
		
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
		comboBox_vente_personne = new JComboBox(nom_personnes);
		if(materiel != null) {//si c'est une modification
			Personne p = null;
			try {
				p = new Personne(id_personne);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			comboBox_vente_personne.setSelectedItem(""+id_personne+" : "+p.getNom()+" "+p.getPrenom());
		}
		comboBox_vente_personne.addActionListener(this);
		panel_personne.add(comboBox_vente_personne);
		interieur_fenetre_vente.add(panel_personne);
		//Type
		vector_type = new Vector<JComboBox>();
		vector_quantite = new Vector<JTextField>();
		vector_prix = new Vector<JLabel>();
		if(materiel == null) {
			interieur_fenetre_vente.add(buildNouveauArticle("", "Quantite"));
		}
		else {
			for(int i=0; i<materiel.size(); i++) {
				interieur_fenetre_vente.add(buildNouveauArticle(materiel.get(i).getTypeMateriel(), ""+materiel.get(i).getQuantite()));
				updateLabelPrix(i);
				if(i!=0) {
					fenetre_ajouter_modifier_vente.setMinimumSize(new Dimension(fenetre_ajouter_modifier_vente.getMinimumSize().width, fenetre_ajouter_modifier_vente.getMinimumSize().height+30));
				}
			}
		}
		//Total
		interieur_fenetre_vente.add(new JSeparator(SwingConstants.HORIZONTAL));
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
		interieur_fenetre_vente.add(panel_total);
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
		interieur_fenetre_vente.add(panel_valider_annuler);
		
		updatePrix();
		verificationAdherent();
	}
	
	public JPanel buildNouveauArticle(String type, String quantite) {
		ArrayList<MaterielVente> matos_vente_temp = materiels_ventes.getMaterielsVentes();
		String [] matos_vente = new String [matos_vente_temp.size()];
		for(int i=0; i<matos_vente_temp.size(); i++) {
			matos_vente[i] = matos_vente_temp.get(i).getTypeMateriel();
		}
		JPanel panel = new JPanel();
		BoxLayout layout2 = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(layout2);
		JLabel label_type = new JLabel("Article : ");
		panel.add(label_type);
		JComboBox comboBox_vente_type = new JComboBox(matos_vente);
		vector_type.add(comboBox_vente_type);
		comboBox_vente_type.setSelectedItem(type);
		panel.add(comboBox_vente_type);
		//Quantité
		JLabel label_quantite = new JLabel(" x ");
		panel.add(label_quantite);
		JTextField textField_vente_quantite = new JTextField(quantite, 20);
		vector_quantite.add(textField_vente_quantite);
		textField_vente_quantite.addActionListener(this);
		panel.add(textField_vente_quantite);
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
		int quantite = 0;
		try {
			quantite = Integer.parseInt(vector_quantite.get(num_article).getText());
		}
		catch(NumberFormatException e) {
			return;
		}
		boolean trouve = false;
		ArrayList<MaterielVente> list_matos_temp = materiels_ventes.getMaterielsVentes();
		for(int i=0; i<list_matos_temp.size() && !trouve; i++) {
			if(list_matos_temp.get(i).getTypeMateriel().equals(vector_type.get(num_article).getSelectedItem().toString())) {
				trouve = true;
				vector_prix.get(num_article).setText(""+(quantite * list_matos_temp.get(i).getPrixBase()));
			}
		}
	}
	
	public void ajouterVente(ArrayList<TypeMaterielAchete> materiel, Date date, int id_personne, int prix) {
		try {
			ventes.ajouterVente(materiel, date, id_personne, prix);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_vente.setVisible(false);
	}
	
	public void modifierVente(int num, ArrayList<TypeMaterielAchete> materiel, String date, int id_personne, int prix) {
		try {
			ventes.modifierVente(num, materiel, Utiles.stringToDate(date), id_personne, prix);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_vente.setVisible(false);
	}
	
	public void supprimerVente(int num) {
		try {
			ventes.supprimerVente(num);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
	}
	
	public void dialogAjouterModifierMatos(String type, String desc, String quantite, String prix) {
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
		//Description
		JPanel panel_desc = new JPanel();
		BoxLayout layout3 = new BoxLayout(panel_desc, BoxLayout.LINE_AXIS);
		panel_desc.setLayout(layout3);
		JLabel label_desc = new JLabel("Description : ");
		panel_desc.add(label_desc);
		textField_matos_desc = new JTextField(desc, 20);
		panel_desc.add(textField_matos_desc);
		interieur_fenetre_matos.add(panel_desc);
		//Quantité
		JPanel panel_quantite = new JPanel();
		BoxLayout layout4 = new BoxLayout(panel_quantite, BoxLayout.LINE_AXIS);
		panel_quantite.setLayout(layout4);
		JLabel label_quantite = new JLabel("Quantité : ");
		panel_quantite.add(label_quantite);
		textField_matos_quantite = new JTextField(quantite, 20);
		panel_quantite.add(textField_matos_quantite);
		interieur_fenetre_matos.add(panel_quantite);
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
	
	public void ajouterMatos(String type, String desc, int quantite, int prix) {
		try {
			materiels_ventes.ajouterMaterielVente(type, desc, quantite, prix);
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_matos.setVisible(false);
	}
	
	public void modifierMatos(String type, String desc, int quantite, int prix) {
		try {
			materiels_ventes.modifierMaterielVente(type, desc, quantite, prix);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
		fenetre_ajouter_modifier_matos.setVisible(false);
	}
	
	public void supprimerMatos(String type) {
		try {
			materiels_ventes.supprimerMaterielVente(type);
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
			return;
		}
	}
	
	public void updateMatos() {
		try {
			materiels_ventes = new MaterielsVentes();
		} catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		materiels_ventes.addMaterielsVentesListener(this);
		buildPanelMatos();
		splitPane.setDividerLocation(0.1);
	}
	
	public void updateVentes() {
		try {
			ventes = new Ventes();
		} 
		catch (SQLException e) {
			Utiles.erreur(e.getLocalizedMessage());
		} 
		catch (ClassNotFoundException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		ventes.addVentesListener(this);
		buildPanelVente();
		splitPane.setDividerLocation(0.9);
	}
	
	public void verificationAdherent() {
		try {
			new Adherent(Integer.parseInt(comboBox_vente_personne.getSelectedItem().toString().substring(0, comboBox_vente_personne.getSelectedItem().toString().indexOf(" "))));
		} catch (SQLException e1) {
			comboBox_reduction.setEnabled(false);
			return;
		}
		comboBox_reduction.setEnabled(true);
		updatePrix();
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
			if(table == table_vente) {
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
				dialogAjouterModifierVente("", 0, "", null);
			}
			else if(boutton.equals(boutton_vente_modifier)) {
				int ligne = table_vente.getSelectedRow();
				dialogAjouterModifierVente(list_ventes[ligne][1].toString(), Integer.parseInt(list_ventes[ligne][2].toString().substring(0, list_ventes[ligne][2].toString().indexOf(" "))), list_ventes[ligne][3].toString(), ventes.getVentes().get(ligne).getMateriel());
			}
			else if(boutton == boutton_vente_supprimer) {
				int ligne = table_vente.getSelectedRow();
				supprimerVente(Integer.parseInt(list_ventes[ligne][0].toString()));
			}
			else if(boutton == boutton_matos_add) {
				dialogAjouterModifierMatos("", "", "", "");
			}
			else if(boutton == boutton_matos_modifier) {
				int ligne = table_matos.getSelectedRow();
				dialogAjouterModifierMatos(list_matos[ligne][0].toString(), list_matos[ligne][1].toString(), list_matos[ligne][2].toString(), list_matos[ligne][3].toString());
			}
			else if(boutton == boutton_matos_effacer) {
				int ligne = table_matos.getSelectedRow();
				supprimerMatos(list_matos[ligne][0].toString());
			}
			else if(boutton.getText().equals("Valider")) {
				if(fenetre_ajouter_modifier_matos != null) {
					if(fenetre_ajouter_modifier_matos.isVisible() && fenetre_ajouter_modifier_matos.getTitle().equals("Ajouter")) {
						ajouterMatos(textField_matos_type.getText(), textField_matos_desc.getText(), Integer.parseInt(textField_matos_quantite.getText()), Integer.parseInt(textField_matos_prix.getText()));
					}
					else if(fenetre_ajouter_modifier_matos.isVisible() && fenetre_ajouter_modifier_matos.getTitle().equals("Modifier")) {
						modifierMatos(textField_matos_type.getText(), textField_matos_desc.getText(), Integer.parseInt(textField_matos_quantite.getText()), Integer.parseInt(textField_matos_prix.getText()));
					}
				}
				if(fenetre_ajouter_modifier_vente != null) {
					if(fenetre_ajouter_modifier_vente.isVisible() && fenetre_ajouter_modifier_vente.getTitle().equals("Ajouter")) {
						int id_personne = Integer.parseInt(comboBox_vente_personne.getSelectedItem().toString().substring(0, comboBox_vente_personne.getSelectedItem().toString().indexOf(" ")));
						ArrayList<TypeMaterielAchete> materiel = new ArrayList<TypeMaterielAchete>();
						int prix = Integer.parseInt(prix_total.getText().substring(0, prix_total.getText().indexOf(" ")));
						for(int i=0; i<vector_prix.size(); i++) {
							if(!vector_quantite.get(i).getText().equals("Quantite")) {
								TypeMaterielAchete type_materiel_achete = new TypeMaterielAchete(vector_type.get(i).getSelectedItem().toString(), Integer.parseInt(vector_quantite.get(i).getText()), Integer.parseInt(vector_prix.get(i).getText()));
								materiel.add(type_materiel_achete);
							}
						}
						Calendar cal = Calendar.getInstance();
						ajouterVente(materiel, new Date(cal.getTimeInMillis()), id_personne, prix);
					}
					else if(fenetre_ajouter_modifier_vente.isVisible() && fenetre_ajouter_modifier_vente.getTitle().equals("Modifier")) {
						int ligne = table_vente.getSelectedRow();
						int id_personne = Integer.parseInt(comboBox_vente_personne.getSelectedItem().toString().substring(0, comboBox_vente_personne.getSelectedItem().toString().indexOf(" ")));
						ArrayList<TypeMaterielAchete> materiel = new ArrayList<TypeMaterielAchete>();
						int prix = Integer.parseInt(prix_total.getText().substring(0, prix_total.getText().indexOf(" ")));
						for(int i=0; i<vector_prix.size(); i++) {
							if(!vector_quantite.get(i).getText().equals("Quantite")) {
								TypeMaterielAchete type_materiel_achete = new TypeMaterielAchete(vector_type.get(i).getSelectedItem().toString(), Integer.parseInt(vector_quantite.get(i).getText()), Integer.parseInt(vector_prix.get(i).getText()));
								materiel.add(type_materiel_achete);
							}
						}
						modifierVente(Integer.parseInt(list_ventes[ligne][0].toString()), materiel, list_ventes[ligne][1].toString(), id_personne, prix);
					}
				}
			}
			else if(boutton.getText().equals("Annuler")) {
				if(fenetre_ajouter_modifier_matos != null) {
					if(fenetre_ajouter_modifier_matos.isVisible()) {
						fenetre_ajouter_modifier_matos.setVisible(false);
					}
				}
				if(fenetre_ajouter_modifier_vente != null) {
					if(fenetre_ajouter_modifier_vente.isVisible()) {
						fenetre_ajouter_modifier_vente.setVisible(false);
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
	public void ajouterMaterielVente(MaterielVente materielVente) {
		updateMatos();
	}

	@Override
	public void supprimerMaterielVente(MaterielVente materielVente) {
		updateMatos();
	}

	@Override
	public void ajouterVente(Vente vente) {
		ArrayList<TypeMaterielAchete> matos = vente.getMateriel();
		for(int i=0; i<matos.size(); i++) {
			MaterielVente mat = null;
			try {
				mat = new MaterielVente(matos.get(i).getTypeMateriel());
			} 
			catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
			try {
				materiels_ventes.modifierMaterielVente(matos.get(i).getTypeMateriel(), mat.getDescription(), mat.getQuantite()-matos.get(i).getQuantite(), mat.getPrixBase());
			} 
			catch (SQLException e) {
				Utiles.erreur(e.getLocalizedMessage());
			}
		}
		updateVentes();
	}

	@Override
	public void supprimerVente(Vente vente) {
		updateVentes();
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
			else if(table == table_vente) {
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
//			else if(table == table_vente) {
//				boutton_vente_facture.setEnabled(false);
//				boutton_vente_modifier.setEnabled(false);
//				boutton_vente_supprimer.setEnabled(false);
//			}
//		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextField textField = null;
		try {
			textField = (JTextField)event.getSource();
		}
		catch(ClassCastException e) {
			JComboBox comboBox = null;
			try {
				comboBox = (JComboBox)event.getSource();
			}
			catch(ClassCastException ee) {
				return;
			}
			if(comboBox == comboBox_vente_personne) {
				verificationAdherent();
			}
			else if(comboBox == comboBox_reduction) {
				updatePrix();
			}
			return;
		}
		int num_article = -1;
		boolean trouve = false;
		for(int i=0; i<vector_quantite.size() && !trouve; i++) {
			if(vector_quantite.get(i) == textField) {
				trouve = true;
				num_article = i;
			}
		}
		updateLabelPrix(num_article);
		if(trouve && num_article+1 == vector_type.size()) {
			interieur_fenetre_vente.add(buildNouveauArticle("", "Quantite"), vector_type.size());
			fenetre_ajouter_modifier_vente.setMinimumSize(new Dimension(fenetre_ajouter_modifier_vente.getMinimumSize().width, fenetre_ajouter_modifier_vente.getMinimumSize().height+30));
		}
		updatePrix();
	}
}
