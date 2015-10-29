package collision.collidables;

import java.awt.Graphics2D;

import collision.BucketCollection;
import collision.CollisionDetector;
import collision.actors.Movable;

public class CircleObj extends Collidable{
	
	private int radius;
	
	public CircleObj() {
		super();
	}
	
	public CircleObj radius(int radius) {
		this.radius = radius;
		return this;
	}
	
	
	
	public double getRadius() {
		return radius;
	}
	
	public int getMaxLength() {
		return radius;
	}
	
	@Override
	public void react(Collidable second) {
		/*
		double r1 = getRadius();
		double r2 = second.getRadius(this);
		r1 *= r1;
		r2 *= r2;
		if (r1 >= r2)
			radius = (int) Math.sqrt((r1) + (r2));
		else
			delete();
		*/
		/*
		x_vel = -1 * x_vel;
		y_vel = -1 * y_vel;
		*/
	}
	
	@Override
	public int left() {
		return (int)(x - radius);
	}
	
	@Override
	public int right() {
		return (int)(x + radius);
	}
	
	@Override
	public int top() {
		return (int)(y - radius);
	}
	
	@Override
	public int bottom() {
		return (int)(y + radius);
	}
	
	// Collision visitor
	public boolean checkCollisionSetup(Collidable object2) {
		return object2.checkCollision(this);
	}

	// Visitor
	@Override
	public boolean checkCollision(CircleObj object2) {
		double tempx = object2.getX() - this.getX();
		double tempy = object2.getY() - this.getY();
		double tempr = object2.getRadius() + this.getRadius();
		return tempx * tempx + tempy * tempy <= tempr * tempr;
	}

	// Visitor
	@Override
	public boolean checkCollision(PolygonObj object2) {
		double tempx, tempy;
		for(int i = 0; i < object2.numPoints(); i++) {
			tempx = object2.getXpoints()[i] - x;
			tempy = object2.getYpoints()[i] - y;
			if (tempx * tempx + tempy * tempy <= radius * radius)
				return true;
		}
		return false;
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
	public void iterate() {}
}
