package passkeeper.gui.panel;

import passkeeper.gui.tools.WindowsManager;
import passkeeper.service.CryptService;
import passkeeper.service.FilesService;
import passkeeper.service.PassKeeperService;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class implements the MainMenu class of <code>PassKeeper</code>.
 * This JPanel displays the options of the PassKeeper JFrame.
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class MainMenu extends JPanel {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The height of the MainMenu.
     */
    private static final int HEIGHT = 60;

    /**
     * Creates a new MainMenu.
     */
    public MainMenu() {
        setLayout(null);
        setSize(WindowsManager.getWidth(), MainMenu.HEIGHT);

        final JButton newItem = new JButton("New");
        newItem.setToolTipText("New passwords' sheet");
        newItem.setIcon(new ImageIcon("src/resources/icons/new.png"));
        newItem.addActionListener(e -> {
            final PassKeeperService passKeeperService = PassKeeperService.getInstance();
            passKeeperService.clearPassKeeperModel();
        });
        newItem.setBounds(10, 10, 110, 40);

        final JButton loadItem = new JButton("Load");
        loadItem.setToolTipText("Decrypt and Load");
        loadItem.setIcon(new ImageIcon("src/resources/icons/load.png"));
        loadItem.addActionListener(e -> {
            final FilesService filesService = FilesService.getInstance();
            final File encryptedPasswordFile = filesService.getFile(this);
            if (filesService.checkFile(encryptedPasswordFile)) {
                final CryptService cryptService = CryptService.getInstance();
                try {
                    final String decrypted = cryptService.decrypt(filesService.readFileToString(encryptedPasswordFile));
                    if (!decrypted.equals("")) {
                        final PassKeeperService passKeeperService = PassKeeperService.getInstance();
                        passKeeperService.loadPassKeeperObjects(decrypted);
                    }
                } catch (Exception exc) {
                    System.err.println(exc.getMessage());
                }
            }
        });
        loadItem.setBounds(130, 10, 110, 40);

        final JButton saveItem = new JButton("Save");
        saveItem.setToolTipText("Encrypt and Save");
        saveItem.setIcon(new ImageIcon("src/resources/icons/save.png"));
        saveItem.addActionListener(e -> {
            final FilesService filesService = FilesService.getInstance();
            final File encryptedPasswordFile = filesService.getFile(this);
            if (filesService.checkFile(encryptedPasswordFile)) {
                final CryptService cryptService = CryptService.getInstance();
                try {
                    PassKeeperService passKeeperService = PassKeeperService.getInstance();
                    final String encrypted = cryptService.encrypt(passKeeperService.getPassKeeperModelToString());
                    if (!encrypted.equals("")) {
                        filesService.writeInFile(encryptedPasswordFile, cryptService.getIterationCount()
                                + " " + cryptService.getSalt() + " " + encrypted);
                    }
                } catch (Exception exc) {
                    System.err.println(exc.getMessage());
                }
            }
        });
        saveItem.setBounds(250, 10, 110, 40);

        final JButton helpItem = new JButton("About");
        helpItem.setIcon(new ImageIcon("src/resources/icons/about.png"));
        helpItem.addActionListener(e -> {
            final ImageIcon icon = new ImageIcon("src/resources/icons/lock.png");
            JOptionPane.showMessageDialog(null, "PassKeeper v0.1",
                    "About PassKeeper", JOptionPane.INFORMATION_MESSAGE, icon);
        });
        helpItem.setBounds(370, 10, 110, 40);

        final JButton exitItem = new JButton();
        exitItem.setIcon(new ImageIcon("src/resources/icons/exit.png"));
        exitItem.addActionListener(e -> System.exit(0));
        exitItem.setBounds(490, 10, 60, 40);

        this.add(newItem);
        this.add(loadItem);
        this.add(saveItem);
        this.add(helpItem);
        this.add(exitItem);
    }
}
