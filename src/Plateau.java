import java.util.Random;

public class Plateau {
    private Case[][] plateau;

    public Case[][] getPlateau() {
        return plateau;
    }

    Plateau(int a) {
        plateau = new Case[a * 2 + 1][a * 2 + 1];
        initialiserplateau(plateau, a);
    }

    private void afficher() {
        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void initialiserplateau(Case[][] p, int z) {
        int[] a = new int[z * z];
        // {b,f,c,p,m}
        boolean good = true;
        String w = "";
        int n;
        int n2;
        int[] b = { 2, 3, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 12 }; // si 4*4
        for (int i = 1; i < p.length - 1; i = i + 2) {
            for (int j = 1; j < p[i].length - 1; j = j + 2) {
                good = true;
                n = 0;
                while (good) {
                    Random r = new Random();
                    n = r.nextInt(z * z);
                    if (a[n] == 0) {
                        a[n] = a[n] + 1;
                        good = false;
                    }
                }
                if (n < 3) { // pour 4*4
                    w = "BOIS";
                } else if (n < 6) {
                    w = "ARGILE";
                } else if (n < 9) {
                    w = "MOUTON";
                } else if (n < 12) {
                    w = "PIERRE";
                } else if (n < 15) {
                    w = "CHAMPS";
                } else {
                    p[i][j] = new Casedesert(i, j);
                    // break;
                }
                if (n != 15) {
                    // pour 4*4
                    good = true;
                    n2 = 0;
                    int jx = 0;
                    while (good) {
                        Random r2 = new Random();
                        n2 = r2.nextInt(z * z - 1);
                        if (b[n2] != 0) {
                            jx = b[n2];
                            b[n2] = 0;
                            good = false;
                        }
                    }
                    p[i][j] = new CaseRessource(i, j, jx, w);
                }
            }
        }
        for (int i = 0; i < p.length; i = i + 2) {
            for (int j = 0; j < p[i].length; j = j + 2) {
                p[i][j] = new CaseColonie(i, j);
            }
        }
        for (int i = 0; i < p.length; i = i + 2) {
            for (int j = 1; j < p[i].length; j = j + 2) {
                p[i][j] = new CaseRoute(i, j);
            }
        }
        for (int i = 1; i < p.length; i = i + 2) {
            for (int j = 0; j < p[i].length; j = j + 2) {
                p[i][j] = new CaseRoute(i, j);
            }
        }

        



    }

    public int getTaille() {
        return plateau.length;
    }

    public CaseColonie selctionnerCaseColonie(int x, int y) {
        if (plateau[x][y] instanceof CaseColonie) {
            return (CaseColonie) plateau[x][y];
        }
        return null;
    }

    public CaseRoute selctionnerCaseRoute(int x, int y) {
        if (plateau[x][y] instanceof CaseRoute) {
            return (CaseRoute) plateau[x][y];
        }
        return null;
    }

    public CaseRessource selctionnerCaseRess(int x, int y) {
        if (plateau[x][y] instanceof CaseRessource) {
            return (CaseRessource) plateau[x][y];
        }
        return null;
    }

    public static void main(String[] args) {
        Plateau p = new Plateau(4);
        p.afficher();

    }

}
