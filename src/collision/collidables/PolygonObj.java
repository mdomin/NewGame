package collision.collidables;

import java.awt.Graphics2D;

import collision.BucketCollection;
import collision.CollisionDetector;
import collision.actors.Movable;

public class PolygonObj extends Collidable{
	private int numPoints;
	
	private int[] lengths;
	private int[] angles;
	private double[] xpoints;
	private double[] ypoints;
	
	private int maxLength;
	
	public PolygonObj(int[] lengths, int[] angles) {
		super();
		this.lengths = lengths;
		this.angles = angles;
		numPoints = lengths.length;
		xpoints = new double[numPoints];
		ypoints = new double[numPoints];
		calculatePoints();
		maxLength = getMaxLength();
	}
	
	
	@Override
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		calculatePoints();
	}
	
	private void calculatePoints() {
		double angleRad;
		for(int i = 0; i < lengths.length; i++) {
			angleRad = Math.toRadians(angles[i]);
			xpoints[i] = (x + lengths[i] * Math.cos(angle + angleRad));
			ypoints[i] = (y + lengths[i] * Math.sin(angle + angleRad));
		}
	}
	
	public int getMaxLength() {
		int tempMax = 0;
		for(int val : lengths) {
			if (val > tempMax)
				tempMax = val;
		}
		return tempMax;
	}
	
	public int numPoints() {
		return numPoints;
	}
	
	public int[] getXpoints() {
		return this.getXpoints(0, 0);
	}
	
	public int[] getXpoints(double interpolation, double x_vel) {
		int[] temp = new int[numPoints];
		for(int i = 0; i < numPoints; i++) {
			temp[i] = (int) (xpoints[i] + interpolation * x_vel);
		}
		return temp;
	}
	
	public int[] getYpoints() {
		return this.getYpoints(0, 0);
	}
	
	public int[] getYpoints(double interpolation, double y_vel) {
		int[] temp = new int[numPoints];
		for(int i = 0; i < numPoints; i++) {
			temp[i] = (int) (ypoints[i] + interpolation * y_vel);
		}
		return temp;
	}
	
	public double[] getXdoubles() {
		return xpoints;
	}
	
	public double[] getYdoubles() {
		return ypoints;
	}
	
	/*
	public int[] getYpoints() {
		int[] temp = new int[numPoints];
		Iterator<Integer> iter = ypoints.iterator();
		for (int i = 0; i < numPoints; i++) {
	        temp[i] = iter.next().intValue();
		}
		return temp;
	}
	*/
	
	@Override
	public void react(Collidable second) {
	}

	@Override
	public int left() {
		return (int) x - maxLength;
	}

	@Override
	public int right() {
		return (int) x + maxLength;
	}

	@Override
	public int top() {
		return (int) y - maxLength;
	}

	@Override
	public int bottom() {
		return (int) y + maxLength;
	}
	
	public boolean checkCollisionSetup(Collidable object2) {
		return object2.checkCollision(this);
	}

	// Collision will be checked in CircleObj
	@Override
	public boolean checkCollision(CircleObj object2) {
		return false;
	}

	/**
	 * Modified code obtained from
	 * Randolph Franklin
	 * http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
	 */
	@Override
	public boolean checkCollision(PolygonObj object2) {
		int j, k;
		double testX, testY;
		boolean result;
		
		double[] obj2x = object2.getXdoubles();
		double[] obj2y = object2.getYdoubles();
		
		// For all points in other object
		for(int i = 0; i < object2.numPoints; i++) {
			testX = obj2x[i];
			testY = obj2y[i];
			result = false;
			
			k = numPoints - 1;
			/** Test points
			 * 	Using the ray casting technique (calculate lines
			 *  and determine the number of times a ray passes through)
			 */
			for(j = 0; j < numPoints; j++) {				
				if( ((ypoints[j] > testY) != (ypoints[k] > testY)) &&
					(testX < (xpoints[k] - xpoints[j]) * (testY - ypoints[j]) / (ypoints[k] - ypoints[j]) + xpoints[j]) )
					result = !result;
				k = j;
			}
			if (result)
				return true;
		}
		return false;
		
		/**
		int pnpoly(int nvert, float *vertx, float *verty, float testx, float testy)
		{
		  int i, j, c = 0;
		  for (i = 0, j = nvert-1; i < nvert; j = i++) {
		    if ( ((verty[i]>testy) != (verty[j]>testy)) &&
		     (testx < (vertx[j]-vertx[i]) * (testy-verty[i]) / (verty[j]-verty[i]) + vertx[i]) )
		       c = !c;
		  }
		  return c;
		}
		**/
	}
	
	@Override
	public void drawVisitor(CollisionDetector base, Graphics2D g2d, Movable owner) {
		base.drawObj(g2d, this, owner);
	}
	
	@Override
	public void bucketVisitor(BucketCollection buckets, int bucketSize) {
		buckets.attemptPlace(this, bucketSize);
	}
	
	@Override
	public void iterate() {
		calculatePoints();
	}
}
