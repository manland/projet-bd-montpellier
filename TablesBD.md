# Personnes #

  * personne ( <u>id_personne</u>, nom, prenom, dateDeNaissance, adresse)
  * adherent ( <u>num_adherent</u>, #id\_personne)
  * !personnelAdministratif ( <u>num_employe</u>, #id\_personne, affectation)
  * !personnelSportif ( <u>id_prof</u>, #id\_personne, type\_sport)

# Matériel #

  * !materielVente( <u>type_materiel</u>,description, quantite, prix\_base)
  * !materielLocation ( <u>num_materiel</u>, type\_materiel, date\_achat, etat, description,prix\_base)
  * location ( <u>num_location</u>,date\_location,heure\_debut,heure\_fin, #num\_materiel, #id\_personne, prix\_paye)
  * vente ( <u>num_vente</u>,#type\_materiel, date\_vente, #id\_personne,  quantite, prix\_paye)

# Casiers #

  * casier ( <u>num_casier</u> )
  * !reservationCasierAnnee ( <u>#num_casier</u>, #num\_adherent, <u>annee</u> )
  * !reservationCasierSeance ( <u>#num_casier</u>, <u>date_location</u>, <u>heure_debut</u>, heure\_fin #id\_pers, prix\_paye)

# Forfaits #

  * forfait ( <u>type_forfait</u>, prix\_mois, prix\_trimestre, prix\_semestre, prix\_an )
  * historiqueSouscription ( <u>#type_forfait</u>, <u>#num_adherent</u>, <u>date_debut</u>, <u>date_fin</u> )
  * souscription ( <u>#type_forfait</u>, <u>#num_adherent</u>, <u>date_debut</u>, <u>date_fin</u> )

# Terrains Squash #

  * !terrainSquash ( <u>num_terrain_squash</u> )
  * !reservationTerrainSquash ( <u>#num_terrain_squash</u>, <u>date_location</u>, <u>heure_debut</u>, <u>heure_fin</u>,<u>minute_debut</u>,<u>minute_fin</u>, #id\_personne, prix\_paye)

# Cours Squash #

  * !coursSquash ( <u>id_cours</u>, #id\_prof, type\_de\_cours) //type = indiv ou collectif
  * !coursSquashIndividuel ( <u>#id_cours</u>, #num\_adherent)
  * !coursSquashCollectif ( <u>#id_cours</u>, nb\_personne )
  * !inscriptionCoursSqCollectif ( <u>#id_cours</u>, <u>#num_adherent</u> )
  * creneauCoursSqIndividuel(<u>#id_cours</u>,#num\_terrain\_squash,date\_cours,heure\_debut,heure\_fin)
  * creneauCoursSqColl(<u>#id_cours</u>,#num\_terrain\_squash,jour,heure\_debut,heure\_fin,debut\_trimestre)

# Gym #

  * !salleGym ( <u>id_salle</u>, capacite)
  * !coursGym ( <u>type_cours</u>, #id\_prof )
  * !creneauCoursGym ( <u>#id_salle</u>, #type\_cours, <u>date_cours</u>, <u>heure_debut</u>, heure\_fin,debut\_trimestre)