package Security;

import java.util.List;

public class DiffieHellman {

    public List<Integer> getKeys(int q, int alpha, int xa, int xb) {
        long o_a = modPow(alpha, xa, q);
        long o_b = modPow(alpha, xb, q);

        long ka = modPow(o_b, xa, q);
        long kb = modPow(o_a, xb, q);

        return List.of((int) ka, (int) kb);
    }

    public static long modPow(long base, long exp, long mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) {
                result = (result * base) % mod;
            }
            exp >>= 1;
            base = (base * base) % mod;
        }

        return result;
    }
}