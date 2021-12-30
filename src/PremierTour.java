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

    public boolean ajouterColonie() {
        boolean ajouter = false;
        if (j.estHumain) {
            x = v.X;
            y = v.Y;
        } else {
            Random rand = new Random();
            x = rand.nextInt(p.getTaille());
            y = rand.nextInt(p.getTaille());
        }
        if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()) {
            p.selctionnerCaseColonie(x, y).mettreColonie(j);
            ajouter = true;
        }else{
            v.erreur.setText("Votre colonie ne peut pas être ajoutée ici.");
        }
        return ajouter;
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

    public boolean ajouterRoute() {
        boolean ajouter = false;
        while (!ajouter) {
            int x;
            int y;
            if (j.estHumain) {
                x = v.X;
                y = v.Y;
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
                    v.erreur.setText("Votre route ne peut pas être ajoutée ici.");
                }
            } else if (j.estHumain) {
                v.erreur.setText("Votre route ne peut pas être ajoutée ici.");
            }
        }
        return ajouter;
    }

    private boolean correcte(int x, int y) {
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
