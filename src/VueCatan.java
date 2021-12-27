import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

public class VueCatan extends JFrame {
    public VueCatan() {
        setTitle("Catan");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public class ImageEditModel {
        private BufferedImage image;

        public ImageEditModel(String chemin) {
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
}
