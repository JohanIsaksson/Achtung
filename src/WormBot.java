import javafx.util.Pair;

import java.awt.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA. User: JOHANOVIC Date: 24/01/14 Time: 23:27 To change this template use File | Settings | File
 * Templates.
 */
public class WormBot extends Worm
{
    private double distL180,distL158,distL136,distL112,distL90,distL67,distL45,distL20,dist0,distR20,distR45,distR67,distR90,distR112,distR136,distR158,distR180;
	ArrayList<Pair<Double, Double>> distanceList;
	private enum State {Corner, Normal, Wall}
	private State botState;
	private ArrayList<Position> trailList, prev_trailList;

    public WormBot() {
		botState = State.Normal;

		trailList = new ArrayList<Position>();
		prev_trailList = new ArrayList<Position>();

		distanceList = new ArrayList<Pair<Double, Double>>();
    }

    @Override
    public void updateWorm() {
        super.updateWorm();

		//check surroundings
		prev_trailList.clear();
		for (Position p: trailList) {
			prev_trailList.add(new Position(p));
		}
		trailList.clear();

		distL180 = advancedDistanceToWall(this.turningAngle-Math.PI);
		distL158 = advancedDistanceToWall(this.turningAngle-7*Math.PI/8);
		distL136 = advancedDistanceToWall(this.turningAngle-6*Math.PI/8);
		distL112 = advancedDistanceToWall(this.turningAngle-5*Math.PI/8);
		distL90 = advancedDistanceToWall(this.turningAngle-4*Math.PI/8);
		distL67 = advancedDistanceToWall(this.turningAngle-3*Math.PI/8);
		distL45 = advancedDistanceToWall(this.turningAngle-2*Math.PI/8);
		distL20 = advancedDistanceToWall(this.turningAngle-1*Math.PI/8);
		dist0 = advancedDistanceToWall(this.turningAngle);
		distR20 = advancedDistanceToWall(this.turningAngle+1*Math.PI/8);
		distR45 = advancedDistanceToWall(this.turningAngle+2*Math.PI/8);
		distR67 = advancedDistanceToWall(this.turningAngle+3*Math.PI/8);
		distR90 = advancedDistanceToWall(this.turningAngle+4*Math.PI/8);
		distR112 = advancedDistanceToWall(this.turningAngle+5*Math.PI/8);
		distR136 = advancedDistanceToWall(this.turningAngle+6*Math.PI/8);
		distR158 = advancedDistanceToWall(this.turningAngle+7*Math.PI/8);
		distR180 = advancedDistanceToWall(this.turningAngle+Math.PI);


		distanceList.clear();
		distanceList.add(new Pair<Double, Double>(0.0, distL180));

		distanceList.add(new Pair<Double, Double>(-Math.PI, distL180));
		distanceList.add(new Pair<Double, Double>(-7*Math.PI/8, distL158));
		distanceList.add(new Pair<Double, Double>(-6*Math.PI/8, distL136));
		distanceList.add(new Pair<Double, Double>(-5*Math.PI/8, distL112));
		distanceList.add(new Pair<Double, Double>(-4*Math.PI/8, distL90));
		distanceList.add(new Pair<Double, Double>(-3*Math.PI/8, distL67));
		distanceList.add(new Pair<Double, Double>(-2*Math.PI/8, distL45));
		distanceList.add(new Pair<Double, Double>(-1*Math.PI/8, distL20));

		distanceList.add(new Pair<Double, Double>(1*Math.PI/8, distR20));
		distanceList.add(new Pair<Double, Double>(2*Math.PI/8, distR45));
		distanceList.add(new Pair<Double, Double>(3*Math.PI/8, distR67));
		distanceList.add(new Pair<Double, Double>(4*Math.PI/8, distR90));
		distanceList.add(new Pair<Double, Double>(5*Math.PI/8, distR112));
		distanceList.add(new Pair<Double, Double>(6*Math.PI/8, distR136));
		distanceList.add(new Pair<Double, Double>(7*Math.PI/8, distR158));
		distanceList.add(new Pair<Double, Double>(Math.PI, distR180));

		Collections.sort(distanceList, new Comparator<Pair<Double, Double>>() {
			@Override
			public int compare(Pair<Double, Double> o1, Pair<Double, Double> o2) {
				if (o1.getValue() == o2.getValue())
					return 0;
				return (o1.getValue() < o2.getValue()) ? 1 : -1;
			}
		});
		if (distanceList.get(0).getKey() > 0) {
			this.turnRight();
		}
		else if (distanceList.get(0).getKey() < 0) {
			this.turnLeft();
		}

		if (turningAngle <= 0) turningAngle += Math.PI*2;
		else if (turningAngle >= Math.PI*2) turningAngle -= Math.PI*2;
		
    }

