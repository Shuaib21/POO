import java.util.Scanner;

public abstract class Tour {
    protected Joueur j;
    protected Plateau p;

    public Tour(Joueur j, Plateau p) {
        this.j = j;
        this.p = p;
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

    public abstract void ajouterRoute();

    public abstract void ajouterColonie();
}
