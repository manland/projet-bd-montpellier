package Coeur;
import java.sql.*;
import java.util.ArrayList;


public class SQL {
	
	private static final int nombre_casier = 100;
	public static final int premier_casier_annee = 1;
	public static final int dernier_casier_annee = 70;
	public static final int premier_casier_seance = 71;
	public static final int dernier_casier_seance = 100;

	private static String proprietaire="";
	private static String utilisateur="";
	private static String mot_de_passe="";
	private static Connection connexion = null;
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	
	
	
	public static String getProprietaire() {
		return proprietaire;
	}
	public static void setProprietaire(String proprietaire) {
		SQL.proprietaire = proprietaire;
	}
	public static int getPremierCasierSeance(){
		return SQL.premier_casier_seance;
	}
	public static int getDernierCasierSeance(){
		return SQL.dernier_casier_seance;
	}

	public static int getPremierCasierAnnee(){
		return SQL.premier_casier_annee;
	}
	public static int getDernierCasierAnnee(){
		return SQL.dernier_casier_annee;
	}
	
	public static int getNombreCasier(){
		return SQL.nombre_casier;
	}

	public static void setUtilisateur(String utilisateur)
	{
		SQL.utilisateur = utilisateur;
	}
	public static void setUrl(String url)
	{
		SQL.url= url;
	}
	public static void setMotDePasse(String mot_de_passe)
	{
		SQL.mot_de_passe = mot_de_passe;
	}
	
