import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
    	/**
    	System.out.println("Angle \t\t cos \t sin \t rad");
    	for(int i = -360; i <= 360; i += 30) {
    		System.out.println("Angle " + i + ": \t" +
    				String.format("%.3f\t", Math.cos(Math.toRadians(i))) + 
    				String.format("%.3f\t", Math.sin(Math.toRadians(i))) + "r: " + 
    				String.format("%.4f\t", Math.toRadians(i) / (Math.PI)));
    	}
    	**/

    	int dof = 5;
    	int size = 360 * dof;
    	int iter = 1000000;
    	List<Double> tmp = new ArrayList<Double>();
    	List<Double> tmp2 = new ArrayList<Double>();
    	Random rand = new Random();
    	double[] selections = new double[iter];
    	for(int i = 0; i < iter; i++) {
    		selections[i] = rand.nextDouble() * 360;
    	}
    	
    	long startTime = System.nanoTime();
    	double[] values = new double[size];
    	for(int i = 0; i < size; i++) {
    		values[i] = Math.sin((i * Math.PI)/(dof * 180));
    	}
    	System.out.println("Total Time to setup lookup tables: " + (System.nanoTime() - startTime) / (1000000) + " ms");
    	
    	startTime = System.nanoTime();
    	for(int i = 0; i < iter; i++) {
    		tmp.add(values[(int)(selections[i] * dof + 0.5) % size]);
    	}
    	System.out.println("Lookup tables total Time: " + (System.nanoTime() - startTime) / (1000000) + " ms");
    	
    	startTime = System.nanoTime();
    	for(int i = 0; i < iter; i++) {
    		tmp2.add(Math.sin(selections[i]));
    	}
    	System.out.println("Math.sin total Time: " + (System.nanoTime() - startTime) / (1000000) + " ms");
    	
    	double total = 0;
    	double tmpval;
    	for(int i = 0; i < iter; i++) {
    		tmpval = (tmp.get(i) - tmp2.get(i));
    		total += tmpval * tmpval;
    	}
    	System.out.println("Sum of squared diff: " + total);
	}
}
