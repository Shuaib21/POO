public class CaseRessource extends CasePaysage {
    public final int num;
    public final char ressource;

    CaseRessource(int x, int y, int num, char ressource) {
        super(x, y, false);
        this.num = num;
        this.ressource = ressource;
    }

    @Override
    public String toString() {
        if (super.getContientVoleur()) {
            return ConsoleColors.RED_BACKGROUND_BRIGHT+ ((num > 9) ? "" : " ") + num + ressource+ ConsoleColors.RESET;
        } else {
            return ((num > 9) ? "" : " ")+ num + ressource;
        }
    }
}
