package algorithms;

import java.util.*;

public class RadixSort {

    public List<Integer> radixSort(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return numbers;
        }
        int max = Collections.max(numbers);
        int exp = 1;

        while (max / exp > 0) {
            numbers = countingSort(numbers, exp);
            exp *= 10;
        }
        return numbers;
    }

    private List<Integer> countingSort(List<Integer> numbers, int exp) {
        int n = numbers.size();
        int[] output = new int[n];
        int[] count = new int[10];

        for (int num : numbers) {
            int digit = (num / exp) % 10;
            count[digit]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            int num = numbers.get(i);
            int digit = (num / exp) % 10;
            output[count[digit] - 1] = num;
            count[digit]--;
        }
        List<Integer> sortedList = new ArrayList<>();
        for (int num : output) {
            sortedList.add(num);
        }
        return sortedList;
    }
}
