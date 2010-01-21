package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CoursSquashs {
	private ArrayList<CoursSquash> cours_squashs;
	protected final EventListenerList listeners = new EventListenerList();
	
	public CoursSquashs() throws SQLException
	{
		cours_squashs = new ArrayList<CoursSquash>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquash";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int id_prof = resultat.getInt("id_prof");
				String type_cours = resultat.getString("type_de_cours");
				CoursSquash cours_squash = new CoursSquash(id_cours,id_prof, type_cours);
				cours_squashs.add(cours_squash);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCoursSquashsListener(CoursSquashsListener listener) {
        listeners.add(CoursSquashsListener.class, listener);
    }
	
	public void removeCoursSquashsListener(CoursSquashsListener listener){
		 listeners.remove(CoursSquashsListener.class, listener);
	}
	
	public CoursSquashsListener[] getCoursSquashsListener()
	{
		return listeners.getListeners(CoursSquashsListener.class);
	}
	
	protected void fireAjouterCoursSquash(CoursSquash cours_squash) {
        for(CoursSquashsListener listener : getCoursSquashsListener()) {
            listener.ajouterCoursSquash(cours_squash);
        }
    }
	
	protected void fireSupprimerCoursSquash(CoursSquash cours_squash) {
        for(CoursSquashsListener listener : getCoursSquashsListener()) {
            listener.supprimerCoursSquash(cours_squash);
        }
    }
	
	public void supprimerCoursSquash(int id_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".coursSquash WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CoursSquash cours_squash_a_supprimer=null;
				for(CoursSquash cours_squash : cours_squashs)
					if(cours_squash.getIdCours() == id_cours)
						cours_squash_a_supprimer = cours_squash;
				cours_squashs.remove(cours_squash_a_supprimer);
				fireSupprimerCoursSquash(cours_squash_a_supprimer);
			}
			else
				throw new SQLException("Cours Squash non supprimé dans la Dabatase numero cours :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCoursSquash(int id_cours, int id_prof) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".coursSquash SET id_prof=? WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_prof);
			pstmt.setInt(2, id_cours);
			if(pstmt.executeUpdate()>0)
			{
				CoursSquash cours_squash_a_modifier = null;
				for(CoursSquash cours_squash : cours_squashs)
					if(cours_squash.getIdCours() == id_cours)
						cours_squash_a_modifier = cours_squash;
				if(cours_squash_a_modifier != null)
				{
					cours_squashs.remove(cours_squash_a_modifier);
					fireSupprimerCoursSquash(cours_squash_a_modifier);
					
					cours_squash_a_modifier.setIdProf(id_prof);
					cours_squashs.add(cours_squash_a_modifier);
					fireAjouterCoursSquash(cours_squash_a_modifier);
				}		
			}
			else
				throw new SQLException("CoursSquash non modifié dans la Dabatase identifiant :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCoursSquash(int id_prof, String type_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int id_cours = maximumIdCours() + 1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".coursSquash VALUES(?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, id_prof);
			pstmt.setString(3, type_cours);
			if(pstmt.executeUpdate()> 0)
			{
				CoursSquash cours_squash = new CoursSquash(id_cours, id_prof, type_cours);
				cours_squashs.add(cours_squash);
				fireAjouterCoursSquash(cours_squash);
			}
			else
				throw new SQLException("CoursSquash non ajouté dans la Dabatase numero :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	private int maximumIdCours() {
		// TODO Auto-generated method stub
		int maximum =0;
		for(CoursSquash a : cours_squashs)
		{
			if(a.getIdCours() > maximum)
				maximum = a.getIdCours();
		}
		return maximum;
	}

	public ArrayList<CoursSquash> getCoursSquashs() {
		return cours_squashs;
	}

	public String toString() {
		String string;
		string = "CoursSquashs = \n";
		for(CoursSquash a : cours_squashs)
			string += a+"\n";
		return string;
	}

}