    private boolean collides(Position p, int size) {

		for (int y = (int)p.getY() - size/2; y < p.getY() + size/2; y++) {
    		for (int x = (int)p.getX() - size/2; x < p.getX() + size/2; x++) {
				if (Settings.backBuffer.isOutOfBounds(x, y) || Settings.backBuffer.getScreen().getRGB(x, y) != Color.BLACK.getRGB()) {
					return true;
				}
			}
		}
    	return false;
	}

    private double advancedDistanceToWall(double angle) {
    	double dist = 0;
	int range = 200;
	double currentAngle = this.turningAngle;
	double dir;
	double min = 0.05d;
	double speed = this.getSpeed();

	Position p = new Position(this.getPos().getX()+this.getSpeed()*Math.cos(turningAngle),this.getPos().getY()+this.getSpeed()*Math.sin(
		turningAngle));

	if (Math.abs(angle-turningAngle) < min)
	    dir = 0;
	else if (angle > turningAngle)
	    dir = 1;
	else
	    dir = -1;

	//while (!Settings.backBuffer.isOutOfBounds((int)p.getX(),(int)p.getY()) &&
	//        	Settings.backBuffer.getScreen().getRGB((int)p.getX(),(int)p.getY()) == Color.BLACK.getRGB() &&
	//                	dist < range) {
		while (dist < 5) {
			p.setPosition(p.getX()+this.getSpeed()*Math.cos(currentAngle),p.getY()+this.getSpeed()*Math.sin(currentAngle));
			if (dist > 5)
				trailList.add(new Position(p));

			dist+= speed;
			speed = 0.1d;
			if (Math.abs(currentAngle-angle) > min) {
				currentAngle += dir*this.turningSpeed;
				speed = this.getSpeed();
			}
		}

	while (!collides(p, this.size-2) && dist < range) {
	    p.setPosition(p.getX()+this.getSpeed()*Math.cos(currentAngle),p.getY()+this.getSpeed()*Math.sin(currentAngle));
	    if (dist > 5)
	    	trailList.add(new Position(p));

	    dist+= speed;
	    speed = 0.1d;
	    if (Math.abs(currentAngle-angle) > min) {
	    	currentAngle += dir*this.turningSpeed;
		speed = this.getSpeed();
	    }
	}
/*
	System.out.println("--------------------");
	System.out.println("efter sväng");
	System.out.println(dist);
	System.out.println("------------------------");
*/

	if (dist > range)
	    return range;
	return dist;
    }


	@Override
	public void draw(Graphics2D g) {

    	// Undraw last trails
		/*g.setColor(Color.BLACK);
		for (Position p: prev_trailList) {
			g.fillRect((int) p.getX(), (int) p.getY(), 1, 1);
		}*/

    	super.draw(g);

		// Draw new trails
		/*g.setColor(Color.WHITE);
		for (Position p: trailList) {
			g.fillRect((int) p.getX(), (int) p.getY(), 1, 1);
		}*/

	}

}

