import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class KeyGen {

    public void keygen(String passcode) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        //byte[] salt = getSalt();
        String saltStr = "A009C1A485912C6AE630D3E744240B04";
        byte[] salt = saltStr.getBytes();
        int iterations = 65536;
        String pass = "shellHacks";
        char[] password = passcode.toCharArray();
        KeySpec spec = new PBEKeySpec(password, salt, iterations, 256);
        SecretKey secret = factory.generateSecret(spec);
        Key key = new SecretKeySpec(secret.getEncoded(), "AES");
        //System.out.println(key);


//        String algorithm = "AES";
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        Key key = keyGenerator.generateKey();
//        byte[] keyValue = keyFilePath.getBytes();
//        System.out.println(Arrays.toString(keyValue));
//        Key key = new SecretKeySpec(keyValue, algorithm);
        //FileOutputStream fos_key = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/key");
        FileOutputStream fos_key = new FileOutputStream("/home/pi/fit/shellhacks/sample_photos/key");


        ObjectOutputStream obj_key = new ObjectOutputStream(fos_key);
        try {
            obj_key.writeObject(key);
        } finally {
            obj_key.close();
        }
        //FileOutputStream fos_key = new FileOutputStream("/home/xps/Documents/shellHacks/sample_photos/key");
        //fos_key.write(key.getEncoded());
        //fos_key.close();
//        return key;
    }

    private static byte[] getSalt() throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    public static void main(String args[]) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String keyFilePath = args[0];
        KeyGen k = new KeyGen();
        k.keygen(keyFilePath);
    }
}
