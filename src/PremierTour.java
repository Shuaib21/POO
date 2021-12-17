public class PremierTour extends Tour {
    private int x;
    private int y;

    public PremierTour(Joueur j, Plateau p) {
        super(j, p);
    }

    public void ajouterColonie() {
        boolean ajouter = false;
        while (!ajouter) {
            x = getCoordonnee('x');
            y = getCoordonnee('y');
            if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getestVide()) {
                p.selctionnerCaseColonie(x, y).mettreColonie(j);
                ajouter = true;
            } else {
                System.out.println("Case non-vide");
            }
        }
    }

    public void ajouterRoute() {
        boolean ajouter = false;
        while (!ajouter) {
            int x = getCoordonnee('x');
            int y = getCoordonnee('y');
            if (correcte(x, y)) {
                if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getestVide()) {
                    p.selctionnerCaseRoute(x, y).mettreRoute(j);
                    ajouter = true;
                } else {
                    System.out.println("Case non-vide");
                }
            } else {
                System.out.println("Case non-valide");
            }
        }
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

    private void MAJNordOuest() {
        if (x - 1 >= 0 && y - 1 >= 0) {
            j.ajouterRessource(p.selctionnerCaseRess(x-1, y-1).ressource);
        }
    }

    private void MAJNordEst() {
        if (x + 1 < p.getTaille() && y - 1 >= 0) {
            j.ajouterRessource(p.selctionnerCaseRess(x+1, y-1).ressource);
        }
    }

    private void MAJSudOuest() {
        if (x - 1 >= 0 && y + 1 < p.getTaille()) {
            j.ajouterRessource(p.selctionnerCaseRess(x-1, y+1).ressource);
        }
    }

    private void MAJSudEst() {
        if (x + 1 < p.getTaille() && y + 1 < p.getTaille()) {
            j.ajouterRessource(p.selctionnerCaseRess(x+1, y+1).ressource);
        }
    }

}
