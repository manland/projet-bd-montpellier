package Graphique;

import java.awt.Dimension;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public final class Utiles {

	private static String theme = "man";
	private static String url_skin = "../Skins/";
	public static String getTheme() {
		return url_skin+theme;
	}
	public static void setTheme(String theme2) {
		theme = theme2;
	}
	public static String getUrlTheme() {
		return url_skin;
	}
	public static void setUrlTheme(String url) {
		url_skin = url;
	}
	public static String getQueTheme() {
		return theme;
	}
	
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } 
        else {
            System.err.println(Traductions.utiles(Options.getLangue(), "image_erreur") + " : " + path);
            return null;
        }
    }
    
    public static String dateToString(Date d) {
    	String res = "";
    	Calendar date = Calendar.getInstance();
    	date.setTime(d);
    	res = res + date.get(Calendar.DATE) + " ";
		String [] list_mois = Traductions.utilesTab(Options.getLangue(), "mois");
		res = res + list_mois[date.get(Calendar.MONTH)] + " ";
		res = res + date.get(Calendar.YEAR);
		return res;
    }
    
    public static Date stringToDate(String date) {
    	int premier_espace = date.indexOf(" ", 0);
    	String jour = date.substring(0, premier_espace);
    	int deuxieme_espace = date.indexOf(" ", premier_espace+1);
    	String mois = date.substring(premier_espace+1, deuxieme_espace);
    	String annee = date.substring(deuxieme_espace+1, date.length());
    	
		String [] list_mois = Traductions.utilesTab(Options.getLangue(), "mois");
		for(int i=0; i<list_mois.length; i++) {
			if(mois.equals(list_mois[i]))
				mois = ""+(i+1);
		}
		
		return Date.valueOf(annee+"-"+mois+"-"+jour);
    }
    
    public static String moisToString(int mois) {
    	String [] list_mois = Traductions.utilesTab(Options.getLangue(), "mois");
    	return list_mois[mois];
    }
    
    public static int getNowDay() {
		return Calendar.getInstance().get(Calendar.DATE);
	}
	public static int getNowMonth() {
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	public static int getNowYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	public static void erreur(String message) {
		JPanel dialog_erreur = new JPanel();
		BoxLayout boxLayout_erreur = new BoxLayout(dialog_erreur, BoxLayout.PAGE_AXIS);
		dialog_erreur.setLayout(boxLayout_erreur);
		
		JOptionPane.showMessageDialog(dialog_erreur,
			    message,
			    Traductions.utiles(Options.getLangue(), "dialog_erreur_bd"),
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public static void information(String message) {
		JPanel dialog_information = new JPanel();
		dialog_information.setMaximumSize(new Dimension(600, 600));
		BoxLayout boxLayout_erreur = new BoxLayout(dialog_information, BoxLayout.PAGE_AXIS);
		dialog_information.setLayout(boxLayout_erreur);
		
		JOptionPane.showMessageDialog(dialog_information,
			    message,
			    Traductions.utiles(Options.getLangue(), "dialog_information_bd"),
			    JOptionPane.INFORMATION_MESSAGE);
	}
}
