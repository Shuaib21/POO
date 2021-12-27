import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class TourNormal extends Tour {

  private int sommeDés;
  private Joueur[] tabJ;

  TourNormal(Joueur j, Plateau p, Joueur[] tabJ, Scanner sc) {
    super(j, p);
    sommeDés = lancerLesDés();
    this.tabJ = tabJ;
    toucherRessource();
    if (sommeDés == 7) {
      deplacerVoleur(sc);
    }
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
      if (!cc.getEstVide()) {
        if (cc.getEstVille()) {
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

  public void ajouterColonie(Scanner sc) {
    if (j.combienRessource("ARGILE") == 0 ||
        j.combienRessource("BOIS") == 0 ||
        j.combienRessource("CHAMPS") == 0 ||
        j.combienRessource("MOUTON") == 0) {
      System.out.println(
          "Vous ne posseder pas assez de ressource pour construire votre colonie");
      return;
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x;
      int y;
      if (j.estHumain) {
        x = getCoordonnee('x', sc);
        y = getCoordonnee('y', sc);
      } else {
        Random rand = new Random();
        x = rand.nextInt(p.getTaille());
        y = rand.nextInt(p.getTaille());
      }

      if (p.selctionnerCaseColonie(x, y) != null) {
        if (p.selctionnerCaseColonie(x, y).getEstVide()) {
          if (estColleARoute(x, y)) {
            p.selctionnerCaseColonie(x, y).mettreColonie(j);
            j.ajouterUneColonie();
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
    if (x + 1 < p.getTaille() &&
        !p.selctionnerCaseRoute(x + 1, y).getEstVide() &&
        p.selctionnerCaseRoute(x + 1, y).getJ() == j) {
      return true;
    }
    if (x - 1 >= 0 &&
        !p.selctionnerCaseRoute(x - 1, y).getEstVide() &&
        p.selctionnerCaseRoute(x - 1, y).getJ() == j) {
      return true;
    }
    if (y + 1 < p.getTaille() &&
        !p.selctionnerCaseRoute(x, y + 1).getEstVide() &&
        p.selctionnerCaseRoute(x, y + 1).getJ() == j) {
      return true;
    }
    if (y - 1 >= 0 &&
        !p.selctionnerCaseRoute(x, y - 1).getEstVide() &&
        p.selctionnerCaseRoute(x, y - 1).getJ() == j) {
      return true;
    }
    return false;
  }

  public void ajouterRoute(Scanner sc) {
    ajouterRoute(false, sc);
  }

  private void ajouterRoute(boolean utiliseChevalier, Scanner sc) {
    if (!utiliseChevalier) {
      if (j.combienRessource("BOIS") == 0 || j.combienRessource("ARGILE") == 0) {
        System.out.println(
            "Vous n'avez pas assez de ressource pour construire une route");
        return;
      }
    }
    boolean ajouter = false;
    while (!ajouter) {
      int x;
      int y;
      if (j.estHumain) {
        x = getCoordonnee('x', sc);
        y = getCoordonnee('y', sc);
      } else {
        Random rand = new Random();
        x = rand.nextInt(p.getTaille());
        y = rand.nextInt(p.getTaille());
      }
      if (p.selctionnerCaseRoute(x, y) != null) {
        if (p.selctionnerCaseRoute(x, y).getEstVide()) {
          if (estColleAColonie(x, y) || routeColleARoute(x, y)) {
            p.selctionnerCaseRoute(x, y).mettreRoute(j);
            if (!utiliseChevalier) {
              j.enleverRessource("BOIS");
              j.enleverRessource("ARGILE");
            }
            ajouter = true;
          }
        }
      }
    }
  }

  private boolean estColleAColonie(int x, int y) {
    if (x + 1 < p.getTaille() &&
        p.selctionnerCaseColonie(x + 1, y) != null &&
        !p.selctionnerCaseColonie(x + 1, y).getEstVide() &&
        p.selctionnerCaseColonie(x + 1, y).getJ() == j) {
      return true;
    }
    if (x - 1 >= 0 &&
        p.selctionnerCaseColonie(x - 1, y) != null &&
        !p.selctionnerCaseColonie(x - 1, y).getEstVide() &&
        p.selctionnerCaseColonie(x - 1, y).getJ() == j) {
      return true;
    }
    if (y + 1 < p.getTaille() &&
        p.selctionnerCaseColonie(x, y + 1) != null &&
        !p.selctionnerCaseColonie(x, y + 1).getEstVide() &&
        p.selctionnerCaseColonie(x, y + 1).getJ() == j) {
      return true;
    }
    if (y - 1 >= 0 &&
        p.selctionnerCaseColonie(x, y - 1) != null &&
        !p.selctionnerCaseColonie(x, y - 1).getEstVide() &&
        p.selctionnerCaseColonie(x, y - 1).getJ() == j) {
      return true;
    }
    return false;
  }

  private boolean routeColleARoute(int x, int y) {
    if (x + 1 < p.getTaille() && y + 1 < p.getTaille() &&
        !p.selctionnerCaseRoute(x + 1, y + 1).getEstVide() &&
        p.selctionnerCaseRoute(x + 1, y + 1).getJ() == j) {
      return true;
    }
    if (x - 1 >= 0 && y + 1 < p.getTaille() &&
        !p.selctionnerCaseRoute(x - 1, y + 1).getEstVide() &&
        p.selctionnerCaseRoute(x - 1, y + 1).getJ() == j) {
      return true;
    }
    if (x + 1 < p.getTaille() && y - 1 >= 0 &&
        !p.selctionnerCaseRoute(x + 1, y - 1).getEstVide() &&
        p.selctionnerCaseRoute(x + 1, y - 1).getJ() == j) {
      return true;
    }
    if (x - 1 >= 0 && y - 1 >= 0 &&
        !p.selctionnerCaseRoute(x - 1, y - 1).getEstVide() &&
        p.selctionnerCaseRoute(x - 1, y - 1).getJ() == j) {
      return true;
    }
    return false;
  }

  public void echangerAvecPort() {
    if (j.getMesPorts().size() == 0) {
      System.out.println("Vous ne possedez pas de port");
    } else {
      System.out.println("Vous possedez ces ports :");
      for (String a : j.getMesPorts()) {
        System.out.println("- " + a);
      }
      System.out.println("- ANNULER");
      Scanner sc = new Scanner(System.in);
      System.out.println("Vous voulez jouer quel port ?");
      String choix = sc.nextLine();
      if (choix.equals("ANNULER")) {
        return;
      }
      int nb = 0;
      if (choix.equals("3:1")) {
        jouerPort3_1();
        return ;
      } else if (choix.equals("2M:1")) {
        jouerport2_1("MOUTON");
        return ;
      } else if (choix.equals("2B:1")) {
        jouerport2_1("BOIS");
        return ;
      } else if (choix.equals("2C:1")) {
        jouerport2_1("CHAMPS");
        return ;
      } else if (choix.equals("2A:1")) {
        jouerport2_1("ARGILE");
        return ;
      } else if (choix.equals("2P:1")) {
        jouerport2_1("PIERRE");
        return ;
      } else {
        System.out.println("Nous n'avons pas compris votre choix veuillez reessayer\n");
        echangerAvecPort();
        return ;
      }
    }
  }

  private void jouerport2_1(String ressource) {
    if(j.combienRessource(ressource)<2){
      System.out.println("Vous n'avez pas assez de "+ressource) ;
      return ;
    }
    j.enleverRessource(ressource);
    j.enleverRessource(ressource);
    Scanner scRess = new Scanner(System.in);
    System.out.println("Vous voulez echanger avec quelles ressources ?");
    String nvchoix = scRess.nextLine();
    boolean ressExistPas = true ;
    while(ressExistPas){
      if(!nvchoix.equals("MOUTON") && !nvchoix.equals("PIERRE") && !nvchoix.equals("BOIS") && !nvchoix.equals("ARGILE") && !nvchoix.equals("CHAMPS")){
        System.out.println("Nous n'avons pas compris votre demande, veuillez selectionner la ressource avec la quelles vous voulez echanger ?");
        nvchoix = scRess.nextLine();
      }else{
        ressExistPas = false ;
        j.ajouterRessource(nvchoix);
      }
    }
  }

  private void jouerPort3_1() {
    if(j.combienRessource("PIERRE")<3 && j.combienRessource("ARGILE")<3 && j.combienRessource("BOIS")<3 && j.combienRessource("CHAMPS")<3 && j.combienRessource("MOUTON")<3){
      System.out.println("Vous n'avez pas les ressources demandé pour faire l'ecahnge 3 en 1.") ;
      return ;
    }
    Scanner scRessEchan = new Scanner(System.in);
    System.out.println("Vous voulez echanger quelles ressources ?");
    String choix = scRessEchan.nextLine();
    boolean PeuxPas = true ;
    while(PeuxPas){
      if(!choix.equals("MOUTON") && !choix.equals("PIERRE") && !choix.equals("BOIS") && !choix.equals("ARGILE") && !choix.equals("CHAMPS")){
        System.out.println("Nous n'avons pas compris votre demande, veuillez selectionner la ressource avec la quelles vous voulez echanger ?");
        choix = scRessEchan.nextLine();
      }else{
        if(j.combienRessource(choix)<3){
          System.out.println("Veuillez selectionner une ressource que vous possedez trois fois :");
          choix = scRessEchan.nextLine();
        }else{
          PeuxPas = false ;
        }
        
      }
    }

    j.enleverRessource(choix);
    j.enleverRessource(choix);
    j.enleverRessource(choix);
    Scanner scRess = new Scanner(System.in);
    System.out.println("Vous voulez echanger avec quelles ressources ?");
    String nvchoix = scRess.nextLine();
    boolean ressExistPas = true ;
    while(ressExistPas){
      if(!nvchoix.equals("MOUTON") && !nvchoix.equals("PIERRE") && !nvchoix.equals("BOIS") && !nvchoix.equals("ARGILE") && !nvchoix.equals("CHAMPS")){
        System.out.println("Nous n'avons pas compris votre demande, veuillez selectionner la ressource avec la quelles vous voulez echanger ?");
        nvchoix = scRess.nextLine();
      }else{
        ressExistPas = false ;
        j.ajouterRessource(nvchoix);
      }
    }
  }

  public void ajouterVille(Scanner sc) {
    if (j.getNbrVilles() < j.getNbrColonies()) {
      if (j.combienRessource("CHAMPS") < 2 || j.combienRessource("PIERRE") < 2) {
        System.out.println(
            "Vous ne possedez pas assez de ressource pour construire une ville");
        return;
      }
      boolean ajouter = false;
      while (!ajouter) {
        int x;
        int y;
        if (j.estHumain) {
          x = getCoordonnee('x', sc);
          y = getCoordonnee('y', sc);
        } else {
          Random rand = new Random();
          x = rand.nextInt(p.getTaille());
          y = rand.nextInt(p.getTaille());
        }
        if (p.selctionnerCaseColonie(x, y) != null &&
            !p.selctionnerCaseColonie(x, y).getEstVide() &&
            p.selctionnerCaseColonie(x, y).getJ().equals(j)) {
          if (p.selctionnerCaseColonie(x, y).getEstVille()) {
            System.out.println("C'est déjà une ville");
          } else {
            p.selctionnerCaseColonie(x, y).transformerEnVille();
            j.enleverRessource("PIERRE");
            j.enleverRessource("PIERRE");
            j.enleverRessource("PIERRE");
            j.enleverRessource("CHAMPS");
            j.enleverRessource("CHAMPS");
            j.ajouterUneVille();
            ajouter = true;
            j.ajouterPoint();
          }
        }
      }
    } else {
      System.out.println("Toutes vos colonies ont déjà été tranformées en villes.");
    }
  }

  public void acheterCartDev() {
    if (j.combienRessource("CHAMPS") == 0 ||
        j.combienRessource("PIERRE") == 0 ||
        j.combienRessource("MOUTON") == 0) {
      System.out.println(
          "Vous n'avez pas les ressources demandées pour acheter une carte développement");
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

  public void jouezCarteDev(Scanner sc) {
    System.out.println("Choix:");
    System.out.println("A: Jouer Carte Chevalier");
    System.out.println("B: Jouer Carte Progrès Construction de routes");
    System.out.println("C: Jouer Carte Progrès Découverte");
    System.out.println("D: Jouer Carte Progrès Monopole");

    String choix;

    if (j.estHumain) {
      choix = sc.next();
    } else {
      Random rand = new Random();
      int n = rand.nextInt(4) + 1;
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
        default:
          choix = "D";
          break;
      }
    }

    switch (choix) {
      case "A":
        if (j.enleverCarteDev("Chevalier")) {
          deplacerVoleur(sc);
        } else {
          System.out.println("Vous ne possédez pas de Carte Chevalier");
        }
        break;
      case "B":
        if (j.enleverCarteDev("Progrès Construction de routes")) {
          ajouterRoute(true, sc);
        } else {
          System.out.println("Vous ne possédez pas de Carte Progrès Construction de routes");
        }
        break;
      case "C":
        if (j.enleverCarteDev("Progrès Découverte")) {
          progresDecouverte(sc);
        } else {
          System.out.println("Vous ne possédez pas de Carte Progrès Découverte");
        }
        break;
      case "D":
        if (j.enleverCarteDev("Progrès Monopole")) {
          progresMonopole(sc);
        } else {
          System.out.println("Vous ne possédez pas de Carte Progrès Monopole");
        }
        break;
      default:
        System.out.println("Choix incorrect");
        break;
    }
  }

  public void deplacerVoleur(Scanner sc) {
    boolean deplacer = false;
    while (!deplacer) {
      int x;
      int y;
      if (j.estHumain) {
        x = getCoordonnee('x', sc);
        y = getCoordonnee('y', sc);
      } else {
        Random rand = new Random();
        x = rand.nextInt(p.getTaille());
        y = rand.nextInt(p.getTaille());
      }

      if (p.selctionnerCasePaysage(x, y) != null && !p.selctionnerCasePaysage(x, y).getContientVoleur()) {
        for (int i = 0; i < p.getTaille(); i++) {
          for (int j = 0; j < p.getTaille(); j++) { // Le Plateau est toujours de taille n x n
            if (p.selctionnerCasePaysage(i, j) != null && p.selctionnerCasePaysage(i, j).getContientVoleur()) {
              p.selctionnerCasePaysage(i, j).setContientVoleur(false);
            }
          }
        }
        p.selctionnerCasePaysage(x, y).setContientVoleur(true);
        deplacer = true;
      } else if (j.estHumain) {
        System.out.println("Le voleur ne peut pas être déplacé dans cette case");
      }

    }
  }

  public void progresDecouverte(Scanner sc) {
    int nbr = 0;
    while (nbr != 2) {
      boolean correct = false;
      while (!correct) {
        System.out.println("Choix de la matière première à ajouter:");
        System.out.println("A: ARGILE");
        System.out.println("B: BOIS");
        System.out.println("C: CHAMPS");
        System.out.println("D: MOUTON");
        System.out.println("E: PIERRE");

        String choix;

        if (j.estHumain) {
          choix = sc.next();
        } else {
          Random rand = new Random();
          int n = rand.nextInt(5) + 1;
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
            default:
              choix = "E";
              break;
          }
        }

        correct = true;
        switch (choix) {
          case "A":
            j.ajouterRessource("ARGILE");
            break;
          case "B":
            j.ajouterRessource("BOIS");
            break;
          case "C":
            j.ajouterRessource("CHAMPS");
            break;
          case "D":
            j.ajouterRessource("MOUTON");
            break;
          case "E":
            j.ajouterRessource("PIERRE");
            break;
          default:
            System.out.println("Choix incorrect");
            correct = false;
            break;
        }
        if (correct) {
          nbr++;
        }
      }
    }
  }

  public void progresMonopole(Scanner sc) {
    boolean correct = false;
    String ress = "";
    while (!correct) {
      System.out.println("Choix de la matière première designé:");
      System.out.println("A: ARGILE");
      System.out.println("B: BOIS");
      System.out.println("C: CHAMPS");
      System.out.println("D: MOUTON");
      System.out.println("E: PIERRE");

      String choix;

      if (j.estHumain) {
        choix = sc.next();
      } else {
        Random rand = new Random();
        int n = rand.nextInt(5) + 1;
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
          default:
            choix = "E";
            break;
        }
      }

      correct = true;
      switch (choix) {
        case "A":
          ress = "ARGILE";
          break;
        case "B":
          ress = "BOIS";
          break;
        case "C":
          ress = "CHAMPS";
          break;
        case "D":
          ress = "MOUTON";
          break;
        case "E":
          ress = "PIERRE";
          break;
        default:
          System.out.println("Choix incorrect");
          correct = false;
          break;
      }
    }
    int nbr = 0;
    for (Joueur joueur : tabJ) {
      if (joueur != j) {
        while (joueur.combienRessource(ress) != 0) {
          joueur.enleverRessource(ress);
          nbr++;
        }
      }
    }
    for (int i = 0; i < nbr; i++) {
      j.ajouterRessource(ress);
    }
  }
}
