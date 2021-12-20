import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

class BinomialCoefficient {

    private int maxN, maxK = 0;
    private HashMap<Integer, Long> cachedResults  = new HashMap<>();;

    public long biomialTo2(int n) {
        return cachedResults.computeIfAbsent(n, v -> binomial(v, 2));
    }

    private static long binomial(int n, int k) {
        if (k>n-k) {
            k=n-k;
        }

        long b=1;
        for (int i=1, m=n; i<=k; i++, m--) {
            b=b*m/i;
        }
        return b;
    }

    // For testing purpose
    public static void main(String[] args) {
        final var bio = new BinomialCoefficient();
        for (int i = 1; i < 10; i++) {
            int n = 50 * i, k = 2;
            System.out.println("Map Value of C(" + n + "," + k + ") is " + bio.binomial(n, k));
        }

    }
}
