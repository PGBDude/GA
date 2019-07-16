import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population implements Serializable {

    private ArrayList<Individual> pop = new ArrayList();
    public Integer nrIndividuals;
    public Integer nrChromosomes;
    public Integer nrGenes;

    private Integer testFunction;

    private Chromosome dominationMap = new Chromosome();

    public Population(int nrIndividuals, int nrChromosomes, int nrGenes, int testFunction) {
        for (int i = 0; i < nrIndividuals; i++) {
            Individual ind = new Individual(nrChromosomes, nrGenes, testFunction);
            ind.setPhenotype(nrGenes);
            this.pop.add(ind);
        }
        this.nrIndividuals=nrIndividuals;
        this.nrChromosomes = nrChromosomes;
        this.nrGenes = nrGenes;
        this.testFunction=testFunction;

    }

    Population(Population newPop) {
        this.pop.addAll(newPop.pop);
    }

    public void setNrChromosomes(Integer nr){
        this.nrChromosomes=nr;
    }

    public void setNrGenes(Integer nr){
        this.nrGenes=nr;
    }

    public void setTestFunction(Integer t){
        this.testFunction=t;
    }

    public void calculatePhenotype(int nrChromosomes, int nrGenes){
        Individual ind;
        double gen1, gen2, dom;
        for(int i=0;i<pop.size();i++){
            ind = pop.get(i);
            ind.setPhenotype(nrGenes);
            switch(nrChromosomes){
                case 1: {
                    for (int k = 0; k < nrGenes; k++) {
                        gen1 = ind.getChromosome(0).chromosomeGenes.get(k);
                        ind.setPhenotypeValue(k, gen1);
                    }
                    break;
                }
                case 2: {
                    for(int k=0;k<nrGenes;k++){
                        gen1=ind.getChromosome(0).chromosomeGenes.get(k);
                        gen2=ind.getChromosome(1).chromosomeGenes.get(k);
                        dom=dominationMap.chromosomeGenes.get(k);
                        if(dom<0.50) ind.setPhenotypeValue(k,gen1);
                        else ind.setPhenotypeValue(k,gen2);
                    }
                }
            }
        }
    }

    public void calculateFitness(int ploidy, int tf){
        Individual ind;

        for(int i=0;i<pop.size();i++){
            ind=pop.get(i);
            ind.setFitness();
        }
    }


    public void calculateFitnessScore(int nrGenes, int tf){
        Individual ind;
        double fit;
        double mean = 0;
        double deviation = 0;

        for(int i=0;i<pop.size();i++){
            ind=pop.get(i);
            //ind.setFitness(tf);
            mean+=ind.getFitness();
        }

        mean = mean / pop.size();

        //System.out.println("Sum Median:\t"+mean);
        for(int i=0;i<pop.size();i++){
            ind=pop.get(i);
            deviation += Math.pow((ind.getFitness()-mean),2);
        }

        deviation = Math.sqrt (deviation / pop.size());
        //System.out.println("Deviation:\t"+ deviation);
        for(int i=0;i<pop.size();i++) {
            ind = pop.get(i);
            ind.setFitnessScore((ind.getFitness() - mean) / deviation);
        }

        System.out.print(mean + ";"+ deviation+ "\n");
    }

    public void setDominationMap (Integer nrGenes){
        this.dominationMap = new Chromosome(nrGenes);
    }

    public void setDominationMap(Chromosome dom){
        this.dominationMap = dom;
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

    public ArrayList<Individual> Recombination(Individual parent1, Individual parent2, Integer nrGenes, Integer testFunction){
        ArrayList<Individual> offspring = new ArrayList<>();
        Individual offspring1 = new Individual(nrChromosomes);
        Individual offspring2 = new Individual(nrChromosomes);
        //System.out.println(offspring1.toString());

        Random randomGenerator = new Random();
        Chromosome gam11 = new Chromosome(nrGenes), gam12= new Chromosome(nrGenes), gam21= new Chromosome(nrGenes), gam22= new Chromosome(nrGenes);
        Chromosome P11, P12, P21, P22;// = new Chromosome(nrGenes),P12= new Chromosome(nrGenes), P21= new Chromosome(nrGenes), P22= new Chromosome(nrGenes);

        P11 = parent1.getChromosome(0);
        P12 = parent1.getChromosome(1);
        P21 = parent2.getChromosome(0);
        P22 = parent2.getChromosome(1);


        Double u = randomGenerator.nextDouble();
        for(int i=0;i<nrGenes;i++){
            gam11.chromosomeGenes.set(i,P11.chromosomeGenes.get(i)*u+P12.chromosomeGenes.get(i)*(1-u));
            gam12.chromosomeGenes.set(i,P12.chromosomeGenes.get(i)*u+P11.chromosomeGenes.get(i)*(1-u));
            gam21.chromosomeGenes.set(i,P21.chromosomeGenes.get(i)*u+P22.chromosomeGenes.get(i)*(1-u));
            gam22.chromosomeGenes.set(i,P22.chromosomeGenes.get(i)*u+P21.chromosomeGenes.get(i)*(1-u));
        }
        //System.out.println(gam11.toString());
        offspring1.setChromosome(0,gam11);
        offspring1.setChromosome(1,gam22);
        offspring2.setChromosome(0,gam12);
        offspring2.setChromosome(1,gam21);
        offspring1.setAge(0);
        offspring2.setAge(0);
        offspring.add(0,offspring1);
        offspring.add(1,offspring2);

        return offspring;
    }

    public ArrayList<Individual>  Crossover1X(Individual parent1, Individual parent2, Integer nrGenes, Integer testFunction){
        ArrayList<Individual> offspring = new ArrayList<>();
        Individual offspring1 = new Individual(nrChromosomes);
        Individual offspring2 = new Individual(nrChromosomes);
        Random randomGenerator = new Random();
        Integer rng = randomGenerator.nextInt(nrGenes);
        Chromosome P1 = parent1.getChromosome(0), P2 = parent2.getChromosome(0);

        Chromosome off1 = new Chromosome(nrGenes), off2 = new Chromosome(nrGenes);

        for(int i=0; i<nrGenes;i++){
            if(i<rng) {
                off1.chromosomeGenes.set(i, P1.chromosomeGenes.get(i));
                off2.chromosomeGenes.set(i, P2.chromosomeGenes.get(i));
            }
            else {
                off1.chromosomeGenes.set(i, P2.chromosomeGenes.get(i));
                off2.chromosomeGenes.set(i, P1.chromosomeGenes.get(i));
            }
        }

        offspring1.setChromosome(0,off1);
        offspring1.setAge(0);
        offspring2.setChromosome(0,off2);
        offspring2.setAge(0);
        offspring.add(0,offspring1);
        offspring.add(1,offspring2);
        return offspring;
    }

    public ArrayList<Individual> Crossover2X1(Individual parent1, Individual parent2, Integer nrGenes, Integer testFunction){
        ArrayList<Individual> offspring = new ArrayList<>();
        Individual offspring1 = new Individual(nrChromosomes);
        Individual offspring2 = new Individual(nrChromosomes);
        Random randomGenerator = new Random();
        //Integer rng = randomGenerator.nextInt(nrGenes);
        Chromosome gam11 = new Chromosome(nrGenes), gam12= new Chromosome(nrGenes), gam21= new Chromosome(nrGenes), gam22= new Chromosome(nrGenes);
        Chromosome P11 = new Chromosome(nrGenes),P12= new Chromosome(nrGenes), P21= new Chromosome(nrGenes), P22= new Chromosome(nrGenes);

        Integer rng = randomGenerator.nextInt(nrGenes);
        P11 = parent1.getChromosome(0);
        P12 = parent1.getChromosome(1);
        P21 = parent2.getChromosome(0);
        P22 = parent2.getChromosome(1);

        for(int i=0; i<nrGenes;i++){
            if(i<rng){
                gam11.chromosomeGenes.set(i,P11.chromosomeGenes.get(i));
                gam12.chromosomeGenes.set(i,P12.chromosomeGenes.get(i));
                gam21.chromosomeGenes.set(i,P21.chromosomeGenes.get(i));
                gam22.chromosomeGenes.set(i,P22.chromosomeGenes.get(i));
            }
            else {
                gam11.chromosomeGenes.set(i,P12.chromosomeGenes.get(i));
                gam12.chromosomeGenes.set(i,P11.chromosomeGenes.get(i));
                gam21.chromosomeGenes.set(i,P22.chromosomeGenes.get(i));
                gam22.chromosomeGenes.set(i,P21.chromosomeGenes.get(i));

            }
        }
        offspring1.setChromosome(0,gam11);
        offspring1.setChromosome(1,gam22);
        offspring2.setChromosome(0,gam12);
        offspring2.setChromosome(1,gam21);
        offspring1.setAge(0);
        offspring2.setAge(0);
        offspring.add(0,offspring1);
        offspring.add(1,offspring2);

        return offspring;
    }

    public ArrayList<Individual> Crossover2X2(Individual parent1, Individual parent2, Integer nrGenes, Integer testFunction){
        ArrayList<Individual> offspring = new ArrayList<>();
        Individual offspring1 = new Individual(nrChromosomes);
        Individual offspring2 = new Individual(nrChromosomes);
        Random randomGenerator = new Random();
        //Integer rng = randomGenerator.nextInt(nrGenes);
        Chromosome gam11 = new Chromosome(nrGenes), gam12= new Chromosome(nrGenes), gam21= new Chromosome(nrGenes), gam22= new Chromosome(nrGenes);
        Chromosome P11 ,P12, P21, P22;

        Integer rng1 = randomGenerator.nextInt(nrGenes);
        Integer rng2 = randomGenerator.nextInt(nrGenes);

        if(rng1>rng2) { Integer tmp = rng1; rng1 = rng2; rng2 = tmp; }

        P11 = parent1.getChromosome(0);
        P12 = parent1.getChromosome(1);
        P21 = parent2.getChromosome(0);
        P22 = parent2.getChromosome(1);

        for(int i=0; i<nrGenes;i++){
            if(i<rng1 || i>rng2){
                gam11.chromosomeGenes.set(i,P11.chromosomeGenes.get(i));
                gam12.chromosomeGenes.set(i,P12.chromosomeGenes.get(i));
                gam21.chromosomeGenes.set(i,P21.chromosomeGenes.get(i));
                gam22.chromosomeGenes.set(i,P22.chromosomeGenes.get(i));
            }
            else {
                gam11.chromosomeGenes.set(i,P12.chromosomeGenes.get(i));
                gam12.chromosomeGenes.set(i,P11.chromosomeGenes.get(i));
                gam21.chromosomeGenes.set(i,P22.chromosomeGenes.get(i));
                gam22.chromosomeGenes.set(i,P21.chromosomeGenes.get(i));

            }
        }
        offspring1.setChromosome(0,gam11);
        offspring1.setChromosome(1,gam22);
        offspring2.setChromosome(0,gam12);
        offspring2.setChromosome(1,gam21);
        offspring1.setAge(0);
        offspring2.setAge(0);
        offspring.add(0,offspring1);
        offspring.add(1,offspring2);

        return offspring;
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


    public Chromosome getDominationMap(){
        return dominationMap;
    }

    @Override
    public String toString() {
        String solution = "";
        for (int i = 0; i < this.nrIndividuals; i++) {
            Individual ind = this.pop.get(i);
            String str = "Individual : " + i + "\n"+ ind.toString() + "\n";

            solution += str;
        }
        return solution;
    }

    public void addIndividual(Individual ind)
    {
        int age = ind.getAge();
        age++;
        ind.setAge(age);
        this.pop.add(ind);
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