	public static void sqlConnexion() throws ClassNotFoundException, SQLException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		connexion = DriverManager.getConnection (url, getUtilisateur(),getMotDePasse()); //parametres
		connexion.setAutoCommit(false);
	}
	
	public static void sqlDeconnexion() throws SQLException
	{
		if(connexion != null)
			connexion.close();
	}
	
	public static boolean tablesInstallees() throws SQLException
	{
		boolean b = true;
		String sql = "SELECT * FROM all_tables WHERE table_name='PERSONNE' AND owner='"+SQL.getProprietaire().toUpperCase()+"'";

		Statement statement = SQL.getConnexion().createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultat = statement.executeQuery(sql);
		resultat.last();
		
		if(resultat.getRow() == 0)
			b = false;
		return b;
	}
	
	public static void creerTable() throws SQLException
	{
		if(!SQL.getProprietaire().equals(SQL.getUtilisateur()) ||
				SQL.getProprietaire().equals(""))
			throw new SQLException("Impossible de cr�er les tables");
		Statement statement = null;
		try
		{
			Connection c = SQL.getConnexion();
			statement = c.createStatement();
			
			/*==============================================================*/
			/* Table : Personne                                             */
			/*==============================================================*/
			String sql ="";
			sql = "drop table personne cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table personne detruite");
			}
			catch (Exception e)
			{
				System.out.println("Personne :"+e );
			}
			
			sql ="create table personne";
			sql += "( ";
			sql +="id_personne int,";
			sql +="nom varchar2(30),";
			sql +="prenom varchar2(30),";
			sql +="dateDeNaissance date,";
			sql +="adresse varchar2(200),";
			sql +="constraint pk_personne primary key (id_personne),";
			sql +="unique(nom,prenom)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table personne cr�e");
			c.commit();
			
	
			/*==============================================================*/
			/* Table : Adherent                                             */
			/*==============================================================*/
			sql = "drop table adherent cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table adherent detruite");
			}
			catch (Exception e)
			{
				System.out.println("Adherent :"+e );
			}
			
			sql ="create table adherent";
			sql += "( ";
			sql +="num_adherent int,";
			sql += "id_personne int not null,";
			sql +="constraint pk_adherent primary key (num_adherent),";
			sql +="constraint fk_adherent foreign key(id_personne) references personne(id_personne),";
			sql +="unique (id_personne)";	
			sql +=")";	
			statement.executeQuery(sql);
			System.out.println("Table adherent cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : personnelAdministratif                                             */
			/*==============================================================*/
			sql = "drop table personnelAdministratif cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table personnelAdministratif detruite");
			}
			catch (Exception e)
			{
				System.out.println("personnelAdministratif :"+e );
			}
			sql ="create table personnelAdministratif";
			sql +="(";
			sql +="num_employe int,";
			sql +="id_personne int not null,";
			sql +="affectation varchar2(30),";
			sql +="constraint pk_personnel_administratif primary key (num_employe),";
			sql +="constraint fk_personnel_administratif foreign key(id_personne) references personne(id_personne),";
			sql +="unique (id_personne)";
			sql+=")";
			statement.executeQuery(sql);
			System.out.println("Table personnelAdministratif cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : personnelSportif                                             */
			/*==============================================================*/
			sql = "drop table personnelSportif cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table personnelSportif detruite");
			}
			catch (Exception e)
			{
				System.out.println("personnelSportif :"+e );
			}
			sql ="create table personnelSportif";
			sql +="(";
			sql +="id_prof int,";
			sql +="id_personne int not null,";
			sql +="type_sport varchar2(30),";
			sql +="constraint pk_personnel_sportif primary key (id_prof),";
			sql +="constraint fk_personnel_sportif foreign key(id_personne) references personne(id_personne),";
			sql +="unique (id_personne)";
			sql+=")";
			statement.executeQuery(sql);
			System.out.println("Table personnelSportif cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : mat�riel en location                                             */
			/*==============================================================*/
			sql = "drop table materielLocation cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table materielLocation detruite");
			}
			catch (Exception e)
			{
				System.out.println("materielLocation :"+e );
			}
			sql ="create table materielLocation";
			sql +="(";
			sql +="num_materiel int,";
			sql +="type_materiel varchar2(50) not null,";
			sql +="date_achat date,";
			sql +="etat varchar2(10),";
			sql +="description varchar2(200),";
			sql +="prix_base int,";
			sql +="constraint pk_materielLocation primary key (num_materiel)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table materielLocation cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : location                                             */
			/*==============================================================*/
			sql = "drop table location cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table location detruite");
			}
			catch (Exception e)
			{
				System.out.println("location :"+e );
			}
				
			try{
				sql = "drop type tab_materiel_loue force";
				statement.executeQuery(sql);
				System.out.println("Type tab_materiel_loue detruit");
			}
			catch (Exception e)
			{
				System.out.println("tab_materiel_loue :"+e );
			}
				
			try{
				sql = "drop type type_materiel_loue force";
				statement.executeQuery(sql);
				System.out.println("Type type_materiel_loue detruit");
			}
			catch (Exception e)
			{
				System.out.println("type_materiel_loue :"+e );
			}	

			sql ="create type type_materiel_loue as object";
			sql +="(";
			sql +="num_materiel int";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Type type_materiel_loue cr�e");
			c.commit();
			
			
			sql ="create type tab_materiel_loue as table of type_materiel_loue";
			statement.executeQuery(sql);
			System.out.println("Type tab_materiel_loue cr�e");
			c.commit();
			
			
				
			sql ="create table location";
			sql +="(";
			sql +="num_location int,";
			sql +="date_location date,";
			sql +="heure_debut int,";
			sql +="heure_fin int,";
			sql +="check(heure_fin=heure_debut+1),";
			sql +="materiel tab_materiel_loue,";
			sql +="id_personne int,";
			sql +="prix_paye int,";
			sql +="constraint pk_location primary key (num_location),";
			sql +="constraint fk_location_id_personne foreign key(id_personne) references personne(id_personne)";
			sql +=")";
			sql +="nested table materiel store as tab_materiel_loc";
			statement.executeQuery(sql);
			System.out.println("Table location cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_location ";
			sql+= "before insert or update on location ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "tab_mat_loue tab_materiel_loue; ";
			sql+= "cpt int; ";
			sql+= "taille int; ";
			sql+= "date_loc date; ";
			sql+= "hd int; ";
			sql+= "hf int; ";
			sql+= "begin ";
			sql+= "tab_mat_loue := :new.materiel; ";
			sql+= "for cpt in tab_mat_loue.first..tab_mat_loue.last loop ";
			sql+= "select count(num_materiel) into taille from materielLocation where num_materiel=tab_mat_loue(cpt).num_materiel; ";
			sql+= "if(taille=0) then ";
			sql+= "raise_application_error(-20301,'erreur materiel pas dans la liste du materiel a louer'); ";
			sql+= "end if; ";
			sql+= "end loop; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger location cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_location_2 ";
			sql+= "before insert or update on location ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "tab_mat_loue tab_materiel_loue; ";
			sql+= "cpt int; ";
			sql+= "i int; ";
			sql+= "taille int; ";
			sql+= "date_loc date; ";
			sql+= "hd int; ";
			sql+= "hf int; ";
			sql+= "begin ";
			sql+= "date_loc:=:new.date_location; ";
			sql+= "hd := :new.heure_debut; ";
			sql+= "hf := :new.heure_fin; ";
			sql+= "tab_mat_loue := :new.materiel; ";
			sql+= "for i in tab_mat_loue.first..tab_mat_loue.last loop ";
			sql+= "select count(*) into cpt from location; ";
			sql+= "if(cpt!=0) then ";
			sql+= "select count(u.num_materiel) into taille from table(select ct.materiel from location ct where ct.date_location=date_loc and ct.heure_debut=hd and ct.heure_fin=hf) u where num_materiel=tab_mat_loue(i).num_materiel; ";
			sql+= "if(taille!=0) then ";
			sql+= "raise_application_error(-20301,'erreur au moins un materiel est deja loue'); ";
			sql+= "end if; ";
			sql+= "end if; ";
			sql+= "end loop; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger location 2 cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : mat�riel en vente                                             */
			/*==============================================================*/
			sql = "drop table materielVente cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table materielVente detruite");
			}
			catch (Exception e)
			{
				System.out.println("materielVente :"+e );
			}
			sql ="create table materielVente";
			sql +="(";
			sql +="type_materiel varchar2(50),";
			sql +="description varchar2(200),";
			sql +="quantite int,";
			sql +="prix_base int,";
			sql +="constraint pk_materielVente primary key (type_materiel)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table materielVente cr�e");
			c.commit();
			
			
			
			/*==============================================================*/
			/* Table : vente                                             */
			/*==============================================================*/
			sql = "drop table vente cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table vente detruite");
			}
			catch (Exception e)
			{
				System.out.println("vente :"+e );
			}
			
			sql = "drop type tab_materiel_achete force";
			try {
				statement.executeQuery(sql);
				System.out.println("Type tab_materiel_achete detruit");
			}
			catch (Exception e)
			{
				System.out.println("tab_materiel_achete :"+e );
			}
			
			sql = "drop type type_materiel_achete force";
			try {
				statement.executeQuery(sql);
				System.out.println("Type type_materiel_achete detruit");
			}
			catch (Exception e)
			{
				System.out.println("type_materiel_achete :"+e );
			}
			
			sql ="create type type_materiel_achete as object";
			sql +="(";
			sql +="type_materiel varchar2(50),";
			sql +="quantite int,";
			sql +="prix_base int";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Type type_materiel_achete cr�e");
			c.commit();
			
			sql ="create type tab_materiel_achete as Table of type_materiel_achete";
			statement.executeQuery(sql);
			System.out.println("Type tab_materiel_achete cr�e");
			c.commit();
			
			sql ="create table vente";
			sql +="(";
			sql +="num_vente int,";
			sql +="materiel tab_materiel_achete,";
			sql +="date_vente date,";
			sql +="id_personne int,";
			sql +="prix_paye int,";
			sql +="constraint pk_vente primary key (num_vente),";
			sql +="constraint fk_vente_id_personne foreign key(id_personne) references personne(id_personne)";
			sql +=")";
			sql +="nested table materiel store as tab_materiel";
			statement.executeQuery(sql);
			System.out.println("Table vente cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_vente ";
			sql+= "before insert or update on vente ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "tab_mat_achete tab_materiel_achete; ";
			sql+= "cpt int; ";
			sql+= "taille int; ";
			sql+= "taille_qte int; ";
			sql+= "qte int; ";
			sql+= "begin ";
			sql+= "tab_mat_achete := :new.materiel; ";
			sql+= "for cpt in tab_mat_achete.first..tab_mat_achete.last loop ";
			sql+= "select count(type_materiel) into taille from materielVente where type_materiel=tab_mat_achete(cpt).type_materiel; ";
			sql+= "if(taille=0) then ";
			sql+= "raise_application_error(-20301,'erreur type_materiel_achete pas dans stock'); ";
			sql+= "else ";
			sql+= "select count(quantite) into taille_qte from materielVente where quantite>=tab_mat_achete(cpt).quantite; ";
			sql+= "if(taille_qte=0) then ";
			sql+= "raise_application_error(-20301,'erreur type_materiel_achete quantite'); ";
			sql+= "end if; ";
			sql+= "end if; ";
			sql+= "end loop; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger vente cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : casier                                             */
			/*==============================================================*/
			sql = "drop table casier cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table casier detruite");
			}
			catch (Exception e)
			{
				System.out.println("casier :"+e );
			}
			sql ="create table casier";
			sql +="(";
			sql +="num_casier int,";
			sql +="check(num_casier between 1 and 100),";
			sql +="constraint pk_casier primary key (num_casier)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table casier cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : reservation casier a l'annee                                             */
			/*==============================================================*/
			sql = "drop table reservationCasierAnnee cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table reservationCasierAnnee detruite");
			}
			catch (Exception e)
			{
				System.out.println("reservationCasierAnnee :"+e );
			}
			sql ="create table reservationCasierAnnee";
			sql +="(";
			sql +="num_casier int,";
			sql +="check(num_casier between 1 and 70),";
			sql +="num_adherent int,";
			sql +="annee int,";
			sql +="constraint pk_reservation_casier_annee primary key (num_casier, annee),";
			sql +="constraint fk_resa_casier_an_adherent foreign key(num_adherent) references adherent(num_adherent),";
			sql +="constraint fk_resa_casier_an_casier foreign key(num_casier) references casier(num_casier),";
			sql +="unique(num_adherent,annee)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table reservationCasierAnnee cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : reservation casier a la seance                                             */
			/*==============================================================*/
			sql = "drop table reservationCasierSeance cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table reservationCasierSeance detruite");
			}
			catch (Exception e)
			{
				System.out.println("reservationCasierSeance :"+e );
			}
			sql ="create table reservationCasierSeance";
			sql +="(";
			sql +="num_casier int,";
			sql +="check(num_casier between 71 and 100),";
			sql +="date_location date,";
			sql +="heure_debut int,";
			sql +="check(heure_debut between 9 and 21),";
			sql +="heure_fin int,";
			sql +="check(heure_fin between 10 and 22),";
			sql +="id_personne int,";
			sql +="prix_paye int,";
			sql +="check(heure_fin=heure_debut+1),";
			sql +="constraint pk_reservation_casier_seance primary key (num_casier, date_location, heure_debut),";
			sql +="constraint fk_resa_casier_seance_personne foreign key(id_personne) references personne(id_personne),";
			sql +="constraint fk_resa_casier_seance_casier foreign key(num_casier) references casier(num_casier)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table reservationCasierSeance cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : forfait                                           */
			/*==============================================================*/
			sql = "drop table forfait cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table forfait detruite");
			}
			catch (Exception e)
			{
				System.out.println("forfait :"+e );
			}
			sql ="create table forfait";
			sql +="(";
			sql +="type_forfait varchar2(30),";
			sql +="prix_mois int,";
			sql +="prix_trimestre int,";
			sql +="prix_semestre int,";
			sql +="prix_an int,";
			sql +="check(type_forfait in ('salle muscu','cours gymnastique','terrain squash','cours particulier squash','cours groupe squash')),";
			sql +="constraint pk_forfait primary key (type_forfait)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table forfait cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : Historiquesouscription                                           */
			/*==============================================================*/
			sql = "drop table Historiquesouscription cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table Historiquesouscription detruite");
			}
			catch (Exception e)
			{
				System.out.println("Historiquesouscription :"+e );
			}
			sql ="create table Historiquesouscription";
			sql +="(";
			sql +="type_forfait varchar2(30),";
			sql +="num_adherent int,";
			sql +="date_debut date,";
			sql +="date_fin date,";
			sql +="	check(type_forfait in ('salle muscu','cours gymnastique','terrain squash','cours particulier squash','cours groupe squash')),";
			sql +="constraint pk_histo_souscription primary key (type_forfait,date_debut,date_fin,num_adherent),";
			sql +="constraint fk_histo_sous_num_adherent foreign key(num_adherent) references adherent(num_adherent),";
			sql +="constraint fk_histo_sous_type_forfait foreign key(type_forfait) references forfait(type_forfait)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table Historiquesouscription cr�e");
			c.commit();
			
			
			
			/*==============================================================*/
			/* Table : Souscription                                           */
			/*==============================================================*/
			sql = "drop table souscription cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table souscription detruite");
			}
			catch (Exception e)
			{
				System.out.println("souscription :"+e );
			}
			sql ="create table souscription";
			sql +="(";
			sql +="type_forfait varchar2(30),";
			sql +="num_adherent int,";
			sql +="date_debut date,";
			sql +="date_fin date,";
			sql +="check(type_forfait in ('salle muscu','cours gymnastique','terrain squash','cours particulier squash','cours groupe squash')),";
			sql +="constraint pk_souscription primary key (type_forfait,date_debut,date_fin,num_adherent),";
			sql +="constraint fk_souscription_num_adherent foreign key(num_adherent) references adherent(num_adherent),";
			sql +="constraint fk_souscription_type_forfait foreign key(type_forfait) references forfait(type_forfait)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table souscription cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_souscription ";
			sql+= "before insert or update on souscription ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "res int; ";
			sql+= "begin ";
			sql+= "res := :new.date_fin-:new.date_debut; ";
			sql+= "if(res!=30 and res!=90 and res!=180 and res!=365) then ";
			sql+= "raise_application_error(-20501,'date souscription incorrecte'); ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger souscription cr�e");
			c.commit();
			
			sql="create or replace trigger tg_souscription_perime ";
			sql+= "before insert or update on souscription ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "res int; ";
			sql+= "date_auj date; ";
			sql+= "nb_forf int; ";
			sql+= "num_ad int; ";
			sql+= "typ varchar2(30); ";
			sql+= "dat_deb date; ";
			sql+= "dat_fin date; ";
			sql+= "begin ";
			sql+= "res := :new.date_fin-:new.date_debut; ";
			sql+= "date_auj := sysdate; ";
			sql+= "num_ad := :new.num_adherent; ";
			sql+= "typ := :new.type_forfait; ";
			sql+= "select count(*) into nb_forf from souscription where num_adherent=num_ad and type_forfait=typ; ";
			sql+= "if(nb_forf>0) then ";
			sql+= "select  date_debut, date_fin into dat_deb,dat_fin from souscription where num_adherent=num_ad and type_forfait=typ; ";
			sql+= "if(dat_fin<date_auj) then ";
			sql+= "insert into historiqueSouscription values (typ,num_ad,dat_deb,dat_fin); ";
			sql+= "delete from souscription where num_adherent=num_ad and date_fin<date_auj and type_forfait=typ; ";
			sql+= "else ";
			sql+= "raise_application_error(-20501,'cet adherent possede deja ce type de forfait'); ";
			sql+= "end if; ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger souscription_perime cr�e");
			c.commit();
				
			/*==============================================================*/
			/* Table : terrainSquash                                           */
			/*==============================================================*/
			sql = "drop table terrainSquash cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table terrainSquash detruite");
			}
			catch (Exception e)
			{
				System.out.println("terrainSquash :"+e );
			}
			sql ="create table terrainSquash";
			sql +="(";
			sql +="num_terrain_squash int,";
			sql +="check(num_terrain_squash between 1 and 6),";
			sql +="constraint pk_terrainSquash primary key (num_terrain_squash)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table terrainSquash cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : reservationTerrainSquash                                           */
			/*==============================================================*/
			sql = "drop table reservationTerrainSquash cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table reservationTerrainSquash detruite");
			}
			catch (Exception e)
			{
				System.out.println("reservationTerrainSquash :"+e );
			}
			sql ="create table reservationTerrainSquash";
			sql +="(";
			sql +="num_terrain_squash int,";
			sql +="date_location date,";
			sql +="heure_debut int,";
			sql +="heure_fin int,";
			sql +="minute_debut int,";
			sql +="minute_fin int,";
			sql +="id_personne int,";
			sql +="prix_paye int,";
			sql +="constraint pk_reservation_terrain_squash primary key (num_terrain_squash,date_location,heure_debut,heure_fin,minute_debut,minute_fin),";
			sql +="constraint fk_resa_terrain_sq_personne foreign key(id_personne) references personne(id_personne),";
			sql +="constraint fk_resa_terrain_sq_terrain foreign key(num_terrain_squash) references terrainSquash(num_terrain_squash)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table reservationTerrainSquash cr�e");
			c.commit();
			
			
			
			sql= "create or replace trigger tg_res_terrain_limite_ad ";
			sql+= "before insert or update on reservationTerrainSquash ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "nb_terr_res_par_ad int; ";
			sql+= "nb_adherent int; ";
			sql+= "date_loc date; ";
			sql+= "hd int; ";
			sql+= "hf int; ";
			sql+= "num_person int; ";
			sql+= "nb_cours_col int; ";
			sql+= "nb_cours_ind int; ";
			sql+= "res_nb_cours int; ";
			sql+= "min_fin int; ";
			sql+= "min_deb int; ";
			sql+= "jour_inter int; ";
			sql+= "jour_res varchar2(9); ";
			sql+= "begin ";
			sql+= "date_loc := :new.date_location; ";
			sql+= "hd := :new.heure_debut; ";
			sql+= "hf := :new.heure_fin; ";
			sql+= "min_fin := :new.minute_fin; ";
			sql+= "min_deb := :new.minute_debut; ";
			sql+= "num_person :=:new.id_personne; ";
			sql+= "jour_inter := to_char(:new.date_location,'D')-1; ";
			sql+= "select count(num_terrain_squash) into nb_terr_res_par_ad from personne p, reservationTerrainSquash r, adherent a where r.id_personne=a.id_personne and r.id_personne=p.id_personne and p.id_personne=a.id_personne and date_location=date_loc and ((((hd=heure_debut and min_deb<=minute_debut) or hd<heure_debut) and (hf>heure_debut or (hf=heure_debut and min_fin>minute_debut))) or (((heure_debut=hd and minute_debut<min_deb) or heure_debut<hd) and (heure_fin>hd or (heure_fin=hd and minute_fin>min_deb))) ); "; 
			sql+= "if(nb_terr_res_par_ad>=4) then ";
			sql+= "select count(num_adherent) into nb_adherent from adherent where id_personne=num_person; ";
			sql+= "if(nb_adherent!=0) then ";
			sql+= "raise_application_error(-20501,'erreur il n y a plus de terrain dispo pour les adherents'); ";
			sql+= "end if; ";
			sql+= "end if; ";
			sql+= "select count(*) into nb_cours_col from creneauCoursSqColl cren, adherent a, inscriptionCoursSqCollectif i where a.id_personne=num_person and i.num_adherent=a.num_adherent and i.id_cours=cren.id_cours and jour=jour_inter and ((cren.heure_debut=hd or cren.heure_debut=hd-1) or ((hf=cren.heure_debut and min_fin!=0) or hf=cren.heure_debut+1)); ";
			sql+= "if(nb_cours_col>0) then ";
			sql+= "raise_application_error(-20501,'erreur cet adherent a un cours collectif pour cet horaire'); ";
			sql+= "end if; ";
			sql+= "select count(*) into nb_cours_ind from creneauCoursSqIndividuel cren, adherent a, coursSquashIndividuel c where a.id_personne=num_person and c.num_adherent=a.num_adherent and c.id_cours=cren.id_cours and date_cours=date_loc and (cren.heure_debut=hd  or ((hf=cren.heure_debut and min_fin!=0))); ";
			sql+= "if(nb_cours_ind>0) then ";
			sql+= "raise_application_error(-20501,'erreur cet adherent a un cours individuel pour cet horaire'); ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger reservationTerrainSquash_limite_ad cr�e");
			c.commit();	
			
			
			sql = "create or replace trigger tg_res_terr_limite_reserv ";
			sql+= "before insert or update on reservationTerrainSquash ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "nb_terr_res_par_ad int; ";
			sql+= "nb_adherent int; ";
			sql+= "date_loc date; ";
			sql+= "hd int; ";
			sql+= "hf int; ";
			sql+= "min_deb int; ";
			sql+= "min_fin int; ";
			sql+= "num_person int; ";
			sql+= "n int; ";
			sql+= "nb_id_pers int; ";
			sql+= "num_terr int; ";
			sql+= "cpt int; ";
			sql+= "begin ";
			sql+= "date_loc := :new.date_location; ";
			sql+= "hd := :new.heure_debut; ";
			sql+= "hf := :new.heure_fin; ";
			sql+= "min_deb := :new.minute_debut; ";
			sql+= "min_fin := :new.minute_fin; ";
			sql+= "num_person :=:new.id_personne; ";
			sql+= "num_terr := :new.num_terrain_squash; ";
			sql+= "select count(*) into n from reservationTerrainSquash; ";
			sql+= "if(n!=0) then ";
			sql+= "select count(*) into nb_id_pers from reservationTerrainSquash where id_personne=num_person and date_location=date_loc and ((((hd=heure_debut and min_deb<=minute_debut) or hd<heure_debut) and (hf>heure_debut or (hf=heure_debut and min_fin>minute_debut))) or (((heure_debut=hd and minute_debut<min_deb) or heure_debut<hd) and (heure_fin>hd or (heure_fin=hd and minute_fin>min_deb))) ); ";
			sql+= "if(nb_id_pers!=0) then ";
			sql+= "raise_application_error(-20501,'erreur cette personne reserve deja un terrain au meme horaire (ou chevauchement des horraires)'); ";
			sql+= "end if; ";
			sql+= "select count(*) into cpt from reservationTerrainSquash where num_terrain_squash=num_terr and date_location=date_loc and ((((hd=heure_debut and min_deb<=minute_debut) or hd<heure_debut) and (hf>heure_debut or (hf=heure_debut and min_fin>minute_debut))) or (((heure_debut=hd and minute_debut<min_deb) or heure_debut<hd) and (heure_fin>hd or (heure_fin=hd and minute_fin>min_deb))) ); ";
			sql+= "if(cpt!=0) then ";
			sql+= "raise_application_error(-20501,'erreur ce terrain est deja reservé pour cet horraire (ou chevauchement des horraires)'); ";
			sql+= "end if; ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger reservationTerrainSquash_limite_res cr�e");
			c.commit();	
			
			sql= "create or replace trigger tg_res_terr_reserv_cren ";
			sql+= "before insert or update on reservationTerrainSquash ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "num_terr int; ";
			sql+= "date_res date; ";
			sql+= "hd int; ";
			sql+= "hf int; ";
			sql+= "min_deb int; ";
			sql+= "min_fin int; ";
			sql+= "nb_cours_ind int; ";
			sql+= "nb_cours_col int; ";
			sql+= "jour_res int; ";
			sql+= "begin ";
			sql+= "num_terr := :new.num_terrain_squash; ";
			sql+= "date_res := :new.date_location; ";
			sql+= "hd := :new.heure_debut; ";
			sql+= "hf := :new.heure_fin; ";
			sql+= "min_deb := :new.minute_debut; ";
			sql+= "min_fin := :new.minute_fin; ";
			sql+= "jour_res := to_char(:new.date_location,'D')-1; ";
			sql+= "select count(*) into nb_cours_ind from creneauCoursSqIndividuel where num_terrain_squash=num_terr and date_cours=date_res and (heure_debut=hd or (hf=heure_debut and min_fin!=0)); ";
			sql+= "if(nb_cours_ind>0) then ";
			sql+= "raise_application_error(-20501,'erreur, terrain deja reservé pour un cours individuel'); ";
			sql+= "end if; ";
			sql+= "select count(*) into nb_cours_col from creneauCoursSqColl where num_terrain_squash=num_terr and jour=jour_res and ((heure_debut=hd or heure_debut=hd-1) or ((hf=heure_debut and min_fin!=0) or hf=heure_debut+1)); ";
			sql+= "if(nb_cours_col>0) then ";
			sql+= "raise_application_error(-20501,'erreur, terrain deja reservé pour un cours collectif'); ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger reservationTerrainSquash_limite_ad cr�e");
			c.commit();	
			
			
			
			/*==============================================================*/
			/* Table : coursSquash                                           */
			/*==============================================================*/
			sql = "drop table coursSquash cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table coursSquash detruite");
			}
			catch (Exception e)
			{
				System.out.println("coursSquash :"+e );
			}
			sql ="create table coursSquash";
			sql +="(";
			sql +="id_cours int,";
			sql +="id_prof int,";
			sql +="type_de_cours varchar2(10),";
			sql +="check(type_de_cours in ('individuel','collectif')),";
			sql +="constraint pk_cours_squash primary key (id_cours),";
			sql +="constraint fk_cours_squash_prof foreign key(id_prof) references personnelSportif(id_prof)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table coursSquash cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_cours_squash_prof ";
			sql +="before insert or update on coursSquash ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_p int; ";
			sql +="typ_p varchar2(30); ";
			sql +="begin ";
			sql +="id_p:= :new.id_prof; ";
			sql +="select type_sport into typ_p from personnelSportif where id_prof=id_p; ";
			sql +="if(typ_p!='squash') then ";
			sql +="raise_application_error(-20501,'erreur type_prof!=squash'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger coursSquash_prof cr�e");
			c.commit();	
			

			
			/*==============================================================*/
			/* Table : coursSquashIndividuel                                           */
			/*==============================================================*/
			sql = "drop table coursSquashIndividuel cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table coursSquashIndividuel detruite");
			}
			catch (Exception e)
			{
				System.out.println("coursSquashIndividuel :"+e );
			}
			sql ="create table coursSquashIndividuel";
			sql +="(";
			sql +="id_cours int,";
			sql +="num_adherent int,";
			sql +="constraint pk_cours_squash_individuel primary key (id_cours),";
			sql +="constraint fk_cours_squash_indiv_cours foreign key(id_cours) references coursSquash(id_cours),";
			sql +="constraint fk_cours_squash_indiv_adherent foreign key(num_adherent) references adherent(num_adherent)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table coursSquashIndividuel cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_cours_squash_individuel\n";
			sql+= "before insert or update on coursSquashIndividuel\n";
			sql+= "for each row\n";
			sql+= "declare\n";
			sql+= "id_c int;\n";
			sql+= "typ varchar2(30);\n";
			sql+= "begin\n";
			sql+= "id_c := :new.id_cours;\n";
			sql+= "select type_de_cours into typ from coursSquash where \n";
			sql+= "id_cours=id_c;\n";
			sql+= "if(typ='collectif') then\n";
			sql+= "raise_application_error(-20501,'erreur type doit etre individuel');\n";
			sql+= "end if;\n";
			sql+= "exception\n";
			sql+= "when no_data_found then\n";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee');\n";
			sql+= "end;\n";
			statement.executeQuery(sql);
			System.out.println("Trigger coursSquashIndividuel cr�e");
			c.commit();

			sql= "create or replace trigger tg_cours_sq_indiv_forfait ";
			sql+= "before insert or update on coursSquashIndividuel ";
			sql+= "for each row ";
			sql+= "declare ";
			sql+= "id_c int; ";
			sql+= "nb_pers int; ";
			sql+= "cpt int; ";
			sql+= "num_ad int; ";
			sql+= "date_c date; ";
			sql+= "date_deb_forfait date; ";
			sql+= "date_fin_forfait date; ";
			sql+= "nb_forfait int; ";
			sql+= "res int; ";
			sql+= "nb_ad int; ";
			sql+= "begin ";
			sql+= "id_c := :new.id_cours; ";
			sql+= "num_ad := :new.num_adherent; ";
			sql+= "select count(*) into nb_forfait from souscription where type_forfait='cours particulier squash' and num_adherent=num_ad; ";
			sql+= "if (nb_forfait=0) then ";
			sql+= "raise_application_error(-20301,'erreur pas de forfait pour cet adherent'); ";
			sql+= "end if; ";
			sql+= "exception ";
			sql+= "when no_data_found then ";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql+= "end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger coursSquashIndividuel cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : creneauCoursSqIndividuel                                           */
			/*==============================================================*/
			sql = "drop table creneauCoursSqIndividuel cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table creneauCoursSqIndividuel detruite");
			}
			catch (Exception e)
			{
				System.out.println("creneauCoursSqIndividuel :"+e );
			}
			sql ="create table creneauCoursSqIndividuel";
			sql +="(";
			sql +="id_cours int,";
			sql +="num_terrain_squash int,";
			sql +="date_cours date,";
			sql +="heure_debut int,";
			sql +="check(heure_debut between 9 and 20),";
			sql +="check(heure_fin=heure_debut+1),";
			sql +="heure_fin int,";
			sql +="check(heure_fin between 10 and 22),";
			sql +="constraint pk_creneau_cours_sq_ind primary key (id_cours),";
			sql +="constraint fk_creneau_cours_sq_cours foreign key(id_cours) references coursSquash(id_cours),";
			sql +="constraint fk_creneau_cours_sq_terrain foreign key(num_terrain_squash) references terrainSquash(num_terrain_squash),";
			sql +="unique(num_terrain_squash,date_cours,heure_debut)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table creneauCoursSqIndividuel cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_creneau_cours_sq_indiv ";
			sql +="before insert or update on creneauCoursSqIndividuel ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="typ varchar2(10); ";
			sql +="taille int; ";
			sql +="begin ";
			sql +="id_c := :new.id_cours; ";
			sql +="select count(*) into taille from creneauCoursSqIndividuel; ";
			sql +="if(taille!=0) then ";
			sql +="select type_de_cours into typ from coursSquash where id_cours=id_c; ";
			sql +="if(typ!='individuel') then ";
			sql +="raise_application_error(-20501, 'erreur type_cours=collectif'); ";
			sql +="end if; ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursSqIndividuel cr�e");
			c.commit();
			
			
			sql= "create or replace trigger tg_cren_cours_sq_ind_date ";
			sql +="after insert on creneauCoursSqIndividuel ";
			sql +="declare ";
			sql +="jour_ind varchar2(10); ";
			sql +="id_c_cren int; ";
			sql +="id_c_ind int; ";
			sql +="hd_ind int; ";
			sql +="hf_ind int; ";
			sql +="hd_coll int; ";
			sql +="hf_coll int; ";
			sql +="num_terr int; ";
			sql +="jour_coll int; ";
			sql +="date_c date; ";
			sql +="cpt int; ";
			sql +="prof int; ";
			sql +="res_prof_ind int; ";
			sql +="res_prof_col int; ";
			sql +="res_total int; ";
			sql +="res_adh_coll int; ";
			sql +="res_adh_ind int; ";
			sql +="res_total_adh int; ";
			sql +="num_ad int; ";
			sql +="idd creneauCoursSqIndividuel.id_cours%TYPE; ";
			sql +="i int; ";
			sql +="jour_inter int; ";
			sql +="trimestre date; ";
			sql +="mois_fin_trimestre varchar2(2); ";
			sql +="annee int; ";
			sql +="fin_trimestre varchar2(10); ";
			sql +="concat_mois varchar2(5); ";
			sql +="concat_annee varchar2(5); ";
			sql +="date_fin_trimestre date; ";
			sql +="jour_trim varchar2(2); ";
			sql +="nb_terr_res int; ";
			sql +="nb_reservation int; ";
			sql +="CURSOR curseur is select ci.date_cours,to_char(date_cours,'dy'),ci.id_cours, ci.heure_debut, ci.heure_fin, ci.num_terrain_squash from creneauCoursSqIndividuel ci; ";
			sql +="begin ";
			sql +="i :=0; ";
			sql +="open curseur; ";
			sql +="loop ";
			sql +="FETCH curseur into date_c,jour_ind,id_c_cren,hd_ind,hf_ind,num_terr; ";
			sql+= "jour_inter := to_char(date_c,'D')+1; ";
			sql +="select num_adherent into num_ad from coursSquashIndividuel where id_cours=id_c_cren; ";
			sql +="select id_prof into prof from coursSquash where id_cours=id_c_cren; ";
			sql +="select count(*) into res_prof_ind from creneauCoursSqIndividuel cren, coursSquash cs where id_prof=prof and cren.id_cours=cs.id_cours and date_cours=date_c and (heure_debut=hd_ind or heure_debut=hf_ind-1); "; 
			sql +="select count(*) into res_adh_ind from creneauCoursSqIndividuel cren, coursSquashIndividuel cs where num_adherent=num_ad and cren.id_cours=cs.id_cours and date_cours=date_c and (heure_debut=hd_ind or heure_debut=hf_ind-1); "; 
			sql +="select count(*) into nb_reservation from reservationTerrainSquash resa, adherent a, coursSquashIndividuel ind where ind.id_cours=id_c_cren and resa.id_personne=a.id_personne and a.num_adherent=ind.num_adherent and date_location=date_c and (resa.heure_debut=hd_ind or (resa.heure_fin=hd_ind and resa.minute_fin!=0)); ";
			sql +="select count(*) into res_prof_col from creneauCoursSqColl cren, coursSquash cs where id_prof=prof and cren.id_cours=cs.id_cours and jour=jour_inter and (heure_debut=hd_ind or heure_debut=hd_ind-1); ";
			sql +="select count(*) into cpt from creneauCoursSqColl where num_terrain_squash=num_terr and jour=jour_inter and (heure_debut=hd_ind or heure_debut=hd_ind-1);  ";
			sql +="select count(*) into res_adh_coll from creneauCoursSqColl cren, coursSquashIndividuel cs, inscriptionCoursSqCollectif col where col.num_adherent=num_ad and cren.id_cours=col.id_cours and jour=jour_inter and  (cren.heure_debut=hd_ind or cren.heure_debut=hd_ind-1); "; 
			sql +="select count(*) into nb_terr_res from reservationTerrainSquash where num_terrain_squash=num_terr and date_location=date_c and (heure_debut=hd_ind or (heure_fin=hd_ind and minute_fin!=0)); ";
			sql +="if(nb_reservation>0) then ";
			sql +="raise_application_error(-20501,'erreur cet adherent reserve deja un terrain pour cet horraire'); ";
			sql +="end if; ";
			sql +="if(cpt>0) then ";
			sql +="delete from creneauCoursSqIndividuel where id_cours=id_c_cren and to_char(date_cours,'dy')=jour_ind and heure_debut=hd_ind and heure_fin=hf_ind; ";
			sql +="raise_application_error(-20501,'erreur ce terrain est deja pris pour cet horraire'); ";
			sql +="end if; ";
			sql +="res_total := res_prof_col+res_prof_ind; ";
			sql +="if(res_total>1) then ";
			sql +="delete from creneauCoursSqIndividuel where id_cours=id_c_cren and date_cours=date_c and heure_debut=hd_ind and heure_fin=hf_ind; ";
			sql +="raise_application_error(-20501,'erreur ce prof est deja occupe pour cet horraire'); ";
			sql +="end if; ";
			sql +="res_total_adh:=res_adh_coll+res_adh_ind; ";
			sql +="if(res_total_adh>1) then ";
			sql +="delete from creneauCoursSqIndividuel where id_cours=id_c_cren and date_cours=date_c and heure_debut=hd_ind and heure_fin=hf_ind; ";
			sql +="raise_application_error(-20501,'erreur cet adherent a deja un cours pour cet horraire'); ";
			sql +="end if; ";
			sql +="if(nb_terr_res>0) then ";
			sql +="delete from creneauCoursSqIndividuel where id_cours=id_c_cren and date_cours=date_c and heure_debut=hd_ind and heure_fin=hf_ind; ";
			sql +="raise_application_error(-20501,'erreur ce terrain est deja reservé pour cet horraire'); ";
			sql +="end if; ";
			sql +="EXIT WHEN (curseur%NOTFOUND); ";
			sql +="i := i + 1; ";
			sql +="end loop; ";
			sql +="CLOSE curseur; ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursSqIndividuel_date cr�e");
			c.commit();
			
			
			sql= "create or replace trigger tg_creneau_cours_ind_forfait ";
			sql +="before insert or update on creneauCoursSqIndividuel ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="cpt int; ";
			sql +="num_ad int; ";
			sql +="date_c date; ";
			sql +="date_deb_forfait date; ";
			sql +="date_fin_forfait date; ";
			sql +="nb_forfait int; ";
			sql +="begin ";
			sql +="id_c := :new.id_cours; ";
			sql +="date_c:=:new.date_cours; ";
			sql +="select num_adherent into num_ad from coursSquashIndividuel where id_cours=id_c; ";
			sql +="select date_debut,date_fin into date_deb_forfait,date_fin_forfait from souscription where type_forfait='cours particulier squash' and num_adherent=num_ad; ";
			sql +="if(not (date_deb_forfait=date_c or (date_deb_forfait<date_c and date_fin_forfait>date_c) or date_fin_forfait=date_c)) then ";
			sql +="raise_application_error(-20301,'erreur dates de forfait non valides'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursSqIndividuel_forfait cr�e");
			c.commit();			
			
			
			/*==============================================================*/
			/* Table : coursSquashCollectif                                           */
			/*==============================================================*/
			sql = "drop table coursSquashCollectif cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table coursSquashCollectif detruite");
			}
			catch (Exception e)
			{
				System.out.println("coursSquashCollectif :"+e );
			}
			sql ="create table coursSquashCollectif";
			sql +="(";
			sql +="id_cours int,";
			sql +="nb_personne int,";
			sql +="check(nb_personne between 2 and 6),";
			sql +="constraint pk_cours_squash_coll primary key (id_cours),";
			sql +="constraint fk_cours_squash_coll_id_cours foreign key(id_cours) references coursSquash(id_cours)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table coursSquashCollectif cr�e");
			c.commit();
			
			sql ="create or replace trigger tg_cours_squash_collectif\n";
			sql+= "before insert or update on coursSquashCollectif\n";
			sql+= "for each row\n";
			sql+= "declare\n";
			sql+= "id_c int;\n";
			sql+= "typ varchar2(30);\n";
			sql+= "begin\n";
			sql+= "id_c := :new.id_cours;\n";
			sql+= "select type_de_cours into typ from coursSquash where \n";
			sql+= "id_cours=id_c;\n";
			sql+= "if(typ='individuel') then\n";
			sql+= "raise_application_error(-20501,'erreur type de cours attendu = collectif');\n";
			sql+= "end if;\n";
			sql+= "exception\n";
			sql+= "when no_data_found then\n";
			sql+= "raise_application_error(-20301,'aucune donnee trouvee');\n";
			sql+= "end;\n";
			statement.executeQuery(sql);
			System.out.println("Trigger coursSquashCollectif cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : inscriptionCoursSquashCollectif                                           */
			/*==============================================================*/
			sql = "drop table inscriptionCoursSqCollectif cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table inscriptionCoursSqCollectif detruite");
			}
			catch (Exception e)
			{
				System.out.println("inscriptionCoursSqCollectif :"+e );
			}
			sql ="create table inscriptionCoursSqCollectif";
			sql +="(";
			sql +="id_cours int,";
			sql +="num_adherent int,";
			sql +="constraint pk_insc_cours_squash_coll primary key (id_cours,num_adherent),";
			sql +="constraint fk_insc_cours_sq_coll_cours foreign key(id_cours) references coursSquash(id_cours),";
			sql +="constraint fk_insc_cours_sq_coll_adherent foreign key(num_adherent) references adherent(num_adherent)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table inscriptionCoursSqCollectif cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_inscription_cours_sq_coll ";
			sql +="before insert or update on inscriptionCoursSqCollectif ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="nb_pers int; ";
			sql +="cpt int; ";
			sql +="typ varchar2(30); ";
			sql +="begin ";
			sql +="id_c := :new.id_cours; ";
			sql +="select type_de_cours into typ from coursSquash where id_cours=id_c; ";
			sql +="if(typ!='collectif') then ";
			sql +="raise_application_error(-20501,'erreur vous essayez de vous inscrire a un cours individuel'); ";
			sql +="end if; ";
			sql +="select nb_personne into nb_pers from coursSquashCollectif where id_cours=id_c; ";
			sql +="select count(num_adherent) into cpt from inscriptionCoursSqCollectif where  id_cours=id_c; ";
			sql +="if(cpt>=nb_pers) then ";
			sql +="raise_application_error(-20501,'erreur ce cours est plein'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";	
			statement.executeQuery(sql);
			System.out.println("Trigger inscriptionCoursSqCollectif cr�e");
			c.commit();
			
			sql = "create or replace trigger tg_insc_cours_sq_coll_forfait ";
			sql +="before insert or update on inscriptionCoursSqCollectif ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="nb_pers int; ";
			sql +="cpt int; ";
			sql +="num_ad int; ";
			sql +="jour_cours int; ";
			sql +="deb_trimestre date; ";
			sql +="fin_trimestre date; ";
			sql +="date_deb_forfait date; ";
			sql +="date_fin_forfait date; ";
			sql +="nb_forfait int; ";
			sql +="res int; ";
			sql +="nb_ad int; ";
			sql +="begin ";
			sql +="id_c := :new.id_cours; ";
			sql +="num_ad := :new.num_adherent; ";
			sql +="select jour, debut_trimestre into jour_cours,deb_trimestre from creneauCoursSqColl where id_cours=id_c; ";
			sql +="fin_trimestre := deb_trimestre+90; ";
			sql +="select count(*) into nb_forfait from souscription where type_forfait='cours groupe squash' and num_adherent=num_ad; ";
			sql +="if(nb_forfait>0) then ";
			sql +="select date_debut,date_fin into date_deb_forfait,date_fin_forfait from souscription where type_forfait='cours groupe squash' and num_adherent=num_ad; ";
			sql +="if(not((date_deb_forfait<deb_trimestre and date_fin_forfait<fin_trimestre) or (date_deb_forfait=deb_trimestre and (date_fin_forfait=fin_trimestre or date_fin_forfait<fin_trimestre)) or (date_deb_forfait>deb_trimestre and (date_fin_forfait=fin_trimestre or date_fin_forfait>fin_trimestre or date_fin_forfait<fin_trimestre)))) then "; 
			sql +="raise_application_error(-20301,'erreur dates de forfait non valides'); ";
			sql +="end if; ";
			sql +="elsif (nb_forfait=0) then ";
			sql +="raise_application_error(-20301,'erreur pas de forfait pour cet adherent'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger inscriptionCoursSqCollectif_forfait cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_insc_cours_sq_coll_adh ";
			sql +="before insert or update on inscriptionCoursSqCollectif ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="jour_c int; ";
			sql +="hd int; ";
			sql +="hf int; ";
			sql +="res_col int; ";
			sql +="res_ind int; ";
			sql +="res_total int; ";
			sql +="jour_inter varchar2(3); ";
			sql +="num_ad int; ";
			sql +="trimestre date; ";
			sql +="mois_fin_trimestre varchar2(2); ";
			sql +="annee int; ";
			sql +="fin_trimestre varchar2(10); ";
			sql +="concat_mois varchar2(5); ";
			sql +="concat_annee varchar2(5); ";
			sql +="date_fin_trimestre date; ";
			sql +="jour_trim varchar2(2); ";
			sql +="nb_terr_res int; ";
			sql +="nb_reservation int; ";
			sql +="personne int; ";
			sql +="begin ";
			sql +="id_c:=:new.id_cours; ";
			sql +="num_ad := :new.num_adherent; ";
			sql +="select jour,heure_debut,heure_fin,debut_trimestre,to_number(to_char(debut_trimestre,'YYYY')) into jour_c,hd,hf,trimestre,annee from creneauCoursSqColl where  id_cours=id_c; ";
			sql +="select count(*) into res_col from creneauCoursSqColl c, inscriptionCoursSqCollectif i where  num_adherent=num_ad and c.id_cours=i.id_cours and jour=jour_c and (heure_debut=hd or (heure_debut>hd and heure_debut<hf) or (hd>heure_debut and hd<heure_fin)) and debut_trimestre=trimestre; ";
			sql +="if(jour_c=0) then ";
			sql +="jour_inter:='mon'; ";
			sql +="elsif (jour_c=1) then ";
			sql +="jour_inter:='tue'; ";
			sql +="elsif (jour_c=2) then ";
			sql +="jour_inter:='wed'; ";
			sql +="elsif (jour_c=3) then ";
			sql +="jour_inter:='thu'; ";
			sql +="elsif (jour_c=4) then ";
			sql +="jour_inter:='fri'; ";
			sql +="elsif (jour_c=5) then ";
			sql +="jour_inter:='sat'; ";
			sql +="elsif (jour_c=6) then ";
			sql +="jour_inter:='sun'; ";
			sql +="end if; ";
			sql +="if(to_char(trimestre,'MM')=01) then ";
			sql +="mois_fin_trimestre:='04'; ";
			sql +="elsif(to_char(trimestre,'MM')=04) then "; 
			sql +="mois_fin_trimestre:='07'; ";
			sql +="elsif(to_char(trimestre,'MM')=07) then "; 
			sql +="mois_fin_trimestre:='10'; ";
			sql +="elsif(to_char(trimestre,'MM')=10) then "; 
			sql +="mois_fin_trimestre:='01'; ";
			sql +="annee:=annee+1; ";
			sql +="end if; ";
			sql +="jour_trim:='01'; ";
			sql +="concat_mois := concat(jour_trim,mois_fin_trimestre); ";
			sql +="fin_trimestre :=  concat(concat_mois,annee); ";
			sql +="date_fin_trimestre:= to_date(fin_trimestre); ";
			sql +="select count(*) into res_ind from creneauCoursSqIndividuel c, creneauCoursSqColl col, coursSquashIndividuel ind where  ind.num_adherent=num_ad and ind.id_cours=c.id_cours and col.id_cours=id_c and to_char(c.date_cours,'D')=jour_c-1 and (c.heure_debut=hd or (c.heure_debut>hd and c.heure_debut<hf) or (hd>c.heure_debut and hd<c.heure_fin)) and debut_trimestre=trimestre and (c.date_cours>trimestre and c.date_cours<date_fin_trimestre); ";
			sql +="select id_personne into personne from adherent where num_adherent=num_ad; ";
			sql +="select count(*) into nb_reservation from reservationTerrainSquash res, creneauCoursSqColl cren where id_personne=personne and to_char(date_location,'D')=jour_c-1 and ((cren.heure_debut=res.heure_debut or cren.heure_debut=res.heure_debut-1) or ((res.heure_fin=cren.heure_debut and minute_fin!=0) or res.heure_fin=cren.heure_debut+1)); "; 
			sql +="if(nb_reservation>0)then ";
			sql +="raise_application_error(-20501,'erreur cet adherent reserve deja un terrain pour cet horraire'); ";
			sql +="end if; ";
			sql +="res_total:= res_col+res_ind; ";
			sql +="if(res_total>0) then ";
			sql +="raise_application_error(-20301,'erreur adherent deja inscrit dans un autre cours pour ce creneau'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger inscriptionCoursSqCollectif_ adh cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : creneauCoursSqhColl                                           */
			/*==============================================================*/
			sql = "drop table creneauCoursSqColl cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table creneauCoursSqColl detruite");
			}
			catch (Exception e)
			{
				System.out.println("creneauCoursSqColl :"+e );
			}
			sql ="create table creneauCoursSqColl";
			sql +="(";
			sql +="id_cours int,";
			sql +="num_terrain_squash int,";
			sql +="jour int,";
			sql +="check(jour between 0 and 6),";
			sql +="heure_debut int,";
			sql +="check(heure_debut between 9 and 20),";
			sql +="check(heure_fin=heure_debut+2),";
			sql +="heure_fin int,";
			sql +="check(heure_fin between 10 and 22),";
			sql +="debut_trimestre date,";
			sql +="constraint pk_creneau_cours_sq_c primary key (id_cours),";
			sql +="constraint fk_creneau_cours_sq_cours_c foreign key(id_cours) references coursSquash(id_cours),";
			sql +="constraint fk_creneau_cours_sq_terrain_c foreign key(num_terrain_squash) references terrainSquash(num_terrain_squash)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table creneauCoursSqColl cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_creneau_cours_sq_coll ";
			sql +="before insert or update on creneauCoursSqColl ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="typ varchar2(10); ";
			sql +="hd int; ";
			sql +="hf int; ";
			sql +="semaine int; ";
			sql +="taille int; ";
			sql +="nb_creneau int; ";
			sql +="dat_trim date; ";
			sql +="jour_c int; ";
			sql +="mois_trimestre int; ";
			sql +="jour_trimestre int; ";
			sql +="begin  ";
			sql +="id_c := :new.id_cours; ";
			sql +="hd := :new.heure_debut; ";
			sql +="hf := :new.heure_fin; ";
			sql +="jour_c := :new.jour; ";
			sql +="mois_trimestre:= to_char(:new.debut_trimestre,'MM'); ";
			sql +="jour_trimestre := to_char(:new.debut_trimestre,'DD'); ";
			sql +="if(not((mois_trimestre=01 or mois_trimestre=04 or mois_trimestre=07 or mois_trimestre=10) and jour_trimestre=01)) then ";
			sql +="raise_application_error(-20501,'erreur, les creneaux horraires ne peuvent etre changer que par trimestre'); ";
			sql +="end if; ";
			sql +="select count(*) into taille from creneauCoursSqColl; ";
			sql +="if(taille!=0) then ";
			sql +="select type_de_cours into typ from coursSquash where id_cours=id_c; ";
			sql +="if(typ!='collectif') then ";
			sql +="raise_application_error(-20501, 'erreur type_cours=individuel au lieu de collectif'); ";
			sql +="end if; ";
			sql +="select count(id_cours) into nb_creneau from creneauCoursSqColl where jour=jour_c and (heure_debut=hd or (hd>heure_debut and hd<heure_fin) or (hf>heure_debut and hf<heure_fin)); ";
			sql +="if(nb_creneau>=3) then ";
			sql +="raise_application_error(-20501,'erreur trop de cours groupe pour ce creneau'); ";
			sql +="end if; ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursSqColl cr�e");
			c.commit();
			
			
			sql ="create or replace trigger tg_cren_cours_sq_co_date_terr ";
			sql +="before insert or update on creneauCoursSqColl ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_c int; ";
			sql +="jour_c int; ";
			sql +="jour_coll varchar2(3); ";
			sql +="hd int; ";
			sql +="hf int; ";
			sql +="num_terr int; ";
			sql +="res_col int; ";
			sql +="res_ind int; ";
			sql +="res_total_terr int; ";
			sql +="taille int; ";
			sql +="prof int; ";
			sql +="res_prof_ind int; ";
			sql +="res_prof_col int; ";
			sql +="res_total_prof int; ";
			sql +="res_adh_ind int; ";
			sql +="res_adh_coll int; ";
			sql +="jour_inter varchar2(3); ";
			sql +="trimestre date; ";
			sql +="mois_fin_trimestre varchar2(2); ";
			sql +="annee int; ";
			sql +="fin_trimestre varchar2(10); ";
			sql +="concat_mois varchar2(5); ";
			sql +="concat_annee varchar2(5); ";
			sql +="date_fin_trimestre date; ";
			sql +="jour_trim varchar2(2); ";
			sql +="nb_terr_res int; ";
			sql +="begin ";
			sql +="id_c:=:new.id_cours; ";
			sql +="hd := :new.heure_debut; ";
			sql +="hf := :new.heure_fin; ";
			sql +="jour_c := :new.jour; ";
			sql +="num_terr := :new.num_terrain_squash; ";
			sql +="trimestre := :new.debut_trimestre; ";
			sql +="annee := to_number(to_char(trimestre,'YYYY')); ";
			sql +="select id_prof into prof from coursSquash where id_cours=id_c; ";
			sql +="select count(*) into res_col from creneauCoursSqColl where jour=jour_c and num_terrain_squash=num_terr and (heure_debut=hd or (heure_debut>hd and heure_debut<hf) or (hd>heure_debut and hd<heure_fin)) and debut_trimestre=trimestre; ";
			sql +="select count(*) into res_prof_col from creneauCoursSqColl col, coursSquash cs where id_prof=prof and (heure_debut=hd or (heure_debut>hd and heure_debut<hf) or (hd>heure_debut and hd<heure_fin)) and jour=jour_c and col.id_cours=cs.id_cours and debut_trimestre=trimestre; ";
			sql +="if(jour_c=0) then ";
			sql +="jour_inter:='mon'; ";
			sql +="elsif (jour_c=1) then ";
			sql +="jour_inter:='tue'; ";
			sql +="elsif (jour_c=2) then ";
			sql +="jour_inter:='wed'; ";
			sql +="elsif (jour_c=3) then ";
			sql +="jour_inter:='thu'; ";
			sql +="elsif (jour_c=4) then ";
			sql +="jour_inter:='fri'; ";
			sql +="elsif (jour_c=5) then ";
			sql +="jour_inter:='sat'; ";
			sql +="elsif (jour_c=6) then ";
			sql +="jour_inter:='sun'; ";
			sql +="end if; ";
			sql +="if(to_char(trimestre,'MM')=01) then ";
			sql +="mois_fin_trimestre:='04'; ";
			sql +="elsif(to_char(trimestre,'MM')=04) then "; 
			sql +="mois_fin_trimestre:='07'; ";
			sql +="elsif(to_char(trimestre,'MM')=07) then "; 
			sql +="mois_fin_trimestre:='10'; ";
			sql +="elsif(to_char(trimestre,'MM')=10) then "; 
			sql +="mois_fin_trimestre:='01'; ";
			sql +="annee:=annee+1; ";
			sql +="end if; ";
			sql +="jour_trim:='01'; ";
			sql +="concat_mois := concat(jour_trim,mois_fin_trimestre); ";
			sql +="fin_trimestre :=  concat(concat_mois,annee); ";
			sql +="date_fin_trimestre:= to_date(fin_trimestre); ";
			sql +="select count(*) into res_ind from creneauCoursSqIndividuel where to_char(date_cours,'dy')=jour_inter and num_terrain_squash=num_terr and (heure_debut=hd or (heure_debut>hd and heure_debut<hf) or (hd>heure_debut and hd<heure_fin)) and (date_cours>trimestre and date_cours<date_fin_trimestre); ";
			sql +="select count(*) into res_prof_ind from creneauCoursSqIndividuel ind, coursSquash cs where id_prof=prof and (heure_debut=hd or (heure_debut>hd and heure_debut<hf) or (hd>heure_debut and hd<heure_fin)) and to_char(date_cours,'dy')=jour_inter and ind.id_cours=cs.id_cours and (date_cours>trimestre and date_cours<date_fin_trimestre); ";
			sql +="res_total_terr:=res_col+res_ind; ";
			sql +="if(res_total_terr>0) then ";
			sql +="raise_application_error(-20301,'erreur creneau sq terrain deja reserver pour un cours collectif'); ";
			sql +="end if; ";
			sql +="select count(*) into nb_terr_res from reservationTerrainSquash where num_terrain_squash=num_terr and to_char(date_location,'dy')=jour_inter and (heure_debut=hd or heure_debut=hd+1 or (heure_fin=hd and minute_fin!=0) or heure_fin=hd+1); ";
			sql +="if(nb_terr_res>0) then ";
			sql +="raise_application_error(-20301,'erreur creneau sq date terrain deja pris'); ";
			sql +="end if; ";
			sql +="res_total_prof:=res_prof_col+res_prof_ind; ";
			sql +="if(res_total_prof>0) then ";
			sql +="raise_application_error(-20301,'erreur creneau sq prof deja occupe'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursSquash_date_terr cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : salleGym                                           */
			/*==============================================================*/
			sql = "drop table salleGym cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table salleGym detruite");
			}
			catch (Exception e)
			{
				System.out.println("salleGym :"+e );
			}
			sql ="create table salleGym";
			sql +="(";
			sql +="id_salle int,";
			sql +="check(id_salle between 1 and 10),";
			sql +="capacite int,";
			sql +="constraint pk_salle_gym primary key (id_salle)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table salleGym cr�e");
			c.commit();
			
			/*==============================================================*/
			/* Table : coursGym                                           */
			/*==============================================================*/
			sql = "drop table coursGym cascade constraint";
			try {
				statement.executeUpdate(sql);
				System.out.println("Table coursGym detruite");
			}
			catch (Exception e)
			{
				System.out.println("coursGym :"+e );
			}
			sql ="create table coursGym";
			sql +="(";
			sql +="type_cours varchar2(30),";
			sql +="id_prof int,";
			sql +="constraint pk_cours_gym primary key (type_cours),";
			sql +="constraint fk_cours_gym_prof foreign key(id_prof) references personnelSportif(id_prof)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table coursGym cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_cours_gym_prof ";
			sql +="before insert or update on coursGym ";
			sql +="for each row ";
			sql +="declare ";
			sql +="id_p int; ";
			sql +="typ_p varchar2(30); ";
			sql +="begin ";
			sql +="id_p:= :new.id_prof; ";
			sql +="select type_sport into typ_p from personnelSportif where id_prof=id_p; ";
			sql +="if(typ_p='squash') then ";
			sql +="raise_application_error(-20501,'erreur type_prof=squash'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger coursGym_prof cr�e");
			c.commit();
			
			
			
			/*==============================================================*/
			/* Table : creneauCoursGym                                           */
			/*==============================================================*/
			sql = "drop table creneauCoursGym cascade constraint";
			try {
				statement.executeQuery(sql);
				System.out.println("Table creneauCoursGym detruite");
			}
			catch (Exception e)
			{
				System.out.println("creneauCoursGym :"+e );
			}
			sql ="create table creneauCoursGym";
			sql +="(";
			sql +="id_salle int,";
			sql +="type_cours varchar2(30),";
			sql +="date_cours date,";
			sql +="heure_debut int,";
			sql +="check(heure_debut between 9 and 21), ";
			sql +="heure_fin int,";
			sql +="check(heure_fin between 10 and 22),";
			sql +="check(heure_fin=heure_debut+1),";
			sql +="debut_trimestre date,";
			sql +="constraint pk_creneau_cours_gym primary key (id_salle,date_cours,heure_debut),";
			sql +="constraint fk_creneau_cours_gym_salle foreign key(id_salle) references salleGym(id_salle),";
			sql +="constraint fk_creneau_cours_gym_cours foreign key(type_cours) references coursGym(type_cours)";
			sql +=")";
			statement.executeQuery(sql);
			System.out.println("Table creneauCoursGym cr�e");
			c.commit();
			
			sql= "create or replace trigger tg_creneau_gym_trimestre ";
			sql +="before insert or update on creneauCoursGym ";
			sql +="for each row ";
			sql +="declare ";
			sql +="jour_trim varchar2(2); ";
			sql +="hd int; ";
			sql +="hf int; ";
			sql +="date_c date; ";
			sql +="mois_trimestre int; ";
			sql +="jour_trimestre int; ";
			sql +="trimestre date; ";
			sql +="mois_fin_trimestre varchar2(2); ";
			sql +="annee int; ";
			sql +="fin_trimestre varchar2(10); ";
			sql +="concat_mois varchar2(5); ";
			sql +="concat_annee varchar2(5); ";
			sql +="date_fin_trimestre date; ";
			sql +="cpt int; ";
			sql +="begin  ";
			sql +="hd := :new.heure_debut; ";
			sql +="hf := :new.heure_fin; ";
			sql +="date_c := :new.date_cours; ";
			sql +="trimestre := :new.debut_trimestre; ";
			sql +="annee := to_number(to_char(trimestre,'YYYY')); ";
			sql +="mois_trimestre:= to_char(:new.debut_trimestre,'MM'); ";
			sql +="jour_trimestre := to_char(:new.debut_trimestre,'DD'); ";
			sql +="if(not((mois_trimestre=01 or mois_trimestre=04 or mois_trimestre=07 or mois_trimestre=10) and jour_trimestre=01)) then ";
			sql +="raise_application_error(-20501,'erreur, les creneaux horraires ne peuvent etre changer que par trimestre'); ";
			sql +="end if; ";
			sql +="if(to_char(trimestre,'MM')=01) then ";
			sql +="mois_fin_trimestre:='04'; ";
			sql +="elsif(to_char(trimestre,'MM')=04) then "; 
			sql +="mois_fin_trimestre:='07'; ";
			sql +="elsif(to_char(trimestre,'MM')=07) then "; 
			sql +="mois_fin_trimestre:='10'; ";
			sql +="elsif(to_char(trimestre,'MM')=10) then "; 
			sql +="mois_fin_trimestre:='01'; ";
			sql +="annee:=annee+1; ";
			sql +="end if; ";
			sql +="jour_trim:='01'; ";
			sql +="concat_mois := concat(jour_trim,mois_fin_trimestre); ";
			sql +="fin_trimestre :=  concat(concat_mois,annee); ";
			sql +="date_fin_trimestre:= to_date(fin_trimestre); ";
			sql +="if(date_c<trimestre or date_c>date_fin_trimestre) then ";
			sql +="raise_application_error(-20501,'erreur, la date du cours n est pas comprise dans le trimestre'); ";
			sql +="end if; ";
			sql +="exception ";
			sql +="when no_data_found then ";
			sql +="raise_application_error(-20301,'aucune donnee trouvee'); ";
			sql +="end; ";
			statement.executeQuery(sql);
			System.out.println("Trigger creneauCoursGym_trimestre cr�e");
			c.commit();
			

			sql = "alter session set NLS_DATE_FORMAT='DD-MM-YYYY'";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Alter Table");
			
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (1,'novak','audrey','08-04-1987','8 rue fournel MEZE')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (2,'novak','diane','08-04-1987','8 rue fournel MEZE')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (3,'maneschi','romain','12-02-1986','8 ave pr grasser MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (4,'maillet','laurent','27-11-1987','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (5,'fhal','john','13-12-1986','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (6,'sauvan','william','08-10-1987','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (7,'konig','melanie','11-11-1988','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (8,'bregu','erion','12-02-1986','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (9,'smith','john','12-02-1986','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personne(id_personne,nom,prenom,dateDeNaissance,adresse) values (10,'pitt','brad','12-02-1986','8 ave pr grasset MONTPELLIER')";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion personne r�ussi");
			

			sql = "insert into adherent(num_adherent,id_personne) values (1,1)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into adherent(num_adherent,id_personne) values (2,2)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into adherent(num_adherent,id_personne) values (3,3)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into adherent(num_adherent,id_personne) values (4,4)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into adherent(num_adherent,id_personne) values (5,5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into adherent(num_adherent,id_personne) values (6,6)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion adh�rent r�ussi");
			
			sql = "insert into personnelAdministratif(num_employe,id_personne,affectation) values (1,1,'secretaire')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personnelAdministratif(num_employe,id_personne,affectation) values (2,2,'directeur')";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion personnel administratif r�ussi");
			
			sql = "insert into personnelSportif(id_prof,id_personne,type_sport) values (1,1,'gym')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personnelSportif(id_prof,id_personne,type_sport) values (2,2,'squash')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personnelSportif(id_prof,id_personne,type_sport) values (3,3,'squash')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personnelSportif(id_prof,id_personne,type_sport) values (4,4,'squash')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into personnelSportif(id_prof,id_personne,type_sport) values (5,5,'squash')";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion personnel sportif r�ussi");
			
			sql = "insert into materielLocation(num_materiel,type_materiel,date_achat,etat,prix_base) values (1,'raquette','01-01-2008','etat',5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into materielLocation(num_materiel,type_materiel,date_achat,etat,prix_base) values (2,'raquette','01-01-2008','etat',5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into materielLocation(num_materiel,type_materiel,date_achat,etat,prix_base) values (3,'balle','01-01-2008','etat',5)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion mat�riel location r�ussi");

			sql = "insert into location values (1,'01-12-2009',14,15,tab_materiel_loue(type_materiel_loue(1),type_materiel_loue(3)),1,5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into location values (8,'01-12-2009',15,16,tab_materiel_loue(type_materiel_loue(1) ),1,5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into location values (7,'02-12-2009',14,15,tab_materiel_loue(type_materiel_loue(2)),2,5)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion location r�ussi");
			
			sql = "insert into materielVente(type_materiel,description,quantite,prix_base) values ('raquette','pour jouer au tennis',8,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into materielVente(type_materiel,description,quantite,prix_base) values ('balles','pour jouer au tennis',8,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into materielVente(type_materiel,description,quantite,prix_base) values ('filet','pour jouer au tennis',8,30)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion materiel vente r�ussi");
			
			sql = "insert into vente values (3,tab_materiel_achete(type_materiel_achete('raquette',8,30),type_materiel_achete('raquette',2,30)),'10-04-2000',2,50)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into vente values (4,tab_materiel_achete(type_materiel_achete('filet',8,30),type_materiel_achete('raquette',2,30)),'10-04-2000',1,50)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion vente r�ussi");
			
			sql = "insert into casier(num_casier) values (1)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into casier(num_casier) values (2)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into casier(num_casier) values (70)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into casier(num_casier) values (71)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into casier(num_casier) values (100)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion casier r�ussi");
			
			sql = "insert into reservationCasierAnnee(num_casier,num_adherent,annee) values (1,1,2010)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into reservationCasierAnnee(num_casier,num_adherent,annee) values (2,2,2010)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion Reservation Casier Annee r�ussi");
			
			sql = "insert into reservationCasierSeance(num_casier,date_location,heure_debut,heure_fin,id_personne,prix_paye) values (71,'3-03-2009',14,15,1,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into reservationCasierSeance(num_casier,date_location,heure_debut,heure_fin,id_personne,prix_paye) values (100,'3-03-2009',14,15,2,30)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion Reservation Casier Seance r�ussi");
			
			sql = "insert into forfait(type_forfait,prix_mois,prix_trimestre,prix_semestre,prix_an) values ('salle muscu',35,95,175,300)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into forfait(type_forfait,prix_mois,prix_trimestre,prix_semestre,prix_an) values ('cours gymnastique',45,120,210,400)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into forfait(type_forfait,prix_mois,prix_trimestre,prix_semestre,prix_an) values ('terrain squash',10,25,45,80)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into forfait(type_forfait,prix_mois,prix_trimestre,prix_semestre,prix_an) values ('cours particulier squash',75,215,410,800)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into forfait(type_forfait,prix_mois,prix_trimestre,prix_semestre,prix_an) values ('cours groupe squash',65,190,360,700)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion Forfait r�ussi");
			
			
			sql = "insert into souscription(type_forfait,num_adherent,date_debut,date_fin) values ('salle muscu',1,'30-12-2009','29-01-2010')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into souscription(type_forfait,num_adherent,date_debut,date_fin) values ('cours groupe squash',1,'01-12-2009','31-12-2009')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into souscription(type_forfait,num_adherent,date_debut,date_fin) values ('cours particulier squash',1,'5-01-2010','04-02-2010')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into souscription(type_forfait,num_adherent,date_debut,date_fin) values ('cours particulier squash',3,'5-01-2010','04-02-2010')";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into souscription(type_forfait,num_adherent,date_debut,date_fin) values ('cours groupe squash',3,'5-01-2010','04-02-2010')";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion souscription r�ussi");
			
			sql = "insert into terrainSquash(num_terrain_squash) values (1)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into terrainSquash(num_terrain_squash) values (2)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into terrainSquash(num_terrain_squash) values (3)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into terrainSquash(num_terrain_squash) values (4)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into terrainSquash(num_terrain_squash) values (5)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into terrainSquash(num_terrain_squash) values (6)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion terrainSquash r�ussi");
