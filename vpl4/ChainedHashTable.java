import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private LinkedList<Pair<K, V>>[] table;
    private int size;

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
        this.hashFunc = hashFactory.pickHash(k);
        table = new LinkedList[capacity];
        for(int i = 0; i < table.length; i++)
            table[i] = new LinkedList<>();
        size = 0;
    }

    public V search(K key) {
        LinkedList<Pair<K, V>> l = table[hashFunc.hash(key)];
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
        size++;
        if(size/capacity > maxLoadFactor)
            extandTable();
    }

    public void extandTable()
    {
        LinkedList<Pair<K, V>>[] temp = table;
        table = new LinkedList[capacity*2];
        hashFunc = hashFactory.pickHash(capacity*2);
        for (int i = 0; i < capacity; i++)
            for (Pair<K, V> p : temp[i])
                insert(p.first(), p.second());
        capacity *= 2;
    }

    public boolean delete(K key) {
        boolean b = table[hashFunc.hash(key)].remove(key);
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
