package sortingAlgorithms;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class quickSort implements SortAlgorithm{
    private int[] array;
    private boolean isDone = false;
    private Stack<int[]> stack;   // stores subarray ranges
    private int[] currentRange; 
    private Set<Integer> highlighted = new HashSet<>();
    private Set<Integer> pivotIndices = new HashSet<>();
    private Set<Integer> currentIndices = new HashSet<>();
    private Set<Integer> partitionIndices = new HashSet<>();
    private Set<Integer> rangeIndices = new HashSet<>();
    
    // State for step-by-step partition
    private int partitionLow;
    private int partitionHigh;
    private int partitionJ;
    private int partitionI;
    private int partitionPivot;
    private boolean isPartitioning = false;
    
    // State for celebration animation
    private boolean isCelebrating = false;
    private int celebrationIndex = 0;

    public void highlight(int index) {
        highlighted.add(index);
    }

    public void clearHighlight(int index) {
        highlighted.remove(index);
    }

    public Set<Integer> getHighlighted() {
        return highlighted;
    }
    
    public Set<Integer> getPivotIndices() {
        return pivotIndices;
    }
    
    public Set<Integer> getCurrentIndices() {
        return currentIndices;
    }
    
    public Set<Integer> getPartitionIndices() {
        return partitionIndices;
    }
    
    public Set<Integer> getRangeIndices() {
        return rangeIndices;
    }

    public void reset(int[] array){
        this.array = array;
        this.stack = new Stack<>();
        this.stack.push(new int[]{0, array.length - 1});
        this.isDone = false;
        this.isPartitioning = false;
        this.isCelebrating = false;
        this.celebrationIndex = 0;
        highlighted.clear();
        pivotIndices.clear();
        currentIndices.clear();
        partitionIndices.clear();
        rangeIndices.clear();
        // initializes array.
    }

    public boolean step(){
        // Handle celebration animation
        if (isCelebrating) {
            return handleCelebration();
        }
        
        if (stack.isEmpty() && !isPartitioning){
            // Start celebration instead of returning true immediately
            isCelebrating = true;
            isDone = true;
            celebrationIndex = 0;
            highlighted.clear();
            return false; // Continue animation
        }
        
        // If we're in the middle of partitioning, continue the partition
        if (isPartitioning) {
            return continuePartition();
        }
        
        // Otherwise, start a new partition
        int[] range = stack.pop();
        partitionLow = range[0];
        partitionHigh = range[1];

        if (partitionLow < partitionHigh) {
            // Start partition
            isPartitioning = true;
            partitionJ = partitionLow;
            partitionI = partitionLow - 1;
            partitionPivot = array[partitionHigh];
            
            // Clear previous highlights
            highlighted.clear();
            pivotIndices.clear();
            currentIndices.clear();
            partitionIndices.clear();
            rangeIndices.clear();
            
            // Highlight the range being partitioned
            for (int i = partitionLow; i <= partitionHigh; i++) {
                rangeIndices.add(i);
            }
            
            // Highlight pivot
            pivotIndices.add(partitionHigh);
            
            currentRange = new int[]{partitionLow, partitionHigh};
            return false;
        }
        
        return false;
    }
    
    private boolean handleCelebration() {
        if (celebrationIndex >= array.length) {
            // Celebration complete, clear highlights
            currentIndices.clear();
            return true; // Really done now
        }
        
        // Clear previous highlight and add current
        currentIndices.clear();
        currentIndices.add(celebrationIndex);
        celebrationIndex++;
        
        return false; // Continue celebration
    }
    
    private boolean continuePartition() {
        // Clear previous current and partition highlights
        currentIndices.clear();
        partitionIndices.clear();
        
        // Perform comparison
        if (partitionJ < partitionHigh) {
            // Highlight current element being compared (j)
            currentIndices.add(partitionJ);
            
            // Highlight partition position (i+1) where elements will go
            if (partitionI >= partitionLow) {
                partitionIndices.add(partitionI + 1);
            }
            
            if (array[partitionJ] <= partitionPivot) {
                partitionI++;
                swap(array, partitionI, partitionJ);
                // Update partition position after swap
                partitionIndices.clear();
                if (partitionI >= partitionLow) {
                    partitionIndices.add(partitionI + 1);
                }
            }
            partitionJ++;
            return false; // Partition not done
        } else {
            // Partition complete
            swap(array, partitionI + 1, partitionHigh);
            int pi = partitionI + 1;
            
            // Highlight final pivot position
            pivotIndices.clear();
            pivotIndices.add(pi);
            
            // Clear other highlights
            currentIndices.clear();
            partitionIndices.clear();
            rangeIndices.clear();
            
            // Push subarrays
            stack.push(new int[]{pi + 1, partitionHigh});
            stack.push(new int[]{partitionLow, pi - 1});
            
            isPartitioning = false;
            return false;
        }
    }

    private void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
    public int[] getCurrentRange() {
        return currentRange;
    }
}