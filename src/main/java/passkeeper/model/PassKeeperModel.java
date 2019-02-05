package passkeeper.model;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class PassKeeperModel extends AbstractTableModel {

    private final String[] columnNames = { "ID", "Login", "Password"};

    private PassKeeperService passKeeperService;

    private Vector<PassKeeperObject> passKeeperObjects;

    public PassKeeperModel() {
        this.passKeeperService = PassKeeperService.getInstance();
        this.passKeeperObjects = passKeeperService.loadPassKeeperObjectList();
    }

    @Override
    public int getRowCount() {
        return this.passKeeperObjects.size();
    }

    @Override
    public int getColumnCount() {
        return this.columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames[columnIndex];
    }

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

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

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

    public void clearModel() {
        passKeeperObjects.clear();
        fireTableDataChanged();
    }

    public void removeRow(int rowIndex) {
        passKeeperObjects.removeElementAt(rowIndex);
        fireTableDataChanged();
    }

    public void addRow() {
        passKeeperObjects.add(new PassKeeperObject("ID", "Login", "Password"));
        fireTableDataChanged();
    }
}
