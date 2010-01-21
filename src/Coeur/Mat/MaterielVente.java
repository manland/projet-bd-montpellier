package Coeur.Mat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class MaterielVente extends Materiel {
	
	private int quantite;
	
	public MaterielVente(String type_materiel) throws SQLException{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".materielVente WHERE type_materiel=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setString(1, type_materiel);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				setTypeMateriel(type_materiel);
				setPrixBase(resultat.getInt("prix_base"));
				setQuantite(resultat.getInt("quantite"));
				setDescription(resultat.getString("description"));
			}
			else
				throw new SQLException("Materiel en vente non trouvé dans la Dabatase type ::"+type_materiel);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
		
	}
	
	public MaterielVente(String type_materiel, String description, int quantite, int prix_base){
		super(type_materiel, prix_base, description);
		this.quantite = quantite;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "MaterielVente [quantite=" + quantite + "] ======> "+super.toString();
	}

}
