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
        System.out.println("Tour de " + j.getPseudo());
        v.getT().getJouerRoute().setEnabled(false);
        v.getT().getJouerColonie().setEnabled(false);
        v.getT().getCreerVille().setEnabled(false);
        v.getT().getAcheterCarteDev().setEnabled(false);
        v.getT().getJouerCarteDev().setEnabled(false);
        v.getT().getEchangerAvecPort().setEnabled(false);
        v.getT().getTerminerTour().setEnabled(false);
        for (int i = 0; i < 9; i++) {
            for (int a = 0; a < 9; a++) {
                v.getT().getTab(i, a).setEnabled(false);
            }
        }
        for (int i = 0; i < 9; i = i + 2) {
            for (int a = 0; a < 9; a = a + 2) {
                if (p.selctionnerCaseColonie(i, a).getEstVide() && estColleARouteVide(i, a)) {
                    v.getT().getTab(i, a).setEnabled(true);
                }
            }
        }
        v.incorrect = true;
        v.aide.setText("Veuillez selectionner \nla case ou vous voulez \nmettre votre colonie");
    }

    public void ajouterColonie(Scanner sc) {
        boolean ajouter = false;
        CaseColonie colStrat = obtenirColonieStrategie();
        while (!ajouter) {
            if (j.estHumain) {
                x = getCoordonnee('x', sc);
                y = getCoordonnee('y', sc);
            } else {
                if (colStrat != null) {
                    colStrat.mettreColonie(j);
                    j.ajouterUneColonie();
                    x = colStrat.getX();
                    y = colStrat.getY();
                    ajouter = true;
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
                } else {
                    Random rand = new Random();
                    x = rand.nextInt(p.getTaille());
                    y = rand.nextInt(p.getTaille());
                }
            }
            if (!ajouter) {
                if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()
                        && estColleARouteVide(x, y)) {
                    p.selctionnerCaseColonie(x, y).mettreColonie(j);
                    j.ajouterUneColonie();
                    ajouter = true;
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
                } else if (j.estHumain) {
                    System.out.println("Case non-vide");
                }
            }
        }
    }

    public void ajouterColonie(int X, int Y) {
        int x = X;
        int y = Y;
        if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()
                && estColleARouteVide(x, y)) {
            p.selctionnerCaseColonie(x, y).mettreColonie(j);
            mettreColonieInter(x, y);
            j.ajouterUneColonie();
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
        } else {
            v.aide.setText("Votre colonie ne peut pas être ajoutée ici.");
        }
    }

    public void ajouterColonie() {
        boolean ajouter = false;
        CaseColonie colStrat = obtenirColonieStrategie();
        while (!ajouter) {
            if (colStrat != null) {
                colStrat.mettreColonie(j);
                j.ajouterUneColonie();
                x = colStrat.getX();
                y = colStrat.getY();
                mettreColonieInter(x, y);
                ajouter = true;
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
            } else {
                Random rand = new Random();
                x = rand.nextInt(p.getTaille());
                y = rand.nextInt(p.getTaille());
            }
            if (!ajouter) {
                if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getEstVide()
                        && estColleARouteVide(x, y)) {
                    p.selctionnerCaseColonie(x, y).mettreColonie(j);
                    mettreColonieInter(x, y);
                    j.ajouterUneColonie();
                    ajouter = true;
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

    private boolean estColleARouteVide(int x, int y) {
        if (x + 1 < p.getTaille() &&
                p.selctionnerCaseRoute(x + 1, y).getEstVide()) {
            return true;
        }
        if (x - 1 >= 0 &&
                p.selctionnerCaseRoute(x - 1, y).getEstVide()) {
            return true;
        }
        if (y + 1 < p.getTaille() &&
                p.selctionnerCaseRoute(x, y + 1).getEstVide()) {
            return true;
        }
        if (y - 1 >= 0 &&
                p.selctionnerCaseRoute(x, y - 1).getEstVide()) {
            return true;
        }
        return false;
    }

    private CaseColonie obtenirColonieStrategie() {
        for (int a = 1; a < p.getTaille(); a += 2) {
            for (int b = 1; b < p.getTaille(); b += 2) {
                if (p.selctionnerCaseRess(a, b) != null) {
                    CaseRessource cr = p.selctionnerCaseRess(a, b);
                    if (cr.num == 5 || cr.num == 6 || cr.num == 8 || cr.num == 9) { // Chiffres sortant fréquemment
                                                                                    // aux dés: 5, 6, 8 ou 9
                        if (p.selctionnerCaseColonie(a - 1, b - 1).getEstVide() && estColleARouteVide(a - 1, b - 1)) {
                            return p.selctionnerCaseColonie(a - 1, b - 1);
                        } else if (p.selctionnerCaseColonie(a - 1, b + 1).getEstVide()
                                && estColleARouteVide(a - 1, b + 1)) {
                            return p.selctionnerCaseColonie(a - 1, b + 1);
                        } else if (p.selctionnerCaseColonie(a + 1, b - 1).getEstVide()
                                && estColleARouteVide(a + 1, b - 1)) {
                            return p.selctionnerCaseColonie(a + 1, b - 1);
                        } else if (p.selctionnerCaseColonie(a + 1, b + 1).getEstVide()
                                && estColleARouteVide(a + 1, b + 1)) {
                            return p.selctionnerCaseColonie(a + 1, b + 1);
                        }
                    }
                }
            }
        }
        return null;
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
        if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getEstVide()) {
            p.selctionnerCaseRoute(x, y).mettreRoute(j);
            mettreRouteInter(x, y);
        }
    }

    public void ajouterRoute() {
        boolean ajouter = false;
        while (!ajouter) {
            Random rand = new Random();
            int x = rand.nextInt(p.getTaille());
            int y = rand.nextInt(p.getTaille());

            if (correcte(x, y)) {
                if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getEstVide()) {
                    p.selctionnerCaseRoute(x, y).mettreRoute(j);
                    mettreRouteInter(x, y);
                    ajouter = true;
                }
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

    public void toucherRessource(int x, int y) {
        if (x - 1 >= 0 && y - 1 >= 0 && p.selctionnerCaseRess(x - 1, y - 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x - 1, y - 1).ressource);
        }
        if (x + 1 < p.getTaille() && y - 1 >= 0 && p.selctionnerCaseRess(x + 1, y - 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x + 1, y - 1).ressource);
        }

        if (x - 1 >= 0 && y + 1 < p.getTaille() && p.selctionnerCaseRess(x - 1, y + 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x - 1, y + 1).ressource);
        }
        if (x + 1 < p.getTaille() && y + 1 < p.getTaille() && p.selctionnerCaseRess(x + 1, y + 1) != null) {
            j.ajouterRessource(p.selctionnerCaseRess(x + 1, y + 1).ressource);
        }
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
