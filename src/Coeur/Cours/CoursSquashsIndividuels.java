package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CoursSquashsIndividuels {
	private ArrayList<CoursSquashIndividuel> cours_squashs_individuels;
	protected final EventListenerList listeners = new EventListenerList();
	
	public CoursSquashsIndividuels() throws SQLException
	{
		cours_squashs_individuels = new ArrayList<CoursSquashIndividuel>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquashIndividuel";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int num_adherent = resultat.getInt("num_adherent");
				CoursSquashIndividuel cours_squash_individuel = new CoursSquashIndividuel(id_cours, num_adherent);
				cours_squashs_individuels.add(cours_squash_individuel);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCoursSquashsIndividuelsListener(CoursSquashsIndividuelsListener listener) {
        listeners.add(CoursSquashsIndividuelsListener.class, listener);
    }
	
	public void removeCoursSquashsIndividuelsListener(CoursSquashsIndividuelsListener listener){
		 listeners.remove(CoursSquashsIndividuelsListener.class, listener);
	}
	
	public CoursSquashsIndividuelsListener[] getCoursSquashsIndividuelsListener()
	{
		return listeners.getListeners(CoursSquashsIndividuelsListener.class);
	}
	
	protected void fireAjouterCoursSquashIndividuel(CoursSquashIndividuel cours_squash_individuel) {
        for(CoursSquashsIndividuelsListener listener : getCoursSquashsIndividuelsListener()) {
            listener.ajouterCoursSquashIndividuel(cours_squash_individuel);
        }
    }
	
	protected void fireSupprimerCoursSquashIndividuel(CoursSquashIndividuel cours_squash_individuel) {
        for(CoursSquashsIndividuelsListener listener : getCoursSquashsIndividuelsListener()) {
            listener.supprimerCoursSquashIndividuel(cours_squash_individuel);
        }
    }
	
	public void supprimerCoursSquashIndividuel(int id_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".coursSquashIndividuel WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CoursSquashIndividuel cours_squash_individuel_a_supprimer=null;
				for(CoursSquashIndividuel cours_squash_individuel : cours_squashs_individuels)
					if(cours_squash_individuel.getIdCours() == id_cours)
						cours_squash_individuel_a_supprimer = cours_squash_individuel;
				cours_squashs_individuels.remove(cours_squash_individuel_a_supprimer);
				fireSupprimerCoursSquashIndividuel(cours_squash_individuel_a_supprimer);
			}
			else
				throw new SQLException("Cours Squash Individuel non supprimé dans la Dabatase numero cours :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCoursSquash(int id_cours, int num_adherent) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".coursSquashIndividuel SET num_adherent=? WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_adherent);
			pstmt.setInt(2, id_cours);
			if(pstmt.executeUpdate()>0)
			{
				CoursSquashIndividuel cours_squash_individuel_a_modifier = null;
				for(CoursSquashIndividuel cours_squash_individuel : cours_squashs_individuels)
					if(cours_squash_individuel.getIdCours() == id_cours)
						cours_squash_individuel_a_modifier = cours_squash_individuel;
				if(cours_squash_individuel_a_modifier != null)
				{
					cours_squashs_individuels.remove(cours_squash_individuel_a_modifier);
					fireSupprimerCoursSquashIndividuel(cours_squash_individuel_a_modifier);
					
					cours_squash_individuel_a_modifier.setNumAdherent(num_adherent);
					cours_squashs_individuels.add(cours_squash_individuel_a_modifier);
					fireAjouterCoursSquashIndividuel(cours_squash_individuel_a_modifier);
				}		
			}
			else
				throw new SQLException("CoursSquashIndividuel non modifié dans la Dabatase identifiant :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCoursSquashIndividuel(int id_cours, int num_adherent) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".coursSquashIndividuel VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, num_adherent);
			if(pstmt.executeUpdate()> 0)
			{
				CoursSquashIndividuel cours_squash_individuel = new CoursSquashIndividuel(id_cours, num_adherent);
				cours_squashs_individuels.add(cours_squash_individuel);
				fireAjouterCoursSquashIndividuel(cours_squash_individuel);
			}
			else
				throw new SQLException("CoursSquashIndividuel non ajouté dans la Dabatase numero :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<CoursSquashIndividuel> getCoursSquashsIndividuels() {
		return cours_squashs_individuels;
	}

	public String toString() {
		String string;
		string = "CoursSquashsIndividuels = \n";
		for(CoursSquashIndividuel a : cours_squashs_individuels)
			string += a+"\n";
		return string;
	}
}
