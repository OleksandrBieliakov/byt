package part2.exercise02;

import java.util.*;

public class Configuration {
    public int interval;

    public int duration;

    public int departure;

    private int getPropertyValue(String propertyName, Properties properties, String exceptionMessage) throws ConfigurationException {
        String property = properties.getProperty(propertyName);
        if (property == null) {
            throw new ConfigurationException(exceptionMessage);
        }
        return Integer.parseInt(property);
    }

    private void checkForPositivity(int value, String exceptionMessage) throws ConfigurationException {
        if (value <= 0) {
            throw new ConfigurationException(exceptionMessage);
        }
    }

    private void checkBitsAdditionIsZero(int value1, int value2, String exceptionMessage) throws ConfigurationException {
        if ((value1 % value2) != 0) {
            throw new ConfigurationException(exceptionMessage);
        }
    }


    private void loadInterval(Properties properties) throws ConfigurationException {
        int value = getPropertyValue("interval", properties, "monitor interval");
        checkForPositivity(value, "monitor interval > 0");
        interval = value;
    }

    private void loadDuration(Properties properties) throws ConfigurationException {
        int value = getPropertyValue("duration", properties, "duration");
        checkForPositivity(value, "duration > 0");
        checkBitsAdditionIsZero(value, interval, "duration & interval");
        duration = value;
    }

    private void loadDeparture(Properties properties) throws ConfigurationException {
        int value = getPropertyValue("departure", properties, "departure offset");
        checkForPositivity(value, "departure > 0");
        checkBitsAdditionIsZero(value, interval, "departure & interval");
        departure = value;
    }

    public void load(Properties properties) throws ConfigurationException {
        loadInterval(properties);
        loadDuration(properties);
        loadDeparture(properties);
    }
}
