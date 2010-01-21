package Graphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Coeur.SQL;

public class VueOptions extends JPanel implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	
	private JPanel panel_skin;
	private JComboBox comboBox_skin;
	private JPanel panel_image;
	
	private JPanel panel_langue;
	private JComboBox comboBox_langue;
	private String langue = Options.getLangue();
	private boolean premier_passage;
	
	private JPanel panel_client_internet;
	private JTextField textField_explorateur_internet;
	private JTextField textField_url_client;
	
	private JPanel panel_backup;
	private Process p;
	private JComboBox comboBox_import_bd;
	private JTextField textField_oracle_home;
	private JTextField textField_oracle_sid;
	private JPanel panel_grant;
	private Vector<JTextField> vector_nom;
    private Vector<Vector<JCheckBox>> vector_droits;
	private JScrollPane scrollPane_grant;

	public VueOptions(MainWindow main_window) {
		this.main_window = main_window;
		BoxLayout layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
//		GridLayout layout = new GridLayout(0, 1);
		setLayout(layout);
		premier_passage = true;
		
		buildPanelSkin();
		buildPanelLangue();
		buildPanelClientInternet();
		if(Options.getProprietaire().equals(SQL.getUtilisateur())) {
			buildBd();
		}
		
		JButton boutton_valider = new JButton(Traductions.vueOptions(Options.getLangue(), "constructor_valider"));
		boutton_valider.setIcon(Utiles.createImageIcon(Utiles.getTheme()+"/ok.png"));
		boutton_valider.addMouseListener(this);
		add(boutton_valider);
	}

	public void buildPanelSkin() {
		try{remove(panel_skin);}catch(NullPointerException e){}
		panel_skin = new JPanel();
		panel_skin.setBorder(BorderFactory.createTitledBorder(Traductions.vueOptions(langue, "buildpanelskin_titre")));
		BoxLayout layout = new BoxLayout(panel_skin, BoxLayout.PAGE_AXIS);
		panel_skin.setLayout(layout);
		
		File file = new File("bin/Skins/");
		String [] list_skin = null;
	    if (file.isDirectory()) {
	      File [] files = file.listFiles();
	      list_skin = new String[files.length];
	      for (int i=0; i<files.length; i++) {
	    	  list_skin[i] = files[i].getName();
	      }
	      comboBox_skin = new JComboBox(list_skin);
	      comboBox_skin.setSelectedItem(Utiles.getQueTheme());
	      comboBox_skin.setMaximumSize(new Dimension(300, 30));
	      comboBox_skin.addActionListener(this);
	      panel_skin.add(comboBox_skin);
	      
	      buildPanelImages();
	      
	      add(panel_skin, 0);
	      validate();
	    }
	}
	
	public void buildPanelImages() {
		try {panel_skin.remove(panel_image);}catch(NullPointerException e) {}
		panel_image = new JPanel();
		BoxLayout layout = new BoxLayout(panel_image, BoxLayout.LINE_AXIS);
		panel_image.setLayout(layout);
		
		JLabel image_personnes = new JLabel();
		image_personnes.setIcon(Utiles.createImageIcon(Utiles.getUrlTheme()+comboBox_skin.getSelectedItem().toString()+"/Images/Personnes/Personnes.png"));
		panel_image.add(image_personnes);
		
		JLabel image_batiments = new JLabel();
		image_batiments.setIcon(Utiles.createImageIcon(Utiles.getUrlTheme()+comboBox_skin.getSelectedItem().toString()+"/Images/Batiments/Batiments.png"));
		panel_image.add(image_batiments);
		
		JLabel image_euros = new JLabel();
		image_euros.setIcon(Utiles.createImageIcon(Utiles.getUrlTheme()+comboBox_skin.getSelectedItem().toString()+"/Images/Euros/Euros.png"));
		panel_image.add(image_euros);
		
		JLabel image_client_internet = new JLabel();
		image_client_internet.setIcon(Utiles.createImageIcon(Utiles.getUrlTheme()+comboBox_skin.getSelectedItem().toString()+"/Images/Client/Client Internet.png"));
		panel_image.add(image_client_internet);
		
		JLabel image_options = new JLabel();
		image_options.setIcon(Utiles.createImageIcon(Utiles.getUrlTheme()+comboBox_skin.getSelectedItem().toString()+"/Images/Options/Options.png"));
		panel_image.add(image_options);
		
		panel_skin.add(panel_image);
		panel_skin.validate();
		validate();
	}
	
	public void buildPanelLangue() {
		try{remove(panel_langue);}catch(NullPointerException e){}
		panel_langue = new JPanel();
		panel_langue.setBorder(BorderFactory.createTitledBorder(Traductions.vueOptions(langue, "buildpanellangue_titre")));
		BoxLayout layout = new BoxLayout(panel_langue, BoxLayout.LINE_AXIS);
		panel_langue.setLayout(layout);
		
		JLabel label = new JLabel("Langue : ");
		panel_langue.add(label);
		String [] list_langue = Traductions.vueOptionsTab();
		comboBox_langue = new JComboBox(list_langue);
		comboBox_langue.addActionListener(this);
		if(langue.equals("fr"))
			comboBox_langue.setSelectedIndex(0);
		else if(langue.equals("en"))
			comboBox_langue.setSelectedIndex(1);
		else if(langue.equals("it"))
			comboBox_langue.setSelectedIndex(2);
		else if(langue.equals("al"))
			comboBox_langue.setSelectedIndex(3);
		comboBox_langue.setMaximumSize(new Dimension(300, 30));
		comboBox_langue.addActionListener(this);
		panel_langue.add(comboBox_langue);
		add(panel_langue, 1);
		validate();
	}
	
	public void buildPanelClientInternet() {
		try{remove(panel_client_internet);}catch(NullPointerException e){}
		panel_client_internet = new JPanel();
		panel_client_internet.setBorder(BorderFactory.createTitledBorder(Traductions.vueOptions(langue, "buildpanelclientinternet_titre")));
		BoxLayout layout = new BoxLayout(panel_client_internet, BoxLayout.PAGE_AXIS);
		panel_client_internet.setLayout(layout);
		
		JPanel sspanel = new JPanel();
		BoxLayout sslayout = new BoxLayout(sspanel, BoxLayout.LINE_AXIS);
		sspanel.setLayout(sslayout);
		JLabel label_explorateur_internet = new JLabel(Traductions.vueOptions(langue, "buildpanelclientinternet_explorateurinternet")+" : ");
		sspanel.add(label_explorateur_internet);
		textField_explorateur_internet = new JTextField(Options.getExplorateurInternet(), 30);
		textField_explorateur_internet.setMaximumSize(new Dimension(300, 30));
		sspanel.add(textField_explorateur_internet);
		panel_client_internet.add(sspanel);
		
		sspanel = new JPanel();
		sslayout = new BoxLayout(sspanel, BoxLayout.LINE_AXIS);
		sspanel.setLayout(sslayout);
		JLabel label_url_client = new JLabel(Traductions.vueOptions(langue, "buildpanelclientinternet_url")+" : ");
		sspanel.add(label_url_client);
		textField_url_client = new JTextField(Options.getUrlClientInternet(), 30);
		textField_url_client.setMaximumSize(new Dimension(300, 30));
		sspanel.add(textField_url_client);
		panel_client_internet.add(sspanel);
		
		add(panel_client_internet, 2);
		validate();
	}
	
	public void buildBd() {
		try{remove(panel_backup);}catch(NullPointerException e){}
		panel_backup = new JPanel();
		panel_backup.setBorder(BorderFactory.createTitledBorder(Traductions.vueOptions(langue, "buildpanelbd_titre")));
		BoxLayout layout = new BoxLayout(panel_backup, BoxLayout.PAGE_AXIS);
		panel_backup.setLayout(layout);
		
		JPanel panel_oracle_home = new JPanel();
		BoxLayout layout_panel_oracle_home = new BoxLayout(panel_oracle_home, BoxLayout.LINE_AXIS);
		panel_oracle_home.setLayout(layout_panel_oracle_home);
		JLabel label_oracle_home = new JLabel("URL oracle (sans / à la fin) : ");
		panel_oracle_home.add(label_oracle_home);
		textField_oracle_home = new JTextField(Options.getURLOracle(), 20);
		textField_oracle_home.setMaximumSize(new Dimension(300, 30));
		panel_oracle_home.add(textField_oracle_home);
		panel_backup.add(panel_oracle_home);
		
		JPanel panel_oracle_sid = new JPanel();
		BoxLayout layout_panel_oracle_sid = new BoxLayout(panel_oracle_sid, BoxLayout.LINE_AXIS);
		panel_oracle_sid.setLayout(layout_panel_oracle_sid);
		JLabel label_oracle_sid = new JLabel("SID oracle : ");
		panel_oracle_sid.add(label_oracle_sid);
		textField_oracle_sid = new JTextField(Options.getSIDOracle(), 20);
		textField_oracle_sid.setMaximumSize(new Dimension(300, 30));
		panel_oracle_sid.add(textField_oracle_sid);
		panel_backup.add(panel_oracle_sid);
		
		JPanel panel_sauver_restaurer = new JPanel();
		BoxLayout layout_panel_sauver_restaurer = new BoxLayout(panel_sauver_restaurer, BoxLayout.LINE_AXIS);
		panel_sauver_restaurer.setLayout(layout_panel_sauver_restaurer);
		JButton boutton = new JButton(Traductions.vueOptions(langue, "buildpanelbd_boutton_enregistrer"));
		boutton.addMouseListener(this);
		panel_sauver_restaurer.add(boutton);
		
		JLabel label = new JLabel(" - ");
		panel_sauver_restaurer.add(label);
		
		File file = new File("bin/BackupBD/");
		String [] list_backup = null;
	    if (file.isDirectory()) {
	      File [] files = file.listFiles();
	      list_backup = new String[files.length/2];
	      int cpt = 0;
	      for (int i=0; i<files.length; i++) {
	    	  if(files[i].getName().contains("dmp")) {
	    		  list_backup[cpt] = files[i].getName();
	    		  cpt++;
	    	  }
	      }
	    }
	    if(list_backup == null) {
	    	Options.getExportFichier();
	    	list_backup = new String[1];
	    	list_backup[0] = "";
	    }
	    comboBox_import_bd = new JComboBox(list_backup);
	    comboBox_import_bd.setSelectedItem(Utiles.getQueTheme());
	    comboBox_import_bd.setMaximumSize(new Dimension(300, 30));
	    panel_sauver_restaurer.add(comboBox_import_bd);
	    
	    JButton boutton_importer = new JButton(Traductions.vueOptions(langue, "buildpanelbd_boutton_importer"));
	    boutton_importer.addMouseListener(this);
	    panel_sauver_restaurer.add(boutton_importer);
	    
	    panel_backup.add(panel_sauver_restaurer);
	    
	    vector_nom = new Vector<JTextField>();
		vector_droits = new Vector<Vector<JCheckBox>>();
	    panel_grant = new JPanel();
	    scrollPane_grant = new JScrollPane(panel_grant);
		BoxLayout layout_panel_grant = new BoxLayout(panel_grant, BoxLayout.PAGE_AXIS);
		panel_grant.setLayout(layout_panel_grant);
		panel_grant.add(builPanelGrant());
		
		panel_backup.add(scrollPane_grant);
		
		JButton boutton_valider_droits = new JButton("Valider Droits");
		boutton_valider_droits.addMouseListener(this);
		panel_backup.add(boutton_valider_droits);
		
		add(panel_backup, 3);
		validate();
	}
	
	public JPanel builPanelGrant() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(""));
		BoxLayout layout = new BoxLayout(panel, BoxLayout.LINE_AXIS);
		panel.setLayout(layout);
		
		JLabel label_nom = new JLabel("Nom : ");
		panel.add(label_nom);
		JTextField textFiel_nom = new JTextField("", 20);
		textFiel_nom.setMaximumSize(new Dimension(300, 30));
		vector_nom.add(textFiel_nom);
		panel.add(textFiel_nom);
		
		Vector<JCheckBox> vector_checkBox = new Vector<JCheckBox>(7);
		JPanel panel_droits = new JPanel();
		BoxLayout layout_panel_droits = new BoxLayout(panel_droits, BoxLayout.PAGE_AXIS);
		panel_droits.setLayout(layout_panel_droits);
		JCheckBox checkBox_select = new JCheckBox("Select");
		panel_droits.add(checkBox_select);
		vector_checkBox.add(checkBox_select);
		JCheckBox checkBox_insert = new JCheckBox("Insert");
		panel_droits.add(checkBox_insert);
		vector_checkBox.add(checkBox_insert);
		JCheckBox checkBox_update = new JCheckBox("Update");
		panel_droits.add(checkBox_update);
		vector_checkBox.add(checkBox_update);
		JCheckBox checkBox_delete = new JCheckBox("Delete");
		panel_droits.add(checkBox_delete);
		vector_checkBox.add(checkBox_delete);
		JCheckBox checkBox_references = new JCheckBox("References");
		panel_droits.add(checkBox_references);
		vector_checkBox.add(checkBox_references);
		JCheckBox checkBox_alter = new JCheckBox("Alter");
		panel_droits.add(checkBox_alter);
		vector_checkBox.add(checkBox_alter);
		JCheckBox checkBox_index = new JCheckBox("Index");
		panel_droits.add(checkBox_index);
		vector_checkBox.add(checkBox_index);
		vector_droits.add(vector_checkBox);
		
		panel.add(panel_droits);
		
		JButton boutton_plus = new JButton("+");
		boutton_plus.addMouseListener(this);
		panel.add(boutton_plus);
		
		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent action) {
		JComboBox comboBox = null;
		try {
			comboBox = (JComboBox)action.getSource();
		}
		catch(ClassCastException e) {
			return;
		}
		if(comboBox == comboBox_skin) {
			buildPanelImages();
		}
		else if(comboBox == comboBox_langue) {
			if(comboBox_langue.getSelectedIndex() == 0) {
				langue = "fr";
			}
			else if(comboBox_langue.getSelectedIndex() == 1) {
				langue = "en";
			}
			else if(comboBox_langue.getSelectedIndex() == 2) {
				langue = "it";
			}
			else if(comboBox_langue.getSelectedIndex() == 3) {
				langue = "al";
			}
			if(premier_passage) {
				premier_passage = false;
				buildPanelSkin();
				buildPanelImages();
				buildPanelLangue();
				buildPanelClientInternet();
				buildBd();
				premier_passage = true;
			}
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
			if(boutton.getText().equals(Traductions.vueOptions(langue, "buildpanelbd_boutton_enregistrer"))) {
				Runtime run = Runtime.getRuntime();
				Options.setURLOracle(textField_oracle_home.getText());
				Options.setSIDOracle(textField_oracle_sid.getText());
				Options.enregistrerOptions();
				System.setProperty("ORACLE_HOME", textField_oracle_home.getText());
				System.setProperty("ORACLE_SID", textField_oracle_sid.getText());
				String [] env = new String [System.getProperties().size()];
				Enumeration<Object> keys = System.getProperties().keys();
				String key = (String)keys.nextElement();
				int i=0;
				while(!key.equals("")) {
						env[i] = key+"="+System.getProperty(key);
						i++;
					try {
						key = (String)keys.nextElement();
					}
					catch(NoSuchElementException e) {
						key = "";
					}
				}
				p = null;
				try {
					String tables = "personne, adherent, personnelAdministratif, personnelSportif, ";
					tables = tables + "materielVente, materielLocation, location, vente, ";
					tables = tables + "casier, reservationCasierAnnee, reservationCasierSeance, ";
					tables = tables + "forfait, historiqueSouscription, souscription, ";
					tables = tables + "terrainSquash, reservationTerrainSquash, ";
					tables = tables + "coursSquash, coursSquashIndividuel, coursSquashCollectif, inscriptionCoursSqCollectif, creneauCoursSqIndividuel, creneauCoursSqColl, ";
					tables = tables + "salleGym, coursGym, creneauCoursGym";
					p = run.exec("exp userid="+Options.getProprietaire()+"/"+Options.getPasseOracle()+" file="+ Options.getExportFichier()+" log="+Options.getExportFichierLog()+" tables=("+tables+")", env);
				} catch (IOException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				
				byte[] b = new byte[1024];
				try {
					p.getErrorStream().read(b);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String s = new String(b);
				if(s.length() != 1024) {
					Utiles.erreur(s);
				}
				buildBd();
			}
			else if(boutton.getText().equals(Traductions.vueOptions(langue, "buildpanelbd_boutton_importer"))) {
				Runtime run = Runtime.getRuntime();
				System.setProperty("ORACLE_HOME", "/usr/lib/oracle/xe/app/oracle/product/10.2.0/server");
				System.setProperty("ORACLE_SID", "XE");
				String [] env = new String [System.getProperties().size()];
				Enumeration<Object> keys = System.getProperties().keys();
				String key = (String)keys.nextElement();
				int i=0;
				while(!key.equals("")) {
						env[i] = key+"="+System.getProperty(key);
						i++;
					try {
						key = (String)keys.nextElement();
					}
					catch(NoSuchElementException e) {
						key = "";
					}
				}
				p = null;
				try {
					p = run.exec("imp userid="+Options.getProprietaire()+"/"+Options.getPasseOracle()+" file="+ comboBox_import_bd.getSelectedItem().toString(), env);
				} catch (IOException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				
				byte[] b = new byte[1024];
				try {
					p.getErrorStream().read(b);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String s = new String(b);
				Utiles.erreur(s);
				buildBd();
			}
			else if(boutton.getText().equals("+")) {
				panel_grant.add(builPanelGrant());
				panel_grant.validate();
				scrollPane_grant.validate();
			}
			else if(boutton.getText().equals("Valider Droits")) {
				for(int nom=0; nom<vector_nom.size(); nom++) {
					if(!vector_nom.get(nom).getText().equals("")) {
						ArrayList<String> list_droit = new ArrayList<String>();
						for(int droit=0; droit<vector_droits.get(nom).size(); droit++) {
							if(vector_droits.get(nom).get(droit).isSelected()) {
								list_droit.add(vector_droits.get(nom).get(droit).getText());
							}
						}
						try {
							SQL.ajouterDroit(list_droit, vector_nom.get(nom).getText());
						} catch (SQLException e) {
							Utiles.erreur(e.getLocalizedMessage());
						}
					}
				}
			}
			else {
				Utiles.setTheme(comboBox_skin.getSelectedItem().toString());
				String langue = "";
				if(comboBox_langue.getSelectedIndex() == 0)
					langue = "fr";
				else if(comboBox_langue.getSelectedIndex() == 1)
					langue = "en";
				else if(comboBox_langue.getSelectedIndex() == 2)
					langue = "it";
				else if(comboBox_langue.getSelectedIndex() == 3)
					langue = "al";
				Options.setLangue(langue);
				Options.setExplorateurInternet(textField_explorateur_internet.getText());
				Options.setUrlClientInternet(textField_url_client.getText());
				Options.enregistrerOptions();
				main_window.changerTab(Traductions.vueOptions(langue, "racine"), new PremiereVue(main_window));
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
}
