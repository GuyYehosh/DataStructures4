import java.util.Iterator;
import java.util.ArrayList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private ArrayList<Pair<K, V>>[] table;
    private int size;
    private int k;

    /*
     * You should add additional private members as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.k = k;
        this.hashFunc = hashFactory.pickHash(k);
        table = new ArrayList[capacity];
        for(int i = 0; i < table.length; i++)
            table[i] = new ArrayList<Pair<K, V>>();
        size = 0;
    }

    public V search(K key) {
        ArrayList<Pair<K, V>> l = table[hashFunc.hash(key)];
        Iterator<Pair<K, V>> it = l.iterator();
        while(it.hasNext())
        {
            Pair<K, V> p = it.next();
            if(p.first().equals(key))
                return p.second();
        }
        return null;
    }

    public void insert(K key, V value) {
        Pair p = new Pair<>(key, value);
        table[hashFunc.hash(key)].add(p);
        size+=1;
        if((double)size/capacity >= maxLoadFactor)
            rehash();
    }

    public void rehash()
    {
        ArrayList<Pair<K , V>> tempList = new ArrayList<>();
        for(ArrayList<Pair<K , V>> l: this.table)
        {
            tempList.addAll(l);
        }
        k+=1;
        capacity = (int)Math.pow(2 , k);
        this.table = new ArrayList[this.capacity];
        for(int i = 0; i < table.length; i++)
            table[i] = new ArrayList<>();
        this.hashFunc = hashFactory.pickHash(k);
        this.size = 0;
        for (Pair<K, V> p : tempList)
            this.insert(p.first() , p.second());
    }

    public boolean delete(K key) {
        Pair<K, V> toRem = new Pair<>(key, search(key));
        ArrayList<Pair<K, V>> l = table[hashFunc.hash(key)];
        for (Pair<K, V> p : l) {
            if(p.first().equals(key))
                toRem = p;
        }
        boolean b = table[hashFunc.hash(key)].remove(toRem);
        if(b)
            size--;
        return b;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }

    public int size() { return size; }
}
