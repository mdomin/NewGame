package collision.characters;

import collision.collidables.Collidable;

public class EntityOld{

	Collidable hitbox;
	int HP;
	int maxHP;
	double acceleration;
	double maxSpeed;
	MoveStates moveState;
	
	public EntityOld(int maxHP, double maxSpeed, Collidable hitbox) {
		this.hitbox = hitbox;
		this.maxHP = maxHP;
		HP = maxHP;
		this.maxSpeed = maxSpeed;
		acceleration = 0;
	}
	
	public void setHP(int HP) {
		this.HP = HP;
	}
	
	public void addHP(int addpart) {
		HP += addpart;
	}
	
	public void subHP(int subpart) {
		HP -= subpart;
	}
	
	public int getHP() {
		return HP;
	}

	public void move() {
		if(hitbox.getSpeedSquared() > maxSpeed * maxSpeed) {
			hitbox.setSpeed(maxSpeed);
		}
		hitbox.move();
	}
	
	public void setPosition(int x, int y) {
		hitbox.setPosition(x, y);
	}
}
