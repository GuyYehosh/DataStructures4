import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    List<Functor> funcs;
    public MultiplicativeShiftingHash() {
        funcs = new LinkedList<Functor>();
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
        Random rand = new Random();
        funcs.add(new Functor((long)Math.pow(2,k)));
        funcs.add(new Functor((long)Math.pow(2,k)));
        funcs.add(new Functor((long)Math.pow(2,k)));
        funcs.add(new Functor((long)Math.pow(2,k)));
        funcs.add(new Functor((long)Math.pow(2,k)));
        return funcs.get(rand.nextInt(0,funcs.size()));
    }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(long k)
        {
            Random rand = new Random();
            this.k = k;
            this.a = rand.nextLong(2,Long.MAX_VALUE);
        }

        @Override
        public int hash(Long key) {
            long ans = key*a();
            ans = (long)(ans % Math.pow(2,WORD_SIZE));
            ans = (long)(ans / Math.pow(2,WORD_SIZE-k()));
            return (int)ans;
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}
