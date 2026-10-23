package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Compares two products by their real difference,  product's own
 * compareTo method is used.
 */
public class RealDifferenceComparator implements Comparator<Product> {

	/**
	 * Compares two products by their real difference.
	 *
	 * @param p1 the first product
	 * @param p2 the second product
	 * @return a negative number if p1's real difference is smaller, zero if it is
	 *         equal, or else positive number if it is larger
	 */
	@Override
	public int compare(Product p1, Product p2) {
		return p1.compareTo(p2);
	}
}