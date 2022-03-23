import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {
    // максимальное количество итераций
    public static final int MAX_ITERATIONS = 2000;

    @Override
    // установка начальных значений для отрисовки фрактала
    public void getInitialRange(Rectangle2D.Double rect) {
        // в соответствии с начальным диапазоном в (-2 - 1.5i) - (1 + 1.5i):
        rect.x = -2;
        rect.y = -1.5;
        rect.height = 3;
        rect.width = 3;

    }

    @Override
    // подсчет итераций для каждого пикселя
    public int numIterations(double x, double y) {
        int counter = 0;
        // действительная часть комплексного числа
        double real = 0;
        // мнимая часть
        double imaginary = 0;
        double z_n2 = 0;

        // основная формула для построения фрактала мандельброта
        // модуль комплексного числа должен не превышать 2
        while (counter < MAX_ITERATIONS && z_n2 < 4) {
            counter++;

            double nextRe = real * real - imaginary * imaginary + x;
            double nextIm = 2 * real * imaginary + y;

            z_n2 = nextRe * nextRe + nextIm * nextIm;

            real = nextRe;
            imaginary = nextIm;
        }

        // тернарный оператор: true -> counter; false -> -1
        // -1 -> установка черного цвета
        return counter < MAX_ITERATIONS ? counter : -1;
    }

    public String toString() {
        return "Mandelbrot";
    }
}
