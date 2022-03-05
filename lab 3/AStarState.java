import java.nio.file.Watchable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map. This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints." In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState {
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    // инициализация полей для открытых и закрытых вершин с ссылками на пустые
    // коллекции (предоставляют возможность совершать операции с группой объектов)
    private HashMap<Location, Waypoint> OpenedWaypoints;

    private HashMap<Location, Waypoint> ClosedWaypoints;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null) {
            throw new NullPointerException("map cannot be null");
        }
        this.map = map;
        OpenedWaypoints = new HashMap<>();
        ClosedWaypoints = new HashMap<>();
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap() {
        return map;
    }

    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints() {
        // возвращает количество открытых вершин
        return OpenedWaypoints.size();
    }

    /**
     * Метод сканирует все открытые вершины и возвращает вершину с минимальной общей
     * стоимостью.
     * Если открытых вершин нет, возвращает null
     **/
    public Waypoint getMinOpenWaypoint() {
        // Если в "открытом" наборе нет вершин
        if (OpenedWaypoints.isEmpty()) {
            return null;
        }
        // список, который заполняется значениями ключей из коллекции открытых точек
        List<Location> ListWaypoints = new ArrayList<>(OpenedWaypoints.keySet());
        // создание точки класса Waypoint, присваивание ей первого значения из коллекции
        Waypoint TempMinWaypoint = OpenedWaypoints.get(ListWaypoints.get(0));
        // перебор все значений из коллекции открытых точек (сравнивнение с
        // настоящей точкой, и, если значение меньше, то переприсваиваем)
        for (int i = 1; i < ListWaypoints.size(); i++) {
            if (TempMinWaypoint.getRemainingCost() > OpenedWaypoints.get(ListWaypoints.get(i)).getRemainingCost()) {
                TempMinWaypoint = OpenedWaypoints.get(ListWaypoints.get(i));
            }
        }
        return TempMinWaypoint;
    }

    /**
     * 
     * Этот метод добавляет вершину newWP в коллекции открытых вершин.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {
        // по ключу проверяем - содержит ли коллекция открытых вершин настоящую вершину
        // если вершины нет, то добавляем ее в коллекцию
        if (!OpenedWaypoints.containsKey(newWP.getLocation())) {
            // HashMap.put()(заменит старое значение на новое)
            OpenedWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        // обновляем стоимость пути от текущей точки до новой (выбирается минимум)
        if (OpenedWaypoints.get(newWP.getLocation()).getPreviousCost() > newWP.getPreviousCost()) {
            OpenedWaypoints.put(newWP.getLocation(), newWP);
            return true;
        }
        return false;
    }

    /**
     * возвращает значение true, если указанное местоположение встречается в наборе
     * закрытых вершин
     **/
    public boolean isLocationClosed(Location loc) {
        if (ClosedWaypoints.containsKey(loc)) {
            return true;
        }
        return false;
    }

    /**
     * перемещает вершину из набора «открытых вершин» в набор «закрытых вершин»
     **/
    public void closeWaypoint(Location loc) {
        // HashMap.put()(заменит старое значение на новое)
        ClosedWaypoints.put(loc, OpenedWaypoints.get(loc));
        OpenedWaypoints.remove(loc);
    }
}
