# Catan

## Table des matières
* [Info general](#info-general)
* [Technologies](#technologies)
* [Setup](#setup)

## Info general
Le projet implémente le jeu Catan qui peut être joué soit en mode graphique, soit en mode texte dépendant du choix de l'utilisateur.

### Règles implémentées :
#### Un joueur est capable de :
— construire des routes, villes, cités ;
— consulter ses ressources ;
— gérer le personnage du voleur en cas de 7 aux dés ;
— acheter, stocker, utiliser les cartes spéciales de développement ;
— échanger des ressources via les ports.
Le score se calcule simplement à partir du nombre de ville et de cités construites.(Chaque colonie: 1 point de victoire ; Chaque ville: 2 points de victoire)
#### Bonus particuliers :
— le tirage d’une Carte Point de victoire (1 point de victoire); 
— le fait d’être celui qui a le Chevalier le plus puissant (2 points de victoire);
— le fait d’être le joueur qui a la route de commerce la plus longue (2 points de victoire)
	
## Setup
Afin d'exécuter le projet, rendez-vous dans POO/src via le terminal et lancer la commande javac qui est un compilateur de code source Java avec "javac *.java" suivie de la commande java qui invoque la JVM (Java Virtual Machine) sur un fichier de classes avec "java Jouer". Les 3 lignes suivantes seront ensuite affichées sur le terminal :
Mode de jeu:
A: Mode graphique
B: Mode texte

Dépendant de votre choix (A ou B), vous aurez la possibilité de jouer soit en mode graphique, soit en mode texte

```
$ cd ../POO/src
$ javac *.java
$ java Jouer
```