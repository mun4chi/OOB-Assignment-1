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
	 *         equal or else a positive number if it  is larger.
	 */

	@Override
	public int compare(Product p1, Product p2) {

		if(p1.getUpdatedPrice()< p2.getUpdatedPrice()) {
			return -1;
		}
		else if(p1.getUpdatedPrice()== p2.getUpdatedPrice()) {
			return 0;
		
		}
		else {
			return +1;
		}
	}

}
