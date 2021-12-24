import java.io.Console;
import java.util.ArrayList;

public class Joueur {

  private ArrayList<CarteDev> mainDev;
  private int[] mainRess; // [ARGILE, BOIS, CHAMPS, MOUTON, PIERRE]
  private ArrayList<String> mesPorts;
  private String couleur;
  private int point;
  protected final boolean estHumain;
  private String pseudo;
  private int nbrChevaliers = 0;
  private static int nbr = 0;
  protected final int numJoueur;

  Joueur(String couleur) {
    this(couleur, true);
  }

  Joueur(String couleur, boolean estHumain){
    mainDev = new ArrayList<CarteDev>();
    mainRess = new int[5];
    mesPorts = new ArrayList<String>();
    this.couleur = couleur;
    point = 0;
    this.estHumain = estHumain;
    nbr++;
    numJoueur = nbr;
  }

  public String getCouleur() {
    return couleur;
  }

  public int getPoint() {
    return point;
  }

  public String getPseudo() {
    return pseudo;
  }

  public ArrayList<CarteDev> getMainDev() {
    return mainDev;
  }

  public int[] getMainRess() {
    return mainRess;
  }

  public ArrayList<String> getMesPorts() {
    return mesPorts;
  }

  public void ajouterPoint() {
    point++;
  }

  public int combienRessource(String nomRessource) {
    switch (nomRessource) {
      case "ARGILE":
        return mainRess[0];
      case "BOIS":
        return mainRess[1];
      case "CHAMPS":
        return mainRess[2];
      case "MOUTON":
        return mainRess[3];
      case "PIERRE":
        return mainRess[4];
    }
    return 0;
  }

  public void ajouterRessource(String nomRessource) {
    switch (nomRessource) {
      case "ARGILE":
        mainRess[0]++;
        break;
      case "BOIS":
        mainRess[1]++;
        break;
      case "CHAMPS":
        mainRess[2]++;
        break;
      case "MOUTON":
        mainRess[3]++;
        break;
      case "PIERRE":
        mainRess[4]++;
        break;
    }
  }

  public void enleverRessource(String nomRessource) {
    switch (nomRessource) {
      case "ARGILE":
        mainRess[0]--;
        break;
      case "BOIS":
        mainRess[1]--;
        break;
      case "CHAMPS":
        mainRess[2]--;
        break;
      case "MOUTON":
        mainRess[3]--;
        break;
      case "PIERRE":
        mainRess[4]--;
        break;
    }
  }

  public void ajouterCarteDev(CarteDev c) {
    mainDev.add(c);
    if (c.pouvoir.equals("Chevalier")) {
      nbrChevaliers++;
      if (nbrChevaliers > Tour.nbrChevalierMax) {
        Tour.contientChevalierPuissant = this;
        Tour.nbrChevalierMax = nbrChevaliers;
      }
    }
    if (c.pouvoir.equals("Point de victoire")) {
      ajouterPoint();
    }
  }

  public boolean enleverCarteDev(String pouvoir) {
    for (CarteDev c : mainDev) {
      if (c.pouvoir.equals(pouvoir)) {
        mainDev.remove(c);
        return true;
      }
    }
    return false;
  }

  public String enCouleur(String a) {
    switch (couleur) {
      case "RED":
        return ConsoleColors.RED + a + ConsoleColors.RESET;
      case "GREEN":
        return ConsoleColors.GREEN + a + ConsoleColors.RESET;
      case "YELLOW":
        return ConsoleColors.YELLOW + a + ConsoleColors.RESET;
      case "BLUE":
        return ConsoleColors.BLUE + a + ConsoleColors.RESET;
      case "PURPLE":
        return ConsoleColors.PURPLE + a + ConsoleColors.RESET;
      case "CYAN":
        return ConsoleColors.CYAN + a + ConsoleColors.RESET;
      case "WHITE":
        return ConsoleColors.WHITE + a + ConsoleColors.RESET;
    }
    return a;
  }
}
