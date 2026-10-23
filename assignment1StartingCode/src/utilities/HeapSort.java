
package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Heap Sort.
 */
public class HeapSort {

    public static void sort(
            Product[] products,
            Comparator<Product> comparator) {

        int n = products.length;

        // Build a min-heap.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(products, n, i, comparator);
        }

        // Move the smallest item to the end of the array.
        for (int i = n - 1; i > 0; i--) {

            Product temp = products[0];
            products[0] = products[i];
            products[i] = temp;

            // Restore the heap using only the unsorted elements.
            heapify(products, i, 0, comparator);
        }
    }

    private static void heapify(
            Product[] products,
            int heapSize,
            int root,
            Comparator<Product> comparator) {

        int smallest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < heapSize
                && comparator.compare(
                        products[left], products[smallest]) < 0) {

            smallest = left;
        }

        if (right < heapSize
                && comparator.compare(
                        products[right], products[smallest]) < 0) {

            smallest = right;
        }

        if (smallest != root) {

            Product temp = products[root];
            products[root] = products[smallest];
            products[smallest] = temp;

            heapify(products, heapSize, smallest, comparator);
        }
    }
}
