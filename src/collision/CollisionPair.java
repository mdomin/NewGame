package collision;

import collision.collidables.Collidable;

public class CollisionPair {

	  private final Collidable first;
	  private final Collidable second;

	  public CollisionPair(Collidable left, Collidable right) {
		  this.first = left;
		  this.second = right;
	  }
	  
	  public Collidable getFirst() {
		  return first;
	  }
	  
	  public Collidable getSecond() {
		  return second;
	  }
	  
	  public void act() {
		  first.react(second);
		  second.react(first);
	  }
}
