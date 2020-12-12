import java.util.Date;

public class Observer {

    private final String id;
    private final String url;
    private Date lastModified;

    Observer(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void update(Date lastModified) {
        this.lastModified = lastModified;
        System.out.println(this);
    }

    @Override
    public String toString() {
        return "Observer: " + id + ", url: " + url + ", lastModified: " + lastModified;
    }
}
