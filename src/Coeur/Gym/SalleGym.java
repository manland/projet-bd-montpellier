package Coeur.Gym;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class SalleGym {
	private int id_salle;
	private int quantite;
	
	public SalleGym(int id_salle) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try
		{	
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".salleGym WHERE id_salle=?";
			pstmt = c.prepareStatement(sql);
			pstmt.setInt(1, id_salle);
			resultat = pstmt.executeQuery();
			
			while(resultat.next()) //nombre d'element
			{
				this.id_salle = id_salle;
				this.quantite = resultat.getInt("capacite");
			}
		}
		finally {
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();	
		}
	}
	
	public SalleGym(int id_salle, int quantite){
		this.id_salle = id_salle;
		this.quantite = quantite;
	}

	public int getIdSalle() {
		return id_salle;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "SalleGym [id_salle=" + id_salle + ", quantite=" + quantite
				+ "]";
	}

}
