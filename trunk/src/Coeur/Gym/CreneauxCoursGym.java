package Coeur.Gym;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CreneauxCoursGym {
	private ArrayList<CreneauCoursGym> creneaux_cours_gym;
	protected final EventListenerList listeners = new EventListenerList();

	public CreneauxCoursGym() throws SQLException
	{
		creneaux_cours_gym = new ArrayList<CreneauCoursGym>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursGym";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_salle = resultat.getInt("id_salle");
				String type_cours = resultat.getString("type_cours");
				Date date_cours = resultat.getDate("date_cours");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				Date debut_trimestre = resultat.getDate("debut_trimestre");
				CreneauCoursGym salle_gym = new CreneauCoursGym(id_salle, type_cours, date_cours, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_gym.add(salle_gym);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	public CreneauxCoursGym(Date date_cours) throws SQLException
	{
		creneaux_cours_gym = new ArrayList<CreneauCoursGym>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursGym WHERE date_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setDate(1, date_cours);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int id_salle = resultat.getInt("id_salle");
				String type_cours = resultat.getString("type_cours");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				Date debut_trimestre = resultat.getDate("debut_trimestre");
				CreneauCoursGym salle_gym = new CreneauCoursGym(id_salle, type_cours, date_cours, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_gym.add(salle_gym);
			}
			
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCreneauxCoursGymListener(CreneauxCoursGymListener listener) {
        listeners.add(CreneauxCoursGymListener.class, listener);
    }
	
	public void removeCreneauxCoursGymListener(CreneauxCoursGymListener listener){
		 listeners.remove(CreneauxCoursGymListener.class, listener);
	}
	
	public CreneauxCoursGymListener[] getCreneauxCoursGymListener()
	{
		return listeners.getListeners(CreneauxCoursGymListener.class);
	}
	
	protected void fireAjouterCreneauCoursGym(CreneauCoursGym creneau_cours_gym) {
        for(CreneauxCoursGymListener listener : getCreneauxCoursGymListener()) {
            listener.ajouterCreneauCoursGym(creneau_cours_gym);
        }
    }
	
	protected void fireSupprimerCreneauCoursGym(CreneauCoursGym creneau_cours_gym) {
        for(CreneauxCoursGymListener listener : getCreneauxCoursGymListener()) {
            listener.supprimerCreneauCoursGym(creneau_cours_gym);
        }
    }
	
	public void supprimerCreneauCoursGym(int id_salle, Date date_cours, int heure_debut) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".creneauCoursGym WHERE id_salle=? AND date_cours=? AND heure_debut=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_salle);
			pstmt.setDate(2, date_cours);
			pstmt.setInt(3, heure_debut);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CreneauCoursGym creneau_cours_gym_a_supprimer=null;
				for(CreneauCoursGym creneau_cours_gym : creneaux_cours_gym)
					if(creneau_cours_gym.getIdSalle() == id_salle && 
							creneau_cours_gym.getHeureDebut() == heure_debut &&
							creneau_cours_gym.getDateCours().compareTo(date_cours) == 0)
						creneau_cours_gym_a_supprimer = creneau_cours_gym;
				creneaux_cours_gym.remove(creneau_cours_gym_a_supprimer);
				fireSupprimerCreneauCoursGym(creneau_cours_gym_a_supprimer);
			}
			else
				throw new SQLException("Creneau Cours gym non supprimé dans la Dabatase salle :"+id_salle+" le "+date_cours+" a "+heure_debut+"h");
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCreneauCoursGym(int id_salle, String type_cours, Date date_cours, int heure_debut, int heure_fin, Date debut_trimestre) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".creneauCoursGym SET heure_fin=?, type_cours=?, debut_trimestre=? WHERE id_salle=? AND date_cours=? AND heure_debut=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, heure_fin);
			pstmt.setString(2, type_cours);
			pstmt.setInt(4, id_salle);
			pstmt.setDate(5, date_cours);
			pstmt.setDate(3, debut_trimestre);
			pstmt.setInt(6, heure_debut);
			if(pstmt.executeUpdate()>0)
			{
				CreneauCoursGym creneau_cours_gym_a_modifier = null;
				for(CreneauCoursGym creneau_cours_gym : creneaux_cours_gym)
					if(creneau_cours_gym.getIdSalle() == id_salle && 
							creneau_cours_gym.getHeureDebut() == heure_debut &&
							creneau_cours_gym.getDateCours().compareTo(date_cours) == 0)
						creneau_cours_gym_a_modifier = creneau_cours_gym;
				if(creneau_cours_gym_a_modifier != null)
				{
					creneaux_cours_gym.remove(creneau_cours_gym_a_modifier);
					fireSupprimerCreneauCoursGym(creneau_cours_gym_a_modifier);

					creneau_cours_gym_a_modifier.setHeureFin(heure_fin);
					creneau_cours_gym_a_modifier.setTypeCours(type_cours);
					creneau_cours_gym_a_modifier.setDebutTrimestre(debut_trimestre);
					creneaux_cours_gym.add(creneau_cours_gym_a_modifier);
					fireAjouterCreneauCoursGym(creneau_cours_gym_a_modifier);
				}		
			}
			else
				throw new SQLException("Creneau cours Gym non modifié dans la Dabatase salle :"+id_salle+" le "+date_cours+" a "+heure_debut+"h");
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCreneauCoursGym(int id_salle, String type_cours, Date date_cours, int heure_debut, int heure_fin, Date debut_trimestre) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".creneauCoursGym VALUES(?,?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_salle);
			pstmt.setString(2, type_cours);
			pstmt.setDate(3, date_cours);
			pstmt.setInt(4, heure_debut);
			pstmt.setInt(5, heure_fin);
			pstmt.setDate(6, debut_trimestre);
			if(pstmt.executeUpdate()> 0)
			{
				CreneauCoursGym creneau_cours_gym = new CreneauCoursGym(id_salle, type_cours, date_cours, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_gym.add(creneau_cours_gym);
				fireAjouterCreneauCoursGym(creneau_cours_gym);
			}
			else
				throw new SQLException("Creneau cours gym non ajouté dans la Dabatase cours :"+id_salle+" le "+date_cours+" a "+heure_debut+"h");
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}


	public ArrayList<CreneauCoursGym> getCreneauxCoursGym() {
		return creneaux_cours_gym;
	}

	public String toString() {
		String string;
		string = "CreneauxCoursGym = \n";
		for(CreneauCoursGym a : creneaux_cours_gym)
			string += a+"\n";
		return string;
	}
}
