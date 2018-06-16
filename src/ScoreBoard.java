import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA. User: frela412 Date: 10/4/13 Time: 4:57 PM To change this template use File | Settings | File
 * Templates.
 */
public class ScoreBoard extends JPanel
{
    private JLabel[] labelList;
    private ArrayList<Worm> wormList;


    public ScoreBoard(ArrayList<Worm> wormList){
        this.wormList = wormList;
        this.setBounds((int)(Settings.ScreenSize.getWidth()- Settings.scoreBoardSize.getWidth()),0,(int)Settings.scoreBoardSize.getWidth(),(int)Settings.scoreBoardSize.getHeight());

        this.setLayout(null);
        this.setVisible(true);
        this.setFocusable(true);

        labelList = new JLabel[wormList.size()];
        int margins = (int)Settings.ScreenSize.getHeight()/10;
        int tempHeight = (int)Settings.ScreenSize.getHeight()/8;
        for (int i = 0; i < wormList.size(); i++) {
            labelList[i] = new JLabel();
            if (wormList.get(i) instanceof WormBot)
                labelList[i].setText("AI" + (i + 1) + ": 0");
            else if (wormList.get(i) instanceof Worm)
                labelList[i].setText("Player" + (i + 1) + ": 0");
	    int width = 200;
	    int height = 100;
            labelList[i].setBounds(10,(i+1)*margins+i*tempHeight, width, height);
	    int fontSize = 30;
            labelList[i].setFont(new Font("Franklin Gothic Demi", Font.BOLD, fontSize));
            labelList[i].setForeground(wormList.get(i).getColor());
            labelList[i].setVisible(true);
            labelList[i].grabFocus();
            this.add(labelList[i]);
        }

        //try to set background
        BufferedImage back;
        try {
            back = ImageIO.read(new File("textures\\ijtexture.jpg"));
            JLabel pic = new JLabel(new ImageIcon(back));
            pic.setVisible(true);
            pic.setBounds(0,0,this.getWidth(),this.getHeight());
            this.add(pic);
        }
        catch (IOException ex) {
	    System.out.println("lyckades ej ladda bakgrundsbild");
	}
    }

    public Worm winner() {
        return wormList.get(0);
    }



    public void updateScore(ArrayList<Worm> wormList){ //the wormlist is an arraylist, not a java.util.list, but im not sure of the differences.
        for (int i = 0; i < labelList.length ; i++) {
            if (wormList.get(i) instanceof WormBot)
                labelList[i].setText("AI: " + wormList.get(i).getPoints());
            else if (wormList.get(i) instanceof Worm)
                labelList[i].setText("Player: " + wormList.get(i).getPoints());
        }
    }
}
