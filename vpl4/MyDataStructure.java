import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {
    /*
     * You may add any members that you wish to add.
     * Remember that all the data-structures you use must be YOUR implementations,
     * except for the List and its implementation for the operation Range(low, high).
     */
    private IndexableSkipList list;
    private ChainedHashTable<Integer, AbstractSkipList.Node> hashTable;

    private int maximalSize;

    private ModularHash mh;

    /***
     * This function is the Init function described in Part 4.
     *
     * @param N The maximal number of items expected in the DS.
     */
    public MyDataStructure(int N)
    {
        this.list = new IndexableSkipList(0.5);
        this.mh = new ModularHash();
        this.hashTable = new ChainedHashTable<Integer , AbstractSkipList.Node>(mh , (int)Math.round(Math.log(N)) , 1);
        this.maximalSize = N;
    }

    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */
    public boolean insert(int value)
    {
        if(this.list.find(value).key() == value)
            return false;
        AbstractSkipList.Node node = this.list.insert(value);
        this.hashTable.insert(value , node);
        return true;
    }

    public boolean delete(int value)
    {
        AbstractSkipList.Node node = this.hashTable.search(value);
        if(node == null)
            return false;
        return this.hashTable.delete(value) && this.list.delete(node);
    }

    public boolean contains(int value)
    {
        return this.hashTable.search(value)!=null;
    }

    public int rank(int value) {
        return this.list.rank(value);
    }

    public int select(int index) {
        return this.list.select(index);
    }

    public List<Integer> range(int low, int high)
    {
        ArrayList<Integer> L = new ArrayList<Integer>();
        AbstractSkipList.Node node = this.hashTable.search(low);
        L.add(node.key());
        while(node.key()<=high)
        {
            int temp = this.list.successor(node);
            L.add(temp);
            node=this.hashTable.search(temp);
        }
        return L;
    }
}
