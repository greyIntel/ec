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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.operator.crossover;

import ec.algorithms.ga.Evolve;
import ec.individuals.Chromosome;
import ec.individuals.Individual;
import ec.individuals.fitnesspackage.BasicFitness;
import ec.individuals.populations.Population;
import ec.operator.CrossoverModule;
import ec.util.random.GenerateMask;
import ec.util.random.RandomGenerator;

import java.util.ArrayList;

/**
 * 
 * Each element comes from one parent together with its position.
 * 
 * e.g for TSP, each city (and its position) comes from one of the parents
 * Example: Cycle crossover Step 1: identify cycle p1: 1 2 3 4 5 6 7 8 9 p2: 9 3
 * 7 8 2 6 5 1 4 c1: 1 * * 4 * * * 8 9 Step 2: Fill the remaining cities from
 * the other parent c1: 1 3 7 4 2 6 5 8 9
 * 
 * @author Anthony Awuley
 */
public class CycleCrossover extends CrossoverModule {

	private String crossoverMask;

	public CycleCrossover() {
		super("cx");
	}

	public void setCrossoverMask(String mask) {
		this.crossoverMask = mask;
	}

	public String getCrossOverMask() {
		return this.crossoverMask;
	}

	/**
	 * 
	 * @param p
	 *            - population
	 * @param c1
	 *            - first chromosome
	 * @param c2
	 *            - second chromosome
	 * @param parentsId
	 *            - array of parents id's
	 * @param numberOfChildrenToAdd
	 *            - determine number of offsprings to add to new population
	 * @return
	 * 
	 *         Example: Cycle crossover Step 1: identify cycle p1: 1 2 3 4 5 6 7
	 *         8 9 p2: 9 3 7 8 2 6 5 1 4 c1: 1 * * 4 * * * 8 9 Step 2: Fill the
	 *         remaining cities from the other parent c1: 1 3 7 4 2 6 5 8 9
	 * 
	 */
	@Override
	public ArrayList<Individual> performCrossoverOperation(
			Evolve e,Population p,
			Chromosome c1, Chromosome c2,
			ArrayList<Integer> tournamentIndividuals, int numberOfChildrenToAdd) {

		Individual id1 = new Individual();
		Individual id2 = new Individual();
		// GenerateMask randMsk = new GenerateMask();

		ArrayList<Individual> children = new ArrayList<>();
		ArrayList<Integer> mask = new ArrayList<>();

		crossoverMask = GenerateMask.getMask(e,p.get(0).getChromosome()
				.getGenes().size());
		this.indexes.clear(); // very important
		// randomly generate initial point
		mask = this.getCycleMask(c1, c2, RandomGenerator
				.getMultiThreadedRandNumber(e,0, c1.getGenes().size() - 1)); 
		  // return  index  position  of  1's  in  mask
		// mask = this.getCycleMask(c1,c2,0); //return index position of 1's in  mask

		/*
		 * complete replacement of other bit positions without mask flag of "1"
		 * set -1 for values that must be replaced individuals that have -1
		 * after operation are invalid
		 */

		for (int j = 0; j < c1.getGenes().size(); j++) // take size of any of
														// the chromosomes
		{
			if (!GenerateMask.isExistIndex(mask, j + "")) {
				c1.getGenes().set(
						j,
						p.get(tournamentIndividuals.get(1)).getChromosome()
								.getGenes().get(j)); // swap remaining
														// chromosomes
				c2.getGenes().set(
						j,
						p.get(tournamentIndividuals.get(0)).getChromosome()
								.getGenes().get(j));
			}
		}

		// set individual properties for chldren and add to new population
		id1.setChromosome(c1);
		id1.setFitness(new BasicFitness()); // set fitness object
		id2.setChromosome(c2);
		id2.setFitness(new BasicFitness()); // set fitness object
		// System.out.println("#ADDED "+numberOfChildrenToAdd);
		switch (numberOfChildrenToAdd) {
		case 1: // add one offspring
			if (!chromosomeHasDuplicateGenes(c1.getGenes()))
				children.add(id1);
			break;
		default: // add two offsprings
			if (!chromosomeHasDuplicateGenes(c1.getGenes())
					&& !chromosomeHasDuplicateGenes(c2.getGenes())) {
				children.add(id1);
				children.add(id2);
			}
			break;
		}

		this.setChildrenAdded(children.size()); // set number of children added
		setOffsprings(children);
		return children;

	}

	@Override
	public ArrayList<Individual> performCrossoverOperation(
			Evolve e,Population p,
			Chromosome c1, Chromosome c2,
			ArrayList<Integer> tournamentIndividuals,
			int numberOfChildrenToAdd, ArrayList<Double> ages) {

		Individual id1 = new Individual();
		Individual id2 = new Individual();
		// GenerateMask randMsk = new GenerateMask();

		ArrayList<Individual> children = new ArrayList<>();
		ArrayList<Integer> mask = new ArrayList<>();

		crossoverMask = GenerateMask.getMask(e,p.get(0).getChromosome()
				.getGenes().size());
		this.indexes.clear(); // very important
		// randomly generate initial point
		mask = this.getCycleMask(c1, c2, RandomGenerator
				.getMultiThreadedRandNumber(e,0, c1.getGenes().size() - 1)); // return index  position  of  1's  in mask

		/*
		 * complete replacement of other bit positions without mask flag of "1"
		 * set -1 for values that must be replaced individuals that have -1
		 * after operation are invalid
		 */

		for (int j = 0; j < c1.getGenes().size(); j++) // take size of any of
														// the chromosomes
		{
			if (!GenerateMask.isExistIndex(mask, j + "")) {
				c1.getGenes().set(
						j,
						p.get(tournamentIndividuals.get(1)).getChromosome()
								.getGenes().get(j)); // swap remaining
														// chromosomes
				c2.getGenes().set(
						j,
						p.get(tournamentIndividuals.get(0)).getChromosome()
								.getGenes().get(j));
			}
		}

		id1.setChromosome(c1);

		id1.setFitness(new BasicFitness()); // set fitness object
		id2.setChromosome(c2);
		id2.setFitness(new BasicFitness()); // set fitness object

		// asssign parent with lowest evluation value
		double eval = ages.get(0) < ages.get(1) ? ages.get(0) : ages.get(1);
		id1.setBirthEvaluations(eval);
		id2.setBirthEvaluations(eval);
		double age = ages.get(0) > ages.get(1) ? ages.get(0) : ages.get(1);
		id1.setAge(age); // add age
		id2.setAge(age); // add age

		// System.out.println("#ADDED "+numberOfChildrenToAdd);
		switch (numberOfChildrenToAdd) {
		case 1: // add one offspring
			if (!chromosomeHasDuplicateGenes(c1.getGenes()))
				children.add(id1);

			break;
		default: // add two offsprings
			if (!chromosomeHasDuplicateGenes(c1.getGenes())
					&& !chromosomeHasDuplicateGenes(c2.getGenes())) {
				children.add(id1);
				children.add(id2);
			}
			break;
		}

		this.setChildrenAdded(children.size()); // set number of children added
		setOffsprings(children);
		return children;

	}

}
