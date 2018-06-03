import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: JOHANOVIC
 * Date: 02/10/13
 * Time: 08:55
 * To change this template use File | Settings | File Templates.
 */
public final class Settings {

    public static int baseHolesize = 15;
    public static int baseHoleDistance = 250;

    public static Dimension ScreenSize = new Dimension(1300,750); //new screen with width 1300 and height 750
    public static Dimension scoreBoardSize = new Dimension(200,ScreenSize.height); //the scoreboard should just be a small part of the screen, therefor the width of 200
    public static Rectangle viewPort = new Rectangle(0,0,ScreenSize.width - scoreBoardSize.width,ScreenSize.height);

    public static BackBuffer backBuffer = BackBuffer.getInstance();




}
