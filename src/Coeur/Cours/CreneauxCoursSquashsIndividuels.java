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

public class CreneauxCoursSquashsIndividuels {
	private ArrayList<CreneauCoursSquashIndividuel> creneaux_cours_squashs_individuels;
	protected final EventListenerList listeners = new EventListenerList();
	
	public CreneauxCoursSquashsIndividuels() throws SQLException
	{
		creneaux_cours_squashs_individuels = new ArrayList<CreneauCoursSquashIndividuel>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursSqIndividuel";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int num_terrain_squash = resultat.getInt("num_terrain_squash");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				Date date_cours = resultat.getDate("date_cours");
				CreneauCoursSquashIndividuel creneau_cours_squash_individuel = new CreneauCoursSquashIndividuel(id_cours, num_terrain_squash, date_cours, heure_debut, heure_fin);
				creneaux_cours_squashs_individuels.add(creneau_cours_squash_individuel);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public CreneauxCoursSquashsIndividuels(int jour_debut, int mois_debut, int jour_fin, int mois_fin, int annee) throws SQLException
	{
		mois_debut = mois_debut + 1;
		mois_fin = mois_fin + 1;
		creneaux_cours_squashs_individuels = new ArrayList<CreneauCoursSquashIndividuel>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql;
			if(mois_debut == mois_fin)
			{			
				sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursSqIndividuel WHERE to_char(date_cours, 'DD')>=? AND to_char(date_cours, 'DD')<=? AND to_char(date_cours, 'MM')=? AND to_char(date_cours, 'YYYY')=?";
				pstmt = c.prepareStatement(sql);
				pstmt.setInt(1, jour_debut);
				pstmt.setInt(2, jour_fin);
				pstmt.setInt(3, mois_debut);
				pstmt.setInt(4, annee);
				resultat = pstmt.executeQuery();
			}else
			{			
				sql = "SELECT * FROM "+SQL.getProprietaire()+".creneauCoursSqIndividuel WHERE ((to_char(date_cours, 'DD')>=? AND to_char(date_cours, 'MM')=?) OR (to_char(date_cours, 'DD')<=? AND to_char(date_cours, 'MM')=?)) AND to_char(date_cours, 'YYYY')=?";
				pstmt = c.prepareStatement(sql);
				pstmt.setInt(1, jour_debut);
				pstmt.setInt(2, mois_debut);
				pstmt.setInt(3, jour_fin);
				pstmt.setInt(4, mois_fin);
				pstmt.setInt(5, annee);
				resultat = pstmt.executeQuery();
			}
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int id_cours = resultat.getInt("id_cours");
				int num_terrain_squash = resultat.getInt("num_terrain_squash");
				Date date_cours = resultat.getDate("date_cours");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				CreneauCoursSquashIndividuel creneau_cours_squash = new CreneauCoursSquashIndividuel(id_cours, num_terrain_squash, date_cours, heure_debut, heure_fin);
				creneaux_cours_squashs_individuels.add(creneau_cours_squash);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addCreneauxCoursSquashsIndividuelsListener(CreneauxCoursSquashsIndividuelsListener listener) {
        listeners.add(CreneauxCoursSquashsIndividuelsListener.class, listener);
    }
	
	public void removeCreneauxCoursSquashsIndividuelsListener(CreneauxCoursSquashsIndividuelsListener listener){
		 listeners.remove(CreneauxCoursSquashsIndividuelsListener.class, listener);
	}
	
	public CreneauxCoursSquashsIndividuelsListener[] getCreneauxCoursSquashsIndividuelsListener()
	{
		return listeners.getListeners(CreneauxCoursSquashsIndividuelsListener.class);
	}
	
	protected void fireAjouterCreneauCoursSquashIndividuel(CreneauCoursSquashIndividuel creneau_cours_squash) {
        for(CreneauxCoursSquashsIndividuelsListener listener : getCreneauxCoursSquashsIndividuelsListener()) {
            listener.ajouterCreneauCoursSquashIndividuel(creneau_cours_squash);
        }
    }
	
	protected void fireSupprimerCreneauCoursSquashIndividuel(CreneauCoursSquashIndividuel creneau_cours_squash) {
        for(CreneauxCoursSquashsIndividuelsListener listener : getCreneauxCoursSquashsIndividuelsListener()) {
            listener.supprimerCreneauCoursSquashIndividuel(creneau_cours_squash);
        }
    }
	
	public void supprimerCreneauCoursSquashIndividuel(int num_terrain_squash) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".creneauCoursSqIndividuel WHERE id_cours=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_terrain_squash);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				CreneauCoursSquashIndividuel creneau_cours_squash_individuel_a_supprimer=null;
				for(CreneauCoursSquashIndividuel creneau_cours_squash_individuel : creneaux_cours_squashs_individuels)
					if(creneau_cours_squash_individuel.getNumTerrainSquash() == num_terrain_squash)
						creneau_cours_squash_individuel_a_supprimer = creneau_cours_squash_individuel;
				creneaux_cours_squashs_individuels.remove(creneau_cours_squash_individuel_a_supprimer);
				fireSupprimerCreneauCoursSquashIndividuel(creneau_cours_squash_individuel_a_supprimer);
			}
			else
				throw new SQLException("Creneau Cours Squash Individuel non supprimé dans la Dabatase terrain :"+num_terrain_squash);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierCreneauCoursSquashIndividuel(int id_cours, int num_terrain_squash, Date date_cours, int heure_debut, int heure_fin) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".creneauCoursSqIndividuel SET id_cours=?, date_cours=?, heure_debut=?, heure_fin=? WHERE num_terrain_squash=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(5, num_terrain_squash);
			pstmt.setDate(2, date_cours);
			pstmt.setInt(3, heure_debut);
			pstmt.setInt(4, heure_fin);
			if(pstmt.executeUpdate()>0)
			{
				CreneauCoursSquashIndividuel creneau_cours_squash_individuel_a_modifier = null;
				for(CreneauCoursSquashIndividuel creneau_cours_squash_individuel : creneaux_cours_squashs_individuels)
					if(creneau_cours_squash_individuel.getNumTerrainSquash() == num_terrain_squash)
						creneau_cours_squash_individuel_a_modifier = creneau_cours_squash_individuel;
				if(creneau_cours_squash_individuel_a_modifier != null)
				{
					creneaux_cours_squashs_individuels.remove(creneau_cours_squash_individuel_a_modifier);
					fireSupprimerCreneauCoursSquashIndividuel(creneau_cours_squash_individuel_a_modifier);
					
					creneau_cours_squash_individuel_a_modifier.setHeureDebut(heure_debut);
					creneau_cours_squash_individuel_a_modifier.setHeureFin(heure_debut);
					creneau_cours_squash_individuel_a_modifier.setDateCours(date_cours);
					creneau_cours_squash_individuel_a_modifier.setIdCours(id_cours);
					creneaux_cours_squashs_individuels.add(creneau_cours_squash_individuel_a_modifier);
					fireAjouterCreneauCoursSquashIndividuel(creneau_cours_squash_individuel_a_modifier);
				}		
			}
			else
				throw new SQLException("Creneau Cours Squash Individuel non modifié dans la Dabatase terraib :"+num_terrain_squash);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterCreneauCoursSquashIndividuel(int id_cours, int num_terrain_squash, Date date_cours, int heure_debut, int heure_fin) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".creneauCoursSqIndividuel VALUES(?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_cours);
			pstmt.setInt(2, num_terrain_squash);
			pstmt.setDate(3, date_cours);
			pstmt.setInt(4, heure_debut);
			pstmt.setInt(5, heure_fin);
			if(pstmt.executeUpdate()> 0)
			{
				CreneauCoursSquashIndividuel creneau_cours_squash_collectif = new CreneauCoursSquashIndividuel(id_cours, num_terrain_squash, date_cours, heure_debut, heure_fin);
				creneaux_cours_squashs_individuels.add(creneau_cours_squash_collectif);
				fireAjouterCreneauCoursSquashIndividuel(creneau_cours_squash_collectif);
			}
			else
				throw new SQLException("Creneau Cours Squash Individuel non ajouté dans la Dabatase terrain :"+num_terrain_squash);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<CreneauCoursSquashIndividuel> getCoursSquashs() {
		return creneaux_cours_squashs_individuels;
	}

	public String toString() {
		String string;
		string = "CreneauxCoursSquashsIndividuels = \n";
		for(CreneauCoursSquashIndividuel a : creneaux_cours_squashs_individuels)
			string += a+"\n";
		return string;
	}
}
