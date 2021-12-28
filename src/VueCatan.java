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

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            VueCatan view = new VueCatan();
            view.pack();
            view.setVisible(true);
        });
    }
}
