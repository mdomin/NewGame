package collision;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import collision.actors.Entity;
import collision.actors.Movable;
import collision.collidables.CircleObj;
import collision.collidables.CollectionObj;
import collision.collidables.Collidable;
import collision.collidables.PolygonObj;
import mainGame.StateAbstract;

public class CollisionDetector extends StateAbstract{
		
	// DEBUGGING
	/*
	private static final boolean CALCULATEOPTIMALFRAME = false;
    private static final int MAXBUCKET = 70;
    private static final int NUMFRAMETEST = 250;
    */
	
    private int numObjects;
    
    private static boolean DEBUG1 = false;
    private static boolean SHOWVECTOR = false;

    /*
	private static int totalCollisions = 0;
	private static List<Double> averageCollisions;
	private static List<Integer> bucketSizes;
	*/
	//private List<Collidable> collidables;	
	//private List<CollectionObj> collections;
	
    private List<CollisionPair> collisions;
	
	private int bucketSize = 20;
	private BucketCollection buckets;
	private int mapWidth;
	private int mapHeight;
	private Entity player;
	private List<Movable> entities;

	private double interpolation;
	
	private static int CURRENTCOLLECTNUM;
	
	public CollisionDetector(JPanel panel, int mapWidth, int mapHeight) {
		super(panel);
		CURRENTCOLLECTNUM = 0;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		buckets = new BucketCollection(bucketSize, mapWidth, mapHeight);
		
		entities = new ArrayList<Movable>();
		
		//collidables = new ArrayList<Collidable>();
		//collections = new ArrayList<CollectionObj>();
		
		// DEBUGGING
		/*
		averageCollisions = new ArrayList<Double>();
		bucketSizes = new ArrayList<Integer>();
		*/

		addPlayer();
		keySetup(panel);
		
		for(int i = 0; i < 1; i++) {
			
			for(int j = 0; j < 5; j++) {
				//addCircle(1, 5);
				//addEllipse(1, 5, 1, 2);
			}
			//addCircle(5, 10, 50);
			addPolygon(5, 10, 4, 500);
			//addCollection(10, 10, 5, 10, 5, 10);
			//addEllipse(30, 70, 15, 25);
			
			for(int j = 0; j < 10; j++) {
				//addCircle(3, 5);
			}
			
			/*
			for(int j = 0; j < 2; j++) {
				addEllipse(19, 20, 19, 20);
			}
			*/
		}
	}
	
	public void moveEntities() {
		player.setMovement();
		
		for(Movable entity : entities) {
			if (entity.doesBounce())
				entity.checkBorders(0, mapWidth, 0, mapHeight);
			entity.move();
		}
	}
	
	/**
	public void deleteOne() {
		if(!entities.isEmpty())
			entities.remove(0);
	}
	**/
	
	public void addPlayer() {
		List<Collidable> tempList = new ArrayList<Collidable>();
		tempList.add(new CircleObj().radius(20));
		tempList.add(new CircleObj().radius(10));
		tempList.add(new CircleObj().radius(15));
		
		for(Collidable obj : tempList) {
			obj.collectionNum(CURRENTCOLLECTNUM);
		}
		
		int[] tempLengths = {0, 20, 20};
		int[] tempAngles = {0, 0, 180};

		//public Entity(Collidable hitbox, double maxSpeedF, double maxSpeedB, double slowSpeed, double maxAccel, double maxDecel, double rotationSpeed) {
		player = new Entity(tempList, tempLengths, tempAngles);
		player.setMaxHp(200);
		player.setMaxEnergy(100);
		player.setMaxSpeedF(5.0);
		player.setMaxSpeedB(-3.0);
		player.setSlowSpeed(0.02);
		player.setMaxAccel(0.03);
		player.setMaxDecel(0.06);
		player.setRotationSpeed(10);
		player.setX(200);
		player.setY(200);
		entities.add(player);
		CURRENTCOLLECTNUM++;
	}
	
