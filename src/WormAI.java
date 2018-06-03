import com.sun.org.apache.xpath.internal.operations.Or;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 17/10/13
 * Time: 18:11
 * To change this template use File | Settings | File Templates.
 */


public class WormAI extends Worm {

    int anglesPerScan  = 25;

    Comparator<double[]> comp;
    PriorityQueue<double[]> list; //this shall not be weakened due to the fact that we need a priorityqueue, not a queue.


    public WormAI() {

        comp = new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                if (o1[1] < o2[1]) {
                    return 1;
                }
                else if (o1[1] > o2[1]) {
                    return -1;
                }
                if (turningAngle-Math.abs(o1[0]) > turningAngle-Math.abs(o2[0]))
                    return 1;
                else if (turningAngle-Math.abs(o1[0]) < turningAngle-Math.abs(o2[0]))
                    return -1;
                return 0;
            }
        };
        list = new PriorityQueue<double[]>(anglesPerScan,comp);
    }

    @Override
    public void updateWorm() {
        super.updateWorm();

        for (double i = turningAngle - turningSpeed; i <= turningAngle + turningSpeed; i += (turningSpeed*2)/anglesPerScan) {
            //list.add(new double[] {i-turningAngle,distanceToWall(i)});
            list.add(advancedDistanceToWall(turningAngle-i));
        }
        list.add(new double[] {0,distanceToWall(turningAngle)});


        turningAngle += list.poll()[0];
        list.clear();

    }

    private int distanceToWall(double angle) {
        double dist = 0;
        double speed = 0.5d;
	double range = 300d;

        Position p = new Position(this.pos.getX() + Math.cos(angle)*this.size,this.pos.getY() + Math.sin(angle)*this.size);

        while (!Settings.backBuffer.isOutOfBounds((int)p.getX(),(int)p.getY()) &&
                Settings.backBuffer.getScreen().getRGB((int)p.getX(),(int)p.getY()) == Color.BLACK.getRGB() &&
                dist < range) {
            p.setPosition(p.getX() + Math.cos(angle)*speed,p.getY() + Math.sin(angle)*(speed));
            dist += speed;
        }
        return (int)dist;
    }

    private double[] advancedDistanceToWall(double angle) {
        double dist = 0;
        double range = 300;
        double[] ans = new double[2];
        double speed = 0.5d;

        double currentAngle = turningAngle;
        Position p = new Position(this.pos.getX() + Math.cos(currentAngle)*(this.size),this.pos.getY() + Math.sin(currentAngle)*this.size);
        dist += this.size;

        while (!Settings.backBuffer.isOutOfBounds((int)p.getX(),(int)p.getY()) &&
                Settings.backBuffer.getScreen().getRGB((int)p.getX(),(int)p.getY()) == Color.BLACK.getRGB() &&
                dist < range) {

            currentAngle+=angle;
            p.setPosition(p.getX() + Math.cos(currentAngle)*speed,p.getY() + Math.sin(currentAngle)*(speed));
            dist += speed;
        }

        ans[0] = angle;
        ans[1] = dist;
        return  ans;
    }

}
