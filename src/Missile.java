import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Missile extends Ability {

    Position pos;
    Position lastPos;
    double movementSpeed;
    double angle;
    int explosionSize;
    int size, fps;

    boolean collided;

    Graphics2D g;

    public Missile() {
        super("Missile");

        pos = Position.ZERO;
        lastPos = Position.ZERO;
        movementSpeed = 5;
        angle = 0;
        explosionSize = 75;
        size = 4;

	fps = 60;

        tick = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTicks++;
                if (!collided) {
                    if (elapsedTicks == 100 || hasCollided())  {
                        collided = true;
                        explode();
                    }
                    else {
                        draw();
                        lastPos = new Position(pos.getX(),pos.getY());
                        pos.setPosition(pos.getX() + Math.cos(angle) * movementSpeed,pos.getY() + Math.sin(angle) * movementSpeed);
                    }
                }
                else if (elapsedTicks == cdTime)
                    reset();
            }
        };
        timer = new Timer(1000/fps,tick);

        collided = false;
	int seconds = 15;
        cdTime = seconds*fps;

    }

    private boolean hasCollided() {
        if (Settings.backBuffer.isOutOfBounds((int)pos.getX(),(int)pos.getY()) || Settings.backBuffer.getScreen().getRGB((int)pos.getX(),(int)pos.getY()) != Color.BLACK.getRGB()) {
            return true;
        }
        return false;
    }

    private void explode() {
        g = worm.getGraphics();
        g.setColor(worm.getColor());
        g.fillOval((int)lastPos.getX()-explosionSize/2,(int)lastPos.getY()-explosionSize/2, explosionSize, explosionSize);
    }

    private void draw() {
        g = worm.getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval((int) lastPos.getX(), (int) lastPos.getY(), size, size);
        g.setColor(worm.getColor());
        g.fillOval((int) pos.getX(), (int) pos.getY(), size, size);
    }

    private void undraw() {
        g = worm.getGraphics();
        g.setColor(Color.BLACK);
        g.fillOval((int) lastPos.getX(), (int) lastPos.getY(), size, size);
        g.fillOval((int) pos.getX(), (int) pos.getY(), size, size);
    }


    @Override
    public void activate(){
        angle = worm.getAngle();
	int headStart = 30;
        pos = new Position(worm.getPos().getX() + Math.cos(angle)*headStart, worm.getPos().getY() + Math.sin(angle)*headStart);
        lastPos = pos;
        collided = false;
	super.activate();
    }

    @Override
    public void reset() {
	super.reset();
        undraw();
        collided = false;
    }

}
