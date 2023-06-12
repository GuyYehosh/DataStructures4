import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Pair<K, V>[] table;
    private int size;

    /*
     * You should add additional private members as needed.
     */

    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        table = new Pair[capacity];
        size = 0;
    }
    public void extandTable()
    {
        Pair<K, V>[] temp = table;
        table = new Pair[capacity*2];
        for (int i = 0; i < capacity; i++)
            if(temp[i] != null)
                insert(temp[i].first(), temp[i].second());
        capacity *= 2;
    }
    public V search(K key) {
        if (key == null)
            return null;
        int index = hashFunc.hash(key);
        while(table[index] != null && table[index].first() != key)
            index = (index + 1) % capacity;
        if(table[index] == null)
            return null;
        return table[index].second();
    }

    public void insert(K key, V value) {
        Pair p = new Pair<>(key, value);
        int index = hashFunc.hash(key);
        while(table[index] != null)
            index = (index + 1) % capacity;
        table[index] = p;
        size++;
        if(size/capacity > maxLoadFactor)
            extandTable();
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        while(table[index] != null && table[index].first() != key)
            index = (index + 1) % capacity;
        if(table[index] == null)
            return false;
        table[index] = null;
        size--;
        return  true;
    }

    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() { return capacity; }
    public int size() { return size; }
}
