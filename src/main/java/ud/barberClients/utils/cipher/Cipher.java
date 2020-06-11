package ud.barberClients.utils.cipher;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Cipher {

    public static String encryptString(String word) {
        MCrypt mCrypt = new MCrypt();
        String encryptedPass = "fail";
        try {
            byte[] pass = word.getBytes(StandardCharsets.UTF_8);
            String uname64 = Base64.getEncoder().encodeToString(pass);
            encryptedPass = MCrypt.bytesToHex(mCrypt.encrypt(uname64));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedPass;
    }

    public static String decryptString(String word) {
        MCrypt mCrypt = new MCrypt();
        String pass = "fail";
        try {
            String beforeProcess = new String(mCrypt.decrypt(word)).trim();
            byte[] decode64 = Base64.getDecoder().decode(beforeProcess);
            pass = new String(decode64, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pass;
    }

}
