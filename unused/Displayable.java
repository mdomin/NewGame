package collision;

public class Displayable {

	protected double radius;

	private double x;
	private double y;
	private double x_vel;
	private double y_vel;
	private boolean markedForDeletion;

	
	public Displayable(int x, int y) {
		this.x = x;
		this.y = y;
		x_vel = 0;
		y_vel = 0;
		radius = 20; // Should be gotten from the image
		markedForDeletion = false;
	}
	
	public void delete() {
		markedForDeletion = true;
	}
	
	public boolean checkDeleted() {
		return markedForDeletion;
	}
	
	public void setVelocity(double x_vel, double y_vel) {
		this.x_vel = x_vel;
		this.y_vel = y_vel;
	}
	
	public void setXVel(double x_vel) {
		this.x_vel = x_vel;
	}
	
	public void setYVel(double y_vel) {
		this.y_vel = y_vel;
	}
	
	public double getXVel() {
		return x_vel;
	}
	
	public double getYVel() {
		return y_vel;
	}
	
	public int left() {
		return (int)(x - radius);
	}
	
	public int right() {
		return (int)(x + radius);
	}
	
	public int top() {
		return (int)(y - radius);
	}
	
	public int bottom() {
		return (int)(y + radius);
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public double trueX() {
		return x;
	}
	
	public double trueY() {
		return y;
	}
	
	public double getRadius() {
		return radius;
	}
	
	public void move(int rightEdge, int bottomEdge) {
		if (x + x_vel - radius <= 0) {
			x_vel = -1 * x_vel;
		}
		else if (x + x_vel + radius >= rightEdge) {
			x_vel = -1 * x_vel;
		}
		else if (y + y_vel - radius <= 0) {
			y_vel = -1 * y_vel;
		}
		else if (y + y_vel + radius >= bottomEdge) {
			y_vel = -1 * y_vel;
		}
		
		this.x += this.x_vel;
		this.y += this.y_vel;
	}
}
