package sortingAlgorithms;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class insertionSort implements SortAlgorithm {
    private int[] array;
    private int i = 1, j;
    private boolean isDone = false;
    private Set<Integer> highlighted = new HashSet<>();
    private Set<Integer> pivotIndices = new HashSet<>();
    private Set<Integer> currentIndices = new HashSet<>();
    private Set<Integer> partitionIndices = new HashSet<>();
    private Set<Integer> rangeIndices = new HashSet<>();
    boolean hasKey = false;
    boolean isScanning = false;
    int key;

    public boolean step(){
        // Need state and flag management because of While loop condition, instead of just for loop i and j incrementation, you will need a boolean flag that can be checked, just like a while loop!
        int n = array.length;
        if (i >= n) {return true;} // sorting is done, so return true.
        if (!hasKey){
            key = array[i]; // Current item being inserted.
            j = i - 1; // Start scanning for element before current spot.
            hasKey = true; // flag to say that we have a key wanting to be inserted.
            isScanning = true; // tells us that now we are looking for a place to put it.
            return false;
        }
        if (isScanning){
            pivotIndices.clear();
            pivotIndices.add(i); // Adds insertion element
            pivotIndices.add(j); // Current checking element.
            if (j >= 0 && array[j] > key){
                array[j + 1] = array[j]; // Shifts our element at j to the right, which is j+1.
                j--;
                return false;
            } else {
                array[j + 1] = key; // inserting key at right spot.
                i++;
                hasKey = false; // key inserted, so its "gone" now, and ready for next element.
                isScanning = false; // found place to put key, so were done scanning.
                return false;
            }
        }
        return true;
    }
    public void reset(int[] arr){
        isDone = false;
        this.array = arr;
        i = 1;
        highlighted.clear();
        pivotIndices.clear();
        currentIndices.clear();
        partitionIndices.clear();
        rangeIndices.clear();
    }

    public Map<String, Set<Integer>> getVisualizationState(){
        Map<String, Set<Integer>> state = new HashMap<>();
        state.put("pivot", pivotIndices);
        state.put("current", currentIndices);
        state.put("partition", partitionIndices);
        state.put("range", rangeIndices);
        return state;
    }
}