//
			
			sql ="insert into reservationTerrainSquash(num_terrain_squash,date_location,heure_debut,heure_fin,minute_debut,minute_fin,id_personne,prix_paye) values(2,'3-04-2010',14,15,20,00,1,30) ";
			statement.executeQuery(sql);
			c.commit();
			
			sql ="insert into reservationTerrainSquash(num_terrain_squash,date_location,heure_debut,heure_fin,minute_debut,minute_fin,id_personne,prix_paye) values(5,'3-04-2010',14,15,20,00,3,30) ";
			statement.executeQuery(sql);
			c.commit();
			
			sql ="insert into reservationTerrainSquash(num_terrain_squash,date_location,heure_debut,heure_fin,minute_debut,minute_fin,id_personne,prix_paye) values(6,'3-04-2010',14,15,20,00,9,30)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion reservation Terrain Squash r�ussi");
//			
//
			
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (1,5,'individuel') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (2,3,'individuel') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (3,3,'collectif') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (4,3,'collectif') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (5,2,'individuel') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (6,2,'individuel')  ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (7,2,'collectif') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (8,2,'individuel') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (9,4,'collectif') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquash(id_cours,id_prof,type_de_cours) values (10,4,'collectif')  ";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion cours Squash r�ussi");
			


			sql="insert into coursSquashIndividuel(id_cours,num_adherent) values (1,1) ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquashIndividuel(id_cours,num_adherent) values (2,3) ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquashIndividuel(id_cours,num_adherent) values (6,1) ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into coursSquashIndividuel(id_cours,num_adherent) values (5,1) ";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion cours Squash individuel r�ussi");
