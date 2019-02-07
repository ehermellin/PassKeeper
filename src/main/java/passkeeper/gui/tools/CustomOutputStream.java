package passkeeper.gui.tools;

import java.io.OutputStream;
import java.io.Serializable;
import javax.swing.JTextArea;

/**
 * This class implements the CustomOutputStream class of <code>PassKeeper</code>.
 * This class extends the OutputStream to write output in a JTextArea.
 *
 * @author E. Hermellin
 * @version 1.0 - 08.02.2019
 */
public class CustomOutputStream extends OutputStream implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The JTextArea which contains the output.
     */
    private JTextArea textArea;

    /**
     * Create a new CustomOutputStream for a specific JTextArea.
     * @param textArea the JTextArea
     */
    public CustomOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    /**
     * Write an output into a JtextArea.
     * @param b the int value of a character
     */
    @Override
    public void write(int b) {
        // redirects data to the text area
        this.textArea.append(String.valueOf((char)b));
        // scrolls the text area to the end of data
        this.textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
