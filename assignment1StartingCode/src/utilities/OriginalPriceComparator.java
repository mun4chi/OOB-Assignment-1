package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Compare two products by their original price.
 */

public class OriginalPriceComparator implements Comparator<Product> {
	/**
	 * Compare two products by their original prices
	 * 
	 * @param p1 is the first product
	 * @param p2 is the second product
	 * @return a negative number if p1's original price is smaller, zero if it is
	 *         equal or a positive number is the price is larger.
	 */

	@Override
	public int compare(Product p1, Product p2) {

		return Double.compare(p1.getOriginalPrice(), p2.getOriginalPrice());
	}

}
