import java.util.Random;
import java.util.Scanner;

public class Partie {

  private final Joueur[] tabJ;
  private Plateau p;

  Partie(Joueur[] j, Plateau p) {
    tabJ = j;
    this.p = p;
  }

  public void jouerPartie() {
    Scanner sc = new Scanner(System.in);
    init(sc);
    while (!partiefini()) {
      for (Joueur j : tabJ) {
        System.out.println("Tour du joueur numéro " + j.numJoueur);

        TourNormal t = new TourNormal(j, p, tabJ, sc);

        String choix;

        do {
          System.out.println("Choix:");
          System.out.println("A: Ajouter une colonie");
          System.out.println("B: Ajouter une route");
          System.out.println("C: Ajouter une ville");
          System.out.println("D: Acheter une carte développement");
          System.out.println("E: Jouer une carte développement");
          System.out.println("F: Echanger avec les ports");
          System.out.println("G: Terminer tour");

          if (j.estHumain) {
            choix = sc.next();
          } else {
            Random rand = new Random();
            int n = rand.nextInt(7) + 1;
            switch (n) {
              case 1:
                choix = "A";
                break;
              case 2:
                choix = "B";
                break;
              case 3:
                choix = "C";
                break;
              case 4:
                choix = "D";
                break;
              case 5:
                choix = "E";
                break;
                case 6:
                choix = "F";
                break;
              default:
                choix = "G";
                break;
            }
          }

          switch (choix) {
            case "A":
              t.ajouterColonie(sc);
              p.afficher();
              break;
            case "B":
              t.ajouterRoute(sc);
              p.afficher();
              break;
            case "C":
              t.ajouterVille(sc);
              p.afficher();
              break;
            case "D":
              t.acheterCartDev();
              break;
            case "E":
              t.jouezCarteDev(sc);
              break;
            case "F":
              t.echangerAvecPort(sc);;
              break;
            case "G":
              break;
            default:
              System.out.println("Choix incorrect");
              break;
          }
          // Afficher les cartes Point de victoire
        } while (!choix.equals("G"));
      }
    }
    sc.close();
  }

  private void init(Scanner sc) {
    for (Joueur j : tabJ) {
      System.out.println("Tour du joueur numéro " + j.numJoueur);
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie(sc);
      t.ajouterRoute(sc);
      t.toucherRessource();
      p.afficher();
    }
    for (int i = tabJ.length - 1; i >= 0; i--) {
      Joueur j = tabJ[i];
      System.out.println("Tour du joueur numéro " + j.numJoueur);
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie(sc);
      t.ajouterRoute(sc);
      p.afficher();
    }
    Tour.genereCartes();
  }

  private boolean partiefini() {
    for (Joueur a : tabJ) {
      if (a.getPoint() >= 10) {
        return true;
      }
    }
    return false;
  }
}
