package Graphique;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

public class Options {

	private static String url_options = "bin/Options";
	private static String pseudo_oracle;
	private static String passe_oracle;
	private static String serveur_oracle = "localhost:1521:xe";
	private static String langue = "fr";
	private static String explorateur_internet = "firefox";
	private static String url_client_internet = "http://127.0.0.1/partieJohn/";
	private static String proprietaire = "";
	private static String url_oracle = "/usr/lib/oracle/xe/app/oracle/product/10.2.0/server";
	private static String sid_oracle = "XE";
	
	public static void chargerOptions() {
		File file = new File(url_options);
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    BufferedReader dis = null;

	    try {
	      fis = new FileInputStream(file);
	      bis = new BufferedInputStream(fis);
	      dis = new BufferedReader(new InputStreamReader(bis));
	      String ligne = "";
	      while ((ligne = dis.readLine()) != null) {
	    	  int position_egal = ligne.indexOf("=");
	    	  String variable = ligne.substring(0, position_egal);
	    	  String valeur = ligne.substring(position_egal+1, ligne.length());
	    	  if(variable.equals("skin")) {
	    		  Utiles.setTheme(valeur);
	    	  }
	    	  else if(variable.equals("url_skin")) {
	    		  Utiles.setUrlTheme(valeur);
	    	  }
	    	  else if(variable.equals("pseudo_oracle")) {
	    		  pseudo_oracle = valeur;
	    	  }
	    	  else if(variable.equals("passe_oracle")) {
	    		  passe_oracle = valeur;
	    	  }
	    	  else if(variable.equals("serveur_oracle")) {
	    		  serveur_oracle = valeur;
	    	  }
	    	  else if(variable.equals("langue")) {
	    		  langue = valeur;
	    	  }
	    	  else if(variable.equals("explorateur_internet")) {
	    		  explorateur_internet = valeur;
	    	  }
	    	  else if(variable.equals("url_client_internet")) {
	    		  url_client_internet = valeur;
	    	  }
	    	  else if(variable.equals("proprietaire")) {
	    		  proprietaire = valeur;
	    	  }
	    	  else if(variable.equals("url_oracle")) {
	    		  url_oracle = valeur;
	    	  }
	    	  else if(variable.equals("sid_oracle")) {
	    		  sid_oracle = valeur;
	    	  }
	    	  
	      }
	      fis.close();
	      bis.close();
	      dis.close();

	    } 
	    catch (FileNotFoundException e) {
	      Utiles.erreur(e.getLocalizedMessage());
	    } 
	    catch (IOException e) {
	    	Utiles.erreur(e.getLocalizedMessage());
	    }
	}
	
	public static void enregistrerOptions() {
		try {
	        BufferedWriter out = new BufferedWriter(new FileWriter(url_options));
	        out.write("url_skin="+Utiles.getUrlTheme());
	        out.newLine();
	        out.write("skin="+Utiles.getTheme().substring(Utiles.getUrlTheme().length(), Utiles.getTheme().length()));
	        out.newLine();
	        out.write("pseudo_oracle="+pseudo_oracle);
	        out.newLine();
	        out.write("passe_oracle="+passe_oracle);
	        out.newLine();
	        out.write("serveur_oracle="+serveur_oracle);
	        out.newLine();
	        out.write("langue="+langue);
	        out.newLine();
	        out.write("explorateur_internet="+explorateur_internet);
	        out.newLine();
	        out.write("url_client_internet="+url_client_internet);
	        out.newLine();
	        out.write("proprietaire="+proprietaire);
	        out.newLine();
	        out.write("url_oracle="+url_oracle);
	        out.newLine();
	        out.write("sid_oracle="+sid_oracle);
	        out.close();
	    } 
		catch (IOException e) {
			Utiles.erreur(e.getLocalizedMessage());
	    }
	}
	
	public static String getPseudoOracle() {
		return pseudo_oracle;
	}
	public static String getPasseOracle() {
		return passe_oracle;
	}
	public static String getServeurOracle() {
		return serveur_oracle;
	}
	public static String getServeurOracleDebut() {
		return "jdbc:oracle:thin:@";
	}
	
	public static void setPseudoOracle(String s) {
		pseudo_oracle = s;
	}
	public static void setPasseOracle(String s) {
		passe_oracle = s;
	}
	public static void setServeurOracle(String s) {
		serveur_oracle = s;
	}
	
	public static String getLangue() {
		return langue;
	}
	public static void setLangue(String s) {
		langue = s;
	}
	
	public static String getExplorateurInternet() {
		return explorateur_internet;
	}
	public static void setExplorateurInternet(String s) {
		explorateur_internet = s;
	}
	public static String getUrlClientInternet() {
		return url_client_internet;
	}
	public static void setUrlClientInternet(String s) {
		url_client_internet = s;
	}
	
	public static String getExportFichier() {
		Calendar calendar = Calendar.getInstance();
		File file = new File("bin/BackupBD/backup-"+calendar.get(Calendar.DAY_OF_MONTH)+"-"+Utiles.moisToString(calendar.get(Calendar.MONTH))+"-"+calendar.get(Calendar.YEAR)+".dmp");
		try {
			file.createNewFile();
		} catch (IOException e) {
			File dir = new File("bin/BackupBD/");
			dir.mkdir();
		}
		return file.getAbsolutePath();
	}
	public static String getExportFichierLog() {
		Calendar calendar = Calendar.getInstance();
		File file = new File("bin/BackupBD/backup-"+calendar.get(Calendar.DAY_OF_MONTH)+"-"+Utiles.moisToString(calendar.get(Calendar.MONTH))+"-"+calendar.get(Calendar.YEAR)+".log");
		try {
			file.createNewFile();
		} catch (IOException e) {
			Utiles.erreur(e.getLocalizedMessage());
		}
		return file.getAbsolutePath();
	}
	
	public static String getProprietaire() {
		return proprietaire;
	}
	public static void setProprietaire(String p) {
		proprietaire = p;
	}
	
	public static String getURLOracle() {
		return url_oracle;
	}
	public static void setURLOracle(String p) {
		url_oracle = p;
	}
	
	public static String getSIDOracle() {
		return sid_oracle;
	}
	public static void setSIDOracle(String p) {
		sid_oracle = p;
	}
}