//			
//
			
			  sql="insert into creneauCoursSqIndividuel(id_cours,num_terrain_squash,date_cours,heure_debut,heure_fin) values (1,1,'05-01-2010',9,10) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqIndividuel(id_cours,num_terrain_squash,date_cours,heure_debut,heure_fin) values (5,1,'12-01-2010',11,12) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqIndividuel(id_cours,num_terrain_squash,date_cours,heure_debut,heure_fin) values (2,2,'12-01-2010',9,10) ";
			  statement.executeQuery(sql);
			  c.commit();
			  System.out.println("Insertion creneau Cours Squash r�ussi");
//			
//
			  
			  sql="insert into creneauCoursSqColl(id_cours,num_terrain_squash,jour,heure_debut,heure_fin,debut_trimestre) values (3,2,1,10,12,'01-10-2010') ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqColl(id_cours,num_terrain_squash,jour,heure_debut,heure_fin,debut_trimestre) values (4,1,0,9,11,'01-01-2010') ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqColl(id_cours,num_terrain_squash,jour,heure_debut,heure_fin,debut_trimestre) values (10,3,0,10,12,'01-01-2010') ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqColl(id_cours,num_terrain_squash,jour,heure_debut,heure_fin,debut_trimestre) values (9,5,5,10,12,'01-01-2010')";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into creneauCoursSqColl(id_cours,num_terrain_squash,jour,heure_debut,heure_fin,debut_trimestre) values (7,1,2,9,11,'01-01-2010') ";
			  statement.executeQuery(sql);
			  c.commit();
			  System.out.println("Insertion Creneau Cours Squash Collectif r�ussi");
