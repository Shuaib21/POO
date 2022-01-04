public class Jouer {

  public static void main(String[] args) {
    Joueur j1 = new Joueur("BLUE", "", false);
    Joueur j2 = new Joueur("RED", "", false);

    Joueur[] tabJ = { j1, j2 };

    Partie part = new Partie(tabJ);
    part.jouerPartie();
  }
}
