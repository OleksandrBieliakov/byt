import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MonitorTest {

    private static final String URL1 = "http://www.pja.edu.pl/";
    private static final String URL2 = "https://builtthings.com/about-1";
    private static final String URL3 = "https://www.theartoftexture.com/";
    private static final int WORK_TIME_MILLISECONDS = 30000;

    private final Monitor monitor = new Monitor();
    private final Observer observer1 = new Observer("1", URL1);
    private final Observer observer2 = new Observer("2", URL2);
    private final Observer observer3 = new Observer("3", URL3);

    @Mock
    private URLConnection connectionMock;
    @InjectMocks
    private Monitor monitorMock;

    @Test
    public void monitorReal() {
        try {
            monitor.register(observer1);
            monitor.register(observer2);
            monitor.register(observer3);
            monitor.monitor(WORK_TIME_MILLISECONDS);
        } catch (IOException e) {
            fail(e.toString());
        }
    }

    @Test
    public void monitorMock() {
        when(connectionMock.getLastModified()).thenReturn(System.currentTimeMillis());
        try {
            monitorMock.register(observer1);
            monitorMock.register(observer2);
            monitorMock.register(observer3);
            monitorMock.monitor(WORK_TIME_MILLISECONDS);
        } catch (IOException e) {
            fail(e.toString());
        }
    }
}