package products;

/**
 * Represents product type Skirt with an adjustment of 0.1.
 */

public class Skirts extends Product {
	/**
	 * Given adjustment value of Skirt.
	 */
	private static final double ADJUSTMENT = 0.1;

	/**
	 * Creates a Skirt with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this Skirt.
	 * @param updatedPrice  Updated price of this Skirt.
	 */
	public Skirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of Skirt. The adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}