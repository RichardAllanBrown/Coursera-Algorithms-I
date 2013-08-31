//PercolationStats accepts a number of trials and a grid size and determines what proportion of cells must be
//open in order for the system to percolate.
public class PercolationStats {
	
	//The number of trials to perform.
	private int trialCount;
	
	//The proportion of cells opened to percolate the system, each entry represents one trial.
	private double[] results;
	
	//Constructor, accepts the size of the grid and number of trials, then performs them.
	public PercolationStats(int gridSize, int numberOfTrials) {
		if(gridSize <= 0 || numberOfTrials <= 0) {
			throw new IllegalArgumentException("Input must be a positive integer greater than 0");
		}
		
		this.trialCount = numberOfTrials;
		results = new double[numberOfTrials];
		
		for(int i = 0; i < this.trialCount; i++) {
			results[i] = performTrial(gridSize);
		}
	}
	
	//Returns the mean proportion of cells opened to percolate the system.
	public double mean() {
		return StdStats.mean(results);
	}
	
	//Returns the standard deviation of proportion of cells opened to percolate the system.
	public double stddev() {
		if (trialCount == 1) {
			return Double.NaN;
		} else {
			return StdStats.stddev(results);
		}
	}
	
	//Returns the lower, 95% confidence interval of open cell proportion, assumes trial number is over 30.
	public double confidenceLo() {
		return (mean() - (1.96 * stddev() / Math.sqrt(trialCount)));
	}
	
	//Returns the upper, 95% confidence interval of open cell proportion, assumes trial number is over 30.
	public double confidenceHi() {
		return (mean() + (1.96 * stddev() / Math.sqrt(trialCount)));
	}
	
	//Performs the trial by opening cells until the system percolates, adding the proportion to the results.
	private double performTrial(int gridLength) {
		Percolation perc = new Percolation(gridLength);
		int sitesOpened = 0;
		
		while(!perc.percolates()) {
			while(true) {
				int i = StdRandom.uniform(1, gridLength + 1);
				int j = StdRandom.uniform(1, gridLength + 1);
				
				if(!perc.isOpen(i, j)) {
					perc.open(i, j);
					sitesOpened++;
					
					break;
				}
			}
		}
		
		double result = (double)sitesOpened / (double)(gridLength * gridLength);
		
		return result;
	}
	
	//Main method that accepts in 2 arguments, the trial count and the size of the grid then prints the results.
	public static void main(String[] args) {
		int trialCount = Integer.parseInt(args[0]);
		int gridSize = Integer.parseInt(args[1]);
		
		PercolationStats stats = new PercolationStats(gridSize, trialCount);
		
		StdOut.println("mean                    = " + stats.mean());
		StdOut.println("stddev                  = " + stats.stddev());
		StdOut.println("95% confidence interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
	}
}

