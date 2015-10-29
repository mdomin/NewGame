package collision.actors;

import java.util.ArrayList;
import java.util.List;

import collision.collidables.Collidable;
import collision.weapons.Weapon;

public class Entity extends Movable {
	
	private int maxHp;
	private double hp;
	private int maxEnergy;
	private double energy;
	
	private List<Weapon> weapons;
	private Weapon currWeapon;
	
	public Entity(Collidable collidable) {
		super(collidable);
		defaultSetup();
	}
	
	public Entity(List<Collidable> collidables, int[] distances, int[] angles) {
		super(collidables, distances, angles);
		defaultSetup();
	}
	
	private void defaultSetup() {
		this.maxHp = 200;
		this.hp = maxHp;
		this.maxEnergy = 100;
		this.energy = maxEnergy;
		this.weapons = new ArrayList<Weapon>();
		this.currWeapon = null;
	}
	
	public void weapons(List<Weapon> weapons) {
		this.weapons = weapons;
		if (!weapons.isEmpty()) {
			currWeapon = weapons.get(0);
		}
		else {
			currWeapon = null;
		}
	}
	
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	
	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}
	
	
	
	public int getHp() {
		return (int)hp;
	}
	
	public void setHp(double newHp) {
		hp = Math.min(newHp, maxHp);
	}
	
	public void setHpNocap(int newHp) {
		hp = newHp;
	}
	
	public void fullHeal() {
		hp = maxHp;
	}
	
	public int getEnergy() {
		return (int) energy;
	}
	
	public void setEnergy(double newEnergy) {
		energy = Math.min(newEnergy, maxEnergy);
	}
	
	public void setEnergyNocap(double newEnergy) {
		energy = newEnergy;
	}
	
	public void fullEnergy() {
		energy = maxEnergy;
	}
	
	public void fireWeapon() {
		if (currWeapon != null) {
			if (currWeapon.reloaded()) {
				currWeapon.getBullets();
			}
		}
	}
	
	@Override
	public void loop() {
		if (currWeapon != null) {
			currWeapon.tickReload();
		}
	}
	
	/**
	 * 	Weapons that fire multiple times after the initial shot
	 * could be implemented by having the loop add objects into the
	 * projectile queue or directly into the list of collidables
	 */
}
