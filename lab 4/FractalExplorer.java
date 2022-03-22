
// import javafx.scene.control.ComboBox;
// import javafx.scene.layout.Border;
import java.awt.geom.Rectangle2D;
// import java.awt.geom.Rectangle2D.Double;
import java.awt.event.*;
// import javax.swing.JFileChooser.*;
import javax.swing.filechooser.*;
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;

public class FractalExplorer {

    /** Элементы, которые надо блокировать методом enableUI **/
    private JComboBox<FractalGenerator> comboBox;
    private JButton button_reset;
    private JButton button_save;

    private int screenSize;
    private JImageDisplay img;
    private FractalGenerator gen;
    private Rectangle2D.Double rectangle;
    private int rowsRemaining;

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

        JPanel top_panel = new JPanel();
        JLabel lable = new JLabel("Fractal: ");
        top_panel.add(lable);

        FractalGenerator tricorn_frac = new Tricorn();
        FractalGenerator ship_frac = new BurningShip();

        JComboBox<FractalGenerator> comboBox = new JComboBox<>();
        comboBox.addItem(gen);
        comboBox.addItem(tricorn_frac);
        comboBox.addItem(ship_frac);

        ActionListener choose = new ChooseFractal();
        comboBox.addActionListener(choose);

        top_panel.add(comboBox);
        frame.add(top_panel, BorderLayout.NORTH);

        JButton save_b = new JButton("save");
        save_b.setPreferredSize(new Dimension(200, 100));
        ActionListener save = new SavePic();
        save_b.addActionListener(save);

        but_panel.add(save_b);

        frame.pack();
        frame.setResizable(false);

    }

    public void drawFractal() {
        enableUI(false);
        rowsRemaining = screenSize;
        for (int x = 0; x < screenSize; x++) {
            FractalWorker drawRow = new FractalWorker(x);
            // запуск фонового потока
            drawRow.execute();
        }
    }

    private void enableUI(boolean value) {
        comboBox.setEnabled(value);
        button_reset.setEnabled(value);
        button_save.setEnabled(value);
    }

    private class ResetDisplay implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gen.getInitialRange(rectangle);
            FractalExplorer.this.drawFractal();
        }
    }

    private class SavePic implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // объект, в который будем сохранять картинку
            JFileChooser chooser = new JFileChooser();
            // указываем фильтр по расширению файла
            FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
            chooser.setFileFilter(filter);
            chooser.setAcceptAllFileFilterUsed(false);
            // вызываем диалоговое окно сохранения файла
            int userSelection = chooser.showSaveDialog(img);
            // если юзер подтверждает сохранение
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                // узнаем директорию, выбранную пользователем
                java.io.File file = chooser.getSelectedFile();
                try {
                    BufferedImage displayImage = img.getImage();
                    // записываем картинку в указанный файл
                    javax.imageio.ImageIO.write(displayImage, "png", file);
                } catch (Exception exception) {
                    // сообщение об ошибке
                    JOptionPane.showMessageDialog(img, exception.getMessage(), "Cannot Save Image",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    // выбор фрактала через комбобокс
    private class ChooseFractal implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox<FractalGenerator> target = (JComboBox<FractalGenerator>) e.getSource();
            gen = (FractalGenerator) target.getSelectedItem();
            gen.getInitialRange(rectangle);
            FractalExplorer.this.drawFractal();
        }
    }

    private class MouseHandler extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            if (rowsRemaining != 0) {
                return;
            }
            int x = e.getX();
            int y = e.getY();
            double xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, screenSize, x);
            double yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.height, screenSize, y);
            gen.recenterAndZoomRange(rectangle, xCoord, yCoord, 0.5);
            FractalExplorer.this.drawFractal();
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object> {
        int yCoordinate;
        int[] pixColor;

        private FractalWorker(int yTarget) {
            yCoordinate = yTarget;
        }

        protected Object doInBackground() {
            pixColor = new int[screenSize];

            double xCoord;
            double yCoord;
            for (int i = 0; i < pixColor.length; i++) {
                xCoord = FractalGenerator.getCoord(rectangle.x, rectangle.x + rectangle.width, screenSize, i);
                yCoord = FractalGenerator.getCoord(rectangle.y, rectangle.y + rectangle.height, screenSize,
                        yCoordinate);
                int iterations_number = gen.numIterations(xCoord, yCoord);

                if (iterations_number == -1) {
                    pixColor[i] = 0;
                } else {
                    float hue = 0.7f + (float) iterations_number / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                    pixColor[i] = rgbColor;

                }
            }

            return null;
        }

        protected void done() {
            for (int i = 0; i < pixColor.length; i++) {
                img.drawPixel(i, yCoordinate, pixColor[i]);
            }

            img.repaint(0, 0, yCoordinate, screenSize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }

    }

    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(600);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }

}
