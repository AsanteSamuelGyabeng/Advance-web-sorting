package services;

public class RunAlgo {
    public static void main(String[] args) {

        StudentDAO studentDAO = new StudentDAO();

        System.out.println("\nQuick Sorted by Name:");
        studentDAO.quicksortByName().forEach(System.out::println);

        System.out.println("\nHeap Sorted by Name:");
        studentDAO.heapSortByName().forEach(System.out::println);
    }
}
