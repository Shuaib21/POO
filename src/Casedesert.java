public class Casedesert extends Caseress {

    public Casedesert(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        if(getContientVoleur()){
            return " dX" ;
        }
        return " d " ;
    }
}
