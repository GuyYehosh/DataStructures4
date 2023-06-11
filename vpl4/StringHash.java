import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringHash implements HashFactory<String> {
    List<Functor> funcs;
    public StringHash() {
        funcs = new ArrayList<Functor>();
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
        funcs.add(new Functor(k));
        funcs.add(new Functor(k));
        funcs.add(new Functor(k));
        funcs.add(new Functor(k));
        funcs.add(new Functor(k));
        Random rand = new Random();
        return funcs.get(rand.nextInt(0,funcs.size()));
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;

        public Functor(int k)
        {
            Random rand = new Random();
            q = rand.nextInt(Integer.MAX_VALUE / 2, Integer.MAX_VALUE) + 1;
            c = rand.nextInt(2, q);
            ModularHash m = new ModularHash();
            carterWegmanHash = m.pickHash(q) ;
        }
        @Override
        public int hash(String key) {
            int ans = 0;
            for (int i = 0; i < key.length(); i++)
            {
                ans += HashingUtils.mod((int)(key.charAt(i) * HashingUtils.fastModularPower(c, key.length() - i, q)), q);
            }
            return HashingUtils.mod(ans, q);
        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
