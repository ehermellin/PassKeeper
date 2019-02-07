package passkeeper.service;

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
        passKeeperModel = new PassKeeperModel();
    }

    /**
     * Returns the instance of the PassKeeperService.
     *
     * @return the instance of the PassKeeperService.
     */
    public static synchronized PassKeeperService getInstance() {
        if (instance == null) {
            instance = new PassKeeperService();
        }
        return instance;
    }

    /**
     * Loads the data from a String into the PassKeeperModel.
     */
    public synchronized void loadPassKeeperObjects(String data) {
        final String[] lines = data.split(";");
        if (lines.length % 3 == 0) {
            System.out.println();
            for (int i = 0; i < lines.length; i = i + 3) {
                passKeeperModel.addRow(lines[i], lines[i + 1], lines[i + 2]);
            }
        } else {
            System.out.println();
        }
    }

    /**
     * Return a String version of the PassKeeperModel.
     *
     * @return a String version of the PassKeeperModel.
     */
    public String getPassKeeperModelToString() {
        return this.passKeeperModel.toString();
    }
}
