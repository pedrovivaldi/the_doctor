package thedoctor;

import javax.swing.ImageIcon;

/**
 *
 * @author PedroHenrique
 */
public class Tardis extends Agent{

    public Tardis(int x, int y) {
        super(x, y);

        ImageIcon icon = new ImageIcon("./src/thedoctor/tardis.png");
        this.image = icon.getImage();
        this.alive = true;
        this.sizeX = icon.getIconWidth();
        this.sizeY = icon.getIconHeight();
    }
}
