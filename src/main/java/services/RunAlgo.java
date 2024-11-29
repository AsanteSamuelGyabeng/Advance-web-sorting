package services;

import model.Student;

import java.util.List;

public class RunAlgo {

    public static String getSortedResults() {
        StudentDAO studentDAO = new StudentDAO();
        StringBuilder result = new StringBuilder();

        result.append("\nQuick Sorted by Name:\n");
        List<Student> quickSorted = studentDAO.quicksortByName();
        for (Student student : quickSorted) {
            result.append(student.toString()).append("\n");
        }

        result.append("\nHeap Sorted by Name:\n");
        List<Student> heapSorted = studentDAO.heapSortByName();
        for (Student student : heapSorted) {
            result.append(student.toString()).append("\n");
        }

        result.append("\nMerge Sorted by Name:\n");
        List<Student> mergeSorted = studentDAO.mergeSortByName();
        for (Student student : mergeSorted) {
            result.append(student.toString()).append("\n");
        }

        result.append("\nRadix Sorted by Age:\n");
        List<Student> radixSorted = studentDAO.radixSortByGrade();
        for (Student student : radixSorted) {
            result.append(student.toString()).append("\n");
        }

        result.append("\nBucket Sorted by Age:\n");
        List<Student> bucketSorted = studentDAO.bucketSortByGrade();
        for (Student student : bucketSorted) {
            result.append(student.toString()).append("\n");
        }

        return result.toString();
    }
}
