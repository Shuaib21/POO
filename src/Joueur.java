import java.io.Console;
import java.util.ArrayList;

public class Joueur {
    private ArrayList<CarteDev> mainDev;
    private ArrayList<CarteRess> mainRess;
    private String couleur;
    private int point;
    private boolean estHumain;
    private String pseudo;

    public ArrayList<CarteDev> getMainDev() {
        return mainDev;
    }

    public ArrayList<CarteRess> getMainRess() {
        return mainRess;
    }

    Joueur(String couleur) {
        mainDev = new ArrayList<CarteDev>();
        mainRess = new ArrayList<CarteRess>();
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

    public String enCouleur(String a) {
        switch (couleur) {
        case "RED":
            return ConsoleColors.RED + " + a + " + ConsoleColors.RESET;
        case "GREEN":
            return ConsoleColors.GREEN + a + ConsoleColors.RESET;
        case "YELLOW":
            return ConsoleColors.YELLOW + a + ConsoleColors.RESET;
        case "BLUE":
            return ConsoleColors.BLUE + a + ConsoleColors.RESET;
        case "PURPLE":
            return ConsoleColors.PURPLE + a + ConsoleColors.RESET;
        case "CYAN":
            return ConsoleColors.CYAN + a + ConsoleColors.RESET;
        case "WHITE":
            return ConsoleColors.WHITE + a + ConsoleColors.RESET;
        }
        return a;
    }

}
