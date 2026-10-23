
package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Bubble Sort.
 */
public class BubbleSort {

    public static void sort(
            Product[] products,
            Comparator<Product> comparator) {

        int n = products.length;

        for (int i = 0; i < n - 1; i++) {

            boolean swapped = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (comparator.compare(
                        products[j], products[j + 1]) < 0) {

                    Product temp = products[j];
                    products[j] = products[j + 1];
                    products[j + 1] = temp;

                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }
}

