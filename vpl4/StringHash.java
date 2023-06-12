import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringHash implements HashFactory<String> {
    Functor[] func;
    public StringHash() {
        func = new Functor[5];
    }

    @Override
    public HashFunctor<String> pickHash(int k) {
        Random random = new Random();
        for(int i = 0; i < 5; i++)
            func[i] = new Functor(k);
        return func[random.nextInt(0, 5)];
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
            carterWegmanHash = m.pickHash(k) ;
        }
        @Override
        public int hash(String key) {
            int ans = 0;
            for (int i = 0; i < key.length(); i++)
            {
                ans += HashingUtils.mod((int)(key.charAt(i) * HashingUtils.fastModularPower(c, key.length() - i, q)), q);
            }
            return carterWegmanHash.hash(HashingUtils.mod(ans, q));
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
