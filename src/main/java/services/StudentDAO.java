package services;
import algorithms.*;
import model.Student;

import java.util.*;

public class StudentDAO {
    private static Map<Integer, Student> studentMap = new HashMap<>();
    private static int idCounter = 1;
    private QuickSort<Student> quickSort = new QuickSort<>();
    private HeapSort heapSort = new HeapSort();
    private MergeSort<Student> mergeSort = new MergeSort<>();
    private RadixSort radixSort = new RadixSort();
    private BucketSort bucketSort = new BucketSort();

    /**
     * preloaded sample student details
     */
    static {
        studentMap.put(1, new Student(1, "John Akowah", "john@example.com", "Prempeh High School", "10"));
        studentMap.put(2, new Student(2, "Jennifer Owusu", "owusu@example.com", "Prempeh High School", "12"));
        studentMap.put(3, new Student(3, "Elvis Marfo", "el@marfo.com", "Prempeh High School", "10"));
        studentMap.put(4, new Student(4, "Samuel Asante", "owusu@example.com", "Prempeh High School", "12"));
        studentMap.put(5, new Student(5, "John Mahama", "john@example.com", "Prempeh High School", "10"));
        studentMap.put(6, new Student(6, "Nicholas Owusu", "owusu@example.com", "Prempeh High School", "12"));
    }

    /**
     * @getStudent
     * @param id
     * @return
     */
    public Student getStudent(int id) {
        return studentMap.get(id);
    }

    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    /**
     *@createStudent actual logic that creates new creates or register new user
     */

    public void createStudent(Student student) {
        int newId = getNextId();
        student.setId(newId);
        studentMap.put(newId, student);
    }

    /**
     * updateStudents logic do update a student
     * @param student
     * @return
     */
    public boolean updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return true;
        }
        return false;
    }

    /**
     * @deleteStudent deletes a student from the hashmap
     * @param id
     * @return
     */
    public boolean deleteStudent(int id) {
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            return true;
        }
        return false;
    }

    public int getNextId() {
        return idCounter++;
    }

    /**
     * Quick sort methods
     * @return
     */
    public List<Student> quicksortByName() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        quickSort.sort(studentList, Comparator.comparing(Student::getName));
        return studentList;
    }

    public List<Student> quicksortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        quickSort.sort(studentList, Comparator.comparing(Student::getGrade));
        return studentList;
    }

    /**
     * Heap Sort methods
     */
    public List<Student> heapSortByName() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        return heapSort.heapSort(studentList, Comparator.comparing(Student::getName));
    }

    public List<Student> heapSortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        return heapSort.heapSort(studentList, Comparator.comparing(Student::getGrade));
    }

    /**
     * Merge Sort methods
     */
    public List<Student> mergeSortByName() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        return mergeSort.mergeSort(studentList, Comparator.comparing(Student::getName));
    }

    public List<Student> mergeSortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        return mergeSort.mergeSort(studentList, Comparator.comparing(Student::getGrade));
    }

    /**
     * Radix Sort by Grade
     */
    public List<Student> radixSortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        List<Integer> grades = new ArrayList<>();
        for (Student student : studentList) {
            grades.add(Integer.parseInt(student.getGrade()));
        }
        List<Integer> sortedGrades = radixSort.radixSort(grades);

        List<Student> sortedStudents = new ArrayList<>();
        for (int grade : sortedGrades) {
            for (Student student : studentList) {
                if (Integer.parseInt(student.getGrade()) == grade && !sortedStudents.contains(student)) {
                    sortedStudents.add(student);
                    break;
                }
            }
        }
        return sortedStudents;
    }

    /**
     * Bucket Sort by Grade
     */
    public List<Student> bucketSortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        List<Integer> grades = new ArrayList<>();
        for (Student student : studentList) {
            grades.add(Integer.parseInt(student.getGrade()));
        }
        List<Integer> sortedGrades = bucketSort.bucketSort(grades);

        List<Student> sortedStudents = new ArrayList<>();
        for (int grade : sortedGrades) {
            for (Student student : studentList) {
                if (Integer.parseInt(student.getGrade()) == grade && !sortedStudents.contains(student)) {
                    sortedStudents.add(student);
                    break;
                }
            }
        }
        return sortedStudents;
    }
}
