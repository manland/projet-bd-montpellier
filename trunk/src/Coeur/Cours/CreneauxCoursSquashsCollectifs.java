package Coeur.Cours;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class CreneauxCoursSquashsCollectifs {
	private ArrayList<CreneauCoursSquashCollectif> creneaux_cours_squashs_collectifs;
	protected final EventListenerList listeners = new EventListenerList();
	
	public CreneauxCoursSquashsCollectifs() throws SQLException
	{
		creneaux_cours_squashs_collectifs = new ArrayList<CreneauCoursSquashCollectif>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursSqColl";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int num_terrain_squash = resultat.getInt("num_terrain_squash");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int jour = resultat.getInt("jour");
				Date debut_trimestre = resultat.getDate("debut_trimestre");
				CreneauCoursSquashCollectif creneau_cours_squash = new CreneauCoursSquashCollectif(id_cours, num_terrain_squash, jour, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_squashs_collectifs.add(creneau_cours_squash);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public CreneauxCoursSquashsCollectifs(Date debut_trimestre) throws SQLException
	{
		creneaux_cours_squashs_collectifs = new ArrayList<CreneauCoursSquashCollectif>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql;
			sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursSqColl WHERE debut_trimestre=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setDate(1, debut_trimestre);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int jour = resultat.getInt("jour");
				int id_cours = resultat.getInt("id_cours");
				int num_terrain_squash = resultat.getInt("num_terrain_squash");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				CreneauCoursSquashCollectif creneau_cours_squash = new CreneauCoursSquashCollectif(id_cours, num_terrain_squash, jour, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_squashs_collectifs.add(creneau_cours_squash);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCreneauxCoursSquashsCollectifsListener(CreneauxCoursSquashsCollectifsListener listener) {
        listeners.add(CreneauxCoursSquashsCollectifsListener.class, listener);
    }
	
	public void removeCreneauxCoursSquashsCollectifsListener(CreneauxCoursSquashsCollectifsListener listener){
		 listeners.remove(CreneauxCoursSquashsCollectifsListener.class, listener);
	}
	
	public CreneauxCoursSquashsCollectifsListener[] getCreneauxCoursSquashsCollectifsListener()
	{
		return listeners.getListeners(CreneauxCoursSquashsCollectifsListener.class);
	}
	
	protected void fireAjouterCreneauCoursSquashCollectif(CreneauCoursSquashCollectif creneau_cours_squash) {
        for(CreneauxCoursSquashsCollectifsListener listener : getCreneauxCoursSquashsCollectifsListener()) {
            listener.ajouterCreneauCoursSquashCollectif(creneau_cours_squash);
        }
    }
	
	protected void fireSupprimerCreneauCoursSquashCollectif(CreneauCoursSquashCollectif creneau_cours_squash) {
        for(CreneauxCoursSquashsCollectifsListener listener : getCreneauxCoursSquashsCollectifsListener()) {
            listener.supprimerCreneauCoursSquashCollectif(creneau_cours_squash);
        }
    }
	
	public void supprimerCreneauCoursSquashCollectif(int id_cours) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".creneauCoursSqColl WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CreneauCoursSquashCollectif creneau_cours_squash_collectif_a_supprimer=null;
				for(CreneauCoursSquashCollectif creneau_cours_squash_collectif : creneaux_cours_squashs_collectifs)
					if(creneau_cours_squash_collectif.getIdCours() == id_cours)
						creneau_cours_squash_collectif_a_supprimer = creneau_cours_squash_collectif;
				creneaux_cours_squashs_collectifs.remove(creneau_cours_squash_collectif_a_supprimer);
				fireSupprimerCreneauCoursSquashCollectif(creneau_cours_squash_collectif_a_supprimer);
			}
			else
				throw new SQLException("Creneau Cours Squash non supprimé dans la Dabatase cours :"+id_cours);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCreneauCoursSquashCollectif(int id_cours, int num_terrain_squash, int jour, int heure_debut, int heure_fin, Date debut_trimestre) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".creneauCoursSqColl SET jour=?, num_terrain_squash=?, heure_debut=?, heure_fin=?, debut_trimestre=? WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(6, id_cours);
			pstmt.setInt(2, num_terrain_squash);
			pstmt.setDate(5, debut_trimestre);
			pstmt.setInt(3, heure_debut);
			pstmt.setInt(4, heure_fin);
			pstmt.setInt(1, jour);
			if(pstmt.executeUpdate()>0)
			{
				CreneauCoursSquashCollectif creneau_cours_squash_collectif_a_modifier = null;
				for(CreneauCoursSquashCollectif creneau_cours_squash : creneaux_cours_squashs_collectifs)
					if(creneau_cours_squash_collectif_a_modifier.getIdCours() == id_cours)
						creneau_cours_squash_collectif_a_modifier = creneau_cours_squash;
				if(creneau_cours_squash_collectif_a_modifier != null)
				{
					creneaux_cours_squashs_collectifs.remove(creneau_cours_squash_collectif_a_modifier);
					fireSupprimerCreneauCoursSquashCollectif(creneau_cours_squash_collectif_a_modifier);
					
					creneau_cours_squash_collectif_a_modifier.setHeureDebut(heure_debut);
					creneau_cours_squash_collectif_a_modifier.setHeureFin(heure_debut);
					creneau_cours_squash_collectif_a_modifier.setJour(jour);
					creneau_cours_squash_collectif_a_modifier.setNumTerrainSquash(num_terrain_squash);
					creneau_cours_squash_collectif_a_modifier.setDebutTrimestre(debut_trimestre);
					creneaux_cours_squashs_collectifs.add(creneau_cours_squash_collectif_a_modifier);
					fireAjouterCreneauCoursSquashCollectif(creneau_cours_squash_collectif_a_modifier);
				}		
			}
			else
				throw new SQLException("Creneau Cours Squash non modifié dans la Dabatase cours :"+id_cours+" le "+debut_trimestre);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCreneauCoursSquashCollectif(int id_cours, int num_terrain_squash, int jour, int heure_debut, int heure_fin, Date debut_trimestre) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".creneauCoursSqColl VALUES(?,?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, num_terrain_squash);
			pstmt.setInt(3, jour);
			pstmt.setInt(4, heure_debut);
			pstmt.setInt(5, heure_fin);
			pstmt.setDate(6, debut_trimestre);
			if(pstmt.executeUpdate()> 0)
			{
				CreneauCoursSquashCollectif creneau_cours_squash_collectif = new CreneauCoursSquashCollectif(id_cours, num_terrain_squash, jour, heure_debut, heure_fin, debut_trimestre);
				creneaux_cours_squashs_collectifs.add(creneau_cours_squash_collectif);
				fireAjouterCreneauCoursSquashCollectif(creneau_cours_squash_collectif);
			}
			else
				throw new SQLException("Creneau Cours Squash non ajouté dans la Dabatase cours :"+id_cours+" le "+debut_trimestre);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<CreneauCoursSquashCollectif> getCoursSquashs() {
		return creneaux_cours_squashs_collectifs;
	}

	public String toString() {
		String string;
		string = "CreneauxCoursSquashsCollectifs = \n";
		for(CreneauCoursSquashCollectif a : creneaux_cours_squashs_collectifs)
			string += a+"\n";
		return string;
	}
}
