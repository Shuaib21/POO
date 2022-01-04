import java.util.Scanner;
import javax.swing.*;

import java.awt.*;
import javax.swing.plaf.ColorUIResource;

public class Jouer {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    boolean correcte = false;
    do {
      System.out.println("Mode de jeu:");
      System.out.println("A: Mode graphique");
      System.out.println("B: Mode texte");
      String choix = sc.next();
      switch (choix) {
        case "A":
          UIManager.getDefaults().put("Button.disabledText", new ColorUIResource(Color.LIGHT_GRAY));
          EventQueue.invokeLater(() -> {
            VueCatan view = new VueCatan();
            view.pack();
            view.setVisible(true);
          });
          correcte = true;
          break;
        case "B":
          String reponse;
          Joueur[] tabJ;
          do {
            System.out.println("Nombre de joueurs:");
            System.out.println("A: 3");
            System.out.println("B: 4");
            reponse = sc.next();
            switch (reponse) {
              case "A":
                tabJ = new Joueur[3];
                for (int i = 0; i < 3; i++) {
                  tabJ[i] = creerJoueur(sc, i);
                }
                break;
              case "B":
                tabJ = new Joueur[4];
                for (int i = 0; i < 4; i++) {
                  tabJ[i] = creerJoueur(sc, i);
                }
                break;
              default:
                System.out.println("Nous n'avons pas compris votre réponse");
                tabJ = new Joueur[0]; // Pour éviter les erreurs d'initialisation en créeant une partie
            }
          } while (!reponse.equals("A") && !reponse.equals("B"));
          new Partie(tabJ).jouerPartie(sc);
          correcte = true;
          break;
        default:
          System.out.println("Nous n'avons pas compris votre réponse");
          break;
      }
    } while (!correcte);
    sc.close();
  }

  public static Joueur creerJoueur(Scanner sc, int i) {
    String couleur = "";
    switch (i) {
      case 1:
        couleur = "RED";
        break;
      case 2:
        couleur = "GREEN";
        break;
      case 3:
        couleur = "YELLOW";
        break;
      case 4:
        couleur = "BLUE";
        break;
    }
    System.out.println("Veuillez entreer le pseudo du joueur :");
    String pseudo = sc.next();
    String reponse;
    do {
      System.out.println("Joueur “humain” ou “IA” ?");
      reponse = sc.next();
      if (!reponse.equals("humain") && !reponse.equals("IA")) {
        System.out.println("Nous n'avons pas compris votre réponse");
      }
    } while (!reponse.equals("humain") && !reponse.equals("IA"));
    if (reponse.equals("humain")) {
      return new Joueur(couleur, pseudo);
    }
    return new Joueur(couleur, pseudo, false);
  }
}
