package products;

/**
 * Shows a product type Jacket with an adjustment of 0.7.
 */

public class Jackets extends Product {
	/**
	 * Given adjustment value of Jacket.
	 */
	private static final double ADJUSTMENT = 0.7;

	/**
	 * Creates a Jacket  with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this Jacket.
	 * @param updatedPrice  Updated price of this Jacket.
	 */
	public Jackets(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of Jacket. The adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}