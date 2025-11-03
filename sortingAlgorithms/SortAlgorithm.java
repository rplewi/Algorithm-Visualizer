package sortingAlgorithms;

import java.util.Map;
import java.util.Set;

public interface SortAlgorithm {
    void reset(int[] array); // Gives updated array
    boolean step(); // Does one step, then returns true when done to be displayed on the visualizer.
    
    /**
     * Returns a map of visualization state.
     * Keys are color category names (e.g., "pivot", "current", "compare", "sorted")
     * Values are sets of indices to highlight with that color.
     */
    Map<String, Set<Integer>> getVisualizationState();
}