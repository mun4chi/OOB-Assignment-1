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
import utilities.OriginalPriceComparator;
import utilities.Sorter;
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
 * descending order using the sorting algorithm and compare type chosen on the
 * command line, then displays the sorted products and the time the sort took.
 * </p>
 * <p>
 * Usage (arguments are case insensitive and may appear in any order):
 * </p>
 * <pre>
 * java -jar Sort.jar -f&lt;file_name&gt; -t&lt;compare_type&gt; -s&lt;sort_alg&gt;
 * </pre>
 */
public class AppDriver
{
	/**
	 * Help text shown whenever the command line arguments are invalid.
	 */
	private static final String USAGE =
			"Usage: java -jar Sort.jar -f<file_name> -t<compare_type> -s<sort_alg>\n"
			+ "  -f  the data file name and path, e.g. -fproducts1.txt or -f\"res\\products1.txt\"\n"
			+ "  -t  the compare type: o = original price, u = updated price, d = calculated difference\n"
			+ "  -s  the sorting algorithm: b = bubble, s = selection, i = insertion,\n"
			+ "      m = merge, q = quick, z = custom sort\n"
			+ "Options are case insensitive, can be in any order, and must have no space\n"
			+ "between the option and its value (e.g. -tO -sQ -fproducts2.txt).";

	/**
	 * The main method is the entry point of the application.
	 *
	 * @param args The input to control the execution of the application.
	 */
	public static void main( String[] args )
	{
		String[] options;
		try
		{
			options = parseArguments( args );
		}
		catch( IllegalArgumentException e )
		{
			System.out.println( "Error: " + e.getMessage() );
			System.out.println();
			System.out.println( USAGE );
			return;
		}

		String fileName = options[0];
		char compareType = options[1].charAt( 0 );
		char sortType = options[2].charAt( 0 );

		Product[] products;
		try
		{
			products = readProducts( fileName );
		}
		catch( FileNotFoundException e )
		{
			System.out.println( "Error: the file \"" + fileName + "\" could not be found."
					+ " Check the file name and path given with -f." );
			return;
		}
		catch( IOException e )
		{
			System.out.println( "Error: could not read \"" + fileName + "\". " + e.getMessage() );
			return;
		}

		Comparator<Product> comparator = getComparator( compareType );

		// Only the sort itself is timed, not reading the file or printing.
		long startTime = System.nanoTime();
		sort( products, comparator, sortType );
		long runTime = ( System.nanoTime() - startTime ) / 1_000_000;

		displayResults( products, compareType );
		System.out.println( getSortName( sortType ) + " run time was: " + runTime + " milliseconds" );
	}

	/**
	 * Checks the command line arguments and pulls out the file name, the
	 * compare type and the sorting algorithm. The options can be given in any
	 * order and in upper or lower case.
	 *
	 * @param args the command line arguments
	 * @return an array holding the file name at index 0, the compare type
	 *         (o, u or d) at index 1 and the sort type (b, s, i, m, q or z)
	 *         at index 2
	 * @throws IllegalArgumentException if an option is missing, repeated,
	 *                                  unknown or has an invalid value
	 */
	private static String[] parseArguments( String[] args )
	{
		String fileName = null;
		String compareType = null;
		String sortType = null;

		for( String arg : args )
		{
			String option = arg.trim();

			// Treat a dash pasted from a document (– or —) the same as "-".
			if( option.startsWith( "\u2013" ) || option.startsWith( "\u2014" ) )
			{
				option = "-" + option.substring( 1 );
			}

			if( option.length() < 2 || option.charAt( 0 ) != '-' )
			{
				throw new IllegalArgumentException( "\"" + arg + "\" is not a valid option."
						+ " Each option must start with -f, -t or -s." );
			}

			char flag = Character.toLowerCase( option.charAt( 1 ) );
			String value = removeQuotes( option.substring( 2 ).trim() );

			switch( flag )
			{
				case 'f':
					if( fileName != null )
					{
						throw new IllegalArgumentException( "The file name (-f) was given more than once." );
					}
					if( value.isEmpty() )
					{
						throw new IllegalArgumentException( "No file name was given after -f."
								+ " Put the file name right after -f with no space, e.g. -fproducts1.txt" );
					}
					fileName = value;
					break;

				case 't':
					if( compareType != null )
					{
						throw new IllegalArgumentException( "The compare type (-t) was given more than once." );
					}
					value = value.toLowerCase();
					if( !value.equals( "o" ) && !value.equals( "u" ) && !value.equals( "d" ) )
					{
						throw new IllegalArgumentException( "\"" + arg + "\" has an invalid compare type."
								+ " Use -to (original price), -tu (updated price) or -td (calculated difference)." );
					}
					compareType = value;
					break;

				case 's':
					if( sortType != null )
					{
						throw new IllegalArgumentException( "The sorting algorithm (-s) was given more than once." );
					}
					value = value.toLowerCase();
					if( value.length() != 1 || "bsimqz".indexOf( value.charAt( 0 ) ) == -1 )
					{
						throw new IllegalArgumentException( "\"" + arg + "\" has an invalid sorting algorithm."
								+ " Use -sb, -ss, -si, -sm, -sq or -sz." );
					}
					sortType = value;
					break;

				default:
					throw new IllegalArgumentException( "\"" + arg + "\" is not a recognized option."
							+ " Only -f, -t and -s are allowed." );
			}
		}

		if( fileName == null )
		{
			throw new IllegalArgumentException( "The file name is missing. Add it with -f, e.g. -fproducts1.txt" );
		}
		if( compareType == null )
		{
			throw new IllegalArgumentException( "The compare type is missing. Add it with -t, e.g. -to" );
		}
		if( sortType == null )
		{
			throw new IllegalArgumentException( "The sorting algorithm is missing. Add it with -s, e.g. -sq" );
		}

		return new String[] { fileName, compareType, sortType };
	}

