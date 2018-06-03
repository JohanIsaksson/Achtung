/**
 * Created with IntelliJ IDEA. User: frela412 Date: 9/20/13 Time: 4:14 PM To change this template use File | Settings | File
 * Templates.
 */
public class Position
{

    double x;
    double y;

    public void setPosition(double x,double y) {
	this.x = x;
	this.y = y;
    }

    public double getX() {
	return x;
    }

    public double getY() {
	return y;
    }


    public Position(double x, double y) {
	this.x = x;
	this.y = y;
    }

    public Position(Position p) {
	 x = p.getX();
	 y = p.getY();
    }

    @Override
    public String toString() {
        return "(" + (int)x + "," + (int)y +")";
    }
	
	public static Position ZERO = new Position(0,0);

}
