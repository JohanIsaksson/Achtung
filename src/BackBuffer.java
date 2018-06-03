import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 22/09/13
 * Time: 00:10
 * To change this template use File | Settings | File Templates.
 */
public class BackBuffer {
    public static volatile BackBuffer instance = new BackBuffer();

    private BufferedImage screen;

    private BackBuffer() {
        screen = new BufferedImage((int)Settings.viewPort.getWidth(),(int)Settings.viewPort.getHeight(),BufferedImage.TYPE_INT_ARGB);
        this.clear();
    }

    public static BackBuffer getInstance() {
        return instance;
    }


    public Graphics getGraphics() {
	    return screen.getGraphics();
    }

    public BufferedImage getScreen(){
        return screen;
    }

    public boolean isOutOfBounds(int x, int y) {
        if (x < 0 || y < 0 || x >= Settings.viewPort.getWidth() || y >= Settings.viewPort.getHeight())
            return true;
        return false;
    }

    public void clear() {
        for (int x = 0; x < screen.getWidth(); x++) {
            for (int y = 0; y < screen.getHeight(); y++) {
                screen.setRGB(x,y,Color.BLACK.getRGB());
            }
        }
    }

    public void Draw(Graphics g) {
        g.drawImage(screen, (int)Settings.viewPort.getX(), (int)Settings.viewPort.getY(), (int)Settings.viewPort.getWidth(), (int)Settings.viewPort.getHeight(), null);
    }



}
