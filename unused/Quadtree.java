package collision;


public class Quadtree {
	static int maxdepth = 10;
	static int maxobjects = 3;

	private Quadtree NW, NE, SW, SE;
	private Object objects[];
	private AABB quadAABB;
	private int depth;
	
	public boolean insert (Object newobject) {
		if (newobject.in_boundry(quadAABB)) {
			if (depth <= maxdepth) {
				if (objects.length >= maxobjects) {
					split();
				}
			}
		}
	}
	
	public void split () {
	}
}
