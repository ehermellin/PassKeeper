package passkeeper.gui.panel;

import passkeeper.gui.tools.WindowsManager;
import passkeeper.model.PassKeeperModel;
import passkeeper.model.PassKeeperService;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * This class implements the JTablePanel class of <code>PassKeeper</code>.
 * This JPanel displays the JTable of PassKeeper.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class JTablePanel extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The height of the JTablePanel.
     */
    private static final int HEIGHT = 450;

    /**
     * Creates a new JTablePanel.
     */
    public JTablePanel() {
        setLayout(null);
        setSize(WindowsManager.getWidth(), JTablePanel.HEIGHT);

        final PassKeeperModel passKeeperModel = PassKeeperService.getInstance().getPassKeeperModel();

        final JTable table = new JTable(passKeeperModel);

        final JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10,10,540, 380);
        this.add(scrollPane);

        final JButton addItem = new JButton("Add");
        addItem.setToolTipText("Add new entry in sheet");
        addItem.addActionListener(e -> passKeeperModel.addRow());
        addItem.setBounds(145, 410, 90, 30);

        final JButton removeItem = new JButton("Remove");
        removeItem.setToolTipText("Remove selected entry in sheet");
        removeItem.addActionListener(e -> {
            final int rowIndex = table.getSelectedRow();
            if (rowIndex != -1) {
                passKeeperModel.removeRow(rowIndex);
            }
        });
        removeItem.setBounds(245, 410, 90, 30);

        final JButton filterItem = new JButton("Filter");
        filterItem.setToolTipText("Filter sheet");
        filterItem.addActionListener(e -> {});
        filterItem.setBounds(345, 410, 90, 30);

        this.add(addItem);
        this.add(removeItem);
        this.add(filterItem);
    }
}
