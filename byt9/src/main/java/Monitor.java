import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import static com.sun.webkit.network.URLs.newURL;

public class Monitor {

    private static final int PERIOD_MILLISECONDS = 1000;

    private final HashMap<Connection, Set<Observer>> observers = new HashMap<>();

    public void register(Observer observer) throws IOException {
        String url = observer.getUrl();
        Connection connection = createConnection(url);
        registerObserverWithConnection(observer, connection);
    }

    public void registerObserverWithConnection(Observer observer, Connection connection) {
        if (observer.getLastModified() == null) {
            observer.update(connection.lastModified());
        }
        Set<Observer> urlObservers = observers.computeIfAbsent(connection, k -> new HashSet<>());
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

    private void checkUrls() {
        Date currentDateModified;
        for (Connection connection : observers.keySet()) {
            currentDateModified = connection.lastModified();
            broadcast(connection, currentDateModified);
        }
    }

    private Connection createConnection(String url) throws IOException {
        URL address = newURL(url);
        URLConnection connection = address.openConnection();
        return new Connection(url, connection);
    }

    private void broadcast(Connection connection, Date currentDateModified) {
        for (Observer observer : observers.get(connection)) {
            if (currentDateModified.compareTo(observer.getLastModified()) > 0) {
                observer.update(currentDateModified);
            }
        }
    }

    public String serialize() {
        StringBuilder buffer = new StringBuilder();
        for (Connection connection : observers.keySet()) {
            buffer.append("url");
            buffer.append('\n');
            buffer.append(connection.getUrl());
            buffer.append('\n');
            for (Observer observer : observers.get(connection)) {
                buffer.append(observer.serialize());
                buffer.append('\n');
            }
        }
        return buffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Monitor)) return false;
        Monitor monitor = (Monitor) o;
        return observers.equals(monitor.observers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(observers);
    }
}
