package Coeur.TerrainSquash;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class ReservationsTerrainsSquash {
	private ArrayList<ReservationTerrainSquash> reservations_terrains;
	protected final EventListenerList listeners = new EventListenerList();
	
	public ReservationsTerrainsSquash() throws SQLException
	{
		reservations_terrains = new ArrayList<ReservationTerrainSquash>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM reservationTerrainSquash";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_terrain = resultat.getInt("num_terrain_squash");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int minute_debut = resultat.getInt("minute_debut");
				int minute_fin = resultat.getInt("minute_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationTerrainSquash reservation_terrain = new ReservationTerrainSquash(num_terrain, date_location, heure_debut, heure_fin, minute_debut, minute_fin, id_personne, prix_paye);
				reservations_terrains.add(reservation_terrain);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsTerrainsSquash(int jour, int mois, int annee) throws SQLException
	{
		mois = mois +1;
		reservations_terrains = new ArrayList<ReservationTerrainSquash>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM reservationTerrainSquash WHERE to_char(date_location, 'DD')=? AND to_char(date_location, 'MM')=? AND to_char(date_location, 'YYYY')=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, jour);
			pstmt.setInt(2, mois);
			pstmt.setInt(3, annee);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_terrain = resultat.getInt("num_terrain_squash");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int minute_debut = resultat.getInt("minute_debut");
				int minute_fin = resultat.getInt("minute_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationTerrainSquash reservation_terrain = new ReservationTerrainSquash(num_terrain, date_location, heure_debut, heure_fin, minute_debut, minute_fin, id_personne, prix_paye);
				reservations_terrains.add(reservation_terrain);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsTerrainsSquash(int num_terrain_squash, int jour, int mois, int annee) throws SQLException
	{
		mois = mois +1;
		reservations_terrains = new ArrayList<ReservationTerrainSquash>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM reservationTerrainSquash WHERE to_char(date_location, 'DD')=? AND to_char(date_location, 'MM')=? AND to_char(date_location, 'YYYY')=? AND num_terrain_squash=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, jour);
			pstmt.setInt(2, mois);
			pstmt.setInt(3, annee);
			pstmt.setInt(4, num_terrain_squash);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_terrain = resultat.getInt("num_terrain_squash");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int minute_debut = resultat.getInt("minute_debut");
				int minute_fin = resultat.getInt("minute_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationTerrainSquash reservation_terrain = new ReservationTerrainSquash(num_terrain, date_location, heure_debut, heure_fin, minute_debut, minute_fin, id_personne, prix_paye);
				reservations_terrains.add(reservation_terrain);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addReservationsTerrainsListener(ReservationsTerrainsSquashListener listener) {
        listeners.add(ReservationsTerrainsSquashListener.class, listener);
    }
	
	public void removeReservationsTerrainsListener(ReservationsTerrainsSquashListener listener){
		 listeners.remove(ReservationsTerrainsSquashListener.class, listener);
	}
	
	public ReservationsTerrainsSquashListener[] getReservationsTerrainsListener()
	{
		return listeners.getListeners(ReservationsTerrainsSquashListener.class);
	}
	
	protected void fireAjouterReservationTerrainSquash(ReservationTerrainSquash reservation_terrain_seance) {
        for(ReservationsTerrainsSquashListener listener : getReservationsTerrainsListener()) {
            listener.ajouterReservationTerrainSquash(reservation_terrain_seance);
        }
    }
	
	protected void fireSupprimerReservationTerrainSquash(ReservationTerrainSquash reservation_terrain_seance) {
        for(ReservationsTerrainsSquashListener listener : getReservationsTerrainsListener()) {
            listener.supprimerReservationTerrainSquash(reservation_terrain_seance);
        }
    }
	
	public void supprimerReservationTerrainSquash(int num_terrain, Date date_location, int heure_debut, int heure_fin, int minute_debut, int minute_fin) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM reservationTerrainSquash WHERE num_terrain_squash=? AND date_location=? AND heure_debut=? AND heure_fin=? AND minute_debut=? AND minute_fin=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_terrain);
			pstmt.setDate(2, date_location);
			pstmt.setInt(3, heure_debut);
			pstmt.setInt(4, heure_fin);
			pstmt.setInt(5, minute_debut);
			pstmt.setInt(6, minute_fin);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				ReservationTerrainSquash reservation_terrain_seance_a_supprimer=null;
				for(ReservationTerrainSquash reservation_terrain_seance : reservations_terrains)
				{
					if(reservation_terrain_seance.getNumTerrainSquash() == num_terrain &&
							reservation_terrain_seance.getDateLocation().compareTo(date_location) == 0 &&
							reservation_terrain_seance.getHeureDebut() == heure_debut&&
							reservation_terrain_seance.getMinuteDebut() == minute_debut&&
							reservation_terrain_seance.getMinuteDebut() == heure_fin&&
							reservation_terrain_seance.getMinuteDebut() == minute_fin)
						reservation_terrain_seance_a_supprimer = reservation_terrain_seance;
				}
				reservations_terrains.remove(reservation_terrain_seance_a_supprimer);
				fireSupprimerReservationTerrainSquash(reservation_terrain_seance_a_supprimer);
			}
			else
				throw new SQLException("ReservationTerrainSquash non supprimé dans la Dabatase numero terrain :"+num_terrain+" le "+date_location.toString()+" de "+heure_debut+"h"+minute_debut+" a "+heure_fin+"h"+minute_fin);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierReservationTerrainSquash(int num_terrain, Date date_location, int heure_debut, int heure_fin, int minute_debut, int minute_fin, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE reservationTerrainSquash SET id_personne=?, prix_paye=? WHERE heure_fin=? AND num_terrain_squash=? AND date_location=? AND heure_debut=? AND minute_debut=? AND minute_fin=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(4, num_terrain);
			pstmt.setDate(5, date_location);
			pstmt.setInt(6, heure_debut);
			pstmt.setInt(3, heure_fin);
			pstmt.setInt(7, minute_debut);
			pstmt.setInt(8, minute_fin);
			pstmt.setInt(1, id_personne);
			pstmt.setInt(2, prix_paye);
			if(pstmt.executeUpdate()>0)
			{
				ReservationTerrainSquash reservation_terrain_seance_a_modifier = null;
				for(ReservationTerrainSquash reservation_terrain_seance : reservations_terrains)
					if(reservation_terrain_seance.getNumTerrainSquash() == num_terrain &&
							reservation_terrain_seance.getDateLocation().compareTo(date_location) == 0 &&
							reservation_terrain_seance.getHeureDebut() == heure_debut&&
							reservation_terrain_seance.getMinuteDebut() == minute_debut&&
							reservation_terrain_seance.getMinuteDebut() == heure_fin&&
							reservation_terrain_seance.getMinuteDebut() == minute_fin)
						reservation_terrain_seance_a_modifier = reservation_terrain_seance;
				if(reservation_terrain_seance_a_modifier != null)
				{
					reservations_terrains.remove(reservation_terrain_seance_a_modifier);
					fireSupprimerReservationTerrainSquash(reservation_terrain_seance_a_modifier);

					reservation_terrain_seance_a_modifier.setIdPersonne(id_personne);
					reservation_terrain_seance_a_modifier.setPrixPaye(prix_paye);
					reservations_terrains.add(reservation_terrain_seance_a_modifier);
					fireAjouterReservationTerrainSquash(reservation_terrain_seance_a_modifier);
				}		
			}
			else
				throw new SQLException("ReservationTerrainSquash non modifié dans la Dabatase numero :"+num_terrain+" le "+date_location.toString()+" de "+heure_debut+"h"+minute_debut+" a "+heure_fin+"h"+minute_fin);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterReservationTerrainSquash(int num_terrain, Date date_location, int heure_debut, int heure_fin, int minute_debut, int minute_fin, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO reservationTerrainSquash VALUES(?,?,?,?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_terrain);
			pstmt.setDate(2, date_location);
			pstmt.setInt(3, heure_debut);
			pstmt.setInt(4, heure_fin);
			pstmt.setInt(5, minute_debut);
			pstmt.setInt(6, minute_fin);
			pstmt.setInt(7, id_personne);
			pstmt.setInt(8, prix_paye);
			if(pstmt.executeUpdate()> 0)
			{
				ReservationTerrainSquash reservation_terrain_seance = new ReservationTerrainSquash(num_terrain, date_location, heure_debut, heure_fin, minute_debut, minute_fin, id_personne, prix_paye);
				reservations_terrains.add(reservation_terrain_seance);
				fireAjouterReservationTerrainSquash(reservation_terrain_seance);
			}
			else
				throw new SQLException("ReservationTerrainSquash non ajouté dans la Dabatase au numero :"+num_terrain+" le "+date_location.toString()+" de "+heure_debut+"h"+minute_debut+" a "+heure_fin+"h"+minute_fin);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public ArrayList<ReservationTerrainSquash> getMois(int mois, int annee){
		ArrayList<ReservationTerrainSquash> reservation_terrain_seance = new ArrayList<ReservationTerrainSquash>();
		Calendar date_a_verif = Calendar.getInstance();
		date_a_verif.set(annee, mois, 0);
		Calendar date = Calendar.getInstance();
		for(ReservationTerrainSquash r : reservations_terrains){
			date.setTime(r.getDateLocation());
			if(date.get(Calendar.MONTH) == date_a_verif.get(Calendar.MONTH) &&
					date.get(Calendar.YEAR) == date_a_verif.get(Calendar.YEAR))
				reservation_terrain_seance.add(r);
		}
		return reservation_terrain_seance;
	}

	public ArrayList<ReservationTerrainSquash> getTerrains() {
		return reservations_terrains;
	}

	public String toString() {
		String string;
		string = "ReservationsTerrainsSquash = \n";
		for(ReservationTerrainSquash a : reservations_terrains)
			string += a+"\n";
		return string;
	}


}
