package org.smartcampus.simulation.stdlib.laws;

import javax.management.BadAttributeValueExpException;
import org.smartcampus.simulation.framework.simulator.Law;

public class MarkovStatesLaw extends Law<Integer, Double> {

    private int        size;
    private double[][] matrix;

    /**
     * Creation of the tri-diagonal matrix of Markov
     * 
     * @param nbPlaces
     * @param arrivalFreq
     * @param averageParkingTime
     * @throws BadAttributeValueExpException
     */
    public MarkovStatesLaw(final int nbPlaces, final double arrivalFreq,
            final double averageParkingTime) {
        this.size = nbPlaces + 1;
        this.matrix = new double[this.size][this.size];
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                // diagonal
                if (i == j) {
                    this.matrix[i][i] = -((i * averageParkingTime) + arrivalFreq);
                }
                else if (j == (i + 1)) {
                    this.matrix[i][j] = arrivalFreq;
                }
                else if (j == (i - 1)) {
                    this.matrix[i][j] = i * averageParkingTime;
                }
            }
        }
        this.matrix[nbPlaces][nbPlaces] = -(nbPlaces * averageParkingTime);
    }

    @Override
    protected Double evaluate(final Integer... x) throws Exception {
        if (x.length != 2) {
            // too much arguments or not enough
            throw new BadAttributeValueExpException("you need to give two ints");
        }
        int i = x[0];
        int j = x[1];
        return this.matrix[i][j];
    }

}
