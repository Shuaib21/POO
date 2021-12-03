public class Partie {
    private final Joueur[] tabJ;
    private Plateau p;

    Partie(Joueur[] j, Plateau p) {
        tabJ = j;
        this.p = p;
    }

    public void jouerPartie() {
        init();
        while (!partiefini()) {
            for (Joueur j : tabJ) {
                TourNormal t = new TourNormal(j, p, tabJ);
                t.ajouterColonie();
                t.ajouterRoute();
            }
        }
    }

    private void init() {
        for (Joueur j : tabJ) {
            PremierTour t = new PremierTour(j, p);
            t.ajouterColonie();
            t.ajouterRoute();
            t.toucherRessource();
        }
        for (int i = tabJ.length - 1; i >= 0; i--) {
            Joueur j = tabJ[i];
            PremierTour t = new PremierTour(j, p);
            t.ajouterColonie();
            t.ajouterRoute();
        }
    }

    private boolean partiefini() {
        for (Joueur a : tabJ) {
            if (a.getPoint() >= 10) {
                return true;
            }
        }
        return false;
    }
}
