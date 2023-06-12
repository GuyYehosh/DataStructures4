import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class SkipListExperimentUtils {
    //returns the average levels generated from x height generations with probabillity p
    public static double measureLevels(double p, int x)
    {
        IndexableSkipList list = new IndexableSkipList(p);
        double averageHeights=0.0;
        for(int i=1 ; i<=x ; i++)
            averageHeights+= list.generateHeight();
        averageHeights=averageHeights/x;
        return averageHeights+1.0;
    }

    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        Random rand = new Random();
        IndexableSkipList list = new IndexableSkipList(p);
        ArrayList<Integer> items = new ArrayList<Integer>(size+1);
        ArrayList<Integer> shuffled = new ArrayList<Integer>(size+1);

        for(int i = 0; i <= size; i++) // initialize items for insertion
            items.add(2*i);

        while(items.size() > 0) { // shuffle
            int index = rand.nextInt(items.size());
            shuffled.add(items.remove(index));
        }

        //start measurement
        double sum=0;
        for(int item : shuffled)
        {
            long start = System.currentTimeMillis();
            list.insert(item);
            long finish = System.currentTimeMillis();
            sum+=(finish-start);
        }


        double avgTime = sum / (size+1);
        return new Pair<AbstractSkipList, Double>(list, avgTime);
    }

    public static double measureSearch(AbstractSkipList skipList, int size) {
        ArrayList<Integer> items = new ArrayList<Integer>(size+1);
        ArrayList<Integer> shuffled = new ArrayList<Integer>(size+1);
        Random rand = new Random();
        for(int i = 0; i <= size; i++) //initialize items for search
            items.add(2*i);
        while(items.size() > 0) { //shuffle
            int index = rand.nextInt(items.size());
            shuffled.add(items.remove(index));
        }

        double sum=0;
        for(int item : shuffled) {
            long start = System.currentTimeMillis();
            skipList.search(item); // successful search
            skipList.search(-item); // unsuccessful search
            long finish = System.currentTimeMillis();
            sum+=(finish-start);
        }


        return (sum / (2*(size+1)));
    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        ArrayList<Integer> items = new ArrayList<Integer>(size+1);
        ArrayList<AbstractSkipList.Node> shuffled = new ArrayList<AbstractSkipList.Node>(size+1);
        Random rand = new Random();
        for(int i = 0; i <= size; i++) //initialize items for deletion
            items.add(2*i);
        while(items.size() > 0) { //shuffle
            int index = rand.nextInt(items.size());
            shuffled.add(skipList.find(items.remove(index)));
        }

        double sum=0;
        for(AbstractSkipList.Node item : shuffled) {
            long start = System.currentTimeMillis();
            skipList.delete(item); // successful deletion
            long finish = System.currentTimeMillis();
            sum+=(finish-start);
        }


        return (sum / (size+1));
    }

    public static void Task2_2()
    {
        //p ∈ {0.33, 0.5, 0.75, 0.9}
        //x ∈ {10, 100, 1000, 10000}

        long start = System.currentTimeMillis();

        double[] probability = {0.33, 0.5, 0.75, 0.9};
        int[] iterations = {10, 100, 1000, 10000};
        double sumLevelsForX;
        double expectedLevel;
        for (double p:probability)
        {
            expectedLevel = 1/p;
            System.out.println("Table " + p +" , expectedLevel = "+expectedLevel);
            for (int x: iterations) {
                sumLevelsForX=0;
                System.out.println("for x="+x);
                for (int i = 1 ; i<=5 ; i++)
                {
                    double ml = measureLevels(p, x);
                    System.out.println("l" + i + " = " + ml);
                    sumLevelsForX+=ml;
                }
                System.out.println("average delta: "+((sumLevelsForX-5*expectedLevel)/5));
            }
        }
        // ...
        long finish = System.currentTimeMillis();
        System.out.println("time: " + (finish - start));
    }

    public static void question2_6()
    {
        //Fill the following table for each p ∈ {0.33, 0.5, 0.75, 0.9} with the average of 30
        //experiments for each value of x ∈ {1000, 2500, 5000, 10000, 15000, 20000, 50000}:
        DecimalFormat df2 = new DecimalFormat("0.00");
        DecimalFormat df4 = new DecimalFormat("0.0000");
        DecimalFormat df6 = new DecimalFormat("0.000000");
        double[] prob = {0.33, 0.5, 0.75, 0.9};
        int[] iterations = {1000, 2500, 5000, 10000, 15000, 20000, 50000};
        for(double p : prob)
        {
            System.out.println("table: "+p);
            for(int x : iterations)
            {
                System.out.print("x="+x+" : ");
                double avgInsertions = 0;
                double avgSearches = 0;
                double avgDeletions = 0;
                for(int i=1 ; i<=30 ; i++)//"experiments"
                {
                    Pair<AbstractSkipList, Double> measureInsertions = measureInsertions(p , x);
                    avgInsertions += measureInsertions.second();
                    avgSearches += measureSearch(measureInsertions.first(), x);
                    avgDeletions += measureDeletions(measureInsertions.first(), x);
                }
                avgInsertions=avgInsertions/30;
                avgSearches=avgSearches/30;
                avgDeletions=avgDeletions/30;

                System.out.println(df6.format(avgInsertions*1000000)+" & "+df6.format(avgSearches*1000000)+" & "+df6.format(avgDeletions*1000000));
            }
        }
    }
    public static void main(String[] args) {
        IndexableSkipList l = new IndexableSkipList(0.3);
        for(int i = 0; i<=9 ; i++)
            l.insert(i);
        System.out.println(l.toString());
        //System.out.println("head height: " + l.head.height());;
        //for(int i = 0 ; i <= 9 ; i++)
        //System.out.println(l.rank(i));
        System.out.println(l.select(6));
        //AbstractSkipList.Node node = l.find(8);
        //for(int i=node.height() ; i>=0 ; i--)
        //System.out.println(node.getSkips(i));

    }
}
