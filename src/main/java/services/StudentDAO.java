package services;

import model.Student;
import algorithms.QuickSort;

import java.util.*;

public class StudentDAO {
    private static Map<Integer, Student> studentMap = new HashMap<>();
    private static int idCounter = 3;
    private QuickSort<Student> quickSort = new QuickSort<>();

    static {
        studentMap.put(1, new Student(1, "John Akowah", "john@example.com", "ABC High School", "10"));
        studentMap.put(2, new Student(2, "Jennifer Owusu", "owusu@example.com", "XYZ High School", "12"));
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

    // Sorting Methods
    public List<Student> sortByName() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        quickSort.sort(studentList, Comparator.comparing(Student::getName));
        return studentList;
    }

    public List<Student> sortByGrade() {
        List<Student> studentList = new ArrayList<>(studentMap.values());
        quickSort.sort(studentList, Comparator.comparing(Student::getGrade));
        return studentList;
    }


}
