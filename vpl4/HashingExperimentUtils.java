import java.util.Collections; // can be useful
import java.util.LinkedList;
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
            int cap = cht.capacity();
            LinkedList<Integer> l = new LinkedList<Integer>();
            while((cht.size() + 1)/ cap < maxLoadFactor)
            {
                int x = random.nextInt(0, cht.capacity());
                cht.insert(x, 99);
                l.add(x);
            }
            long t2 = System.nanoTime();
            for(int j = 0; j < cht.size() / 2; j++)
            {
                cht.search(l.get(random.nextInt(0,l.size())));
            }
            for(int j = 0; j < cht.size() / 2; j++)
            {
                cht.search((random.nextInt(cht.size(),cht.size()*2)));
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
            int cap = pht.capacity();
            LinkedList<Integer> l = new LinkedList<Integer>();
            while((pht.size() + 1)/ cap < maxLoadFactor) {
                int x = random.nextInt(0, pht.capacity());
                pht.insert(x, 99);
                l.add(x);
            }
            long t2 = System.nanoTime();
            for(int j = 0; j < pht.size() / 2; j++)
            {
                pht.search(l.get(random.nextInt(0,l.size())));
            }
            for(int j = 0; j < pht.size() / 2; j += 1)
            {
                pht.search((random.nextInt(pht.size(),pht.size()*2)));
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
        Pair<Double,Double> p = measureOperationsProbing(0.5);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsProbing(0.75);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsProbing(0.875);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsProbing(0.9375);
        System.out.println(p.first());
        System.out.println(p.second());
        System.out.println("chaining:(1/2,3/4,7/8,15/16)");
        p = measureOperationsChained(0.5);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsChained(0.75);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsChained(1);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsChained(1.5);
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureOperationsChained(2);
        System.out.println(p.first());
        System.out.println(p.second());
    }
}
