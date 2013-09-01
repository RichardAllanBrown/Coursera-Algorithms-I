/** Subset will print k random strings from an input array of strings.
 * Input is expected to start with number of strings to return,
 * then a space delimited input of strings. */
public class Subset {
	public static void main(String[] args) {
		int subsetSize = Integer.parseInt(args[0]);
		String[] inputStrings = StdIn.readStrings();
		
		performSubset(subsetSize, inputStrings);
	}

	private static void performSubset(int subsetSize, String[] strings) {
		
		//Knuth shuffle
		for(int i = 1; i < strings.length; i++) {
			int randIndex = StdRandom.uniform(i + 1);
			
			String temp = strings[i];
			strings[i] = strings[randIndex];
			strings[randIndex] = temp;
		}
		
		//Output
		for(int j = 0; j < subsetSize; j++) {
			StdOut.println(strings[j]);
		}
	}
}
