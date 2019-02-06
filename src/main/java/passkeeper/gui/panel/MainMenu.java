package passkeeper.gui.panel;

import passkeeper.service.CryptService;
import passkeeper.gui.tools.FileTools;
import passkeeper.gui.tools.WindowsManager;
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

            String key="ezeon8547";
            String plain="This is an important message";
            final CryptService cryptService = CryptService.getInstance();
            try {
                File testFile = new File("test");
                String enc = cryptService.encrypt2(key, plain);
                FileTools.writeInFile(testFile, enc);
                System.out.println("Original text: " + plain);
                System.out.println("Encrypted text: " + enc);

                String plainAfter = cryptService.decrypt2(key, FileTools.readFileToString(testFile));
                System.out.println("Original text after reading: " + FileTools.readFileToString(testFile));
                System.out.println("Original text after decryption: " + plainAfter);
            }catch (Exception exc) {
                exc.printStackTrace();
            }
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
                    final String decrypted = cryptService.decrypt(cryptService.askPassword(),
                            FileTools.readFileToString(encryptedPasswordFile));
                    System.out.println("text: " + decrypted);
                    if (!decrypted.equals("")) {
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
                    final String encrypted = cryptService.encrypt(cryptService.askPassword(),
                            passKeeperService.getPassKeeperModelToString());
                    System.out.println("text: " + encrypted);
                    FileTools.writeInFile(encryptedPasswordFile, encrypted);
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });
        saveItem.setBounds(230, 10, 100, 40);

        final JButton helpItem = new JButton("Help");
        helpItem.setIcon(new ImageIcon("src/resources/icons/help.png"));
        helpItem.addActionListener(e -> {});
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
