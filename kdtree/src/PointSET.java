import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
	
	private Set<Point2D>  points;
	
	public PointSET() {
		// construct an empty set of points 
		points=new TreeSet<>();
	}
	
	public boolean isEmpty() {
	   // is the set empty? 
		return points.isEmpty();
	}
	
	public int size()    {
		// number of points in the set
		return points.size();
	}
	
	public void insert(Point2D p) {
	   // add the point to the set (if it is not already in the set)
		checknotNull(p);
		points.add(p);
	}
	
	private void checknotNull(Object p) {
		// TODO Auto-generated method stub
		if(p==null)
			throw new NullPointerException();
	}
	public boolean contains(Point2D p)  {
	   // does the set contain point p? 
		checknotNull(p);
		return points.contains(p);
	}
	public void draw()  {
	   // draw all points to standard draw 
		for(Point2D i:points) {
			StdDraw.point(i.x(),i.y());
		}
	}
	public Iterable<Point2D> range(RectHV rect) {
	   // all points that are inside the rectangle (or on the boundary) 
		checknotNull(rect);
		
		List<Point2D> soln=new ArrayList<Point2D>();
		for(Point2D point:points) {
			if(rect.contains(point))
				soln.add(point);
		}
		return soln;
	}
	
	public  Point2D nearest(Point2D p)  {
		// a nearest neighbor in the set to point p; null if the set is empty 
		checknotNull(p);
		Point2D near=null;
		
		for(Point2D point:points) {
			if(near==null || point.distanceTo(p)<near.distanceTo(p))
				near=point;
		}
		
		return near;
	}

	public static void main(String[] args)   {
	   // unit testing of the methods (optional) 
	}
}