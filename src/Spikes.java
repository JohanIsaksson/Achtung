import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Spikes extends Ability {
    private int size;
    private int spikeLength;


    public Spikes() {
	super("Spikes");

	tick = new ActionListener()
	{
	    @Override public void actionPerformed(ActionEvent e) {
		elapsedTicks++;
		if (elapsedTicks == cdTime)
		    reset();
	    }
	};

	timer = new Timer(1000,tick);

	cdTime = 20;
	size = 5;
	spikeLength = 75;

    }

    @Override
    public void activate() {
        double angle1 = worm.getAngle() - Math.PI/2;
        double angle2 = angle1 + Math.PI;
        for (int i = 0; i < spikeLength; i++) {
            draw(worm.getGraphics(), (int)(worm.getPos().getX() + Math.cos(angle1) * i), (int)(worm.getPos().getY() + Math.sin(angle1) * i));
            draw(worm.getGraphics(), (int)(worm.getPos().getX() + Math.cos(angle2) * i), (int)(worm.getPos().getY() + Math.sin(angle2) * i));
        }
	super.activate();

    }

    private void draw(Graphics2D g, int x, int y) {
	g.setColor(worm.getColor());
	g.drawOval(x,y,size,size);
    }



}
