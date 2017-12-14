package thedoctor;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author PedroHenrique
 */
public abstract class Agent {
    protected int sizeX;
    protected int sizeY;
    protected boolean alive;
    protected int x;
    protected int y;
    protected int vx;
    protected int vy;
    protected Image image;

    public Agent(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Rectangle getRect() {
        return new Rectangle(x, y, sizeX, sizeY);
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
