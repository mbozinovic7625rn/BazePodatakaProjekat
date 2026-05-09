import config.Config;

public class Launcher {
    private static Launcher launcher;

    public static Launcher getLauncher() {
        if (launcher == null) {
            synchronized (Launcher.class) {
                if (launcher == null) {
                    launcher = new Launcher();
                }
            }
        }

        return launcher;
    }

    private Launcher() {}

    void launch(String... args) {
        this.setUp(args);
        this.work();
        this.clean();
    }

    private void setUp(String... args) {
        Config.loadProperties(args[0]);
        String host = Config.getPropertyValue("host", "localhost");
        String port = Config.getPropertyValue("port", "3306");
        String database = Config.getPropertyValue("database", "laboratorija_db");
        String user = Config.getPropertyValue("user", "root");
        String password = Config.getPropertyValue("password", "root");

        Config.connect(host, port, database, user, password);
    }

    private void work() {
        new App().start();
    }

    private void clean() {
        Config.disconnect();
    }
}
