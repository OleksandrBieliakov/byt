import java.net.URLConnection;
import java.util.Date;
import java.util.Objects;

public class Connection {

    private String url;
    private final URLConnection connection;

    Connection(String url, URLConnection connection) {
        this.url = url;
        this.connection = connection;
    }

    Connection(URLConnection connection) {
        this.connection = connection;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Date lastModified() {
        return new Date(connection.getLastModified());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Connection)) return false;
        Connection that = (Connection) o;
        return url.equals(that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
