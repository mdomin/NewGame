package collision.weapons;

import java.util.ArrayList;
import java.util.List;

import collision.projectiles.Bullet;



public abstract class Weapon {

	private List<Bullet> bullets;
	private int fireSpeed;
	private int reload;
	
	private int reloadTimer;
	
	
	public Weapon(List<Bullet> bullets, int fireSpeed, int reload) {
		this.bullets = bullets;
		this.fireSpeed = fireSpeed;
		this.reload = reload;
		reloadTimer = 0;
	}
	
	public Weapon(Bullet bullet, int fireSpeed, int reload) {
		this.bullets = new ArrayList<Bullet>();
		bullets.add(bullet);
		this.fireSpeed = fireSpeed;
		this.reload = reload;
		reloadTimer = 0;
	}

	public List<Bullet> getBullets() {
		return bullets;
	}
	
	public int getFireSpeed() {
		return fireSpeed;
	}
	
	public int getReload() {
		return reload;
	}
	
	public boolean reloaded() {
		return (reloadTimer == reload);
	}
	
	public void tickReload() {
		if (reloadTimer < reload) {
			reloadTimer++;
		}
	}
}
