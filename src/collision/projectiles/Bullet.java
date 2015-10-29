package collision.projectiles;

import collision.actors.Entity;
import collision.collidables.Collidable;

public class Bullet extends ProjectileAbstract implements ProjectileInterface{	
	
	public Bullet(Collidable hitbox, int damage) {
		super(hitbox, damage);
	}
	
	// No reaction for ordinary bullet
	// Delete after damage
	@Override
	public void react(Collidable second) {
		delete();
	}
}
