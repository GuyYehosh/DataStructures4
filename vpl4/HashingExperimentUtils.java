import java.util.Collections; // can be useful
import java.util.Random;

public class HashingExperimentUtils {
    final private static int k = 16;
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        ModularHash m = new ModularHash();
        Random random = new Random();
        long ins = 0;
        long ser = 0;
        for(int i = 0; i < 30; i++) {
            ChainedHashTable<Integer, Integer> cht = new ChainedHashTable<Integer, Integer>(m, k, maxLoadFactor);
            long t1 = System.nanoTime();
            while(cht.size() / cht.capacity() < maxLoadFactor)
                cht.insert(random.nextInt(0, cht.capacity()), 99);
            long t2 = System.nanoTime();
            //for(int j = 0; j < cht.capacity() / 2; j++)
            {

            }
            long t3 = System.nanoTime();
            ins += t2 - t1;
            ser += t3 - t2;
        }
        Pair<Double, Double> times = new Pair<>((double)(ins/30), (double)(ser/30));
        return times;
    }

    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        ModularHash m = new ModularHash();
        Random random = new Random();
        long ins = 0;
        long ser = 0;
        for(int i = 0; i < 30; i++) {
            ProbingHashTable<Integer, Integer> pht = new ProbingHashTable<Integer, Integer>(m, k, maxLoadFactor);
            long t1 = System.nanoTime();
            while(pht.size() / pht.capacity() < maxLoadFactor)
                pht.insert(random.nextInt(0, pht.capacity()), 99);
            long t2 = System.nanoTime();
            //for(int j = 0; j < pht.capacity() / 2; j++)
            {

            }
            long t3 = System.nanoTime();
            ins += t2 - t1;
            ser += t3 - t2;
        }
        Pair<Double, Double> times = new Pair<>((double)(ins/30), (double)(ser/30));
        return times;
    }

    public static Pair<Double, Double> measureLongOperations() {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static Pair<Double, Double> measureStringOperations() {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public static void main(String[] args) {
        System.out.println("probing:(1/2,3/4,7/8,15/16)");
        System.out.println(measureOperationsProbing(0.5));
        System.out.println(measureOperationsProbing(0.75));
        System.out.println(measureOperationsProbing(0.875));
        System.out.println(measureOperationsProbing(0.9375));
        System.out.println("probing:(1/2,3/4,7/8,15/16)");
        System.out.println(measureOperationsChained(0.5));
        System.out.println(measureOperationsChained(0.75));
        System.out.println(measureOperationsChained(1));
        System.out.println(measureOperationsChained(1.5));
        System.out.println(measureOperationsChained(2));
    }
}
