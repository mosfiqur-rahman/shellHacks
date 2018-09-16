import java.io.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class ImageEncDec {

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

    public static byte[] decryptImageFile(Key key, byte[] textCryp) {
        Cipher cipher;
        byte[] decrypted = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(textCryp);
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

        String filePath = args[0];
        byte[] content = getFile(filePath);
        System.out.println(content);
        String keyFilePath = args[1];

        Key key;
        FileInputStream fos_key = new FileInputStream(keyFilePath);

        ObjectInputStream obj_key = new ObjectInputStream(fos_key);
        try {
            key = (Key) obj_key.readObject();
        } finally {
            obj_key.close();
        }

        FileOutputStream fos_enc = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/enc_key");
        FileOutputStream fos_dec = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/dec_key");

//        byte[] encrypted = encryptImageFile(key, content);
//        System.out.println(encrypted);
//        fos_enc.write(encrypted);
//        fos_enc.close();

        //FileInputStream fos_enc_read = new FileInputStream("/home/xps/Documents/shellHacks/sample_photos/enc_key");
        byte[] encrypted = getFile("/home/xps/Documents/shellHacks/sample_photos/enc_key");
        byte[] decrypted = decryptImageFile(key, encrypted);
        System.out.println(decrypted);
        fos_dec.write(decrypted);
        fos_dec.close();

        saveFile(decrypted);
        System.out.println("Done");

    }

}