//			
//
			  sql="insert into coursSquashCollectif(id_cours,nb_personne) values (3,2) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into coursSquashCollectif(id_cours,nb_personne) values (4,5) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into coursSquashCollectif(id_cours,nb_personne) values (10,5) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into coursSquashCollectif(id_cours,nb_personne) values (7,2) ";
			  statement.executeQuery(sql);
			  c.commit();
			  sql="insert into coursSquashCollectif(id_cours,nb_personne) values (9,2) ";
			  statement.executeQuery(sql);
			  c.commit();
   			  System.out.println("Insertion Cours Squash Collectifs r�ussi");
//			
   			  
   			 sql="insert into inscriptionCoursSqCollectif(id_cours,num_adherent) values (3,1) ";
   			statement.executeQuery(sql);
			c.commit();
   			sql="insert into inscriptionCoursSqCollectif(id_cours,num_adherent) values (3,3) ";
   			statement.executeQuery(sql);
			c.commit();
   			sql="insert into inscriptionCoursSqCollectif(id_cours,num_adherent) values (10,1) ";
   			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion Inscription Cours Squash Collectifs r�ussi");

			
			sql = "insert into salleGym(id_salle,capacite) values (1,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (2,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (3,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (4,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (5,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (6,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (7,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (8,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (9,30)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into salleGym(id_salle,capacite) values (10,30)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion salle Gym r�ussi");
