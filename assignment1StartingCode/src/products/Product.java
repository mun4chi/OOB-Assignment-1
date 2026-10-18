package products;

/**
 * Shows a generic product with its original and updated prices.
 */

public abstract class Product implements Comparable<Product> {

	private double originalPrice;
	private double updatedPrice;

	/**
	 * Creates a product with original price and updated prices.
	 * 
	 * @param originalPrice the original price of the product
	 * @param updatedPrice  the updated price of the product.
	 */

	public Product(double originalPrice, double updatedPrice) {
		this.originalPrice = originalPrice;
		this.updatedPrice = updatedPrice;
	}

	/**
	 * Returns the original price of this product.
	 * 
	 * @return the original price
	 */
	public double getOriginalPrice() {
		return originalPrice;

	}

	/**
	 * Returns the updated price of this product
	 * 
	 * @return the updated price.
	 */
	public double getUpdatedPrice() {

		return updatedPrice;
	}

	/**
	 * Calculates the real difference of this product. The adjustment of the product
	 * type is multiplied by the absolute difference between the original and
	 * updated price of this product
	 * 
	 * @return the adjusted absolute price difference
	 */
	public abstract double calcRealDiff();

	/**
	 * Compares this product with another product by their real calculated
	 * differences.
	 * 
	 * @param other the product to compare with
	 * @return a negative number if this product's real difference is smaller, zero
	 *         if they are equal or a positive number if it is larger.
	 */

	@Override

	public int compareTo(Product other) {
		return Double.compare(this.calcRealDiff(), other.calcRealDiff());

	}
}
