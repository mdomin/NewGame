package collision;

import java.util.ArrayList;
import java.util.List;

import collision.collidables.Collidable;

public class Bucket {

	private List<Collidable> objects;
	
	public Bucket() {
		objects = new ArrayList<Collidable>();
	}
	
	public int numElements() {
		return objects.size();
	}
	
	public void attemptAdd(Collidable obj) {
		if (!objects.contains(obj))
			objects.add(obj);
	}
	
	public void clear() {
		objects.clear();
	}

	public List<CollisionPair> checkCollisions() {
		List<CollisionPair> temp = new ArrayList<CollisionPair>();
		int collectNum1, collectNum2;
		
		// For all object pairings within the bucket
		for (Collidable object1 : objects) {
			for (Collidable object2 : objects) {
				collectNum1 = object1.getCollectionNum();
				collectNum2 = object2.getCollectionNum();
				
				// Not same object and hasn't collided yet
				if (object1 != object2 && !object1.getCollided().contains(object2) 
						&& (collectNum1 == -1 || collectNum2 == -1 || collectNum1 != collectNum2)) {// && !object1.getChecked().contains(object2)) {
					if(object1.checkCollisionSetup(object2)) {
						// Add the collision pair to be processed
						temp.add(new CollisionPair(object1, object2));
						// Add collided to both
						object1.addCollided(object2);
						object2.addCollided(object1);
					}
					//object1.addChecked(object2);
					//object2.addChecked(object1);
				}
			}
		}
		
		return temp;
	}
}
