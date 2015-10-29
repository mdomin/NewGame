package collision.weapons;

import collision.collidables.CircleObj;
import collision.projectiles.Bullet;

public class BasicGun extends Weapon{
	
	static CircleObj hitbox = (new CircleObj()).radius(3);;
	static Bullet projectile = new Bullet(hitbox, 5);
	//fireSpeed = 10;
	//reload = 40;	// Game ticks		40 * 25 = 1000 milliseconds = 1sec

	public BasicGun() {
		super(projectile, 10, 40);
	}

}
