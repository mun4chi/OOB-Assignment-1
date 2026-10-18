package products;

/**
 * Represents product type Dresses with an adjustment of 0.6.
 */

public class Dresses extends Product {
	/**
	 * Given adjustment value of Dress.
	 */
	private static final double ADJUSTMENT = 0.6;

	/**
	 * Creates a Dresses with Original and Updated price.
	 * 
	 * @param originalPrice Original price of this Dress.
	 * @param updatedPrice  Updated price of this Dress.
	 */
	public Dresses(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of Dress. Given adjustment value is multiplied
	 * by the absolute difference between original price and the updated price
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}