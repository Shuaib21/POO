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
    int X;
    int Y;

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
            for (Joueur j : tabJ) {
                System.out.println(j.estHumain);
            }
            // p.jouerPartie();
            this.remove(menu);
            this.add(new table());
            this.validate();
            this.repaint();
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

    private class table extends JPanel {
        private JPanel plateau;
        private JPanel commande;

        private JButton JouerColonie;
        private JButton JouerRoute;
        private JButton CreerVille;
        private JButton AcheterCarteDev;
        private JButton JouerCarteDev;
        private JButton EchangerAvecPort;
        private JButton TerminerTour;

        private JPanel JoueurHaut;
        private JPanel JoueurBas;
        JPanel[] J = new JPanel[4];

        table() {
            this.setLayout(new BorderLayout());

            plateau = new JPanel();
            plateau.setLayout(new GridLayout(9, 9));
            this.add(plateau, BorderLayout.CENTER);

            commande = new JPanel();
            this.add(commande, BorderLayout.EAST);

            commande.setLayout(new GridLayout(7, 1));

            JouerColonie = new JButton();
            JouerRoute = new JButton();
            CreerVille = new JButton();
            AcheterCarteDev = new JButton();
            JouerCarteDev = new JButton();
            EchangerAvecPort = new JButton();
            TerminerTour = new JButton();

            commande.add(JouerColonie);
            commande.add(JouerRoute);
            commande.add(CreerVille);
            commande.add(AcheterCarteDev);
            commande.add(JouerCarteDev);
            commande.add(EchangerAvecPort);
            commande.add(TerminerTour);

            JoueurBas = new JPanel();
            JoueurHaut = new JPanel();
            JoueurBas.setLayout(new GridLayout());
            JoueurHaut.setLayout(new GridLayout(1, 2));
            JoueurHaut.setLayout(new GridLayout(1, 2));

            for (int i = 0; i < 4; i++) {
                J[i] = new JPanel();
                J[i].setLayout(new GridLayout(2, 5));
            }

            JoueurHaut.add(J[0]);
            JoueurHaut.add(J[1]);
            JoueurBas.add(J[2]);

            if (tabJ.length == 4) { // S'il y a 4 joueurs
                JoueurBas.add(J[3]);
            }

            this.add(JoueurHaut, BorderLayout.NORTH);
            this.add(JoueurBas, BorderLayout.SOUTH);

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

            ButtonInter[][] tab = new ButtonInter[9][9];

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    ButtonInter a = new ButtonInter(i, j);
                    plateau.add(a);
                    tab[i][j] = a;
                }
            }

            JouerColonie.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        this.setEnabled(true);
                    }
                }
                
            });
            JouerRoute.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        this.setEnabled(true);
                    }
                }
            });
            CreerVille.addActionListener((ActionEvent e) -> {
                for (int i = 0; i < 9; i = i++) {
                    for (int j = 0; j < 9; j = j++) {
                        this.setEnabled(true);
                    }
                }
            });
            AcheterCarteDev.addActionListener((ActionEvent e) -> {
            });
            JouerCarteDev.addActionListener((ActionEvent e) -> {
            });
            TerminerTour.addActionListener((ActionEvent e) -> {
            });
            EchangerAvecPort.addActionListener((ActionEvent e) -> {
            });

        }
    }

    public class ButtonInter extends JButton {

        ButtonInter(int i, int j) {
            if (i % 2 == 0 && j % 2 == 0) {
                // colonie
            } else if ((i % 2 == 0 && j % 2 == 1)||(i % 2 == 1 && j % 2 == 0)) {
                // route
            } else if (i % 2 == 1 && j % 2 == 1) {
                // ressource
            }
            addActionListener((ActionEvent e) -> {
                X = i;
                Y = j;
            });

            for (int x = 0; x < 9; x++) {
                for (int y = 0; y < 9; y++) {
                    this.setEnabled(false);
                }
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
