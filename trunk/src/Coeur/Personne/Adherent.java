package Coeur.Personne;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class Adherent extends Personne {
	
	private int num_adherent;
	
	public Adherent (int id_personne) throws SQLException{
		super(id_personne);	
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".adherent WHERE id_personne=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_personne);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				this.num_adherent = resultat.getInt("num_adherent");
			}
			else
				throw new SQLException("Adherent (via personne) non trouvé dans la Dabatase identifiant :"+id_personne);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
	}
	
	public Adherent(int num_adherent, int id_personne) throws SQLException
	{
		Personne personne = null;
		if(id_personne != -1){
			personne = new Personne(id_personne);
		}
		else
		{
			Connection c = SQL.getConnexion();
			PreparedStatement pstmt = null;
			ResultSet resultat = null;
			try{
				String sql = "SELECT * FROM "+SQL.getProprietaire()+".adherent WHERE num_adherent=?";
				pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				pstmt.setInt(1, num_adherent);
				resultat = pstmt.executeQuery();
				resultat.last();
				if(resultat.getRow() == 1) // on a trouvé un utilisateur
				{
					resultat.first();
					personne = new Personne(resultat.getInt("id_personne"));
				}
				else
					throw new SQLException("Adherent (via personne) non trouvé dans la Dabatase identifiant :"+id_personne);
			}
			finally{
				if(pstmt != null)
					pstmt.close();
				if(resultat != null)
					resultat.close();
			}
		}
		if(personne != null)
		{
			this.id_personne = personne.id_personne;
			this.nom = personne.nom;
			this.prenom = personne.prenom;
			this.date = personne.date;
			this.adresse = personne.adresse;	
			this.num_adherent = num_adherent;
		}
	}
	
	public void addAdherentListener(AdherentListener listener) {
        listeners.add(AdherentListener.class, listener);
        super.addPersonneListener(listener);
    }
	
	public void removeAdherentListener(AdherentListener listener){
		listeners.remove(AdherentListener.class, listener);
	    super.removePersonneListener(listener);
	}

	public AdherentListener[] getAdherentListener()
	{
		return listeners.getListeners(AdherentListener.class);
	}

	public int getNumAdherent() {
		return num_adherent;
	}

	@Override
	public String toString() {
		return "Adherent [num_adherent=" + num_adherent + "] ====>"+super.toString();
	}

}
