/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package individuals.representation.tabu;

import individuals.Gene;
import individuals.Representation;

import java.util.ArrayList;



/**
 *
 * @author anthony
 */
public class TSP  extends Gene{
    
	
	
	public TSP(int identity, String[] allel) {
		super(identity, allel);
		// TODO Auto-generated constructor stub
	}

	public TSP(int identity) {
		super(identity);
		// TODO Auto-generated constructor stub
	}

	public TSP() {
		super();
	}

	public static int[] startSolution(int length)
	  {
		 int [] startSolution = new int[length+1];
		 //begin count from 1, since 0 is used as depot
		 for(int i=0;i<length;i++)
	       startSolution[i] = i; 
		 
		 startSolution[length] = 0;
		 
		 return startSolution;
	  }
	

	 
}
