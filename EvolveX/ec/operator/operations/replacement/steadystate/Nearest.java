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
package operator.operations.replacement.steadystate;

import individuals.Individual;
import individuals.populations.Population;
import operator.operations.SelectionOperation;
import operator.operations.replacement.AbstractSSReplacement;
import operator.operations.selection.TournamentSelection;
import util.random.MersenneTwisterFast;
import util.random.RandomGenerator;
import algorithms.alps.system.ALPSLayers;
import algorithms.ga.Evolve;

public class Nearest extends AbstractSSReplacement{

	private int individualID;
	
	public Nearest() 
	{
	}
	
	public String toString()
	{
		return "Nearest Neighbour Replacment";
	}

    /*
	@Override
	public Population performAgeLayerMovements(ALPSLayers alpsLayers,
			Population current) 
	{
		
		Population higherPop = null;
		Population deleteList = new Population();
		
		
		higherPop = (Population) alpsLayers.layers.get(alpsLayers.index+1).
					getEvolution().getCurrentPopulation().clone();
	
		
		for(int i=0;i<current.size();i++)
		{
			if(current.get(i).getAge() >= alpsLayers.layers.get(alpsLayers.index).getMaxAge() )
			{ 
				//fill higher layer with individuals that fall withing its age limit
				if(higherPop.size() < alpsLayers.layers.get(alpsLayers.index+1).getParameters().getPopulationSize())
				{
					alpsLayers.layers.get(alpsLayers.index+1).getEvolution().
					getCurrentPopulation().add(current.get(i));
				}
				else if(higherPop.size()>0) //once higher layer is filled, do selective replacement based on new individuals that have higher age than in the individual in the  higher layer
				{
					@SuppressWarnings("unused")
					RandomGenerator randGen = new RandomGenerator(); 
			        MersenneTwisterFast mtf = new MersenneTwisterFast();
			        mtf.setSeed(alpsLayers.layers.get(alpsLayers.index).getParameters().getSeed()); //set seed
			        
			        this.individualID = nearestPopulationIndividual( //select worst individual in population
									               higherPop,
									               current.get(i).getAge());
					
					alpsLayers.layers.get(alpsLayers.index+1).getEvolution().getCurrentPopulation().
					   set(this.individualID,current.get(i));
					deleteList.add(current.get(i));
				}
			}
		}
		//remove all individuals older than current layer
		for(int id=0;id<deleteList.size();id++)
		{
			//current.remove(deleteList.get(id));
		}
		
		System.out.println(deleteList.size()+ " -- Current!! "+current.size()+
				" Next "+alpsLayers.layers.get(alpsLayers.index+1).getEvolution().
				getCurrentPopulation().size()); //System.exit(0);
		
		return current;
	}
	
	*/
	
	
	
	
	   public Population ssReplacements(Evolve e, ALPSLayers alpsLayers, Population currentPop, Population replacement) {
			
			//Population currentPop = null;
			//Population deleteList = new Population();
			SelectionOperation selectionOperation = new TournamentSelection();
			
			//Do not clone
			//currentPop = (Population) alpsLayers.layers.get(alpsLayers.index).
			//		getEvolution().getCurrentPopulation();
			
		
			for(Individual ind: replacement.getAll()) //iterate through individuals to be replaced
			{
			   selectionOperation.performTournamentSelection(e,
					      alpsLayers.layers.get(alpsLayers.index).getParameters().getPopulationSize());
			   
				 /*
				  selectionOperation.performTournamentSelection(
						  currentPop.size(),
					      alpsLayers.layers.get(alpsLayers.index).getParameters().getTournamentSize());
			     */  
			    /**
			     * first half of elements in array are individuals from current layer
			     * keep padding list if the number of individuals in the current layer isn't
			     * up to the required population size. this is true from layer 1
			     */
			   if(alpsLayers.layers.get(alpsLayers.index).getIsBottomLayer() &&  (alpsLayers.layers.get(alpsLayers.index).getEvolution().getCurrentPopulation().size() //NOT WORKING ALWAYS EQUAL
						< (alpsLayers.layers.get(alpsLayers.index).getParameters().getPopulationSize())) )
				{   
					currentPop.add(0,ind); //System.out.println("Population size()"+currentPop.size());
				}
			   else if( !alpsLayers.layers.get(alpsLayers.index).getIsBottomLayer() &&  (alpsLayers.layers.get(alpsLayers.index).getEvolution().getCurrentPopulation().size() 
						< (alpsLayers.layers.get(alpsLayers.index).getParameters().getPopulationSize())) 
						//&& currentPop.size()<(2*alpsLayers.layers.get(alpsLayers.index).getParameters().getPopulationSize()) 
						)
				{  
					currentPop.add(0,ind); //System.out.println("Population size()"+currentPop.size());
				}
				else
				{  
			        //select nearest individual in population
					this.individualID = nearestPopulationIndividual(currentPop,ind.getFitness().getDouble());
				
					//System.out.println("Replace "+currentPop.get(this.individualID).getFitness().getDouble() +" with: "+ind.getFitness().getDouble());
					 currentPop.set(this.individualID, ind); 
				}
			  
			}
			
			//System.out.println("--"+currentPop.size()+" ---"+alpsLayers.layers.get(alpsLayers.index).getEvolution().getCurrentPopulation().size());
			/*
			System.out.println(deleteList.size()+ " -- Current!! "+currentPop.size()+
					" Next "+alpsLayers.layers.get(alpsLayers.index).getEvolution().
					getCurrentPopulation().size()); //System.exit(0);
			*/
			return currentPop;
		}


}