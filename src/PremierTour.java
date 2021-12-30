import java.util.Random;
import java.util.Scanner;

public class PremierTour extends Tour {
    private int x;
    private int y;

    public PremierTour(Joueur j, Plateau p) {
        super(j, p);
    }

    public PremierTour(Joueur j, Plateau p, VueCatan v) {
        super(j, p, v);
        v.getT().getJouerRoute().setEnabled(false);
        v.getT().getJouerColonie().setEnabled(false);
        v.getT().getCreerVille().setEnabled(false);
        v.getT().getAcheterCarteDev().setEnabled(false);
        v.getT().getJouerCarteDev().setEnabled(false);
        v.getT().getEchangerAvecPort().setEnabled(false);
        v.getT().getTerminerTour().setEnabled(false);
        for (int i = 0; i < 9; i++) {
            for (int a = 0; a < 9; a++) {
                if (p.selctionnerCasePaysage(i, a) == null) {
                v.getT().getTab(i, a).setEnabled(false);
                }
            }
        }
        for (int i = 0; i < 9; i = i + 2) {
            for (int a = 0; a < 9; a = a + 2) {
                if (p.selctionnerCaseColonie(i, a).getEstVide()) {
                    v.getT().getTab(i, a).setEnabled(true);
                }
            }
        }
        v.incorrect = true;
        v.aide.setText("Veuillez selectionner la case ou vous voulez mettre votre colonie");
    }

    public void ajouterColonie(Scanner sc) {
        boolean ajouter = false;
        while (!ajouter) {
            if (j.estHumain) {
                x = getCoordonnee('x', sc);
                y = getCoordonnee('y', sc);
            } else {
                Random rand = new Random();
                x = rand.nextInt(p.getTaille());
                y = rand.nextInt(p.getTaille());
            }
            if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()) {
                p.selctionnerCaseColonie(x, y).mettreColonie(j);
                ajouter = true;
            } else if (j.estHumain) {
                System.out.println("Case non-vide");
            }
        }
    }

    public void ajouterColonie(int X, int Y) {
        int x = X;
        int y = Y;
        if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()) {
            p.selctionnerCaseColonie(x, y).mettreColonie(j);
        } else {
            v.aide.setText("Votre colonie ne peut pas être ajoutée ici.");
        }
    }

    public void ajouterRoute(Scanner sc) {
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
            if (correcte(x, y)) {
                if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getEstVide()) {
                    p.selctionnerCaseRoute(x, y).mettreRoute(j);
                    ajouter = true;
                } else if (j.estHumain) {
                    System.out.println("Case non-vide");
                }
            } else if (j.estHumain) {
                System.out.println("Case non-valide");
            }
        }
    }

    public void ajouterRoute(int x, int y) {
        if (correcte(x, y)) {
            if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getEstVide()) {
                p.selctionnerCaseRoute(x, y).mettreRoute(j);
            }
        }
    }

    public boolean correcte(int x, int y) {
        if (x == this.x && Math.abs(y - this.y) == 1) {
            return true;
        }
        if (y == this.y && Math.abs(x - this.x) == 1) {
            return true;
        }
        return false;
    }

    public void toucherRessource() {
        MAJNordEst();
        MAJNordOuest();
        MAJSudEst();
        MAJSudOuest();
    }

    // p.selctionnerCaseRess(a, b) est null si c'est le désert

    private void MAJNordOuest() {
        if (x - 1 >= 0 && y - 1 >= 0 && p.selctionnerCaseRess(x - 1, y - 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x - 1, y - 1).ressource);
        }
    }

    private void MAJNordEst() {
        if (x + 1 < p.getTaille() && y - 1 >= 0 && p.selctionnerCaseRess(x + 1, y - 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x + 1, y - 1).ressource);
        }
    }

    private void MAJSudOuest() {
        if (x - 1 >= 0 && y + 1 < p.getTaille() && p.selctionnerCaseRess(x - 1, y + 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x - 1, y + 1).ressource);
        }
    }

    private void MAJSudEst() {
        if (x + 1 < p.getTaille() && y + 1 < p.getTaille() && p.selctionnerCaseRess(x + 1, y + 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x + 1, y + 1).ressource);
        }
    }

}