	/**
	 * Removes a pair of double quotes from around a value, if present.
	 *
	 * @param value the value to clean up
	 * @return the value without surrounding quotes
	 */
	private static String removeQuotes( String value )
	{
		if( value.length() >= 2 && value.startsWith( "\"" ) && value.endsWith( "\"" ) )
		{
			return value.substring( 1, value.length() - 1 );
		}
		return value;
	}

	/**
	 * Reads the data file and creates an array of products. The first line of
	 * the file holds the number of products, which sets the size of the array.
	 * Every following line holds a product type followed by its original price
	 * and updated price, separated by spaces.
	 *
	 * @param fileName the name and path of the data file
	 * @return an array containing every product in the file
	 * @throws FileNotFoundException if the file does not exist
	 * @throws IOException           if the file cannot be read or its contents
	 *                               are not in the expected format
	 */
	private static Product[] readProducts( String fileName ) throws IOException
	{
		try( BufferedReader reader = new BufferedReader( new FileReader( fileName ) ) )
		{
			String line = reader.readLine();
			int lineNumber = 1;
			while( line != null && line.trim().isEmpty() )
			{
				line = reader.readLine();
				lineNumber++;
			}
			if( line == null )
			{
				throw new IOException( "The file is empty." );
			}

			int count;
			try
			{
				count = Integer.parseInt( line.trim() );
			}
			catch( NumberFormatException e )
			{
				throw new IOException( "The first line should be the number of products, but it was \""
						+ line.trim() + "\"." );
			}
			if( count < 0 )
			{
				throw new IOException( "The number of products on the first line cannot be negative." );
			}

			Product[] products = new Product[count];
			int index = 0;

			while( index < count && ( line = reader.readLine() ) != null )
			{
				lineNumber++;
				line = line.trim();
				if( line.isEmpty() )
				{
					continue;
				}

				String[] parts = line.split( "\\s+" );
				if( parts.length != 3 )
				{
					throw new IOException( "Line " + lineNumber + " (\"" + line + "\") should have a product type"
							+ " and two prices separated by spaces." );
				}

				double originalPrice;
				double updatedPrice;
				try
				{
					originalPrice = Double.parseDouble( parts[1] );
					updatedPrice = Double.parseDouble( parts[2] );
				}
				catch( NumberFormatException e )
				{
					throw new IOException( "Line " + lineNumber + " (\"" + line + "\") has a price that is not a number." );
				}

				products[index] = createProduct( parts[0], originalPrice, updatedPrice, lineNumber );
				index++;
			}

			if( index < count )
			{
				throw new IOException( "The first line says there are " + count + " products, but only "
						+ index + " were found." );
			}

			return products;
		}
	}

