public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            args = new String[] { "database.cfg" };
        }
        Launcher.getLauncher().launch(args);
    }
}
