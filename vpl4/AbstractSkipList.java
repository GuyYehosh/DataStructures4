import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected Node head;
    final protected Node tail;

    public AbstractSkipList() {
        head = new Node(Integer.MIN_VALUE);
        head.sentinel();
        tail = new Node(Integer.MAX_VALUE);
        tail.sentinel();
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null, -1);
        tail.addLevel(null, head, -1);
    }

    abstract Node find(int key);

    abstract public int generateHeight();

    public Node search(int key) {
        Node curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public Node insert(int key) {
        int nodeHeight = generateHeight();

        while (nodeHeight > head.height()) {
            increaseHeight();
        }

        Node prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        Node newNode = new Node(key);
        int stepsBack = 0; //+
        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {

            Node nextNode = prevNode.getNext(level);

            newNode.addLevel(nextNode, prevNode, stepsBack);

            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);

            if(!nextNode.IsSentinel()) //+
                nextNode.setSkip(nextNode.getSkips(level) - stepsBack, level); //+

            while (prevNode != null && prevNode.height() == level) {
                stepsBack += prevNode.getSkips(level)+1;
                prevNode = prevNode.getPrev(level);
            }
        }

        return newNode;
    }

    public boolean delete(Node node) {
        for (int level = 0; level <= node.height(); ++level) {
            Node prev = node.getPrev(level);
            Node next = node.getNext(level);
            prev.setNext(level, next);
            next.setPrev(level, prev);
            if(!next.IsSentinel()) //+
                next.setSkip(next.getSkips(level)+ node.getSkips(level), level); //+
        }

        return true;
    }

    public int predecessor(Node node) {
        return node.getPrev(0).key();
    }

    public int successor(Node node) {
        return node.getNext(0).key();
    }

    public int minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0).key();
    }

    public int maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0).key();
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        Node curr = head.getNext(level);

        while (curr != tail) {
            s.append(curr.key);
            s.append("    ");

            curr = curr.getNext(level);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class Node {
        final private List<Node> next;
        final private List<Node> prev;
        private int height;
        final private int key;
        private List<Integer> skips; //+
        private boolean isSentinel; //+


        public Node(int key) {
            next = new ArrayList<>();
            prev = new ArrayList<>();
            this.height = -1;
            this.key = key;
            this.skips = new ArrayList<Integer>(); //+
            isSentinel = false; //+
        }

        public int getSkips(int level) //+
        {
            return skips.get(level);
        }

        public void addSkip(int skip) //+
        {
            this.skips.add(skip);
        }

        public void setSkip(int skip , int level) //+
        {
            this.skips.set(level, skip);
        }

        public boolean IsSentinel(){return this.isSentinel;} //+

        public void sentinel(){this.isSentinel =true;} //+

        public Node getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public Node getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, Node next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, Node prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(Node next, Node prev, int stepsBack) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
            this.addSkip(stepsBack); //+
        }

        public int height() { return height; }
        public int key() { return key; }
    }
}
