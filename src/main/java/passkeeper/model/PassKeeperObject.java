package passkeeper.model;

import java.io.Serializable;

public class PassKeeperObject implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String id;

    /**
     *
     */
    private String login;

    /**
     *
     */
    private String password;

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @param id
     * @param login
     * @param password
     */
    public PassKeeperObject(final String id, final String login, final String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
}
