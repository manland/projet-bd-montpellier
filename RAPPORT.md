Doc de la prof sur comment faire un rapport:
http://projet-bd-montpellier.googlecode.com/files/CommentFaire1Projet.doc


---


Et là c'est encore mieux^^

```
for(int i=0; i<taille_du_doc; i++) {
    ctrl+c();
    ctrl+v();
}
return true;
```


---


# LE RAPPORT TECHNIQUE D'UN PROJET #

## Rappel : Cycle de vie d'un logiciel ##

Le cycle de vie du logiciel comprend généralement a minima les activités suivantes :
  * **Définition des objectifs**, consistant à définir la finalité du projet et son inscription dans une stratégie globale.
  * **Analyse des besoins et faisabilité**, c'est-à-dire l'expression, le recueil et la formalisation des besoins du demandeur (le client) et de l'ensemble des contraintes. Il faut ici décrire le but du système et non pas la façon dont il sera réalisé. Il ne faut prendre aucune décision d'implantation
  * **Conception générale**. Il s'agit de l'élaboration des spécifications de l'architecture générale du logiciel.
  * **Conception détaillée**, consistant à définir précisément chaque sous-ensemble du logiciel.
  * **Codage** (Implémentation ou programmation), soit la traduction dans un langage de programmation des fonctionnalités définies lors de phases de conception.
  * **Tests unitaires**, permettant de vérifier individuellement que chaque sous-ensemble du logiciel est implémentée conformément aux spécifications.
  * **Intégration**, dont l'objectif est de s'assurer de l'interfaçage des différents éléments (modules) du logiciel. Elle fait l'objet de tests d'intégration consignés dans un document.
  * **Qualification** (ou recette), c'est-à-dire la vérification de la conformité du logiciel aux spécifications initiales.
  * **Documentation**, visant à produire les informations nécessaires pour l'utilisation du logiciel et pour des développements ultérieurs.
  * **Mise en production**,
  * **Maintenance**, comprenant toutes les actions correctives (maintenance corrective) et évolutives (maintenance évolutive) sur le logiciel.

## Cycle de vie d'un projet universitaire (cycle de vie simplifié d'un logiciel) : ##

  * Analyse des besoins (analyse du sujet) i.e. cahier des charges, exigences fonctionnelles, etc…
  * Analyse fonctionnelle du projet i.e vue globale du système
  * Conception détaillée des différents modules du projet i.e. vue détaillées des différentes briques constituant le système (zoom sur chaque partie importante du projet)
  * Réalisation i.e. programmation proprement dite (toujours en rapport avec le langage de programmation).
  * Vérification du fonctionnement de chaque module (tests unitaires) i.e. tests locaux permettant de valider le fonctionnement de chaque module séparément (quand il y a un intérêt évidemment).
  * Vérification du fonctionnement de l'ensemble des modules (tests d'intégrations) i.e. tests sur l'ensemble du programme permettant de le valider dans son intégralité
  * Révision i.e. correction des défauts détectés
  * Validation
  * Documentation i.e. documentation associée au code et Rapport technique.

## Rapport technique associé au projet : ##

Le rapport technique est aussi important que le programme lui-même et que la documentation associée au programme.

Le rapport technique a plusieurs objectifs :
  * Il permet de présenter clairement le projet
  * Il permet de vérifier la bonne compréhension du projet
  * Il permet de distinguer et de décrire les différentes parties du projet
  * Il permet la réutilisation de code
  * Il permet la maintenance du projet

Le rapport technique ne sert jamais :
  * A décrire les états d'âmes de l'étudiant.
  * A dire ce qu'on a aimé ou pas dans le projet.
  * A réécrire le code que l'on a déjà écrit dans le programme.

