import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class Tour {

  protected Joueur j;
  protected Plateau p;
  protected VueCatan v;
  protected static ArrayList<CarteDev> cartes = new ArrayList<CarteDev>();
  protected static Joueur contientChevalierPuissant;
  protected static Joueur contientRouteLaPlusLongue;
  protected static ArrayList<CaseRoute> RouteLaPlusLongue;
  protected static int nbrChevalierMax = 2;

  public Tour(Joueur j, Plateau p) {
    this.j = j;
    this.p = p;
  }

  public Tour(Joueur j, Plateau p, VueCatan v) {
    this.j = j;
    this.p = p;
    this.v = v;
  }

  protected static void genereCartes() {
    for (int i = 0; i < 5; i++) {
      cartes.add(new CarteDev("Point de victoire"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès Construction de routes"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès Découverte"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès Monopole"));
    }
    for (int i = 0; i < 14; i++) {
      cartes.add(new CarteDev("Chevalier"));
    }
  }

  public int getCoordonnee(char c, Scanner sc) {
    System.out.println("Veuillez entrer la coordonée " + c);
    int coord = sc.nextInt() - 1;
    while (coord < 0 || coord >= p.getTaille()) {
      System.out.println(
          "Coordonnée incorrecte:\nVeuillez entrer une coordonnée entre 1 et " +
              p.getTaille());
      coord = sc.nextInt() - 1;
    }
    return coord;
  }

  public abstract void ajouterRoute(Scanner sc);

  public abstract void ajouterColonie(Scanner sc);

  public void mettreColonieInter(int x, int y) {
    ImageIcon colRouge = new ImageIcon("./Image/CR.png");
    ImageIcon colVert = new ImageIcon("./Image/CV.png");
    ImageIcon colJaune = new ImageIcon("./Image/CJ.png");
    ImageIcon colBleu = new ImageIcon("./Image/CB.png");

    switch (j.getCouleur()) {
      case "RED":
        v.getT().getTab(x, y).setIcon(colRouge);
        break;
      case "GREEN":
        v.getT().getTab(x, y).setIcon(colVert);
        break;
      case "YELLOW":
        v.getT().getTab(x, y).setIcon(colJaune);
        break;
      default:
        v.getT().getTab(x, y).setIcon(colBleu);
        break;
    }
  }

  public void mettreRouteInter(int x, int y) {
    ImageIcon routeRouge = new ImageIcon("./Image/RR.jpg");
    ImageIcon routeVert = new ImageIcon("./Image/RV.jpg");
    ImageIcon routeJaune = new ImageIcon("./Image/RJ.jpg");
    ImageIcon routeBleu = new ImageIcon("./Image/RB.jpg");

    switch (j.getCouleur()) {
      case "RED":
        v.getT().getTab(x, y).setIcon(routeRouge);
        break;
      case "GREEN":
        v.getT().getTab(x, y).setIcon(routeVert);
        break;
      case "YELLOW":
        v.getT().getTab(x, y).setIcon(routeJaune);
        break;
      default:
        v.getT().getTab(x, y).setIcon(routeBleu);
        break;
    }
  }
}
