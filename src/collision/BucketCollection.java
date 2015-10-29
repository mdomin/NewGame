package collision;

import java.util.ArrayList;
import java.util.List;

import collision.collidables.CollectionObj;
import collision.collidables.Collidable;

public class BucketCollection {
	private List<Bucket> buckets;
	private int rightBucketNum;
	private int bottomBucketNum;
	
	public BucketCollection(int bucketSize, int gameWidth, int gameHeight) {
		rightBucketNum = (int) Math.ceil((double)gameWidth / bucketSize);
		bottomBucketNum = (int) Math.ceil((double)gameHeight / bucketSize);
		int length = rightBucketNum * bottomBucketNum;
		buckets = new ArrayList<Bucket>();
		
		for(int i = 0; i < length; i++) {
			buckets.add(new Bucket());
		}
	}
	
	public void empty() {
		for(int i = 0; i < buckets.size(); i++) {
			buckets.get(i).clear();
		}
	}
	
	public void attemptPlace(CollectionObj collect, int bucketSize) {
		for(Collidable obj : collect.getCollidables()) {
			attemptPlace(obj, bucketSize);
		}
	}
	
	public void attemptPlace(Collidable obj, int bucketSize) {
		int leftVal = Math.max((obj.left() / bucketSize), 0);
		int rightVal = Math.min((obj.right() / bucketSize), (rightBucketNum - 1));
		int topVal = Math.max((obj.top() / bucketSize), 0);
		int bottomVal = Math.min((obj.bottom() / bucketSize), (bottomBucketNum - 1));
		
		for(int i = leftVal; i <= rightVal; i++) {
			for(int j = topVal; j <= bottomVal; j++) {
				try {
					buckets.get(i + (rightBucketNum * j)).attemptAdd(obj);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("x_val: " + i   + ", y_val: "  + j);
					System.out.println("\tWidth: " + rightBucketNum + ", Height: " + bottomBucketNum);
					System.out.println("\tRightBucket: " + (rightBucketNum - 1));
					System.out.println("\tBottomBucket: " + (bottomBucketNum - 1));
				}
			}
		}
	}
	
	/*	DECOMISSIONED
	public void attemptPlace(int x, int y, int bucketSize, Collidable obj) {
		if (x >= 0 && y >= 0 && x < rightBucketNum * bucketSize && y < bottomBucketNum * bucketSize) {
			int x_val = (int)(x / bucketSize);
			int y_val = (int)(y / bucketSize);
			try {
				buckets[x_val][y_val].attemptAdd(obj);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("x_val: " + x_val   + ", y_val: "  + y_val);
				System.out.println("\tWidth: " + width() + ", Height: " + height());
			}
		}
	}
	*/
	
	public int length() {
			return buckets.size();
	}
	
	public int getRightSize() {
		return rightBucketNum;
	}
	
	public List<Bucket> getArray() {
		return buckets;
	}
	
	@Deprecated
	public Bucket getBucket(int x, int y) {
		return buckets.get(x + (y * rightBucketNum));
	}
}
