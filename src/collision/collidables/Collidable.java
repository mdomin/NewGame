package collision.collidables;

import java.util.ArrayList;
import java.util.List;

public abstract class Collidable implements CollideInterface{

	protected List<Collidable> collided;
	private List<Collidable> checked;
	protected double x;
	protected double y;
	
	protected double angle;
			
	protected int collectionNum;
	
	public Collidable() {
		this.angle = 0;
		collectionNum = -1;
		collided = new ArrayList<Collidable>();
		checked = new ArrayList<Collidable>();
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Collidable collectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
		return this;
	}
	
		
	public int getCollectionNum() {
		return collectionNum;
	}
	
	public void clearCollided() {
		collided.clear();
	}
	
	public List<Collidable> getCollided() {
		return collided;
	}

	// Adds to the collided 
	public void addCollided(Collidable obj) {
		collided.add(obj);
	}
	
	public void clearChecked() {
		checked.clear();
	}
	
	public List<Collidable> getChecked() {
		return checked;
	}
	
	public void addChecked(Collidable obj) {
		checked.add(obj);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return (int)x;
	}
	
	public int getX(double interpolation, double x_vel) {
		return (int) (x + x_vel * interpolation);
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getY(double interpolation, double y_vel) {
		return (int) (y + y_vel * interpolation);
	}
	
	public double trueX() {
		return x;
	}
	
	public double trueY() {
		return y;
	}
	
	public double angleBetween(Collidable other) {
		return Math.atan2(y - other.y, other.x - x);
	}
	
	public void move(double x_vel, double y_vel) {
		//this.x += speed * Math.cos(angle);
		//this.y += speed * Math.sin(angle);
		this.x += x_vel;
		this.y += y_vel;
		iterate();
	}
		
		
		/*
		double x_vel = getXVel();
		double y_vel = getYVel();
		
		if (y + y_vel - getRadius() <= topEdge && y_vel < 0) {
			y = topEdge + getRadius() - y_vel;
			setAngle(180 - angle);
		}
		else if (y + y_vel + getRadius() >= bottomEdge && y_vel > 0) {
			y = bottomEdge - getRadius() - y_vel;
			setAngle(540 - angle);
		}
		
		if (x + x_vel - getRadius() <= leftEdge && x_vel < 0) {
			x = leftEdge + getRadius() - x_vel;
			setAngle(180 - angle);
		}
		else if (x + x_vel + getRadius() >= rightEdge && x_vel > 0) {
			x = rightEdge - getRadius() - x_vel;
			setAngle(540 - angle);
		}
		*/
	
	// Checks its collision between itself and another object.
	// This check is for circular hit-boxes. In the future,
	// perhaps function overloading can be used with different
	// classes for hit-box types.
	/*
	public boolean checkCollision(Collidable object2) {
		double xpart = this.getX() - object2.getX();
		double ypart = this.getY() - object2.getY();
		double radiusPart = getRadius(object2) + getRadius(this);
		
		return xpart * xpart + ypart * ypart <= radiusPart * radiusPart;
		//return xpart * xpart + ypart * ypart <= radiusPart * radiusPart;
	}
	*/
	
	/* LEGACY
	private void calculateAngle() {
		angle = Math.atan2(y_vel, x_vel);
	} */
	
	public int getNumObj() {
		return 1;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getAngle() {
		return angle;
	}
	
	public void increaseAngle(double angleAdd) {
		angle += angleAdd;
		while (angle > 2 * Math.PI)
			angle -= 2 * Math.PI;
		while (angle < 0)
			angle += 2 * Math.PI;
	}

	// DO NOTHING
	public void calculateCollisions() {}
}
