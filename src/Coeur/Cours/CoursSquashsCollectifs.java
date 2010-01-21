package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CoursSquashsCollectifs {
	private ArrayList<CoursSquashCollectif> cours_squashs_collectifs;
	protected final EventListenerList listeners = new EventListenerList();

	public CoursSquashsCollectifs() throws SQLException
	{
		cours_squashs_collectifs = new ArrayList<CoursSquashCollectif>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquashCollectif";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int nb_personne = resultat.getInt("nb_personne");
				CoursSquashCollectif cours_squash_collectif = new CoursSquashCollectif(id_cours, nb_personne);
				cours_squashs_collectifs.add(cours_squash_collectif);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCoursSquashsCollectifsListener(CoursSquashsCollectifsListener listener) {
        listeners.add(CoursSquashsCollectifsListener.class, listener);
    }
	
	public void removeCoursSquashsCollectifsListener(CoursSquashsCollectifsListener listener){
		 listeners.remove(CoursSquashsCollectifsListener.class, listener);
	}
	
	public CoursSquashsCollectifsListener[] getCoursSquashsCollectifsListener()
	{
		return listeners.getListeners(CoursSquashsCollectifsListener.class);
	}
	
	protected void fireAjouterCoursSquashCollectif(CoursSquashCollectif cours_squash_collectif) {
        for(CoursSquashsCollectifsListener listener : getCoursSquashsCollectifsListener()) {
            listener.ajouterCoursSquashCollectif(cours_squash_collectif);
        }
    }
	
	protected void fireSupprimerCoursSquashCollectif(CoursSquashCollectif cours_squash_collectif) {
        for(CoursSquashsCollectifsListener listener : getCoursSquashsCollectifsListener()) {
            listener.supprimerCoursSquashCollectif(cours_squash_collectif);
        }
    }
	
	public void supprimerCoursSquashCollectif(int id_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".coursSquashCollectif WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CoursSquashCollectif cours_squash_collectif_a_supprimer=null;
				for(CoursSquashCollectif cours_squash_collectif : cours_squashs_collectifs)
					if(cours_squash_collectif.getIdCours() == id_cours)
						cours_squash_collectif_a_supprimer = cours_squash_collectif;
				cours_squashs_collectifs.remove(cours_squash_collectif_a_supprimer);
				fireSupprimerCoursSquashCollectif(cours_squash_collectif_a_supprimer);
			}
			else
				throw new SQLException("Cours Squash Collectif non supprimé dans la Dabatase numero cours :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCoursSquashCollectif(int id_cours, int nb_personne) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".coursSquashCollectif SET nb_personne=? WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, nb_personne);
			pstmt.setInt(2, id_cours);
			if(pstmt.executeUpdate()>0)
			{
				CoursSquashCollectif cours_squash_collectif_a_modifier = null;
				for(CoursSquashCollectif cours_squash_collectif: cours_squashs_collectifs)
					if(cours_squash_collectif.getIdCours() == id_cours)
						cours_squash_collectif_a_modifier = cours_squash_collectif;
				if(cours_squash_collectif_a_modifier != null)
				{
					cours_squashs_collectifs.remove(cours_squash_collectif_a_modifier);
					fireSupprimerCoursSquashCollectif(cours_squash_collectif_a_modifier);
					
					cours_squash_collectif_a_modifier.setNbPersonne(nb_personne);
					cours_squashs_collectifs.add(cours_squash_collectif_a_modifier);
					fireAjouterCoursSquashCollectif(cours_squash_collectif_a_modifier);
				}		
			}
			else
				throw new SQLException("CoursSquashCollectif non modifié dans la Dabatase identifiant :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCoursSquashCollectif(int id_cours, int nb_personne) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".coursSquashCollectif VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, nb_personne);
			if(pstmt.executeUpdate()> 0)
			{
				CoursSquashCollectif cours_squash_collectif = new CoursSquashCollectif(id_cours, nb_personne);
				cours_squashs_collectifs.add(cours_squash_collectif);
				fireAjouterCoursSquashCollectif(cours_squash_collectif);
			}
			else
				throw new SQLException("CoursSquashCollectif non ajouté dans la Dabatase numero :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<CoursSquashCollectif> getCoursSquashsCollectifs() {
		return cours_squashs_collectifs;
	}

	public String toString() {
		String string;
		string = "CoursSquashsCollectifs = \n";
		for(CoursSquashCollectif a : cours_squashs_collectifs)
			string += a+"\n";
		return string;
	}
}
