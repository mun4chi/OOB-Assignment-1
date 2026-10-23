package appDomain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;

import products.Dresses;
import products.Jackets;
import products.Jeans;
import products.Product;
import products.Shoes;
import products.Shorts;
import products.Skirts;
import products.TShirts;
import utilities.BubbleSort;
import utilities.HeapSort;
import utilities.InsertionSort;
import utilities.MergeSort;
import utilities.OriginalPriceComparator;
import utilities.QuickSort;
import utilities.RealDifferenceComparator;
import utilities.SelectionSort;
import utilities.UpdatedPriceComparator;

/**
 * <p>
 * This application driver code is designed to be used as a basis for the
 * Complexity and Sorting assignment that will be developed in the CPRG304
 * class at SAIT. The implementors of this applications will be required
 * to add all the correct functionality.
 * </p>
 * <p>
 * The application reads a file of products into an array, sorts the array in
 * descending order using the compare type and sorting algorithm chosen on the
 * command line, then displays the sorted products and the time the sort took.
 * </p>
 */
public class AppDriver
{
	/**
	 * The main method is the entry point of the application.
	 *
	 * @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
		String fileName = "";
		char compareType = ' ';
		char sortType = ' ';

		//  Read the command line arguments (any order, any case)
		for( String arg : args )
		{
			if( arg.length() < 3 )
			{
				System.out.println( "Error: \"" + arg + "\" is not a valid option." );
				printUsage();
				return;
			}

			String flag = arg.substring( 0, 2 ).toLowerCase();
			String value = arg.substring( 2 );

			if( flag.equals( "-f" ) )
			{
				fileName = value.replace( "\"", "" );
			}
			else if( flag.equals( "-t" ) )
			{
				if( value.length() == 1 )
				{
					compareType = Character.toLowerCase( value.charAt( 0 ) );
				}
				else
				{
					compareType = '?';
				}
			}
			else if( flag.equals( "-s" ) )
			{
				if( value.length() == 1 )
				{
					sortType = Character.toLowerCase( value.charAt( 0 ) );
				}
				else
				{
					sortType = '?';
				}
			}
			else
			{
				System.out.println( "Error: \"" + arg + "\" is not a recognized option." );
				printUsage();
				return;
			}
		}

		// Check all three options were given correctly
		if( fileName.equals( "" ) )
		{
			System.out.println( "Error: the file name is missing. Use -f, e.g. -fproducts1.txt" );
			printUsage();
			return;
		}
		if( compareType != 'o' && compareType != 'u' && compareType != 'd' )
		{
			System.out.println( "Error: the compare type is missing or invalid. Use -to, -tu or -td." );
			printUsage();
			return;
		}
		if( sortType != 'b' && sortType != 's' && sortType != 'i'
				&& sortType != 'm' && sortType != 'q' && sortType != 'z' )
		{
			System.out.println( "Error: the sorting algorithm is missing or invalid."
					+ " Use -sb, -ss, -si, -sm, -sq or -sz." );
			printUsage();
			return;
		}

		//  Read the file into an array of products
		Product[] products;
		try
		{
			products = readProducts( fileName );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "Error: the file \"" + fileName + "\" could not be found." );
			return;
		}
		catch( NumberFormatException e )
		{
			System.out.println( "Error: the file contains a value that is not a valid number." );
			return;
		}
		catch( IOException e )
		{
			System.out.println( "Error: " + e.getMessage() );
			return;
		}

		// Choose the comparator
		Comparator<Product> comparator = getComparator( compareType );

		// Time only the sort
		long start = System.currentTimeMillis();
		runSort( products, comparator, sortType );
		long end = System.currentTimeMillis();

		//  Show the results and time
		displayResults( products, compareType );
		System.out.println( getSortName( sortType ) + " run time was: " + ( end - start ) + " milliseconds" );
	}

	/**
	 * Prints instructions on how to run the program correctly.
	 */
	private static void printUsage()
	{
		System.out.println( "Usage: java -jar Sort.jar -f<file_name> -t<compare_type> -s<sort_alg>" );
		System.out.println( "  -t  o = original price, u = updated price, d = calculated difference" );
		System.out.println( "  -s  b = bubble, s = selection, i = insertion, m = merge, q = quick, z = heap" );
		System.out.println( "Options can be in any order and in upper or lower case, e.g. -tD -sQ -fres/products1.txt" );
	}

	/**
	 * Reads the data file and creates an array of products. The first line holds
	 * the number of products. Every other line holds a product type, its original
	 * price and its updated price, separated by spaces.
	 *
	 * @param fileName the name and path of the data file
	 * @return an array containing every product in the file
	 * @throws IOException if the file cannot be found, cannot be read or if it is not
	 *                     in the expected format.
	 */
	private static Product[] readProducts( String fileName ) throws IOException
	{
		BufferedReader reader = new BufferedReader( new FileReader( fileName ) );

		String firstLine = reader.readLine();
		if( firstLine == null )
		{
			reader.close();
			throw new IOException( "this file is empty." );
		}

		int count = Integer.parseInt( firstLine.trim() );
		Product[] products = new Product[count];

		for( int i = 0; i < count; i++ )
		{
			String line = reader.readLine();
			if( line == null )
			{
				reader.close();
				throw new IOException( "this file has fewer products than the first line says." );
			}

			String[] parts = line.trim().split( " " );
			if( parts.length != 3 )
			{
				reader.close();
				throw new IOException( "the line \"" + line +  "\" is not in the correct format." );
			}

			double originalPrice = Double.parseDouble( parts[1] );
			double updatedPrice = Double.parseDouble( parts[2] );

			products[i] = createProduct( parts[0], originalPrice, updatedPrice );
			if( products[i] == null )
			{
				reader.close();
				throw new IOException( "unknown product type \"" + parts[0] + "\"." );
			}
		}

		reader.close();
		return products;
	}

