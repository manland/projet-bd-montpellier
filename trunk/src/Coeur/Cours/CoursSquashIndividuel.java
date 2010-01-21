package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class CoursSquashIndividuel extends CoursSquash {
	
	private int num_adherent;

	public CoursSquashIndividuel(int id_cours) throws SQLException {
		super(id_cours, "individuel");
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquashIndividuel WHERE id_cours=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_cours);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				num_adherent = resultat.getInt("num_adherent");
			}
			else
				throw new SQLException("coursSquashIndividuel non trouvé dans la Dabatase identifiant :"+id_cours);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
		// TODO Auto-generated constructor stub
	}

	public CoursSquashIndividuel(int id_cours, int num_adherent) throws SQLException {
		super(id_cours, "individuel");
		this.num_adherent = num_adherent;
		// TODO Auto-generated constructor stub
	}

	public CoursSquashIndividuel(int id_cours, int id_prof, int num_adherent) {
		super(id_cours, id_prof, "individuel");
		this.num_adherent = num_adherent;
		// TODO Auto-generated constructor stub
	}

	public int getNumAdherent(){
		return this.num_adherent;
	}

	@Override
	public String toString() {
		return "CoursSquashIndividuel [num_adherent=" + num_adherent + "] =====>"+super.toString();
	}

	public void setNumAdherent(int num_adherent) {
		// TODO Auto-generated method stub
		this.num_adherent = num_adherent;
	}

}
