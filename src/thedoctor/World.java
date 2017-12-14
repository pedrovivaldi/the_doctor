package thedoctor;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author PedroHenrique
 */
public class World {

    private static World singleton;

    synchronized public static World getInstance() {
        if (singleton == null) {
            singleton = new World();
        }
        return singleton;
    }

    private int sizeX;
    private int sizeY;
    private List<Agent> agentList;
    private Tardis tardis;
    private List<WorldChangeListener> worldChangeListenerList;

    private World() {
        agentList = new CopyOnWriteArrayList<>();
        tardis = new Tardis(0, 0);
        worldChangeListenerList = new ArrayList<>();
        sizeX = 500;
        sizeY = 500;
    }

    public void addWorldChangeListener(WorldChangeListener listener) {
        worldChangeListenerList.add(listener);
    }

    public void addAgent(Agent agent) {
        agentList.add(agent);
        worldChangeListenerList.stream().forEach((listener) -> {
            listener.worldHasChanged();
        });
    }

    public boolean move(Agent agent, int dx, int dy) throws InterruptedException {
        
        if (agent.x > sizeX - 14 - agent.sizeX) {
            agent.x = sizeX - agent.sizeX - 14;
        }
        
        if (agent.y > sizeY - 39 - agent.sizeY) {
            agent.y = sizeY - agent.sizeY - 39;
        }
        
        
        agent.x = agent.x + dx;
        agent.y = agent.y + dy;

        if (!moveIsPossible(agent, dx, dy)) {
            agent.x = agent.x - dx;
            agent.y = agent.y - dy;
            return false;
        }

        for (WorldChangeListener listener : worldChangeListenerList) {
            listener.worldHasChanged();
        }
        return true;
    }

    public boolean moveIsPossible(Agent agent, int dx, int dy) {
        if (agent.getX() < 0 || agent.getY() < 0 || agent.getX() > ((sizeX - 14) - agent.getSizeX())
                || agent.getY() > ((sizeY - 39) - agent.getSizeY())) {
            return false;
        }

        for (Agent a : agentList) {
            if (a != agent && agent.getRect().intersects(a.getRect())) {
                if (a instanceof Tardis) {
                    this.foundTardis();
                }

                return false;
            }
        }

        return true;
    }

    private void foundTardis() {
        for (Agent a : agentList) {
            if (a instanceof Dalek) {
                a.setAlive(false);
            }
        }

        ImageIcon icon = new ImageIcon("./src/thedoctor/explosion.gif");
        tardis.setImage(icon.getImage());

        for (WorldChangeListener listener : worldChangeListenerList) {
            listener.worldHasChanged();
        }

        SoundUtils.playClip(getClass().getResource("Bomb.mp3"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.exit(0);
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

    synchronized public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public Tardis getTardis() {
        return tardis;
    }

    public void setTardis(Tardis tardis) {
        this.tardis = tardis;
    }
}
