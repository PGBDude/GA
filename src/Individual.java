import java.util.LinkedList;

public class Individual implements Comparable<Individual> {

    private double fitness;
    private LinkedList<Double> genes = new LinkedList<>();

    Individual(LinkedList<Double> gene){
        this.genes = gene;
        this.fitness = 0;
    }

    public double getFitness(){
        return this.fitness;
    }

    public int geneSize(){
        return genes.size();
    }

    public void calculateFitness(){
        SchwefelFunction f = new SchwefelFunction();
        this.fitness = f.Schwefel(this);
    }

    LinkedList<Double> getGenes(){
        return this.genes;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public int compareTo(Individual o) {
        if (this.fitness < o.fitness)
            return -1;
        else if (this.fitness > o.fitness)
            return 1;
        return 0;
    }
}
