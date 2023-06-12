import java.util.*;

public class HashingExperimentUtils {
    final private static int k = 16;
    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        ModularHash m = new ModularHash();
        Random random = new Random();
        long ins = 0;
        long ser = 0;
        for(int i = 0; i < 30; i++) {
            ChainedHashTable<Integer, Integer> cht = new ChainedHashTable<Integer, Integer>(m, k, maxLoadFactor);
            long t1 = 0;
            int cap = cht.capacity();
            LinkedList<Integer> l = new LinkedList<Integer>();
            while((cht.size() + 1)/ cap < maxLoadFactor)
            {
                int x = random.nextInt(0, cht.capacity());
                long temp = System.nanoTime();
                cht.insert(x, 99);
                t1 += System.nanoTime() - temp;
                l.add(x);
            }
            long t2 = 0;
            for(int j = 0; j < cht.size() / 2; j++)
            {
                int toSearch = l.get(random.nextInt(0,l.size()));
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            for(int j = 0; j < cht.size() / 2; j++)
            {
                int toSearch = (random.nextInt(cht.size(),cht.size()*2));
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            ins += t1 / cht.size();
            ser += t2 / cht.size();
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
            long t1 = 0;
            int cap = pht.capacity();
            LinkedList<Integer> l = new LinkedList<Integer>();
            while((pht.size() + 1)/ cap < maxLoadFactor) {
                int x = random.nextInt(0, pht.capacity());
                long temp = System.nanoTime();
                pht.insert(x, 99);
                t1 += System.nanoTime() - temp;
                l.add(x);
            }
            long t2 = 0;
            for(int j = 0; j < pht.size() / 2; j++)
            {
                int toSearch = l.get(random.nextInt(0,l.size()));
                long temp = System.nanoTime();
                pht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            for(int j = 0; j < pht.size() / 2; j += 1)
            {
                int toSearch = (random.nextInt(pht.size(),pht.size()*2));
                long temp = System.nanoTime();
                pht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            ins += (t1) / pht.size();
            ser += (t2) / pht.size();
        }
        Pair<Double, Double> times = new Pair<>((double)(ins/30), (double)(ser/30));
        return times;
    }

    public static Pair<Double, Double> measureLongOperations() {
        HashingUtils h = new HashingUtils();
        MultiplicativeShiftingHash m = new MultiplicativeShiftingHash();
        Random random = new Random();
        long ins = 0;
        long ser = 0;
        for(int i = 0; i < 10; i++) {
            ChainedHashTable<Long, Integer> cht = new ChainedHashTable<Long, Integer>(m, k, 1);
            long t1 = 0;
            int cap = cht.capacity();
            List<Long> l = Arrays.stream(h.genUniqueLong(cap)).toList();
            while((cht.size() + 1)/ cap < 1) {
                long x = l.get(random.nextInt(0, cap));
                long temp = System.nanoTime();
                cht.insert(x, 99);
                t1 += System.nanoTime() - temp;
            }
            long t2 = 0;
            for(int j = 0; j < cht.size() / 2; j++)
            {
                long toSearch = l.get(random.nextInt(0,l.size()));
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            for(int j = 0; j < cht.size() / 2; j += 1)
            {
                long toSearch = (h.genLong((long)cht.size(),(long)cht.size()*2));
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            ins += (t1) / cht.size();
            ser += (t2) / cht.size();
        }
        Pair<Double, Double> times = new Pair<>((double)(ins/10), (double)(ser/10));
        return times;
    }

    public static Pair<Double, Double> measureStringOperations() {
        StringHash m = new StringHash();
        HashingUtils h = new HashingUtils();
        Random random = new Random();
        long ins = 0;
        long ser = 0;
        for(int i = 0; i < 10; i++) {
            ChainedHashTable<String, Integer> cht = new ChainedHashTable<String, Integer>(m, k, 1);
            long t1 = 0;
            int cap = cht.capacity();
            List<String> l = h.genUniqueStrings(cht.capacity(), 10, 20);
            Iterator<String> it = l.iterator();
            while(it.hasNext()) {
                String x = it.next();
                long temp = System.nanoTime();
                cht.insert(x, 99);
                t1 += System.nanoTime() - temp;
            }
            long t2 = 0;
            for(int j = 0; j < cht.size() / 2; j++)
            {
                String toSearch = l.get(random.nextInt(0,l.size()));
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            for(int j = 0; j < cht.size() / 2; j += 1)
            {
                String toSearch = l.get(random.nextInt(0, cht.size())) + "x";
                long temp = System.nanoTime();
                cht.search(toSearch);
                t2 += System.nanoTime() - temp;
            }
            ins += (t1) / cht.size();
            ser += (t2) / cht.size();
        }
        Pair<Double, Double> times = new Pair<>((double)(ins/10), (double)(ser/10));
        return times;
    }

    public static void main(String[] args) {
        Pair<Double,Double> p;
        System.out.println("probing:(1/2,3/4,7/8,15/16)");
        p = measureOperationsProbing(0.5);
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
        p = measureLongOperations();
        System.out.println(p.first());
        System.out.println(p.second());
        p = measureStringOperations();
        System.out.println(p.first());
        System.out.println(p.second());
    }
}
