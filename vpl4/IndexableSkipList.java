import java.util.Random;
public class IndexableSkipList extends AbstractSkipList {
    final protected double probability;
    public IndexableSkipList(double probability) {
        super();
        this.probability = probability;
    }

    @Override
    public Node find(int val)
    {
        Node ptr = this.head;
        for (int level = ptr.height() ; level>=0 ; level--)
           while(ptr.getNext(level)!=null && ptr.getNext(level).key()<=val)
               ptr=ptr.getNext(level);
        return ptr;
    }

    @Override
    public int generateHeight()//l
    {
        int height=0;
        Random r = new Random();
        while(Math.random()>=this.probability)
            height+=1;
        return height+1;//including
    }

    public int rank(int val) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }

    public int select(int index) {
        throw new UnsupportedOperationException("Replace this by your implementation");
    }
}
