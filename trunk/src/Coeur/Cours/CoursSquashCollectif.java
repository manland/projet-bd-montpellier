package Coeur.Cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Coeur.SQL;

public class CoursSquashCollectif extends CoursSquash{
	
	private int nb_personne;

	public CoursSquashCollectif(int id_cours) throws SQLException {
		super(id_cours, "collectif");
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".coursSquashCollectif WHERE id_cours=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_cours);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouvé un utilisateur
			{
				resultat.first();
				nb_personne = resultat.getInt("nb_personne");
			}
			else
				throw new SQLException("coursSquashCollectif non trouvé dans la Dabatase identifiant :"+id_cours);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
		// TODO Auto-generated constructor stub
	}

	public CoursSquashCollectif(int id_cours, int nb_personne) throws SQLException {
		super(id_cours, "collectif");
		this.nb_personne= nb_personne;
		// TODO Auto-generated constructor stub
	}

	public CoursSquashCollectif(int id_cours, int id_prof, int nb_personne) {
		super(id_cours, id_prof, "collectif");
		this.nb_personne= nb_personne;
		// TODO Auto-generated constructor stub
	}
	
	public void setNbPersonne(int nb_personne){
		this.nb_personne = nb_personne;
	}
	
	public int getNbPersonne(){
		return this.nb_personne;
	}

	@Override
	public String toString() {
		return "CoursSquashCollectif [nb_personne=" + nb_personne+ "] =====>"+super.toString();
	}

}
