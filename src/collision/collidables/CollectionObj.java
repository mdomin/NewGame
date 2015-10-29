package collision.collidables;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import collision.BucketCollection;
import collision.CollisionDetector;
import collision.actors.Movable;

public class CollectionObj extends Collidable {

	private List<Collidable> collidables;
	private int[] distancesFromCenter;
	private double[] angles;
	private int maxLength;
	
	public CollectionObj(Collidable collidable) {
		super();
		this.collidables = new ArrayList<Collidable>();
		collidables.add(collidable);
		distancesFromCenter = new int[1];
		distancesFromCenter[0] = 0;
		angles = new double[1];
		angles[0] = 0;
		maxLength = collidable.getMaxLength();
	}

	public CollectionObj(List<Collidable> collidables, int[] distances, int[] angles) {
		super();
		this.collidables = collidables;
		this.distancesFromCenter = distances;
		this.angles = new double[angles.length];
		this.angle = 0;
		setupAngles(angles);
		setCollidablesPos();
		maxLength = calcMaxLength();
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	public int calcMaxLength() {
		int temp;
		int maxDist = 0;
		int maxDistObj = 0;
		for(Collidable obj : collidables) {
			temp = obj.getMaxLength();
			if (temp > maxDistObj)
				maxDistObj = temp;
		}
		for(int dist : distancesFromCenter) {
			if (dist > maxDist)
				maxDist = dist;
		}
		return maxDistObj + maxDist;
	}
	
	@Override
	public void move(double x_vel, double y_vel) {
		super.move(x_vel, y_vel);
		for(Collidable obj : collidables) {
			obj.move(x_vel, y_vel);
		}
	}
	
	private void setupAngles(int[] angles) {
		for(int i = 0; i < angles.length; i++) {
			this.angles[i] = Math.toRadians(angles[i]);
		}
	}

	public void setCollidablesPos() {
		int tempX, tempY;
		for(int i = 0; i < collidables.size(); i++) {
			tempX = (int)(x + distancesFromCenter[i] * Math.cos(angle + angles[i]));
			tempY = (int)(y + distancesFromCenter[i] * Math.sin(angle + angles[i]));
			collidables.get(i).setPosition(tempX, tempY);
		}
	}
	
	@Override
	public void clearCollided() {
		super.clearCollided();
		for(Collidable obj : collidables) {
			obj.clearCollided();
		}
	}
	
	@Override
	public void clearChecked() {
		super.clearChecked();
		for(Collidable obj : collidables) {
			obj.clearChecked();
		}
	}

	@Override
	public void calculateCollisions() {
		for(Collidable obj : collidables) {
			collided.addAll(obj.getCollided());
		}
	}
	
	public List<Collidable> getCollidables() {
		return collidables;
	}
	
	public int size() {
		return collidables.size();
	}
	
	@Override
	public boolean checkCollisionSetup(Collidable object2) {
		return false;
	}

	@Override
	public boolean checkCollision(CircleObj object2) {
		return false;
	}

	@Override
	public boolean checkCollision(PolygonObj object2) {
		return false;
	}

	@Override
	public void react(Collidable second) {
	}

	@Override
	public int left() {
		return (int) (x - maxLength);
	}

	@Override
	public int right() {
		return (int) (x + maxLength);
	}

	@Override
	public int top() {
		return (int) (y - maxLength);
	}

	@Override
	public int bottom() {
		return (int) (y + maxLength);
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
	public int getNumObj() {
		return collidables.size();
	}
	
	@Override
	public void iterate() {
		setCollidablesPos();
	}
}
