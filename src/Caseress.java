public class Caseress extends Case {
    private boolean contientVoleur;

    public Caseress(int x, int y, boolean contientVoleur) {
        super(x, y);
        this.contientVoleur = contientVoleur;
    }

    public Caseress(int x, int y) {
        this(x, y, true);
    }

    public void setContientVoleur(boolean contientVoleur) {
        this.contientVoleur = contientVoleur;
    }

    public boolean getContientVoleur(){
        return contientVoleur ;
    }

}
