import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.sun.webkit.network.URLs.newURL;

public class Monitor {

    private static final int PERIOD_MILLISECONDS = 1000;

    private final HashMap<String, Set<Observer>> observers = new HashMap<>();

    private String url;
    private URLConnection connection;

    public void register(Observer observer) throws IOException {
        String url = observer.getUrl();
        observer.update(lastModified(url));
        Set<Observer> urlObservers = observers.computeIfAbsent(url, k -> new HashSet<>());
        urlObservers.add(observer);
    }

    public void monitor(int workTimeMilliseconds) throws IOException {
        while (workTimeMilliseconds > 0) {
            checkUrls();
            try {
                Thread.sleep(PERIOD_MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            workTimeMilliseconds -= PERIOD_MILLISECONDS;
        }
    }

    private void checkUrls() throws IOException {
        Date currentDateModified;
        for (String url : observers.keySet()) {
            currentDateModified = lastModified(url);
            broadcast(url, currentDateModified);
        }
    }

    private Date lastModified(String url) throws IOException {
        URL address;
        if (!url.equals(this.url)) {
            address = newURL(url);
            connection = address.openConnection();
        }
        long longtime = connection.getLastModified();
        return new Date(longtime);
    }

    private void broadcast(String url, Date currentDateModified) {
        for (Observer observer : observers.get(url)) {
            if (currentDateModified.compareTo(observer.getLastModified()) > 0) {
                observer.update(currentDateModified);
            }
        }
    }
}
