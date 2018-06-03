import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 09/10/13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class Speed extends Ability {
    double oldSpeed;
    int duration;

    public Speed(Worm w) {
        super("Speed");
	setWorm(w);

	oldSpeed = worm.getSpeed();

        tick = new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
		elapsedTicks++;
		if (elapsedTicks == duration)
		    worm.setSpeed(oldSpeed);
		if (elapsedTicks == cdTime)
		    reset();
            }
        };

        timer = new Timer(1000,tick);
	cdTime = 20;
	duration = 5;
    }

    @Override
    public void activate() {
	oldSpeed = worm.getSpeed();
        worm.setSpeed(worm.getSpeed()+4);
	super.activate();
    }

    @Override
    public void reset() {
	worm.setSpeed(oldSpeed);
	super.reset();
    }


}
