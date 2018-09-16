
import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;

public class ImageEncoder {

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

    public static byte[] encryptImageFile(Key key, byte[] content) {
        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypted = cipher.doFinal(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;

    }

    public static void main(String args[])
            throws NoSuchAlgorithmException, InstantiationException, IllegalAccessException, IOException, InvalidKeySpecException, ClassNotFoundException {

        String imageFilePath = args[0];
        String keyFilePath = args[1];
        String encryptionFileName = args[2];

        byte[] content = getFile(imageFilePath);


        Key key;
        FileInputStream fos_key = new FileInputStream(keyFilePath);
        ObjectInputStream obj_key = new ObjectInputStream(fos_key);
        try {
            key = (Key) obj_key.readObject();
        } finally {
            obj_key.close();
        }

        //FileOutputStream fos_enc = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/" + encryptionFileName);
        FileOutputStream fos_enc = new FileOutputStream("/home/pi/fit/shellhacks/sample_photos/" + encryptionFileName);


        byte[] encrypted = encryptImageFile(key, content);
        fos_enc.write(encrypted);
        fos_enc.close();

        //FileInputStream fos_enc_read = new FileInputStream("/home/xps/Documents/shellHacks/sample_photos/enc_key");

        System.out.println("Done Encryption");

    }

}