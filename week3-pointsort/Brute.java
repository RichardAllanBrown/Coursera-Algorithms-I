import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Brute {	
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
		for(int first = 0; first < points.length - 3; first++) {
			for(int second = first + 1; second < points.length - 2; second++) {
				for(int third = second + 1; third < points.length - 1; third++) {
					for(int fourth = third + 1; fourth < points.length; fourth++) {
						
						//calculate slopes
						double slopeFirstSecond = points[first].slopeTo(points[second]);
						double slopeFirstThird = points[first].slopeTo(points[third]);
						double slopeFirstFourth = points[first].slopeTo(points[fourth]);
						
						//If equal, they are collinear
						if(slopeFirstSecond == slopeFirstThird && slopeFirstSecond == slopeFirstFourth) {
							Point[] result = new Point[4];
							result[0] = points[first];
							result[1] = points[second];
							result[2] = points[third];
							result[3] = points[fourth];
							
							Arrays.sort(result);
							
							//Output to standard out and draw
							StdOut.print(result[0].toString());
							StdOut.print(" -> ");
							StdOut.print(result[1].toString());
							StdOut.print(" -> ");
							StdOut.print(result[2].toString());
							StdOut.print(" -> ");
							StdOut.print(result[3].toString());
							StdOut.println();
							
							result[0].drawTo(result[3]);
						}
					}
				}
			}
		}
	}
}
