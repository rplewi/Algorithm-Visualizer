package sortingAlgorithms;

public class quickSort implements SortAlgorithm{
    private int[] array;
    private boolean isDone = false;
    


    public void reset(int[] array){
        this.array = array;
        isDone = false;
        // initializes array.
    }

    public boolean step(){
        if (!isDone){
            sort(array, 0, array.length-1);
            isDone = true;
        }
        return isDone;
    }

    int partition(int a[], int low, int high){
        int pivot = a[high];
        int i = (low-1);
        for (int j = low; j < high; j++){
            if (a[j] <= pivot){
                i++;
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;
            }
        }
        int temp = a[i+1];
        a[i+1] = a[high];
        a[high] = temp;
        return i+1;
    }

    void sort(int a[], int l, int h){
        if (l < h){
            int pi = partition(a, l, h);
            sort(a, l, pi-1);
            sort(a, pi+1, h);
        }
    }
}