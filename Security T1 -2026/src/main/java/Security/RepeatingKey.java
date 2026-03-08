package Security;

public class RepeatingKey {
    public String analyse(String plainText, String cipherText) {
        // Students should complete this part
        plainText=plainText.toLowerCase();
        cipherText=cipherText.toLowerCase();

        int plain =plainText.length();
        StringBuilder KEY =new StringBuilder();

        for(int i = 0; i< plain; i++){
            int PLAIN =plainText.charAt(i)-'a';
            int CIPHER =cipherText.charAt(i)-'a';

            int k=(CIPHER - PLAIN +26)%26;
            KEY.append((char)(k+'a'));
        }
        for (int k = 1; k<= KEY.length(); k++){
            boolean YAY =true;
            String MAYBE = KEY.substring(0,k);

            for (int i = 0; i< KEY.length(); i++){
                if(KEY.charAt(i)!= MAYBE.charAt(i%k)){
                    YAY =false;
                    break;
                }
            }
            if (YAY){
                return MAYBE;
            }
        }

        return KEY.toString();
    }

    public String decrypt(String cipherText, String key) {
        // Students should complete this part
        cipherText=cipherText.toLowerCase();
        key=key.toLowerCase();
        int cipherLength=cipherText.length();

        StringBuilder s =new StringBuilder(key);
        while(s.length()<cipherLength){
            s.append(s.charAt(s.length()-key.length()));
        }
        StringBuilder plainText= new StringBuilder();
        for(int i=0;i<cipherLength;i++){
            int c=cipherText.charAt(i)-'a';
            int k=s.charAt(i)-'a';
            plainText.append((char) (((c-k+26)%26)+'a'));
        }
        return plainText.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        int plainLen = plainText.length();

        // Repeat key to match plaintext length
        StringBuilder extendedKey = new StringBuilder(key);
        while (extendedKey.length() < plainLen) {
            extendedKey.append(extendedKey.charAt(extendedKey.length() - key.length()));
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < plainLen; i++) {
            int p = plainText.charAt(i) - 'a';
            int k = extendedKey.charAt(i) - 'a';
            cipherText.append((char) (((p + k) % 26) + 'a'));
        }

        return cipherText.toString();
    }
}
