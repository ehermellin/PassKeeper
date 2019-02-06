package passkeeper.model;

import java.io.Serializable;

/**
 * This class implements the PassKeeperObject class of <code>PassKeeper</code>.
 * This is the object used in the PassKeeperModel.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class PassKeeperObject implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The id.
     */
    private String id;

    /**
     * The login.
     */
    private String login;

    /**
     * The password.
     */
    private String password;

    /**
     * Returns the id.
     *
     * @return the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the login.
     *
     * @return the login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the login.
     *
     * @param login the login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Returns the password.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     *
     * @param password the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Creates a new PassKeeperObject.
     */
    public PassKeeperObject() {
        this.id = "id";
        this.login = "login";
        this.password = "password";
    }

    /**
     * Creates a new PassKeeperObject.
     * @param id       the id.
     * @param login    the login.
     * @param password the password.
     */
    public PassKeeperObject(final String id, final String login, final String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
