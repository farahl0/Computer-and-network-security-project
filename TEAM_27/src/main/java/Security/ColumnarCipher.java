package Security;
import java.util.*;

public class ColumnarCipher {

    public List<Integer> analyse(String plainText, String cipherText) {
        // TODO: Analyze the plainText and cipherText to determine the key(s)
        plainText = plainText.replaceAll("[^A-Za-z]", "").toUpperCase();
        cipherText = cipherText.toUpperCase();

        int plainLength = plainText.length();
        int cipherLength = cipherText.length();

        System.out.println("Looking for key that turns '" + plainText + "' into '" + cipherText + "'");

        //we don't kmow how many coulmns the ciphertext actually have
        for (int keySize = 2; keySize <= 10; keySize++) {

            //  how many rows we need for this keysize
            int rows = (int) Math.ceil((double) plainLength / (double) keySize);

            //length = rows * keySize
            int expectedLength = rows * keySize;

            // If the lengths did not  match not the key that would work
            if (expectedLength != cipherLength) {
                continue;
            }

            System.out.println("Trying key size: " + keySize + " (rows = " + rows + ")");

            // the ciphertext of length n have the keysize of n! thus I will generete all the permutations for this size
//           //nested list inerr list have the all permutation of a single key outer list have a list of all the keys including thier permutaions
            List<List<Integer>> allPossibleKeys = generateAllKeys(keySize);

            System.out.println("  Testing " + allPossibleKeys.size() + " different keys...");



            for (int i = 0; i < allPossibleKeys.size(); i++) {
                List<Integer> possibleKey = allPossibleKeys.get(i);

                // Encrypt the plaintext with this possible key
                String encryptedText = encrypt(plainText, possibleKey);

                // Check if it matches our target ciphertext
                if (encryptedText.equals(cipherText)) {
                    System.out.println(" Found matching key: " + possibleKey);
                    return possibleKey;
                }


            }
        }


        System.out.println("no key found the key returning an emptylist");
        return new ArrayList<>();
    }

    // if n=3, it returns: [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
    private List<List<Integer>> generateAllKeys(int keysize) {
        List<List<Integer>> allKeys = new ArrayList<>();
        List<Integer> currentKey = new ArrayList<>();


        buildKeys(currentKey,allKeys, keysize);

        return allKeys;
    }

    // recursive func to build all permutations
    private void buildKeys(List<Integer> currentKey,
                           List<List<Integer>> allKeys, int keysize) {

        // If our current key list has all the numbers copy it to a list then add it to the allkey list why?
        // each list inside the allkey list have the same size diffrent permutation then it would be considered identical every allkey[i] would point on [0],copy it to a separate lsit thus wont be identcal

        if (currentKey.size() == keysize) {

            allKeys.add(new ArrayList<>(currentKey));

        }
// recursive loop to use each bit of the list to build all the permutaions of the keysize
        for (int bit = 1; bit <= keysize; bit++) {
            if (!currentKey.contains(bit)) {  //checking if the bit is used before
                currentKey.add(bit);
                buildKeys(currentKey,allKeys, keysize);
                currentKey.remove(currentKey.size() - 1);
            }
        }



    }

    public String decrypt(String cipherText, List<Integer> key) {
        int cipherSize = cipherText.length();
        int rows = (int) Math.ceil((double) cipherSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        int remainingCols = cipherSize % key.size();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                if (remainingCols != 0 && j == rows - 1 && keyMap.get(i) >= remainingCols) continue;
                grid[j][keyMap.get(i)] = cipherText.charAt(count++);
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                result.append(grid[i][j]);
            }
        }
        return result.toString().toUpperCase().trim();
    }

    public String encrypt(String plainText, List<Integer> key) {
        int ptSize = plainText.length();
        int rows = (int) Math.ceil((double) ptSize / key.size());
        char[][] grid = new char[rows][key.size()];
        int count = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < key.size(); j++) {
                if (count >= ptSize) {
                    grid[i][j] = 'x';
                } else {
                    grid[i][j] = plainText.charAt(count++);
                }
            }
        }

        Map<Integer, Integer> keyMap = new HashMap<>();
        for (int i = 0; i < key.size(); i++) {
            keyMap.put(key.get(i) - 1, i);
        }

        StringBuilder cipherText = new StringBuilder();
        for (int i = 0; i < key.size(); i++) {
            for (int j = 0; j < rows; j++) {
                cipherText.append(Character.toUpperCase(grid[j][keyMap.get(i)]));
            }
        }
        return cipherText.toString();
    }
}
