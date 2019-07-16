import java.util.ArrayList;

public class SchwefelFunction {

    Chromosome ch;


    public SchwefelFunction(Chromosome ch) {
        this.ch = ch;
    }

    public double Schwefel() {
        double sum = 0;
        double genesSum = 0;



        int genesNumber = 0;
        genesNumber = ch.chromosomeGenes.size();
        for (int j = 0; j < genesNumber; j++) {
            double geneVal = ch.chromosomeGenes.get(j);

            genesSum += geneVal;
            sum += geneVal * Math.sin(Math.sqrt(Math.abs(geneVal)));
        }

        double f = 418.9829 *  genesNumber;
        f -= sum;

        return f;

    }
}
