package Coeur.Casier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;
public class ReservationsCasiersAnnees {
	private ArrayList<ReservationCasierAnnee> reservations_casiers_annees;
	protected final EventListenerList listeners = new EventListenerList();
	
	public ReservationsCasiersAnnees() throws SQLException
	{
		reservations_casiers_annees = new ArrayList<ReservationCasierAnnee>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierAnnee";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat = statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				int num_casier = resultat.getInt("num_casier");
				int num_adherent = resultat.getInt("num_adherent");
				int annee = resultat.getInt("annee");
				ReservationCasierAnnee reservation_casier_annee = new ReservationCasierAnnee(num_casier, num_adherent,annee);
				reservations_casiers_annees.add(reservation_casier_annee);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsCasiersAnnees(int annee) throws SQLException
	{
		reservations_casiers_annees = new ArrayList<ReservationCasierAnnee>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierAnnee WHERE annee=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, annee);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_casier = resultat.getInt("num_casier");
				int num_adherent = resultat.getInt("num_adherent");
				ReservationCasierAnnee reservation_casier_annee = new ReservationCasierAnnee(num_casier, num_adherent,annee);
				reservations_casiers_annees.add(reservation_casier_annee);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public ReservationsCasiersAnnees(int num_casier, int annee) throws SQLException
	{
		reservations_casiers_annees = new ArrayList<ReservationCasierAnnee>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".reservationCasierAnnee WHERE annee=? AND num_casier=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, annee);
			pstmt.setInt(2, num_casier);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				int num_adherent = resultat.getInt("num_adherent");
				ReservationCasierAnnee reservation_casier_annee = new ReservationCasierAnnee(num_casier, num_adherent,annee);
				reservations_casiers_annees.add(reservation_casier_annee);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public void addReservationsCasiersAnneesListener(ReservationsCasiersAnneesListener listener) {
        listeners.add(ReservationsCasiersAnneesListener.class, listener);
    }
	
	public void removeReservationsCasiersAnneesListener(ReservationsCasiersAnneesListener listener){
		 listeners.remove(ReservationsCasiersAnneesListener.class, listener);
	}
	
	public ReservationsCasiersAnneesListener[] getReservationsCasiersAnneesListener()
	{
		return listeners.getListeners(ReservationsCasiersAnneesListener.class);
	}
	
	protected void fireAjouterReservationCasierAnnee(ReservationCasierAnnee reservation_casier_annee) {
        for(ReservationsCasiersAnneesListener listener : getReservationsCasiersAnneesListener()) {
            listener.ajouterReservationCasierAnnee(reservation_casier_annee);
        }
    }
	
	protected void fireSupprimerReservationCasierAnnee(ReservationCasierAnnee reservation_casier_annee) {
        for(ReservationsCasiersAnneesListener listener : getReservationsCasiersAnneesListener()) {
            listener.supprimerReservationCasierAnnee(reservation_casier_annee);
        }
    }
	
	public void supprimerReservationCasierAnnee(int num_casier, int annee) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "DELETE FROM "+SQL.getProprietaire()+".reservationCasierAnnee WHERE num_casier=? AND annee=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			pstmt.setInt(2, annee);
			if(pstmt.executeUpdate() > 0) //si suppression
			{
				ReservationCasierAnnee reservation_casier_annee_a_supprimer=null;
				for(ReservationCasierAnnee reservation_casier_annee : reservations_casiers_annees)
					if(reservation_casier_annee.getNumCasier() == num_casier &&
							reservation_casier_annee.getAnnee() == annee)
						reservation_casier_annee_a_supprimer = reservation_casier_annee;
				reservations_casiers_annees.remove(reservation_casier_annee_a_supprimer);
				fireSupprimerReservationCasierAnnee(reservation_casier_annee_a_supprimer);
			}
			else
				throw new SQLException("ReservationCasierAnnee non supprimé dans la Dabatase numero casier :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void modifierReservationCasierAnnee(int num_casier, int num_adherent, int annee) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql ="UPDATE "+SQL.getProprietaire()+".reservationCasierAnnee SET num_adherent=? WHERE num_casier=? AND annee=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(2, num_casier);
			pstmt.setInt(1, num_adherent);
			pstmt.setInt(3, annee);
			if(pstmt.executeUpdate()>0)
			{
				ReservationCasierAnnee reservation_casier_annee_a_modifier = null;
				for(ReservationCasierAnnee reservation_casier_annee : reservations_casiers_annees)
					if(reservation_casier_annee.getNumCasier() == num_casier &&
							reservation_casier_annee.getAnnee() == annee)
						reservation_casier_annee_a_modifier = reservation_casier_annee;
				if(reservation_casier_annee_a_modifier != null)
				{
					reservations_casiers_annees.remove(reservation_casier_annee_a_modifier);
					fireSupprimerReservationCasierAnnee(reservation_casier_annee_a_modifier);
					reservation_casier_annee_a_modifier.setNumAdherent(num_adherent);
					reservations_casiers_annees.add(reservation_casier_annee_a_modifier);
					fireAjouterReservationCasierAnnee(reservation_casier_annee_a_modifier);
				}		
			}
			else
				throw new SQLException("ReservationCasierAnnee non modifié dans la Dabatase numero :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}
	
	public void ajouterReservationCasierAnnee(int num_casier,int num_adherent, int annee) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		try{
			String sql = "INSERT INTO "+SQL.getProprietaire()+".reservationCasierAnnee VALUES(?,?,?)";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_casier);
			pstmt.setInt(2, num_adherent);
			pstmt.setInt(3, annee);
			System.out.println(num_casier);
			if(pstmt.executeUpdate()> 0)
			{
				ReservationCasierAnnee reservation_casier_annee = new ReservationCasierAnnee(num_casier, num_adherent, annee);
				reservations_casiers_annees.add(reservation_casier_annee);
				fireAjouterReservationCasierAnnee(reservation_casier_annee);
			}
			else
				throw new SQLException("ReservationCasierAnnee non ajouté dans la Dabatase au numero :"+num_casier);
			c.commit();
		}
		finally{
			if(pstmt != null)
				pstmt.close();
		}
	}

	public ArrayList<ReservationCasierAnnee> getCasiers() {
		return reservations_casiers_annees;
	}

	public String toString() {
		String string;
		string = "ReservationsCasiersAnnees = \n";
		for(ReservationCasierAnnee a : reservations_casiers_annees)
			string += a+"\n";
		return string;
	}


}
