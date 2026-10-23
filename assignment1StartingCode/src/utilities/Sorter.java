package utilities;

import java.util.Comparator;

import products.Product;

/**
 * <p>
 * Sorter is a utility class containing static sorting algorithms that arrange
 * an array of objects in <b>descending</b> order (largest to smallest).
 * </p>
 * <p>
 * Every algorithm is offered in two forms: one that uses the natural ordering
 * of the objects (their {@link Comparable#compareTo(Object)} method), and one
 * that uses an external {@link Comparator}. The algorithms rely only on the
 * sign of the comparison result, so the Comparable and Comparator contracts
 * are respected and no reversing of the array is ever performed.
 * </p>
 * <p>
 * The methods do not print anything or measure time; benchmarking is the
 * responsibility of the calling code.
 * </p>
 */
public final class Sorter
{
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private Sorter()
	{
	}

	// =====================================================================
	// MERGE SORT
	// =====================================================================

	/**
	 * Sorts the array in descending order using merge sort and the natural
	 * ordering of its elements.
	 *
	 * @param <T>   the type of the elements, which must be Comparable
	 * @param array the array to sort; must not be null
	 */
	public static <T extends Comparable<? super T>> void mergeSort( T[] array )
	{
		mergeSort( array, ( a, b ) -> a.compareTo( b ) );
	}

	/**
	 * Sorts the array in descending order using merge sort and the given
	 * comparator. Merge sort repeatedly splits the array in half, sorts each
	 * half recursively, then merges the two sorted halves. It always runs in
	 * O(n log n) time, uses O(n) extra memory, and is stable (equal elements
	 * keep their original relative order).
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array to sort; must not be null
	 * @param comparator the comparator that decides the ordering
	 */
	public static <T> void mergeSort( T[] array, Comparator<? super T> comparator )
	{
		if( array.length < 2 )
		{
			return;
		}
		// One temporary buffer is allocated once and reused by every merge.
		T[] temp = array.clone();
		mergeSort( array, temp, 0, array.length - 1, comparator );
	}

	/**
	 * Recursively sorts the section array[low..high] in descending order.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array being sorted
	 * @param temp       a buffer the same size as array used while merging
	 * @param low        the first index of the section (inclusive)
	 * @param high       the last index of the section (inclusive)
	 * @param comparator the comparator that decides the ordering
	 */
	private static <T> void mergeSort( T[] array, T[] temp, int low, int high,
			Comparator<? super T> comparator )
	{
		if( low >= high )
		{
			return;
		}
		int mid = low + ( high - low ) / 2;
		mergeSort( array, temp, low, mid, comparator );
		mergeSort( array, temp, mid + 1, high, comparator );
		merge( array, temp, low, mid, high, comparator );
	}

	/**
	 * Merges the two sorted sections array[low..mid] and array[mid+1..high]
	 * into one section sorted in descending order. When two elements are
	 * equal, the one from the left section is taken first to keep the sort
	 * stable.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array being sorted
	 * @param temp       a buffer the same size as array
	 * @param low        the first index of the left section
	 * @param mid        the last index of the left section
	 * @param high       the last index of the right section
	 * @param comparator the comparator that decides the ordering
	 */
	private static <T> void merge( T[] array, T[] temp, int low, int mid, int high,
			Comparator<? super T> comparator )
	{
		// Copy the section into the buffer, then merge back into the array.
		System.arraycopy( array, low, temp, low, high - low + 1 );

		int left = low;
		int right = mid + 1;
		int dest = low;

		while( left <= mid && right <= high )
		{
			// Descending: take the larger element first (left wins ties).
			if( comparator.compare( temp[left], temp[right] ) >= 0 )
			{
				array[dest++] = temp[left++];
			}
			else
			{
				array[dest++] = temp[right++];
			}
		}

		// Copy whatever remains in the left section. Anything remaining in
		// the right section is already in its correct place.
		while( left <= mid )
		{
			array[dest++] = temp[left++];
		}
	}

	// =====================================================================
	// QUICK SORT (MEDIAN-OF-3 PIVOT)
	// =====================================================================

	/**
	 * Sorts the array in descending order using quick sort with median-of-3
	 * pivot selection and the natural ordering of its elements.
	 *
	 * @param <T>   the type of the elements, which must be Comparable
	 * @param array the array to sort; must not be null
	 */
	public static <T extends Comparable<? super T>> void quickSort( T[] array )
	{
		quickSort( array, ( a, b ) -> a.compareTo( b ) );
	}

