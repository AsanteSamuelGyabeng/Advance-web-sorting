package model;

public class Student {
    private int id;
    private String name;
    private String email;
    private String schoolName;
    private String grade;

    // Constructors
    public Student(int id, String name, String email, String schoolName, String grade) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.schoolName = schoolName;
        this.grade = grade;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Optional: Override toString() method for better logging and debugging
    @Override
    public String toString() {
        return "Student{id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
