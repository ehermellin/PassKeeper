package passkeeper.gui.tools;

import java.awt.Point;
import java.io.Serializable;

/**
 * This class implements the WindowsManager class of <code>PassKeeper</code>.
 * This class provides functions to manage windows in PassKeeper.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class WindowsManager implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The origin point of Passkeeper.
     */
    private static Point location;

    /**
     * The width of PassKeeper main frame.
     */
    private static int width = 580;

    /**
     * The height of PassKeeper main frame.
     */
    private static int height = 580;

    /**
     * The marging of PassKeeper main frame.
     */
    private static int marging = 10;

    /**
     * The title of PassKeeper main frame.
     */
    public static final String NAME = "PassKeeper";

    /**
     * Sets the origin point of PassKeeper.
     *
     * @param location the origin point of PassKeeper.
     */
    public static void setPoint(Point location) {
        WindowsManager.location = location;
    }

    /**
     * Gets the width of PassKeeper main frame.
     *
     * @return the width of PassKeeper main frame.
     */
    public static int getWidth() {
        return WindowsManager.width;
    }

    /**
     * Gets the height of PassKeeper main frame.
     *
     * @return the height of PassKeeper main frame.
     */
    public static int getHeight() {
        return WindowsManager.height;
    }

    /**
     * Gets the marging of PassKeeper main frame.
     *
     * @return the marging of PassKeeper main frame.
     */
    public static int getMarging() {
        return WindowsManager.marging;
    }

    /**
     * Return new location for windows according to location point, width parameter and margin.
     *
     * @return new location point.
     */
    public static Point setWindowsLocationWidth() {
        return new Point((int) WindowsManager.location.getX() + WindowsManager.width + WindowsManager.marging,
                (int) WindowsManager.location.getY());
    }
}
