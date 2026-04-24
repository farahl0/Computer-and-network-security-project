package Security;

public class AutoKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part
        plainText = plainText.toLowerCase();
        cipherText = cipherText.toLowerCase();

        StringBuilder keyStream = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {

            int p_t = plainText.charAt(i) - 'a';
            int c_t = cipherText.charAt(i) - 'a';

            char k = (char) (((c_t - p_t + 26) % 26) + 'a');
            keyStream.append(k);
        }


        String key = "";
        for (int i = 0; i < keyStream.length(); i++) {

            key += keyStream.charAt(i);

            String generated = key + plainText.substring(0, plainText.length() - key.length());

            if (generated.equals(keyStream.toString()))
                break;
        }
        return key;
    }

    public String decrypt(String cipherText, String key) {
        // Students should complete this part
        cipherText = cipherText.toLowerCase();
        key = key.toLowerCase();

        StringBuilder plainText = new StringBuilder(key);

        for (int i = 0; i < cipherText.length(); i++) {

            int c_t = cipherText.charAt(i) - 'a';
            int k = plainText.charAt(i) - 'a';

            char p_t = (char) (((c_t - k + 26) % 26) + 'a');

            plainText.append(p_t);
        }

        return plainText.substring(key.length());

    }


    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int len = plainText.length();

        // Extend key using the plaintext
        StringBuilder autoKey = new StringBuilder(key);
        if (autoKey.length() < len) {
            int diffLen = len - autoKey.length();
            for (int i = 0; i < diffLen; i++) {
                autoKey.append(plainText.charAt(i));
            }
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = autoKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }
        return cipherText.toString();
    }
}
