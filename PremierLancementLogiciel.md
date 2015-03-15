# Introduction #

Pour faire marcher notre logiciel suivez les 3 étapes ci-dessous.


# 1 - Eclipse #

Pour développer nous utilisons Eclipse. Il vous faut donc importer le projet, téléchargez le dans la section Download puis dézippez le. Placez le dans votre Workspace. Enfin ouvrez Eclipse, il devrait gueuler à cause des bibliothèques qui sont pas au bon endroit, ojdbc14.jar et jcalendar-1.3.3.jar. C'est normal car Eclipse utilise la localisation absolue et donc il va chercher les bibliothèques dans /home/manland... Vous devez donc cliquez droit sur le projet, puis "properties" et enfin "Java build Path", "Libraries" et "Add External JAR's". Allez chercher les biblios manquantes dans le dossier de départ du projet. Ces bibliothèques sont pour Linux et pour windows

# 2 - Oracle #

Il vous suffit de le télécharger et de l'installer, puis d'avoir un login et password pour vous y connecter. Sous Linux il suffit d'ouvrir le gestionnaire Oracle et d'ajouter un utilisateur.
Pour télécharger oracle sous windows : http://www.oracle.com/technology/products/database/xe/index.html , il suffit de remplir le petit questionnaire
Pour télécharger, installer et configurer oracle sous ubuntu : http://doc.ubuntu-fr.org/oracle

# 3 - Notre Logiciel #

Vous pouvez maintenant lancer notre logiciel, puis entrer votre login et password. Pour le serveur il est automatiquement mis sur local, vous n'avez pas à le changer normalement.

Si les tables ne sont pas présentes, le logiciel les installera.

# Utilisation #

Je vous conseille en premier lieu d'aller dans Personnes -> Personnels/Utilisateurs afin de vous créer quelques Personnes.

# Images #

Si jamais vous ne voyez aucune image, vérifiez un des chemins qui s'inscrit dans le terminal d'Eclipse. Si jamais besoins est, vous pouvez le changer en passant par le package Graphique dans la classe Utiles la fonction getTheme. Par ailleur si vous souhaitez tester un autre thème, il vous suffit de changer le "man" par le nom du dossier contenant votre skin.

De plus, entre la version alpha0.2 et alpha0.3 ce dossier a changé de place. En effet avant il se trouvait dans ~/workspace/musquash/bin (donc dans Graphique.Utiles.getTheme c'était juste "man/" pour le thème) alors que maintenant il est dans ~/workspace/musquash (d'où le "../man" dans Graphique.Utiles.getTheme).

# Ce qui fonctionne #

  * Personnes
    * Personnels
    * Utilisateurs
  * Batiments
    * Casier