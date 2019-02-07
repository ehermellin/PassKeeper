package passkeeper.gui.panel;

import passkeeper.gui.tools.FileTools;
import passkeeper.gui.tools.WindowsManager;
import passkeeper.service.CryptService;
import passkeeper.service.PassKeeperService;

import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
            passKeeperService.getPassKeeperModel().clearModel();
        });
        newItem.setBounds(10, 10, 100, 40);

        final JButton loadItem = new JButton("Load");
        loadItem.setToolTipText("Decrypt and Load");
        loadItem.setIcon(new ImageIcon("src/resources/icons/load.png"));
        loadItem.addActionListener(e -> {
            final File encryptedPasswordFile = FileTools.getFile(this);
            if (FileTools.checkFile(encryptedPasswordFile)) {
                final CryptService cryptService = CryptService.getInstance();
                try {
                    System.out.println("text: " + FileTools.readFileToString(encryptedPasswordFile));
                    final String decrypted = cryptService.decrypt(FileTools.readFileToString(encryptedPasswordFile));
                    if (!decrypted.equals("")) {
                        System.out.println("text: " + decrypted);
                        final PassKeeperService passKeeperService = PassKeeperService.getInstance();
                        passKeeperService.loadPassKeeperObjects(decrypted);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
        loadItem.setBounds(120, 10, 100, 40);

        final JButton saveItem = new JButton("Save");
        saveItem.setToolTipText("Encrypt and Save");
        saveItem.setIcon(new ImageIcon("src/resources/icons/save.png"));
        saveItem.addActionListener(e -> {
            final File encryptedPasswordFile = FileTools.saveFile(this);
            if (FileTools.checkFile(encryptedPasswordFile)) {
                final CryptService cryptService = CryptService.getInstance();
                try {
                    PassKeeperService passKeeperService = PassKeeperService.getInstance();
                    System.out.println("text: " + passKeeperService.getPassKeeperModelToString());
                    final String encrypted = cryptService.encrypt(passKeeperService.getPassKeeperModelToString());
                    if (!encrypted.equals("")) {
                        System.out.println("text: " + encrypted);
                        FileTools.writeInFile(encryptedPasswordFile, encrypted);
                    }
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
        saveItem.setBounds(230, 10, 100, 40);

        final JButton helpItem = new JButton("Help");
        helpItem.setIcon(new ImageIcon("src/resources/icons/help.png"));
        helpItem.addActionListener(e -> System.out.println());
        helpItem.setBounds(340, 10, 100, 40);

        final JButton exitItem = new JButton("Exit");
        exitItem.setIcon(new ImageIcon("src/resources/icons/exit.png"));
        exitItem.addActionListener(e -> System.exit(0));
        exitItem.setBounds(450, 10, 100, 40);

        this.add(newItem);
        this.add(loadItem);
        this.add(saveItem);
        this.add(helpItem);
        this.add(exitItem);
    }
}
