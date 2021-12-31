import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.ColorUIResource;

public class VueCatan extends JFrame {
    protected ModeleCatan model;
    private ImagePane imagePane;
    private JPanel menu;
    private JButton start;
    private Joueur[] tabJ;
    private creerJoueur[] joueurs = new creerJoueur[4];
    private Partie p;
    private Table t;
    int X;
    int Y;
    boolean incorrect = false;
    JTextArea aide = new JTextArea();
    boolean premierTour;
    boolean ajouterColonie;
    boolean ajouterRoute;

    public Table getT() {
        return t;
    }

    public void setX(int x) {
        X = x;
    }

    public void setY(int y) {
        Y = y;
    }

    public VueCatan() {
        setTitle("Catan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new ModeleCatan("./Image/Ocean.jpeg");
        imagePane = new ImagePane();
        setContentPane(imagePane);

        menu = new JPanel();
        menu.setLayout(new GridLayout(5, 1));

        this.add(menu, BorderLayout.CENTER);
        this.setLocationRelativeTo(null);

        for (int i = 0; i < 3; i++) {
            joueurs[i] = new creerJoueur(i);
            menu.add(joueurs[i]);
        }

        JMenuBar menuBar = new JMenuBar();
        JButton plus = new JButton("+");
        JButton moins = new JButton("-");
        start = new JButton("START");

        moins.setEnabled(false);

        menuBar.add(plus);
        menuBar.add(moins);
        menuBar.add(start);

        menu.add(menuBar);

        plus.addActionListener(
                (ActionEvent e) -> {
                    menu.remove(menuBar);
                    joueurs[3] = new creerJoueur(3);
                    menu.add(joueurs[3]);
                    menu.add(menuBar);
                    plus.setEnabled(false);
                    moins.setEnabled(true);
                    this.validate();
                    this.repaint();
                });

        moins.addActionListener(
                (ActionEvent e) -> {
                    menu.remove(menuBar);
                    menu.remove(joueurs[3]);
                    joueurs[3] = null;
                    menu.add(menuBar);
                    plus.setEnabled(true);
                    moins.setEnabled(false);
                    this.validate();
                    this.repaint();
                });

        menu.addMouseMotionListener(new Selection());

        start.setEnabled(false);

        start.addActionListener((ActionEvent e) -> {
            if (joueurs[3] != null) {
                tabJ = new Joueur[4];
                tabJ[3] = new Joueur(joueurs[3].couleur, joueurs[3].nom, joueurs[3].humain);
            } else {
                tabJ = new Joueur[3];
            }
            for (int i = 0; i < 3; i++) {
                tabJ[i] = new Joueur(joueurs[i].couleur, joueurs[i].nom, joueurs[i].humain);
            }
            p = new Partie(tabJ, this);
            this.remove(menu);
            t = new Table();
            this.add(t);
            this.validate();
            this.repaint();
            p.jouerPartieInter(this);
        });

    }

    public class Selection implements MouseInputListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if (joueurs[0].nom == null || joueurs[1].nom == null || joueurs[2].nom == null) {
                start.setEnabled(false);
            } else {
                start.setEnabled(true);
            }
        }
    }

    private class creerJoueur extends JPanel {
        private boolean humain = true;
        private String nom;
        private String couleur;

        public creerJoueur(int n) {
            couleur = getCouleur(n + 1);

            setLayout(new BorderLayout());
            JTextField pseudo; // declare a field
            pseudo = new JTextField(10); // create field approx 10 columns wide.
            add(pseudo, BorderLayout.CENTER); // add it to a JPanel

            JLabel l = new JLabel();
            l.setText("Joueur " + String.valueOf(n + 1) + " : ");
            add(l, BorderLayout.WEST);

            JButton ordi = new JButton("HUMAIN");
            add(ordi, BorderLayout.EAST);
            ordi.setEnabled(false);

            pseudo.addFocusListener(
                    new FocusListener() {
                        public void focusGained(FocusEvent e) {
                            ordi.setEnabled(true);
                        }

                        public void focusLost(FocusEvent e) {
                            nom = pseudo.getText().toString();
                            ordi.setEnabled(true);
                        }
                    });

            ordi.addActionListener(
                    (ActionEvent e) -> {
                        if (humain) {
                            ordi.setText("IA");
                            humain = false;
                        } else {
                            ordi.setText("HUMAIN");
                            humain = true;
                        }
                        VueCatan.this.validate();
                        VueCatan.this.repaint();
                    });
        }

        private String getCouleur(int i) {
            switch (i) {
                case 1:
                    return "RED";
                case 2:
                    return "GREEN";
                case 3:
                    return "YELLOW";
                default:
                    return "BLUE";
            }
        }
    }

    private class ModeleCatan {
        private BufferedImage image;

        public ModeleCatan(String chemin) {
            try {
                image = ImageIO.read(new File(chemin));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public BufferedImage getImage() {
            return image;
        }

    }

    private class ImagePane extends JPanel {

        public ImagePane() {
            setPreferredSize(
                    new Dimension(model.getImage().getWidth(), model.getImage().getHeight()));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(model.getImage(), 0, 0, this);
        }

    }

    class Table extends JPanel { // **************************************************************************************
        private JPanel plateau;
        private JPanel commande;

        private JButton jouerColonie;
        private JButton jouerRoute;
        private JButton creerVille;
        private JButton acheterCarteDev;
        private JButton jouerCarteDev;
        private JButton echangerAvecPort;
        private JButton terminerTour;

        private JPanel joueurHaut;
        private JPanel joueurBas;
        private JPanel[] J = new JPanel[4];
        private ButtonInter[][] tab;

        private JLabel[][] nbrRess;

        private JPanel info;

        private JPanel commandeJouerCarteDev;

        private JButton jouerChevalier;
        private JButton jouerCarteConstru;
        private JButton jouerCarteDecouverte;
        private JButton jouerMonopole;

        final ImageIcon bois = new ImageIcon("./Image/Bois.jpeg");
        final ImageIcon champs = new ImageIcon("./Image/Champs.jpg");
        final ImageIcon montagne = new ImageIcon("./Image/Montagne.jpeg");
        final ImageIcon moutons = new ImageIcon("./Image/Moutons.jpeg");
        final ImageIcon mine = new ImageIcon("./Image/Mine.jpeg");
        final ImageIcon desert = new ImageIcon("./Image/Desert.jpeg");

        Table() {
            this.setLayout(new BorderLayout());

            plateau = new JPanel();
            plateau.setLayout(new GridLayout(11, 11));
            this.add(plateau, BorderLayout.CENTER);

            commande = new JPanel();
            this.add(commande, BorderLayout.EAST);

            info = new JPanel();
            info.setLayout(new GridLayout(3, 1));
            info.add(aide);
            this.add(info, BorderLayout.WEST);
            commandeJouerCarteDev.setLayout(new GridLayout(2,2));

            commande.setLayout(new GridLayout(7, 1));

            jouerColonie = new JButton("Ajouter une colonie");
            jouerRoute = new JButton("Ajouter une route");
            creerVille = new JButton("Ajouter une ville");
            acheterCarteDev = new JButton("Acheter une carte développement");
            jouerCarteDev = new JButton("Jouer une carte développement");
            echangerAvecPort = new JButton("Echanger avec les ports");
            terminerTour = new JButton("Terminer tour");

            commande.add(jouerColonie);
            commande.add(jouerRoute);
            commande.add(creerVille);
            commande.add(acheterCarteDev);
            commande.add(jouerCarteDev);
            commande.add(echangerAvecPort);
            commande.add(terminerTour);

            jouerColonie.setEnabled(false);
            jouerRoute.setEnabled(false);
            creerVille.setEnabled(false);
            acheterCarteDev.setEnabled(false);
            jouerCarteDev.setEnabled(false);
            echangerAvecPort.setEnabled(false);
            terminerTour.setEnabled(false);

            joueurBas = new JPanel();
            joueurHaut = new JPanel();
            joueurBas.setLayout(new GridLayout());
            joueurHaut.setLayout(new GridLayout(1, 2));
            joueurHaut.setLayout(new GridLayout(1, 2));

            for (int i = 0; i < 4; i++) {
                J[i] = new JPanel();
                J[i].setLayout(new GridLayout(2, 5));
            }

            joueurHaut.add(J[0]);
            joueurHaut.add(J[1]);
            joueurBas.add(J[2]);
            joueurBas.add(J[3]);

            this.add(joueurHaut, BorderLayout.NORTH);
            this.add(joueurBas, BorderLayout.SOUTH);

            nbrRess = new JLabel[tabJ.length][5];
            for (int i = 0; i < tabJ.length; i++) {
                nbrRess[i][0] = new JLabel(String.valueOf(tabJ[i].combienRessource("BOIS")));
                nbrRess[i][1] = new JLabel(String.valueOf(tabJ[i].combienRessource("CHAMPS")));
                nbrRess[i][2] = new JLabel(String.valueOf(tabJ[i].combienRessource("PIERRE")));
                nbrRess[i][3] = new JLabel(String.valueOf(tabJ[i].combienRessource("MOUTON")));
                nbrRess[i][4] = new JLabel(String.valueOf(tabJ[i].combienRessource("ARGILE")));
            }

            try {
                BufferedImage buche = ImageIO.read(new File("./Image/buche.png"));
                BufferedImage paille = ImageIO.read(new File("./Image/paille.png"));
                BufferedImage pierre = ImageIO.read(new File("./Image/pierre.png"));
                BufferedImage mouton = ImageIO.read(new File("./Image/mouton.png"));
                BufferedImage argile = ImageIO.read(new File("./Image/argile.png"));

                for (int i = 0; i < tabJ.length; i++) {
                    J[i].add(new JLabel(new ImageIcon(buche)));
                    J[i].add(new JLabel(new ImageIcon(paille)));
                    J[i].add(new JLabel(new ImageIcon(pierre)));
                    J[i].add(new JLabel(new ImageIcon(mouton)));
                    J[i].add(new JLabel(new ImageIcon(argile)));
                    for (int j = 0; j < 5; j++) {
                        nbrRess[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    }
                    J[i].add(nbrRess[i][0]);
                    J[i].add(nbrRess[i][1]);
                    J[i].add(nbrRess[i][2]);
                    J[i].add(nbrRess[i][3]);
                    J[i].add(nbrRess[i][4]);
                    switch (i) {
                        case 0:
                            J[i].setBorder(new LineBorder(Color.RED, 4, true));
                            break;
                        case 1:
                            J[i].setBorder(new LineBorder(Color.GREEN, 4, true));
                            break;
                        case 2:
                            J[i].setBorder(new LineBorder(Color.YELLOW, 4, true));
                            break;
                        default:
                            J[i].setBorder(new LineBorder(Color.BLUE, 4, true));
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (tabJ.length == 3) { // On rajoute des champs vides à J[3]
                J[3].add(new JLabel());
                J[3].add(new JLabel());
                J[3].add(new JLabel());
                J[3].add(new JLabel());
                J[3].add(new JLabel());
            }

            tab = new ButtonInter[9][9];

            ImageIcon portBois = new ImageIcon("./Image/PortBuche.jpeg");
            ImageIcon portChamps = new ImageIcon("./Image/PortPaille.jpeg");
            ImageIcon portPierre = new ImageIcon("./Image/PortPierre.jpeg");
            ImageIcon portMouton = new ImageIcon("./Image/PortMouton.jpeg");
            ImageIcon portArgile = new ImageIcon("./Image/PortArgile.jpeg");
            ImageIcon port3_1 = new ImageIcon("./Image/Port3-1.jpeg");

            for (int i = 0; i < 11; i++) {
                if (i == 1) {
                    JButton a = new JButton(portBois);
                    a.setEnabled(true);
                    plateau.add(a);
                } else if (i == 5) {
                    JButton a = new JButton(port3_1);
                    a.setEnabled(true);
                    plateau.add(a);
                } else if (i == 9) {
                    JButton a = new JButton(portMouton);
                    a.setEnabled(true);
                    plateau.add(a);
                } else {
                    plateau.add(new JLabel());
                }
            }

            for (int i = 0; i < 9; i++) {
                if (i == 4) {
                    JButton a = new JButton(portChamps);
                    a.setEnabled(true);
                    plateau.add(a);
                } else {
                    plateau.add(new JLabel());
                }
                for (int j = 0; j < 9; j++) {
                    ButtonInter a = new ButtonInter(i, j);
                    plateau.add(a);
                    tab[i][j] = a;
                }
                if (i == 8) {
                    JButton a = new JButton(port3_1);
                    a.setEnabled(true);
                    plateau.add(a);
                } else {
                    plateau.add(new JLabel());
                }
            }

            for (int i = 0; i < 11; i++) {
                if (i == 1) {
                    JButton a = new JButton(portArgile);
                    a.setEnabled(true);
                    plateau.add(a);
                } else if (i == 5) {
                    JButton a = new JButton(portPierre);
                    a.setEnabled(true);
                    plateau.add(a);
                } else if (i == 9) {
                    JButton a = new JButton(port3_1);
                    a.setEnabled(true);
                    plateau.add(a);
                } else {
                    plateau.add(new JLabel());
                }
            }

            // Utiliser tab[i][j].setDisabledIcon(null); pour griser l'icône

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (p.getP().selctionnerCasePaysage(i, j) != null) {
                        tab[i][j].setEnabled(true);
                        if (p.getP().selctionnerCaseRess(i, j) != null) {
                            switch (p.getP().selctionnerCaseRess(i, j).ressource) {
                                case "BOIS":
                                    tab[i][j].setIcon(bois);
                                    tab[i][j].setDisabledIcon(bois);
                                    break;
                                case "PIERRE":
                                    tab[i][j].setIcon(montagne);
                                    tab[i][j].setDisabledIcon(montagne);
                                    break;
                                case "CHAMPS":
                                    tab[i][j].setIcon(champs);
                                    tab[i][j].setDisabledIcon(champs);
                                    break;
                                case "ARGILE":
                                    tab[i][j].setIcon(mine);
                                    tab[i][j].setDisabledIcon(mine);
                                    break;
                                case "MOUTON":
                                    tab[i][j].setIcon(moutons);
                                    tab[i][j].setDisabledIcon(moutons);
                                    break;
                            }
                            tab[i][j].setText(String.valueOf(p.getP().selctionnerCaseRess(i, j).num));
                            tab[i][j].setVerticalTextPosition(SwingConstants.CENTER);
                            tab[i][j].setHorizontalTextPosition(SwingConstants.CENTER);
                            tab[i][j].setForeground(Color.LIGHT_GRAY);
                            tab[i][j].setFont(new Font("Arial", Font.PLAIN, 40));
                        } else {
                            tab[i][j].setIcon(desert);
                        }
                    }
                }
            }

            for (int i = 0; i < p.getP().getTaille(); i = i + 2) {
                for (int j = 0; j < p.getP().getTaille(); j = j + 2) {
                    tab[i][j].setEnabled(true);
                }
            }

            jouerColonie.addActionListener((ActionEvent e) -> {
                p.t.ajouterColonie(X, Y);
                for (int i = 0; i < 9; i++) {
                    for (int a = 0; a < 9; a++) {
                        if (p.getP().selctionnerCasePaysage(i, a) != null) {
                            tab[i][a].setEnabled(false);
                        } else {
                            tab[i][a].setEnabled(true);
                        }
                    }
                }
                jouerColonie.setEnabled(false);
                jouerRoute.setEnabled(false);
                creerVille.setEnabled(false);
                acheterCarteDev.setEnabled(true);
                jouerCarteDev.setEnabled(true);
                echangerAvecPort.setEnabled(true);
                terminerTour.setEnabled(true);
                VueCatan.this.validate();
                VueCatan.this.repaint();
                actuRess();
            });
            jouerRoute.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i++) {
                    for (int a = 0; a < 9; a++) {
                        if (p.getP().selctionnerCasePaysage(i, a) != null) {
                            tab[i][a].setEnabled(false);
                        } else {
                            tab[i][a].setEnabled(true);
                        }
                    }
                }
                p.t.ajouterRoute(X, Y);
                jouerColonie.setEnabled(false);
                jouerRoute.setEnabled(false);
                creerVille.setEnabled(false);
                acheterCarteDev.setEnabled(true);
                jouerCarteDev.setEnabled(true);
                echangerAvecPort.setEnabled(true);
                terminerTour.setEnabled(true);
                VueCatan.this.validate();
                VueCatan.this.repaint();
                actuRess();
            });
            creerVille.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i++) {
                    for (int a = 0; a < 9; a++) {
                        if (p.getP().selctionnerCasePaysage(i, a) != null) {
                            tab[i][a].setEnabled(false);
                        } else {
                            tab[i][a].setEnabled(true);
                        }
                    }
                }
                p.t.ajouterVille(X, Y);
                jouerColonie.setEnabled(false);
                jouerRoute.setEnabled(false);
                creerVille.setEnabled(false);
                acheterCarteDev.setEnabled(true);
                jouerCarteDev.setEnabled(true);
                echangerAvecPort.setEnabled(true);
                terminerTour.setEnabled(true);
                VueCatan.this.validate();
                VueCatan.this.repaint();
                actuRess();
            });
            acheterCarteDev.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        tab[i][j].setEnabled(true);
                    }
                }
                p.t.acheterCartDev(true);
                actuRess();
            });
            jouerCarteDev.addActionListener((ActionEvent e) -> {// A FAIRE
                if (p.t.j.getMainDev().size() > 0) {
                    info.add(commandeJouerCarteDev);
                }
                for (int i = 0; i < 9; i++) {
                    for (int a = 0; a < 9; a++) {
                        if (p.getP().selctionnerCasePaysage(i, a) != null) {
                            tab[i][a].setEnabled(false);
                        } else {
                            tab[i][a].setEnabled(true);
                        }
                    }
                }
                jouerColonie.setEnabled(false);
                jouerRoute.setEnabled(false);
                creerVille.setEnabled(false);
                acheterCarteDev.setEnabled(true);
                jouerCarteDev.setEnabled(true);
                echangerAvecPort.setEnabled(true);
                terminerTour.setEnabled(true);
                actuRess();
            });
            terminerTour.addActionListener((ActionEvent e) -> {
                p.tourFini();
                actuRess();
            });
            echangerAvecPort.addActionListener((ActionEvent e) -> { // A FAIRE
                jouerColonie.setEnabled(false);
                jouerRoute.setEnabled(false);
                creerVille.setEnabled(false);
                acheterCarteDev.setEnabled(true);
                jouerCarteDev.setEnabled(true);
                echangerAvecPort.setEnabled(true);
                terminerTour.setEnabled(true);
                actuRess();
            });
        }

        public JButton getJouerColonie() {
            return jouerColonie;
        }

        public JButton getJouerRoute() {
            return jouerRoute;
        }

        public JButton getJouerCarteDev() {
            return jouerCarteDev;
        }

        public JButton getCreerVille() {
            return creerVille;
        }

        public JButton getAcheterCarteDev() {
            return acheterCarteDev;
        }

        public JButton getEchangerAvecPort() {
            return echangerAvecPort;
        }

        public JButton getTerminerTour() {
            return terminerTour;
        }

        public ButtonInter getTab(int i, int j) {
            return tab[i][j];
        }

        class ButtonInter extends JButton {
            ButtonInter(int i, int j) {
                addActionListener((ActionEvent e) -> {
                    if (i % 2 == 0 && j % 2 == 0) { // colonie ou ville
                        if (premierTour) {
                            p.pt.ajouterColonie(i, j);
                            p.pt.toucherRessource(i, j);
                            for (int x = 0; x < 9; x++) {
                                for (int y = 0; y < 9; y++) {
                                    tab[x][y].setEnabled(false);
                                    if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
                                        if (((x == i && Math.abs(j - y) == 1) || (y == j && Math.abs(x - i) == 1))
                                                && p.getP().selctionnerCaseRoute(x, y).getEstVide()) {
                                            tab[x][y].setEnabled(true);
                                        }
                                    }
                                }
                            }
                            incorrect = true;
                            aide.setText("Veuillez selectionner \nla case ou vous voulez \nmettre votre route");
                        } else {
                            for (int x = 0; x < 9; x++) {
                                for (int y = 0; y < 9; y++) {
                                    tab[x][y].setEnabled(false);
                                }
                            }
                            jouerRoute.setEnabled(false);
                            jouerColonie.setEnabled(false);
                            creerVille.setEnabled(false);
                            if (p.getP().selctionnerCaseColonie(i, j).getEstVide()) {
                                jouerColonie.setEnabled(true);
                            } else if (!p.getP().selctionnerCaseColonie(i, j).getEstVille()) {
                                creerVille.setEnabled(true);
                            }
                            acheterCarteDev.setEnabled(false);
                            jouerCarteDev.setEnabled(false);
                            echangerAvecPort.setEnabled(false);
                            terminerTour.setEnabled(false);
                        }
                    } else if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) { // route
                        if (premierTour) {
                            p.pt.ajouterRoute(i, j);
                            p.pTourFini();
                        } else {
                            for (int x = 0; x < 9; x++) {
                                for (int y = 0; y < 9; y++) {
                                    tab[x][y].setEnabled(false);
                                }
                            }
                            jouerRoute.setEnabled(true);
                            jouerColonie.setEnabled(false);
                            creerVille.setEnabled(false);
                            acheterCarteDev.setEnabled(false);
                            jouerCarteDev.setEnabled(false);
                            echangerAvecPort.setEnabled(false);
                            terminerTour.setEnabled(false);

                        }
                    } else if (i % 2 == 1 && j % 2 == 1) {
                        p.t.deplacerVoleur(i, j);

                        for (int b = 0; b < 9; b++) {
                            for (int a = 0; a < 9; a++) {
                                if (p.getP().selctionnerCasePaysage(b, a) == null) {
                                    tab[b][a].setEnabled(true);
                                }
                            }
                        }

                        jouerColonie.setEnabled(false);
                        jouerRoute.setEnabled(false);
                        creerVille.setEnabled(false);
                        acheterCarteDev.setEnabled(true);
                        jouerCarteDev.setEnabled(true);
                        echangerAvecPort.setEnabled(true);
                        terminerTour.setEnabled(true);

                    }
                    X = i;
                    Y = j;
                    if (!premierTour) {
                        actuRess();
                    }
                });
            }
        }

        public void actuRess() {
            for (int i = 0; i < tabJ.length; i++) {
                nbrRess[i][0].setText(String.valueOf(tabJ[i].combienRessource("BOIS")));
                nbrRess[i][1].setText(String.valueOf(tabJ[i].combienRessource("CHAMPS")));
                nbrRess[i][2].setText(String.valueOf(tabJ[i].combienRessource("PIERRE")));
                nbrRess[i][3].setText(String.valueOf(tabJ[i].combienRessource("MOUTON")));
                nbrRess[i][4].setText(String.valueOf(tabJ[i].combienRessource("ARGILE")));
            }
            VueCatan.this.validate();
            VueCatan.this.repaint();
        }
    }

    public static void main(String[] args) {
        UIManager.getDefaults().put("Button.disabledText", new ColorUIResource(Color.LIGHT_GRAY));
        EventQueue.invokeLater(() -> {
            VueCatan view = new VueCatan();
            view.pack();
            view.setVisible(true);
        });
    }
}
