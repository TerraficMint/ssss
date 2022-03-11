public class Point3d extends Point2d {

    private double xCoord;
    private double yCoord;
    private double zCoord;

    // Конструктор инициализации
    public Point3d(double x, double y, double z) {
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }

    public Point3d() {
        this(0, 0, 0);
    }

    // Возвращение координаты Z
    public double getZ() {
        return zCoord;
    }

    // Установка значения
    // координаты Z
    public void setZ(double val) {
        zCoord = val;
    }

    // Метод расчета расстояния между двумя точками
    public static double distance(Point3d poin1, Point3d poin2) {
        return Math.sqrt(Math.pow((poin1.getZ() - poin2.getZ()), 2) + Math.pow((poin1.getY() - poin2.getY()), 2)
                + Math.pow((poin1.getX() - poin2.getX()), 2));
    }

    // Переопределение метода equals
    public boolean equals(Point3d point) {
        if ((this.xCoord == point.xCoord) && (this.yCoord == point.yCoord) && (this.zCoord == point.zCoord))
            return true;
        else
            return false;
    }

    // Метод сравнения двух точек
    public static boolean comparison(Point3d point1, Point3d point2) {

        if (point1.equals(point2))
            return true;
        else
            return false;
    }

    public static void main(String[] args) {

        // Создание новых объектов класса Point3d
        Point3d obj1 = new Point3d(1.2, 2.3, 3.4);
        Point3d obj2 = new Point3d();// создает точку (0,0, 0)
        Point3d obj3 = new Point3d(1.2, 2.3, 3.4);

        // равны ли точки 1 и 3
        // (вызов метода comparison)
        System.out.println("Равны ли точки 1 и 3: " + comparison(obj1, obj3));
        // равны ли точки 1 и 2
        System.out.println("Равны ли точки 1 и 2: " + comparison(obj1, obj2) + "\n");

        // вывод координат первой точки
        System.out.println("Координаты первой точки:\n" + "x = " + obj1.getX()
                + ", y = "
                + obj1.getY() + ", z = " + obj1.getZ() + "\n");

        // изменение координат Х и Y первой точки
        obj1.setX(3);
        obj1.setY(1.4);

        // вывод координат первой точки после их изменения
        System.out.println("Координаты первой точки после их изменения:\n" + "x = " + obj1.getX()
                + ", y = "
                + obj1.getY() + ", z = " + obj1.getZ() + "\n");

        // вывод расстояния между точками 1 и 2
        double dis = distance(obj1, obj2);
        System.out.print("Расстояние между точками 1 и 2 = ");
        // форматирование двоичного числа на выходе метода distance
        System.out.print(String.format("%2.2f", dis));
    }
}
