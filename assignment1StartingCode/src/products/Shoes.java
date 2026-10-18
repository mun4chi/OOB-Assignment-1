package products;

/**
 * Represents product type Shoes with an adjustment of 0.4.
 */

public class Shoes extends Product {
	/**
	 * Given adjustment value of Shoes.
	 */
	private static final double ADJUSTMENT = 0.4;

	/**
	 * Creates a pair of Shoes  with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this pair of Shoes.
	 * @param updatedPrice  Updated price of this pair of Shoes.
	 */
	public Shoes(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of Shoes. Given adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}