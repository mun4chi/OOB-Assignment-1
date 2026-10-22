package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Selection sort.
 */
public class SelectionSort {
	/**
	 * Sorts the array in descending order.  The largest remaining
	 * product is selected at every pass and swapped into the next spot.
	 * 
	 * @param products   the array of products to sort
	 * @param comparator the comparator used to compare two products
	 */
	public static void sort(Product[] products, Comparator<Product> comparator) {

		int n = products.length;

		for (int i = 0; i < n - 1; i++) {
			int maxIndex = i;

			for (int j = i + 1; j < n; j++) {
				if (comparator.compare(products[j], products[maxIndex]) > 0) {
					maxIndex = j;

				}
			}
			Product temp = products[i];
			products[i] = products[maxIndex];
			products[maxIndex] = temp;
		}

	}

}
