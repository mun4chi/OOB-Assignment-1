package products;

/**
 * Represents product type Shorts with an adjustment of 0.3.
 */

public class Shorts extends Product {
	/**
	 * Given adjustment value of Shorts.
	 */
	private static final double ADJUSTMENT = 0.3;

	/**
	 * Creates a pair of shorts  with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this Shorts.
	 * @param updatedPrice  Updated price of this Shorts.
	 */
	public Shorts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of Shorts. Given adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}