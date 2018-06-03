
/**
 * Created with IntelliJ IDEA. User: johis024 Date: 2013-09-24 Time: 16:14 To change this template use File | Settings | File
 * Templates.
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
 
 
public class Keyboard extends KeyAdapter
{
    private ArrayList<Integer> pushedKeys;
 
    public Keyboard() {
        pushedKeys = new ArrayList<Integer>();
    }
 

    public boolean isPressed(int key) {
        for (int k : pushedKeys) {
            if (k == key) return true;
        }
        return false;
    }
 
    @Override
    public void keyTyped(final KeyEvent e) {
    //To change body of implemented methods use File | Settings | File Templates.
    }
 
    @Override
    public void keyPressed(final KeyEvent e) {
    //add all pushed keys
        for (Integer pushedKey : pushedKeys) {
            if (pushedKey == e.getKeyCode()) {
                break;
            }
        }
        pushedKeys.add(e.getKeyCode());
    }
 
    @Override
    public void keyReleased(final KeyEvent e) {
        for (Integer key : pushedKeys) {
                if (e.getKeyCode() == key)
                    pushedKeys.set(pushedKeys.indexOf(key),KeyEvent.VK_NONCONVERT);

            }

        int i = 0;
        while (i < pushedKeys.size()) {
            if (pushedKeys.get(i) == KeyEvent.VK_NONCONVERT)
                pushedKeys.remove(i);
            else
                i++;
        }

    //To change body of implemented methods use File | Settings | File Templates.
    }
}