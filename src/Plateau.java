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

    public void afficher() {
        if (plateau.length == 9) {
            System.out.println("    B:1             3:1             M:1"); // pour 4*4
        }
        for (int i = 0; i < plateau.length; i++) {
            if (plateau.length == 9) {
                if (i == 4) {
                    System.out.print("3:1 ");
                } else {
                    System.out.print("    ");
                }
            }
            for (int j = 0; j < plateau[i].length; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            if (plateau.length == 9) { // pour 4*4
                if (i == 4) {
                    System.out.println(" C:1");
                } else {
                    System.out.println();
                }
            }
        }
        if (plateau.length == 9) {
            System.out.println("    P:1             A:1             3:1");
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
        // ajout des ports
        if (z == 4) { // pour 4*4
            this.selctionnerCaseColonie(0, 0).setPort("2B:1");
            this.selctionnerCaseColonie(0, 4).setPort("3:1");
            this.selctionnerCaseColonie(0, 8).setPort("2M:1");
            this.selctionnerCaseColonie(4, 0).setPort("2C:1");
            this.selctionnerCaseColonie(4, 8).setPort("3:1");
            this.selctionnerCaseColonie(8, 0).setPort("2A:1");
            this.selctionnerCaseColonie(8, 4).setPort("2P:1");
            this.selctionnerCaseColonie(8, 8).setPort("3:1");
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

    public CasePaysage selctionnerCasePaysage(int x, int y) {
        if (plateau[x][y] instanceof CasePaysage) {
            return (CasePaysage) plateau[x][y];
        }
        return null;
    }

}
