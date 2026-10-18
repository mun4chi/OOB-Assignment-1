package utilities;

import java.util.Comparator;

import products.Product;

/**
 * Compare two products by their updated price.
 */

public class UpdatedPriceComparator implements Comparator<Product> {
	/**
	 * Compare two products by their updated prices
	 * 
	 * @param p1 the first product
	 * @param p2 the second product
	 * @return a negative number if p1's updated price is smaller, zero if it is
	 *         equal or a positive number is the price is larger.
	 */

	@Override
	public int compare(Product p1, Product p2) {

		return Double.compare(p1.getUpdatedPrice(), p2.getUpdatedPrice());
	}

}
