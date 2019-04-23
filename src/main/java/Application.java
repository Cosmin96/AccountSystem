import config.Configuration;

public class Application {

    public static void main(String[] args) throws Exception{
        Configuration.setupDB();
        Configuration.startServer();
    }
}
