import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;

public class AES {

    private final String ALGO = "AES";
    private byte[] keyValue;

    AES(byte[] keyValue) {
        this.keyValue = keyValue;
    }

    AES() {
        this.keyValue =
                new byte[]{'T', 'h', 'e', 'B', 'e', 's', 't',
                        'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};
    }

    public void encrypt(File fileToEnc) throws Exception {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);

        FileOutputStream outFile = new FileOutputStream("encryptedfile.des");
        FileInputStream inFile = new FileInputStream(fileToEnc);
        byte[] input = new byte[64];
        int bytesRead;

        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = c.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }
        byte[] output = c.doFinal();
        if (output != null)
            outFile.write(output);

        inFile.close();
        outFile.flush();
        outFile.close();

    }

    public void decrypt(String format) throws Exception {
        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        FileInputStream fis = new FileInputStream("encryptedfile.des");
        FileOutputStream fos = new FileOutputStream("decrypted."+format);
        byte[] in = new byte[64];
        int read;
        while ((read = fis.read(in)) != -1) {
            byte[] output = c.update(in, 0, read);
            if (output != null)
                fos.write(output);
        }

        byte[] output = c.doFinal();
        if (output != null)
            fos.write(output);
        fis.close();
        fos.flush();
        fos.close();
    }

    private Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGO);
        return key;
    }

}