/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing3d;

import java.util.ArrayList;

/**
 *
 * @author marcus
 */
public class MergeSort {
        /**
     * This is a helper method that handles the merging of two sorted sublists
     * in mergeSort. It loops from i to max, each time choosing the value at
     * index i from either the first or second sorted sublist. The value chosen
     * will be whichever value is lowest. Once it has finished selecting all
     * values from either the first or second sublist, the rest of the merge is
     * completed by filling in all of the remaining values from the other
     * sublist.
     *
     * @param unmergedList the unmerged ArrayList. It should have two unmerged,
     * sorted parts to be merged together.
     * @param min the index of the beginning of the first sorted part.
     * @param mid the index of the beginning of the second sorted part.
     * @param max the index ( +1 ) of the end of the second sorted part.
     * @return an ArrayList that is sorted in ascending order (for indices min
     * to max).
     */
    public static ArrayList<Double> merge(ArrayList<Double> unmergedList,
            int min, int mid, int max) {
        ArrayList<Double> mergedList = (ArrayList<Double>) unmergedList.clone();
        int j = min;
        int k = mid;
        int i = min;
        while (j < mid && k < max) {
            if (unmergedList.get(j) < unmergedList.get(k)) {
                mergedList.set(i, unmergedList.get(j));
                j++;
                i++;
            } else {
                mergedList.set(i, unmergedList.get(k));
                k++;
                i++;
            } //if-else
        } //while

        while (j < mid) {
            mergedList.set(i, unmergedList.get(j));
            j++;
            i++;
        } //while

        while (k < max) {
            mergedList.set(i, unmergedList.get(k));
            k++;
            i++;
        } //while
        return mergedList;
    } //merge

    /**
     * This method performs a merge sort on an ArrayList of Doubles, using
     * recursion.
     *
     * @param unsortedList the ArrayList of Doubles to be sorted.
     * @param min the minimum index to sort from. At the top level, this will
     * usually be index 0. For recursion within the method, it could take any
     * index in the unsorted list.
     * @param max the maximum index to sort to. At the top level, this will
     * usually the size of the ArrayList. For recursion within the method, it
     * could take any index in the unsorted list.
     * @return a sorted ArrayList (for indices min to max).
     */
    public static ArrayList<Double> mergeSort(ArrayList<Double> unsortedList,
            int min, int max) {
        if (min == max - 1) {
            return unsortedList;
        }
        int mid = (max + min) / 2;
        ArrayList<Double> sortedList = mergeSort(unsortedList, min, mid);
        sortedList = mergeSort(sortedList, mid, max);
        sortedList = merge(sortedList, min, mid, max);
        return sortedList;
    }
    
}
