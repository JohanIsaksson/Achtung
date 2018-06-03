import javax.swing.*;
import java.awt.event.*;

public class Ghost extends Ability {
    int duration;

    public Ghost() {
        super("Ghost");

        tick = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
                elapsedTicks++;
                if (elapsedTicks == duration)
                    worm.setVisible(true);
                else if (elapsedTicks == cdTime) {
                    reset();
                }
            }
        };

        timer = new Timer(1000,tick);
        cdTime = 25;
        duration = 4;
    }

    @Override
    public void activate() {
        worm.setVisible(false);
	    super.activate();
    }

    @Override
    public void reset() {
        super.reset();
        worm.setVisible(true);
    }
}

