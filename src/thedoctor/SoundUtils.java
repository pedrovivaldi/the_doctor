package thedoctor;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author PedroHenrique
 */
public class SoundUtils {

    synchronized public static void playClip(URL resource) {
        Media hit;
        try {
            hit = new Media(resource.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(hit);
            mediaPlayer.play();

        } catch (URISyntaxException ex) {
            Logger.getLogger(Dalek.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