	public void addCircle(int minR, int maxR, int times) {
		Random rand = new Random();
		for(int i = 0; i < times; i++) {
			CircleObj tempCircle = new CircleObj().radius(minR + rand.nextInt(maxR - minR));
			Movable entity = new Entity(tempCircle);
			entity.setX(50 + rand.nextInt(800 - 100));
			entity.setY(50 + rand.nextInt(640 - 100));
			entity.rotate(rand.nextInt(360));
			entity.setSpeed(rand.nextDouble() * 5 + 1);
			entities.add(entity);
		}
	}
	
	public void addPolygon(int minR, int maxR, int numSides, int times) {
		Random rand = new Random();
		int[] lengths = new int[numSides];
		int[] angles = new int[numSides];
		int angleMod = 360 / numSides;
		
		for(int j = 0; j < times; j++) {
			for(int i = 0; i < numSides; i++) {
				lengths[i] = rand.nextInt(maxR - minR) + minR;
				angles[i] = i * angleMod;
			}
			PolygonObj poly = new PolygonObj(lengths, angles);
			Movable entity = new Entity(poly);
			entity.setX(50 + rand.nextInt(800 - 100));
			entity.setY(50 + rand.nextInt(640 - 100));
			entity.rotate(rand.nextInt(360));
			entity.setSpeed(rand.nextDouble() * 5 + 1);
			entities.add(entity);
		}
	}
	
	public void addCollection(int numPolygons, int numCircles, int minR, int maxR, int numSides, int times) {
		Random rand = new Random();
		
		int[] lengths = new int[numSides];
		int[] angles = new int[numSides];
		int angleMod = 360 / numSides;
		
		for(int k = 0; k < times; k++) {
			List<Collidable> temp = new ArrayList<Collidable>();
			for(int i = 0; i < numPolygons; i++) {
				for (int j = 0; j < numSides; j++) {
					lengths[j] = rand.nextInt(maxR - minR) + minR;
					angles[j] = j * angleMod;
				}
				PolygonObj poly = (PolygonObj) new PolygonObj(lengths, angles)
					.collectionNum(CURRENTCOLLECTNUM);
				temp.add(poly);
			}
			
			for(int i = 0; i < numCircles; i++) {
				CircleObj tempCircle = (CircleObj) new CircleObj()
					.radius(minR + rand.nextInt(maxR - minR))
					.collectionNum(CURRENTCOLLECTNUM);
				temp.add(tempCircle);
			}
			
			int totalNum = numCircles + numPolygons;
			int[] objAngles = new int[totalNum];
			int[] objDistances = new int[totalNum];
			int objAngleMod = 360 / totalNum;
			
			for(int i = 0; i < totalNum; i++) {
				objAngles[i] = objAngleMod * i;
				objDistances[i] = 10 * rand.nextInt(maxR - minR) + minR;
			}
			
			Movable entity = new Entity(temp, objDistances, objAngles);
			entity.setX(100 + rand.nextInt(800 - 200));
			entity.setY(100 + rand.nextInt(640 - 200));
			entity.rotate(rand.nextInt(360));
			entity.setSpeed(rand.nextDouble() * 5 + 1);
			entities.add(entity);
			CURRENTCOLLECTNUM++;
		}
	}
	
	/*
	public void addEllipse(int minR1, int maxR1, int minR2, int maxR2) {
		Random rand = new Random();
		EllipseObj tempEllipse = new EllipseObj(50 + rand.nextInt(800 - 100), 50 + rand.nextInt(640 - 100));
		//tempEllipse.setR1(minR1);
		//tempEllipse.setR2(minR1);
		tempEllipse.setR1(minR1 + rand.nextInt(maxR1 - minR1));
		tempEllipse.setR2(minR2 + rand.nextInt(maxR2 - minR2));
		tempEllipse.setAngle(rand.nextInt(360));
		tempEllipse.setSpeed(5 - rand.nextDouble() * 10);
		collidables.add(tempEllipse);
	}
	*/
	
