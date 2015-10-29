package collision.collidables;

import java.awt.Graphics2D;

import collision.BucketCollection;
import collision.CollisionDetector;
import collision.actors.Movable;

public interface CollideInterface {
	public boolean checkCollisionSetup(Collidable object2);
	public boolean checkCollision(CircleObj object2);
	public boolean checkCollision(PolygonObj object2);
	
	public int getMaxLength();

	public void react(Collidable second);
	
	public void iterate();
	
	public int left();
	public int right();
	public int top();
	public int bottom();
	
	public void drawVisitor(CollisionDetector base, Graphics2D g2d, Movable entity);
	
	public void bucketVisitor(BucketCollection buckets, int bucketSize);
	
	public int getNumObj();
}