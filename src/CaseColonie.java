public class CaseColonie extends Case {
    private boolean estVide;
    private Joueur j;
    private boolean estVille;

    @Override
    public String toString() {
        if (estVille) {
            return j.enCouleur(" v ");
        }
        return estVide ? "   " : j.enCouleur(" c ");
    }

    CaseColonie(int x, int y) {
        super(x, y);
        estVide = true;
        j = null;
        estVille = false;
    }

    public void mettreColonie(Joueur j) {
        estVide = false;
        this.j = j;
    }

    public Joueur getJ() {
        return j;
    }

    public boolean getestVide() {
        return estVide;
    }

    public void transformerenVille() {
        if (estVide) {
            System.out.println("Il n'y a pas de colonie");
        } else {
            estVille = true;
        }
    }

}
