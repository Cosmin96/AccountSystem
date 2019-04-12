import config.Configuration;

public class Application {

    public static void main(String[] args) {
        Configuration.setupDB();
        Configuration.startServer();
    }
}
