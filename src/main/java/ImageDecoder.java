import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

public class ImageDecoder {

    public static byte[] getFile(String filePath) {

        File f = new File(filePath);
        InputStream is = null;
        try {
            is = new FileInputStream(f);
        } catch (FileNotFoundException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        byte[] content = null;
        try {
            content = new byte[is.available()];
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        try {
            is.read(content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return content;
    }

    public static byte[] decryptImageFile(Key key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);
        } catch (BadPaddingException ex) {
            //ignore
        } catch (Exception e) {
            e.printStackTrace();
        }

        return decrypted;
    }

    public static void saveFile(byte[] bytes) throws IOException {

        FileOutputStream fos = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/updated.png");
        fos.write(bytes);
        fos.close();

    }

    public static void main(String args[])
            throws NoSuchAlgorithmException, InstantiationException, IllegalAccessException, IOException, InvalidKeySpecException, ClassNotFoundException {

        String encFilePath = args[0];
        String keyFilePath = args[1];
        String decryptionFileName = args[2];

        Key key;
        FileInputStream fos_key = new FileInputStream(keyFilePath);

        ObjectInputStream obj_key = new ObjectInputStream(fos_key);
        try {
            key = (Key) obj_key.readObject();
        } finally {
            obj_key.close();
        }

        try {
            //FileOutputStream fos_dec = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/" + decryptionFileName);

            FileOutputStream fos_dec = new FileOutputStream("/home/pi/fit/shellhacks/sample_photos/" + decryptionFileName);

            byte[] encrypted = getFile(encFilePath);
            byte[] decrypted = decryptImageFile(key, encrypted);
            fos_dec.write(decrypted);
            fos_dec.close();

        } catch (NullPointerException ex) {
            System.out.println("Incorrect " +
                    "Password! Please, provide correct password for decryption");
        }

        //saveFile(decrypted);
        System.out.println("Done Decryption");
    }

}