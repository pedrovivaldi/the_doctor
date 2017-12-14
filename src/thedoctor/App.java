package thedoctor;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author PedroHenrique
 */
public class App extends Application {

    public static void main(String[] args) {
        WorldPanel panel = new WorldPanel();
        WorldFrame frame = new WorldFrame(panel);
        World.getInstance().addWorldChangeListener(panel);

        SoundUtils.playClip(App.class.getResource("Survive In Me.mp3"));

        Tardis tardis = new Tardis(300, 300);
        World.getInstance().addAgent(tardis);
        World.getInstance().setTardis(tardis);

        for (int i = 0; i < 4; i++) {
            int x = (int) Math.round(Math.random() * 420) + 11;
            int y = (int) Math.round(Math.random() * 400) + 11;

            Dalek d = new Dalek(x, y);
            if (!d.isNearTardis(World.getInstance().getTardis()) && World.getInstance().moveIsPossible(d, 0, 0)) {
                World.getInstance().addAgent(d);

                Thread t = new Thread(d);
                t.start();
            } else {
                i--;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Application.launch();
    }
}
