import java.util.*;

public class Population{
	
	//Class attributes
	private double omega;
	private double costMin;
	private ArrayList<Individual> population; //Er det nødvendigt????
	private Individual bestIndividual;

	public Population(double omega){ //Constructor
		this.population = new ArrayList<>();
		this.omega = omega;
		
	
	}
	
	public void add(Individual i){ //Add individual to the population and update costMin with new lower cost if it is in fact lower
		
		population.add(i);

		if(i.cost() < costMin){ // Changes costmin if cost is lower
			costMin = i.cost();

			bestIndividual = i;
		}

		

	}
	
	public boolean contains(Individual i){
		return population.contains(i);
	}
	
	public void remove(Individual i){
		int index;
		if(population.contains(i)){
			index = population.indexOf(i);
			population.remove(index);
		}
	}
	
	public int size(){
		
		return population.size();
	}
	
	public void epidemic(){

		
		ArrayList<Individual> epidemicBest = new ArrayList<>();
		
		for(int j = 0; j < 5; j++){
			int indexBestFit = 0;
			for(Individual i: population){

				if(fitness(i) > fitness(population.get(indexBestFit))){
				indexBestFit = population.indexOf(i);
				}
			}
			epidemicBest.add(population.get(indexBestFit));
			population.remove(indexBestFit);
		}
		for(int j = 0; j < 5; j++){
			int indexWorstFit = 0;
			for(Individual i: population){
				if(fitness(i) < fitness(population.get(indexWorstFit))){
					indexWorstFit = population.indexOf(i);
					
				}
			}
			population.remove(indexWorstFit);
		}

		for(Individual i: population){
			int index;
			double tap = fitness(i);
			if(RandomUtils.getRandomEvent(tap*tap)){
				
				index = population.indexOf(i);
				epidemicBest.add(population.get(index));
			}
		}

		population.clear();
		for(int q = 0; q < epidemicBest.size(); q++){
			population.add(epidemicBest.get(q));
		}
		epidemicBest.clear();

	}
	
	public double fitness(Individual i){
		double fit = ((omega+((costMin/i.cost())*(costMin/i.cost())))/1+(2*omega));
		return fit;
	}
	
	public City[] bestPath(){

	return bestIndividual.path();	
	}
	
}