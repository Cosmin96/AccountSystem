package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties");
            properties.load(input);
        } catch(FileNotFoundException e) {
            System.out.println(e);
        } catch(IOException e) {
            System.out.println(e);
        }
    }

    public static String getStringProperty(String key) {
        return properties.getProperty(key);
    }
}
