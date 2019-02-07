package passkeeper.gui;

import passkeeper.gui.panel.JTablePanel;
import passkeeper.gui.panel.MainMenu;
import passkeeper.gui.tools.WindowsManager;

import javax.swing.JFrame;

/**
 * This class implements the MainFrame class of <code>PassKeeper</code>.
 * This JFrame displays all the Panel used in PassKeeper.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class MainFrame extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new MainFrame.
     */
    public MainFrame() {
        setLayout(null);
        setResizable(false);
        setSize(WindowsManager.getWidth(), WindowsManager.getHeight());
        setTitle(WindowsManager.NAME + " | password Reminder");
        WindowsManager.setPoint(this.getLocation());

        final MainMenu mainMenu = new MainMenu();
        mainMenu.setBounds(WindowsManager.getMarging(), WindowsManager.getMarging(),
                WindowsManager.getWidth() - 2 * WindowsManager.getMarging(), mainMenu.getHeight());
        add(mainMenu);

        final JTablePanel jTablePanel = new JTablePanel();
        jTablePanel.setBounds(WindowsManager.getMarging(), 2 * WindowsManager.getMarging() + mainMenu.getHeight(),
                WindowsManager.getWidth() - 2 * WindowsManager.getMarging(),
                (jTablePanel.getHeight()));
        add(jTablePanel);

        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
