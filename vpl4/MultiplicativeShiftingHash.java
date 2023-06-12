import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MultiplicativeShiftingHash implements HashFactory<Long> {
    Functor[] func;
    public MultiplicativeShiftingHash() {
        func = new Functor[5];
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
        Random random = new Random();
        for(int i = 0; i < 5; i++)
            func[i] = new Functor(k);
        return func[random.nextInt(0, 5)];
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
            ans = (HashingUtils.mod(ans , (long)Math.pow(2,WORD_SIZE)));
            int toRet = ((int)ans / (int)Math.pow(2,WORD_SIZE-k()));
            return toRet;
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}
