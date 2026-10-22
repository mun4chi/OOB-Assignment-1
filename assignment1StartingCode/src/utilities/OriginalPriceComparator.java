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
	 *         equal or else a positive number if it is larger.
	 */

	@Override
	public int compare(Product p1, Product p2) {

		if(p1.getOriginalPrice()< p2.getOriginalPrice()){
			return -1;
			
	}
	else if(p1.getOriginalPrice()== p2.getOriginalPrice()){
			return 0;
		
	}
     else {
		return +1;
	}
	}
}
