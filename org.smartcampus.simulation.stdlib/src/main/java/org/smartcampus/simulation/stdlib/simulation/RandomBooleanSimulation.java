package org.smartcampus.simulation.stdlib.simulation;

import org.smartcampus.simulation.framework.simulator.SimulationLaw;

public class RandomBooleanSimulation extends SimulationLaw<Object, Object, Boolean> {

    @Override
    protected Object[] computeValue() {
        return null;
    }

    @Override
    protected void onComplete() {
        /*int res = 0;
        for (Boolean b : this.values) {
            if (b) {
                res++;
            }
        }
        this.sendValue("Average", ((100 * res) / this.values.size()) + "%");*/
    }
}
