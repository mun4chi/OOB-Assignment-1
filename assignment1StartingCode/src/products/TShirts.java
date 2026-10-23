package products;

/**
 * Represents product type T-shirt with an adjustment of 0.2.
 */

public class TShirts extends Product {
	/**
	 * Given adjustment value of Tshirt.
	 */
	private static final double ADJUSTMENT = 0.2;

	/**
	 * Creates a t-shirt with Original and Updated price.
	 * 
	 * @param originalPrice Original price of t-shirt.
	 * @param updatedPrice  Updated price of t-shirt .
	 */
	public TShirts(double originalPrice, double updatedPrice) {
		super(originalPrice, updatedPrice);
	}

	/**
	 * calculates the real difference of t-shirt. The adjustment value is multiplied
	 * by the absolute difference between original and the updated price.
	 * 
	 * @return adjusted absolute price difference.
	 */
	@Override
	public double calcRealDiff() {
		return ADJUSTMENT * Math.abs(getOriginalPrice() - getUpdatedPrice());
	}
}