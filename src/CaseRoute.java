public class CaseRoute extends Case {
    private boolean estVide;
    private Joueur j;

    @Override
    public String toString() {
        return estVide ? "   " : j.enCouleur(" r ");
    }

    CaseRoute(int x, int y) {
        super(x, y);
        estVide = true;
        j = null;
    }

    public void mettreRoute(Joueur j) {
        estVide = false;
        this.j = j;
    }

    public Joueur getJ() {
        return j;
    }

    public boolean getestVide() {
        return estVide;
    }

}