Un rapport technique est structuré comme suit :
  * Une page de garde contenant le titre du projet, l'auteur, l'école (avec le logo si vous en avez un), la version du projet si celui-ci contient plusieurs versions et généralement une image représentative de ce que l'on veut faire.
  * Une table des matières
  * Les différentes parties du rapport
  * Une bibliographie si le projet a nécessité la lecture d'articles scientifiques ou la consultation de sites Internet.

Les différentes parties du rapport se décomposent comme suit :
  * Une Introduction dans laquelle on explique ce qu'on doit faire sans rentrer dans les détails
  * Une partie Analyse qui va être découpée en deux parties :
    1. Analyse des besoins i.e. réflexions qui conduit au cahier des charges.
    1. Analyse fonctionnelle i.e. vue globale du système avec justification des choix effectués.
  * Une partie Conception dans laquelle on va détailler les parties les plus importantes du projet  (et uniquement celles la). C'est ici qu'on détaille l'algorithme des méthodes de classes les plus significatives
  * Une partie Réalisation dans laquelle on explique les choix d'implémentation et dans laquelle on détaille les mécanismes d'exceptions mis en place. Cette partie sert à décrire les parties de code qui ont un intérêt particulier. Cela va être utile en particulier lorsque le code doit être repris ultérieurement  par un autre programmeur. Elle doit indiquer les subtilités de votre code et expliquer pourquoi vous avez choisi cette technique plutôt qu'une autre mais vous ne devez jamais faire un copier/coller de tout votre code (surtout s'il n'y a pas d'explications associées) !
Si vous créez vos propres exceptions vous devez dire ce qu'elles doivent vérifier, 	comment vous avez choisi de les traiter (dans le main, dans la méthode…), ce qu'il se passe quand l'exception est levée et de quelle classe d'exception votre exception personnalisée hérite. L'utilisation d'une classe d'exceptions plutôt qu'une autre doit évidemment être justifiée.
  * Une partie Tests décomposées en deux parties :
    1. Une partie Tests unitaires : Description de tests qui permettent de valider les parties importantes de votre code, prises séparément et qui permettent de vérifier que vos exceptions sont correctement levées. Vous vous contentez ici de décrire (nom du test + ce qu'il doit vérifier) les tests que vous allez effectuer sans les exécuter.
    1. Une partie Tests d'intégration : Description d'un ou plusieurs scénario permettant de valider votre projet dans son intégralité. Un scénario = 1 ou plusieurs tests mis bout à bout. Vous vous contentez ici de décrire les scénarii (nom du scénario + ce qu'il doit vérifier) que vous allez effectuer sans les exécuter.
Cette partie doit permettre de vérifier que votre programme correspond bien aux exigences qui ont été définies dans la partie analyse. Les tests doivent donc être réfléchis et ils doivent vérifier que les besoins de l'application ont été respectés.
  * Une partie Validation qui permet de montrer la faisabilité du programme. C'est ici que vous déroulez les tests unitaires et le ou les scénarii que vous avez  décrits dans la partie précédente et que vous montrez que ceux-ci ne provoquent pas d'erreurs ou qu'ils lèvent bien les exceptions souhaitées.
  * Une partie Révision qui indique quelles sont les corrections que vous avez effectuées suite à la vérification du code. Cette partie est optionnelle et est généralement omise dans un rapport de projet. Néanmoins, si un de vos tests a montré une grosse lacune de votre programme, il est intéressant de renseigner cette partie.
  * Une partie Conclusion dans laquelle vous devez dans un premier temps rappeler la problématique globale du projet et les besoins. Vous revenez brièvement sur vos choix de conception et de réalisation par rapport aux exigences attendues. Vous pouvez vous aider d'un tableau dans lequel vous précisez pour chaque exigence, si elle a été traitée et si elle est fonctionnelle. Vous parlez aussi des points durs que vous avez rencontré tout au long du projet. Finalement, vous analysez vos résultats par rapport à ce qui était attendu. Vous ne devez pas mettre des phrases du type "j'ai beaucoup aimé ce projet …." car ca n'a pas d'intérêt dans un rapport technique !