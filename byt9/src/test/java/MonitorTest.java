import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.net.URLConnection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MonitorTest {

    private static final String URL1 = "http://www.pja.edu.pl/";
    private static final String URL2 = "https://builtthings.com/about-1";
    private static final String URL3 = "https://www.theartoftexture.com/";

    private static final int WORK_TIME_MILLISECONDS = 3000;

    private final Monitor monitor = new Monitor();
    private final Observer observer1 = new Observer("1", URL1);
    private final Observer observer2 = new Observer("2", URL2);
    private final Observer observer3 = new Observer("3", URL3);
    private final Observer observer4 = new Observer("4", URL3);

    private final Caretaker caretaker = new Caretaker();

    @Mock
    public URLConnection connection;
    @InjectMocks
    public Connection connectionMock1;
    @InjectMocks
    public Connection connectionMock2;
    @InjectMocks
    public Connection connectionMock3;

    @Test
    public void monitorReal() {
        try {
            monitor.register(observer1);
            monitor.register(observer2);
            monitor.register(observer3);
        } catch (IOException e) {
            fail(e.toString());
        }
        monitor.monitor(WORK_TIME_MILLISECONDS);
    }

    @Test
    public void mementoTest() {
        try {
            monitor.register(observer1);
            monitor.register(observer2);
            monitor.register(observer3);
            monitor.register(observer4);
        } catch (IOException e) {
            fail(e.toString());
        }
        caretaker.makeBackup(monitor);
        Monitor restoredMonitor = caretaker.restore();
        assertEquals(monitor, restoredMonitor);
    }

    @Test
    public void monitorMock() {
        when(connection.getLastModified()).thenAnswer((Answer<Long>) invocation -> System.currentTimeMillis());
        connectionMock1.setUrl(URL1);
        connectionMock2.setUrl(URL2);
        connectionMock3.setUrl(URL3);
        monitor.registerObserverWithConnection(observer1, connectionMock1);
        monitor.registerObserverWithConnection(observer2, connectionMock2);
        monitor.registerObserverWithConnection(observer3, connectionMock3);
        monitor.monitor(WORK_TIME_MILLISECONDS);
    }
}