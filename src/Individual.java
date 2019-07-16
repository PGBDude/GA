import java.util.ArrayList;
import java.util.Random;

public class Individual implements Comparable<Individual> {
    private ArrayList<Chromosome> chrList = new ArrayList<Chromosome>();
    private double fitness;
    private Integer numberOfChromosomes;
    private Integer testFunction;
    private Integer nrGenes;
    private double fitnessScore;
    private Integer age;

    Individual(Individual copyInd) {
        this.fitness = copyInd.fitness;
        this.numberOfChromosomes = copyInd.numberOfChromosomes;
        this.nrGenes = copyInd.nrGenes;
        this.chrList.addAll(copyInd.chrList);
        this.testFunction = copyInd.testFunction;
        this.fitnessScore = copyInd.fitnessScore;
        this.age = copyInd.age;
    }

    public Individual(Integer numberOfChromosomes) {
        this.numberOfChromosomes=numberOfChromosomes;
    }

    public Individual(){

    }
    private Chromosome phenotype = new Chromosome();

    public Individual(Integer numberOfChromosomes, Integer nrGenes, Integer testFunction){
        for(int i=0;i<numberOfChromosomes;i++)
        {
            this.chrList.add(new Chromosome(nrGenes, testFunction));
        }
        this.numberOfChromosomes=numberOfChromosomes;
        this.nrGenes=nrGenes;
        this.age = 0;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness() {
        SchwefelFunction f = new SchwefelFunction(this.phenotype);
        this.fitness = f.Schwefel();
    }

    public void setFitnessValue(double k){
        this.fitness = k;
    }

    public void setFitnessScore(double k){
        this.fitnessScore = k;
    }

    public double getFitnessScore() {
        return fitnessScore;
    }

    public Chromosome getChromosome(int index){
        return this.chrList.get(index);
    }

    public void setChromosome(int index, Chromosome ch) { this.chrList.add(index,ch); }

    public void setPhenotype(Integer nrGenes){
        this.phenotype = new Chromosome(nrGenes,0);
    }

    public void setPhenotypeValue(int i, double k) {
        this.phenotype.chromosomeGenes.set(i,k);
    }

    public void setAge(int a) { this.age = a; }

    public int getAge() { return age; }

    public Chromosome getPhenotype(){
        return this.phenotype;
    }

    public static String round(double value, int places) {
        return String.format("%." + places + "f", value);
    }

    @Override
    public String toString() {
        String solution ="" ; // + this.numberOfChromosomes;

        for (int i = 0; i < this.numberOfChromosomes; i++) {
            Chromosome chrom = this.getChromosome(i);
            solution += chrom.toString();
        }


        solution+="Phenotype: " + phenotype.toString() + "\n";

        solution+="FitnessScore: " + round(fitnessScore,2)+"\n";

        solution+="Fitness: "+ round(fitness,2)+"\n";
        return solution;

    }

    public void Mutation(Integer tf, Double mr) {

        for (int i = 0; i < this.chrList.size(); i++) {
            Chromosome chromToMutate = (Chromosome) this.chrList.get(i);
            chromToMutate.ChromosomeMutation(tf, mr);
        }
    }

    public void GeneSwapMutation(Integer nrGenes, Double mr){
        Random rng = new Random();
        Double geneA, geneB;
        for (int i = 0; i < nrGenes; i++) {
            if (rng.nextDouble() < mr) {
                geneA =this.getChromosome(0).chromosomeGenes.get(i);
                geneB=this.getChromosome(1).chromosomeGenes.get(i);
                this.getChromosome(0).chromosomeGenes.set(i,geneB);
                this.getChromosome(1).chromosomeGenes.set(i,geneA);
            }
        }
    }

    @Override
    public int compareTo(Individual o) {
        if (this.fitness < o.fitness)
            return -1;
        else if (this.fitness > o.fitness)
            return 1;
        return 0;
    }

    public ArrayList<Chromosome> getCromosomsList() {
        return chrList;
    }
}
