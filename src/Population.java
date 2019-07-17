import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Population implements Serializable {

    private ArrayList<Individual> pop = new ArrayList();
    public int nrIndividuals;
    public double popFitness;

    public void calculatePopFitness(){
        double sum = 0;
        calculateIndividualFitness();
        for(int i = 0; i < pop.size(); i++){
            sum += pop.get(i).getFitness();
        }
        this.popFitness = sum / pop.size();
    }

    public void mutatePopulation(){
        for (int i = 0; i < pop.size(); i++) {
            pop.get(i).mutate();
        }
    }

    public void calculateIndividualFitness(){
        for (int i = 0; i < pop.size(); i++) {
            pop.get(i).calculateFitness();
        }
    }

    public void debbuging(){
        System.out.println("Best specimen: " + selectBestSpecimen().getFitness());
        System.out.println("Worst : " + selectWorstSpecimen().getFitness());
        calculatePopFitness();
        System.out.println("Population fitness: " + this.popFitness);
    }

    Population(Population newPop) {
        this.pop.addAll(newPop.pop);
    }

    public void addIndividual(Individual individual){
        pop.add(individual);
    }

    public Population() {
        nrIndividuals = 0;
    }

    public int RouletteWheelSelect(){
        Individual  ind;
        Random randomGenerator = new Random();
        Double Sum = 0.0;
        for(int i=0;i<pop.size();i++){
            ind = pop.get(i);
            Sum+=ind.getFitness();
        }
        double value = randomGenerator.nextDouble()*Sum;

        for(int i=0;i<pop.size();i++){
            ind = pop.get(i);
            value-=ind.getFitness();
            if(value<0)  return i;
        }
        return pop.size()-1;
    }

    public Individual RouletteWheelSelection(){
        return this.pop.get(RouletteWheelSelect());
    }

    public ArrayList<Individual>  Crossover(Individual parent1, Individual parent2, Integer nrGenes){
        ArrayList<Individual> offspring = new ArrayList<>();
        Individual offspring1 = new Individual();
        Individual offspring2 = new Individual();
        Random randomGenerator = new Random();
        Integer r = randomGenerator.nextInt(nrGenes);
        LinkedList<Double> genesP1 = parent1.getGenes(), genesP2 = parent2.getGenes();

        for(int i=0; i<nrGenes;i++){
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
        Individual bestSpecimen = (Individual) this.pop.get(0);
        for (int i = 1; i < this.pop.size(); i++) {
            Individual ind=(Individual) this.pop.get(i);
            if(ind.getFitness()<bestSpecimen.getFitness())
                bestSpecimen = ind;
        }
        return bestSpecimen;
    }

    public Individual selectWorstSpecimen(){
        Individual worstSpecimen = (Individual) this.pop.get(0);
        for (int i = 1; i < this.pop.size(); i++) {
            Individual ind=(Individual) this.pop.get(i);
            if(ind.getFitness()>worstSpecimen.getFitness())
                worstSpecimen = ind;
        }
        return worstSpecimen;
    }

    public void clearPopulation(){
        this.pop.clear();
    }

    public Individual getIndividual(Integer index){
        return this.pop.get(index);
    }
    public int popSize(){
        return this.pop.size();
    }

}
