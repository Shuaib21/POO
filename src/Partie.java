public abstract class Partie {
    private final Joueur[] j;
    private Plateau  p;


    Partie(Joueur[] j, Plateau p){
        this.j=j ;
        this.p = p ;
    }

    private boolean partiefini() {
        for (Joueur a : j) {
            if (a.getPoint() >= 10) {
                return true;
            }
        }
        return false;
    }














    private void ajouterRoute(Joueur m, int x, int y) {
        if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getestVide()) {
            p.selctionnerCaseRoute(x, y).mettreRoute(m);
        } else {
            System.out.println("erreur de selection ");
        }
    }

    private void ajouterColonie(Joueur m, int x, int y) {
        if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getestVide()) {
            p.selctionnerCaseColonie(x, y).mettreColonie(m);
        } else {
            System.out.println("erreur de selection ");
        }
    }

    private void jouerRoute() {

    }

    private void jouerColonier() {

    }

}
