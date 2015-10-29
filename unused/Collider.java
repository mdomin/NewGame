package collision.collidables;

public class Collider {

	public boolean test(CircleObj collidable, CircleObj object2) {
		return false;
	}
	
	public boolean test(CircleObj collidable, PolygonObj object2) {
		return false;
	}
	
	public boolean test(PolygonObj collidable, CircleObj object2) {
		return false;
	}

	public boolean test(PolygonObj quadrilateral, PolygonObj object2) {
		return false;
	}
}
