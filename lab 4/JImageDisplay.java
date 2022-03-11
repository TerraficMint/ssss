import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.Graphics;

// наследует JComponent 
public class JImageDisplay extends JComponent {

    private BufferedImage img;

    // инициализация объекта BufferedImage новым изображением с данной шириной и
    // высотой, и типом изображения TYPE_INT_RGB
    public JImageDisplay(int height, int width) {
        img = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        Dimension size = new Dimension(width,height);
        setPreferredSize(size);
    }

    // вывод на экран данного изображения
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // null - реалиация ImageObserver не требуется
        g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), null);
    }

    // пиксель с конкретными координатами заливается каким-либо цветом
    public void drawPixel(int x, int y, int rgbColor) {
        img.setRGB(x, y, rgbColor);
    }

    // все пиксели изображения заливаются черным
    public void clearImage() {
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, 0);
            }
        }
    }
}