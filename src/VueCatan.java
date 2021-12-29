import javax.imageio.ImageIO;
import javax.swing.*;

import org.w3c.dom.events.Event;

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
        joueurs[3] = new creerJoueur(3);

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
            // à faire
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
            //p.jouerPartie();
            this.remove(menu);
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
        private JPanel J1;
        private JPanel J2;
        private JPanel J3;
        private JPanel J4;

        int X;
        int Y;

        table() throws IOException {
            this.setLayout(new BorderLayout());

            plateau = new JPanel();
            this.add(plateau, BorderLayout.CENTER);

            commande = new JPanel();
            this.add(plateau, BorderLayout.EAST);

            commande.setLayout(new GridLayout(7, 1));

            JouerColonie = new JButton();
            JouerRoute = new JButton();
            CreerVille = new JButton();
            AcheterCarteDev = new JButton();
            JouerCarteDev = new JButton();
            EchangerAvecPort = new JButton();
            TerminerTour = new JButton();

            J1 = new JPanel();
            J2 = new JPanel();
            J3 = new JPanel();
            J4 = new JPanel();

            JoueurBas = new JPanel();
            JoueurHaut = new JPanel();
            JoueurBas.setLayout(new GridLayout());
            JoueurHaut.setLayout(new GridLayout(1, 2));
            JoueurHaut.setLayout(new GridLayout(1, 2));

            JoueurHaut.add(J1);
            JoueurHaut.add(J2);
            JoueurBas.add(J3);
            JoueurBas.add(J4); // peut etre a enlever si il sont moins de 4 Joueurs

            J1.setLayout(new GridLayout(2, 5));
            J2.setLayout(new GridLayout(2, 5));
            J3.setLayout(new GridLayout(2, 5));
            J4.setLayout(new GridLayout(2, 5));

            BufferedImage buche = ImageIO.read(new File("./Image/buche.png"));
            BufferedImage paille = ImageIO.read(new File("./Image/paille.png"));
            BufferedImage pierre = ImageIO.read(new File("./Image/pierre.png"));
            BufferedImage mouton = ImageIO.read(new File("./Image/mouton.png"));
            BufferedImage argile = ImageIO.read(new File("./Image/argile.png"));
            JLabel bucheLabel = new JLabel(new ImageIcon(buche));
            JLabel pailleLabel = new JLabel(new ImageIcon(paille));
            JLabel pierreLabel = new JLabel(new ImageIcon(pierre));
            JLabel moutonLabel = new JLabel(new ImageIcon(mouton));
            JLabel argileLabel = new JLabel(new ImageIcon(argile));
            J1.add(bucheLabel);
            J2.add(bucheLabel);
            J3.add(bucheLabel);
            J4.add(bucheLabel);
            J1.add(pailleLabel);
            J2.add(pailleLabel);
            J3.add(pailleLabel);
            J4.add(pailleLabel);
            J1.add(pierreLabel);
            J2.add(pierreLabel);
            J3.add(pierreLabel);
            J4.add(pierreLabel);
            J1.add(moutonLabel);
            J2.add(moutonLabel);
            J3.add(moutonLabel);
            J4.add(moutonLabel);
            J1.add(argileLabel);
            J2.add(argileLabel);
            J3.add(argileLabel);
            J4.add(argileLabel);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    // plateau.add(new ButtonInter(i, j));
                }
            }
            JouerColonie.addActionListener((ActionEvent e) -> {

            });
            JouerRoute.addActionListener((ActionEvent e) -> {
            });
            CreerVille.addActionListener((ActionEvent e) -> {
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

    public class ButtonInter extends JButton { // a faire
        // ButtonInter(int i, int j){
        // if(i%2==0 && j%2==0){
        // // colonie
        // // addActionListener((ActionEvent e) ->
        // // X = i ;
        // // Y = j ;
        // // });
        // // }else if(i%2==0&&j%2==1)

        // {
        // // route
        // }else if(i%2==1&&j%2==0)
        // {
        // // route
        // }else if(i%2==1&&j%2==1)
        // {
        // // ressource
        // }

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VueCatan view = new VueCatan();
            view.pack();
            view.setVisible(true);
        });
    }
}
