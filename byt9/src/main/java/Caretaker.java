import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Caretaker {

    private static final String PATH = "backup.txt";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");


    public void makeBackup(Monitor monitor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(PATH)))) {
            writer.write(monitor.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Monitor restore() {
        Monitor monitor = new Monitor();
        String url;
        String id;
        Date lastModified;
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            line = reader.readLine();
            while (line != null) {
                url = reader.readLine();
                line = reader.readLine();
                while (line != null && !line.equals("url")) {
                    id = line;
                    lastModified = DATE_FORMAT.parse(reader.readLine());
                    monitor.register(new Observer(id, url, lastModified));
                    line = reader.readLine();
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return monitor;
    }
}
