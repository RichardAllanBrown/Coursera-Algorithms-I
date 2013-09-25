import java.util.LinkedList;
import java.util.List;


/** PointSET represents a set of 2d points in a unit square. */
public class PointSET {
	
	private SET<Point2D> points;
	
	/** Constructor, creates a SET of points */
	public PointSET() {
		points = new SET<Point2D>();
	}
	
	/** Returns true when there are no points in the set */
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/** Returns the number of points in the set */
	public int size() {
		return points.size();
	}
	
	/** Used to insert a new point into the set */
	public void insert(Point2D p) {
		points.add(p);
	}
	
	/** Returns true if the point is contained within the set */
	public boolean contains(Point2D p) {
		return points.contains(p);
	}
	
	/** Uses StdDraw to draw the points in the set on screen */
	public void draw() {
		 for(Point2D point : points) {
			 point.draw();
		 }
	}
	
	/** Returns a set of points held within a rectangle */
	public Iterable<Point2D> range(RectHV rect) {
		List<Point2D> bounded = new LinkedList<Point2D>();
		
		for(Point2D point : points) {
			
			if(rect.contains(point)) {
				bounded.add(point);
			}
		}
		
		return bounded;
	}
	
	/** Returns the nearest point to the provided Point2D */
	public Point2D nearest(Point2D p) {
		Point2D champion = null;
		
		for(Point2D point : points) {
			
			if(champion == null) {
				champion = point;
			} else {
				
				if(p.distanceTo(point) < p.distanceTo(champion)) {
					champion = point;
				}
			}
		}
		
		return champion;
	}
}