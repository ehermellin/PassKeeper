package passkeeper.gui.tools;

import passkeeper.model.PassKeeperModel;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

/**
 * This class implements the RowSorterDocumentListener class of <code>PassKeeper</code>.
 * This class extends the DocumentListener object to apply it on JTextField used to filter JTable.
 *
 * @author E. Hermellin
 * @version 1.0 - 08.02.2019
 */
public class RowSorterDocumentListener implements DocumentListener {

    /**
     * The JTextField to listen.
     */
    private JTextField jTextField;

    /**
     * The TableRowSorter associated to the JTable.
     */
    private TableRowSorter<PassKeeperModel> tableRowSorter;

    /**
     * Creates a new RowSorterDocumentListener.
     * @param textField     the JTextField.
     * @param tableRowSorter the TableRowSorter.
     */
    public RowSorterDocumentListener(final JTextField textField, final TableRowSorter<PassKeeperModel> tableRowSorter) {
        this.jTextField = textField;
        this.tableRowSorter = tableRowSorter;
    }

    /**
     * Triggered action when JTextField change.
     * @param e the event.
     */
    @Override
    public void changedUpdate(DocumentEvent e) {
        tableRowSorter.setRowFilter(RowFilter.regexFilter(jTextField.getText(), 0));
    }

    /**
     * Triggered action when char are inserted in JTextField.
     * @param e the event.
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        tableRowSorter.setRowFilter(RowFilter.regexFilter(jTextField.getText(), 0));
    }

    /**
     * Triggered action when char are removed in JTextField.
     * @param e the event.
     */
    @Override
    public void removeUpdate(DocumentEvent e) {
        tableRowSorter.setRowFilter(RowFilter.regexFilter(jTextField.getText(), 0));
    }
}