	/**
	 * Creates the product subclass that matches the given product type.
	 *
	 * @param type          the product type read from the file
	 * @param originalPrice the original price of the product
	 * @param updatedPrice  the updated price of the product
	 * @param lineNumber    the line in the file, used in the error message
	 * @return a new product of the matching subclass
	 * @throws IOException if the product type is not one of the seven known types
	 */
	private static Product createProduct( String type, double originalPrice, double updatedPrice,
			int lineNumber ) throws IOException
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
				throw new IOException( "Line " + lineNumber + " has an unknown product type \"" + type + "\"." );
		}
	}

	/**
	 * Returns the comparator for the chosen compare type. For the calculated
	 * difference, null is returned so the products are sorted with their own
	 * compareTo method (the Comparable interface).
	 *
	 * @param compareType o for original price, u for updated price or d for
	 *                    calculated difference
	 * @return the matching comparator, or null for the calculated difference
	 */
	private static Comparator<Product> getComparator( char compareType )
	{
		switch( compareType )
		{
			case 'o':
				return new OriginalPriceComparator();
			case 'u':
				return new UpdatedPriceComparator();
			default:
				return null;
		}
	}

	/**
	 * Sorts the products in descending order with the chosen algorithm. If no
	 * comparator is given, the products are compared with compareTo.
	 *
	 * @param products   the array to sort
	 * @param comparator the comparator to use, or null to use compareTo
	 * @param sortType   b, s, i, m, q or z
	 */
	private static void sort( Product[] products, Comparator<Product> comparator, char sortType )
	{
		switch( sortType )
		{
			case 'b':
				if( comparator == null ) Sorter.bubbleSort( products );
				else Sorter.bubbleSort( products, comparator );
				break;
			case 's':
				if( comparator == null ) Sorter.selectionSort( products );
				else Sorter.selectionSort( products, comparator );
				break;
			case 'i':
				if( comparator == null ) Sorter.insertionSort( products );
				else Sorter.insertionSort( products, comparator );
				break;
			case 'm':
				if( comparator == null ) Sorter.mergeSort( products );
				else Sorter.mergeSort( products, comparator );
				break;
			case 'q':
				if( comparator == null ) Sorter.quickSort( products );
				else Sorter.quickSort( products, comparator );
				break;
			case 'z':
				if( comparator == null ) Sorter.customSort( products );
				else Sorter.customSort( products, comparator );
				break;
		}
	}

	/**
	 * Returns the display name of the chosen sorting algorithm.
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
				return "Custom Sort";
		}
	}

	/**
	 * Displays the sorted products. With 10 products or less, every product is
	 * shown. With 1000 or less, the first, every hundredth and the last product
	 * are shown. Otherwise the first, every thousandth and the last are shown.
	 *
	 * @param products    the sorted array of products
	 * @param compareType the compare type, which decides which value is shown
	 */
	private static void displayResults( Product[] products, char compareType )
	{
		int count = products.length;

		if( count == 0 )
		{
			System.out.println( "The file has no products to display." );
			return;
		}

		if( count <= 10 )
		{
			for( int i = 0; i < count; i++ )
			{
				printLine( ordinal( i + 1 ) + " element:", products[i], compareType );
			}
			return;
		}

		int step = ( count <= 1000 ) ? 100 : 1000;

		printLine( "First element is:", products[0], compareType );
		for( int position = step; position < count; position += step )
		{
			printLine( ordinal( position ) + " element:", products[position - 1], compareType );
		}
		printLine( "Last element is:", products[count - 1], compareType );
	}

	/**
	 * Prints one line of output showing a label, the product's class and the
	 * value that was used for sorting.
	 *
	 * @param label       the text shown at the start of the line
	 * @param product     the product to show
	 * @param compareType the compare type, which decides which value is shown
	 */
	private static void printLine( String label, Product product, char compareType )
	{
		String value;
		switch( compareType )
		{
			case 'o':
				value = "Original Price: " + product.getOriginalPrice();
				break;
			case 'u':
				value = "Updated Price: " + product.getUpdatedPrice();
				break;
			default:
				value = "Real Difference: " + product.calcRealDiff();
				break;
		}
		System.out.printf( "%-22s %-20s %s%n", label, product.getClass().getName(), value );
	}

	/**
	 * Converts a position into its ordinal form, e.g. 1 becomes "1-st",
	 * 2 becomes "2-nd", 3 becomes "3-rd" and 11 becomes "11-th".
	 *
	 * @param number the position to convert
	 * @return the position with its ordinal suffix
	 */
	private static String ordinal( int number )
	{
		int lastTwo = number % 100;
		if( lastTwo >= 11 && lastTwo <= 13 )
		{
			return number + "-th";
		}
		switch( number % 10 )
		{
			case 1:
				return number + "-st";
			case 2:
				return number + "-nd";
			case 3:
				return number + "-rd";
			default:
				return number + "-th";
		}
	}
}