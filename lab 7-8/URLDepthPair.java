import java.net.*;
import java.util.regex.*;

// сохранение пары адрес-глубина
public class URLDepthPair {
    // регулярное выражение для поиска адреса
    public static final String URL_PREFIX = "http: //";
    public static final Pattern URL_PATTERN = Pattern.compile(URL_PREFIX, Pattern.CASE_INSENSITIVE);

    private URL URL;

    private int depth;

    public URLDepthPair(URL url, int d) throws MalformedURLException {
        URL = new URL(url.toString());
        depth = d;
    }

    @Override
    public String toString() {
        return "URL: " + URL.toString() + ", Depth: " + depth;
    }

    public URL getURL() {
        return URL;
    }

    public int getDepth() {
        return depth;
    }

    public String getHost() {
        return URL.getHost();
    }

    public String getDocPath() {
        return URL.getPath();
    }

    // ссылка абсолютная, а не относительная
    public static boolean isAbsolute(String url) {
        Matcher URLChecker = URL_PATTERN.matcher(url);
        if (!URLChecker.find()) {
            return false;
        }
        return true;
    }
}
