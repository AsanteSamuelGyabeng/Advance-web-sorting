package services;

import model.Student;
import java.util.*;

public class StudentDAO {
    private static Map<Integer, Student> studentMap = new HashMap<>();
    private static int idCounter = 3;

    // Initialize with some students
    static {
        studentMap.put(1, new Student(1, "John Doe", "john@example.com", "ABC High School", "10"));
        studentMap.put(2, new Student(2, "Jane Smith", "jane@example.com", "XYZ High School", "12"));
    }

    // Retrieve a student by ID
    public Student getStudent(int id) {
        return studentMap.get(id);
    }

    // Retrieve all students
    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    // Create a new student
    public void createStudent(Student student) {
        // Assign a new ID and add to the map
        int newId = getNextId();
        student.setId(newId);
        studentMap.put(newId, student);
    }

    // Update an existing student
    public boolean updateStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            studentMap.put(student.getId(), student);
            return true;
        }
        return false; // Student not found
    }

    // Delete a student by ID
    public boolean deleteStudent(int id) {
        if (studentMap.containsKey(id)) {
            studentMap.remove(id);
            return true;
        }
        return false; // Student not found
    }

    // Generate the next ID for new students
    public int getNextId() {
        return idCounter++;
    }
}
