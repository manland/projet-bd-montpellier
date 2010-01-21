package Graphique;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import Coeur.SQL;

public class PremiereVue extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainWindow main_window;
	private JComboBox personnes_list;
	private JComboBox batiments_list;
	private JComboBox euros_list;
	private JComboBox client_list;
	private JComboBox options_list;
	
	public PremiereVue(MainWindow main_window) {
		super();
		this.main_window = main_window;
		//Personnes - type = 0
        Integer[] int_array_personnes = new Integer[4];
        for (int i = 0; i < 4; i++) {
        	int_array_personnes[i] = new Integer(i);
        }
        personnes_list = new JComboBox(int_array_personnes);
        personnes_list.addActionListener(this);
        BouttonPremiereVue personnes_renderer = new BouttonPremiereVue(0);
        personnes_renderer.setPreferredSize(new Dimension(150, 150));
        personnes_list.setRenderer(personnes_renderer);
        personnes_list.setMaximumRowCount(3);
        add(personnes_list);
        //Batiments - type = 1
        Integer[] int_array_batiments = new Integer[5];
        for (int i = 0; i < 5; i++) {
        	int_array_batiments[i] = new Integer(i);
        }
        batiments_list = new JComboBox(int_array_batiments);
        batiments_list.addActionListener(this);
        BouttonPremiereVue batiments_renderer= new BouttonPremiereVue(1);
        batiments_renderer.setPreferredSize(new Dimension(150, 150));
        batiments_list.setRenderer(batiments_renderer);
        batiments_list.setMaximumRowCount(3);
        add(batiments_list);
        //Euros - type = 2
        Integer[] int_array_euros = new Integer[3];
        for (int i = 0; i < 3; i++) {
        	int_array_euros[i] = new Integer(i);
        }
        euros_list = new JComboBox(int_array_euros);
        euros_list.addActionListener(this);
        BouttonPremiereVue euros_renderer= new BouttonPremiereVue(2);
        euros_renderer.setPreferredSize(new Dimension(150, 150));
        euros_list.setRenderer(euros_renderer);
        euros_list.setMaximumRowCount(3);
        add(euros_list);
        //Client Internet - type = 3
        Integer[] int_array_client = new Integer[2];
        for (int i = 0; i < 2; i++) {
        	int_array_client[i] = new Integer(i);
        }
        client_list = new JComboBox(int_array_client);
        client_list.addActionListener(this);
        BouttonPremiereVue client_renderer= new BouttonPremiereVue(3);
        client_renderer.setPreferredSize(new Dimension(150, 150));
        client_list.setRenderer(client_renderer);
        client_list.setMaximumRowCount(3);
        add(client_list);
        //Options - type = 4
        Integer[] int_array_options = new Integer[3];
        for (int i = 0; i < 3; i++) {
        	int_array_options[i] = new Integer(i);
        }
        options_list = new JComboBox(int_array_options);
        options_list.addActionListener(this);
        BouttonPremiereVue options_renderer= new BouttonPremiereVue(4);
        options_renderer.setPreferredSize(new Dimension(150, 150));
        options_list.setRenderer(options_renderer);
        options_list.setMaximumRowCount(3);
        add(options_list);
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
		if(comboBox == personnes_list) {
			if(personnes_list.getSelectedIndex() == 1) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_personnes"), new VuePersonnes(main_window));
			}
			else if(personnes_list.getSelectedIndex() == 2) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_personnels"), new VuePersonnels(main_window));
			}
			else if(personnes_list.getSelectedIndex() == 3) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_utilisateurs"), new VueAdherents(main_window));
			}
		}
		else if(comboBox == batiments_list) {
			if(batiments_list.getSelectedIndex() == 1) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_squash"), new VueSquash(main_window));
			}
			else if(batiments_list.getSelectedIndex() == 2) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_cours"), new VueGym(main_window));
			}
			else if(batiments_list.getSelectedIndex() == 3) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_casiers"), new VueCasier(main_window));
			}
		}
		else if(comboBox == euros_list) {
			if(euros_list.getSelectedIndex() == 1) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_ventes"), new VueVente(main_window));
			}
			else if(euros_list.getSelectedIndex() == 2) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_location"), new VueLocation(main_window));
			}
		}
		else if(comboBox == client_list) {
			if(client_list.getSelectedIndex() == 1) {
				try {
					client_list.setSelectedIndex(0);
					Runtime.getRuntime().exec(Options.getExplorateurInternet()+" "+Options.getUrlClientInternet());
				} catch (IOException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
			}
		}
		else if(comboBox == options_list) {
			if(options_list.getSelectedIndex() == 1) {
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_skins"), new VueOptions(main_window));
			}
			else if(options_list.getSelectedIndex() == 2) {
				try {
					SQL.sqlDeconnexion();
				} catch (SQLException e) {
					Utiles.erreur(e.getLocalizedMessage());
				}
				SQL.setUtilisateur("");
				SQL.setMotDePasse("");
				main_window.changerTab(Traductions.premiereVue(Options.getLangue(), "actionperformed_deconnection"), new VueConnexionBd(main_window));
			}
		}
	}

}
