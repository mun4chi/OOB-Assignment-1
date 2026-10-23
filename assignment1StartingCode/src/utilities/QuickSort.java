package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Quick Sort with the
 * median-of-3 approach for choosing the pivot.
 * <p>
 * The pivot is the median of the first, middle and last products in each
 * section. Products larger than the pivot are moved to the left side and
 * smaller products to the right side, then each side is sorted the same way.
 * </p>
 */
public class QuickSort
{
	/**
	 * Sorts the array from largest to smallest according to the supplied
	 * comparator.
	 *
	 * @param products   the array of products to sort
	 * @param comparator determines the product ordering
	 */
	public static void sort( Product[] products, Comparator<Product> comparator )
	{
		quickSort( products, 0, products.length - 1, comparator );
	}

	/**
	 * Sorts the section of the array from index low to index high.
	 *
	 * @param products   the array being sorted
	 * @param low        the first index of the section
	 * @param high       the last index of the section
	 * @param comparator determines the product ordering
	 */
	private static void quickSort( Product[] products, int low, int high,
			Comparator<Product> comparator )
	{
		if( high - low < 1 )
		{
			return;
		}

		// A section of two products only needs one comparison.
		if( high - low == 1 )
		{
			if( comparator.compare( products[low], products[high] ) < 0 )
			{
				swap( products, low, high );
			}
			return;
		}

		int split = partition( products, low, high, comparator );
		quickSort( products, low, split, comparator );
		quickSort( products, split + 1, high, comparator );
	}

	/**
	 * Rearranges the section around a median-of-3 pivot so that every product
	 * from low to the returned index is greater than or equal to the pivot,
	 * and every product after it is less than or equal to the pivot.
	 *
	 * @param products   the array being sorted
	 * @param low        the first index of the section
	 * @param high       the last index of the section
	 * @param comparator determines the product ordering
	 * @return the last index of the left side
	 */
	private static int partition( Product[] products, int low, int high,
			Comparator<Product> comparator )
	{
		Product pivot = medianOfThree( products, low, high, comparator );

		int i = low - 1;
		int j = high + 1;

		while( true )
		{
			// Move right until a product that belongs on the right is found.
			do
			{
				i++;
			}
			while( comparator.compare( products[i], pivot ) > 0 );

			// Move left until a product that belongs on the left is found.
			do
			{
				j--;
			}
			while( comparator.compare( products[j], pivot ) < 0 );

			if( i >= j )
			{
				return j;
			}

			swap( products, i, j );
		}
	}

	/**
	 * Puts the first, middle and last products of the section in descending
	 * order and returns the middle one, which is the median of the three.
	 *
	 * @param products   the array being sorted
	 * @param low        the first index of the section
	 * @param high       the last index of the section
	 * @param comparator determines the product ordering
	 * @return the median of the three products, used as the pivot
	 */
	private static Product medianOfThree( Product[] products, int low, int high,
			Comparator<Product> comparator )
	{
		int mid = ( low + high ) / 2;

		if( comparator.compare( products[low], products[mid] ) < 0 )
		{
			swap( products, low, mid );
		}
		if( comparator.compare( products[low], products[high] ) < 0 )
		{
			swap( products, low, high );
		}
		if( comparator.compare( products[mid], products[high] ) < 0 )
		{
			swap( products, mid, high );
		}

		return products[mid];
	}

	/**
	 * Swaps the products at two positions in the array.
	 *
	 * @param products the array containing the products
	 * @param i        the index of the first product
	 * @param j        the index of the second product
	 */
	private static void swap( Product[] products, int i, int j )
	{
		Product temp = products[i];
		products[i] = products[j];
		products[j] = temp;
	}
}