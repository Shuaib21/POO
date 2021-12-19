import java.util.ArrayList;
import java.util.Scanner;

public abstract class Tour {

  protected Joueur j;
  protected Plateau p;
  protected static ArrayList<CarteDev> cartes = new ArrayList<CarteDev>();
  protected static Joueur contientChevalierPuissant;
  protected static int nbrChevalierMax = 2;

  public Tour(Joueur j, Plateau p) {
    this.j = j;
    this.p = p;
  }

  protected static void genereCartes() {
    for (int i = 0; i < 5; i++) {
      cartes.add(new CarteDev("Point de victoire"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès routes"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès ressources"));
    }
    for (int i = 0; i < 2; i++) {
      cartes.add(new CarteDev("Progrès monopole"));
    }
    for (int i = 0; i < 14; i++) {
      cartes.add(new CarteDev("Chevalier"));
    }
  }

  public int getCoordonnee(char c) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Veuillez entrer la coordonée " + c);
    int coord = sc.nextInt() - 1;
    while (coord < 0 || coord >= p.getTaille()) {
      System.out.println(
        "Coordonnée incorrecte:\nVeuillez entrer une coordonnée entre 1 et " +
        p.getTaille()
      );
      coord = sc.nextInt() - 1;
    }
    // sc.close();
    return coord;
  }

  public abstract void ajouterRoute();

  public abstract void ajouterColonie();
}
