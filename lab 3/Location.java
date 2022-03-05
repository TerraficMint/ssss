/**
 * This class represents a specific location in a 2D map. Coordinates are
 * integer values.
 **/
public class Location {
    /** X coordinate of this location. **/
    public int xCoord;

    /** Y coordinate of this location. **/
    public int yCoord;

    /** Creates a new location with the specified integer coordinates. **/
    public Location(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    /** Creates a new location with coordinates (0, 0). **/
    public Location() {
        this(0, 0);
    }

    /** Метод сравнения **/
    // сравнение является ли настоящая точка целью
    public boolean equals(Object target) {
        // действительно ли таргет относится к типу Location который мы хотим
        // преобразовать - downcast приведение
        // (избегание ошибки ClassCastException)
        if (target instanceof Location) {
            // работаем с настоящей координатой как с целью
            Location other = (Location) target;
            if (xCoord == other.xCoord && yCoord == other.yCoord) {
                return true;
            }
        }
        return false;
    }

    /** Генерация Хэш-кода **/
    public int hashCode() {
        // переменная прайм неизменна - любое простое число
        final int PRIME = 17;
        int result = 1;
        // хэш функция
        result = PRIME * (PRIME * result + xCoord) + yCoord;
        return result;
    }
}
