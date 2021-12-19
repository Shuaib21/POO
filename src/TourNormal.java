import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class TourNormal extends Tour {

  private int sommeDés;
  private Joueur[] tabJ;

  TourNormal(Joueur j, Plateau p, Joueur[] tabJ) {
    super(j, p);
    sommeDés = lancerLesDés();
    this.tabJ = tabJ;
    toucherRessource();
    // appelez la fonction du 7 ou chevalier si le 7 sort
  }

  private void toucherRessource() {
    for (int i = 1; i < p.getTaille(); i += 2) {
      for (int j = 1; j < p.getTaille(); j += 2) {
        CaseRessource cr = p.selctionnerCaseRess(i, j);
        toucheRessAux(i - 1, j - 1, cr);
        toucheRessAux(i - 1, j + 1, cr);
        toucheRessAux(i + 1, j - 1, cr);
        toucheRessAux(i + 1, j + 1, cr);
      }
    }
  }

  private void toucheRessAux(int x, int y, CaseRessource cr) {
    CaseColonie cc;
    if (cr != null && !cr.getContientVoleur() && cr.num == sommeDés) {
      cc = p.selctionnerCaseColonie(x, y);
      if (!cc.getestVide()) {
        if (cc.getestVille()) {
          cc.getJ().ajouterRessource(cr.ressource);
        }
        cc.getJ().ajouterRessource(cr.ressource);
      }
    }
  }

  private static int lancerLesDés() {
    Random r = new Random();
    int d1 = 1 + r.nextInt(6);
    int d2 = 1 + r.nextInt(6);
    return d1 + d2;
  }

  public void ajouterColonie() {
    if (
      j.combienRessource("ARGILE") == 0 ||
      j.combienRessource("BOIS") == 0 ||
      j.combienRessource("CHAMPS") == 0 ||
      j.combienRessource("MOUTON") == 0
    ) {
      System.out.println(
        "Vous ne posseder pas assez de ressource pour construire votre colonie"
      );
      return;
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x = getCoordonnee('x');
      int y = getCoordonnee('y');

      if (p.selctionnerCaseColonie(x, y) != null) {
        if (p.selctionnerCaseColonie(x, y).getestVide()) {
          if (estColleARoute(x, y)) {
            p.selctionnerCaseColonie(x, y).mettreColonie(j);
            ajouter = true;
            j.enleverRessource("ARGILE");
            j.enleverRessource("BOIS");
            j.enleverRessource("CHAMPS");
            j.enleverRessource("MOUTON");
            j.ajouterPoint();
            if (p.selctionnerCaseColonie(x, y).estPort()) {
              boolean possede = false;
              for (String a : j.getMesPorts()) {
                if (a.equals(p.selctionnerCaseColonie(x, y).getPort())) {
                  possede = true;
                }
              }
              if (!possede) {
                j.getMesPorts().add(p.selctionnerCaseColonie(x, y).getPort());
              }
            }
          }
        }
      }
    }
  }

  private boolean estColleARoute(int x, int y) {
    if (
      x + 1 < p.getTaille() &&
      p.selctionnerCaseRoute(x + 1, y).getestVide() &&
      p.selctionnerCaseRoute(x + 1, y).getJ() == j
    ) {
      return true;
    }
    if (
      x - 1 >= 0 &&
      p.selctionnerCaseRoute(x - 1, y).getestVide() &&
      p.selctionnerCaseRoute(x - 1, y).getJ() == j
    ) {
      return true;
    }
    if (
      y + 1 < p.getTaille() &&
      p.selctionnerCaseRoute(x, y + 1).getestVide() &&
      p.selctionnerCaseRoute(x, y + 1).getJ() == j
    ) {
      return true;
    }
    if (
      y - 1 >= 0 &&
      p.selctionnerCaseRoute(x, y - 1).getestVide() &&
      p.selctionnerCaseRoute(x, y - 1).getJ() == j
    ) {
      return true;
    }
    return false;
  }

  public void ajouterRoute() {
    if (j.combienRessource("BOIS") == 0 || j.combienRessource("ARGILE") == 0) {
      System.out.println(
        "Vous n'avez pas assez de ressource pour construire une route"
      );
      return;
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x = getCoordonnee('x');
      int y = getCoordonnee('y');
      if (p.selctionnerCaseRoute(x, y) != null) {
        if (p.selctionnerCaseRoute(x, y).getestVide()) {
          if (estColleARoute(x, y)) {
            p.selctionnerCaseRoute(x, y).mettreRoute(j);
            j.enleverRessource("BOIS");
            j.enleverRessource("ARGILE");
            ajouter = true;
          }
        }
      }
    }
  }

  private boolean estColleColonie(int x, int y) {
    if (
      x + 1 < p.getTaille() &&
      p.selctionnerCaseColonie(x + 1, y) != null &&
      p.selctionnerCaseColonie(x + 1, y).getestVide() &&
      p.selctionnerCaseColonie(x + 1, y).getJ() == j
    ) {
      return true;
    }
    if (
      x - 1 >= 0 &&
      p.selctionnerCaseColonie(x + 1, y) != null &&
      p.selctionnerCaseColonie(x - 1, y).getestVide() &&
      p.selctionnerCaseColonie(x - 1, y).getJ() == j
    ) {
      return true;
    }
    if (
      y + 1 < p.getTaille() &&
      p.selctionnerCaseColonie(x + 1, y) != null &&
      p.selctionnerCaseColonie(x, y + 1).getestVide() &&
      p.selctionnerCaseColonie(x, y + 1).getJ() == j
    ) {
      return true;
    }
    if (
      y - 1 >= 0 &&
      p.selctionnerCaseColonie(x + 1, y) != null &&
      p.selctionnerCaseColonie(x, y - 1).getestVide() &&
      p.selctionnerCaseColonie(x, y - 1).getJ() == j
    ) {
      return true;
    }
    return false;
  }

  public void echangerAvecPort() {
    if (j.getMesPorts().size() == 0) {
      System.out.println("Vous ne possedé pas de port");
    } else {
      System.out.println("Vous possedez ces ports :");
      for (String a : j.getMesPorts()) {
        System.out.println("- " + a);
      }
      System.out.println("- ANNULER");
      Scanner sci = new Scanner(System.in);
      System.out.println("Vous voulez jouer qu'elle port");
      String choix = sci.next();
      if (choix.equals("ANNULER")) {
        return;
      }
      int nb = 0;
      if (choix.equals("3:1")) {
        jouerPort3_1();
      } else if (choix.equals("2M:1")) {} else if (
        choix.equals("2B:1")
      ) {} else if (choix.equals("2C:1")) {} else if (
        choix.equals("2A:1")
      ) {} else if (choix.equals("2P:1")) {} else {
        System.out.println(
          "Nous n'avons pas compris votre choix veuillez reessayer\n"
        );
        echangerAvecPort();
      }
    }
  }

  private void jouerPort3_1() {}

  public void ajouterVille() {
    if (j.combienRessource("CHAMPS") < 2 || j.combienRessource("PIERRE") < 2) {
      System.out.print(
        "Vous ne possedez pas assez de ressource pour construire une ville"
      );
      return;
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x = getCoordonnee('x');
      int y = getCoordonnee('y');
      if (
        p.selctionnerCaseColonie(x, y) != null &&
        !p.selctionnerCaseColonie(x, y).getestVide() &&
        p.selctionnerCaseColonie(x, y).getJ().equals(j)
      ) {
        if (p.selctionnerCaseColonie(x, y).getestVille()) {
          System.out.println("C'est déjà une ville");
        } else {
          p.selctionnerCaseColonie(x, y).transformerenVille();
          j.enleverRessource("PIERRE");
          j.enleverRessource("PIERRE");
          j.enleverRessource("PIERRE");
          j.enleverRessource("CHAMPS");
          j.enleverRessource("CHAMPS");
          ajouter = true;
          j.ajouterPoint();
        }
      }
    }
  }

  public void acheterCartDev() {
    if (
      j.combienRessource("CHAMPS") == 0 ||
      j.combienRessource("PIERRE") == 0 ||
      j.combienRessource("MOUTON") == 0
    ) {
      System.out.println(
        "Vous n'avez pas les ressources demandées pour acheter une carte développement"
      );
      return;
    }
    if (!cartes.isEmpty()) {
      j.enleverRessource("CHAMPS");
      j.enleverRessource("PIERRE");
      j.enleverRessource("MOUTON");

      Collections.shuffle(Tour.cartes);
      j.ajouterCarteDev(Tour.cartes.get(0));
      Tour.cartes.remove(0);
    }
  }

  // if num = 7 jouez carte chevalier

  public void jouezCarteDev() {
    System.out.println("Choix:");
    System.out.println("A: Jouer Carte Chevalier");
    System.out.println("B: Jouer Carte Progrès routes");
    System.out.println("C: Jouer Carte Progrès ressources");
    System.out.println("D: Jouer Carte Progrès monopole");
    Scanner sc = new Scanner(System.in);
    String choix = sc.next();

    switch (choix) {
      case "A":
        break;
      case "B":
        break;
      case "C":
        break;
      case "D":
        break;
      default:
        System.out.println("Choix incorrect");
        break;
    }
  }
}
