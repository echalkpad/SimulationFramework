package org.smartcampus.simulation.framework.fluentapi;

import org.smartcampus.simulation.framework.simulator.SensorTransformation;

/**
 * Created by foerster on 21/01/14.
 */
public interface SimulationLawWrapper0 {
    public SimulationLawWrapper1 add(final int nbsensors,final SensorTransformation<?, ?> transformation);
}