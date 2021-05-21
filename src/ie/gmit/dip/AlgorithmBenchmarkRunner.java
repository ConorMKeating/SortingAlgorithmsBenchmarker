package ie.gmit.dip;

import java.text.DecimalFormat;

public class AlgorithmBenchmarkRunner {

	//Used to format results to 3 decimal places.
	private static DecimalFormat df = new DecimalFormat("#0.000");
	//String array used to call algorithms.
	private static final String[] algorithmsArray = { "Bubble Sort", "Cocktail Sort", "Shell Sort", "Quick Sort",
			"Counting Sort"};
	//Array of input sizes for testing in each algorithm.
	private static int[] testValuesArray = { 100, 250, 500, 750, 1000, 1500, 2500, 3750, 5000, 6250, 7500, 8750, 10000};


	
	public static void main(String[] args) {
		
		//Simple output on screen to identify the program.
		System.out.println("\n\t\t\t************************************************************");
		System.out.println("\t\t\t************************************************************");
		System.out.println("\t\t\t*************SIMPLE SORT ALGORITHM COMPARISON***************");
		System.out.println("\t\t\t************************************************************");
		System.out.println("\t\t\t************************************************************");
		System.out.print("\nInput Size:\t");
		
		//This for loop prints the values in testValuesArray as headings for the results.
		for (int i = 0; i < testValuesArray.length; i++) {
			System.out.print(testValuesArray[i] + "\t");
		}
		System.out.println("\n");

		//A time count for the entire sorting process is included here for printing later.
		long beginAlgos = System.nanoTime();
		
		//This loop begins the sorting process for all algorithms.
		for (int i = 0; i < algorithmsArray.length; i++) {
			runSortAlgorithm(testValuesArray, algorithmsArray[i]);
		}
		long endAlgos = System.nanoTime();
		long algoTimeElapsed = (endAlgos - beginAlgos);
		double timeTaken = algoTimeElapsed/1000000000d;
		System.out.println("\nTotal execution time: " + df.format(timeTaken) + " seconds.");

	}

	//A standard loop to print results to screen in the correct format.
	public static void printResults(String sortName, double[] averages) {
		System.out.print(sortName + "\t");
		for (double timeData : averages) {
			System.out.print(df.format(timeData) + "\t");
		}
		System.out.println();
	}

	//Generates the random array values for sorting with a range of 0 to n-1. n being size of input.
	public static int[] generateRandomArray(int n) {
		int[] array = new int[n];
		for (int i = 0; i < n; i++) {
			array[i] = (int) (Math.random() * n);
		}
		return array;
	}

	//Runs loops to cover numRuns(10) runs for each input size, and each algorithm.
	public static void runSortAlgorithm(int[] testValues, String sortAlgoName) {

		//Used later to store time results from each input size.
		double[] averages = new double[testValues.length];
		//Can be changed to increase or decrease the number of runs to calculate average times.
		int numRuns = 10;
		//Initialises the array storing random numbers for sorting.
		int[] randomNumsArray = null;
		/*Outer loop runs once for each different input size.
		 * Prints the results to screen after inner loop terminates.
		 */
		for (int i = 0; i < testValues.length; i++) {
			//Initialises or resets time variables for accurate recording.
			long startTime, endTime, timeElapsed = 0;
			/* Inner loop does numRuns iterations for each input size,
			 * checks which sorting algorithm should be used,
			 * generates a random array,  starts the timer,
			 * calls the appropriate sorting algorithm, ends the timer,
			 * gets the average of numRuns runs and records the times.
			 */
			for (int j = 0; j < numRuns; j++) {
				//If else is used to check which algorithm to call.
				if (sortAlgoName.equals("Bubble Sort")) {
					//Generate random array, start timer, run algorithm, end timer.
					randomNumsArray = generateRandomArray(testValues[i]);
					startTime = System.nanoTime();
					bubbleSort(randomNumsArray);
					endTime = System.nanoTime();
				} else if (sortAlgoName.equals("Cocktail Sort")) {
					randomNumsArray = generateRandomArray(testValues[i]);
					startTime = System.nanoTime();
					cocktailSort(randomNumsArray);
					endTime = System.nanoTime();
				} else if (sortAlgoName.equals("Shell Sort")) {
					randomNumsArray = generateRandomArray(testValues[i]);
					startTime = System.nanoTime();
					shellSort(randomNumsArray);
					endTime = System.nanoTime();
				} else if (sortAlgoName.equals("Quick Sort")) {
					randomNumsArray = generateRandomArray(testValues[i]);
					int low = 0;
					int high = randomNumsArray.length - 1;
					startTime = System.nanoTime();
					quickSort(randomNumsArray,low,high);
					endTime = System.nanoTime();
				} else {
					randomNumsArray = generateRandomArray(testValues[i]);
					startTime = System.nanoTime();
					countingSort(randomNumsArray);
					endTime = System.nanoTime();
				}
				//Sum up times for all runs of input value
				timeElapsed += (endTime - startTime);
			}
			//Convert to milliseconds
			double elapsedMillis = timeElapsed / 1000000d;
			//Place average time result in array for printing.
			averages[i] = elapsedMillis /= numRuns;
		}
		printResults(sortAlgoName, averages);

	}

