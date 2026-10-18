package products;

/**
 * Represents product type jeans with an adjustment of 0.5.
 */

public class TShirts extends Product {
	/**
	 * Given adjustment value of Jeans.
	 */
	private static final double ADJUSTMENT = 0.5;

	/**
	 * Creates a pair of jeans with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this jeans.
	 * @param updatedPrice  Updated price of this jeans.
	 */
	public TShirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of jeans. Given adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}