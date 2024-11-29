package algorithms;

import model.Student;
import java.util.*;

public class HeapSort {

    public List<Student> heapSort(List<Student> students, Comparator<Student> comparator) {
        Student[] array = students.toArray(new Student[0]);

        buildHeap(array, comparator);

        for (int i = array.length - 1; i > 0; i--) {
            swap(array, 0, i); // Move max to end
            heapify(array, i, 0, comparator); // Heapify reduced heap
        }

        return Arrays.asList(array);
    }

    private void buildHeap(Student[] array, Comparator<Student> comparator) {
        int n = array.length;
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(array, n, i, comparator);
        }
    }

    private void heapify(Student[] array, int size, int i, Comparator<Student> comparator) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < size && comparator.compare(array[left], array[largest]) > 0) {
            largest = left;
        }

        if (right < size && comparator.compare(array[right], array[largest]) > 0) {
            largest = right;
        }

        if (largest != i) {
            swap(array, i, largest);
            heapify(array, size, largest, comparator);
        }
    }

    private void swap(Student[] array, int i, int j) {
        Student temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
