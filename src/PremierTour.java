import java.util.Scanner;

public class PremierTour {
    private Joueur j;
    private Plateau p;
    private int x;
    private int y;

    public PremierTour(Joueur j, Plateau p) {
        this.j = j;
        this.p = p;
    }

    public void ajouterColonie() {
        boolean ajouter = false;
        while (!ajouter) {
            x = getCoordonnee('x');
            y = getCoordonnee('y');
            if (p.selctionnerCaseColonie(x, y) != null && p.selctionnerCaseColonie(x, y).getestVide()) {
                p.selctionnerCaseColonie(x, y).mettreColonie(j);
                ajouter = true;
            } else {
                System.out.println("Case non-vide");
            }
        }
    }

    public void ajouterRoute() {
        boolean ajouter = false;
        while (!ajouter) {
            int x = getCoordonnee('x');
            int y = getCoordonnee('y');
            if (correcte(x, y)) {
                if (p.selctionnerCaseRoute(x, y) != null && p.selctionnerCaseRoute(x, y).getestVide()) {
                    p.selctionnerCaseRoute(x, y).mettreRoute(j);
                    ajouter = true;
                } else {
                    System.out.println("Case non-vide");
                }
            }else{
                System.out.println("Case non-valide");
            }
        }
    }

    private boolean correcte(int x, int y) {
        if (x == this.x && Math.abs(y - this.y) == 1) {
            return true;
        }
        if (y == this.y && Math.abs(x - this.x) == 1) {
            return true;
        }
        return false;
    }

    public int getCoordonnee(char c) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez entrer la coordonée " + c);
        int coord = sc.nextInt() - 1;
        while (coord < 0 || coord >= p.getTaille()) {
            System.out.println("Coordonnée incorrecte:\nVeuillez entrer une coordonnée entre 1 et " + p.getTaille());
            coord = sc.nextInt();
        }
        sc.close();
        return coord;
    }
}
