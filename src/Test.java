import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Integer> a = new ArrayList<Integer>() ;
        a.add(1) ;
        a.add(2) ;
        ArrayList<Integer> b = new ArrayList<Integer>() ;
        b.add(2) ;
        b.add(1) ;
        System.out.println(a.containsAll(b)) ;
    }
}
