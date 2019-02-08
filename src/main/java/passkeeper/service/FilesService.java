package passkeeper.service;

import passkeeper.PassKeeper;

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
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class implements the FileTools class of <code>PassKeeper</code>.
 * This class is a toolbox for File management (get, save, etc.).
 *
 * @author E. Hermellin
 * @version 1.0 - 05.02.2019
 */
public class FilesService implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The instance of the service.
     */
    private static FilesService instance;

    /**
     * The last file path used.
     */
    private File staticLastPath = new File(".");

    /**
     * Creates a new FilesService.
     */
    private FilesService() {}

    /**
     * Returns the instance of the FilesService.
     *
     * @return the instance of the FilesService.
     */
    public static synchronized FilesService getInstance() {
        if (FilesService.instance == null) {
            if (PassKeeper.isLOG()) {
                System.out.println("[FilesService] Creates FilesService");
            }
            FilesService.instance = new FilesService();
        }
        return FilesService.instance;
    }

    /**
     * Returns a boolean. True if the file is not null, false otherwise.
     *
     * @param file the file to check.
     * @return true if the file is not null, false otherwise.
     */
    public boolean checkFile(final File file) {
        return (file != null);
    }

    /**
     * Gets a file from a JFileChooser.
     *
     * @param component the swing component for JFileChooser.
     * @return a file.
     */
    public File getFile(final Component component) {
        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(this.staticLastPath);
        fileChooser.setFileFilter(new FileNameExtensionFilter("PassKeeper file", "pkeeper"));
        fileChooser.setSelectedFile(new File("save.pkeeper"));

        File openFile = null;

        int option = fileChooser.showOpenDialog(component);

        if (option == JFileChooser.APPROVE_OPTION) {
            try {
                openFile = fileChooser.getSelectedFile();
                this.staticLastPath = openFile;

                if (PassKeeper.isLOG()) {
                    System.out.println("[FilesService] Getting .pkeeper file : " + openFile.getName());
                }
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[FilesService] Getting .pkeeper file canceled");
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
    public String readFileToString(final File file) {
        final StringBuilder fileContent = new StringBuilder();
        try {
            if (PassKeeper.isLOG()) {
                System.out.println("[FilesService] Reading file " + file.getName());
            }
            Scanner scan = this.readFileToScanner(file);
            if (scan != null) {
                while (scan.hasNext()) {
                    fileContent.append(scan.nextLine());
                    fileContent.append("\n");
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        return fileContent.toString();
    }

    /**
     * Returns a Scanner for a specified file.
     *
     * @param file the file to read.
     * @return a Scanner for a specified file.
     */
    private Scanner readFileToScanner(final File file) {
        try {
            return new Scanner(file, "UTF8");
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }



    /**
     * Writes a content (String) in a specified file.
     *
     * @param file   the file.
     * @param string the content to write.
     */
    public void writeInFile(final File file, final String string) {
        try {
            final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false),
                    StandardCharsets.UTF_8));
            try {
                if (PassKeeper.isLOG()) {
                    System.out.println("[FilesService] Writing file " + file.getName());
                }
                writer.write(string);
            } catch (IOException ioex) {
                System.err.println(ioex.getMessage());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
