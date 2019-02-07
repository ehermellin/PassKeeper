package passkeeper.gui;

import passkeeper.gui.tools.CustomOutputStream;
import passkeeper.gui.tools.WindowsManager;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * This class implements the LogFrame class of <code>PassKeeper</code>.
 * This JFrame displays log and standard output in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 08.02.2019
 */
public class LogFrame extends JFrame {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JTextArea containing solving result of selected token.
     */
    private final JTextArea logArea;

    /**
     * Creates a new LogPanel which displays standard output.
     */
    public LogFrame() {
        setLayout(null);
        setResizable(false);
        setSize(WindowsManager.getWidth(), 140);
        setTitle(WindowsManager.NAME + " | LOG");

        logArea = new JTextArea();

        logArea.setEditable(false);
        final JScrollPane scrollTextPane = new JScrollPane(logArea);
        scrollTextPane.setBounds(10, 10, 500, 80);
        add(scrollTextPane);

        final JButton resetButton = new JButton(new ImageIcon("src/resources/icons/clear.png"));
        resetButton.setBounds(520, 10, 50, 80);
        resetButton.setToolTipText("Clear the logs");
        resetButton.setEnabled(true);
        resetButton.addActionListener(e -> logArea.setText(""));
        add(resetButton);

        try {
            PrintStream printStream = new PrintStream(new CustomOutputStream(logArea), false, "UTF-8");

            // keeps reference of standard output stream
            //standardOut = System.out;

            // re-assigns standard output stream and error output stream
            System.setOut(printStream);
            System.setErr(printStream);
        } catch (UnsupportedEncodingException unex) {
            unex.printStackTrace();
        }

        setLocation(WindowsManager.setWindowsLocationWidth());
        setVisible(true);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}
