package thedoctor;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author PedroHenrique
 */
public class WorldFrame extends JFrame {

    public WorldFrame(WorldPanel panel) {
        this.setTitle("The Doctor - Protect the T.A.R.D.I.S");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(panel);
        World.getInstance().setSizeX(this.getWidth());
        World.getInstance().setSizeY(this.getHeight());
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Adding listener to listen to window resizing
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        this.setVisible(true);
    }

    private void formComponentResized(ComponentEvent evt) {
        try {
            World.getInstance().move(World.getInstance().getTardis(), 0, 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        World.getInstance().setSizeX(this.getWidth());
        World.getInstance().setSizeY(this.getHeight());
    }
}
