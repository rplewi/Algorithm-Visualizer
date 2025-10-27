package sortingAlgorithms;
public interface SortAlgorithm {
    void reset(int[] array); // Gives updated array
    boolean step(); // Does one step, then returns true when done to be displayed on the visualizer.
    public String getName();
    public long getDelay();
    public void setDelay(long delay);
    public void runSort(MyFrame array);
}