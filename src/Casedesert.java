public class Casedesert extends CasePaysage {

    public Casedesert(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        if (getContientVoleur()) {
            return ConsoleColors.RED_BACKGROUND_BRIGHT+ " d " + ConsoleColors.RESET;
        }
        return " d ";
    }
}
