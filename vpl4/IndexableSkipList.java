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
        while(Math.random()>=this.probability)//>= becaue Math.random is in range [0,1)
            height+=1;
        return height+1;//including
    }

    public int rank(int val) {
        Node infimum = find(val);
        Node prev = infimum.getPrev(0);
        if(prev == null)
            return 0;
        int rank = 0 ;
        for(int level=0 ; level<=this.head.height() ; level++)
        {
            while(prev.getPrev(level)!=null && prev.height() == level)
            {
                rank+=prev.getSkips(level);
                prev = prev.getPrev(level);
                rank+=1;
            }
        }

        return rank+1;
    }

    public int select(int index) {
        int i=0;
        Node iterator = this.head;
        for(int height = this.head.height() ; height>=0 ; height--)
        {
            while(iterator.getNext(height)!=null && !iterator.getNext(height).IsSentinel() && i+iterator.getNext(height).getSkips(height)+1<=index)
            {
                i+=iterator.getNext(height).getSkips(height)+1;
                iterator = iterator.getNext(height);
            }
        }
        return iterator.key();
    }
}
