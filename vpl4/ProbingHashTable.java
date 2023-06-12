public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    private int capacity;
    private HashFunctor<K> hashFunc;
    private Pair<K, V>[] table;
    private int size;
    private int k;

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
        this.k = k;
        this.hashFunc = hashFactory.pickHash(k);
        table = new Pair[capacity];
        size = 0;
    }
    public void rehash()
    {
        Pair<K, V>[] temp = new Pair[capacity];
        for(int i = 0; i < capacity; i++) {
            temp[i] = table[i];
        }
        k+=1;
        capacity = 1 << k;
        hashFunc = hashFactory.pickHash(k);
        table = new Pair[capacity];
        size = 0;
        for (int i = 0; i < temp.length; i++)
            if(temp[i] != null)
                insert(temp[i].first(), temp[i].second());

    }
    public V search(K key) {
        if (key == null)
            return null;
        int index = hashFunc.hash(key);
        while(table[index] != null && table[index].first() != key)
            index = HashingUtils.mod(index + 1 , capacity);
        if(table[index] == null)
            return null;
        return table[index].second();
    }

    public void insert(K key, V value) {
        Pair p = new Pair<>(key, value);
        int index = hashFunc.hash(key);
        while(table[index] != null)
            index = HashingUtils.mod(index + 1 , capacity);
        table[index] = p;
        size+=1;
        if((double)size/capacity >= maxLoadFactor)
            rehash();
    }

    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        while(table[index] != null && table[index].first() != key)
            index = HashingUtils.mod(index + 1 , capacity);
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
