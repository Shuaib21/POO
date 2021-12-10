public class Jouer {

  public static void main(String[] args) {
    Plateau p = new Plateau(4);
    p.afficher();

    Joueur j1 = new Joueur("BLUE");
    Joueur j2 = new Joueur("RED");

    Joueur[] tabJ = { j1, j2 };

    Partie part = new Partie(tabJ, p);
    part.jouerPartie();
  }
}
