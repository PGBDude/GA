import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Population implements Serializable {

    private ArrayList<Individual> individuals = new ArrayList();
    public int nrIndividuals;
    public double popFitness;
    static int generation = 0;

    public void calculatePopFitness(){
        double sum = 0;
        calculateIndividualFitness();
        for(int i = 0; i < individuals.size(); i++){
            sum += individuals.get(i).getFitness();
        }
        this.popFitness = sum / individuals.size();
    }

    public void mutatePopulation(){
        for (int i = 0; i < individuals.size(); i++) {
            individuals.get(i).mutate();
        }
    }

    public void calculateIndividualFitness(){
        for (int i = 0; i < individuals.size(); i++) {
            individuals.get(i).calculateFitness();
        }
    }

    public double getPopFitness(){
        return this.popFitness;
    }

    public void debbuging(){
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("Generation : " + Population.generation);
        System.out.println("Population size: " + this.popSize());
        System.out.println("Best specimen: " + selectBestSpecimen().getFitness());
        //System.out.println("Best Specimen Genes: " + selectBestSpecimen().getGenes().toString());
        System.out.println("Worst : " + selectWorstSpecimen().getFitness());
        //System.out.println("Worst Specimen Genes" + selectWorstSpecimen().getGenes().toString());
        calculatePopFitness();
        System.out.println("Population fitness: " + this.popFitness);
        System.out.println("-------------------------------------------------------------------------");
    }

    Population(Population newPop) {
        this.individuals.addAll(newPop.individuals);
    }

    public void addIndividual(Individual individual){
        individuals.add(individual);
        nrIndividuals += 1;
    }

    public Population() {
        nrIndividuals = 0;
    }

    public int RouletteWheelSelect(){
        Individual  ind;
        Collections.sort(individuals);
        Collections.reverse(individuals);
        Random randomGenerator = new Random();
        Double Sum = 0.0;
        for(int i=0;i<this.popSize();i++){
            ind = individuals.get(i);
            Sum+=ind.getFitness();
        }
        double value = randomGenerator.nextDouble()*Sum;

        for(int i=0;i<this.popSize();i++){
            ind = individuals.get(i);
            value-=ind.getFitness();
            if(value<0)  return i;
        }
        return popSize()-1;
    }

    public Individual RouletteWheelSelection(){
        return individuals.get(RouletteWheelSelect());
    }



    public ArrayList<Individual>  Crossover(Individual parent1, Individual parent2, Integer nrGenes){
        ArrayList<Individual> offspring = new ArrayList<>();
        Random randomGenerator = new Random();
        Integer r = randomGenerator.nextInt(nrGenes);
        LinkedList<Double> genesP1 = parent1.getGenes(), genesP2 = parent2.getGenes();
        Individual offspring1 = new Individual(parent1.getGenes());
        Individual offspring2 = new Individual(parent2.getGenes());
        for(int i = 0; i < nrGenes; i++){
            if(i < r) {
               offspring1.setGenes(i, genesP1.get(i));
               offspring2.setGenes(i, genesP2.get(i));
            }
            else {
                offspring1.setGenes(i, genesP2.get(i));
                offspring2.setGenes(i, genesP1.get(i));
            }
        }
        offspring.add(0,offspring1);
        offspring.add(1,offspring2);
        return offspring;
    }

    public Individual selectBestSpecimen(){
        this.calculatePopFitness();
        Individual bestSpecimen = this.individuals.get(0);
        for (int i = 1; i < this.individuals.size(); i++) {
            Individual ind=(Individual) this.individuals.get(i);
            if(ind.getFitness()<bestSpecimen.getFitness())
                bestSpecimen = ind;
        }
        return bestSpecimen;
    }

    public Individual selectWorstSpecimen(){
        this.calculatePopFitness();
        Individual worstSpecimen = (Individual) this.individuals.get(0);
        for (int i = 1; i < this.individuals.size(); i++) {
            Individual ind=(Individual) this.individuals.get(i);
            if(ind.getFitness()>worstSpecimen.getFitness())
                worstSpecimen = ind;
        }
        return worstSpecimen;
    }

    public void clearPopulation(){
        this.individuals.clear();
    }

    public Individual getIndividual(Integer index){
        return this.individuals.get(index);
    }

    public int popSize(){
        return this.individuals.size();
    }

}
