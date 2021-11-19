import java.util.ArrayList;

public class Joueur {
    private ArrayList<Carte> main;
    private String couleur;
    private int point;
    private boolean estHumain;
    private String pseudo ;

    Joueur(String couleur) {
        main = new ArrayList<Carte>();
        this.couleur = couleur;
        point = 0;
        estHumain = true;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getPoint() {
        return point;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String enCouleur(String a){
        
    }

}
