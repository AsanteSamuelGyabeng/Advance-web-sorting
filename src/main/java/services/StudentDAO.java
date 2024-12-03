package services;
import model.Student;

import algorithms.HeapSort;
import algorithms.QuickSort;

import java.util.*;

public class StudentDAO {
    private static Map<Integer, Student> studentMap = new HashMap<>();
    private static int idCounter = 1;
    private QuickSort<Student> quickSort = new QuickSort<>();
    private HeapSort heapSort = new HeapSort();


    static {
        studentMap.put(1, new Student(1, "John Akowah", "john@example.com", "Prempeh High School", "10"));
        studentMap.put(2, new Student(2, "Jennifer Owusu", "owusu@example.com", "Prempeh High School", "12"));
        studentMap.put(1, new Student(3, "Elvis Marfo", "el@marfo.com", "Prempeh High School", "10"));
        studentMap.put(2, new Student(4, "Samuel Asante", "owusu@example.com", "Prempeh High School", "12"));
        studentMap.put(1, new Student(5, "John Mahama", "john@example.com", "Prempeh High School", "10"));
        studentMap.put(2, new Student(6, "Nicholas Owusu", "owusu@example.com", "Prempeh High School", "12"));
    }

    public Student getStudent(int id) {
        return studentMap.get(id);
    }

    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    public void createStudent(Student student) {
        int newId = getNextId();
        student.setId(newId);
        studentMap.put(newId, student);
    }

    public boolean updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return true;
        }
        return false;
    }

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


}
