package Graphique;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class BouttonPremiereVue extends JLabel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	private int type;
	private ImageIcon[] images;
	private String[] personnes_strings = Traductions.bouttonPremiereVue(Options.getLangue(), "personnes");//type = 0
	private String[] batiments_strings = Traductions.bouttonPremiereVue(Options.getLangue(), "batiments");//type = 1
	private String[] euros_strings = Traductions.bouttonPremiereVue(Options.getLangue(), "euros");//type = 2
	private String[] client_strings = Traductions.bouttonPremiereVue(Options.getLangue(), "client");//type = 3
	private String[] options_strings = Traductions.bouttonPremiereVue(Options.getLangue(), "options");//type = 4
	
	private String[] personnes_images = {"Personnes", "Personnes", "Personnels", "Utilisateurs"};//type = 0
	private String[] batiments_images = {"Batiments", "Squash", "Gymnastique", "Casier", "Brasserie"};//type = 1
	private String[] euros_images = {"Euros", "Vente", "Location"};//type = 2
	private String[] client_images = {"Client Internet", "Lancer"};//type = 3
	private String[] options_images = {"Options", "Parametres", "Deconnection"};//type = 4
	
	private int selectedIndex;

	public BouttonPremiereVue(int type) {
		this.type = type;
		setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
        if(type == 0) {
	        images = new ImageIcon[personnes_strings.length];
	        for (int i = 0; i < personnes_strings.length; i++) {
	            images[i] = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Personnes/" + personnes_images[i] + ".png");
	            if (images[i] != null) {
	                images[i].setDescription(personnes_strings[i]);
	            }
	        }
        }
        else if(type == 1) {
	        images = new ImageIcon[batiments_strings.length];
	        for (int i = 0; i < batiments_strings.length; i++) {
	            images[i] = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Batiments/" + batiments_images[i] + ".png");
	            if (images[i] != null) {
	                images[i].setDescription(batiments_strings[i]);
	            }
	        }
        }
        else if(type == 2) {
	        images = new ImageIcon[euros_strings.length];
	        for (int i = 0; i < euros_strings.length; i++) {
	            images[i] = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Euros/" + euros_images[i] + ".png");
	            if (images[i] != null) {
	                images[i].setDescription(euros_strings[i]);
	            }
	        }
        }
        else if(type == 3) {
	        images = new ImageIcon[client_strings.length];
	        for (int i = 0; i < client_strings.length; i++) {
	            images[i] = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Client/" + client_images[i] + ".png");
	            if (images[i] != null) {
	                images[i].setDescription(client_strings[i]);
	            }
	        }
        }
        else if(type == 4) {
	        images = new ImageIcon[options_strings.length];
	        for (int i = 0; i < options_strings.length; i++) {
	            images[i] = Utiles.createImageIcon(Utiles.getTheme()+"/Images/Options/" + options_images[i] + ".png");
	            if (images[i] != null) {
	                images[i].setDescription(options_strings[i]);
	            }
	        }
        }
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		//Get the selected index. (The index param isn't
        //always valid, so just use the value.)
        selectedIndex = ((Integer)value).intValue();

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } 
        else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

		//Set the icon and text.  If icon was null, say so.
        ImageIcon icon = images[selectedIndex];
        String en_cours = "";
        if(type == 0) {
        	en_cours = personnes_strings[selectedIndex];
        }
        if(type == 1) {
        	en_cours = batiments_strings[selectedIndex];
        }
        else if(type == 2) {
        	en_cours = euros_strings[selectedIndex];
        }
        else if(type == 3) {
        	en_cours = client_strings[selectedIndex];
        }
        else if(type == 4) {
        	en_cours = options_strings[selectedIndex];
        }
        setIcon(icon);
        if (icon != null) {
            setText(en_cours);
            setHorizontalTextPosition(0);
            setVerticalTextPosition(BOTTOM);
            setFont(list.getFont());
        } 
        else {
            setText(en_cours);
        }
        return this;
	}


}
