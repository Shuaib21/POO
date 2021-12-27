import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class VueCatan extends JFrame {
    protected ModeleCatan model;
    private ImagePane imagePane;

    public VueCatan() {
        setTitle("Catan");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        model = new ModeleCatan("./Image/Ocean.jpeg");
        imagePane = new ImagePane();
        setContentPane(imagePane);

        this.add(new JPanel(), BorderLayout.CENTER);
        this.setLocationRelativeTo(null);
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
