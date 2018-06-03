import java.awt.*;

/**
 * Created with IntelliJ IDEA. User: JOHANOVIC Date: 24/01/14 Time: 23:27 To change this template use File | Settings | File
 * Templates.
 */
public class WormBot extends Worm
{
    private double distL180,distL90,distL45,distL20,dist0,distR20,distR45,distR90,distR180;
    private double[] dist = new double[9];
	private enum State {Corner, Normal, Wall}
	private State botState;

    public WormBot() {
	botState = State.Normal;


    }

    @Override
    public void updateWorm() {
        super.updateWorm();

	//check surroundings
	distL180 = advancedDistanceToWall(this.turningAngle-Math.PI);
	distL90 = advancedDistanceToWall(this.turningAngle-Math.PI/2);
	distL45 = advancedDistanceToWall(this.turningAngle-Math.PI/4);
	distL20 = advancedDistanceToWall(this.turningAngle-Math.PI/8);
	dist0 = advancedDistanceToWall(this.turningAngle);
	distR20 = advancedDistanceToWall(this.turningAngle+Math.PI/8);
	distR45 = advancedDistanceToWall(this.turningAngle+Math.PI/4);
	distR90 = advancedDistanceToWall(this.turningAngle+Math.PI/2);
	distR180 = advancedDistanceToWall(this.turningAngle+Math.PI);


	dist[0] = advancedDistanceToWall(this.turningAngle-Math.PI);
		dist[1] = advancedDistanceToWall(this.turningAngle-Math.PI/2);
		dist[2] = advancedDistanceToWall(this.turningAngle-Math.PI/4);
		dist[3] = advancedDistanceToWall(this.turningAngle-Math.PI/8);
		dist[4] = advancedDistanceToWall(this.turningAngle);
		dist[5] = advancedDistanceToWall(this.turningAngle+Math.PI/8);
		dist[6] = advancedDistanceToWall(this.turningAngle+Math.PI/4);
		dist[7] = advancedDistanceToWall(this.turningAngle+Math.PI/2);
		dist[8] = advancedDistanceToWall(this.turningAngle+Math.PI);

	switch (botState) {
	    case Corner:
		System.out.println("corner");


		break;

	    case Normal:
		if (dist0 < 150) {
		    if (distL20 < dist0 && distR20 < dist0 && distL45 < distL20 && distR45 < distR20) {
			botState = State.Corner;
		    	break;
		    }
		    else if ((distL20 < dist0 && dist0 < distR20) || distL20 < 30) {
			this.turnRight();
		    }
		    else if ((distL20 > dist0 && dist0 > distR20) || distR20 < 30) {
			this.turnLeft();
		    }

		}
		else
		    setTurn('F');



		break;
	    case Wall:
		break;

	}

	String out = "(" + distL20 + "," + dist0 + "," + distR20 + ")";
	System.out.println(out);


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

	while (!Settings.backBuffer.isOutOfBounds((int)p.getX(),(int)p.getY()) &&
	        	Settings.backBuffer.getScreen().getRGB((int)p.getX(),(int)p.getY()) == Color.BLACK.getRGB() &&
	                	dist < range) {

	    p.setPosition(p.getX()+this.getSpeed()*Math.cos(currentAngle),p.getY()+this.getSpeed()*Math.sin(currentAngle));

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

}
