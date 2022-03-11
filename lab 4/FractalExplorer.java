import java.awt.BorderLayout;
import java.awt.Color;
// import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.Dimension;
// import javax.imageio.ImageIO;
// import javax.swing.filechooser.FileNameExtensionFilter;

// import java.io.*;
import javax.swing.*;

public class FractalExplorer {

    private int screenSize;
    private JImageDisplay img;
    private FractalGenerator gen;
    private Rectangle2D.Double rectangle;

    FractalExplorer(int size) {
        // сохранение значения размера отображения в переменной screenSize
        screenSize = size;
        // инициализация объектов диапазона и фрактального генератора
        gen = new Mandelbrot();
        rectangle = new Rectangle2D.Double();
        img = new JImageDisplay(screenSize, screenSize);
        //
        gen.getInitialRange(rectangle);

    }

    // создание интерфейса
    public void createAndShowGUI() {
        // создание рамки
        JFrame frame = new JFrame("Fractal");
        // закрытие окна по клику на крестик
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // определение размера окна
        frame.setSize(screenSize, screenSize);
        img.setLayout(new BorderLayout());
        // делаем рамку видимой
        frame.setVisible(true);

        // инициализация панели с кнопкой ресета
        JPanel but_panel = new JPanel();
        // but_panel.setBackground(Color.BLACK);

        // инициализация кнопки ресета
        JButton reset_b = new JButton("reset");
        // определение размера кнопки
        reset_b.setPreferredSize(new Dimension(200, 100));

        ActionListener DisplayReset = new ResetDisplay();
        reset_b.addActionListener(DisplayReset);

        // добавление кнопки на панель
        but_panel.add(reset_b);

        MouseHandler click = new MouseHandler();
        img.addMouseListener(click);

        // добавление панели на рамку и привязка ее к середине снизу рамки
        frame.add(but_panel, BorderLayout.SOUTH);
        frame.add(img, BorderLayout.CENTER);

        // img = new JImageDisplay(screenSize, screenSize);
        // frame.getContentPane().add(img, BorderLayout.CENTER);

        frame.pack();
        frame.setResizable(false);

    }

    private void drawFractal() {
        double xCoord;
        double yCoord;
        int x;
        int y;
        for (x = 0; x < screenSize; x++) {
            for (y = 0; y < screenSize; y++) {
                xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, screenSize, x);
                yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.width, screenSize, y);
                int iterations_number = gen.numIterations(xCoord, yCoord);
                float hue = 0.7f + (float) iterations_number / 200f;
                int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                if (iterations_number == -1) {
                    img.drawPixel(x, y, 0);
                    img.repaint();
                } else {
                    img.drawPixel(x, y, rgbColor);
                    img.repaint();
                }
            }
        }
    }

    private class ResetDisplay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gen.getInitialRange(rectangle);
            FractalExplorer.this.drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, screenSize, x);
            double yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.height, screenSize, y);
            gen.recenterAndZoomRange(rectangle, xCoord, yCoord, 0.5);
            FractalExplorer.this.drawFractal();
        }
    }

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(600);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }

}
