package passkeeper.service;

import passkeeper.PassKeeper;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class CryptService implements Serializable {

    /**
     * The serial id of the class.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The instance of the service.
     */
    private static CryptService instance;

    /**
     * 8-byte Salt for key generation.
     */
    private byte[] salt = {
        (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    /**
     * The iteration count for key generation.
     */
    private int iterationCount = 19;

    /**
     * Creates a new CryptService.
     */
    private CryptService() {}

    /**
     * Returns the instance of the CryptService.
     *
     * @return the instance of the CryptService.
     */
    public static synchronized CryptService getInstance() {
        if (CryptService.instance == null) {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Creates CryptService");
            }
            CryptService.instance = new CryptService();
        }
        return CryptService.instance;
    }

    /**
     * Opens JOptionPane to ask for a password.
     *
     * @return the password as a String.
     */
    private String askPassword() {
        final JPanel panel = new JPanel();
        final JPasswordField pass = new JPasswordField(16);
        pass.setEchoChar('-');
        final JCheckBox checkBox = new JCheckBox("Show/Hide");
        checkBox.setSelected(false);
        checkBox.addItemListener(e -> {
            if (checkBox.isSelected()) {
                pass.setEchoChar((char) 0);
            } else {
                pass.setEchoChar('-');
            }
        });
        panel.add(pass);
        panel.add(checkBox);

        final String[] options = new String[]{"OK", "Cancel"};
        final int option = JOptionPane.showOptionDialog(null, panel, "Enter your password:",
                JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[1]);
        if (option == 0) {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Asks password succeed");
            }
            char[] password = pass.getPassword();
            return new String(password).trim();
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Asks password failed");
            }
            return "";
        }
    }

    /**
     * Returns a encrypted String from a plain String.
     *
     * @param plainText the input test to be encrypted.
     * @return returns an encrypted text.
     */
    public String encrypt(String plainText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        //Key generation for enc and desc
        final String password = this.askPassword();
        if (!password.equals("")) {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), this.salt, this.iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);

            //Enc process
            Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            byte[] in = plainText.getBytes(StandardCharsets.UTF_8);
            byte[] out = ecipher.doFinal(in);
            final String outString = new String(Base64.getEncoder().encode(out), StandardCharsets.UTF_8);
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Crypting: " + plainText.trim());
                System.out.println("[CryptService] Crypted text: " + outString.trim());
            }
            return outString;
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Can't crypt because asking passwork failed");
            }
            return "";
        }
    }

    /**
     * Returns a decrypted String from an encrypted String.
     *
     * @param encryptedText the encrypted input text to decrypt.
     * @return the decrypted String.
     */
    public String decrypt(String encryptedText)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchPaddingException,
            InvalidKeyException,
            InvalidAlgorithmParameterException,
            IllegalBlockSizeException,
            BadPaddingException {
        //Key generation for enc and desc
        final String password = this.askPassword();
        if (!password.equals("")) {
            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), this.salt, this.iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
            //Decryption process; same key will be used for decr
            Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
            byte[] in = Base64.getMimeDecoder().decode(encryptedText.trim());
            byte[] out = dcipher.doFinal(in);
            final String outString = new String(out, StandardCharsets.UTF_8);

            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Decrypting: " + encryptedText.trim());
                System.out.println("[CryptService] Decrypted text: " + outString.trim());
            }
            return outString;
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Can't decrypt because asking passwork failed");
            }
            return "";
        }
    }
}
