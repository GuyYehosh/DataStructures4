import java.util.*;

public class ModularHash implements HashFactory<Integer> {
    List<Functor> funcs;
    public ModularHash() {
        funcs = new LinkedList<Functor>();

    }
    @Override
    public HashFunctor<Integer> pickHash(int k) {
        funcs.add(new Functor((int)Math.pow(2,k)));
        funcs.add(new Functor((int)Math.pow(2,k)));
        funcs.add(new Functor((int)Math.pow(2,k)));
        funcs.add(new Functor((int)Math.pow(2,k)));
        funcs.add(new Functor((int)Math.pow(2,k)));
        Random rand = new Random();
        return funcs.get(rand.nextInt(0,funcs.size()));
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
            long ans = (((long)a()*key + b()) % p()) % m();
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
