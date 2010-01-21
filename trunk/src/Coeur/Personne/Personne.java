package Coeur.Personne;
import java.sql.*;

import javax.swing.event.EventListenerList;

import Coeur.SQL;

public class Personne {
	
	protected int id_personne;
	protected String nom;
	protected String prenom;
	protected Date date;
	protected String adresse;
	
	protected final EventListenerList listeners = new EventListenerList();
	
	public Personne()
	{
		this.id_personne = 0;
		this.nom = "";
		this.prenom = "";
		this.date = null;
		this.adresse = "";	
	}
	
	public Personne(int id_personne) throws SQLException
	{
		Connection c = SQL.getConnexion();
		PreparedStatement pstmt = null;
		ResultSet resultat = null;
		try{
			String sql = "SELECT * FROM "+SQL.getProprietaire()+".personne WHERE id_personne=?";
			pstmt = c.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, id_personne);
			resultat = pstmt.executeQuery();
			resultat.last();
			if(resultat.getRow() == 1) // on a trouv� un utilisateur
			{
				resultat.first();
				this.id_personne = id_personne;
				nom = resultat.getString("nom");
				prenom = resultat.getString("prenom");
				date = resultat.getDate("datedenaissance");
				adresse = resultat.getString("adresse");
			}
			else
				throw new SQLException("Personne non trouv� dans la Dabatase identifiant :"+id_personne);
		}
		finally{
			if(pstmt != null)
				pstmt.close();
			if(resultat != null)
				resultat.close();
		}
	}
	
	public Personne(int id_personne, String nom, String prenom, Date date, String adresse)
	{
		this.id_personne = id_personne;
		this.nom = nom;
		this.prenom = prenom;
		this.date = date;
		this.adresse = adresse;
	}
	
	public void addPersonneListener(PersonneListener listener) {
        listeners.add(PersonneListener.class, listener);
    }
	
	public void removePersonneListener(PersonneListener listener){
		 listeners.remove(PersonneListener.class, listener);
	}

	public PersonneListener[] getPersonneListener()
	{
		return listeners.getListeners(PersonneListener.class);
	}

    protected void fireNomPersonneChange(String nouveau_nom) {
        for(PersonneListener listener : getPersonneListener()) {
            listener.nomPersonneChange(this, nouveau_nom);
        }
    }
    protected void firePrenomPersonneChange(String nouveau_prenom) {
        for(PersonneListener listener : getPersonneListener()) {
            listener.prenomPersonneChange(this, nouveau_prenom);
        }
    }
    protected void fireDatePersonneChange(Date nouvelle_date) {
        for(PersonneListener listener : getPersonneListener()) {
            listener.datePersonneChange(this, nouvelle_date);
        }
    }
	
	private void fireAdressePersonneChange(String nouvelle_adresse) {
        for(PersonneListener listener : getPersonneListener()) {
            listener.adressePersonneChange(this, nouvelle_adresse);
        }
	}
    
	public int getIdPersonne() {
		return id_personne;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
		fireNomPersonneChange(this.nom);
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
		firePrenomPersonneChange(this.prenom);
	}
	
	public Date getDate(){
		return date;
	}
	
	public void setDate(Date date) {
		// TODO Auto-generated method stub
		this.date = date;
		fireDatePersonneChange(this.date);
	}
	
	public void setAdresse(String adresse) {
		// TODO Auto-generated method stub
		this.adresse = adresse;
		fireAdressePersonneChange(this.adresse);
	}

	public String getAdresse(){
		return adresse;
	}


	@Override
	public String toString() {
		return "Personne [adresse=" + adresse + ", date=" + date
				+ ", id_personne=" + id_personne + " , nom=" + nom + ", prenom=" + prenom + "]";
	}

	public static void main(String[] args) throws SQLException
	{	
		try{
			SQL.setUtilisateur("lolo");
			SQL.setMotDePasse("lolo");
			SQL.sqlConnexion();
//			String sql = "drop table inscriptionCoursSquashCollec cascade constraint";
//			try {
//				Connection c = SQL.getConnexion();
//				Statement statement ;
//				statement = c.createStatement();
//				statement.executeQuery(sql);
//				System.out.println("Table inscriptionCoursSqCollectif detruite");
//			}finally{
//				
//			}
			//SQL.creerTable();
//			Souscriptions s = new Souscriptions();
//			s.ajouterSouscription("salle muscu", 1, new Date(111,11,30), new Date(112,0,29));
//			System.out.println(s);
//			s.supprimerSouscription("salle muscu", 1, new Date(111,11,30), new Date(112,0,29));
//			System.out.println(s);
			
//			SQL.creerTable();
			SQL.setProprietaire("lolo");
			SQL.creerTable();
//			if(SQL.tablesInstallees())
//				System.out.println("oui");
//			else System.out.println("non");
//			
//			
//			ArrayList<TypeMaterielLoue> materiel = new ArrayList<TypeMaterielLoue>();
//			materiel.add(new TypeMaterielLoue(2));
//			materiel.add(new TypeMaterielLoue(1));
//			l.supprimerLocation(7);
//			System.out.println(l);
//			Casiers c = new Casiers();
//			System.out.println(c);
//			Adherents a = new Adherents();
//			ReservationsCasiersSeances r = new ReservationsCasiersSeances();
//			Date d = new Date(1986,11,11);
//			Date d2 = new Date(1986,12,11);
////			r.ajouterReservationsCasiersSeances(5, d2, 10, 10, 1, 1150);
//			r.supprimerReservationsCasiersSeances(5,d2 ,10);
////			r.modifierReservationsCasiersAnnees(2, 1,2002);
//			ArrayList<ReservationCasierSeance > reservation_casier_seance = r.getCasier(5,11);
//			System.out.println(reservation_casier_seance.size());
//			System.out.println(r);
//			PersonnelsSportifs p = new PersonnelsSportifs();
//			System.out.println("avant");
//			p.modifierPersonnelSportif(1, "piou", "lolo", null, "laaaa", "poli");
////			p.ajouterPersonne("robert", "polochon", null, "piolenc");
////			p.ajouterPersonne("laurent", "maillet", null, "iou");
//			//p.supprimerPersonne(10);System.out.println(p);
//			//p.modifierPersonne(1, "john", "john", null, "laaaaa");
//			System.out.println(p);
//			System.out.println(p);
//			Adherents a = new Adherents();
//			a.ajouterAdherent(13);
//			System.out.println(a);
//			//Adherent a = new Adherent(10,5);
//			System.out.println(a);
		}
		catch (Exception e)
		{
			System.err.println(e);
			e.printStackTrace();
		}
	}

}