//			
			sql = "insert into coursGym(type_cours,id_prof) values ('step',1)";
			statement.executeQuery(sql);
			c.commit();
			sql = "insert into coursGym(type_cours,id_prof) values ('cardio',1)";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion cours Gym r�ussi");
//
			sql="insert into creneauCoursGym(id_salle,type_cours,date_cours,heure_debut,heure_fin,debut_trimestre) values(1,'step','4-07-2010',14,15,'01-07-2010')  ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into creneauCoursGym(id_salle,type_cours,date_cours,heure_debut,heure_fin,debut_trimestre) values(2,'cardio','4-01-2010',14,15,'01-01-2010') ";
			statement.executeQuery(sql);
			c.commit();
			sql="insert into creneauCoursGym(id_salle,type_cours,date_cours,heure_debut,heure_fin,debut_trimestre) values(3,'cardio','4-01-2010',14,15,'01-01-2010') ";
			statement.executeQuery(sql);
			c.commit();
			System.out.println("Insertion Creneau Cours Gym r�ussi");

		}
		catch (Exception e){
			System.out.println("Erreur attrap� :"+e);
		}
		finally{
			if(statement != null)
				statement.close();
		}
	}
	
	public static void ajouterDroit(ArrayList<String> droits, String utilisateur) throws SQLException
	{
		SQL.ajouterDroit(droits, "personne", utilisateur);
		SQL.ajouterDroit(droits, "adherent", utilisateur);
		SQL.ajouterDroit(droits, "personnelAdministratif", utilisateur);
		SQL.ajouterDroit(droits, "personnelSportif", utilisateur);
		SQL.ajouterDroit(droits, "materielVente", utilisateur);
		SQL.ajouterDroit(droits, "materielLocation", utilisateur);
		SQL.ajouterDroit(droits, "location", utilisateur);
		SQL.ajouterDroit(droits, "vente", utilisateur);
		SQL.ajouterDroit(droits, "casier", utilisateur);
		SQL.ajouterDroit(droits, "reservationCasierAnnee", utilisateur);
		SQL.ajouterDroit(droits, "reservationCasierSeance", utilisateur);
		SQL.ajouterDroit(droits, "forfait", utilisateur);
		SQL.ajouterDroit(droits, "souscription", utilisateur);
		SQL.ajouterDroit(droits, "historiqueSouscription", utilisateur);
		SQL.ajouterDroit(droits, "terrainSquash", utilisateur);
		SQL.ajouterDroit(droits, "reservationTerrainSquash", utilisateur);
		SQL.ajouterDroit(droits, "coursSquash", utilisateur);
		SQL.ajouterDroit(droits, "coursSquashIndividuel", utilisateur);
		SQL.ajouterDroit(droits, "coursSquashCollectif", utilisateur);
		SQL.ajouterDroit(droits, "inscriptionCoursSqCollectif", utilisateur);
		SQL.ajouterDroit(droits, "creneauCoursSqIndividuel", utilisateur);
		SQL.ajouterDroit(droits, "creneauCoursSqColl", utilisateur);
		SQL.ajouterDroit(droits, "salleGym", utilisateur);
		SQL.ajouterDroit(droits, "coursGym", utilisateur);
		SQL.ajouterDroit(droits, "creneauCoursGym", utilisateur);
	}
	
	public static void ajouterDroit(ArrayList<String> droits, String table, String utilisateur) throws SQLException{
		String sql="grant ";
		int i=0;
		for(String droit : droits)
		{
			if(i == 0)
				sql+=droit+" ";
			else
				sql+=","+droit+" ";
			i++;
		}
		
		sql += "on ";
		sql+=table+" ";
		sql += "to ";
		sql+=utilisateur;
		
		System.out.println(sql);
		Statement statement = SQL.getConnexion().createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.executeQuery(sql);
	}
	
	public static void supprimerDroit(ArrayList<String> droits, String utilisateur) throws SQLException
	{
		SQL.supprimerDroit(droits, "personne", utilisateur);
		SQL.supprimerDroit(droits, "adherent", utilisateur);
		SQL.supprimerDroit(droits, "personnelAdministratif", utilisateur);
		SQL.supprimerDroit(droits, "personnelSportif", utilisateur);
		SQL.supprimerDroit(droits, "materielVente", utilisateur);
		SQL.supprimerDroit(droits, "materielLocation", utilisateur);
		SQL.supprimerDroit(droits, "location", utilisateur);
		SQL.supprimerDroit(droits, "vente", utilisateur);
		SQL.supprimerDroit(droits, "casier", utilisateur);
		SQL.supprimerDroit(droits, "reservationCasierAnnee", utilisateur);
		SQL.supprimerDroit(droits, "reservationCasierSeance", utilisateur);
		SQL.supprimerDroit(droits, "forfait", utilisateur);
		SQL.supprimerDroit(droits, "souscription", utilisateur);
		SQL.supprimerDroit(droits, "historiqueSouscription", utilisateur);
		SQL.supprimerDroit(droits, "terrainSquash", utilisateur);
		SQL.supprimerDroit(droits, "reservationTerrainSquash", utilisateur);
		SQL.supprimerDroit(droits, "coursSquash", utilisateur);
		SQL.supprimerDroit(droits, "coursSquashIndividuel", utilisateur);
		SQL.supprimerDroit(droits, "coursSquashCollectif", utilisateur);
		SQL.supprimerDroit(droits, "inscriptionCoursSqCollectif", utilisateur);
		SQL.supprimerDroit(droits, "creneauCoursSqIndividuel", utilisateur);
		SQL.supprimerDroit(droits, "creneauCoursSqColl", utilisateur);
		SQL.supprimerDroit(droits, "salleGym", utilisateur);
		SQL.supprimerDroit(droits, "coursGym", utilisateur);
		SQL.supprimerDroit(droits, "creneauCoursGym", utilisateur);
	}
	
	public static void supprimerDroit(ArrayList<String> droits, String table, String utilisateur) throws SQLException{
		String sql="revoke ";
		int i=0;
		for(String droit : droits)
		{
			if(i == 0)
				sql+=droit+" ";
			else
				sql+=","+droit+" ";
			i++;
		}
		
		sql += "on ";
		sql+=table+" ";
		sql += "from ";
		sql+=utilisateur;
		
		System.out.println(sql);
		Statement statement = SQL.getConnexion().createStatement( ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		statement.executeQuery(sql);
	}
	
	public static boolean estConnecte()
	{
		return (connexion != null);
	}
	
	public static Connection getConnexion()  {return connexion;}
	public static String getMotDePasse(){ return mot_de_passe;}
	public static String getUtilisateur(){ return utilisateur;}
	public String getUrl(){ return url;}
}
