package collision;

public class EllipseObj extends Collidable{
	
	private int r1;
	private int r2;
	private int largerRadius;

	public EllipseObj(int x, int y) {
		super(x, y);
		r1 = 10;
		r2 = 5;
		calculateLargerRadius();
	}
	
	public EllipseObj(int x, int y, int r1, int r2) {
		super(x, y);
		this.r1 = r1;
		this.r2 = r2;
		calculateLargerRadius();
	}
	
	private void calculateLargerRadius() {
		largerRadius = Math.max(r1, r2);
	}
	
	public void setR1(int r1) {
		this.r1 = r1;
		calculateLargerRadius();
	}
	
	public void setR2(int r2) {
		this.r2 = r2;
		calculateLargerRadius();
	}

	@Override
	public double getR1() {
		return r1;
	}
	
	@Override
	public double getR2() {
		return r2;
	}

	@Override
	public void react(Collidable second) {
	}

	@Override
	public int left() {
		return (int) (x - largerRadius);
	}

	@Override
	public int right() {
		return (int) (x + largerRadius);
	}

	@Override
	public int top() {
		return (int) (y - largerRadius);
	}

	@Override
	public int bottom() {
		return (int) (y + largerRadius);
	}
	
	@Override
	public double getRadius() {
		return largerRadius;
	}

	@Override
	public double getRadius(Collidable obj) {
		double tempAngleBetween = angleBetween(obj);
		double tempAngleOffset = getAngleRad();
		double tempx = r1 * Math.cos(tempAngleBetween) * Math.cos(tempAngleOffset) - r2 * Math.sin(tempAngleBetween) * Math.sin(tempAngleOffset);
		double tempy = r1 * Math.sin(tempAngleBetween) * Math.sin(tempAngleOffset) + r2 * Math.cos(tempAngleBetween) * Math.cos(tempAngleOffset);
		return Math.sqrt(tempx * tempx + tempy * tempy);
	}
}
