import view.LoginView;

import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CountDownLatch;

public class App {
    public void start() {
        CountDownLatch closed = new CountDownLatch(1);
        SwingUtilities.invokeLater(() -> {
            LoginView view = new LoginView();
            view.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    closed.countDown();
                }
            });
            view.setVisible(true);
        });
        try {
            closed.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
