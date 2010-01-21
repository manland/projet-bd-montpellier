package Coeur.Casier;

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

public class ReservationsCasiersSeances {
	private ArrayList<ReservationCasierSeance> reservations_casiers_seances;
	protected final EventListenerList listeners = new EventListenerList();
	
	public ReservationsCasiersSeances() throws SQLException
	{
		reservations_casiers_seances = new ArrayList<ReservationCasierSeance>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierSeance";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_casier = resultat.getInt("num_casier");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationCasierSeance reservation_casier_seance = new ReservationCasierSeance(num_casier, date_location, heure_debut, heure_fin, id_personne, prix_paye);
				reservations_casiers_seances.add(reservation_casier_seance);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsCasiersSeances(int jour, int mois, int annee) throws SQLException
	{
		mois = mois +1;
		reservations_casiers_seances = new ArrayList<ReservationCasierSeance>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierSeance WHERE to_char(date_location, 'DD')=? AND to_char(date_location, 'MM')=? AND to_char(date_location, 'YYYY')=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, jour);
			pstmt.setInt(2, mois);
			pstmt.setInt(3, annee);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_casier = resultat.getInt("num_casier");
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationCasierSeance reservation_casier_seance = new ReservationCasierSeance(num_casier, date_location, heure_debut, heure_fin, id_personne, prix_paye);
				reservations_casiers_seances.add(reservation_casier_seance);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsCasiersSeances(int num_casier, int jour, int mois, int annee) throws SQLException
	{
		mois = mois +1;
		reservations_casiers_seances = new ArrayList<ReservationCasierSeance>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierSeance WHERE to_char(date_location, 'DD')=? AND to_char(date_location, 'MM')=? AND to_char(date_location, 'YYYY')=? AND num_casier=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, jour);
			pstmt.setInt(2, mois);
			pstmt.setInt(3, annee);
			pstmt.setInt(4, num_casier);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				Date date_location = resultat.getDate("date_location");
				int heure_debut = resultat.getInt("heure_debut");
				int heure_fin = resultat.getInt("heure_fin");
				int id_personne = resultat.getInt("id_personne");
				int prix_paye = resultat.getInt("prix_paye");
				ReservationCasierSeance reservation_casier_seance = new ReservationCasierSeance(num_casier, date_location, heure_debut, heure_fin, id_personne, prix_paye);
				reservations_casiers_seances.add(reservation_casier_seance);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addReservationsCasiersSeancesListener(ReservationsCasiersSeancesListener listener) {
        listeners.add(ReservationsCasiersSeancesListener.class, listener);
    }
	
	public void removeReservationsCasiersSeancesListener(ReservationsCasiersSeancesListener listener){
		 listeners.remove(ReservationsCasiersSeancesListener.class, listener);
	}
	
	public ReservationsCasiersSeancesListener[] getReservationsCasiersSeancesListener()
	{
		return listeners.getListeners(ReservationsCasiersSeancesListener.class);
	}
	
	protected void fireAjouterReservationCasierSeance(ReservationCasierSeance reservation_casier_seance) {
        for(ReservationsCasiersSeancesListener listener : getReservationsCasiersSeancesListener()) {
            listener.ajouterReservationCasierSeance(reservation_casier_seance);
        }
    }
	
	protected void fireSupprimerReservationCasierAnnee(ReservationCasierSeance reservation_casier_seance) {
        for(ReservationsCasiersSeancesListener listener : getReservationsCasiersSeancesListener()) {
            listener.supprimerReservationCasierSeance(reservation_casier_seance);
        }
    }
	
	public void supprimerReservationCasierSeance(int num_casier, Date date_location, int heure_debut) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".reservationCasierSeance WHERE num_casier=? AND date_location=? AND heure_debut=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			pstmt.setDate(2, date_location);
			pstmt.setInt(3, heure_debut);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				ReservationCasierSeance reservation_casier_seance_a_supprimer=null;
				for(ReservationCasierSeance reservation_casier_seance : reservations_casiers_seances)
				{
					if(reservation_casier_seance.getNumCasier() == num_casier &&
							reservation_casier_seance.getDateLocation().compareTo(date_location) == 0 &&
							reservation_casier_seance.getHeureDebut() == heure_debut)
						reservation_casier_seance_a_supprimer = reservation_casier_seance;
				}
				reservations_casiers_seances.remove(reservation_casier_seance_a_supprimer);
				fireSupprimerReservationCasierAnnee(reservation_casier_seance_a_supprimer);
			}
			else
				throw new SQLException("ReservationCasierSeance non supprimé dans la Dabatase numero casier :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierReservationCasierSeance(int num_casier, Date date_location, int heure_debut, int heure_fin, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".reservationCasierSeance SET heure_fin=?, id_personne=?, prix_paye=?WHERE num_casier=? AND date_location=? AND heure_debut=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(4, num_casier);
			pstmt.setDate(5, date_location);
			pstmt.setInt(6, heure_debut);
			pstmt.setInt(1, heure_fin);
			pstmt.setInt(2, id_personne);
			pstmt.setInt(3, prix_paye);
			if(pstmt.executeUpdate()>0)
			{
				ReservationCasierSeance reservation_casier_seance_a_modifier = null;
				for(ReservationCasierSeance reservation_casier_seance : reservations_casiers_seances)
					if(reservation_casier_seance.getNumCasier() == num_casier &&
							reservation_casier_seance.getDateLocation().compareTo(date_location) == 0 &&
							reservation_casier_seance.getHeureDebut() == heure_debut)
						reservation_casier_seance_a_modifier = reservation_casier_seance;
				if(reservation_casier_seance_a_modifier != null)
				{
					reservations_casiers_seances.remove(reservation_casier_seance_a_modifier);
					fireSupprimerReservationCasierAnnee(reservation_casier_seance_a_modifier);

					reservation_casier_seance_a_modifier.setHeureFin(heure_fin);
					reservation_casier_seance_a_modifier.setIdPersonne(id_personne);
					reservation_casier_seance_a_modifier.setPrixPaye(prix_paye);
					reservations_casiers_seances.add(reservation_casier_seance_a_modifier);
					fireAjouterReservationCasierSeance(reservation_casier_seance_a_modifier);
				}		
			}
			else
				throw new SQLException("ReservationCasierSeance non modifié dans la Dabatase numero :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterReservationCasierSeance(int num_casier, Date date_location, int heure_debut, int heure_fin, int id_personne, int prix_paye) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".reservationCasierSeance VALUES(?,?,?,?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			pstmt.setDate(2, date_location);
			pstmt.setInt(3, heure_debut);
			pstmt.setInt(4, heure_fin);
			pstmt.setInt(5, id_personne);
			pstmt.setInt(6, prix_paye);
			if(pstmt.executeUpdate()> 0)
			{
				ReservationCasierSeance reservation_casier_seance = new ReservationCasierSeance(num_casier, date_location, heure_debut, heure_fin, id_personne, prix_paye);
				reservations_casiers_seances.add(reservation_casier_seance);
				fireAjouterReservationCasierSeance(reservation_casier_seance);
			}
			else
				throw new SQLException("ReservationCasierSeance non ajouté dans la Dabatase au numero :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}	
	public ArrayList<ReservationCasierSeance> getMois(int mois, int annee){
		ArrayList<ReservationCasierSeance> reservation_casier_seance = new ArrayList<ReservationCasierSeance>();
		Calendar date_a_verif = Calendar.getInstance();
		date_a_verif.set(annee, mois, 0);
		Calendar date = Calendar.getInstance();
		for(ReservationCasierSeance r : reservations_casiers_seances){
			date.setTime(r.getDateLocation());
			if(date.get(Calendar.MONTH) == date_a_verif.get(Calendar.MONTH) &&
					date.get(Calendar.YEAR) == date_a_verif.get(Calendar.YEAR))
				reservation_casier_seance.add(r);
		}
		return reservation_casier_seance;
	}

	public ArrayList<ReservationCasierSeance> getCasiers() {
		return reservations_casiers_seances;
	}

	public String toString() {
		String string;
		string = "ReservationsCasiersSeances = \n";
		for(ReservationCasierSeance a : reservations_casiers_seances)
			string += a+"\n";
		return string;
	}


}
