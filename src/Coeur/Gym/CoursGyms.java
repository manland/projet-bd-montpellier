package Coeur.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CoursGyms {
	private ArrayList<CoursGym> cours_gyms;
	protected final EventListenerList listeners = new EventListenerList();
	
	public CoursGyms() throws SQLException
	{
		cours_gyms = new ArrayList<CoursGym>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursGym";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				String type_cours = resultat.getString("type_cours");
				int id_prof = resultat.getInt("id_prof");
				CoursGym cours_gym = new CoursGym(type_cours, id_prof);
				cours_gyms.add(cours_gym);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCoursGymsListener(CoursGymsListener listener) {
        listeners.add(CoursGymsListener.class, listener);
    }
	
	public void removeCoursGymsListener(CoursGymsListener listener){
		 listeners.remove(CoursGymsListener.class, listener);
	}
	
	public CoursGymsListener[] getCoursGymsListener()
	{
		return listeners.getListeners(CoursGymsListener.class);
	}
	
	protected void fireAjouterCoursGym(CoursGym cours_gym) {
        for(CoursGymsListener listener : getCoursGymsListener()) {
            listener.ajouterCoursGym(cours_gym);
        }
    }
	
	protected void fireSupprimerCoursGym(CoursGym cours_gym) {
        for(CoursGymsListener listener : getCoursGymsListener()) {
            listener.supprimerCoursGym(cours_gym);
        }
    }
	
	public void supprimerCoursGym(String type_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".coursGym WHERE type_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_cours);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CoursGym cours_gym_a_supprimer=null;
				for(CoursGym cours_gym : cours_gyms)
					if(cours_gym.getTypeCours().equals(type_cours))
						cours_gym_a_supprimer = cours_gym;
				cours_gyms.remove(cours_gym_a_supprimer);
				fireSupprimerCoursGym(cours_gym_a_supprimer);
			}
			else
				throw new SQLException("Cours gym non supprimé dans la Dabatase cours :"+type_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCoursGym(String type_cours, int id_prof) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".coursGym SET id_prof=? WHERE type_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_prof);
			pstmt.setString(2, type_cours);
			if(pstmt.executeUpdate()>0)
			{
				CoursGym cours_gym_a_modifier = null;
				for(CoursGym cours_gym : cours_gyms)
					if(cours_gym.getTypeCours().equals(type_cours))
						cours_gym_a_modifier = cours_gym;
				if(cours_gym_a_modifier != null)
				{
					cours_gyms.remove(cours_gym_a_modifier);
					fireSupprimerCoursGym(cours_gym_a_modifier);
					
					cours_gym_a_modifier.setIdProf(id_prof);
					cours_gyms.add(cours_gym_a_modifier);
					fireAjouterCoursGym(cours_gym_a_modifier);
				}		
			}
			else
				throw new SQLException("Cours Gym non modifié dans la Dabatase cours :"+type_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCoursGym(String type_cours, int id_prof) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".coursGym VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_cours);
			pstmt.setInt(2, id_prof);
			if(pstmt.executeUpdate()> 0)
			{
				CoursGym cours_gym = new CoursGym(type_cours, id_prof);
				cours_gyms.add(cours_gym);
				fireAjouterCoursGym(cours_gym);
			}
			else
				throw new SQLException("CoursGym non ajouté dans la Dabatase cours :"+type_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<CoursGym> getCoursGyms() {
		return cours_gyms;
	}

	public String toString() {
		String string;
		string = "CoursGyms = \n";
		for(CoursGym a : cours_gyms)
			string += a+"\n";
		return string;
	}
}
