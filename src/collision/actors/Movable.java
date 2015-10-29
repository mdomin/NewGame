package collision.actors;

import java.util.List;

import collision.collidables.CollectionObj;
import collision.collidables.Collidable;

public abstract class Movable extends CollectionObj {

	private MoveStates movement;
	private int leftTurnVal;
	private int rightTurnVal;
	
	private boolean markedForDeletion;

	private double speed;
	private double x_vel;
	private double y_vel;
	private double x_trig;
	private double y_trig;
	
	private double maxSpeedF;
	private double maxSpeedB;
	private double slowSpeed;
	private double maxAccel;
	private double maxDecel;
	private double rotationSpeed;
	
	private boolean doesBounce;
	
	public Movable(Collidable collidable) {
		super(collidable);
		defaultSetup();
	}
	
	public Movable(List<Collidable> collidables, int[] distances, int[] angles) {
		super(collidables, distances, angles);
		defaultSetup();
	}
	
	private void defaultSetup() {
		movement = MoveStates.HOLDING;
		leftTurnVal = 0;
		rightTurnVal = 0;
		doesBounce = true;
	}

	
	public void setMaxSpeedF(double maxSpeedF) {
		this.maxSpeedF = maxSpeedF;
	}
	
	public void setMaxSpeedB(double maxSpeedB) {
		this.maxSpeedB = maxSpeedB;
	}
	
	public void setSlowSpeed(double slowSpeed) {
		this.slowSpeed = slowSpeed;
	}
	
	public void setMaxAccel(double maxAccel) {
		this.maxAccel = maxAccel;
	}
	
	public void setMaxDecel(double maxDecel) {
		this.maxDecel = maxDecel;
	}
	
	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	
	public void setBounce(boolean bounceBool) {
		this.doesBounce = bounceBool;
	}
	
	
	
	public boolean doesBounce() {
		return doesBounce;
	}

	public void delete() {
		markedForDeletion = true;
	}
	
	public boolean checkDeleted() {
		return markedForDeletion;
	}
	
	public void move() {
		move(x_vel, y_vel);
	}

	public double getXVel() {
		return x_vel;
	}
	
	public double getYVel() {
		return y_vel;
	}
	
	private void calculateVel() {
		x_vel = speed * x_trig;
		y_vel = speed * y_trig;
	}
	
	private void calculateTrig() {
		x_trig = Math.cos(getAngle());
		y_trig = Math.sin(getAngle());
		calculateVel();
	}
	
	public void rotate(double rotateAngle) {
		super.increaseAngle(Math.toRadians(rotateAngle));
		calculateTrig();
	}
	
	public void setAngle(double setAngle) {
		angle = setAngle;
		calculateTrig();
	}
	
	public void addSpeed(double maxAccel, double maxSpeedF) {
		speed = Math.min(speed + maxAccel, maxSpeedF);
		calculateVel();
	}
	
	public void subSpeed(double maxDecel, double maxSpeedB) {
		speed = Math.max(speed - maxDecel, maxSpeedB);
		calculateVel();
	}
	
	public void setSpeed(double spd) {
		speed = spd;
		calculateVel();
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void checkBorders(int leftEdge, int rightEdge, int topEdge, int bottomEdge) {
		if (left() + x_vel <= leftEdge || right() + x_vel >= rightEdge) {
			setAngle(3 * Math.PI - getAngle());
			calculateTrig();
		}
		else if (bottom() + y_vel >= bottomEdge || top() + y_vel <= topEdge) {
			setAngle(2 * Math.PI - getAngle());
			calculateTrig();
		}
	}
	
	
	
	
	
	
	
	public void setMovement() {
		switch (movement) {
		case HOLDING: {
			if (Math.abs(speed) > slowSpeed) {
				if(speed > 0)
					setSpeed(speed - slowSpeed);
				else
					setSpeed(speed + slowSpeed);
			}
			else
				speed = 0;
			break;
		}
		case ACCELERATING: {
			if (speed < maxSpeedF) {
				addSpeed(maxAccel, maxSpeedF);
			} else {
				setSpeed(maxSpeedF);
			}
			break;
		}
		case DECELERATING: {
			if (speed > maxSpeedB) {
				subSpeed(maxDecel, maxSpeedB);
			} else {
				setSpeed(maxSpeedB);
			}
			break;
		}
		default: {
			break;
		}
		}
		rotate(rotationSpeed * (rightTurnVal + leftTurnVal));
		move(x_vel, y_vel);
	}
		
	public MoveStates getState() {
		return movement;
	}
	
	public void setPos(int x, int y) {
		setPosition(x, y);
	}
	
	public void accelerate() {
		movement = MoveStates.ACCELERATING;
	}
	
	public void decelerate() {
		movement = MoveStates.DECELERATING;
	}
	
	public void turnLeft() {
		leftTurnVal = -1;
	}
	
	public void turnRight() {
		rightTurnVal = 1;
	}
	
	public void stopTurnLeft() {
		leftTurnVal = 0;
	}
	
	public void stopTurnRight() {
		rightTurnVal = 0;
	}
	
	public void stop() {
		movement = MoveStates.HOLDING;
	}

	// DEBUGGING
	public void setMoveState(MoveStates movement) {
		this.movement = movement;
	}

	public void playDeathAnimation() {}
	
	
	/**
	public int getX() {
		return getX();
	}
	
	public int getX(double interpolation) {
		return (int) (trueX() + x_vel * interpolation);
	}
	
	public int getY() {
		return getY();
	}
	
	public int getY(double interpolation) {
		return (int) (trueY() + y_vel * interpolation);
	}
	**/
	
	public void loop() {
		
	}
}


