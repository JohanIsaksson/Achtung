import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
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
public class SplitScreenGame extends Game {

    ArrayList<WormWindow> wormWindows;

    public SplitScreenGame() {
        super();
        wormWindows = new ArrayList<WormWindow>();
        this.setBackground(new Color(55,57,60)); //the absolutly perfect grey with red=55, green=57, blue=60
    }

    @Override
    protected void draw(Graphics g){
	    Graphics2D g2 = (Graphics2D)backBuffer.getGraphics();
        for (Worm w : wormList){
            w.draw(g2);
        }

    }

    @Override
    public void initializeGame(byte r) {
        rounds = r;
        currentRound = 1;
        newRound();
        for (int i = 0; i < wormList.size(); i++) {
            wormWindows.add(new WormWindow(wormList.size(),i+1,wormList.get(i),backBuffer));
            this.add(wormWindows.get(i));
        }

        score = new ScoreBoard(wormList);
        this.add(score);

    }

}
