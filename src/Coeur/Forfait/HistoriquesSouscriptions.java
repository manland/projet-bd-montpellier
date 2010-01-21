package Coeur.Forfait;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class HistoriquesSouscriptions {
	private ArrayList<Souscription> historiques_souscriptions;
	protected final EventListenerList listeners = new EventListenerList();
	
	public HistoriquesSouscriptions() throws SQLException
	{
		historiques_souscriptions = new ArrayList<Souscription>();
		Connection c = SQL.getConnexion();
		Statement statement = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".HistoriqueSouscription";
			statement = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			resultat =  statement.executeQuery(sql);
			
			while(resultat.next()) //nombre d'element
			{
				String type_forfait = resultat.getString("type_forfait");
				int num_adherent = resultat.getInt("num_adherent");
				Date date_debut = resultat.getDate("date_debut");
				Date date_fin = resultat.getDate("date_fin");
				Souscription forfait = new Souscription(type_forfait, num_adherent, date_debut, date_fin);
				historiques_souscriptions.add(forfait);
			}
		}
		finally {
			if(statement != null)
				statement.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
	public HistoriquesSouscriptions(int num_adherent) throws SQLException
	{
		historiques_souscriptions = new ArrayList<Souscription>();
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".HistoriqueSouscription WHERE num_adherent=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, num_adherent);
			resultat = pstmt.executeQuery();
			
			while(resultat.next())
			{
				String type_forfait = resultat.getString("type_forfait");
				Date date_debut = resultat.getDate("date_debut");
				Date date_fin = resultat.getDate("date_fin");
				Souscription forfait = new Souscription(type_forfait, num_adherent, date_debut, date_fin);
				historiques_souscriptions.add(forfait);
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}	
	}
	
//	public void addSouscriptionsListener(SouscriptionsListener listener) {
//        listeners.add(SouscriptionsListener.class, listener);
//    }
//	
//	public void removeSouscriptionsListener(SouscriptionsListener listener){
//		 listeners.remove(SouscriptionsListener.class, listener);
//	}
//	
//	public SouscriptionsListener[] getSouscriptionsListener()
//	{
//		return listeners.getListeners(SouscriptionsListener.class);
//	}
//	
//	protected void fireAjouterSouscription(Souscription souscription) {
//        for(SouscriptionsListener listener : getSouscriptionsListener()) {
//            listener.ajouterSouscription(souscription);
//        }
//    }
//	
//	protected void fireSupprimerSouscription(Souscription souscription) {
//        for(SouscriptionsListener listener : getSouscriptionsListener()) {
//            listener.supprimerSouscription(souscription);
//        }
//    }
	
//	public void supprimerSouscription(String type_forfait, int num_adherent, Date date_debut, Date date_fin) throws SQLException
//	{
//		Connection c = SQL.getConnexion();
//		PreparedStatement pstmt = null;
//		try{
//			String sql = "DELETE FROM "+SQL.getProprietaire()+".souscription WHERE type_forfait=? AND num_adherent=? AND date_debut=? AND date_fin=?";
//			pstmt = c.prepareStatement(sql);
//			pstmt.setString(1, type_forfait);
//			pstmt.setInt(2, num_adherent);
//			pstmt.setDate(3, date_debut);
//			pstmt.setDate(4, date_fin);
//			if(pstmt.executeUpdate() > 0) //si suppression
//			{
//				Souscription souscription_a_supprimer=null;
//				for(Souscription souscription : souscriptions)
//				{if(souscription.getTypeForfait().equals(type_forfait) && 
//							souscription.getNumAdherent() == num_adherent &&
//							souscription.getDateDebut().compareTo(date_debut) == 0 &&
//							souscription.getDateFin().compareTo(date_fin) == 0)
//					{
//						souscription_a_supprimer = souscription;
//						System.out.println("ici");
//					}
//				}
//				souscriptions.remove(souscription_a_supprimer);
//				System.out.println(souscription_a_supprimer.getNumAdherent());
//				fireSupprimerSouscription(souscription_a_supprimer);
//			}
//			else
//				throw new SQLException("Souscription non supprimé dans la Dabatase type_forfait:"+type_forfait+" pour l'adherent "+num_adherent);
//			c.commit();
//		}
//		finally{
//			if(pstmt != null)
//				pstmt.close();
//		}
//	}
//	
//	public void ajouterSouscription(String type_forfait, int num_adherent, Date date_debut, Date date_fin) throws SQLException
//	{
//		Connection c = SQL.getConnexion();
//		PreparedStatement pstmt = null;
//		try{
//			String sql = "INSERT INTO "+SQL.getProprietaire()+".souscription VALUES(?,?,?,?)";
//			pstmt = c.prepareStatement(sql);
//			pstmt.setDate(3, date_debut);
//			pstmt.setDate(4, date_fin);
//			pstmt.setString(1, type_forfait);
//			pstmt.setInt(2, num_adherent);
//			if(pstmt.executeUpdate()> 0)
//			{
//				Souscription souscription = new Souscription(type_forfait, num_adherent, date_debut, date_fin);
//				souscriptions.add(souscription);
//				fireAjouterSouscription(souscription);
//			}
//			else
//				throw new SQLException("Souscription non ajouté dans la Dabatase au type_forfait:"+type_forfait+" pour l'adherent "+num_adherent);
//			c.commit();
//		}
//		finally{
//			if(pstmt != null)
//				pstmt.close();
//		}
//	}
	
	public ArrayList<Souscription> getSouscriptions() {
		return historiques_souscriptions;
	}

	public String toString() {
		String string;
		string = "HistoriquesSouscriptions = \n";
		for(Souscription a : historiques_souscriptions)
			string += a+"\n";
		return string;
	}

}
