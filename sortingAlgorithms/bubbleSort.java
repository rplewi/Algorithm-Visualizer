package sortingAlgorithms;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class bubbleSort implements SortAlgorithm {
    private int[] array;
    private int i = 0, j = 0;
    private boolean isDone = false;
    private Set<Integer> highlighted = new HashSet<>();
    private Set<Integer> pivotIndices = new HashSet<>();
    private Set<Integer> currentIndices = new HashSet<>();
    private Set<Integer> partitionIndices = new HashSet<>();
    private Set<Integer> rangeIndices = new HashSet<>();

    public boolean step(){
        int n = array.length;
        if (!isDone){
            currentIndices.clear();
            // When next iteration starts, and checks if its done is when to clear it.
            if(j < n - i - 1){
                if (array[j] > array[j + 1]){
                    currentIndices.add(j);
                    currentIndices.add(j+1); // Highlights yellow the two numbers being compared, should be correct.
                    swap(j, j + 1);
                }
                j++;
                return false;
            }
            if(i < n - 1){
                i++;
                j = 0;
                
                return false;
            } else {
                isDone = true;
                
                return true;
            }
        }
        return true;
    }
    
    public void reset(int[] arr){
        isDone = false;
        this.array = arr;
        i = 0;
        j = 0;
        highlighted.clear();
        pivotIndices.clear();
        currentIndices.clear();
        partitionIndices.clear();
        rangeIndices.clear();
    }
    
    private void swap(int i, int j) {
        int temp = 0;
        temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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
