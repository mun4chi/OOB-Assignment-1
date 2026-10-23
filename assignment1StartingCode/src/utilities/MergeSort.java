package utilities;

import java.util.Comparator;
import products.Product;

/**
 * Sorts products in descending order using Merge Sort.
 * <p>
 * The array is split in half, each half is sorted, and then the two sorted
 * halves are merged back together with the larger products first.
 * </p>
 */
public class MergeSort
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
		if( products.length < 2 )
		{
			return;
		}

		// One extra array is made once and reused by every merge.
		Product[] temp = new Product[products.length];
		mergeSort( products, temp, 0, products.length - 1, comparator );
	}

	/**
	 * Sorts the section of the array from index low to index high by
	 * splitting it in half, sorting each half, then merging them.
	 *
	 * @param products   the array being sorted
	 * @param temp       extra space used while merging
	 * @param low        the first index of the section
	 * @param high       the last index of the section
	 * @param comparator determines the product ordering
	 */
	private static void mergeSort( Product[] products, Product[] temp, int low, int high,
			Comparator<Product> comparator )
	{
		if( low >= high )
		{
			return;
		}

		int mid = ( low + high ) / 2;
		mergeSort( products, temp, low, mid, comparator );
		mergeSort( products, temp, mid + 1, high, comparator );
		merge( products, temp, low, mid, high, comparator );
	}

	/**
	 * Merges the two sorted sections products[low..mid] and
	 * products[mid+1..high] into one section sorted from largest to smallest.
	 *
	 * @param products   the array being sorted
	 * @param temp       extra space used while merging
	 * @param low        the first index of the left section
	 * @param mid        the last index of the left section
	 * @param high       the last index of the right section
	 * @param comparator determines the product ordering
	 */
	private static void merge( Product[] products, Product[] temp, int low, int mid, int high,
			Comparator<Product> comparator )
	{
		// Copy the section into the temp array.
		for( int i = low; i <= high; i++ )
		{
			temp[i] = products[i];
		}

		int left = low;
		int right = mid + 1;
		int index = low;

		// Take the larger product from the front of either half.
		while( left <= mid && right <= high )
		{
			if( comparator.compare( temp[left], temp[right] ) >= 0 )
			{
				products[index] = temp[left];
				left++;
			}
			else
			{
				products[index] = temp[right];
				right++;
			}
			index++;
		}

		// Copy any products left over in the left half. Products left over in
		// the right half are already in the correct place.
		while( left <= mid )
		{
			products[index] = temp[left];
			left++;
			index++;
		}
	}
}