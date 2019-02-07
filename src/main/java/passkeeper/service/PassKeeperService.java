package passkeeper.service;

import passkeeper.PassKeeper;
import passkeeper.model.PassKeeperModel;

import java.io.Serializable;

/**
 * This class implements the PassKeeperService class of <code>PassKeeper</code>.
 * This is the service used to handle the PassKeeperModel
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class PassKeeperService implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The instance of the service.
     */
    private static PassKeeperService instance;

    /**
     * The data model.
     */
    private PassKeeperModel passKeeperModel;

    /**
     * Returns the PassKeeperModel.
     *
     * @return the PassKeeperModel.
     */
    public PassKeeperModel getPassKeeperModel() {
        return passKeeperModel;
    }

    /**
     * Creates a new PassKeeperService.
     */
    private PassKeeperService() {
        this.passKeeperModel = new PassKeeperModel();
        if (PassKeeper.isLOG()) {
            System.out.println("[PassKeeperService] Creates PasskeeperModel");
        }
    }

    /**
     * Returns the instance of the PassKeeperService.
     *
     * @return the instance of the PassKeeperService.
     */
    public static synchronized PassKeeperService getInstance() {
        if (PassKeeperService.instance == null) {
            if (PassKeeper.isLOG()) {
                System.out.println("[PassKeeperService] Creates PassKeeperService");
            }
            PassKeeperService.instance = new PassKeeperService();
        }
        return PassKeeperService.instance;
    }

    /**
     * Loads the data from a String into the PassKeeperModel.
     */
    public synchronized void loadPassKeeperObjects(String data) {
        this.clearPassKeeperModel();

        final String[] lines = data.split(";");
        if (lines.length % 3 == 0) {
            if (PassKeeper.isLOG()) {
                System.out.println("[PassKeeperService] Loads data into data model (" + lines.length / 3 + " rows)");
            }
            for (int i = 0; i < lines.length; i = i + 3) {
                this.passKeeperModel.addRow(lines[i], lines[i + 1], lines[i + 2]);
            }
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[PassKeeperService] Can't load data into data model (bad data format)");
            }
        }
    }

    /**
     * Clear the PassKeeperModel.
     */
    public void clearPassKeeperModel() {
        if (PassKeeper.isLOG()) {
            System.out.println("[PassKeeperService] Clears the PassKeeperModel");
        }
        this.passKeeperModel.clearModel();
    }

    /**
     * Return a String version of the PassKeeperModel.
     *
     * @return a String version of the PassKeeperModel.
     */
    public String getPassKeeperModelToString() {
        if (PassKeeper.isLOG()) {
            System.out.println("[PassKeeperService] Gets data model as String");
        }
        return this.passKeeperModel.toString();
    }
}
