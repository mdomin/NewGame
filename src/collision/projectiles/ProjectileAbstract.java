package collision.projectiles;

import java.util.List;

import collision.actors.Movable;
import collision.collidables.Collidable;

public abstract class ProjectileAbstract extends Movable{
	
	private final int damage;
	private int teamID;

	public ProjectileAbstract(Collidable collidable, int damage) {
		super(collidable);
		this.damage = damage;
	}
	
	public ProjectileAbstract(List<Collidable> collidables, int[] distances, int[] angles, int damage) {
		super(collidables, distances, angles);
		this.damage = damage;
	}
	
	public void teamID(int teamID) {
		this.teamID = teamID;
	}
	
	public int getTeam() {
		return teamID;
	}

	public int getDamage() {
		return damage;
	}
	
}
