/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package individuals.populations;

import individuals.Individual;
import individuals.fitnesspackage.PopulationFitness;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import algorithms.alps.system.ALPSLayers;
import util.DeepClone;
import util.deepclone.cloning.Cloner;

/**
 *
 * @author anthony
 */
public class Population implements PopulationInterface, Cloneable, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Individual> population = new ArrayList<>();
	private ArrayList<Individual> bestIndividuals = new ArrayList<>();
    private int popSize;
    ArrayList<Individual> individuals;
    private PopulationFitness f;
    private Individual bestIndividual;
    
    
    /**
     * 
     */
    public Population() 
    {
        individuals = new ArrayList<Individual>(0);
    }

    public Population(int size) 
    {
        individuals = new ArrayList<Individual>(size);
    }
    
    
    public Population(ArrayList<Individual> pop) 
    {
        this.population = pop;
    }
    
    /**
     * 
      public Object clone() throws CloneNotSupportedException
      {
        return (Population) super.clone();
      }
    */
    
    /**
     *  not working
    
    @Override
    public Population clone()
    {
        try 
        { 
        	return  (Population) super.clone(); 
        } 
        catch (CloneNotSupportedException e) 
        { 
        	e.printStackTrace(); 
        }
		return null;
    }
     */
    
    /**
     * 
     */
    public Population clone() 
    {
    	
    	//Cloner cloner=new Cloner();
    	//XX clone = cloner.deepClone(someObjectOfTypeXX);
    	//return (new Cloner()).deepClone(this);
    	
    	return (Population) DeepClone.clone(this);
    }
   
    
    /**
     * calculate age of individuals within the population for 
     * SS replacement strategy in ALPS
     * @param currentEvaluationCount
     * @param popSize
     */
    public void calculateAge(int currentEvaluationCount,int popSize)
    {
    	for(Individual e: this.population)
    		e.setAge(1 + (currentEvaluationCount-e.getBirthEvaluations())/popSize);
    }
    
    /**
     * calculate age of individuals within the population for 
     * SS replacement strategy in ALPS
     * divide by total population size of all layers
     * 
     * @param alpsLayers
     * @param currentEvaluationCount
     * @param popSize
     */
    public void calculateAge(ALPSLayers alpsLayers, int currentEvaluationCount)
    {
    	int totalPop = 0;
    	
    	for(int i=0;i<alpsLayers.layers.size();i++)
    		totalPop += alpsLayers.layers.get(i).getEvolution().getCurrentPopulation().size();
    	
    	//System.out.println("total sizeiiiiii"+ totalPop);
    	for(Individual e: this.population)
    		e.setAge(1 + (currentEvaluationCount-e.getBirthEvaluations())/totalPop);
    	
    }
    
    /**
     * 
     */
    public void sort() 
    {
    }
    /**
     * 
     * @param size
     */
    public void setSize(int size)
    {
       popSize = size;
    }
    /**
     * Look through individual objects and select individual with best fitness
     * @return 
     */
    @Override
    public Individual getBestIndividual() 
    {
        return this.bestIndividual;
    }
    /**
     * 
     */
    @Override
    public void setBestIndividual(Individual i) 
    {
        this.bestIndividual = i;
    }
    /**
     * 
     * @return 
     */
    @Override
    public int size() 
    {
        return population.isEmpty()?
        		this.popSize:population.size();
    }
    /**
     * 
     */
    @Override
    public void addAll(ArrayList<Individual> immigrants) 
    {
        Iterator<Individual> indIt = immigrants.iterator();
        while (indIt.hasNext()) 
            this.add(indIt.next());
    }
    /**
     * 
     */
    @Override
    public void addAll(Population pop, ArrayList<Individual> immigrants) 
    {
        Iterator<Individual> indIt = immigrants.iterator();
        while (indIt.hasNext()) 
            pop.add(indIt.next());
    }
    /**
     * 
     */
    @Override
    public void addAll(PopulationInterface pop) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     */
    @Override
    public ArrayList<Individual> getAll() 
    {
        return population;
    }
    /**
     * 
     */
    @Override
    public boolean contains(Individual individual) 
    {
        return this.population.contains(individual);
    }
    /**
     * 
     * @param i 
     */
    @Override
    public void add(Individual i) 
    {
        this.population.add(i);
    }
    
    /**
     * @param index
     * @param i 
     */
    @Override
    public void add(int index, Individual i) 
    {
        this.population.add(index,i);
    }
    
    /**
     * 
     * @param index
     * @param i
     */
    public void set(int index, Individual i) 
    {
        this.population.set(index, i);
    }
    /**
     * 
     */
    @Override
    public Individual get(int id) 
    {
        return this.population.get(id);
    }
    /**
     * 
     */
    @Override
    public ArrayList<Individual> get(ArrayList<Integer> ids) 
    {
    	for(int id:ids)
    		bestIndividuals.add(this.population.get(id));
    	
        return this.bestIndividuals;
    }
    /**
     * 
     */
    @Override
    public void clear() 
    {
        this.population.clear();
    }

    @Override
    public void remove(Individual ind) 
    {
        this.population.remove(ind);
    }

	@Override
	public void setBestIndividuals(ArrayList<Individual> inds) 
	{
		this.bestIndividuals = inds;
	}

	@Override
	public ArrayList<Individual> getBestIndividuals() 
	{
		return this.bestIndividuals;
	}

	@Override
	public PopulationFitness getFitness() 
	{
		return this.f;
	}

	@Override
	public void setFitness(PopulationFitness f) {
		 this.f = f;
	}

	    

}
