import java.util.Random;

public class TourNormal extends Tour {

  private int sommeDés;
  private Joueur[] tabJ;

  TourNormal(Joueur j, Plateau p, Joueur[] tabJ) {
    super(j, p);
    sommeDés = lancerLesDés();
    this.tabJ = tabJ;
    toucherRessource();
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
          cc.getJ().ajouterCarteRessource(cr.ressource);
        }
        cc.getJ().ajouterCarteRessource(cr.ressource);
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
    int[] r = { 0, 0, 0, 0 }; // FORET, PRE, COLLINE, CHAMPS
    for (CarteRess a : j.getMainRess()) {
      if (a.getR().equals("FORET")) {
        r[0]++;
      }
      if (a.getR().equals("PRE")) {
        r[1]++;
      }
      if (a.getR().equals("COLLINE")) {
        r[2]++;
      }
      if (a.getR().equals("CHAMPS")) {
        r[3]++;
      }
    }
    for (int a : r) {
      if (a == 0) {
        System.out.println("Vous n'avez pas les ressources demandé");
        return;
      }
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
    int[] r = { 0, 0 }; // FORET, PRE, COLLINE, CHAMPS
    for (CarteRess a : j.getMainRess()) {
      if (a.getR().equals("FORET")) {
        r[0]++;
      }
      if (a.getR().equals("COLLINE")) {
        r[2]++;
      }
    }
    for (int a : r) {
      if (a == 0) {
        System.out.println("Vous n'avez pas les ressources demandé");
        return;
      }
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x = getCoordonnee('x');
      int y = getCoordonnee('y');

      if (p.selctionnerCaseRoute(x, y) != null) {
        if (p.selctionnerCaseRoute(x, y).getestVide()) {
          if (estColleARoute(x, y)) {
            p.selctionnerCaseRoute(x, y).mettreRoute(j);
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
  // public void ajouterVille();

  // public void acheterCartDev();

  // jouezCarteDev() ;

}