	public void checkDeletion() {
		List<Movable> temp = new ArrayList<Movable>();
		for(Movable entity : entities) {
			if(entity.checkDeleted()) {
				temp.add(entity);
			}
		}
		for(Movable obj : temp) {
			obj.playDeathAnimation();
		}
		entities.removeAll(temp);
	}
	
	public void entityLoops() {
		for (Movable entity : entities) {
			entity.loop();
		}
	}
	
	public BucketCollection getBuckets() {
		return buckets;
	}

	public void generateBuckets() {
		// List of all collisions
		collisions = new ArrayList<CollisionPair>();
		
		/*
		System.out.println("Gamesize: " + mapWidth + ", " + mapHeight);
		System.out.println("bucketSize: " + buckets.width() + ", " + buckets.height());
		*/
		
		buckets.empty();		// Clears the buckets
		
		/*
		for (Collidable obj : collidables) {
			
			//System.out.println("Is not null: " + (obj != null));
			//System.out.println("Is collidable: " + (obj instanceof Collidable));
			
			obj.clearCollided();	// Clears what the object has collided with
			obj.clearChecked();
			
			buckets.attemptPlace(obj, bucketSize);
		}
		 */
		
		for (Movable entity : entities) {
			entity.clearChecked();
			entity.clearCollided();
			entity.bucketVisitor(buckets, bucketSize);
		}
	
		/**
		for (CollectionObj collect : collections) {
			collect.clearCollided();
			collect.calculateCollisions();
			for(Collidable obj : collect.getCollidables()) {
				obj.clearCollided();
				obj.clearChecked();
				buckets.attemptPlace(obj, bucketSize);
			}
		}
		**/
		
		for (Bucket currBucket : buckets.getArray()) {
			collisions.addAll(currBucket.checkCollisions());
		}
		
		for (Movable entity : entities) {
			entity.calculateCollisions();
		}
		
		// With the collision pairs, things should occur.
		for (CollisionPair collision : collisions) {
			collision.act();
		}
	}
	
	public List<CollisionPair> getCollisions() {
		return collisions;
	}

	public List<Movable> getObjects() {
		return entities;
	}

	@Override
	public void update() {
		/*
		if (collidables.size() < 15) {
			addEllipse(1, 10, 1, 10);
			addCircle(1, 10);
		}
		*/
		/*
		for(int i = 1; i <= 15; i++) {
			addCircle();
			addEllipse();
		}
		deleteOne();
		*/
		
		moveEntities();
		entityLoops();
		generateBuckets();
	}
	
	public void updatebucketSize(int bucketSize) {
		this.bucketSize = bucketSize;
		buckets = new BucketCollection(bucketSize, mapWidth, mapHeight);
	}

