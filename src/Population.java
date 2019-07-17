import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population implements Serializable {

    private ArrayList<Individual> pop = new ArrayList();
    public int nrIndividuals;

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

    public Individual tournamentSelection(int tournamentSize){
        Individual winner;
        ArrayList<Individual> winnersCircle = new ArrayList<>();
        Random randomGenerator = new Random();
        if(this.pop.size()<tournamentSize){
            System.out.printf("The population size is too small for tournament selection.");
        }

        for (int j = 0; j < tournamentSize; j++){
            Integer rng = randomGenerator.nextInt(this.pop.size());
            int vectorIndex = rng % pop.size();
            winnersCircle.add(this.pop.get(vectorIndex));
        }
        winnersCircle.sort(Individual::compareTo);
        winner = Collections.min(winnersCircle,null);
        return winner;
    }

    public int RankSelect(){
        Individual ind;
        ArrayList<Double> ranks = new ArrayList<>();
        Random randomGenerator = new Random();
        Integer psize = this.pop.size();

        Integer rsum = psize*(psize+1)/2;

        for(int i=0;i<psize;i++){
            ranks.add(i,1.0*(psize-1-i)/rsum);
        }
        double value = randomGenerator.nextDouble()*rsum;

        for(int i=0;i<psize;i++){
            value-=ranks.get(i);
            if(value<0)  return i;
        }
        return pop.size()-1;
    }

    public Individual RankSelection(int index) {
        ArrayList<Individual> population = new ArrayList<>();

        Integer psize = this.pop.size();
        for(int i=0;i<psize;i++)
            population.add(this.pop.get(i));

        population.sort(Individual::compareTo);
        return population.get(index);
    }

    public Individual selectRandomIndividual(){
        Random rnd = new Random();
        Individual ind = (Individual) this.pop.get(rnd.nextInt(this.pop.size()));
        return ind;
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
