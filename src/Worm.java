import java.awt.*;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Serdiev
 * Date: 2013-09-12
 * Time: 13:37
 * To change this template use File | Settings | File Templates.
 */
public class Worm {

    //worm properties--------------------------
    protected Position pos;
    protected double movementSpeed;
    protected float turningSpeed;
    protected int size;
    protected boolean alive;
    protected int[] controllerKeys;
    protected byte points;
    protected Color color;
    protected boolean visible;
    protected Ability ability;
    protected Graphics2D graphics;
    protected double turningAngle;
    //Hole related variables----------------------

    protected int maxHoleDistance;
    protected double distanceToHole;
    protected int holeSize;
    protected int newHole;
    protected boolean hole;

    protected enum TurnState{
    	Forward,
    	Left,
    	Right
        }
    protected TurnState turnState = TurnState.Forward;

    //getters and setters------------------------------------------
    public int getSize(){
        return size;
    }
    public void setHoleSize(int holeSize) {
        this.holeSize = holeSize;
    }
    public void setHoleDistance(int holeDistance) {
        this.maxHoleDistance = holeDistance;
    }

    public int[] getControllerKeys() {
        return controllerKeys;
    }
    public void setKey(int key, byte num){
        controllerKeys[num] = key;
    }

    public void setMovementSpeed(double movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    public void setTurningSpeed(float turningSpeed) {
        this.turningSpeed = turningSpeed;
    }

    public Position getPos(){
        return pos;
    }
    public void setPos(Position p) {
        this.pos = p;
    }

    public void setAngle(double a) {
        turningAngle = a;
    }
    public double getAngle() {
    	return this.turningAngle;
        }

    public  void setSpeed(double speed) {
	this.movementSpeed = speed;
    }
    public double getSpeed() {
	return this.movementSpeed;
    }

    public Color getColor() {
        return this.color;
    }
    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isAlive() {
        return alive;
    }
    public void setAlive(final boolean alive) {
        this.alive = alive;
    }

    public void setVisible(boolean bol) {
	this.visible = bol;
    }
    public boolean isVisible() {
	return visible;
    }

    public void setAbility(Ability a) {
	ability = a;
	a.setWorm(this);
    }

    public Graphics2D getGraphics() {
	    return graphics;
	}

    //---------------------------------------------------------------------------

    public Worm() {
        controllerKeys = new int[3];
        color = Color.RED;
        pos = new Position(0,0);
        size = 10;
        movementSpeed = 3.0d;
        turningSpeed = 0.1f;
        turningAngle = 0;
	    alive = true;
        visible = true;
        maxHoleDistance = 1;
        holeSize = 1;
        Random rn = new Random();
        newHole = size + rn.nextInt(holeSize);
    }

    public void addPoint() {
	    this.points++;
    }

    public int getPoints() {
        return this.points;
    }
    public void setPoints(byte p) {
        points = p;
    }

    public void updateWorm() {
        updateTurn();
        updatePosition();

        if (!hole) {
            distanceToHole -= movementSpeed;
            if (distanceToHole <= 0){
                hole = true;
                Random rn = new Random();
                newHole = Settings.baseHolesize + rn.nextInt(holeSize);
            }
        }
        else{
            distanceToHole += movementSpeed;
            if (distanceToHole >= newHole){
                hole = false;
                Random rn = new Random();
                distanceToHole = Settings.baseHoleDistance + 5*rn.nextInt(maxHoleDistance);
            }
        }
    }
    protected void updateTurn() {
        switch (turnState) {
            case Left:
                turnLeft();
                break;
            case Right:
                turnRight();
                break;
        }
    }
    protected void updatePosition(){
        if (alive) {
            pos.setPosition(pos.getX() + Math.cos(turningAngle) * movementSpeed,
                            pos.getY() + Math.sin(turningAngle) * movementSpeed);
        }
    }

    protected void turnRight(){
	    turningAngle += turningSpeed;
	    if (turningAngle >= Math.PI*2) turningAngle -= Math.PI*2;
    }
    protected void turnLeft(){
	    turningAngle -= turningSpeed;
	    if (turningAngle <= 0) turningAngle += Math.PI*2;
    }
    public void setTurn(char dir) {
        if (dir == 'R') turnState = TurnState.Right;
        else if (dir == 'L') turnState = TurnState.Left;
        else turnState = TurnState.Forward;
    }

    public char getTurn() {
        if (turnState == TurnState.Forward) return 'F';
        else if (turnState == TurnState.Left) return 'L';
        else return 'R';
    }

    public void activateAbility() {
        if (!ability.isOnCooldown())
            ability.activate();
    }

    public Color getCollision(){

        //(size/2+1) is to check one pixel outside the drawn worm. (The pixels which we want to check)
        //size/2 is to move the starting position of the position check (because it starts in the upper left corner otherwise)
	double anglePerRevolution = 32;
        if (visible && !hole ){
            for (double curr = turningAngle - (Math.PI/5); curr < turningAngle + (Math.PI/5); curr += ((2*Math.PI)/anglePerRevolution)) {
                if (Settings.backBuffer.isOutOfBounds((int) pos.getX() + (int) (Math.cos(curr) * (size/2+2)), (int) (pos.getY() + (int) (Math.sin(curr) * (size/2+2))))) {
                    onDeath();
                    return Color.BLACK;
                }
                else {
                    int temp = Settings.backBuffer.getScreen().getRGB((int) pos.getX() + (int) (Math.cos(curr) * (size/2+2)), (int) (pos.getY() + (int) (Math.sin(curr) * (size/2+2))));
                    if (temp != Color.BLACK.getRGB()) {
                        onDeath();
                        return new Color(Settings.backBuffer.getScreen().getRGB((int) pos.getX() + (int) (Math.cos(curr) * (size/2+2)), (int) (pos.getY() + (int) (Math.sin(curr) * (size/2+2)))));
                    }
                }
            }
        }
        else {
            for (double curr = turningAngle - (Math.PI/5); curr < turningAngle + (Math.PI/5); curr += ((2*Math.PI)/anglePerRevolution)) {
                if (Settings.backBuffer.isOutOfBounds((int) pos.getX() + (int) (Math.cos(curr) * (size/2+2)), (int) (pos.getY() + (int) (Math.sin(curr) * (size/2+2))))) {
                    onDeath();
                    return Color.BLACK;
                }
            }
        }
	    return null;
    }



    private void onDeath() {
        alive = false;
    }

    public void draw(Graphics2D g){
	this.graphics = g; //----------------------------------------------------------------------------------------------- new
        if (visible && !hole){ //----------------------------------------------------------------------------------------------- new
            g.setColor(this.color);
            g.fillOval((int)this.pos.getX()-size/2,(int)this.pos.getY()-size/2,this.size,this.size);
        }

    }
    public void drawHead(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval((int)this.pos.getX()-size/2,(int)this.pos.getY()-size/2,this.size,this.size);

    }

    public void reset() {
        alive = true;
        ability.reset();
        turnState = TurnState.Forward;

    }


}
