package thedoctor;

import java.awt.geom.Ellipse2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author PedroHenrique
 */
public class Dalek extends Agent implements Runnable {

    public static final int SEARCH_MODE = 0;
    public static final int EXTERMINATE_MODE = 1;
    private static final ImageIcon DALEK_ICON = new ImageIcon("./src/thedoctor/dalek.gif");
    private static final ImageIcon DALEK_EXTERMINATE_ICON = new ImageIcon("./src/thedoctor/dalek_exterminate.gif");
    private static final int SEARCH_DELAY = 50;
    private static final int EXTERMINATE_DELAY = 40;

    private int mode;
    private int delay;

    public Dalek(int x, int y) {
        super(x, y);

        this.image = DALEK_ICON.getImage();
        this.alive = true;
        this.sizeX = DALEK_ICON.getIconWidth();
        this.sizeY = DALEK_ICON.getIconHeight();
        this.changeDirection();
        this.mode = SEARCH_MODE;
        this.delay = SEARCH_DELAY;
    }

    @Override
    public void run() {
        while (alive) {

            switch (mode) {
                case SEARCH_MODE:
                    if (this.isNearTardis(World.getInstance().getTardis())) {
                        this.switchMode(EXTERMINATE_MODE);
                    }
                    break;
                case EXTERMINATE_MODE:
                    if (!this.isNearTardis(World.getInstance().getTardis())) {
                        this.switchMode(SEARCH_MODE);
                    }
                    this.checkVelocityToTardis(World.getInstance().getTardis());
                    break;
            }

            try {
                if (!World.getInstance().move(this, vx, vy)) {
                    this.changeDirection();

                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Dalek.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeDirection() {
        do {
            vx = (int) Math.round(Math.random() * 2) - 1;
            vy = (int) Math.round(Math.random() * 2) - 1;
        } while (vx == 0 && vy == 00);
    }

    public boolean isNearTardis(Tardis tardis) {
        Ellipse2D elipse = new Ellipse2D.Double(x + (sizeX / 2) - 75, y + (sizeY / 2) - 75, 150, 150);

        if (elipse.intersects(tardis.getRect())) {
            return true;
        }

        return false;
        /*for (int i = tardis.x; i < (tardis.x + tardis.sizeX); i++) {
         for (int j = tardis.y; j < (tardis.y + tardis.sizeY); j++) {
         if (elipse.contains(i, j)) {
         return true;
         }
         }
         }
         return false;*/
    }

    private void switchMode(int mode) {
        this.mode = mode;
        switch (mode) {
            case SEARCH_MODE:

                image = DALEK_ICON.getImage();
                sizeX = DALEK_ICON.getIconWidth();
                sizeY = DALEK_ICON.getIconHeight();
                this.delay = SEARCH_DELAY;
                this.changeDirection();
                break;
            case EXTERMINATE_MODE:
                image = DALEK_EXTERMINATE_ICON.getImage();
                sizeX = DALEK_EXTERMINATE_ICON.getIconWidth();
                sizeY = DALEK_EXTERMINATE_ICON.getIconHeight();
                this.delay = EXTERMINATE_DELAY;

                this.checkVelocityToTardis(World.getInstance().getTardis());

                SoundUtils.playClip(getClass().getResource("Exterminate.mp3"));

                break;
        }
    }

    public int getMode() {
        return mode;
    }

    private void checkVelocityToTardis(Tardis tardis) {
        if (x + sizeX/2 > (tardis.x + tardis.sizeX / 2)) {
            vx = -1;
        } else if (x + sizeX/2 == tardis.x + tardis.sizeY / 2) {
            vx = 0;
        } else {
            vx = 1;
        }

        if (y + sizeY/2 > (tardis.y + tardis.sizeY / 2)) {
            vy = -1;
        } else if (y + sizeY/2 == (tardis.y + tardis.sizeY / 2)) {
            vy = 0;
        } else {
            vy = 1;
        }
    }
}