	@SuppressWarnings("serial")
	@Override
	public void keySetup(JPanel panel) {
		bind(panel, KeyStroke.getKeyStroke("W"), "forward", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.accelerate();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("released W"), "stop forward", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.stop();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("S"), "backwards", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.decelerate();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("released S"), "stop backwards", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.stop();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("A"), "left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.turnLeft();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("released A"), "released left", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.stopTurnLeft();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("D"), "right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.turnRight();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("released D"), "released right", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.stopTurnRight();
			}
		});
		
		
		
		
		bind(panel, KeyStroke.getKeyStroke("SPACE"), "spacebar", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Firing");
				player.fireWeapon();	
			}
		});
		
		
		
		
		
		
		bind(panel, KeyStroke.getKeyStroke("ESCAPE"), "end Game", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				endGame();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("ESCAPE "), "pause", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				togglePause();
			}
		});
		
		bind(panel, KeyStroke.getKeyStroke("ENTER"), "reset position", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				player.setPos(100, 100);
			}
		});
		
		// DEBUGGING ACTIONS
		
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("SLASH"), "toggle showing movement vectors", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SHOWVECTOR = !SHOWVECTOR;
			}
		});
		
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("MINUS"), "smaller bucket", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (bucketSize > 1) {
					//System.out.println("Avg Num Collisions: " + Double.toString(totalCollisions / totalFrames));
					//System.out.println("Avg Time: " + Double.toString(totalElapsedTime / totalFrames));
					//System.out.println("Size: " + bucketSize + "\n");
	
					/*
					totalFrames = 0;
					totalCollisions = 0;
					totalElapsedTime = 0.0;
					*/
					updatebucketSize(--bucketSize);
				}
			}
		});
		
		StateAbstract.bind(panel, KeyStroke.getKeyStroke("EQUALS"), "bigger bucket", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//System.out.println("Avg Num Collisions: " + Double.toString(totalCollisions / totalFrames));
				//System.out.println("Avg Time: " + Double.toString(totalElapsedTime / totalFrames));
				//System.out.println("Size: " + bucketSize + "\n");

				/*
				totalFrames = 0;
				totalCollisions = 0;
				totalElapsedTime = 0.0;
				*/
				updatebucketSize(++bucketSize);
			}
		});
	}
	
	public void drawObj(Graphics2D g2d, CircleObj obj, Movable entity) {
		g2d.fillOval((int) (obj.getX(interpolation, entity.getXVel()) - obj.getRadius()), (int) (obj.getY(interpolation, entity.getYVel()) - obj.getRadius()), (int) obj.getRadius() * 2, (int) obj.getRadius() * 2);
	}
	
	public void drawObj(Graphics2D g2d, PolygonObj obj, Movable entity) {		
		/*
		for(int point : obj.getXpoints())
			System.out.print(" " + point);
		System.out.println();
		for(int point : obj.getYpoints())
			System.out.print(" " + point);
		System.out.println("\n");
		*/
		g2d.fillPolygon(obj.getXpoints(interpolation, entity.getXVel()), obj.getYpoints(interpolation, entity.getYVel()), obj.numPoints());
	}
	
	public void drawObj(Graphics2D g2d, CollectionObj collect, Movable entity) {
		if(collect.getCollided().isEmpty()) {
			g2d.setColor(Color.black);
		}
		else {
			g2d.setColor(Color.red);
		}
		int j = collect.size() - 1;
		int x1, y1, x2, y2;
		List<Collidable> tempList = collect.getCollidables();
		Collidable tempObj1, tempObj2;
		for(int i = 0; i < tempList.size(); i++) {
			tempObj1 = tempList.get(i);
			tempObj2 = tempList.get(j);
			x1 = tempObj1.getX(interpolation, entity.getXVel());
			y1 = tempObj1.getY(interpolation, entity.getYVel());
			x2 = tempObj2.getX(interpolation, entity.getXVel());
			y2 = tempObj2.getY(interpolation, entity.getYVel());
			
			g2d.drawLine(x1, y1, x2, y2);
			j = i;
		}
		for(Collidable obj : tempList) {
			if (obj.getCollided().isEmpty()) {
				g2d.setColor(Color.black);
			}
			else {
				g2d.setColor(Color.red);
			}
			obj.drawVisitor(this, g2d, entity);
		}
	}

	@Override
	public void doGraphics(Graphics2D g2d, double interpolation) {
        float h, s, b;
        this.interpolation = interpolation;
        
       	BucketCollection buckets = getBuckets();
       	
       	// DEBUGGING
       	int i = 0;
       	int j = 0;
       	
       	for(Bucket temp : buckets.getArray()) {
       		
   			/*
   			h = (float) (0.45 - Math.min(1, temp.numElements() * 0.03));
   			s = (float) 0.7;
   			b = (float) 0.7;
			*/
   			
   			h = (float) 0.5;
   			s = (float) Math.min((temp.numElements() * 0.15), 1);;
   			b = (float) (0.35 + Math.min((temp.numElements() * 	0.05), 0.5));
   			
   			g2d.setColor(Color.getHSBColor(h, s, b));
            g2d.fillRect(i * bucketSize, j * bucketSize, bucketSize, bucketSize);

   			g2d.setColor(Color.gray);
            g2d.drawRect(i * bucketSize, j * bucketSize, bucketSize, bucketSize);
            
            i++; 
            if (i >= buckets.getRightSize()) {
            	i = 0; j++;
            }
       	}
       	
		for(Movable entity : getObjects()) {
			if(entity.getCollided().isEmpty())
		       	g2d.setColor(Color.black);
			else
		       	g2d.setColor(Color.red);
			
			entity.drawVisitor(this, g2d, entity);
			
			if (DEBUG1) {
				g2d.setColor(Color.white);
				g2d.drawString(String.format("%.1f, %.1f, %.1f, %b", entity.getXVel(), entity.getYVel(), entity.getAngle(), entity.doesBounce()), entity.getX(), entity.getY());
			}
			
			if (SHOWVECTOR) {
				if (entity.getSpeed() > 0)
					g2d.setColor(Color.green);
				else
					g2d.setColor(Color.blue);				
				if (entity.getCollectionNum() != 0) {
					int x_dist = (int) (entity.getX(interpolation, entity.getXVel()) + 0.5 * entity.getMaxLength() * entity.getSpeed() * (Math.cos(entity.getAngle())));
					int y_dist = (int) (entity.getY(interpolation, entity.getYVel()) + 0.5 * entity.getMaxLength() * entity.getSpeed() * (Math.sin(entity.getAngle())));
					g2d.drawLine(entity.getX(interpolation, entity.getXVel()), entity.getY(interpolation, entity.getYVel()), x_dist, y_dist);
				}
			}

			//g2d.setColor(Color.gray);
			//g2d.drawString(Double.toString(Math.toDegrees(obj.getAngle())), (int) (obj.getX() - obj.getRadius() / 2), obj.getY());
		}
		
		/**
		for(CollectionObj collect : getCollections()) {
			collect.drawVisitor(this, g2d);
			
			if (SHOWVECTOR) {
				if (collect.getSpeed() > 0)
					g2d.setColor(Color.green);
				else
					g2d.setColor(Color.blue);
				int x_dist = (int) (collect.getX() + collect.getMaxLength() * collect.getSpeed() * (Math.cos(collect.getAngleRad())));
				int y_dist = (int) (collect.getY() + collect.getMaxLength() * collect.getSpeed() * (Math.sin(collect.getAngleRad())));
				g2d.drawLine(collect.getX(), collect.getY(), x_dist, y_dist);
			}
		}
		**/

		
		g2d.setColor(Color.blue);
		g2d.drawRect(0, 0, mapWidth, mapHeight);
		
		numObjects = 0;
		for(Movable obj : getObjects()) numObjects += obj.getNumObj();
		
		g2d.setColor(Color.white);
		g2d.drawString("Bucket Size: " + bucketSize, 10, 80);
		g2d.drawString("Number of Objects: " + Integer.toString(numObjects), 10, 95);
		g2d.drawString("Number of Collisions: " + Integer.toString(getCollisions().size()), 10, 110);
	}
}


/*

if( CALCULATEOPTIMALFRAME ) {
			// DEBUG			
			if (totalFrames >= NUMFRAMETEST) {
				if (bucketSize != MAXBUCKET) {
					averageCollisions.add((double) (totalCollisions / totalFrames));
					averageTime.add((double) (totalElapsedTime / totalFrames));
					bucketSizes.add(bucketSize);
					
					updatebucketSize(++bucketSize);
					
					totalFrames = 0;
					totalElapsedTime = 0;
					totalCollisions = 0;
				}
				
				else {
					for(int k = 0; k < averageTime.size(); k++) {
						System.out.println(averageTime.get(k) + ", " + bucketSizes.get(k));
					}
					System.out.println("\n\n");
					
					setPaused();
					int minIndex = averageTime.indexOf(Collections.min(averageTime));
					System.out.println("Best Time: " + averageTime.get(minIndex));
					System.out.println("Collisions: " + averageCollisions.get(minIndex));
					System.out.println("Bucket Size: " + bucketSizes.get(minIndex));
				}
			}
		}
		
*/
