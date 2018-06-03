import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 26/09/13
 * Time: 12:34
 * To change this template use File | Settings | File Templates.
 */
public class PlayerMenyItem extends JPanel {

    PlayerMenyItem me;

    //player schöbens
    private boolean isPlaying;
    private JCheckBox playing;
    ArrayList<Color> colorList = new ArrayList<Color>();
    Keyboard keyboard;
    JLabel title;
    JButton left,right,spec;
    JComboBox<String> setColor;
    JComboBox<Ability> setAbility;
    Worm worm;
    enum Key {Left, Right, Special, None}
    Key key = Key.Left;

    public Worm getWorm() {
        return worm;
    }
    public void setSelected(boolean tr){
	    playing.setSelected(tr);
    }
    public boolean isPlaying() {
        return isPlaying;
    }

    public void addColors(){
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.YELLOW);
        colorList.add(Color.GREEN);
        colorList.add(Color.MAGENTA);
        colorList.add(Color.PINK);
        colorList.add(Color.GRAY);
        colorList.add(Color.CYAN);
        colorList.add(Color.WHITE);
        setColor.addItem("Red");
        setColor.addItem("Blue");
        setColor.addItem("Yellow");
        setColor.addItem("Green");
        setColor.addItem("Magenta");
        setColor.addItem("Pink");
        setColor.addItem("Gray");
        setColor.addItem("Cyan");
        setColor.addItem("White");

        setAbility.addItem(new Ghost());
        setAbility.addItem(new Missile());
        setAbility.addItem(new Spikes());
        setAbility.addItem(new Speed(worm));
        setAbility.addItem(new PathClearing());

    }

    public void setPlaying(final boolean playing) {
	    isPlaying = playing;
    }


    public PlayerMenyItem(int x, int y, int w, int h, int playerNr) {
        this.setLayout(null);

        me = this;
        worm = new Worm();

        this.setBounds(x, y, w, h);
        this.setBorder(new LineBorder(Color.BLACK));
        this.setForeground(Color.WHITE);
        this.setVisible(true);
        this.setFocusable(true);


        int margins = this.getHeight()/15; //15 and 6 is calculated ratios to maken the
        int heightTemp = this.getHeight()/6;

        byte n = 1;
        //Playing checkbox
        playing = new JCheckBox();
        playing.setBounds(w/5,n*margins+(n-1)*heightTemp,heightTemp,heightTemp); //want a square box, hence heightTemp in both width and height
        playing.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPlaying = !isPlaying;
            }
        });

        //Title
        title = new JLabel("Player " + playerNr);
        title.setBounds(w/2,n*margins+(n-1)*heightTemp, w - (2 * margins),heightTemp);
        title.setVisible(true);
        this.add(title);

        n++;
        //Color chooser
        setColor = new JComboBox<String>();
        setColor.setBounds(margins, n*margins+(n-1)*heightTemp, w - (2 * margins), heightTemp);
        setColor.addItemListener(colorListener);

        n++;
        //Ability chooser
        setAbility = new JComboBox<Ability>();
        setAbility.setBounds(margins, n*margins+(n-1)*heightTemp, w - (2 * margins), heightTemp);
        setAbility.addItemListener(abilityListener);
        addColors();


        n++;
        //Left and right buttons
        left = new JButton("Left");
        left.setBounds(margins,n*margins+(n-1)*heightTemp,(w-4*margins)/3,heightTemp);
        left.addActionListener(leftListener);

        right = new JButton("Right");
        right.setBounds((w-margins)-((w-4*margins)/3),n*margins+(n-1)*heightTemp,(w-4*margins)/3,heightTemp);
        right.addActionListener(rightListener);

        spec = new JButton("Spec");
        spec.setBounds(margins*2+(w-4*margins)/3,n*margins+(n-1)*heightTemp,(w-4*margins)/3,heightTemp);
        spec.addActionListener(specListener);






        //Adds the different JPanels etc.
        this.add(left);
        this.add(right);
        this.add(setColor);
        this.add(playing);
        this.add(spec);
        this.add(setAbility);

        worm.setColor(colorList.get(setColor.getSelectedIndex()));
        worm.setAbility((Ability)setAbility.getSelectedItem());
        keyboard = new Keyboard();
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (key) {
                    case Left:
                        left.setText(Character.toString(e.getKeyChar()));
                        worm.setKey(e.getKeyCode(),(byte)0);
                        left.setBackground(null);
                        break;

                    case Right:
                        right.setText(Character.toString(e.getKeyChar()));
                        worm.setKey(e.getKeyCode(),(byte)1);
                        right.setBackground(null);
                        break;

                    case Special:
                        spec.setText(Character.toString(e.getKeyChar()));
                        worm.setKey(e.getKeyCode(),(byte)2);
                        spec.setBackground(null);
                        break;
                }
                key = Key.None;

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

    }


    ActionListener leftListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            left.setText("Pick Key");
            key = Key.Left;
            me.grabFocus();
            left.setBackground(Color.CYAN);

        }
    };
    ActionListener specListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            spec.setText("Pick Key");
            key = Key.Special;
            me.grabFocus();
            spec.setBackground(Color.CYAN);
        }
    };

    ActionListener rightListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            right.setText("Pick Key");
            key = Key.Right;
            me.grabFocus();
            right.setBackground(Color.CYAN);
        }
    };

    ItemListener abilityListener = new ItemListener(){
        @Override
        public void itemStateChanged(ItemEvent e) {
            worm.setAbility((Ability) setAbility.getSelectedItem());
        }
    };


    ItemListener colorListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            worm.setColor(colorList.get(setColor.getSelectedIndex()));
        }
    };
}
