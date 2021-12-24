public class CaseColonie extends Case {
    private boolean estVide;
    private Joueur j;
    private boolean estVille;
    private String port;

    // 3
    // 2B
    // 2A
    // 2M
    // 2P
    // 2C

    public String getPort() {
        return port;
    }

    public void setPort(String p) {
        port = p;
    }

    public boolean estPort() {
        return (!port.equals(""));
    }

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
        port = "";
    }

    public void mettreColonie(Joueur j) {
        estVide = false;
        this.j = j;
    }

    public Joueur getJ() {
        return j;
    }

    public boolean getEstVide() {
        return estVide;
    }

    public boolean getEstVille() {
        return estVille;
    }

    public void transformerEnVille() {
        if (estVide) {
            System.out.println("Il n'y a pas de colonie");
        } else {
            estVille = true;
        }
    }

}
