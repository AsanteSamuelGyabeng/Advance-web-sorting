package algorithms;

import model.Student;
import java.util.*;

public class HeapSort {

    // HeapSort logic
    public List<Student> heapSort(List<Student> students, Comparator<Student> comparator) {
        // Convert list to array for easier heap management
        Student[] array = students.toArray(new Student[0]);

        // Build max heap
        buildHeap(array, comparator);

        // Perform heap sort
        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i); // Move max to end
            heapify(array, i, 0, comparator); // Heapify reduced heap
        }

        // Convert back to list and return
        return Arrays.asList(array);
    }

    // Build initial max heap
    private void buildHeap(Student[] array, Comparator<Student> comparator) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i, comparator);
        }
    }

    // Heapify subtree rooted at index `i`
    private void heapify(Student[] array, int size, int i, Comparator<Student> comparator) {
        int largest = i; // Root
        int left = 2 * i + 1; // Left child
        int right = 2 * i + 2; // Right child

        // Check if left child is larger
        if (left < size && comparator.compare(array[left], array[largest]) > 0) {
            largest = left;
        }

        // Check if right child is larger
        if (right < size && comparator.compare(array[right], array[largest]) > 0) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            swap(array, i, largest);
            // Recursively heapify the affected subtree
            heapify(array, size, largest, comparator);
        }
    }

    // Swap two elements in the array
    private void swap(Student[] array, int i, int j) {
        Student temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
