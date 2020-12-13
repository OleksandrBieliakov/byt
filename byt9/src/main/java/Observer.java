import java.util.Date;
import java.util.Objects;

public class Observer {

    private final String id;
    private final String url;
    private Date lastModified;

    Observer(String id, String url) {
        this.id = id;
        this.url = url;
    }

    Observer(String id, String url, Date lastModified) {
        this.id = id;
        this.url = url;
        this.lastModified = lastModified;
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

    public String serialize() {
        return id + "\n" + lastModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Observer)) return false;
        Observer observer = (Observer) o;
        return getId().equals(observer.getId()) &&
                getUrl().equals(observer.getUrl()) &&
                Objects.equals(getLastModified(), observer.getLastModified());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getLastModified());
    }
}