	/**
	 * Sorts the array in descending order using quick sort and the given
	 * comparator. The pivot is the median of the first, middle and last
	 * elements of each section, which avoids the O(n^2) worst case on data
	 * that is already sorted or reverse sorted. Average running time is
	 * O(n log n) and the sort is done in place.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array to sort; must not be null
	 * @param comparator the comparator that decides the ordering
	 */
	public static <T> void quickSort( T[] array, Comparator<? super T> comparator )
	{
		quickSort( array, 0, array.length - 1, comparator );
	}

	/**
	 * Recursively sorts the section array[low..high] in descending order.
	 * To keep the recursion depth at O(log n), the method recurses into the
	 * smaller partition and loops over the larger one.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array being sorted
	 * @param low        the first index of the section (inclusive)
	 * @param high       the last index of the section (inclusive)
	 * @param comparator the comparator that decides the ordering
	 */
	private static <T> void quickSort( T[] array, int low, int high,
			Comparator<? super T> comparator )
	{
		while( high - low >= 2 )
		{
			int split = partition( array, low, high, comparator );

			if( split - low < high - split )
			{
				quickSort( array, low, split, comparator );
				low = split + 1;
			}
			else
			{
				quickSort( array, split + 1, high, comparator );
				high = split;
			}
		}

		// A section of two elements just needs one comparison.
		if( high - low == 1 && comparator.compare( array[low], array[high] ) < 0 )
		{
			swap( array, low, high );
		}
	}

	/**
	 * Partitions array[low..high] around a median-of-3 pivot so that every
	 * element in array[low..split] is greater than or equal to the pivot and
	 * every element in array[split+1..high] is less than or equal to it.
	 * Scanning stops on elements equal to the pivot, which keeps the
	 * partitions balanced when the data contains many duplicate values.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array being sorted
	 * @param low        the first index of the section (needs at least 3 elements)
	 * @param high       the last index of the section
	 * @param comparator the comparator that decides the ordering
	 * @return the split index; both sides are non-empty and smaller than the section
	 */
	private static <T> int partition( T[] array, int low, int high,
			Comparator<? super T> comparator )
	{
		T pivot = medianOfThree( array, low, high, comparator );

		int i = low - 1;
		int j = high + 1;

		while( true )
		{
			// Move right past elements that belong on the left (larger).
			do
			{
				i++;
			}
			while( comparator.compare( array[i], pivot ) > 0 );

			// Move left past elements that belong on the right (smaller).
			do
			{
				j--;
			}
			while( comparator.compare( array[j], pivot ) < 0 );

			if( i >= j )
			{
				return j;
			}
			swap( array, i, j );
		}
	}

	/**
	 * Orders the first, middle and last elements of the section so that, in
	 * descending order, array[low] &gt;= array[mid] &gt;= array[high], and
	 * returns the middle value (the median of the three) to use as the pivot.
	 *
	 * @param <T>        the type of the elements
	 * @param array      the array being sorted
	 * @param low        the first index of the section
	 * @param high       the last index of the section
	 * @param comparator the comparator that decides the ordering
	 * @return the median of the three sampled elements
	 */
	private static <T> T medianOfThree( T[] array, int low, int high,
			Comparator<? super T> comparator )
	{
		int mid = low + ( high - low ) / 2;

		if( comparator.compare( array[low], array[mid] ) < 0 )
		{
			swap( array, low, mid );
		}
		if( comparator.compare( array[low], array[high] ) < 0 )
		{
			swap( array, low, high );
		}
		if( comparator.compare( array[mid], array[high] ) < 0 )
		{
			swap( array, mid, high );
		}
		return array[mid];
	}

	// =====================================================================
	// HELPERS
	// =====================================================================

	/**
	 * Swaps the elements at two positions in the array.
	 *
	 * @param <T>   the type of the elements
	 * @param array the array containing the elements
	 * @param i     the index of the first element
	 * @param j     the index of the second element
	 */
	private static <T> void swap( T[] array, int i, int j )
	{
		T temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	}

	public static void bubbleSort(Product[] products, Comparator<Product> comparator) {
		// TODO Auto-generated method stub
		
	}
}