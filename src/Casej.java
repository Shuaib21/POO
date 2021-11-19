public class Casej extends Caseress {
    public final int num;
    public final char ressource;

    Casej(int x, int y, int num, char ressource) {
        super(x, y, false);
        this.num = num;
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        if (super.getContientVoleur()) {
            return "X" + ((num > 9) ? "" : " ") + num + ressource;
        } else {
            return "" + num + ressource;
        }
    }
}