	// Code taken from CTA course materials
	public static void bubbleSort(int[] randomNums) {

		int outer, inner;
		//Outer loop moves down, ignoring the most recent sorted item each time.
		for (outer = randomNums.length - 1; outer > 0; outer--) {
			//Inner loop moves up, bringing highest value to the last unsorted index. 
			for (inner = 0; inner < outer; inner++) {
				//Test for whether we need to swap higher value up in index.
				if (randomNums[inner] > randomNums[inner + 1]) {
					int temp = randomNums[inner];
					randomNums[inner] = randomNums[inner + 1];
					randomNums[inner + 1] = temp;
				}
			}
		}
	}

	// From https://www.geeksforgeeks.org/cocktail-sort/
	public static void cocktailSort(int[] randomNums) {
		
		//Boolean decides whether to keep running the sorting process.
		boolean swapped = true;
		//Set start and end points for comparison.
		int start = 0;
		int end = randomNums.length-1;

		//While loop test. If true.. more swapping. If false... finished.
		while (swapped == true) {
			//Set to false for this run
			swapped = false;

			//Bubble sort here, If any swaps occur, swapped set to true.
			for (int i = start; i < end; ++i) {
				if (randomNums[i] > randomNums[i + 1]) {
					int temp = randomNums[i];
					randomNums[i] = randomNums[i + 1];
					randomNums[i + 1] = temp;
					swapped = true;
				}
			}

			// If false here, no swaps occurred, exit sorting.
			if (swapped == false)
				break;

			//Set to false again for checking if sorted later.
			swapped = false;

			//Decrement so we ignore bubbled highest value on next run.
			end = end - 1;

			//Bubble in reverse. If swap occurs, set swapped to true.
			for (int i = end - 1; i >= start; i--) {
				if (randomNums[i] > randomNums[i + 1]) {
					int temp = randomNums[i];
					randomNums[i] = randomNums[i + 1];
					randomNums[i + 1] = temp;
					swapped = true;
				}
			}

			//Increment so we ignore reverse bubbled lowest value on next run.
			start = start + 1;
		}
	}
	
	// Code from https://www.geeksforgeeks.org/shellsort/
	public static void shellSort(int[] randomNums) {
		int n = randomNums.length;

		// Outer loop cuts gap in half every iteration.
		for (int gap = n / 2; gap > 0; gap /= 2) {
			// This loop sets the gapped elements for sorting.
			for (int i = gap; i < n; i++) {
				// Identify next gapped element for sorting.
				int temp = randomNums[i];
				int j;
				
				// Insertion sort carried out here on gapped elements.
				for (j = i; j >= gap && randomNums[j - gap] > temp; j -= gap)
					randomNums[j] = randomNums[j - gap];

				//Add tested element to correct position.
				randomNums[j] = temp;
			}
		}
	}
	
	// Code taken from https://www.programcreek.com/2012/11/quicksort-array-in-java/
	public static void quickSort(int[] randomNums, int low, int high) {
		// Base case for stopping recursion.
		if (randomNums == null || randomNums.length == 0)
			return;
		// Base case stopping recursion when low and high cross.
		if (low >= high)
			return;

		// Sets the pivot as the middle value in array.
		int middle = low + (high - low) / 2;
		int pivot = randomNums[middle];

		// Set left to low and set right to high.
		int i = low, j = high;
		
		/* While loop executes until bounds cross, after which
		 * all values to the left are lower than pivot
		 * and all to the right are higher.
		 */
		
		while (i <= j) {
			// Find a value greater than pivot.
			while (randomNums[i] < pivot) {
				i++;
			}
			
			// Find a value less than pivot.
			while (randomNums[j] > pivot) {
				j--;
			}

			// Perform swap if values are in wrong position.
			if (i <= j) {
				int temp = randomNums[i];
				randomNums[i] = randomNums[j];
				randomNums[j] = temp;
				i++;
				j--;
			}
		}

		// Recursive calls to deal with partitions created.
		if (low < j)
			quickSort(randomNums, low, j);

		if (high > i)
			quickSort(randomNums, i, high);
	}

	// From https://www.geeksforgeeks.org/counting-sort/
	private static void countingSort(int[] randomNums) {
		int n = randomNums.length;

		// Initialise array for results of sorting.
		int output[] = new int[n];

		// Initialise count array to store count of values.
		int count[] = new int[n];
		// Loop to set all values to 0.
		for (int i = 0; i < n; ++i)
			count[i] = 0;

		// Store count of each value.
		for (int i = 0; i < n; ++i)
			++count[randomNums[i]];

		// Build the count sum array for reference position.
		for (int i = 1; i < n; ++i)
			count[i] += count[i - 1];

		// Populate the output array in reverse to maintain stability.
		for (int i = n - 1; i >= 0; i--) {
			output[count[randomNums[i]] - 1] = randomNums[i];
			--count[randomNums[i]];
		}

		// Copy contents of sorted array to original array.
		for (int i = 0; i < n; ++i)
			randomNums[i] = output[i];
	}


}
