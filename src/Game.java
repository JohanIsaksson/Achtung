import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Serdiev
 * Date: 2013-09-12
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public class Game extends JPanel implements Runnable {

    //Game loop
    protected Timer gameLoop;
    protected ActionListener action;
    protected int fps = 60;

    //Game variables
    protected ScoreBoard score;
    protected ArrayList<Worm> wormList;
    protected byte rounds;
    protected byte currentRound;
    protected int moveAtStart;
    protected boolean ended;

    //Graphics
    protected BackBuffer backBuffer;

    //Input
    protected Keyboard keyboard;

    //Gamestates
    protected GameState currentState;


    public ArrayList<Worm> getWormList(){
        return wormList;
    }
    public Game() {
        wormList = new ArrayList<Worm>();
        currentRound = 0;
        ended = false;

        backBuffer = Settings.backBuffer;

        //fix the panel
        this.setLayout(null);
        this.setFocusable(true);
        this.requestFocus();

        //initailize keyboard
        keyboard = new Keyboard();
        this.addKeyListener(keyboard);

        //initialize gameloop timer
    	action = new ActionListener()
        {
            @Override public void actionPerformed(final ActionEvent e) {
                update();
                repaint();
            }
        };
        gameLoop = new Timer(1000/fps,action);

    }

    public void update(){
        switch (currentState){
            case RUNNING:
                if (!isEndOfRound()) {
                    for (Worm w : wormList)
                        w.updateWorm();

                    collisionDetection();
                    input();
                }
                else {
                    for (Worm w : wormList)
                        w.reset();
                    currentState = GameState.ENDOFROUND;
                }
                break;

            case INTERMISSION:
                if (moveAtStart < 4) {
                    for (Worm w : wormList)
                        w.updateWorm();
                    moveAtStart++;
                }
                else if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
                    currentState = GameState.RUNNING;
                }
                break;

            case ENDOFROUND:
                if (currentRound < rounds) {
                    if (keyboard.isPressed(KeyEvent.VK_SPACE))
                        newRound();
                }
                else
                    currentState = GameState.SCORESCREEN;
                break;

            case SCORESCREEN:
                if (keyboard.isPressed(KeyEvent.VK_SPACE)) {
                    gameLoop.stop();
                    ended = true;
                }
            break;
        }
    }

    public void input() {
        for (Worm w : wormList) {
            if (keyboard.isPressed(w.getControllerKeys()[0]) && keyboard.isPressed(w.getControllerKeys()[1])) {
                w.setTurn('F');
            }
            else if (keyboard.isPressed(w.getControllerKeys()[0]))
                w.setTurn('L');
            else if (keyboard.isPressed(w.getControllerKeys()[1]))
                w.setTurn('R');
            else
                w.setTurn('F');
            if (keyboard.isPressed(w.getControllerKeys()[2])) {
		        w.activateAbility();
            }
        }
    }

    public void collisionDetection(){
        for (Worm w : wormList) {
            if (w.isAlive()) {
                Color temp = w.getCollision();
                if (temp != null) {
                    for (Worm w1 : wormList) {
                        if (w1 != w && temp.equals(w1.getColor())) {
                            w1.addPoint();
                        }
                    }
                    givePoints();
                    score.updateScore(wormList);
                }
            }
        }
    }

    private void givePoints() {
        for (Worm w : wormList) {
            if (w.isAlive()) w.addPoint();
        }
    }

    private boolean isEndOfRound() {
        byte aliveCount = 0;
        for (Worm w : wormList) {
            if (w.isAlive())
            aliveCount++;
        }
        if (aliveCount > 1)
            return false;
        return true;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
	    draw(g);
    }

    protected void draw(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)backBuffer.getGraphics();
        backBuffer.Draw(g);
        for (Worm w : wormList){
            w.draw(g2);
            w.drawHead((Graphics2D)g);
        }
    }

    @Override
    public void run(){
        gameLoop.start();
    }

    public void addWorm(Worm w) {
        wormList.add(w);
    }

    protected void newRound() {
        backBuffer.clear();
        currentRound++;
        moveAtStart = 0;
        calculateWormPositions();
        currentState = GameState.INTERMISSION;
    }

    private void calculateWormPositions() {
        int margin = 100;

        for (Worm w : wormList) {
            if (!w.equals(wormList.get(0))) {
                Position p = new Position(Randomizer.next(margin,Settings.viewPort.getWidth()-margin),Randomizer.next(margin,Settings.viewPort.getHeight()-margin));
                while (isTooClose(p)) {
                    p = new Position(Randomizer.next(margin,Settings.viewPort.getWidth()-margin),Randomizer.next(margin,Settings.viewPort.getHeight()-margin));
                }
                w.setPos(p);
            }
            else {
                w.setPos(new Position(Randomizer.next(margin,Settings.viewPort.getWidth()-margin),Randomizer.next(margin,Settings.viewPort.getHeight()-margin)));
            }
            w.setAngle((float)Randomizer.next(0.0,Math.PI*2));

        }
    }

    private boolean isTooClose(Position p) {
        int distance = 50;
        for (Worm w : wormList) {
            if (Math.sqrt(Math.pow(p.getX()-w.getPos().getX(), 2) + Math.pow(p.getY()-w.getPos().getY(), 2)) < distance) {
                return true;
            }
        }
        return false;
    }

    public void initializeGame(byte r) {
        rounds = r;
        currentRound = 1;
        newRound();
        score = new ScoreBoard(wormList);
        this.add(score);

    }

    public boolean hasEnded() {
        return ended;
    }

}
