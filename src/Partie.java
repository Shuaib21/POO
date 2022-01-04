import java.util.Random;
import java.util.Scanner;

public class Partie {

  private final Joueur[] tabJ;
  private Plateau p;
  TourNormal t;
  PremierTour pt;
  private int tour;
  private VueCatan v;
  private int pTour;

  Partie(Joueur[] j) {
    tabJ = j;
    p = new Plateau(4);
  }

  Partie(Joueur[] j, VueCatan v) {
    this(j);
    this.v = v;
    tour = 0;
    pTour = 0;
  }

  public void tourFini() {
    tour++;
    jouerPartieInter(v);
  }

  public void pTourFini() {
    pTour++;
    jouerPartieInter(v);
  }

  public int getTour() {
    return tour;
  }

  public int getpTour() {
    return pTour;
  }

  public Plateau getP() {
    return p;
  }

  public Joueur[] getTabJ() {
    return tabJ;
  }

  public void jouerPartie() {
    Scanner sc = new Scanner(System.in);
    init(sc);
    while (!partiefini()) {
      for (Joueur j : tabJ) {
        System.out.println("Tour de : " + j.getPseudo());

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
            if (j.getNbrVilles() < j.getNbrColonies()) {
              if (j.combienRessource("CHAMPS") < 2 || j.combienRessource("PIERRE") < 3) {
                t.ajouterVille();
              }
            }
            if (j.combienRessource("ARGILE") != 0 &&
                j.combienRessource("BOIS") != 0 &&
                j.combienRessource("CHAMPS") != 0 &&
                j.combienRessource("MOUTON") != 0) {
              t.ajouterColonie();
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
              t.acheterCartDev(false);
              break;
            case "E":
              t.jouezCarteDev(sc);
              break;
            case "F":
              t.echangerAvecPort(sc);
              break;
            case "G":
              break;
            default:
              System.out.println("Choix incorrect");
              break;
          }
        } while (!choix.equals("G"));
      }
    }
    // Afficher les cartes Point de victoire
    sc.close();
  }

  public void jouerPartieInter(VueCatan v) {
    if (pTour <= tabJ.length * 2 - 1) {
      System.out.println(pTour);
      v.premierTour = true;
      initInter(v);
    } else {
      if (tour == 0) {
        v.getT().actuRess();
        Tour.genereCartes();
        pTour = 10;
        v.premierTour = false;
      }
      if (!partiefini()) {
        v.tourDeQui.setText("Tour de " + tabJ[tour % tabJ.length].getPseudo());
        v.validate();
        v.repaint();
        System.out.println("Tour de : " + tabJ[tour % tabJ.length].getPseudo()); // afficher le joueur qui joue
        t = new TourNormal(tabJ[tour % tabJ.length], p, tabJ, v);
        if (!tabJ[tour % tabJ.length].estHumain) {
          String choix;

          do {
            Random rand = new Random();
            int n = rand.nextInt(7) + 1;
            Joueur j = tabJ[tour % tabJ.length];
            if (j.getNbrVilles() < j.getNbrColonies()) {
              if (j.combienRessource("CHAMPS") < 2 || j.combienRessource("PIERRE") < 3) {
                t.ajouterVille();
              }
            }
            if (j.combienRessource("ARGILE") != 0 &&
                j.combienRessource("BOIS") != 0 &&
                j.combienRessource("CHAMPS") != 0 &&
                j.combienRessource("MOUTON") != 0) {
              t.ajouterColonie();
            }
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

            switch (choix) {
              case "A":
                t.ajouterColonie();
                break;
              case "B":
                t.ajouterRoute(false);
                break;
              case "C":
                t.ajouterVille();
                break;
              case "D":
                t.acheterCartDev(true);
                break;
              case "E":
                t.jouezCarteDev();
                break;
              case "F":
                t.echangerAvecPort();
                break;
              default:
                break;
            }
          } while (!choix.equals("G"));
          v.validate();
          v.repaint();
          tourFini();
        }
      } else {
        System.out.println("partie fini");// afficher la fin
      }
    }
  }

  private void init(Scanner sc) {
    for (Joueur j : tabJ) {
      System.out.println("Tour de : " + j.getPseudo());
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie(sc);
      t.ajouterRoute(sc);
      t.toucherRessource();
      p.afficher();
    }
    for (int i = tabJ.length - 1; i >= 0; i--) {
      Joueur j = tabJ[i];
      System.out.println("Tour de : " + j.getPseudo());
      PremierTour t = new PremierTour(j, p);
      t.ajouterColonie(sc);
      t.ajouterRoute(sc);
      p.afficher();
    }
    Tour.genereCartes();
  }

  private void initInter(VueCatan v) {
    if (pTour < tabJ.length) {
      v.tourDeQui.setText("Tour de " + tabJ[pTour % tabJ.length].getPseudo());
      v.validate();
      v.repaint();
      pt = new PremierTour(tabJ[pTour % tabJ.length], p, v);
      if (!tabJ[pTour % tabJ.length].estHumain) {
        pt.ajouterColonie();
        pt.ajouterRoute();
        pTourFini();
      }
    } else {
      v.tourDeQui.setText("Tour de " + tabJ[tabJ.length - pTour % tabJ.length - 1].getPseudo());
      v.validate();
      v.repaint();
      pt = new PremierTour(tabJ[tabJ.length - pTour % tabJ.length - 1], p, v);
      if (!tabJ[tabJ.length - pTour % tabJ.length - 1].estHumain) {
        pt.ajouterColonie();
        pt.ajouterRoute();
        pTourFini();
      }
    }
    v.validate();
    v.repaint();
  }

  private boolean partiefini() {
    for (Joueur a : tabJ) {
      if (a.getPoint() >= 10) {
        System.out.println(a.getPseudo() + " a remporté la partie.");
        return true;
      }
    }
    return false;
  }
}
