import java.net.*;

public class Crawler {
    // пул всех адресов
    private URLPool pool;

    public int numThreads = 4;

    // конструктор класса, на кход корневой адрес и макс глубина
    public Crawler(String root, int max) throws MalformedURLException {

        pool = new URLPool(max);

        URL rootURL = new URL(root);
        pool.add(new URLDepthPair(rootURL, 0));
    }

    // запуск кравлера
    public void crawl() {
        // создание кравлеров и потоков, их запуск
        for (int i = 0; i < numThreads; i++) {
            CrawlerTask crawler = new CrawlerTask(pool);
            Thread thread = new Thread(crawler);
            thread.start();
        }
        // если все потоки ждут, значит метод выполнен
        while (pool.getWaitCount() != numThreads) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                System.out.println("Ignoring unexpected InterruptedException - " +
                        e.getMessage());
            }
        }

        // вывод всех адресов
        pool.printURLs();
    }

    public static void main(String[] args) {
        // если пользователь ввел неверное число аргументов
        if (args.length < 2 || args.length > 5) {
            System.err.println("Usage: java Crawler <URL> <depth> " +
                    "<patience> -t <threads>");
            System.exit(1);
        }

        try {
            Crawler crawler = new Crawler(args[0], Integer.parseInt(args[1]));

            // свитч по количеству введенных аргументов
            switch (args.length) {
                case 3:
                    CrawlerTask.maxPatience = Integer.parseInt(args[2]);
                    break;
                case 4:
                    crawler.numThreads = Integer.parseInt(args[3]);
                    break;
                case 5:
                    CrawlerTask.maxPatience = Integer.parseInt(args[2]);
                    crawler.numThreads = Integer.parseInt(args[4]);
                    break;
            }
            crawler.crawl();
        } catch (MalformedURLException e) {
            System.err.println("Error: The URL " + args[0] + " is not valid");
            System.exit(1);
        }
        System.exit(0);
    }
}

// http://htmlbook.ru/http://htmlbook.ru/ 1
