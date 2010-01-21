package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class InscriptionsCoursSquashsCollectifs {
	private ArrayList<InscriptionCoursSquashCollectif> inscriptions_cours_squashs_collectifs;
	protected final EventListenerList listeners = new EventListenerList();
	
	public InscriptionsCoursSquashsCollectifs() throws SQLException
	{
		inscriptions_cours_squashs_collectifs = new ArrayList<InscriptionCoursSquashCollectif>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".inscriptionCoursSqCollectif";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int num_adherent = resultat.getInt("num_adherent");
				InscriptionCoursSquashCollectif inscription_cours_squash_collectif = new InscriptionCoursSquashCollectif(id_cours, num_adherent);
				inscriptions_cours_squashs_collectifs.add(inscription_cours_squash_collectif);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public InscriptionsCoursSquashsCollectifs(int id_cours) throws SQLException
	{
		inscriptions_cours_squashs_collectifs = new ArrayList<InscriptionCoursSquashCollectif>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM inscriptionCoursSqCollectif WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_adherent = resultat.getInt("num_adherent");
				InscriptionCoursSquashCollectif inscription_cours_squash_collectif = new InscriptionCoursSquashCollectif(id_cours, num_adherent);
				inscriptions_cours_squashs_collectifs.add(inscription_cours_squash_collectif);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addInscriptionsCoursSquashsCollectifsListener(InscriptionsCoursSquashsCollectifsListener listener) {
        listeners.add(InscriptionsCoursSquashsCollectifsListener.class, listener);
    }
	
	public void removeInscriptionsCoursSquashsCollectifsListener(InscriptionsCoursSquashsCollectifsListener listener){
		 listeners.remove(InscriptionsCoursSquashsCollectifsListener.class, listener);
	}
	
	public InscriptionsCoursSquashsCollectifsListener[] getInscriptionsCoursSquashsCollectifsListener()
	{
		return listeners.getListeners(InscriptionsCoursSquashsCollectifsListener.class);
	}
	
	protected void fireAjouterInscriptionCoursSquashCollectif(InscriptionCoursSquashCollectif inscription_cours_squash_collectif) {
        for(InscriptionsCoursSquashsCollectifsListener listener : getInscriptionsCoursSquashsCollectifsListener()) {
            listener.ajouterInscriptionCoursSquashCollectif(inscription_cours_squash_collectif);
        }
    }
	
	protected void fireSupprimerInscriptionCoursSquashCollectif(InscriptionCoursSquashCollectif inscription_cours_squash_collectif) {
        for(InscriptionsCoursSquashsCollectifsListener listener : getInscriptionsCoursSquashsCollectifsListener()) {
            listener.supprimerInscriptionCoursSquashCollectif(inscription_cours_squash_collectif);
        }
    }
	
	public void supprimerInscriptionCoursSquashCollectif(int id_cours, int num_adherent) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".inscriptionCoursSqCollectif WHERE id_cours=? AND num_adherent=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, num_adherent);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				InscriptionCoursSquashCollectif inscription_cours_squash_collectif_a_supprimer=null;
				for(InscriptionCoursSquashCollectif inscription_cours_squash_collectif : inscriptions_cours_squashs_collectifs)
					if(inscription_cours_squash_collectif.getIdCours() == id_cours &&
							inscription_cours_squash_collectif.getNumAdherent() == num_adherent)
						inscription_cours_squash_collectif_a_supprimer = inscription_cours_squash_collectif;
				inscriptions_cours_squashs_collectifs.remove(inscription_cours_squash_collectif_a_supprimer);
				fireSupprimerInscriptionCoursSquashCollectif(inscription_cours_squash_collectif_a_supprimer);
			}
			else
				throw new SQLException("InscriptionCoursSquashCollectif non supprimé dans la Dabatase numero cours :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCoursSquash(int id_cours, int num_adherent) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".inscriptionCoursSqCollectif VALUES(?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, num_adherent);
			if(pstmt.executeUpdate()> 0)
			{
				InscriptionCoursSquashCollectif inscription_cours_squash_collectif = new InscriptionCoursSquashCollectif(id_cours, num_adherent);
				inscriptions_cours_squashs_collectifs.add(inscription_cours_squash_collectif);
				fireAjouterInscriptionCoursSquashCollectif(inscription_cours_squash_collectif);
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
	

	public ArrayList<InscriptionCoursSquashCollectif> getInscriptionsCoursSquashsCollectifs() {
		return inscriptions_cours_squashs_collectifs;
	}

	public String toString() {
		String string;
		string = "InscriptionsCoursSquashsCollectifs = \n";
		for(InscriptionCoursSquashCollectif a : inscriptions_cours_squashs_collectifs)
			string += a+"\n";
		return string;
	}
}
