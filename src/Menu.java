
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: Serdiev
 * Date: 2013-09-12
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class Menu extends JPanel{
    Menu me;
    JFrame gameWindow;
    byte rounds = 6;
    int margins, layer;
    JSlider angleSlider;
    JSlider speedSlider;
    JSlider holeSizeSlider;
    JSlider holeRandomSlider;
    JSlider roundsSlider;
    JButton playNormalButton,playSplitButton;
    ActionListener normal,split;
    ArrayList<PlayerMenyItem> players;

    Timer waitForEnd;
    ActionListener wait;

    Game game;


    public void addSliders(){

        // Speed and angle slider and configurations. We set the minimum to 3 so it's not 0 ( then the worm would not move).
        // The ticks are 3 to 60 with starting position 30, so it looks the slider is about in the middle.

        speedSlider = new JSlider();
        angleSlider = new JSlider();
        roundsSlider = new JSlider();
        holeRandomSlider = new JSlider();
        holeSizeSlider = new JSlider();


        //set position and size off sliders relatively too the screen size
	float widthRatio = 47f/250f;
        int tempWidth = (int)(Settings.ScreenSize.getWidth()*widthRatio);
	int tempHeight = 200;
        int n = 1;
        speedSlider.setBounds(n*margins+(n-1)*tempWidth,layer,tempWidth,tempHeight);
        n++;
        angleSlider.setBounds(n*margins+(n-1)*tempWidth,layer,tempWidth,tempHeight);
        n++;
        roundsSlider.setBounds(n*margins+(n-1)*tempWidth,layer,tempWidth,tempHeight);
        n++;
        holeSizeSlider.setBounds(n*margins+(n-1)*tempWidth,layer,tempWidth,tempHeight);
        n++;
        holeRandomSlider.setBounds(n*margins+(n-1)*tempWidth,layer,tempWidth,tempHeight);



        //speedSlider config
	int startValue = 30;

        speedSlider.setMinimum(3);
        speedSlider.setMajorTickSpacing(3);
        speedSlider.setSnapToTicks(true);
        speedSlider.setValue(startValue);
        speedSlider.setPaintTicks(true);
        speedSlider.setOrientation(JLabel.VERTICAL);
        speedSlider.setMaximum(60);
        Hashtable speedLabel = new Hashtable();
        speedLabel.put(60,new JLabel("Double Speed"));
        speedLabel.put(startValue,new JLabel("Normal Speed"));
        speedLabel.put(3,new JLabel("Really Slow"));
        speedSlider.setLabelTable(speedLabel);
        speedSlider.setPaintLabels(true);
        speedSlider.setBorder(new LineBorder(Color.BLACK));

        //angleSlider config
        angleSlider.setMaximum(60);
        angleSlider.setMinimum(3);
        angleSlider.setMajorTickSpacing(3);
        angleSlider.setSnapToTicks(true);
        angleSlider.setValue(startValue);
        angleSlider.setPaintTicks(true);
        angleSlider.setOrientation(JLabel.VERTICAL);
        Hashtable angleLabel = new Hashtable();
        angleLabel.put(60,new JLabel("Fast turn"));
        angleLabel.put(startValue,new JLabel("Normal turn"));
        angleLabel.put(3, new JLabel("Slow turn"));
        angleSlider.setLabelTable(angleLabel);
        angleSlider.setPaintLabels(true);
        angleSlider.setBorder(new LineBorder(Color.BLACK));

        //roundsSlider config
	startValue = 15;
        roundsSlider.setMaximum(30);
        roundsSlider.setMinimum(1);
        roundsSlider.setMinorTickSpacing(1);
        roundsSlider.setSnapToTicks(true);
        roundsSlider.setValue(startValue);
        roundsSlider.setPaintTicks(true);
        roundsSlider.setOrientation(JLabel.VERTICAL);
        Hashtable roundsLabel = new Hashtable();
        roundsLabel.put(30,new JLabel("Thirty rounds"));
        roundsLabel.put(startValue,new JLabel("Fifteen rounds"));
        roundsLabel.put(1,new JLabel("One round"));
        roundsSlider.setLabelTable(roundsLabel);
        roundsSlider.setPaintLabels(true);
        roundsSlider.setBorder(new LineBorder(Color.BLACK));

        //holeSlider config
        holeSizeSlider.setMaximum(35);
        holeSizeSlider.setMinimum(5);
        holeSizeSlider.setMinorTickSpacing(1);
        holeSizeSlider.setSnapToTicks(true);
        holeSizeSlider.setValue(startValue);
        holeSizeSlider.setPaintTicks(true);
        holeSizeSlider.setOrientation(JLabel.VERTICAL);
        Hashtable holeSizeLabel = new Hashtable();
        holeSizeLabel.put(35,new JLabel("Large hole"));
        holeSizeLabel.put(startValue,new JLabel("Normal hole"));
        holeSizeLabel.put(5,new JLabel("Will fit a worm"));
        holeSizeSlider.setLabelTable(holeSizeLabel);
        holeSizeSlider.setPaintLabels(true);
        holeSizeSlider.setBorder(new LineBorder(Color.BLACK));

        //holeRandomSlider config
        holeRandomSlider.setMaximum(35);
        holeRandomSlider.setMinimum(1);
        holeRandomSlider.setMinorTickSpacing(1);
        holeRandomSlider.setSnapToTicks(true);
        holeRandomSlider.setValue(startValue);
        holeRandomSlider.setPaintTicks(true);
        holeRandomSlider.setOrientation(JLabel.VERTICAL);
        Hashtable holeRandomLabel = new Hashtable();
        holeRandomLabel.put(35,new JLabel("Very random"));
        holeRandomLabel.put(startValue,new JLabel("Random"));
        holeRandomLabel.put(1,new JLabel("Not random at all"));
        holeRandomSlider.setLabelTable(holeRandomLabel);
        holeRandomSlider.setPaintLabels(true);
        holeRandomSlider.setBorder(new LineBorder(Color.BLACK));


    }

    public Menu() {
        me = this;
        gameWindow = new JFrame("Achtung");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    //Vi adderar 32 pixlar för skillnaden mellan unix och windows
        gameWindow.setPreferredSize(new Dimension((int) Settings.ScreenSize.getWidth(),(int) Settings.ScreenSize.getHeight()+32));
        gameWindow.setVisible(true);
        gameWindow.requestFocus();
        gameWindow.setBackground(Color.DARK_GRAY);
        gameWindow.setResizable(false);

        this.setLayout(null);
        this.setVisible(true);
        this.requestFocus();


        wait = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game.hasEnded()) {
                    gameWindow.remove(game);
                    gameWindow.add(me);
                    repaint();
                    waitForEnd.stop();
                    me.grabFocus();
                    me.setVisible(true);
                }
            }
        };
        waitForEnd = new Timer(1000,wait);

        margins = (int)Settings.ScreenSize.getWidth()/100;
        layer = margins*2;

        // Adding buttons to JPanels.
        addSliders();
        layer += speedSlider.getHeight();


        //add PlayerMenuItems
        int heightTemp = (int)Settings.ScreenSize.getHeight()/5;
	float widthRatio = 19f/80f;
        int widthTemp = (int)(Settings.ScreenSize.getWidth()*widthRatio);
        layer += margins*2;
        players = new ArrayList<PlayerMenyItem>();
	int worms = 5;
        for (int i = 1; i < worms; i++) {
            players.add(new PlayerMenyItem(i*margins+(i-1)*widthTemp,layer,widthTemp,heightTemp,i));
            this.add(players.get(i-1));
        }
        //Sets player 1 and 2 to play from the start.
        players.get(0).setPlaying(true);
        players.get(0).setSelected(true);
        players.get(1).setPlaying(true);
        players.get(1).setSelected(true);

        layer += heightTemp + margins*8;

        //put playbuttons depending on size
        margins = (int)Settings.ScreenSize.getWidth()/5;
        widthTemp = margins;
        byte n = 1;

        playNormalButton = new JButton("Play Normal Mode");
        playNormalButton.setBounds(n*margins+(n-1)*widthTemp,layer,widthTemp,heightTemp);
        n++;

        playSplitButton = new JButton("Play SplitScreen Mode");
        playSplitButton.setBounds(n*margins+(n-1)*widthTemp,layer,widthTemp,heightTemp);

        //add everything to frame
        this.add(playNormalButton);
        this.add(playSplitButton);
        this.add(speedSlider);
        this.add(angleSlider);
        this.add(roundsSlider);
        this.add(holeSizeSlider);
        this.add(holeRandomSlider);

        split = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create and fix
                game = new SplitScreenGame();
                initializeGame();
                game.run();
            }
        };
        normal = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game = new Game();
                initializeGame();
                game.run();
            }
        };

        // Listeners
        playSplitButton.setVisible(true);
        playSplitButton.addActionListener(split);
        playNormalButton.setVisible(true);
        playNormalButton.addActionListener(normal);

        //try to set background
        BufferedImage back;
        try {
            back = ImageIO.read(new File("textures\\bg.jpg"));
            JLabel pic = new JLabel(new ImageIcon(back));
            pic.setVisible(true);
            pic.setBounds(0, 0, Settings.ScreenSize.width,Settings.ScreenSize.height);
            this.add(pic);
        }
        catch (IOException ex) {
        }

        gameWindow.add(this);
	    gameWindow.pack();
    }

    private void initializeGame() {
        game.setVisible(true);
        game.setPreferredSize(Settings.ScreenSize);

        rounds = (byte)roundsSlider.getValue();

        for (PlayerMenyItem player : players) {
            if (player.isPlaying())
                game.addWorm(player.getWorm());
        }

        //------------------------------------------------------------------------------- ai här

        Worm ai = new WormBot();
        ai.setColor(Color.cyan);
        ai.setAbility(new Ghost());
        ai.setTurningSpeed(angleSlider.getValue());
        game.addWorm(ai);


        initializeWorms();

        me.setVisible(false);
        gameWindow.remove(me);
        gameWindow.add(game);

        game.requestFocus();
        game.initializeGame(rounds);
        waitForEnd.start();
    }

    private void initializeWorms() {
        for (Worm w : game.wormList) {
	    float ratio = 10f;
            w.setMovementSpeed(speedSlider.getValue()/ ratio);
	    ratio = 600f;
            w.setTurningSpeed(angleSlider.getValue()/ ratio);
            w.setHoleDistance(holeRandomSlider.getValue());
            w.setHoleSize(holeSizeSlider.getValue());
            w.setPoints((byte)0);
        }
    }
}
