import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 08/10/13
 * Time: 09:24
 * To change this template use File | Settings | File Templates.
 */
public class WormWindow extends JPanel {
    Position center;
    AffineTransform transform;
    double zoom;
    Worm worm;
    Position wormPos;
    BackBuffer back;

    public WormWindow(int players, int nr, Worm w, BackBuffer img) {
        worm = w;
        wormPos = worm.getPos();
        back = img;
        this.setLayout(null);

        if (players == 2) {
            if (nr == 1)
                this.setBounds(0,0,Settings.viewPort.width/2,Settings.viewPort.height);
            else
                this.setBounds(Settings.viewPort.width/2,0,Settings.viewPort.width/2,Settings.viewPort.height);
        }
        else {
            switch (nr) {
                case 1:
                    this.setBounds(0,0,Settings.viewPort.width/2,Settings.viewPort.height/2);
                    break;

                case 2:
                    this.setBounds(Settings.viewPort.width/2,0,Settings.viewPort.width/2,Settings.viewPort.height/2);
                    break;

                case 3:
                    this.setBounds(0,Settings.viewPort.height/2,Settings.viewPort.width/2,Settings.viewPort.height/2);
                    break;

                case 4:
                    this.setBounds(Settings.viewPort.width/2,Settings.viewPort.height/2,Settings.viewPort.width/2,Settings.viewPort.height/2);
                    break;
            }
        }


        this.setVisible(true);
        this.setBackground(new Color(0,0,0,0));
        this.setBorder(new LineBorder(Color.WHITE));

	zoom = 0.8;
    }

    @Override
    public void paintComponent(Graphics g) {
        wormPos = worm.getPos();


        Graphics2D g2 = (Graphics2D)g;
        AffineTransform old = g2.getTransform();

        //create matrix
        transform = new AffineTransform();
        transform.translate(this.getX() + this.getWidth()/2.0d,this.getY() + this.getHeight()/1.5d);
        transform.rotate(-worm.getAngle()-Math.PI/2);
        transform.scale(zoom,zoom);
        g2.setTransform(transform);

        //draw rotated and scaled image
        g2.drawImage(back.getScreen(), -(int)wormPos.getX(), - (int)wormPos.getY(), null);

        //restore
        g2.setTransform(old);

        //draw head
        g.setColor(Color.WHITE);
	Position wormHeadPos = new Position(this.getWidth()/2.0-worm.getSize()/2f,this.getHeight()/1.5-worm.getSize()/2f); //calculate the correct position for the head
        g.fillOval((int)wormHeadPos.getX(),(int)wormHeadPos.getY(),(int)(worm.getSize()*zoom),(int)(worm.getSize()*zoom));
    }
}
