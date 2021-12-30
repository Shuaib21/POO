import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.event.MouseInputListener;

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
    JLabel erreur = new JLabel();
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
            p = new Partie(tabJ);
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
        ButtonInter[][] tab;

        Table() {
            this.setLayout(new BorderLayout());

            plateau = new JPanel();
            plateau.setLayout(new GridLayout(9, 9));
            this.add(plateau, BorderLayout.CENTER);

            commande = new JPanel();
            this.add(commande, BorderLayout.EAST);

            commande.setLayout(new GridLayout(7, 1));

            jouerColonie = new JButton();
            jouerRoute = new JButton();
            creerVille = new JButton();
            acheterCarteDev = new JButton();
            jouerCarteDev = new JButton();
            echangerAvecPort = new JButton();
            terminerTour = new JButton();

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

            if (tabJ.length == 4) { // S'il y a 4 joueurs
                joueurBas.add(J[3]);
            }

            this.add(joueurHaut, BorderLayout.NORTH);
            this.add(joueurBas, BorderLayout.SOUTH);

            try {
                BufferedImage buche = ImageIO.read(new File("./Image/buche.png"));
                BufferedImage paille = ImageIO.read(new File("./Image/paille.png"));
                BufferedImage pierre = ImageIO.read(new File("./Image/pierre.png"));
                BufferedImage mouton = ImageIO.read(new File("./Image/mouton.png"));
                BufferedImage argile = ImageIO.read(new File("./Image/argile.png"));

                for (int i = 0; i < 4; i++) {
                    J[i].add(new JLabel(new ImageIcon(buche)));
                    J[i].add(new JLabel(new ImageIcon(paille)));
                    J[i].add(new JLabel(new ImageIcon(pierre)));
                    J[i].add(new JLabel(new ImageIcon(mouton)));
                    J[i].add(new JLabel(new ImageIcon(argile)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            tab = new ButtonInter[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    ButtonInter a = new ButtonInter(i, j);
                    plateau.add(a);
                    tab[i][j] = a;
                }
            }

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    tab[x][y].setEnabled(false);
                }
            }
            for (int i = 0; i < p.getP().getTaille(); i = i + 2) {
                for (int j = 0; j < p.getP().getTaille(); j = j + 2) {
                    tab[i][j].setEnabled(true);
                }
            }

            jouerColonie.addActionListener((ActionEvent e) -> {
                p.t.ajouterColonie(X, Y);
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        tab[i][j].setEnabled(true);
                    }
                }
                VueCatan.this.validate();
                VueCatan.this.repaint();
            });
            jouerRoute.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        tab[i][j].setEnabled(true);
                    }
                }
            });
            creerVille.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        tab[i][j].setEnabled(true);
                    }
                }
            });
            acheterCarteDev.addActionListener((ActionEvent e) -> {
            });
            jouerCarteDev.addActionListener((ActionEvent e) -> {
            });
            terminerTour.addActionListener((ActionEvent e) -> {
            });
            echangerAvecPort.addActionListener((ActionEvent e) -> {
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
                        System.out.println(premierTour);
                        if (premierTour) {
                            p.pt.ajouterColonie(i, j);
                            System.out.println("Colo placé");
                            for (int x = 0; x < 9; x++) {
                                for (int y = 0; y < 9; y++) {
                                    tab[x][y].setEnabled(false);
                                    if ((x % 2 == 0 && y % 2 == 1) || (x % 2 == 1 && y % 2 == 0)) {
                                        if (p.pt.correcte(x, y) && p.getP().selctionnerCaseRoute(x, y).getEstVide()) {
                                            tab[x][y].setEnabled(true);
                                        }
                                    }
                                }
                            }
                            System.out.println("errror");
                            incorrect = true;
                            erreur.setText("Veuillez selectionner la case ou vous voulez mettre votre route");
                        }
                    } else if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) { // route
                        if (premierTour) {
                            p.pt.ajouterRoute(i, j);
                        } else {
                            jouerRoute.setEnabled(true);
                        }
                    } else if (i % 2 == 1 && j % 2 == 1) {
                        p.t.deplacerVoleur(i, j);
                    }
                    X = i;
                    Y = j;
                    if (!premierTour) {
                        for (int x = 0; x < 9; x++) {
                            for (int y = 0; y < 9; y++) {
                                tab[x][y].setEnabled(false);
                            }
                        }
                    }
                });
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VueCatan view = new VueCatan();
            view.pack();
            view.setVisible(true);
        });
    }
}
