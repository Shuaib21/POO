import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.awt.event.*;

public class VueCatan extends JFrame {
    protected ModeleCatan model;
    private ImagePane imagePane;
    private JPanel menu;
    private Joueur[] tabJ = new Joueur[4];

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

        for (int i = 1; i < 4; i++) {
            menu.add(new creerJoueur(i));
        }

        JMenuBar menuBar = new JMenuBar();
        JButton plus = new JButton("+");
        JButton moins = new JButton("-");
        JButton start = new JButton("START");

        moins.setEnabled(false);

        menuBar.add(plus);
        menuBar.add(moins);
        menuBar.add(start);

        menu.add(menuBar);

        creerJoueur j4 = new creerJoueur(4);

        plus.addActionListener(
                (ActionEvent e) -> {
                    menu.remove(menuBar);
                    menu.add(j4);
                    menu.add(menuBar);
                    plus.setEnabled(false);
                    moins.setEnabled(true);
                    this.validate();
                    this.repaint();
                });

        moins.addActionListener(
                (ActionEvent e) -> {
                    menu.remove(menuBar);
                    menu.remove(j4);
                    menu.add(menuBar);
                    tabJ[3] = null;
                    plus.setEnabled(true);
                    moins.setEnabled(false);
                    this.validate();
                    this.repaint();
                });

        start.addActionListener((ActionEvent e) -> {
            // à faire
            for (int i = 0; i < 3; i++) {
                System.out.println(tabJ[i].getPseudo());
            }

        });
    }

    private class creerJoueur extends JPanel {
        private boolean humain = true;

        public creerJoueur(int n) {
            setLayout(new BorderLayout());
            JTextField pseudo; // declare a field
            pseudo = new JTextField(10); // create field approx 10 columns wide.
            add(pseudo, BorderLayout.CENTER); // add it to a JPanel

            JLabel l = new JLabel();
            l.setText("Joueur " + String.valueOf(n) + " : ");
            add(l, BorderLayout.WEST);

            JButton ordi = new JButton("HUMAIN");
            add(ordi, BorderLayout.EAST);

            pseudo.addFocusListener(
                    new FocusListener() {
                        public void focusGained(FocusEvent e) {
                        }

                        public void focusLost(FocusEvent e) {
                            String nom = pseudo.getText();
                            tabJ[n - 1] = new Joueur(getCouleur(n), nom);
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
                        tabJ[n - 1].setEstHumain(humain);
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
        //     if(i%2==0 && j%2==0){
        //         // colonie
        //     //     addActionListener((ActionEvent e) -> 
        //     //             X = i ;
        //     //             Y = j ;
        //     //         });
        //     // }else if(i%2==0&&j%2==1)

        // {
        //     // route
        // }else if(i%2==1&&j%2==0)
        // {
        //     // route
        // }else if(i%2==1&&j%2==1)
        // {
        //     // ressource
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
