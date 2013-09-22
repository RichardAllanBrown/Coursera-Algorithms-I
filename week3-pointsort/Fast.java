import java.util.Arrays;

public class Fast {
	private static final int minCollinearCount = 4;
	
	public static void main(String[] args) {
		StdDraw.setXscale(0, 32768);
		StdDraw.setYscale(0, 32768);
		StdDraw.setPenRadius(0.01);
		
		//read name of input file from standard input
		String fileName = args[0];
		
		Point[] input = readInputFile(fileName);
		findCollinearPoints(input);
	}

	private static Point[] readInputFile(String fileName) {
		//Read data from input file
		In in = new In(fileName);
		
		int datasetSize = in.readInt();
		
		Point[] points = new Point[datasetSize];
		
		for(int rowsRead = 0; rowsRead < datasetSize; rowsRead++) {
			int x = in.readInt();
			int y = in.readInt();
			
			points[rowsRead] = new Point(x, y);
			
			points[rowsRead].draw();
		}
		
		StdDraw.setPenRadius();
		
		return points;
	}

	private static void findCollinearPoints(Point[] points) {
		
		for(int i = 0; i < points.length - 1; i++) {
			
			//Sort the array with respect to one of the points
			Arrays.sort(points, i + 1, points.length, points[i].SLOPE_ORDER);
			
			//Calculate slope between i point and i + 1 to start with
			double currentSlope = points[i].slopeTo(points[i + 1]);
			int consecutiveCount = 1;
			
			//Move through the rest of the array comparing slopes
			for(int compare = i + 2; compare < points.length; compare++) {
				
				//When the same, increment the count because points are collinear
				if (points[i].slopeTo(points[compare]) == currentSlope) {
					consecutiveCount++;
					
				//Otherwise, reset the count and set the slope to compare
				} else {
					currentSlope = points[i].slopeTo(points[compare]);
					consecutiveCount = 1;
				}
				
				if (consecutiveCount >= minCollinearCount) {
					//Continue the search until it changes
					
					
					//Write the output
					
					points[i].drawTo(points[compare]);
					
				}
			}
		}
	}
}
