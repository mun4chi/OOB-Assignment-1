
package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Insertion Sort.
 */
public class InsertionSort {

    /**
     * Sorts products from largest to smallest according
     * to the supplied comparator.
     *
     * @param products array of products to sort
     * @param comparator determines product ordering
     */
    public static void sort(
            Product[] products,
            Comparator<Product> comparator) {

        for (int i = 1; i < products.length; i++) {

            // Save the product we want to insert.
            Product key = products[i];

            // Start at the end of the sorted portion.
            int j = i - 1;

            // Shift smaller products one position to the right.
            while (j >= 0
                    && comparator.compare(products[j], key) < 0) {

                products[j + 1] = products[j];
                j--;
            }

            // Insert the saved product in its correct position.
            products[j + 1] = key;
        }
    }
}
