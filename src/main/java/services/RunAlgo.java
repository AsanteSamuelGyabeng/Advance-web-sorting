package services;

public class RunAlgo {
    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        System.out.println("\nSorted by Name:");
        studentDAO.sortByName().forEach(System.out::println);
    }
}
