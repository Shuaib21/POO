import java.util.Scanner;

public class Partie {

  private final Joueur[] tabJ;
  private Plateau p;

  Partie(Joueur[] j, Plateau p) {
    tabJ = j;
    this.p = p;
  }

  public void jouerPartie() {
    init();
    while (!partiefini()) {
      for (Joueur j : tabJ) {
        TourNormal t = new TourNormal(j, p, tabJ);

        Scanner sc = new Scanner(System.in);
        String choix;

        do {
          System.out.println("Choix:");
          System.out.println("A: Ajouter une colonie");
          System.out.println("B: Ajouter une route");
          System.out.println("C: Ajouter une ville");
          System.out.println("D: Terminer tour");
          choix = sc.next();

          switch (choix) {
            case "A":
              t.ajouterColonie();
              p.afficher();
              break;
            case "B":
              t.ajouterRoute();
              p.afficher();
              break;
            case "C":
              t.ajouterVille();
              p.afficher();
              break;
            case "D":
              break;
            default:
              System.out.println("Choix incorrect");
              break;
          }
        } while (!choix.equals("C"));
      }
    }
  }

  private void init() {
    for (Joueur j : tabJ) {
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie();
      t.ajouterRoute();
      t.toucherRessource();
      p.afficher();
    }
    for (int i = tabJ.length - 1; i >= 0; i--) {
      Joueur j = tabJ[i];
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie();
      t.ajouterRoute();
      p.afficher();
    }
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
