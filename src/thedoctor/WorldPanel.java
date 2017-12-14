package thedoctor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 *
 * @author PedroHenrique
 */
public class WorldPanel extends JPanel implements WorldChangeListener, MouseListener, MouseMotionListener {

    private static final String MOVE_UP = "move up";
    private static final String MOVE_DOWN = "move down";
    private static final String MOVE_LEFT = "move left";
    private static final String MOVE_RIGHT = "move right";

    public WorldPanel() {
        super();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), MOVE_UP);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), MOVE_DOWN);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), MOVE_LEFT);
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), MOVE_RIGHT);

        this.getActionMap().put(MOVE_UP, new Move(0, -1));
        this.getActionMap().put(MOVE_DOWN, new Move(0, 1));
        this.getActionMap().put(MOVE_LEFT, new Move(-1, 0));
        this.getActionMap().put(MOVE_RIGHT, new Move(1, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.WHITE);

        World w = World.getInstance();
        Graphics2D g2 = (Graphics2D) g;

        w.getAgentList().stream().filter((d -> d instanceof Dalek)).map((d) -> {
            Dalek dalek = (Dalek) d;
            if (dalek.getMode() == Dalek.SEARCH_MODE) {

                g2.setColor(new Color(Color.YELLOW.getRed() / 255, Color.YELLOW.getGreen() / 255, Color.YELLOW.getBlue() / 255, 0.5f));
            } else {
                g2.setColor(new Color(Color.RED.getRed() / 255, Color.RED.getGreen() / 255, Color.RED.getBlue() / 255, 0.5f));
            }
            return d;
        }).forEach((d) -> {
            g2.fillOval(d.getX() + (d.getSizeX() / 2) - 75, d.getY() + (d.getSizeY() / 2) - 75, 150, 150);
        });

        for (Agent a : w.getAgentList()) {
            g.drawImage(a.getImage(), a.getX(), a.getY(), a.getSizeX(), a.getSizeY(), this);
        }
    }

    @Override
    public void worldHasChanged() {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Tardis tardis = World.getInstance().getTardis();
        try {
            World.getInstance().move(tardis, e.getX() - tardis.getX(), e.getY() - tardis.getY());
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Ignore
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //Ignore
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //Ignore
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //Ignore
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Tardis tardis = World.getInstance().getTardis();
        try {
            World.getInstance().move(tardis, e.getX() - tardis.getX(), e.getY() - tardis.getY());
        } catch (InterruptedException ex) {
            Logger.getLogger(WorldPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //Ignore
    }

    private class Move extends AbstractAction {

        private int vx;
        private int vy;

        public Move(int vx, int vy) {
            this.vx = vx;
            this.vy = vy;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                World.getInstance().move(World.getInstance().getTardis(), vx, vy);
            } catch (InterruptedException ex) {
                Logger.getLogger(WorldPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
