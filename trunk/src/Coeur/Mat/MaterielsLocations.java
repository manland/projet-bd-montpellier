package Coeur.Mat;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class MaterielsLocations {
	private ArrayList<MaterielLocation> materiels_locations;
	protected final EventListenerList listeners = new EventListenerList();
	
	public MaterielsLocations() throws SQLException
	{
		materiels_locations = new ArrayList<MaterielLocation>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".materielLocation";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				String type_materiel = resultat.getString("type_materiel");
				int prix_base = resultat.getInt("prix_base");
				int num_materiel = resultat.getInt("num_materiel");
				Date date_achat = resultat.getDate("date_achat");
				String etat = resultat.getString("etat");
				String description = resultat.getString("description");
				MaterielLocation materiel_location = new MaterielLocation(num_materiel, type_materiel, date_achat, etat, description, prix_base);
				materiels_locations.add(materiel_location);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addMaterielsLocationsListener(MaterielsLocationsListener listener) {
        listeners.add(MaterielsLocationsListener.class, listener);
    }
	
	public void removeMaterielsLocationsListener(MaterielsLocationsListener listener){
		 listeners.remove(MaterielsLocationsListener.class, listener);
	}
	
	public MaterielsLocationsListener[] getMaterielsLocationsListener()
	{
		return listeners.getListeners(MaterielsLocationsListener.class);
	}
	
	protected void fireAjouterMaterielLocation(MaterielLocation materiel_location) {
        for(MaterielsLocationsListener listener : getMaterielsLocationsListener()) {
            listener.ajouterMaterielLocation(materiel_location);
        }
    }
	
	protected void fireSupprimerMaterielLocation(MaterielLocation materiel_location) {
        for(MaterielsLocationsListener listener : getMaterielsLocationsListener()) {
            listener.supprimerMaterielLocation(materiel_location);
        }
    }
	
	public void supprimerMaterielLocation(int num_materiel) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM materielLocation WHERE num_materiel=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_materiel);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				MaterielLocation materiel_location_a_supprimer=null;
				for(MaterielLocation materiel_location : materiels_locations)
					if(materiel_location.getNumMateriel() == num_materiel)
						materiel_location_a_supprimer = materiel_location;
				materiels_locations.remove(materiel_location_a_supprimer);
				fireSupprimerMaterielLocation(materiel_location_a_supprimer);
			}
			else
				throw new SQLException("MaterielLocation non supprimé dans la Dabatase num_materiel:"+num_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierMaterielLocation(int num_materiel, String type_materiel, Date date_achat, String etat, String description, int prix_base) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".materielLocation SET type_materiel=?,prix_base=?, etat=?, date_achat=?, description=? WHERE num_materiel=? ";
			pstmt = c.prepareStatement(sql);
			pstmt.setString(1, type_materiel);
			pstmt.setInt(2, prix_base);
			pstmt.setString(3, etat);
			pstmt.setDate(4, date_achat);
			pstmt.setString(5, description);
			pstmt.setInt(6, num_materiel);
			if(pstmt.executeUpdate()>0)
			{
				MaterielLocation materiel_location_a_modifier = null;
				for(MaterielLocation materiel_location : materiels_locations)
					if(materiel_location.getNumMateriel() == num_materiel)
						materiel_location_a_modifier = materiel_location;
				if(materiel_location_a_modifier != null)
				{
					materiels_locations.remove(materiel_location_a_modifier);
					fireSupprimerMaterielLocation(materiel_location_a_modifier);
					materiel_location_a_modifier.setEtat(etat);
					materiel_location_a_modifier.setTypeMateriel(type_materiel);
					materiel_location_a_modifier.setDateAchat(date_achat);
					materiel_location_a_modifier.setPrixBase(prix_base);
					materiel_location_a_modifier.setDescription(description);
					materiels_locations.add(materiel_location_a_modifier);
					fireAjouterMaterielLocation(materiel_location_a_modifier);
				}		
			}
			else
				throw new SQLException("MaterielLocation non modifié dans la Dabatase num_materiel :"+num_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterMaterielLocation(String type_materiel, Date date_achat, String etat, String description, int prix_base) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			int num_materiel = maxMaterielsLocations()+1;
			String sql = "INSERT INTO "+SQL.getProprietaire()+".materielLocation VALUES(?,?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_materiel);
			pstmt.setString(2, type_materiel);
			pstmt.setDate(3, date_achat);
			pstmt.setString(4, etat);
			pstmt.setString(5, description);
			pstmt.setInt(6, prix_base);
			if(pstmt.executeUpdate()> 0)
			{
				MaterielLocation materiel_location = new MaterielLocation(num_materiel, type_materiel, date_achat, etat, description, prix_base);
				materiels_locations.add(materiel_location);
				fireAjouterMaterielLocation(materiel_location);
			}
			else
				throw new SQLException("MaterielLocation non ajouté dans la Dabatase au num_materiel:"+num_materiel);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	public ArrayList<MaterielLocation> getMaterielsLocations() {
		return materiels_locations;
	}
	
	public int maxMaterielsLocations(){
		int maximum =0;
		for(MaterielLocation l : materiels_locations)
		{
			if(l.getNumMateriel() > maximum)
				maximum = l.getNumMateriel();
		}
		return maximum;
	}

	public String toString() {
		String string;
		string = "MaterielsLocations = \n";
		for(MaterielLocation a : materiels_locations)
			string += a+"\n";
		return string;
	}


}
