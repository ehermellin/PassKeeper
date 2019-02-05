package passkeeper.gui.tools;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * This class implements the FileTools class of <code>PassKeeper</code>.
 * This class is a toolbox for File management (get, save, etc.).
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class FileTools implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The last file path used.
     */
    private static File staticLastPath = new File(".");

    /**
     * The file extension used for PassKeeper.
     */
    private static FileNameExtensionFilter pKeeper = new FileNameExtensionFilter("PassKeeper file", "pkeeper");

    /**
     * Returns a boolean. True if the file is not null, false otherwise.
     *
     * @param file the file to check.
     * @return true if the file is not null, false otherwise.
     */
    public static boolean checkFile(final File file) {
        return (file != null);
    }

    /**
     * Returns a String containing the name of the file without the extension.
     *
     * @param fileName the filename with the extension.
     * @return the filename without the extension.
     */
    public static String removeExtension(final String fileName) {
        return fileName.substring(0, fileName.lastIndexOf('.'));
    }

    /**
     * Gets a list of file from a JFileChooser.
     *
     * @param component the swing component for JFileChooser.
     * @return a file.
     */
    public static File getFile(Component component) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(staticLastPath);
        fileChooser.setFileFilter(pKeeper);

        File openFile = null;

        int option = fileChooser.showOpenDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                openFile = fileChooser.getSelectedFile();
                staticLastPath = openFile;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return openFile;
    }

    /**
     * Returns the content of a file as a String.
     *
     * @param file the file to read.
     * @return the content of the file as a String.
     */
    public static String readFileToString(File file) {
        final StringBuilder fileContent = new StringBuilder();
        try {
            Scanner scan = FileTools.readFileToScanner(file);
            if (scan != null) {
                while (scan.hasNext()) {
                    fileContent.append(scan.nextLine());
                    fileContent.append("\n");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return fileContent.toString();
    }

    /**
     * Returns a Scanner for a specified file.
     *
     * @param file the file to read.
     * @return a Scanner for a specified file.
     */
    private static Scanner readFileToScanner(File file) {
        try {
            return new Scanner(file, "UTF8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a new file, with a JFileChooser, in a specified place with a specified extension.
     *
     * @param component the swing component for the JFileChooser.
     * @return a new file with a specified place and extension.
     */
    public static File saveFile(Component component) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(staticLastPath);

        File saveFile = null;

        fileChooser.setSelectedFile(new File("password.pkeeper"));
        fileChooser.setFileFilter(pKeeper);

        int option = fileChooser.showSaveDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                saveFile = fileChooser.getSelectedFile();
                staticLastPath = saveFile;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return saveFile;
    }

    /**
     * Writes a content (String) in a specified file.
     *
     * @param file   the file.
     * @param string the content to write.
     */
    public static void writeInFile(File file, String string) {
        try {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false),
                    StandardCharsets.UTF_8));
            try {
                writer.write(string);
            } catch (IOException ioex) {
                ioex.printStackTrace();
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