	/**
	 * Creates the product subclass that matches the given product type.
	 *
	 * @param type          the product type read from the file
	 * @param originalPrice the original price of the product
	 * @param updatedPrice  the updated price of the product
	 * @return a new product of the matching subclass, or null if the type is unknown
	 */
	private static Product createProduct( String type, double originalPrice, double updatedPrice )
	{
		switch( type )
		{
			case "Jeans":
				return new Jeans( originalPrice, updatedPrice );
			case "TShirts":
				return new TShirts( originalPrice, updatedPrice );
			case "Skirts":
				return new Skirts( originalPrice, updatedPrice );
			case "Dresses":
				return new Dresses( originalPrice, updatedPrice );
			case "Shorts":
				return new Shorts( originalPrice, updatedPrice );
			case "Jackets":
				return new Jackets( originalPrice, updatedPrice );
			case "Shoes":
				return new Shoes( originalPrice, updatedPrice );
			default:
				return null;
		}
	}

	/**
	 * Returns the comparator that matches the chosen compare type.
	 *
	 * @param compareType o for original price, u for updated price or d for
	 *                    calculated difference
	 * @return the matching comparator
	 */
	private static Comparator<Product> getComparator( char compareType )
	{
		if( compareType == 'o' )
		{
			return new OriginalPriceComparator();
		}
		else if( compareType == 'u' )
		{
			return new UpdatedPriceComparator();
		}
		else
		{
			return new RealDifferenceComparator();
		}
	}

	/**
	 * Sorts the products in descending order with the chosen algorithm.
	 *
	 * @param products   the array to sort
	 * @param comparator the comparator used to compare two products
	 * @param sortType   b, s, i, m, q or z
	 */
	private static void runSort( Product[] products, Comparator<Product> comparator, char sortType )
	{
		switch( sortType )
		{
			case 'b':
				BubbleSort.sort( products, comparator );
				break;
			case 's':
				SelectionSort.sort( products, comparator );
				break;
			case 'i':
				InsertionSort.sort( products, comparator );
				break;
			case 'm':
				MergeSort.sort( products, comparator );
				break;
			case 'q':
				QuickSort.sort( products, comparator );
				break;
			case 'z':
				HeapSort.sort( products, comparator );
				break;
		}
	}

	/**
	 * Returns the name of the chosen sorting algorithm.
	 *
	 * @param sortType b, s, i, m, q or z
	 * @return the name of the sorting algorithm
	 */
	private static String getSortName( char sortType )
	{
		switch( sortType )
		{
			case 'b':
				return "Bubble Sort";
			case 's':
				return "Selection Sort";
			case 'i':
				return "Insertion Sort";
			case 'm':
				return "Merge Sort";
			case 'q':
				return "Quick Sort";
			default:
				return "Heap Sort";
		}
	}

	/**
	 * shows the sorted products. If it contains 10 products or less, every product is
	 * shown. With 1000 or less, the first, every 100th and the last product
	 * are shown. Otherwise the first, every 1000th and the last are shown.
	 *
	 * @param products    the sorted array of products
	 * @param compareType the compare type, which decides which value is shown
	 */
	private static void displayResults( Product[] products, char compareType )
	{
		int n = products.length;

		if( n <= 10 )
		{
			for( int i = 0; i < n; i++ )
			{
				printProduct( "Element " + ( i + 1 ) + ":", products[i], compareType );
			}
			return;
		}

		int step;
		if( n <= 1000 )
		{
			step = 100;
		}
		else
		{
			step = 1000;
		}

		printProduct( "First element:", products[0], compareType );
		for( int position = step; position < n; position += step )
		{
			printProduct( position + "th element:", products[position - 1], compareType );
		}
		printProduct( "Last element:", products[n - 1], compareType );
	}

	/**
	 * Prints one product: a label, the product type, and the value that was
	 * used for sorting.
	 *
	 * @param label       the text shown at the start of the line
	 * @param product     the product to show
	 * @param compareType the compare type, which decides which value is shown
	 */
	private static void printProduct( String label, Product product, char compareType )
	{
		String value;
		if( compareType == 'o' )
		{
			value = "Original Price: " + product.getOriginalPrice();
		}
		else if( compareType == 'u' )
		{
			value = "Updated Price: " + product.getUpdatedPrice();
		}
		else
		{
			value = "Real Difference: " + product.calcRealDiff();
		}
		System.out.println( label + " " + product.getClass().getSimpleName() + "   " + value );
	}
}