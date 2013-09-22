import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();  // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
    	if (this.x == that.x && this.y != that.y) {
    		return Double.POSITIVE_INFINITY;
    	} else if (this.x == that.x && this.y == that.y) {
    		return Double.NEGATIVE_INFINITY;
    	} else if (that.y - this.y == 0) {
    		return 0.0;
    	}
    	
        return ((double)(that.y - this.y) / (double)(that.x - this.x));
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
    	if (this.y < that.y) {
        	return -1;
        } else if (this.y > that.y) {
        	return 1;
        } else {
        	if (this.x < that.x) {
        		return -1;
        	} else if (this.x > that.x) {
        		return 1;
        	} else {
        		return 0;
        	}
        }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class BySlope implements Comparator<Point>
    {
		public int compare(Point o1, Point o2) {
			double slope1 = Point.this.slopeTo(o1);
			double slope2 = Point.this.slopeTo(o2);
			
			if (slope1 < slope2) return -1;
			if (slope2 < slope1) return 1;
			return 0;
		}
    	
    }

    // unit test
    public static void main(String[] args) {
        
    }
}