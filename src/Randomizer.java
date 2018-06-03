import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Fredrik
 * Date: 2013-09-30
 * Time: 18:41
 * To change this template use File | Settings | File Templates.
 */
public final class Randomizer {

    public static int next(int min, int max) {
        return new Random().nextInt(max-min)+min;
    }

    public static double next(double min, double max) {
        return new Random().nextDouble()*(max-min) + min;
    }




}
