package passkeeper.model;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

/**
 * This class implements the PassKeeperModel class of <code>PassKeeper</code>.
 * This is the data model used in the JTable.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class PassKeeperModel extends AbstractTableModel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The title of each column.
     */
    private final String[] columnNames = { "ID", "Login", "Password"};

    /**
     * The vector list containing the PassKeeperObject.
     */
    private Vector<PassKeeperObject> passKeeperObjects;

    /**
     * Editable status of the model.
     */
    private boolean isEditable = true;

    /**
     * Creates a new PassKeeperModel.
     */
    public PassKeeperModel() {
        this.passKeeperObjects = new Vector<>();
    }

    /**
     * Returns the number of row.
     *
     * @return the number of row.
     */
    @Override
    public int getRowCount() {
        return this.passKeeperObjects.size();
    }

    /**
     * Returns the number of columns.
     *
     * @return the number of column.
     */
    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    /**
     * Return the column name.
     *
     * @param columnIndex the column index.
     * @return the column name.
     */
    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }

    /**
     * Returns the value of a cell in the table.
     *
     * @param rowIndex    the row index.
     * @param columnIndex the column index.
     * @return the value of the corresponding cell.
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:
                // ID
                return this.passKeeperObjects.get(rowIndex).getId();

            case 1:
                // Login
                return this.passKeeperObjects.get(rowIndex).getLogin();

            case 2:
                // Password
                return this.passKeeperObjects.get(rowIndex).getPassword();

            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Returns if the cell is editable.
     *
     * @param rowIndex    the row index.
     * @param columnIndex the column index.
     * @return if the cell is editable.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return this.isEditable;
    }

    /**
     * Sets the value of a cell.
     * @param value       the value.
     * @param rowIndex    the row index.
     * @param columnIndex the column index.
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        switch (columnIndex) {

            case 0:
                // ID
                this.passKeeperObjects.get(rowIndex).setId((String) value);
                break;

            case 1:
                // Login
                this.passKeeperObjects.get(rowIndex).setLogin((String) value);
                break;

            case 2:
                // Password
                this.passKeeperObjects.get(rowIndex).setPassword((String) value);
                break;

            default:
                throw new IllegalArgumentException();
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

    /**
     * Clears the model.
     */
    public void clearModel() {
        passKeeperObjects.clear();
        fireTableDataChanged();
    }

    /**
     * Removes a row in the model.
     *
     * @param rowIndex the row index.
     */
    public void removeRow(int rowIndex) {
        passKeeperObjects.removeElementAt(rowIndex);
        fireTableDataChanged();
    }

    /**
     * Adds a default row in the model.
     */
    public void addRow() {
        passKeeperObjects.add(new PassKeeperObject());
        fireTableDataChanged();
    }

    /**
     * Adds a row in the model.
     * @param id       the id.
     * @param login    the login.
     * @param password the password.
     */
    public void addRow(final String id, final String login, final String password) {
        passKeeperObjects.add(new PassKeeperObject(id, login, password));
        fireTableDataChanged();
    }

    /**
     * Returns a String version of the model.
     *
     * @return a String version of the model.
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (PassKeeperObject passKeeperObject : passKeeperObjects) {
            stringBuilder.append(passKeeperObject.getId()).append(";");
            stringBuilder.append(passKeeperObject.getLogin()).append(";");
            stringBuilder.append(passKeeperObject.getPassword()).append(";");
        }
        return stringBuilder.toString();
    }
}
