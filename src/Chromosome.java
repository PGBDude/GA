import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Chromosome implements Serializable {
    public ArrayList<Double> chromosomeGenes = new ArrayList<Double>();
    public double minvalue, maxvalue;
    Integer testFunction;
    Chromosome() {

    }

    public Chromosome(Integer nrGenes) {
        for (int j = 0; j < nrGenes; j++) {
            Random randomGenerator = new Random();
            double randValue =  randomGenerator.nextDouble();
            chromosomeGenes.add(randValue);
        }
    }

    public Chromosome(Integer nrGenes, double k) {

        for (int j = 0; j < nrGenes; j++) {

            chromosomeGenes.add(k);
        }
    }

    public Chromosome(Integer nrGenes, Integer testFunc){
        switch (testFunc){
            case 1: {                   // SchwefelFunction test function limits
                minvalue = -500;
                maxvalue = 500;
                break;
            }
            case 2: {                   // Ackley test function limits
                minvalue = -32.768;
                maxvalue = 32.768;
                break;
            }
            case 3: {                   // Sphere test function limits
                minvalue = -5.12;
                maxvalue = 5.12;
                break;
            }
            case 4: {                   // Rastrigin test function limits
                minvalue = -5.12;
                maxvalue = 5.12;
                break;
            }
            case 5: {                   // Griewank test function limits
                minvalue = -600;
                maxvalue = 600;
                break;
            }
            case 6: {
                minvalue = -10;
                maxvalue = 10;
                break;
            }
        }
        for (int j = 0; j < nrGenes; j++) {
            Random randomGenerator = new Random();
            double randValue = minvalue + (maxvalue - minvalue) * randomGenerator.nextDouble();
            chromosomeGenes.add(randValue);
        }
    }
    @Override
    public String toString(){
        String value = "";

        for(Double val: this.chromosomeGenes){
            value += Chromosome.round(val,2)+  " ";
        }
        return value;
    }

    public static String round(double value, int places) {
        return String.format("%." + places + "f", value);
    }

    public ArrayList getChromosomeGenes() {
        return chromosomeGenes;
    }

    public void setChromosomeGenes(ArrayList cromosom) {
        this.chromosomeGenes = cromosom;
    }

    public void ChromosomeMutation( int tf, Double mr) {
        Random rng = new Random();

        for (int i = 0; i < this.chromosomeGenes.size(); i++) {

            if (rng.nextDouble() < mr) {
                switch (tf) {
                    case 1: {                   // SchwefelFunction test function limits
                        minvalue = -500;
                        maxvalue = 500;
                        break;
                    }
                    case 2: {                   // Ackley test function limits
                        minvalue = -32.768;
                        maxvalue = 32.768;
                        break;
                    }
                    case 3: {                   // Sphere test function limits
                        minvalue = -5.12;
                        maxvalue = 5.12;
                        break;
                    }
                    case 4: {                   // Rastrigin test function limits
                        minvalue = -5.12;
                        maxvalue = 5.12;
                        break;
                    }
                    case 5: {                   // Griewank test function limits
                        minvalue = -600;
                        maxvalue = 600;
                        break;
                    }
                    case 6: {
                        minvalue = -10;
                        maxvalue = 10;
                        break;
                    }
                }
                Double randValue;
                Random randomGenerator = new Random();
                Random rnd = new Random();
                randValue = minvalue + (maxvalue - minvalue) * randomGenerator.nextDouble();
                this.chromosomeGenes.set(rnd.nextInt(chromosomeGenes.size()), randValue);
            }
        }
    }
}
