import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PathClearing extends Ability {

    Position pos;
    double movementSpeed;
    double angle;
    int size;
    byte length;

    Graphics2D g;

    public PathClearing() {
        super("PathClearing");
        pos = Position.ZERO;
        movementSpeed = 5;
        angle = 0;
        size = 30;
        length = 65;

	tick = new ActionListener() {
	    @Override public void actionPerformed(ActionEvent e) {
		elapsedTicks++;
		if (elapsedTicks == cdTime)
		    reset();
	    }
	};

	timer = new Timer(1000,tick);

	cdTime = 20;
    }


    private boolean hasCollided() {
        if (Settings.backBuffer.isOutOfBounds((int)pos.getX(),(int)pos.getY()) ) {
            return true;
        }
        return false;
    }

    private void draw(){
        g = worm.getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval((int) pos.getX()-size/2, (int) pos.getY()-size/2, size, size);
    }

    @Override
    public void activate(){
        angle = worm.getAngle();
	int headStart = 13;
        pos = new Position(worm.getPos().getX() + Math.cos(angle)*headStart, worm.getPos().getY() + Math.sin(angle)*headStart);
        for (int i = 0; i < length ; i++) {
            if (!hasCollided()) {
                draw();
                pos.setPosition(pos.getX() + Math.cos(angle) * movementSpeed,pos.getY() + Math.sin(angle) * movementSpeed);
            }
        }
	super.activate();

    }
}
