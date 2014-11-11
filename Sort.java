package lab1;

public class Sort {
    
    //================================= sort =================================
    //
    // Input: array A of XYPoints refs 
    //        lessThan is the function used to compare points
    //
    // Output: Upon completion, array A is sorted in nondecreasing order
    //         by lessThan.
    //
    // DO NOT USE ARRAYS.SORT.  WE WILL CHECK FOR THIS.  YOU WILL GET A 0.
    // I HAVE GREP AND I AM NOT AFRAID TO USE IT.
    //=========================================================================
    public static void msort(XYPoint[] A, Comparator lessThan) {
        int size = A.length;
        mergeSort(A, 0, size - 1, lessThan);
        /*Terminal.print("Sorted Points: ");
            for (int h = 0; h < A.length ; h++){
                Terminal.print(A[h] + " , ");
            }
        Terminal.println("end of merge");*/
    }

    public static void mergeSort(XYPoint[] A, int beg, int end, Comparator lessThan){
        if (beg < end){
            int mid = (beg + end) / 2;
            mergeSort(A, beg, mid, lessThan);
            mergeSort(A, mid + 1, end, lessThan);
            merge(A, beg, mid, end, lessThan);  
        }
        else{

        }
    }

    public static void merge(XYPoint[] A, int beg, int mid, int end, Comparator lessThan){
        XYPoint[] temp = new XYPoint[end - beg + 1];
        int i = beg;
        int j = mid + 1;
        int k = 0;

        while(i <= mid && j <= end) {

            if(lessThan.comp(A[i], A[j])){
                temp[k] = A[i];
                i++;
            }

            else {
                temp[k] = A[j];
                j++;
            }

            k++;
        }

        if(i <= mid) {
            while (i <= mid) {
                temp[k] = A[i];
                i++;
                k++;
            }
        }
        else if (j <= end) {
            while (j <= end){
                temp[k] = A[j];
                j++;
                k++;
            }
        }

        for (int s = 0; s < temp.length ; s++){
            A[beg + s] = temp[s];
        }
    }
}
