package passkeeper.service;

import passkeeper.PassKeeper;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
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
    private byte[] salt = getRandomSalt();

    /**
     * The iteration count for key generation.
     */
    private int iterationCount = ThreadLocalRandom.current().nextInt(18, 40);

    /**
     * Gets the iteration count parameter used to encrypt data.
     *
     * @return the iteration count parameter.
     */
    public int getIterationCount() {
        if (PassKeeper.isLOG()) {
            System.out.println("[CryptService] Iteration count : " + this.iterationCount);
        }
        return this.iterationCount;
    }

    /**
     * Sets the iteration count parameter.
     *
     * @param iterationCount the iteration count parameter.
     */
    private void setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
    }

    /**
     * Returns the salt for key generation as Base64 String.
     *
     * @return the salt for key generation as Base64 String.
     */
    public String getSalt() {
        final String saltString = new String(Base64.getEncoder().encode(this.salt), StandardCharsets.UTF_8);
        if (PassKeeper.isLOG()) {
            System.out.println("[CryptService] Salt : " + saltString);
        }
        return saltString;
    }

    /**
     * Sets the salt for key generation.
     *
     * @param salt the salt for key generation.
     */
    private void setSalt(byte[] salt) {
        this.salt = salt;
    }

    /**
     * Creates a new CryptService.
     */
    private CryptService() {}

    /**
     * Generates a random salt for key generation.
     *
     * @return a random salt for key generation.
     */
    private byte[] getRandomSalt() {
        final Random random = new SecureRandom();
        byte[] salt = new byte[8];
        random.nextBytes(salt);
        return salt;
    }

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
            final String[] rawData = encryptedText.split(" ");
            final String iteration = rawData[0].trim();
            final String salt = rawData[1].trim();
            final String data = rawData[2].trim();

            if (!iteration.equals("") && !salt.equals("") && !data.equals("")) {
                this.setIterationCount(Integer.parseInt(iteration.trim()));
                this.setSalt(Base64.getMimeDecoder().decode(salt.trim()));

                KeySpec keySpec = new PBEKeySpec(password.toCharArray(), this.salt, this.iterationCount);
                SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
                // Prepare the parameter to the ciphers
                AlgorithmParameterSpec paramSpec = new PBEParameterSpec(this.salt, this.iterationCount);
                //Decryption process; same key will be used for decr
                Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
                dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
                byte[] in = Base64.getMimeDecoder().decode(data.trim());
                byte[] out = dcipher.doFinal(in);
                final String outString = new String(out, StandardCharsets.UTF_8);

                if (PassKeeper.isLOG()) {
                    System.out.println("[CryptService] Salt: " + salt.trim());
                    System.out.println("[CryptService] Iteration count: " + iteration.trim());
                    System.out.println("[CryptService] Decrypting: " + data.trim());
                    System.out.println("[CryptService] Decrypted text: " + outString.trim());
                }
                return outString;
            } else {
                if (PassKeeper.isLOG()) {
                    System.out.println("[CryptService] Error in encrypted data");
                }
                return "";
            }
        } else {
            if (PassKeeper.isLOG()) {
                System.out.println("[CryptService] Can't decrypt because asking passwork failed");
            }
            return "";
        }
    }
}
