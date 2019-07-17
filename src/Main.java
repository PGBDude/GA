import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) {
        Population population = new Population();

        //Reading the Data in the file and creating the initial population
        int popSize = 100;
        if(popSize < 10000){
            try{
                Scanner sc = new Scanner(new File("src/date.txt"));
                for (int i = 0; i < popSize; i++) {
                    LinkedList<Double> genes = new LinkedList<>();
                    String gene = sc.nextLine();
                    StringTokenizer tokenizer = new StringTokenizer(gene, " ");
                    while(tokenizer.hasMoreTokens()){
                        genes.add(Double.parseDouble(tokenizer.nextToken()));
                    }
                    //System.out.println("Individ " + i + " : " + genes.toString());
                    Individual individual = new Individual(genes);
                    individual.calculateFitness();
                    //System.out.println("Individ " + i + " fitness: " + individual.getFitness());
                    population.addIndividual(individual);
                }
            }catch(Exception e){
                System.out.println(e);
            }
            population.debbuging();
        }
    }
}
