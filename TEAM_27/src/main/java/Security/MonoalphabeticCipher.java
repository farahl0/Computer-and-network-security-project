package Security;

import java.util.*;

public class MonoalphabeticCipher {

    public String analyse(String plainText, String cipherText) {
        cipherText = cipherText.toLowerCase();
        plainText = plainText.toLowerCase();
        char[] key = new char[26];

        // Initialize with null characters
        Arrays.fill(key, '\0');

        // Map existing characters from plain to cipher
        for (int i = 0; i < plainText.length(); i++) {
            int m = plainText.charAt(i) - 'a';
            key[m] = cipherText.charAt(i);
        }

        // Fill remaining empty spots in the key with unused letters
        for (int i = 0; i < 26; i++) {
            if (key[i] == '\0') {
                for (int j = 0; j < 26; j++) {
                    char c = (char) (j + 'a');
                    boolean found = false;
                    for (int k = 0; k < 26; k++) {
                        if (key[k] == c) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        key[i] = c;
                        break;
                    }
                }
            }
        }
        return new String(key);
    }

    public String decrypt(String cipherText, String key) {
        cipherText = cipherText.toLowerCase();
        key = key.toLowerCase();
        StringBuilder plainText = new StringBuilder();

        for (int i = 0; i < cipherText.length(); i++) {
            char letter = cipherText.charAt(i);
            int j = key.indexOf(letter);

            if (j != -1) {
                char l = (char) (j + 'a');
                plainText.append(l);
            }
        }
        return plainText.toString();
    }

    public String encrypt(String plainText, String key) {
        plainText = plainText.toLowerCase();
        key = key.toLowerCase();
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plainText.length(); i++) {
            int index = plainText.charAt(i) - 'a';
            ciphertext.append(key.charAt(index));
        }
        return ciphertext.toString();
    }


    /// Frequency Information:
        /// E   12.51%
        /// T	9.25
        /// A	8.04
        /// O	7.60
        /// I	7.26
        /// N	7.09
        /// S	6.54
        /// R	6.12
        /// H	5.49
        /// L	4.14
        /// D	3.99
        /// C	3.06
        /// U	2.71
        /// M	2.53
        /// F	2.30
        /// P	2.00
        /// G	1.96
        /// W	1.92
        /// Y	1.73
        /// B	1.54
        /// V	0.99
        /// K	0.67
        /// X	0.19
        /// J	0.16
        /// Q	0.11
        /// Z	0.09
    public String analyseUsingCharFrequency(String cipher) {
        // Students should complete this part
        int[] freqsInCipher = new int[26];
        cipher = cipher.toLowerCase();
        int cipherLen = cipher.length();

        double[] lettersFreqs = {
                8.04, 1.54, 3.06, 3.99, 12.51, 2.30, 1.96, 5.49, 7.26,
                0.16, 0.67, 4.14, 2.53, 7.09,  7.60, 2.00, 0.11, 6.12,
                6.54, 9.25, 2.71, 0.99, 1.92,  0.19, 1.73, 0.09
        };

        Integer[] cipherIndx = new Integer[26];
        Integer[] engIndx = new Integer[26];

        for(int i = 0; i < 26; i++){
            cipherIndx[i] = i;
            engIndx[i] = i;
        }

        for(int i = 0; i < cipherLen; i++){
            char c = cipher.charAt(i);
            if(c >= 'a' && c <= 'z') freqsInCipher[c - 'a']++;
        }

        Arrays.sort(cipherIndx, (a, b) -> freqsInCipher[b] - freqsInCipher[a]);
        Arrays.sort(engIndx, (a, b) -> Double.compare(lettersFreqs[b], lettersFreqs[a]));

        char[] mapping = new char[26];
        for(int i = 0; i < 26; i++){
            mapping[cipherIndx[i]] = (char)(engIndx[i] + 'a');
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < cipherLen; i++){
            char c = cipher.charAt(i);
            if(c >= 'a' && c <= 'z') result.append(mapping[c - 'a']);
            else result.append(c);
        }

        return result.toString();
    }
}
