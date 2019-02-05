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
    private Vector<PassKeeperObject> passKeeperObjects;

    /**
     *
     */
    private PassKeeperService() {}

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
    public synchronized Vector<PassKeeperObject> loadPassKeeperObjectList() {
        if (passKeeperObjects != null) {
            return passKeeperObjects;
        } else {
            passKeeperObjects = new Vector<>();
            addPassKeeperObject("test1","test1", "test1");
            addPassKeeperObject("test2","test2", "test2");
            addPassKeeperObject("test3","test3", "test3");
            return passKeeperObjects;
        }
    }

    /**
     *
     * @return
     */
    public synchronized Vector<PassKeeperObject> loadPassKeeperObjectList(String data) {
        loadFromString(data);
        return passKeeperObjects;
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
     * @param id
     * @param login
     * @param password
     */
    private void addPassKeeperObject(final String id, final String login, final String password) {
        this.passKeeperObjects.add(new PassKeeperObject(id, login, password));
    }

    /**
     *
     * @return
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (PassKeeperObject passKeeperObject : passKeeperObjects) {
            stringBuilder.append(passKeeperObject.getId()).append("|");
            stringBuilder.append(passKeeperObject.getLogin()).append("|");
            stringBuilder.append(passKeeperObject.getPassword()).append("\n");
        }
        return stringBuilder.toString();
    }
}
