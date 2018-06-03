import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA. User: johis024 Date: 2013-09-27 Time: 13:52 To change this template use File | Settings | File
 * Templates.
 */
public class Ability
{
    protected Worm worm;
    protected String name;
    protected boolean cooldown;
    protected int cdTime, elapsedTicks;
    protected Timer timer;
    protected ActionListener tick;


    public Ability(String name) {
        this.name = name;
        cooldown = false;
        elapsedTicks = 0;
    }

    public void setWorm(Worm worm) {
        this.worm = worm;
    }

    public void activate() {
        cooldown = true;
        elapsedTicks = 0;
        timer.start();
    }
    public void reset() {
        cooldown = false;
        timer.stop();
    }
    public boolean isOnCooldown() {
        return cooldown;
    }

    @Override
    public String toString() {
        return name;
    }


}

