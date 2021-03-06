/*******************************************************************************
 * Copyright (c) 2014, 2015 
 * Anthony Awuley - Brock University Computer Science Department
 * All rights reserved. This program and the accompanying materials
 * are made available under the Academic Free License version 3.0
 * which accompanies this distribution, and is available at
 * https://aawuley@bitbucket.org/aawuley/evolvex.git
 *
 * Contributors:
 *     ECJ                     MersenneTwister & MersenneTwisterFast (https://cs.gmu.edu/~eclab/projects/ecj)
 *     voidException      Tabu Search (http://voidException.weebly.com)
 *     Lucia Blondel       Simulated Anealing 
 *     
 *
 *        
 *******************************************************************************/
package ec.algorithms.alps.replacement;

import ec.operator.operations.SelectionOperation;
import ec.individuals.populations.Population;
import ec.algorithms.alps.ALPSReplacement;
import ec.algorithms.alps.system.ALPSLayers;
import ec.algorithms.ga.Evolve;
/**
 * no modifications done. All individuals are shipped to the next higher 
 * layer after evolution is exhausted in one layer
 * 
 *  @author Anthony Awuley
 */
public class Default  extends ALPSReplacement{

	public Default() 
	{
	}
	
	@Override
	public String toString()
	{
		return "Default Replacment";
	}

	@Override
	public Population performAgeLayerMovements(Evolve e, ALPSLayers alpsLayers,
			Population current,SelectionOperation selectionOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
