import java.util.*;

public class ModularHash implements HashFactory<Integer> {
    Functor[] func;
    public ModularHash() {
        func = new Functor[5];
    }
    @Override
    public HashFunctor<Integer> pickHash(int k) {
        Random random = new Random();
        for(int i = 0; i < 5; i++)
            func[i] = new Functor((int)Math.pow(2,k));
        return func[random.nextInt(0, 5)];
    }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private long p;
        final private int m;
        Functor(int m)
        {
            this.m = m;
            Random rand = new Random();
            this.a = rand.nextInt(1,Integer.MAX_VALUE);
            this.b = rand.nextInt(0,Integer.MAX_VALUE);
            long temp = Integer.MAX_VALUE;
            temp++;
            this.p = rand.nextLong(temp,Long.MAX_VALUE );
        }
        @Override
        public int hash(Integer key) {
            long ans = HashingUtils.mod(HashingUtils.mod(((long)a()*key + b()) , p()) , m());
            return (int)ans;
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public long p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
