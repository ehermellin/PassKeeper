package passkeeper.model;

import java.util.Vector;

public class PassKeeperService {

    /**
     *
     */
    private static PassKeeperService instance;

    /**
     *
     */
    private PassKeeperModel passKeeperModel;

    /**
     *
     */
    private Vector<PassKeeperObject> passKeeperObjects;

    /**
     *
     * @return
     */
    public PassKeeperModel getPassKeeperModel() {
        return passKeeperModel;
    }

    /**
     *
     */
    private PassKeeperService() {
        passKeeperModel = new PassKeeperModel(initPassKeeperObjects());
    }

    /**
     *
     * @return
     */
    public static synchronized PassKeeperService getInstance() {
        if (instance == null) {
            instance = new PassKeeperService();
        }
        return instance;
    }

    /**
     *
     * @return
     */
    public synchronized Vector<PassKeeperObject> initPassKeeperObjects() {
        if (passKeeperObjects != null) {
            return passKeeperObjects;
        } else {
            passKeeperObjects = new Vector<>();
            return passKeeperObjects;
        }
    }

    /**
     * TODO fusionner avec loadFromString
     * @return
     */
    public synchronized void loadPassKeeperObjects(String data) {
        loadFromString(data);
    }

    /**
     *
     */
    private void loadFromString(final String data) {
        passKeeperObjects = new Vector<>();
        /* addPassKeeperObject()...*/
    }

    /**
     *
     * @return
     */
    public String getPassKeeperModelToString() {
        return this.passKeeperModel.toString();
    }
}
