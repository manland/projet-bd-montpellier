package Graphique;

public class Traductions {
	private static String fr_defaut = "Aucune traduction";
	private static String en_defaut = "No traduction";
	private static String it_defaut = "Non tradotto";
	private static String al_defaut = "E paperkthyer";
	private static String error = "Error";
	
	static public String mainWindow(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("constructeur_connexionbd")) {
				return "Connection BD";
			}
			else if(variable.equals("ajoutertab_connexionbd")) {
				return "Connection BD";
			}
			else if(variable.equals("ajoutertab_racine")) {//1ere vue peut être Home en anglais
				return "Racine";
			}
			else {
				return fr_defaut;
			}
		}
		else if(langue.equals("en")) {
			if(variable.equals("constructeur_connexionbd")) {
				return "Database Connection";
			}
			else if(variable.equals("ajoutertab_connexionbd")) {
				return "Database Connection";
			}
			else if(variable.equals("ajoutertab_racine")) {//1ere vue peut être Home en anglais
				return "Home";
			}
			else {
				return en_defaut;
			}
		}
		else if(langue.equals("it")) {
			if(variable.equals("constructeur_connexionbd")) {
				return "Conneti al database";
			}
			else if(variable.equals("ajoutertab_connexionbd")) {
				return "Conneti al database";
			}
			else if(variable.equals("ajoutertab_racine")) {//1ere vue peut �tre Home en anglais
				return "Prima paggina";
			}
			else {
				return it_defaut;
			}
		}
		else if(langue.equals("al")) {
			if(variable.equals("constructeur_connexionbd")) {
				return "Lidhu me Bazen e te dhenave";
			}
			else if(variable.equals("ajoutertab_connexionbd")) {
				return "Lidhu me Bazen e te dhenave";
			}
			else if(variable.equals("ajoutertab_racine")) {//1ere vue peut être Home en anglais
				return "Faqja e pare";
			}
			else {
				return al_defaut;
			}
		}
		else {
			return error;
		}
	}
	
	
	static public String [] bouttonPremiereVue(String langue, String variable) {
		if(langue.equals("fr")) {//Pour toutes les langues car sert au skin
			if(variable.equals("personnes")) {
				String [] s = {"Personnes", "Personnes", "Personnels", "Utilisateurs"}; 
				return s;
			}
			else if(variable.equals("batiments")) {
				String [] s = {"Batiments", "Squash", "Gymnastique", "Casier", "Brasserie"};
				return s;
			}
			else if(variable.equals("euros")) {
				String [] s = {"Euros", "Vente", "Location"};
				return s;
			}
			else if(variable.equals("client")) {
				String [] s = {"Client Internet", "Lancer"};
				return s;
			}
			else if(variable.equals("options")) {
				String [] s = {"Options", "Parametres", "Deconnection"};
				return s;
			}
			else {
				String [] s = {fr_defaut};
				return s;
			}
		}
		
		else if(langue.equals("en")) {//Pour toutes les langues car sert au skin
			if(variable.equals("personnes")) {
				String [] s = {"Persons", "Persons", "Staff", "Users"}; 
				return s;
			}
			else if(variable.equals("batiments")) {
				String [] s = {"Buildings", "Squash", "Gymnastics", "Rack", "Brewery"};
				return s;
			}
			else if(variable.equals("euros")) {
				String [] s = {"Euros", "Sale", "Renting"};
				return s;
			}
			else if(variable.equals("client")) {
				String [] s = {"Internet Guest", "Launch"};
				return s;
			}
			else if(variable.equals("options")) {
				String [] s = {"Options", "Settings", "Logout"};
				return s;
			}
			else {
				String [] s = {en_defaut};
				return s;
			}
		}
		
		else if(langue.equals("it")) {//Pour toutes les langues car sert au skin
			if(variable.equals("personnes")) {
				String [] s = {"Persone", "Persone", "Personale", "Utente"}; 
				return s;
			}
			else if(variable.equals("batiments")) {
				String [] s = {"Edifici", "Squash", "Ginnastica", "Armadietti", "Birreria"};
				return s;
			}
			else if(variable.equals("euros")) {
				String [] s = {"Euros", "Vendite", "Affitti"};
				return s;
			}
			else if(variable.equals("client")) {
				String [] s = {"Clienti Internet", "Avvia"};
				return s;
			}
			else if(variable.equals("options")) {
				String [] s = {"Opzioni", "Parametri", "Disconnetti"};
				return s;
			}
			else {
				String [] s = {it_defaut};
				return s;
			}
		}
		
		else if(langue.equals("al")) {//Pour toutes les langues car sert au skin
			if(variable.equals("personnes")) {
				String [] s = {"Persona", "Persona", "Personeli", "Perdorues"}; 
				return s;
			}
			else if(variable.equals("batiments")) {
				String [] s = {"Godina", "Skuosh", "Gjimnastike", "Dollape", "Birrari"};
				return s;
			}
			else if(variable.equals("euros")) {
				String [] s = {"Euro", "Shitje", "Marrje me qera"};
				return s;
			}
			else if(variable.equals("client")) {
				String [] s = {"Klient Interneti", "Fillo"};
				return s;
			}
			else if(variable.equals("options")) {
				String [] s = {"Opsione", "Te dhena", "Shkeput lidhjen"};
				return s;
			}
			else {
				String [] s = {al_defaut};
				return s;
			}
		}
		
		
		else {
			String [] s = {error};
			return s;
		}
	}
	
	static public String menuBar(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("constructeur_fichier")) {
				return "Fichier";
			}
			else if(variable.equals("constructeur_fichier_tootltip")) {
				return "Acc�der aux diff�rents param�tres";
			}
			else if(variable.equals("constructeur_nouvelletab")) {
				return "Nouvel onglet";
			}
			else if(variable.equals("constructeur_nouvelletab_tooltip")) {
				return "Nouvel onglet";
			}
			// TODO Auto-generated method stub
			else if(variable.equals("fichier_deconnexion")) {
				return "Déconnection";
			}
			else if(variable.equals("fichier_deconnexion_tooltip")) {
				return "Déconnectez-vous";
			}
			else if(variable.equals("fichier_quitter")) {
				return "Quitter";
			}
			else if(variable.equals("fichier_quitter_tooltip")) {
				return "Quittez le logiciel";
			}
			else if(variable.equals("constructeur_allera")) {
				return "Aller à";
			}
			else if(variable.equals("constructeur_allera_tooltip")) {
				return "Allez où vous voulez.";
			}
			else if(variable.equals("allera_personnels")) {
				return "Personnels";
			}
			else if(variable.equals("allera_personnels_tooltip")) {
				return "Gestion du personnel.";
			}
			else if(variable.equals("allera_adherents")) {
				return "Adhérents";
			}
			else if(variable.equals("allera_adherents_tooltip")) {
				return "Gestion des membres.";
			}
			else if(variable.equals("allera_squash")) {
				return "Squash";
			}
			else if(variable.equals("allera_squash_tooltip")) {
				return "Gestion des salles, réservation et cours de squash.";
			}
			else if(variable.equals("allera_gym")) {
				return "Gymnastique";
			}
			else if(variable.equals("allera_gym_tooltip")) {
				return "Gestion des salles et cours de gymnastique.";
			}
			else if(variable.equals("allera_casier")) {
				return "Casiers";
			}
			else if(variable.equals("allera_casier_tooltip")) {
				return "Gestion des casiers";
			}
			else if(variable.equals("allera_vente")) {
				return "Vente";
			}
			else if(variable.equals("allera_vente_tooltip")) {
				return "Gérez le stock ou vendez des produits.";
			}
			else if(variable.equals("allera_location")) {
				return "Location";
			}
			else if(variable.equals("allera_location_tooltip")) {
				return "Gérez le stock ou louez des produits.";
			}
			else if(variable.equals("allera_clientinternet")) {
				return "Client Internet";
			}
			else if(variable.equals("allera_clientinternet_tooltip")) {
				return "Ouvre un explorateur internet avec le site du client internet.";
			}
			else if(variable.equals("allera_options")) {
				return "Options";
			}
			else if(variable.equals("allera_options_tooltip")) {
				return "Paramètrez ce logiciel.";
			}
			else if(variable.equals("allera_deconnection")) {
				return "Déconnection";
			}
			else if(variable.equals("allera_deconnection_tooltip")) {
				return "Déconnectez vous.";
			}
			else if(variable.equals("constructeur_options")) {
				return "Options";
			}
			else if(variable.equals("constructeur_options_tooltip")) {
				return "Paramètrer le logiciel";
			}
			else if(variable.equals("options_skins")) {
				return "Skins";
			}
			else if(variable.equals("options_skins_tooltip")) {
				return "Choisissez le skin";
			}
			else if(variable.equals("options_langues")) {
				return "Langues";
			}
			else if(variable.equals("options_langues_tooltip")) {
				return "Choisissez la langue";
			}
			else if(variable.equals("options_options")) {
				return "Plus d'options";
			}
			else if(variable.equals("options_options_tooltip")) {
				return "Accédez à toutes les options";
			}
			else if(variable.equals("constructeur_aide")) {
				return "?";
			}
			else if(variable.equals("constructeur_aide_tooltip")) {
				return "Aide";
			}
			else if(variable.equals("aide_doc")) {
				return "Documentation";
			}
			else if(variable.equals("aide_doc_tooltip")) {
				return "Accédez à la documentation";
			}
			else if(variable.equals("aide_aproposde")) {
				return "A Propos De...";
			}
			else if(variable.equals("aide_aproposde_tooltip")) {
				return "A propos du logiciel";
			}
			else if(variable.equals("aproposde_titre")) {
				return "A propos de Musquash";
			}
			else if(variable.equals("aproposde_1")) {
				return "Ce logiciel...";
			}
			else if(variable.equals("aproposde_2")) {
				return "...";
			}
			else if(variable.equals("aproposde_3")) {
				return "...";
			}
			else if(variable.equals("aproposde_boutton")) {
				return "Continuer";
			}
			else if(variable.equals("cmd_racine")) {
				return "Racine";
			}
			else if(variable.equals("cmd_personnels")) {
				return "Personnels";
			}
			else if(variable.equals("cmd_adherents")) {
				return "Adhérents";
			}
			else if(variable.equals("cmd_squash")) {
				return "Squash";
			}
			else if(variable.equals("cmd_gym")) {
				return "Gymnastique";
			}
			else if(variable.equals("cmd_casiers")) {
				return "Casiers";
			}
			else if(variable.equals("cmd_vente")) {
				return "Vente";
			}
			else if(variable.equals("cmd_location")) {
				return "Location";
			}
			else if(variable.equals("cmd_options")) {
				return "Options";
			}
			else if(variable.equals("cmd_connexionbd")) {
				return "Connection BD";
			}
			else {
				return fr_defaut;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("constructeur_fichier")) {
				return "File";
			}
			else if(variable.equals("constructeur_fichier_tootltip")) {
				return "Access the various parameters";
			}
			else if(variable.equals("constructeur_nouvelletab")) {
				return "New tab";
			}
			else if(variable.equals("constructeur_nouvelletab_tooltip")) {
				return "New tab";
			}
			else if(variable.equals("fichier_deconnexion")) {
				return "Disconnect";
			}
			else if(variable.equals("fichier_deconnexion_tooltip")) {
				return "Disconnect from the database";
			}
			else if(variable.equals("fichier_quitter")) {
				return "Exit";
			}
			else if(variable.equals("fichier_quitter_tooltip")) {
				return "Quit the software";
			}
			else if(variable.equals("constructeur_allera")) {
				return "Move";
			}
			else if(variable.equals("constructeur_allera_tooltip")) {
				return "Navigate everywhere through your software.";
			}
			else if(variable.equals("allera_personnels")) {
				return "Staff";
			}
			else if(variable.equals("allera_personnels_tooltip")) {
				return "Staff or admin menus";
			}
			else if(variable.equals("allera_adherents")) {
				return "Member";
			}
			else if(variable.equals("allera_adherents_tooltip")) {
				return "Member administration.";
			}
			else if(variable.equals("allera_squash")) {
				return "Squash";
			}
			else if(variable.equals("allera_squash_tooltip")) {
				return "Edit your lessons, your schedule etc.";
			}
			else if(variable.equals("allera_gym")) {
				return "Gymnastics";
			}
			else if(variable.equals("allera_gym_tooltip")) {
				return "Edit your lessons, your schedule etc.";
			}
			else if(variable.equals("allera_casier")) {
				return "Rack";
			}
			else if(variable.equals("allera_casier_tooltip")) {
				return "Rent a rack, control your rack etc.";
			}
			else if(variable.equals("allera_vente")) {
				return "Sales";
			}
			else if(variable.equals("allera_vente_tooltip")) {
				return "Manage your products, buy new ones, or sale yours.";
			}
			else if(variable.equals("allera_location")) {
				return "Rentals";
			}
			else if(variable.equals("allera_location_tooltip")) {
				return "Manage stocks or rent equipements";
			}
			else if(variable.equals("allera_clientinternet")) {
				return "Internet Client";
			}
			else if(variable.equals("allera_clientinternet_tooltip")) {
				return "Get a look at your internet page in your favorite browser.";
			}
			else if(variable.equals("allera_options")) {
				return "Options";
			}
			else if(variable.equals("allera_options_tooltip")) {
				return "Customize your software";
			}
			else if(variable.equals("allera_deconnection")) {
				return "Disconnect";
			}
			else if(variable.equals("allera_deconnection_tooltip")) {
				return "Disconnect";
			}
			else if(variable.equals("constructeur_options")) {
				return "Options";
			}
			else if(variable.equals("constructeur_options_tooltip")) {
				return "Customize your software";
			}
			else if(variable.equals("options_skins")) {
				return "Skins";
			}
			else if(variable.equals("options_skins_tooltip")) {
				return "Set your skin";
			}
			else if(variable.equals("options_langues")) {
				return "Languages";
			}
			else if(variable.equals("options_langues_tooltip")) {
				return "Set your language";
			}
			else if(variable.equals("options_options")) {
				return "More options...";
			}
			else if(variable.equals("options_options_tooltip")) {
				return "Acces the option list";
			}
			else if(variable.equals("constructeur_aide")) {
				return "?";
			}
			else if(variable.equals("constructeur_aide_tooltip")) {
				return "Help";
			}
			else if(variable.equals("aide_doc")) {
				return "Documentation";
			}
			else if(variable.equals("aide_doc_tooltip")) {
				return "Documentation";
			}
			else if(variable.equals("aide_aproposde")) {
				return "About...";
			}
			else if(variable.equals("aide_aproposde_tooltip")) {
				return "About";
			}
			else if(variable.equals("aproposde_titre")) {
				return "About Musquash";
			}
			else if(variable.equals("aproposde_1")) {
				return "Software...";
			}
			else if(variable.equals("aproposde_2")) {
				return "...";
			}
			else if(variable.equals("aproposde_3")) {
				return "...";
			}
			else if(variable.equals("aproposde_boutton")) {
				return "Continue";
			}
			else if(variable.equals("cmd_racine")) {
				return "Home";
			}
			else if(variable.equals("cmd_personnels")) {
				return "Staff";
			}
			else if(variable.equals("cmd_adherents")) {
				return "Members";
			}
			else if(variable.equals("cmd_squash")) {
				return "Squash";
			}
			else if(variable.equals("cmd_gym")) {
				return "Gymnastics";
			}
			else if(variable.equals("cmd_casiers")) {
				return "Rack";
			}
			else if(variable.equals("cmd_vente")) {
				return "Sale";
			}
			else if(variable.equals("cmd_location")) {
				return "Rentals";
			}
			else if(variable.equals("cmd_options")) {
				return "Options";
			}
			else if(variable.equals("cmd_connexionbd")) {
				return "Database Connection";
			}
			else {
				return en_defaut;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("constructeur_fichier")) {
				return "File";
			}
			else if(variable.equals("constructeur_fichier_tootltip")) {
				return "Accedi ai parametri";
			}
			else if(variable.equals("constructeur_nouvelletab")) {
				return "Nuova etichetta";
			}
			else if(variable.equals("constructeur_nouvelletab_tooltip")) {
				return "Nuova etichetta";
			}
			else if(variable.equals("fichier_deconnexion")) {
				return "Disconnetti";
			}
			else if(variable.equals("fichier_deconnexion_tooltip")) {
				return "Disconnetersi dal banco dati";
			}
			else if(variable.equals("fichier_quitter")) {
				return "Esci";
			}
			else if(variable.equals("fichier_quitter_tooltip")) {
				return "Fermare il software ed uscire";
			}
			else if(variable.equals("constructeur_allera")) {
				return "Vai";
			}
			else if(variable.equals("constructeur_allera_tooltip")) {
				return "Navigare tra le pagine";
			}
			else if(variable.equals("allera_personnels")) {
				return "Personale";
			}
			else if(variable.equals("allera_personnels_tooltip")) {
				return "Gestire il personale.";
			}
			else if(variable.equals("allera_adherents")) {
				return "Participanti";
			}
			else if(variable.equals("allera_adherents_tooltip")) {
				return "Gestire i participanti";
			}
			else if(variable.equals("allera_squash")) {
				return "Squash";
			}
			else if(variable.equals("allera_squash_tooltip")) {
				return "Gestire le salle, prenotare corsi squash etc.";
			}
			else if(variable.equals("allera_gym")) {
				return "Ginnastica";
			}
			else if(variable.equals("allera_gym_tooltip")) {
				return "Gestire le salle, prenotare corsi ginnastica etc.";
			}
			else if(variable.equals("allera_casier")) {
				return "Armadietti";
			}
			else if(variable.equals("allera_casier_tooltip")) {
				return "Gestire i armadietti";
			}
			else if(variable.equals("allera_vente")) {
				return "Vendite";
			}
			else if(variable.equals("allera_vente_tooltip")) {
				return "Gestire lo stock, vendere dei prodotti.";
			}
			else if(variable.equals("allera_location")) {
				return "Affittare";
			}
			else if(variable.equals("allera_location_tooltip")) {
				return "Gestire lo stock, affittare dei prodotti.";
			}
			else if(variable.equals("allera_clientinternet")) {
				return "Cliente Internet";
			}
			else if(variable.equals("allera_clientinternet_tooltip")) {
				return "Visitate la vostra pagina sul vostro browser preferito";
			}
			else if(variable.equals("allera_options")) {
				return "Opzioni";
			}
			else if(variable.equals("allera_options_tooltip")) {
				return "Personalizzare il software";
			}
			else if(variable.equals("allera_deconnection")) {
				return "Disconnetti";
			}
			else if(variable.equals("allera_deconnection_tooltip")) {
				return "Disconnetti";
			}
			else if(variable.equals("constructeur_options")) {
				return "Opzioni";
			}
			else if(variable.equals("constructeur_options_tooltip")) {
				return "Personalizzare il software";
			}
			else if(variable.equals("options_skins")) {
				return "Grafica";
			}
			else if(variable.equals("options_skins_tooltip")) {
				return "Personalizzare la grafica";
			}
			else if(variable.equals("options_langues")) {
				return "Lingua";
			}
			else if(variable.equals("options_langues_tooltip")) {
				return "Personalizzare la lingua";
			}
			else if(variable.equals("options_options")) {
				return "Altri opzioni";
			}
			else if(variable.equals("options_options_tooltip")) {
				return "Accedi a tutti gli opzioni";
			}
			else if(variable.equals("constructeur_aide")) {
				return "?";
			}
			else if(variable.equals("constructeur_aide_tooltip")) {
				return "Aiuto";
			}
			else if(variable.equals("aide_doc")) {
				return "Documentazione";
			}
			else if(variable.equals("aide_doc_tooltip")) {
				return "Accedi a la documentazione";
			}
			else if(variable.equals("aide_aproposde")) {
				return "Circa";
			}
			else if(variable.equals("aide_aproposde_tooltip")) {
				return "Circa il software";
			}
			else if(variable.equals("aproposde_titre")) {
				return "Circa Musquash";
			}
			else if(variable.equals("aproposde_1")) {
				return "Software...";
			}
			else if(variable.equals("aproposde_2")) {
				return "...";
			}
			else if(variable.equals("aproposde_3")) {
				return "...";
			}
			else if(variable.equals("aproposde_boutton")) {
				return "Continua";
			}
			else if(variable.equals("cmd_racine")) {
				return "Pagina iniziale";
			}
			else if(variable.equals("cmd_personnels")) {
				return "Personale";
			}
			else if(variable.equals("cmd_adherents")) {
				return "Participanti";
			}
			else if(variable.equals("cmd_squash")) {
				return "Squash";
			}
			else if(variable.equals("cmd_gym")) {
				return "Ginnastica";
			}
			else if(variable.equals("cmd_casiers")) {
				return "Armadietti";
			}
			else if(variable.equals("cmd_vente")) {
				return "Vendite";
			}
			else if(variable.equals("cmd_location")) {
				return "Affitti";
			}
			else if(variable.equals("cmd_options")) {
				return "Opzioni";
			}
			else if(variable.equals("cmd_connexionbd")) {
				return "Connessione Banco dati";
			}
			
			else {
				return it_defaut;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("constructeur_fichier")) {
				return "Dosje";
			}
			else if(variable.equals("constructeur_fichier_tootltip")) {
				return "Shiko te dhenat";
			}
			else if(variable.equals("constructeur_nouvelletab")) {
				return "Etikete e re";
			}
			else if(variable.equals("constructeur_nouvelletab_tooltip")) {
				return "Etikete e re";
			}
			else if(variable.equals("fichier_deconnexion")) {
				return "Shkeput";
			}
			else if(variable.equals("fichier_deconnexion_tooltip")) {
				return "Shkeput lidhjen";
			}
			else if(variable.equals("fichier_quitter")) {
				return "Mbyll";
			}
			else if(variable.equals("fichier_quitter_tooltip")) {
				return "Mbyll programin";
			}
			else if(variable.equals("constructeur_allera")) {
				return "Shko...";
			}
			else if(variable.equals("constructeur_allera_tooltip")) {
				return "Shkoni tek pjesa e programit qe ju intereson";
			}
			else if(variable.equals("allera_personnels")) {
				return "Personeli";
			}
			else if(variable.equals("allera_personnels_tooltip")) {
				return "Modifiko personelin.";
			}
			else if(variable.equals("allera_adherents")) {
				return "Pjesemarres";
			}
			else if(variable.equals("allera_adherents_tooltip")) {
				return "Modifiko pjesemarresit";
			}
			else if(variable.equals("allera_squash")) {
				return "Skuosh";
			}
			else if(variable.equals("allera_squash_tooltip")) {
				return "Modifikoni sallat, reservoni kurset etj.";
			}
			else if(variable.equals("allera_gym")) {
				return "Gjimnastike";
			}
			else if(variable.equals("allera_gym_tooltip")) {
				return "Modifikoni sallat, reservoni kurset etj.";
			}
			else if(variable.equals("allera_casier")) {
				return "Dollape";
			}
			else if(variable.equals("allera_casier_tooltip")) {
				return "Reservo, modifiko dollapet";
			}
			else if(variable.equals("allera_vente")) {
				return "Shitje";
			}
			else if(variable.equals("allera_vente_tooltip")) {
				return "Kontrolloni produktet ne stok, shisni produktet tuaja.";
			}
			else if(variable.equals("allera_location")) {
				return "Marrje me qera";
			}
			else if(variable.equals("allera_location_tooltip")) {
				return "Kontrolloni produktet ne stok, merrni me qera produkte te tjera.";
			}
			else if(variable.equals("allera_clientinternet")) {
				return "Klient Interneti";
			}
			else if(variable.equals("allera_clientinternet_tooltip")) {
				return "Hapni faqen tuaj ne browser-in tuaj te prefuar.";
			}
			else if(variable.equals("allera_options")) {
				return "Opsione";
			}
			else if(variable.equals("allera_options_tooltip")) {
				return "Personalizoni programin.";
			}
			else if(variable.equals("allera_deconnection")) {
				return "Shkeput";
			}
			else if(variable.equals("allera_deconnection_tooltip")) {
				return "Shkeputni lidhjen.";
			}
			else if(variable.equals("constructeur_options")) {
				return "Opsione";
			}
			else if(variable.equals("constructeur_options_tooltip")) {
				return "Personalizoni programin";
			}
			else if(variable.equals("options_skins")) {
				return "Grafika";
			}
			else if(variable.equals("options_skins_tooltip")) {
				return "Personalizoni grafiken";
			}
			else if(variable.equals("options_langues")) {
				return "Gjuha";
			}
			else if(variable.equals("options_langues_tooltip")) {
				return "Zgjidhni gjuhen";
			}
			else if(variable.equals("options_options")) {
				return "Me shume opsione";
			}
			else if(variable.equals("options_options_tooltip")) {
				return "Konsultoni listen e opsioneve";
			}
			else if(variable.equals("constructeur_aide")) {
				return "?";
			}
			else if(variable.equals("constructeur_aide_tooltip")) {
				return "Ndihme";
			}
			else if(variable.equals("aide_doc")) {
				return "Dokumente";
			}
			else if(variable.equals("aide_doc_tooltip")) {
				return "Shihni dokumentet";
			}
			else if(variable.equals("aide_aproposde")) {
				return "Mbi...";
			}
			else if(variable.equals("aide_aproposde_tooltip")) {
				return "Informacion mbi programin";
			}
			else if(variable.equals("aproposde_titre")) {
				return "Informacion mbi Musquash";
			}
			else if(variable.equals("aproposde_1")) {
				return "Programi...";
			}
			else if(variable.equals("aproposde_2")) {
				return "...";
			}
			else if(variable.equals("aproposde_3")) {
				return "...";
			}
			else if(variable.equals("aproposde_boutton")) {
				return "Vazhdo";
			}
			else if(variable.equals("cmd_racine")) {
				return "Faqja kryesore";
			}
			else if(variable.equals("cmd_personnels")) {
				return "Personeli";
			}
			else if(variable.equals("cmd_adherents")) {
				return "Pjesemarres";
			}
			else if(variable.equals("cmd_squash")) {
				return "Skuosh";
			}
			else if(variable.equals("cmd_gym")) {
				return "Gjimnastike";
			}
			else if(variable.equals("cmd_casiers")) {
				return "Dollape";
			}
			else if(variable.equals("cmd_vente")) {
				return "Shitje";
			}
			else if(variable.equals("cmd_location")) {
				return "Marrje me qera";
			}
			else if(variable.equals("cmd_options")) {
				return "Opsione";
			}
			else if(variable.equals("cmd_connexionbd")) {
				return "Lidhu me bazen et dhenave";
			}
			else {
				return al_defaut;
			}
		}
		
		else {
			return error;
		}
	}
	
	static public String premiereVue(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("actionperformed_personnels")) {
				return "Personnels";
			}
			else if(variable.equals("actionperformed_utilisateurs")) {
				return "Utilisateurs";
			}
			else if(variable.equals("actionperformed_squash")) {
				return "Squash";
			}
			else if(variable.equals("actionperformed_cours")) {
				return "Cours";
			}
			else if(variable.equals("actionperformed_casiers")) {
				return "Casiers";
			}
			else if(variable.equals("actionperformed_ventes")) {
				return "Ventes";
			}
			else if(variable.equals("actionperformed_location")) {
				return "Locations";
			}
			else if(variable.equals("actionperformed_skins")) {
				return "Skins";
			}
			else if(variable.equals("actionperformed_deconnection")) {
				return "Connection BD";
			}
			else {
				return fr_defaut;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("actionperformed_personnels")) {
				return "Persons";
			}
			else if(variable.equals("actionperformed_utilisateurs")) {
				return "Users";
			}
			else if(variable.equals("actionperformed_squash")) {
				return "Squash";
			}
			else if(variable.equals("actionperformed_cours")) {
				return "Lessons";
			}
			else if(variable.equals("actionperformed_casiers")) {
				return "Rack";
			}
			else if(variable.equals("actionperformed_ventes")) {
				return "Sales";
			}
			// TODO Auto-generated method stub
			else if(variable.equals("actionperformed_location")) {
				return "Rentals";
			}
			else if(variable.equals("actionperformed_skins")) {
				return "Skins";
			}
			else if(variable.equals("actionperformed_deconnection")) {
				return "Database connection";
			}
			else {
				return en_defaut;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("actionperformed_personnels")) {
				return "Persone";
			}
			else if(variable.equals("actionperformed_utilisateurs")) {
				return "Personale";
			}
			else if(variable.equals("actionperformed_squash")) {
				return "Squash";
			}
			else if(variable.equals("actionperformed_cours")) {
				return "Lezioni";
			}
			else if(variable.equals("actionperformed_casiers")) {
				return "Armadietti";
			}
			else if(variable.equals("actionperformed_ventes")) {
				return "Vendite";
			}
			// TODO Auto-generated method stub
			else if(variable.equals("actionperformed_location")) {
				return "Affitare";
			}
			else if(variable.equals("actionperformed_skins")) {
				return "Grafica";
			}
			else if(variable.equals("actionperformed_deconnection")) {
				return "Connessione a la Banca dati";
			}
			else {
				return it_defaut;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("actionperformed_personnels")) {
				return "Persona";
			}
			else if(variable.equals("actionperformed_utilisateurs")) {
				return "Personeli";
			}
			else if(variable.equals("actionperformed_squash")) {
				return "Skuosh";
			}
			else if(variable.equals("actionperformed_cours")) {
				return "Mesime";
			}
			else if(variable.equals("actionperformed_casiers")) {
				return "Dollape";
			}
			else if(variable.equals("actionperformed_ventes")) {
				return "Shitje";
			}
			// TODO Auto-generated method stub
			else if(variable.equals("actionperformed_location")) {
				return "Marrje me qera";
			}
			else if(variable.equals("actionperformed_skins")) {
				return "Grafika";
			}
			else if(variable.equals("actionperformed_deconnection")) {
				return "Lidhu me bazen e te dhenave";
			}
			else {
				return al_defaut;
			}
		}
		
		else {
			return error;
		}
	}
	
	static public String utiles(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("image_erreur")) {
				return "Image non trouv�e";
			}
			else if(variable.equals("dialog_erreur_bd")) {
				return "Erreur - Base de donn�e";
			}
			else if(variable.equals("dialog_information_bd")) {
				return "Information - Base de donn�e";
			}
			else {
				return fr_defaut;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("image_erreur")) {
				return "Picture not found";
			}
			else if(variable.equals("dialog_erreur_bd")) {
				return "Error - Database";
			}
			else if(variable.equals("dialog_information_bd")) {
				return "Information - Database";
			}
			else {
				return en_defaut;
			}
		}
		
		
		else if(langue.equals("it")) {
			if(variable.equals("image_erreur")) {
				return "Immagine introvabile";
			}
			else if(variable.equals("dialog_erreur_bd")) {
				return "Errore - Banca dati";
			}
			else if(variable.equals("dialog_information_bd")) {
				return "Informazione - Banca dati";
			}
			else {
				return it_defaut;
			}
		}
		
		
		else if(langue.equals("al")) {
			if(variable.equals("image_erreur")) {
				return "Figura nuk gjendet";
			}
			else if(variable.equals("dialog_erreur_bd")) {
				return "Gabim - Baza e te dhenave";
			}
			else if(variable.equals("dialog_information_bd")) {
				return "Informacion - Baza e te dhenave";
			}
			else {
				return al_defaut;
			}
		}
		
		else {
			return error;
		}
	}
	
	static public String [] utilesTab(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("mois")) {
				String [] s = {"Janvier", "F�vrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"}; 
				return s;
			}
			else {
				String [] s = {fr_defaut};
				return s;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("mois")) {
				String [] s = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
				return s;
			}
			else {
				String [] s = {en_defaut};
				return s;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("mois")) {
				String [] s = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"}; 
				return s;
			}
			else {
				String [] s = {it_defaut};
				return s;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("mois")) {
				String [] s = {"Janar", "Shkurt", "Mars", "Prill", "Maj", "Qershor", "Korrik", "Gusht", "Shtator", "Tetor", "Nentor", "Dhjetor"}; 
				return s;
			}
			else {
				String [] s = {al_defaut};
				return s;
			}
		}
		
		else {
			String [] s = {error};
			return s;
		}
	}
	
	static public String vueAdherents(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("constructeur_bouttonhome")) {
				return "Racine";
			}
			else if(variable.equals("constructeur_bouttonajouter")) {
				return "Ajouter";
			}
			else if(variable.equals("constructeur_bouttonmodifier")) {
				return "Modifier";
			}
			else if(variable.equals("constructeur_bouttonsupprimer")) {
				return "Supprimer";
			}
			else if(variable.equals("constructeur_bouttonforfaits")) {
				return "Forfaits";
			}
			else if(variable.equals("ajoutermodifier_ajouter")) {
				return "Ajouter";
			}
			else if(variable.equals("ajoutermodifier_modifier")) {
				return "Modifier";
			}
			else if(variable.equals("ajoutermodifier_nom")) {
				return "Nom";
			}
			else if(variable.equals("ajoutermodifier_prenom")) {
				return "Prenom";
			}
			else if(variable.equals("ajoutermodifier_adresse")) {
				return "Adresse";
			}
			else if(variable.equals("ajoutermodifier_datedenaissance")) {
				return "Date de naissance";
			}
			else if(variable.equals("ajoutermodifier_valider")) {
				return "Valider";
			}
			else if(variable.equals("ajoutermodifier_annuler")) {
				return "Annuler";
			}
			else if(variable.equals("buildpanelforfait_ajouter")) {
				return "Ajouter";
			}
			else if(variable.equals("buildpanelforfait_valider")) {
				return "Valider";
			}
			else if(variable.equals("buildpanelforfait_toutvider")) {
				return "Remise � zero";
			}
			else if(variable.equals("buildpanelforfait_modifiersupprimer")) {
				return "Modifier/Supprimer";
			}
			else if(variable.equals("buildpanelforfait_modifier")) {
				return "Modifier";
			}
			else if(variable.equals("buildpanelforfait_supprimer")) {
				return "Supprimer";
			}
			else if(variable.equals("buildpanelforfait_cacher")) {
				return "Cacher";
			}
			else {
				return fr_defaut;
			}
		}
		
		
		else if(langue.equals("en")) {
			if(variable.equals("constructeur_bouttonhome")) {
				return "Home";
			}
			else if(variable.equals("constructeur_bouttonajouter")) {
				return "Add";
			}
			else if(variable.equals("constructeur_bouttonmodifier")) {
				return "Edit";
			}
			else if(variable.equals("constructeur_bouttonsupprimer")) {
				return "Delete";
			}
			else if(variable.equals("constructeur_bouttonforfaits")) {
				return "Fixed price";
			}
			else if(variable.equals("ajoutermodifier_ajouter")) {
				return "Add";
			}
			else if(variable.equals("ajoutermodifier_modifier")) {
				return "Edit";
			}
			else if(variable.equals("ajoutermodifier_nom")) {
				return "Last name";
			}
			else if(variable.equals("ajoutermodifier_prenom")) {
				return "First name";
			}
			else if(variable.equals("ajoutermodifier_adresse")) {
				return "Adress";
			}
			else if(variable.equals("ajoutermodifier_datedenaissance")) {
				return "Date of birth";
			}
			else if(variable.equals("ajoutermodifier_valider")) {
				return "Ok";
			}
			else if(variable.equals("ajoutermodifier_annuler")) {
				return "Cancel";
			}
			else if(variable.equals("buildpanelforfait_ajouter")) {
				return "Add";
			}
			else if(variable.equals("buildpanelforfait_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelforfait_toutvider")) {
				return "Reset";
			}
			else if(variable.equals("buildpanelforfait_modifiersupprimer")) {
				return "Edit/Delete";
			}
			else if(variable.equals("buildpanelforfait_modifier")) {
				return "Edit";
			}
			else if(variable.equals("buildpanelforfait_supprimer")) {
				return "Delete";
			}
			else if(variable.equals("buildpanelforfait_cacher")) {
				return "Hide";
			}
			else {
				return en_defaut;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("constructeur_bouttonhome")) {
				return "Prima pagina";
			}
			else if(variable.equals("constructeur_bouttonajouter")) {
				return "Aggiungi";
			}
			else if(variable.equals("constructeur_bouttonmodifier")) {
				return "Modifica";
			}
			else if(variable.equals("constructeur_bouttonsupprimer")) {
				return "Cancella";
			}
			else if(variable.equals("constructeur_bouttonforfaits")) {
				return "Pachetto";
			}
			else if(variable.equals("ajoutermodifier_ajouter")) {
				return "Aggiungi";
			}
			else if(variable.equals("ajoutermodifier_modifier")) {
				return "Modifica";
			}
			else if(variable.equals("ajoutermodifier_nom")) {
				return "Cognome";
			}
			else if(variable.equals("ajoutermodifier_prenom")) {
				return "Nome";
			}
			else if(variable.equals("ajoutermodifier_adresse")) {
				return "Indirizo";
			}
			else if(variable.equals("ajoutermodifier_datedenaissance")) {
				return "Data di nascita";
			}
			else if(variable.equals("ajoutermodifier_valider")) {
				return "Ok";
			}
			else if(variable.equals("ajoutermodifier_annuler")) {
				return "Annulla";
			}
			else if(variable.equals("buildpanelforfait_ajouter")) {
				return "Aggiungi";
			}
			else if(variable.equals("buildpanelforfait_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelforfait_toutvider")) {
				return "Ricommincia";
			}
			else if(variable.equals("buildpanelforfait_modifiersupprimer")) {
				return "Modifica/Cancella";
			}
			else if(variable.equals("buildpanelforfait_modifier")) {
				return "Modifica";
			}
			else if(variable.equals("buildpanelforfait_supprimer")) {
				return "Cancella";
			}
			else if(variable.equals("buildpanelforfait_cacher")) {
				return "Annulla";
			}
			else {
				return it_defaut;
			}
		}
		
		
		else if(langue.equals("al")) {
			if(variable.equals("constructeur_bouttonhome")) {
				return "Faqja kryesore";
			}
			else if(variable.equals("constructeur_bouttonajouter")) {
				return "Shto";
			}
			else if(variable.equals("constructeur_bouttonmodifier")) {
				return "Ndrysho";
			}
			else if(variable.equals("constructeur_bouttonsupprimer")) {
				return "Fshi";
			}
			else if(variable.equals("constructeur_bouttonforfaits")) {
				return "Pakete e parapaguar";
			}
			else if(variable.equals("ajoutermodifier_ajouter")) {
				return "Shto";
			}
			else if(variable.equals("ajoutermodifier_modifier")) {
				return "Ndrysho";
			}
			else if(variable.equals("ajoutermodifier_nom")) {
				return "Mbiemer";
			}
			else if(variable.equals("ajoutermodifier_prenom")) {
				return "Emer";
			}
			else if(variable.equals("ajoutermodifier_adresse")) {
				return "Adresse";
			}
			else if(variable.equals("ajoutermodifier_datedenaissance")) {
				return "Datelindja";
			}
			else if(variable.equals("ajoutermodifier_valider")) {
				return "Ok";
			}
			else if(variable.equals("ajoutermodifier_annuler")) {
				return "Anullo";
			}
			else if(variable.equals("buildpanelforfait_ajouter")) {
				return "Shto";
			}
			else if(variable.equals("buildpanelforfait_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelforfait_toutvider")) {
				return "Rifillo nga e para";
			}
			else if(variable.equals("buildpanelforfait_modifiersupprimer")) {
				return "Ndrysho/Fshi";
			}
			else if(variable.equals("buildpanelforfait_modifier")) {
				return "Modifiko";
			}
			else if(variable.equals("buildpanelforfait_supprimer")) {
				return "Fshi";
			}
			else if(variable.equals("buildpanelforfait_cacher")) {
				return "Anullo";
			}
			else {
				return al_defaut;
			}
		}
		
		
		else {
			return error;
		}
	}
	
	static public String [] vueAdherentsTab(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("columns")) {
				String [] s = {"Id", "Num Adh�rent", "Nom", "Pr�nom", "Date de Naissance", "Adresse"}; 
				return s;
			}
			else if(variable.equals("mois")) {
				String [] s = {"Janvier", "F�vrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "Aout", "Septembre", "Octobre", "Novembre", "Decembre"};
				return s;
			}
			else if(variable.equals("forfaits")) {
				String [] s = {"Mois", "Trimestre", "Semestre", "Ann�e"};
				return s;
			}
			else {
				String [] s = {fr_defaut};
				return s;
			}
		}
		
		
		else if(langue.equals("en")) {
			if(variable.equals("columns")) {
				String [] s = {"Id", "Member number", "Last name", "First name", "Date of birth", "Adress"}; 
				return s;
			}
			else if(variable.equals("mois")) {
				String [] s = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
				return s;
			}
			else if(variable.equals("forfaits")) {
				String [] s = {"Month", "Trimester", "Semester", "Year"};
				return s;
			}
			else {
				String [] s = {en_defaut};
				return s;
			}
		}
		
		
		else if(langue.equals("it")) {
			if(variable.equals("columns")) {
				String [] s = {"Id", "Numero Aderente", "Cognome", "Nome", "Data di nascita", "Indirizzo"}; 
				return s;
			}
			else if(variable.equals("mois")) {
				String [] s = {"Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"}; 
				return s;
			}
			else if(variable.equals("forfaits")) {
				String [] s = {"Mese", "Trimestre", "Simestre", "Anno"};
				return s;
			}
			else {
				String [] s = {it_defaut};
				return s;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("columns")) {
				String [] s = {"Id", "Numri i pjesemarresit", "Mbiemri", "Emri", "Datelindja", "Adresa"}; 
				return s;
			}
			else if(variable.equals("mois")) {
				String [] s = {"Janar", "Shkurt", "Mars", "Prill", "Maj", "Qershor", "Korrik", "Gusht", "Shtator", "Tetor", "Nentor", "Dhjetor"}; 
				return s;
			}
			else if(variable.equals("forfaits")) {
				String [] s = {"Muaj", "Trimester", "Simester", "Vit"};
				return s;
			}
			else {
				String [] s = {al_defaut};
				return s;
			}
		}
		
		
		else {
			String [] s = {error};
			return s;
		}
	}
	
	static public String vueCasier(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("boutton_home")) {
				return "Racine";
			}
			else if(variable.equals("boutton_vue_annee")) {
				return "Vue Ann�e";
			}
			else if(variable.equals("boutton_vue_seance")) {
				return "Vue S�ance";
			}
			else if(variable.equals("boutton_recharger")) {
				return "Recharger";
			}
			else if(variable.equals("boutton_reserver")) {
				return "R�server";
			}
			else if(variable.equals("boutton_annuler")) {
				return "Annuler";
			}
			else if(variable.equals("buildpaneldroitecasierannee_border")) {
				return "Casier n�";
			}
			else if(variable.equals("buildpaneldroitecasieranne_vide")) {
				return "Vide";
			}
			else if(variable.equals("buildpaneldroitecasierseance_border")) {
				return "Casier n�";
			}
			else if(variable.equals("creerpanelannee_border")) {
				return "R�servation pour une Ann�e";
			}
			else if(variable.equals("creerpanelannee_nom_adh")) {
				return "Nom adh�rent";
			}
			else if(variable.equals("creerpanelannee_annee")) {
				return "Ann�e";
			}
			else if(variable.equals("creerpanelseance_border")) {
				return "R�servation pour une S�ance";
			}
			else if(variable.equals("creerpanelseance_nom")) {
				return "Nom";
			}
			else if(variable.equals("creerpanelseance_annee")) {
				return "Ann�e";
			}
			else if(variable.equals("creerpanelseance_horaire")) {
				return "Horaire";
			}
			else if(variable.equals("reservercaiserdialog_titre")) {
				return "R�servation";
			}
			else if(variable.equals("reservercaiserdialog_border")) {
				return "Casier n�";
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				return "Type";
			}
			else if(variable.equals("reservercaiserdialog_boutton_valider")) {
				return "Valider";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Annuler";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Annuler";
			}
			else {
				return fr_defaut;
			}
		}
		
		
		else if(langue.equals("en")) {
			if(variable.equals("boutton_home")) {
				return "Home";
			}
			else if(variable.equals("boutton_vue_annee")) {
				return "Year View";
			}
			else if(variable.equals("boutton_vue_seance")) {
				return "Session View";
			}
			else if(variable.equals("boutton_recharger")) {
				return "Refresh";
			}
			else if(variable.equals("boutton_reserver")) {
				return "Reservation";
			}
			else if(variable.equals("boutton_annuler")) {
				return "Cancel";
			}
			else if(variable.equals("buildpaneldroitecasierannee_border")) {
				return "Rack number";
			}
			else if(variable.equals("buildpaneldroitecasieranne_vide")) {
				return "Empty";
			}
			else if(variable.equals("buildpaneldroitecasierseance_border")) {
				return "Rack number";
			}
			else if(variable.equals("creerpanelannee_border")) {
				return "Year reservation";
			}
			else if(variable.equals("creerpanelannee_nom_adh")) {
				return "Member Last name";
			}
			else if(variable.equals("creerpanelannee_annee")) {
				return "Year";
			}
			else if(variable.equals("creerpanelseance_border")) {
				return "Session reservation";
			}
			else if(variable.equals("creerpanelseance_nom")) {
				return "Name";
			}
			else if(variable.equals("creerpanelseance_annee")) {
				return "Year";
			}
			else if(variable.equals("creerpanelseance_horaire")) {
				return "Schedule";
			}
			else if(variable.equals("reservercaiserdialog_titre")) {
				return "Reservation";
			}
			else if(variable.equals("reservercaiserdialog_border")) {
				return "Rack number";
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				return "Type";
			}
			else if(variable.equals("reservercaiserdialog_boutton_valider")) {
				return "Ok";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Cancel";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Cancel";
			}
			else {
				return en_defaut;
			}
		}
		
		
		else if(langue.equals("it")) {
			if(variable.equals("boutton_home")) {
				return "Prima pagina";
			}
			else if(variable.equals("boutton_vue_annee")) {
				return "Visualizza anno";
			}
			else if(variable.equals("boutton_vue_seance")) {
				return "Visualizza seduta";
			}
			else if(variable.equals("boutton_recharger")) {
				return "Ricarica";
			}
			else if(variable.equals("boutton_reserver")) {
				return "Prenota";
			}
			else if(variable.equals("boutton_annuler")) {
				return "Annulla";
			}
			else if(variable.equals("buildpaneldroitecasierannee_border")) {
				return "Numero armadietto";
			}
			else if(variable.equals("buildpaneldroitecasieranne_vide")) {
				return "Vuoto";
			}
			else if(variable.equals("buildpaneldroitecasierseance_border")) {
				return "Numero armadietto";
			}
			else if(variable.equals("creerpanelannee_border")) {
				return "Prenota per un anno";
			}
			else if(variable.equals("creerpanelannee_nom_adh")) {
				return "Nome adherente";
			}
			else if(variable.equals("creerpanelannee_annee")) {
				return "Anno";
			}
			else if(variable.equals("creerpanelseance_border")) {
				return "Prenota per una seduta";
			}
			else if(variable.equals("creerpanelseance_nom")) {
				return "Nome";
			}
			else if(variable.equals("creerpanelseance_annee")) {
				return "Anno";
			}
			else if(variable.equals("creerpanelseance_horaire")) {
				return "Orario";
			}
			else if(variable.equals("reservercaiserdialog_titre")) {
				return "Prenota";
			}
			else if(variable.equals("reservercaiserdialog_border")) {
				return "Numero armadietto";
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				return "Tipo";
			}
			else if(variable.equals("reservercaiserdialog_boutton_valider")) {
				return "OK";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Annulla";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Annulla";
			}
			else {
				return it_defaut;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("boutton_home")) {
				return "Faqja e pare";
			}
			else if(variable.equals("boutton_vue_annee")) {
				return "Shiko gjithe vitin";
			}
			else if(variable.equals("boutton_vue_seance")) {
				return "Shiko seancen";
			}
			else if(variable.equals("boutton_recharger")) {
				return "Rikariko";
			}
			else if(variable.equals("boutton_reserver")) {
				return "Rezervo";
			}
			else if(variable.equals("boutton_annuler")) {
				return "Anullo";
			}
			else if(variable.equals("buildpaneldroitecasierannee_border")) {
				return "Nr. i dollapit";
			}
			else if(variable.equals("buildpaneldroitecasieranne_vide")) {
				return "Bosh";
			}
			else if(variable.equals("buildpaneldroitecasierseance_border")) {
				return "Nr. i dollapit";
			}
			else if(variable.equals("creerpanelannee_border")) {
				return "Rezervim vjetor";
			}
			else if(variable.equals("creerpanelannee_nom_adh")) {
				return "Emri i pjesemarresit";
			}
			else if(variable.equals("creerpanelannee_annee")) {
				return "Viti";
			}
			else if(variable.equals("creerpanelseance_border")) {
				return "Rezervo per nje seance";
			}
			else if(variable.equals("creerpanelseance_nom")) {
				return "Emri";
			}
			else if(variable.equals("creerpanelseance_annee")) {
				return "Viti";
			}
			else if(variable.equals("creerpanelseance_horaire")) {
				return "Orari";
			}
			else if(variable.equals("reservercaiserdialog_titre")) {
				return "Rezervim";
			}
			else if(variable.equals("reservercaiserdialog_border")) {
				return "Nr. i dollapit";
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				return "Tipi";
			}
			else if(variable.equals("reservercaiserdialog_boutton_valider")) {
				return "OK";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Anullo";
			}
			else if(variable.equals("reservercaiserdialog_boutton_annuler")) {
				return "Anullo";
			}
			else {
				return al_defaut;
			}
		}
		
		
		else {
			return error;
		}
	}
	
	static public String [] vueCasierTab(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("buildpaneldroitecasierseance_columns")) {
				String [] s = {"Horaire", "N� Adh�rent", "Nom", "Pr�nom"}; 
				return s;
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				String [] s = {"Pour une ann�e", "Pour une s�ance"}; 
				return s;
			}
			else {
				String [] s = {fr_defaut};
				return s;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("buildpaneldroitecasierseance_columns")) {
				String [] s = {"Schedule", "Member number", "Last name", "First name"}; 
				return s;
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				String [] s = {"For a year", "For a session"}; 
				return s;
			}
			else {
				String [] s = {en_defaut};
				return s;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("buildpaneldroitecasierseance_columns")) {
				String [] s = {"Orario", "Numero aderente", "Cognome", "Nome"}; 
				return s;
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				String [] s = {"Annuale", "Per una seduta"}; 
				return s;
			}
			else {
				String [] s = {it_defaut};
				return s;
			}
		}
		
		
		else if(langue.equals("al")) {
			if(variable.equals("buildpaneldroitecasierseance_columns")) {
				String [] s = {"Orari", "Numri i pjesemarresit", "Mbiemri", "Emri"}; 
				return s;
			}
			else if(variable.equals("reservercaiserdialog_type")) {
				String [] s = {"Vjetore", "Per nje seance"}; 
				return s;
			}
			else {
				String [] s = {al_defaut};
				return s;
			}
		}
		
		else {
			String [] s = {error};
			return s;
		}
	}
	
	static public String vueConnexionBd(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("border_connexion")) {
				return "Connection Base de Donn�e";
			}
			else if(variable.equals("pseudo")) {
				return "Pseudo";
			}
			else if(variable.equals("passe")) {
				return "Mot de Passe";
			}
			else if(variable.equals("adresse")) {
				return "Adresse (ip:port:version)";
			}
			else if(variable.equals("enregistrer")) {
				return "Enregistrer les informations sur ce pc";
			}
			else if(variable.equals("valider")) {
				return "Valider";
			}
			else if(variable.equals("erreur")) {
				return "Erreur de passe ou de pseudo !!";
			}
			else if(variable.equals("racine")) {
				return "Racine";
			}
			// TODO Auto-generated method stub
			else if(variable.equals("dialogproprietaire_titre")) {
				return "Proprietaire";
			}
			else if(variable.equals("dialogproprietaire_label1")) {
				return "Vous venez de lancer ce logiciel pour la première fois.";
			}
			else if(variable.equals("dialogproprietaire_label2")) {
				return "Merci de bien vouloir renseigner le nom du propriétaire des tables d'Oracle.";
			}
			else if(variable.equals("dialogproprietaire_label3")) {
				return "Ensuite il faudra vous connecter avec ce même nom pour créer les tables dans votre base de donnée.";
			}
			else if(variable.equals("dialogproprietaire_label4")) {
				return "Enfin vous pourez vous rendre dans la partie option du logiciel pour le paramétrer à votre convenance.";
			}
			else if(variable.equals("dialogproprietaire_propretaire")) {
				return "Propriétaire";
			}
			else if(variable.equals("dialogproprietaire_valider")) {
				return "Valider";
			}
			else {
				return fr_defaut;
			}
		}
		
		
		else if(langue.equals("en")) {
			if(variable.equals("border_connexion")) {
				return "Database Connection";
			}
			else if(variable.equals("pseudo")) {
				return "Nickname";
			}
			else if(variable.equals("passe")) {
				return "Password";
			}
			else if(variable.equals("adresse")) {
				return "Server";
			}
			else if(variable.equals("enregistrer")) {
				return "Remember data";
			}
			else if(variable.equals("valider")) {
				return "Ok";
			}
			else if(variable.equals("erreur")) {
				return "Bad nickname or password !!";
			}
			else if(variable.equals("racine")) {
				return "Home";
			}
			else if(variable.equals("dialogproprietaire_titre")) {
				return "Owner";
			}
			else if(variable.equals("dialogproprietaire_label1")) {
				return "Its your first launch of the software";
			}
			else if(variable.equals("dialogproprietaire_label2")) {
				return "Get the Oracle tables admin login.";
			}
			else if(variable.equals("dialogproprietaire_label3")) {
				return "Connect with this login to create new tables.";
			}
			else if(variable.equals("dialogproprietaire_label4")) {
				return "You can customize your software through the Option menu";
			}
			else if(variable.equals("dialogproprietaire_propretaire")) {
				return "Owner";
			}
			else if(variable.equals("dialogproprietaire_valider")) {
				return "OK";
			}
			else {
				return en_defaut;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("border_connexion")) {
				return "Connessione banco dati";
			}
			else if(variable.equals("pseudo")) {
				return "Sopranome";
			}
			else if(variable.equals("passe")) {
				return "Password";
			}
			else if(variable.equals("adresse")) {
				return "Server";
			}
			else if(variable.equals("enregistrer")) {
				return "Salva i dati su questo pc";
			}
			else if(variable.equals("valider")) {
				return "Ok";
			}
			else if(variable.equals("erreur")) {
				return "Sopranome o password invalido!!";
			}
			else if(variable.equals("racine")) {
				return "Prima pagina";
			}
			else if(variable.equals("dialogproprietaire_titre")) {
				return "Proprietario";
			}
			else if(variable.equals("dialogproprietaire_label1")) {
				return "E la prima volta che lanciate questo software.";
			}
			else if(variable.equals("dialogproprietaire_label2")) {
				return "Consultare il nome utilizzato dal creatore delle tabelle Oracle.";
			}
			else if(variable.equals("dialogproprietaire_label3")) {
				return "Pottete creare nuove tabelle connettendovi con questo nome.";
			}
			else if(variable.equals("dialogproprietaire_label4")) {
				return "Per personnalizzare il vostro software rendetevi nel menu Opzioni.";
			}
			else if(variable.equals("dialogproprietaire_propretaire")) {
				return "Proprietario";
			}
			else if(variable.equals("dialogproprietaire_valider")) {
				return "OK";
			}
			else {
				return it_defaut;
			}
		}
		

		else if(langue.equals("al")) {
			if(variable.equals("border_connexion")) {
				return "Lidhu me bazen e te dhenave";
			}
			else if(variable.equals("pseudo")) {
				return "Pseudonimi";
			}
			else if(variable.equals("passe")) {
				return "Fjalekalimi";
			}
			else if(variable.equals("adresse")) {
				return "Server";
			}
			else if(variable.equals("enregistrer")) {
				return "Regjistro te dhenat ne kete kompiuter";
			}
			else if(variable.equals("valider")) {
				return "Ok";
			}
			else if(variable.equals("erreur")) {
				return "Pseudonim ose fjalekalimi i pasakte!!";
			}
			else if(variable.equals("racine")) {
				return "Faqja kryesore";
			}
			else if(variable.equals("dialogproprietaire_titre")) {
				return "Pronari";
			}
			else if(variable.equals("dialogproprietaire_label1")) {
				return "Eshte hera e pare qe ekzekutoni kete program.";
			}
			else if(variable.equals("dialogproprietaire_label2")) {
				return "Konsultoni emrin e personit qe ka krijuar tabelat Oracle.";
			}
			else if(variable.equals("dialogproprietaire_label3")) {
				return "Lidhuni me kete emer per te krijuar tabela te tjera.";
			}
			else if(variable.equals("dialogproprietaire_label4")) {
				return "Per te personalizuar programin shkoni tek menuja Opsione.";
			}
			else if(variable.equals("dialogproprietaire_propretaire")) {
				return "Pronari";
			}
			else if(variable.equals("dialogproprietaire_valider")) {
				return "Ok";
			}
			else {
				return al_defaut;
			}
		}
		
		
		else {
			return error;
		}
	}
	
	// TODO Auto-generated method stub
	static public String vueCours(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("")) {
				return "";
			}
			else {
				return fr_defaut;
			}
		}
		
		else if(langue.equals("en")) {
			if(variable.equals("")) {
				return "";
			}
			else {
				return en_defaut;
			}
		}
		
		else if(langue.equals("it")) {
			if(variable.equals("")) {
				return "";
			}
			else {
				return it_defaut;
			}
		}
		
		else if(langue.equals("al")) {
			if(variable.equals("")) {
				return "";
			}
			else {
				return al_defaut;
			}
		}
		
		else {
			return error;
		}
	}
	
	static public String vueOptions(String langue, String variable) {
		if(langue.equals("fr")) {
			if(variable.equals("racine")) {
				// TODO Auto-generated method stub
				return "Racine";
			}
			else if(variable.equals("constructor_valider")) {
				return "Valider";
			}
			else if(variable.equals("buildpanelskin_titre")) {
				return "Skin";
			}
			else if(variable.equals("buildpanellangue_titre")) {
				return "Langue";
			}
			else if(variable.equals("buildpanelclientinternet_titre")) {
				return "Client Internet";
			}
			else if(variable.equals("buildpanelclientinternet_explorateurinternet")) {
				return "Explorateur Internet";
			}
			else if(variable.equals("buildpanelclientinternet_url")) {
				return "URL";
			}
			else if(variable.equals("buildpanelbd_titre")) {
				// TODO Auto-generated method stub
				return "Sauvegarder/Restaurer les données";
			}
			else if(variable.equals("buildpanelbd_boutton_enregistrer")) {
				return "Enregistrer";
			}
			else if(variable.equals("buildpanelbd_boutton_importer")) {
				// TODO Auto-generated method stub
				return "Importer";
			}
			//panel_bd non fait !!!!
			else {
				return fr_defaut;
			}
		}
		
		
		else if(langue.equals("en")) {
			if(variable.equals("racine")) {
				return "Home";
			}
			else if(variable.equals("constructor_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelskin_titre")) {
				return "Skin";
			}
			else if(variable.equals("buildpanellangue_titre")) {
				return "Language";
			}
			else if(variable.equals("buildpanelclientinternet_titre")) {
				return "Internet User";
			}
			else if(variable.equals("buildpanelclientinternet_explorateurinternet")) {
				return "Web browser";
			}
			else if(variable.equals("buildpanelclientinternet_url")) {
				return "URL";
			}
			else if(variable.equals("buildpanelbd_titre")) {
				return "Save data / Load data";
			}
			else if(variable.equals("buildpanelbd_boutton_enregistrer")) {
				return "Save";
			}
			else {
				return en_defaut;
			}
		}
		
		
		else if(langue.equals("it")) {
			if(variable.equals("racine")) {
				// TODO Auto-generated method stub
				return "Pagina principale";
			}
			else if(variable.equals("constructor_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelskin_titre")) {
				return "Grafica";
			}
			else if(variable.equals("buildpanellangue_titre")) {
				return "Lingua";
			}
			else if(variable.equals("buildpanelclientinternet_titre")) {
				return "Cliente Internet";
			}
			else if(variable.equals("buildpanelclientinternet_explorateurinternet")) {
				return "Web browser";
			}
			else if(variable.equals("buildpanelclientinternet_url")) {
				return "URL";
			}
			else if(variable.equals("buildpanelbd_titre")) {
				return "Salva dati / Ripristino dati";
			}
			else if(variable.equals("buildpanelbd_boutton_enregistrer")) {
				return "Salva";
			}
			else {
				return it_defaut;
			}
		}
		
		
		else if(langue.equals("al")) {
			if(variable.equals("racine")) {
				// TODO Auto-generated method stub
				return "Faqja e pare";
			}
			if(variable.equals("constructor_valider")) {
				return "Ok";
			}
			else if(variable.equals("buildpanelskin_titre")) {
				return "Grafika";
			}
			else if(variable.equals("buildpanellangue_titre")) {
				return "Gjuha";
			}
			else if(variable.equals("buildpanelclientinternet_titre")) {
				return "Klient Interneti";
			}
			else if(variable.equals("buildpanelclientinternet_explorateurinternet")) {
				return "Eksplorues Internet";
			}
			else if(variable.equals("buildpanelclientinternet_url")) {
				return "URL";
			}
			else if(variable.equals("buildpanelbd_titre")) {
				return "Regjistro te dhenat / Karriko te dhenat";
			}
			else if(variable.equals("buildpanelbd_boutton_enregistrer")) {
				return "Regjistro";
			}
			else {
				return al_defaut;
			}
		}
		
		else {
			return error;
		}
	}
	
	static public String [] vueOptionsTab() {
		String [] s = {"Fran�ais", "English", "Italiano", "Shqip"}; 
		return s;
	}
	
	
//	Traductions.vueAdherents(Options.getLangue(), "buildpanelforfait_cacher")
}
