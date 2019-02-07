package passkeeper;

import com.pagosoft.plaf.PlafOptions;
import passkeeper.gui.LogFrame;
import passkeeper.gui.MainFrame;

import java.io.Serializable;

/**
 * This class implements the main class of <code>PassKeeper</code>.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class PassKeeper implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The status for logging PassKeeper.
     */
    private static boolean LOG = false;

    /**
     * Returns the status of the LOG.
     *
     * @return the status of the LOG.
     */
    public static boolean isLOG() {
        return LOG;
    }

    /**
     * Sets the status of the LOG in PassKeeper.
     */
    private static void setLOG() {
        PassKeeper.LOG = true;
    }

    /**
     * Creates a new main GUI.
     */
    private PassKeeper(final String[] args) {
        //TODO Exception and error output
        new MainFrame();
        if (args.length > 0 && args[0].equals("-LOG")) {
            PassKeeper.setLOG();
            new LogFrame();
            if (PassKeeper.isLOG()) {
                System.out.println("[PassKeeper] Launches PassKeeper with LOG");
            }
        }
    }

    /**
     * The PassKeeper main method of <code>PassKeeper</code>.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        PlafOptions.setAsLookAndFeel();
        PlafOptions.updateAllUIs();

        new PassKeeper(args);
    }
}
