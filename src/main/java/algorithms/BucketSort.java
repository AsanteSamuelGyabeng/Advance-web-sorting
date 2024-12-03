package algorithms;

import java.util.*;

public class BucketSort {

    public List<Integer> bucketSort(List<Integer> numbers) {
        if (numbers.isEmpty()) {
            return numbers;
        }

        int max = Collections.max(numbers);
        int min = Collections.min(numbers);

        int bucketRange = (max - min) / numbers.size() + 1;

        int bucketCount = (max - min) / bucketRange + 1;
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);

        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        for (int num : numbers) {
            int bucketIndex = (num - min) / bucketRange;
            buckets.get(bucketIndex).add(num);
        }

        for (List<Integer> bucket : buckets) {
            Collections.sort(bucket);
        }

        List<Integer> sortedList = new ArrayList<>();
        for (List<Integer> bucket : buckets) {
            sortedList.addAll(bucket);
        }

        return sortedList;
    }
}
